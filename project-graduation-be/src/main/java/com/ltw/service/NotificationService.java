package com.ltw.service;

import com.ltw.constant.Constants;
import com.ltw.dto.entity.notification.NotificationDTO;
import com.ltw.dto.entity.notification.NotificationDeleteDTO;
import com.ltw.dto.entity.notification.NotificationUpdateValueDto;
import com.ltw.dto.request.notification.CreateNotificationRequest;
import com.ltw.dto.request.notification.DeleteNotificationRequest;
import com.ltw.dto.request.notification.UpdateNotificationRequest;
import com.ltw.dto.response.ErrorDetail;
import com.ltw.exception.DataNotFoundException;
import com.ltw.model.Notification;
import com.ltw.model.User;
import com.ltw.repository.notification.CustomNotificationQuery;
import com.ltw.repository.notification.NotificationRepository;
import com.ltw.service.mapper.NotificationUpdateMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("NotificationService")
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final ModelMapper modelMapper;
    private final NotificationUpdateMapper notificationUpdateMapper;
    private final UserService userService;

    public NotificationDTO createNotification(CreateNotificationRequest request) {
        try {
            Optional<User> userOptional = userService.getCurrentUser();
            if(userOptional.isEmpty()){
                throw new DataNotFoundException("Không tìm thấy thông tin người dùng tạo thông báo");
            }
            User user = userOptional.get();
            Notification notification = modelMapper.map(request, Notification.class);
            notification.setTitle(request.getTitle());
            notification.setDescription(request.getDescription());
            notification.setContent(request.getContent());
            notification.setFile(request.getFile());
            notification.setIsRead(request.getIsRead());
            notification.setUser(user);
            notification.setCreateAt(new Timestamp(System.currentTimeMillis()));
            notification.setUpdateAt(new Timestamp(System.currentTimeMillis()));
            return modelMapper.map(notificationRepository.saveAndFlush(notification), NotificationDTO.class);
        } catch (Exception ex) {
            log.info("loi : {}", ex);
            throw new DataNotFoundException("Quá trình tạo thông báo không thành công!" + ex);
        }
    }

    public NotificationDTO updateNotification(UpdateNotificationRequest request) {
        Optional<Notification> notification = notificationRepository.findById(request.getId());
        if (!notification.isPresent()) {
            throw new DataNotFoundException(Constants.ErrorMessageNotificationValidation.NOT_FIND_NOTIFICATION_BY_ID + request.getId());
        }
        NotificationUpdateValueDto updateValueDto = modelMapper.map(request, NotificationUpdateValueDto.class);
        Notification updateValue = notification.get();
        notificationUpdateMapper.updateNotificationFromDto(updateValueDto, updateValue);
        updateValue.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        return modelMapper.map(notificationRepository.saveAndFlush(updateValue), NotificationDTO.class);
    }

    public List<ErrorDetail> deleteNotification(DeleteNotificationRequest request) {
        log.info("deleteNotification: {}", request.getIds());
        List<NotificationDeleteDTO> notificationDTOS = notificationRepository.findAllById(request.getIds()).stream()
                .map(e -> modelMapper.map(e, NotificationDeleteDTO.class)).toList();
        List<ErrorDetail> errorDetails = new ArrayList<>();
        for (Integer requestId : request.getIds()) {
            boolean isExist = notificationDTOS.stream().anyMatch(e -> e.getId().equals(requestId));
            if (!isExist) {
                ErrorDetail errorDetail = new ErrorDetail();
                errorDetail.setId(requestId.toString());
                errorDetail.setMessage(Constants.ErrorMessageNotificationValidation.NOT_FIND_NOTIFICATION_BY_ID + requestId);
                errorDetails.add(errorDetail);
            }
        }

        if (errorDetails.isEmpty()) {
            notificationRepository.deleteAllById(request.getIds());
            return null;
        }

        return errorDetails;
    }

    public NotificationDTO getById(Integer id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        if (!notification.isPresent()) {
            throw new DataNotFoundException(Constants.ErrorMessageNotificationValidation.NOT_FIND_NOTIFICATION_BY_ID + id);
        }
        return modelMapper.map(notification.get(), NotificationDTO.class);
    }

    public Page<Notification> getNotificationParam(CustomNotificationQuery.NotificationFilterParam param, PageRequest pageRequest) {
        Specification<Notification> specification = CustomNotificationQuery.getFilterNotification(param);
        return notificationRepository.findAll(specification, pageRequest);
    }

}
