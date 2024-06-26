package com.ltw.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.ltw.constant.Constants;
import com.ltw.dto.entity.topic.TopicDTO;
import com.ltw.dto.entity.topic.TopicDTO1;
import com.ltw.dto.entity.topic.TopicDeleteDTO;
import com.ltw.dto.request.topic.*;
import com.ltw.dto.response.ErrorDetail;
import com.ltw.exception.DataNotFoundException;
import com.ltw.model.Topic;
import com.ltw.model.User;
import com.ltw.repository.topic.CustomTopicQuery;
import com.ltw.repository.topic.TopicRepository;
import com.ltw.repository.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service("TopicService")
@RequiredArgsConstructor
@Slf4j
public class TopicService {
    private final UsersRepository usersRepository;
    private final TopicRepository topicRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;


    public TopicDTO createTopic(CreateTopicRequest request) {
        Topic topic = modelMapper.map(request, Topic.class);
        User teacher = userService.findTeacher(request.getTeacher());
        User student = userService.findStudent(request.getStudent());

        topic.setNameTopic(request.getNameTopic());
        topic.setSemester(request.getSemester());
        topic.setStatus(request.getStatus());
        topic.setDepartmentManagement(request.getDepartmentManagement());
        topic.setMenterInternshipFacility(request.getMenterInternshipFacility());
        topic.setNameInternshipFacility(request.getNameInternshipFacility());
        topic.setPhoneInstructorInternshipFacility(request.getPhoneInstructorInternshipFacility());
        topic.setTeacher(teacher);
        topic.setStudent(student);
        topic.setInstructor(request.getInstructor());
        topic.setReviewer(request.getReviewer());
        topic.setBoardMembers1(request.getBoardMembers1());
        topic.setBoardMembers2(request.getBoardMembers2());
        topic.setBoardMembers3(request.getBoardMembers3());
        topic.setScoresInternshipFacility(request.getScoresInternshipFacility());
        topic.setCreateAt(new Timestamp(System.currentTimeMillis()));
        topic.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        Topic savedTopic = topicRepository.saveAndFlush(topic);
        calculateResult(topic);
        return modelMapper.map(savedTopic, TopicDTO.class);
    }

    public TopicDTO1 studentCreateTopic(StudentCreateTopicRequest request) {
        Optional<User> userOptional = userService.getCurrentUser();
        if (userOptional.isPresent()) {
            User currentUser = userOptional.get();
            if (currentUser.getRole().equals("STUDENT")) {
//                if (hasStudentSubmittedTopic(currentUser.getId())) {
//                    throw new DataNotFoundException("Đơn đăng ký đề tài chỉ được gửi một lần, bạn đã gửi đơn đăng ký không thể gửi tiếp");
//
//                }
                if(hasStudentSubmittedTopicForSemester(currentUser.getId(), request.getSemester())){
                    throw new DataNotFoundException("Đơn đăng ký đề tài chỉ được gửi một lần trong mỗi kỳ, bạn đã gửi đơn đăng ký cho kỳ này.");
                }
                Topic topic = modelMapper.map(request, Topic.class);
                User teacher = userService.findTeacher(request.getTeacher());
                topic.setNameTopic(request.getNameTopic());
                topic.setTeacher(teacher);
                topic.setStudent(currentUser);
                topic.setSemester(request.getSemester());
                topic.setStatus("Chờ phê duyệt");
                topic.setDepartmentManagement(request.getDepartmentManagement());
                topic.setMenterInternshipFacility(request.getMenterInternshipFacility());
                topic.setNameInternshipFacility(request.getNameInternshipFacility());
                topic.setPhoneInstructorInternshipFacility(request.getPhoneInstructorInternshipFacility());
                topic.setCreateAt(new Timestamp(System.currentTimeMillis()));
                topic.setUpdateAt(new Timestamp(System.currentTimeMillis()));
                Topic saveTopic = topicRepository.saveAndFlush(topic);
                return modelMapper.map(saveTopic, TopicDTO1.class);
            } else {
                throw new IllegalStateException("Người dùng đăng nhập không phải là sinh viên.");
            }
        } else {
            throw new DataNotFoundException("Không tìm thấy thông tin người dùng đang đăng nhập");
        }
    }

    public TopicDTO studentViewTopic(){
        Topic userTopic = findTopicFromStudentLogin();
        if(userTopic == null){
            throw new DataNotFoundException("Không tìm thấy đề tài của người dùng hiện tại.");
        }

        return modelMapper.map(userTopic, TopicDTO.class);
    }

