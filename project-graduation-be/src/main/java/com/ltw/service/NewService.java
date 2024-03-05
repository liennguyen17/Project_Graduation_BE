package com.ltw.service;

import com.ltw.constant.Constants;
import com.ltw.dto.entity.news.NewDTO;
import com.ltw.dto.entity.news.NewDeleteDTO;
import com.ltw.dto.entity.news.NewUpdateValueDto;
import com.ltw.dto.request.news.CreateNewsRequest;
import com.ltw.dto.request.news.DeleteNewsRequest;
import com.ltw.dto.request.news.GetNewsListRequest;
import com.ltw.dto.request.news.UpdateNewsRequest;
import com.ltw.dto.response.ErrorDetail;
import com.ltw.exception.DataNotFoundException;
import com.ltw.model.News;
import com.ltw.repository.news.CustomNewsQuery;
import com.ltw.repository.news.NewsRepository;
import com.ltw.service.mapper.NewUpdateMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("NewService")
@RequiredArgsConstructor
@Slf4j
public class NewService {
    private final NewsRepository newsRepository;
    private final ModelMapper modelMapper;
    private final NewUpdateMapper newUpdateMapper;
    public NewDTO createNew(CreateNewsRequest request){
        try{
            News news = modelMapper.map(request, News.class);
            news.setTitle(request.getTitle());
            news.setDescription(request.getDescription());
            news.setSubject(request.getSubject());
            news.setFile(request.getFile());
            news.setYear(request.getYear());
            news.setCreateAt(new Timestamp(System.currentTimeMillis()));
            news.setUpdateAt(new Timestamp(System.currentTimeMillis()));
            return modelMapper.map(newsRepository.saveAndFlush(news), NewDTO.class);
        }catch (Exception ex){
            log.info("loi : {}", ex);
            throw new DataNotFoundException("Quá trình tạo tin tức không thành công!");
        }
    }

    public  NewDTO updateNew(UpdateNewsRequest request){
        Optional<News> news = newsRepository.findById(request.getId());
        if(!news.isPresent()){
            throw new DataNotFoundException(Constants.ErrorMessageNewsValidation.NOT_FIND_NEW_BY_ID + request.getId());
        }
        NewUpdateValueDto updateValueDto = modelMapper.map(request, NewUpdateValueDto.class);
        News updateValue = news.get();
        newUpdateMapper.updateNewFromDto(updateValueDto, updateValue);
        updateValue.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        return modelMapper.map(newsRepository.saveAndFlush(updateValue), NewDTO.class);
    }

    public List<ErrorDetail> deleteNews(DeleteNewsRequest request){
        List<NewDeleteDTO> news = newsRepository.findAllById(request.getIds()).stream()
                .map(e -> modelMapper.map(e, NewDeleteDTO.class)).toList();
        List<ErrorDetail> errorDetails = new ArrayList<>();
        for (Integer requestId :  request.getIds()){
            boolean isExist = news.stream().anyMatch(e -> e.getId().equals(requestId));
            if(!isExist) {
                ErrorDetail errorDetail = new ErrorDetail();
                errorDetail.setId(requestId.toString());
                errorDetail.setMessage(Constants.ErrorMessageNewsValidation.NOT_FIND_NEW_BY_ID + requestId);
                errorDetails.add(errorDetail);
            }
        }

        if(errorDetails.isEmpty()){
            newsRepository.deleteAllById(request.getIds());
            return null;
        }

        return errorDetails;
    }

    public NewDTO getNewById(Integer id){
        Optional<News> news = newsRepository.findById(id);
        if(!news.isPresent()){
            throw new DataNotFoundException(Constants.ErrorMessageNewsValidation.NOT_FIND_NEW_BY_ID + id);
        }
        return modelMapper.map(news.get(), NewDTO.class);
    }

    public Page<News> getListNews(GetNewsListRequest request){
        if(request.getGetAll()){
            request.setLimit((int) newsRepository.count());
        }
        return newsRepository.findAll(PageRequest.of(request.getStart(), request.getLimit()));
    }

    public Page<News> getNewByParam(CustomNewsQuery.NewsFilterParam param, PageRequest pageRequest){
        Specification<News> specification = CustomNewsQuery.getFilterNew(param);
        return newsRepository.findAll(specification, pageRequest);
    }
}
