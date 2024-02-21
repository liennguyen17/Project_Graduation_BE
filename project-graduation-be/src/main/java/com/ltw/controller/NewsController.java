package com.ltw.controller;

import com.ltw.constant.ErrorCodeDefs;
import com.ltw.dto.entity.news.NewDTO;
import com.ltw.dto.request.news.*;
import com.ltw.dto.response.BaseItemResponse;
import com.ltw.dto.response.BaseListResponse;
import com.ltw.dto.response.BaseResponse;
import com.ltw.dto.response.ErrorDetail;
import com.ltw.model.News;
import com.ltw.service.NewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewService newService;
    private final ModelMapper modelMapper;

    @PostMapping("")
    public BaseItemResponse<NewDTO> createNew(@Valid @RequestBody CreateNewsRequest request){
        return BaseResponse.successData(newService.createNew(request));
    }

    @PutMapping
    public BaseItemResponse<NewDTO> updateNew(@Valid @RequestBody UpdateNewsRequest request){
        return BaseResponse.successData(newService.updateNew(request));
    }

    @DeleteMapping
    public BaseResponse deleteNews(@Valid @RequestBody DeleteNewsRequest request){
        List<ErrorDetail> errorDetails = newService.deleteNews(request);
        return BaseResponse.error(ErrorCodeDefs.ERR_VALIDATION,
                ErrorCodeDefs.getMessage(ErrorCodeDefs.ERR_VALIDATION), errorDetails);
    }

    @GetMapping("{id}")
    public BaseItemResponse<NewDTO> getNewById(@PathVariable("id") Integer id){
        return BaseResponse.successData(newService.getNewById(id));
    }

    @PostMapping("/getList")
    public BaseListResponse<NewDTO> getListNew(@Valid @RequestBody GetNewsListRequest request){
        Page<News> newsPage = newService.getListNews(request);
        return BaseResponse.successListData(newsPage.getContent().stream()
                .map(e -> modelMapper.map(e, NewDTO.class)).collect(Collectors.toList()), (int)newsPage.getTotalElements());
    }

    //tim kiem
    @PostMapping("/filter")
    public BaseListResponse<NewDTO> filterNews(@Valid @RequestBody GetNewRequest request){
        Page<News> newsPage = newService.getNewByParam(request, PageRequest.of(request.getStart(), request.getLimit()));
        return BaseResponse.successListData(newsPage.getContent().stream()
                .map(e->modelMapper.map(e, NewDTO.class))
                .collect(Collectors.toList()), (int)newsPage.getTotalElements());
    }

}