    public Topic findTopicFromStudentLogin(){
        Optional<User> userOptional = userService.getCurrentUser();
        if(userOptional.isEmpty()){
            throw new DataNotFoundException("Không tìm thấy thông tin người dùng hiện tại vui lòng đăng nhập lại.");
        }

        User currentUser = userOptional.get();
        List<Topic> topics = topicRepository.findTopicByStudentOrderByCreateAtDesc(currentUser);

        if(topics.isEmpty()){
            throw new DataNotFoundException("Không tìm thấy đơn đăng ký của sinh viên để trả về kết quá.");
        }
        return topics.get(0);
//        return topicRepository.findByStudent(currentUser);
//        return topicRepository.findTopicByStudentOrderByCreateAtDesc(currentUser);
    }

    public ByteArrayInputStream generatePdf(){
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Optional<User> currentUserOptional = userService.getCurrentUser();
        if(currentUserOptional.isPresent()){
            User currentUser = currentUserOptional.get();
            if(currentUser.getRole().equals("STUDENT")){
                Topic topic = topicRepository.findByStudent(currentUser);
                try{
                    PdfWriter writer = PdfWriter.getInstance(document, out);
                    document.open();

                    //Embed font
                    BaseFont baseFont = BaseFont.createFont("src/main/resources/times-new-roman-14.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                    BaseFont baseFont1 = BaseFont.createFont("src/main/resources/SVN-Times New Roman Italic.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

                    //header
                    Paragraph header = new Paragraph();
                    header.setAlignment(Element.ALIGN_CENTER);
                    header.add(Chunk.NEWLINE);
                    header.add(Chunk.NEWLINE);
                    header.add(new Phrase("CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM", new Font(baseFont, 15, Font.BOLD)));
                    header.add(Chunk.NEWLINE);
                    header.add(new Phrase("Độc lập – Tự do – Hạnh phúc", new Font(baseFont, 15, Font.BOLD)));
                    header.add(Chunk.NEWLINE);
                    header.add(new Phrase("---------", new Font(baseFont, 15, Font.BOLD)));
                    header.add(Chunk.NEWLINE);
                    header.add(Chunk.NEWLINE);
                    header.add(Chunk.NEWLINE);
                    header.add(Chunk.NEWLINE);
                    header.add(new Phrase("ĐƠN XIN XÁC NHẬN CỦA ĐƠN VỊ THỰC TẬP", new Font(baseFont, 18, Font.BOLD)));
                    document.add(header);

                    //body
                    Paragraph body = new Paragraph();
                    body.add(Chunk.NEWLINE);
                    body.add(Chunk.NEWLINE);
                    body.add(new Phrase("Kính gửi: " + topic.getNameInternshipFacility(), new Font(baseFont1, 14, Font.BOLD)));
                    body.add(Chunk.NEWLINE);
                    body.add(new Phrase("Tên tôi là: " + topic.getStudent().getName(), new Font(baseFont, 14)));
                    body.add(Chunk.NEWLINE);
                    body.add(new Phrase("Mã sinh viên: " + topic.getStudent().getUserCode(), new Font(baseFont, 14)));
                    body.add(Chunk.NEWLINE);
                    body.add(new Phrase("Sinh viên lớp: " + topic.getStudent().getClassName() + " - Khoa Công nghệ thông tin - Học viện Nông nghiệp Việt Nam", new Font(baseFont, 14)));
//                    body.add(Chunk.NEWLINE);
//                    body.add(new Phrase(" - Khoa Công nghệ thông tin - Học viện Nông nghiệp Việt Nam" , new Font(baseFont, 14)));
                    body.add(Chunk.NEWLINE);
                    body.add(new Phrase("Được sự đồng ý của đơn vị, trong thời gian từ ngày..................đến ngày..................." , new Font(baseFont, 14)));
                    body.add(Chunk.NEWLINE);
                    body.add(new Phrase("tôi thực tập, làm quen với công việc thực tế tại đơn vị. Dự kiến tôi được giao những công " , new Font(baseFont, 14)));
                    body.add(new Phrase("việc sau với thời gian.................buổi / 1 tuần" , new Font(baseFont, 14)));
                    body.add(Chunk.NEWLINE);
                    body.add(new Phrase("Đề tài thực tập : " + topic.getNameTopic() , new Font(baseFont, 14)));
                    body.add(Chunk.NEWLINE);
                    body.add(new Phrase("Họ và tên cán bộ hướng dẫn tại đơn vị thực tập: " + topic.getMenterInternshipFacility(), new Font(baseFont, 14)));
                    body.add(Chunk.NEWLINE);
                    body.add(new Phrase("Số điện thoại cán bộ hướng dẫn tại đơn vị thực tập: " + topic.getPhoneInstructorInternshipFacility(), new Font(baseFont, 14)));
                    document.add(body);

                    //footer
                    Paragraph footer = new Paragraph();
//                    footer.setAlignment(Element.ALIGN_RIGHT);
                    footer.add(Chunk.NEWLINE);
                    footer.add(Chunk.NEWLINE);
                    footer.add(new Phrase("                                                                                     Hà Nội, ngày ... tháng ... năm ...", new Font(baseFont1, 14)));
                    footer.add(Chunk.NEWLINE);
                    footer.add(new Phrase("             XÁC NHẬN CỦA                                                  NGƯỜI LÀM ĐƠN", new Font(baseFont, 14, Font.BOLD)));
                    footer.add(Chunk.NEWLINE);
                    footer.add(new Phrase("           ĐƠN VỊ THỰC TẬP", new Font(baseFont, 14, Font.BOLD)));
                    footer.add(Chunk.NEWLINE);
                    footer.add(new Phrase("             (Ký tên, đóng dấu)", new Font(baseFont1, 14, Font.BOLD)));
                    document.add(footer);

                    document.close();


                } catch (DocumentException | IOException e) {
                    throw new RuntimeException(e);
                }
                return new ByteArrayInputStream(out.toByteArray());
            } else {
                throw new DataNotFoundException("Người dùng không phải là sinh viên.");
            }
        } else {
            throw new DataNotFoundException("Không tìm thấy thông tin người dùng đăng nhập.");
        }
    }

    public boolean hasStudentSubmittedTopic(Integer userId) {
        return topicRepository.existsByStudentId(userId);
    }


    public TopicDTO updateTopic(UpdateTopicRequest request) {
        User teacher = userService.findTeacher(request.getTeacherId());
        User student = userService.findStudent(request.getStudentId());
        Optional<Topic> topicOptional = topicRepository.findById(request.getId());
        Topic topic = topicOptional.get();
        topic.setNameTopic(request.getNameTopic());
        topic.setStatus(request.getStatus());
        topic.setNote(request.getNote());
        topic.setTeacher(teacher);
        topic.setStudent(student);
        topic.setSemester(request.getSemester());
        topic.setDepartmentManagement(request.getDepartmentManagement());
        topic.setMenterInternshipFacility(request.getMenterInternshipFacility());
        topic.setNameInternshipFacility(request.getNameInternshipFacility());
        topic.setPhoneInstructorInternshipFacility(request.getPhoneInstructorInternshipFacility());
        topic.setInstructor(request.getInstructor());
        topic.setReviewer(request.getReviewer());
        topic.setBoardMembers1(request.getBoardMembers1());
        topic.setBoardMembers2(request.getBoardMembers2());
        topic.setBoardMembers3(request.getBoardMembers3());
        topic.setScoresInternshipFacility(request.getScoresInternshipFacility());
        topic.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        calculateResult(topic);
        calculateSuccess(topic);
        return modelMapper.map(topicRepository.saveAndFlush(topic), TopicDTO.class);
    }

    public List<ErrorDetail> deleteTopics(DeleteTopicRequest request) {
        log.info("deleteUser: {}", request.getIds());
        List<TopicDeleteDTO> users = usersRepository.findAllById(request.getIds()).stream()
                .map(u -> modelMapper.map(u, TopicDeleteDTO.class)).toList();
        List<ErrorDetail> errorDetails = new ArrayList<>();
        for (Integer requesId : request.getIds()) {
            boolean isExit = users.stream().anyMatch(u -> u.getId().equals(requesId));
            if (!isExit) {
                ErrorDetail errorDetail = new ErrorDetail();
                errorDetail.setId(requesId.toString());
                errorDetail.setMessage(Constants.ErrorMessageTopicValidation.NOT_FIND_TOPIC_BY_ID+ request.getIds());
                errorDetails.add(errorDetail);
            }
        }

        if (errorDetails.isEmpty()) {
            usersRepository.deleteAllById(request.getIds());
            return null;
        }
        return errorDetails;
    }

    public TopicDTO getTopicById(Integer id) {
        Optional<Topic> topic = topicRepository.findById(id);
        if (!topic.isPresent()) {
            throw new DataNotFoundException("Không tìm thấy đề tài có ID " + id);
        }
        return modelMapper.map(topic.get(), TopicDTO.class);
    }



    public Page<Topic> getTopicByParam(CustomTopicQuery.TopicFilterParam param, PageRequest request) {
        Specification<Topic> specification = CustomTopicQuery.getFilterTopic(param);
        return topicRepository.findAll(specification, request);
    }

    //statistics
    public List<Map<String, Object>> getSuccessStatistics(StatisticsSuccessRequest request) {
        List<Topic> topics = topicRepository.findBySemester(request.getSemester());

        Map<String, Integer> frequencyMap = new HashMap<>();
        for (Topic topic : topics) {
            String success = topic.getSuccess();
            if (success != null && (success.equals("Qua KLTN") || success.equals("Trượt KLTN") || success.equals("Trượt (điểm GVHD + GVPB < 5,5)") || success.equals("Chưa có kết quả"))) {
                frequencyMap.put(success, frequencyMap.getOrDefault(success, 0) + 1);
            }
        }

        return frequencyMap.entrySet().stream().map(entry -> {
            Map<String, Object> map = new HashMap<>();
            map.put("type", entry.getKey());
            map.put("quantity", entry.getValue());
            return map;
        }).collect(Collectors.toList());

    }



    public boolean existsById(Integer topicId) {
        return topicRepository.existsById(topicId);
    }

    public Topic findById(Integer topicId) {
        return topicRepository.findById(topicId).orElseThrow(() -> new DataNotFoundException("Không tìm thấy topic với id là " + topicId));
    }

//    public void calculateResult(Topic topic) {
//        if (isAllValuesProvidedForCalculation(topic)) {
//            float scoresInternshipFacility = topic.getScoresInternshipFacility() >= 8.5 ? 1 : 0;
//            float boardMembersAvg = (topic.getBoardMembers1() + topic.getBoardMembers2() + topic.getBoardMembers3())/3;
//            float result = scoresInternshipFacility + ((boardMembersAvg*3) + topic.getInstructor() + topic.getReviewer())/5;
//            topic.setResult(result);
//        }
//    }

    public void calculateResult(Topic topic) {
        if (isAllValuesProvidedForCalculation(topic)) {
            float instructorScore = topic.getInstructor();
            float reviewerScore = topic.getReviewer();
            float boardMember1Score = topic.getBoardMembers1();
            float boardMember2Score = topic.getBoardMembers2();
            float boardMember3Score = topic.getBoardMembers3();

            float boardMembersAvg = (boardMember1Score + boardMember2Score + boardMember3Score) / 3;
            float A = instructorScore + reviewerScore;

            if (A >= 5.5) {
                float C = instructorScore - boardMembersAvg;
                float D = reviewerScore - boardMembersAvg;

                boolean keepInstructor = true;
                boolean keepReviewer = true;

                if (C >= 2) {
                    keepInstructor = false;
                }
                if (D >= 2) {
                    keepReviewer = false;
                }

                float sumScores = boardMembersAvg * 3;
                int count = 3; // Always include the board member scores

                if (keepInstructor) {
                    sumScores += instructorScore;
                    count++;
                }
                if (keepReviewer) {
                    sumScores += reviewerScore;
                    count++;
                }

                float result = sumScores / count;
                topic.setResult(result);
            } else {
                topic.setSuccess("Trượt (điểm GVHD + GVPB < 5,5)");
            }
        }
    }

// You will also need to ensure that the following methods exist in the Topic class
// float getInstructor();
// float getReviewer();
// float getBoardMembers1();
// float getBoardMembers2();
// float getBoardMembers3();
// void setResult(float result);
// void setSuccess(String message);


//    public void calculateSuccess(Topic topic) {
//        Float result = topic.getResult();
//        if (result == null) {
//            topic.setSuccess("Chưa có kết quả");
//        } else if (result >= 4.0) {
//            topic.setSuccess("Qua KLTN");
//        } else {
//            topic.setSuccess("Trượt KLTN");
//        }
//    }

    public void calculateSuccess(Topic topic) {
        Float result = topic.getResult();
        float instructorScore = topic.getInstructor();
        float reviewerScore = topic.getReviewer();
        float A = instructorScore + reviewerScore;

        if (result == null) {
            topic.setSuccess("Chưa có kết quả");
        } else if (A < 5.5) {
            topic.setSuccess("Trượt (điểm GVHD + GVPB < 5,5)");
        } else if (result >= 4.0) {
            topic.setSuccess("Qua KLTN");
        } else {
            topic.setSuccess("Trượt KLTN");
        }
    }

// You will also need to ensure that the following methods exist in the Topic class
// float getInstructor();
// float getReviewer();
// Float getResult();
// void setSuccess(String message);



    public boolean isAllValuesProvidedForCalculation(Topic topic) {
        return topic.getScoresInternshipFacility() != null &&
                topic.getInstructor() != null &&
                topic.getReviewer() != null &&
                topic.getBoardMembers1() != null &&
                topic.getBoardMembers2() != null &&
                topic.getBoardMembers3() != null;

    }

//    public List<TopicDTO> getTopicForTeacher(){
//        Optional<User> currentUserOptional = userService.getCurrentUser();
//        if (currentUserOptional.isPresent()) {
//            User currentUser = currentUserOptional.get();
//            if (currentUser.getRole().equals("TEACHER")) {
//                List<Topic> topics = topicRepository
//            }
//    }

    private boolean hasStudentSubmittedTopicForSemester(Integer studentId, String semester){
        return topicRepository.existsByStudentIdAndSemester(studentId, semester);
    }




}
