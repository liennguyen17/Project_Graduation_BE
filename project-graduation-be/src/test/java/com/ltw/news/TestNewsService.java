package com.ltw.news;

import com.ltw.dto.entity.news.NewDTO;
import com.ltw.dto.request.news.CreateNewsRequest;
import com.ltw.dto.request.news.GetNewRequest;
import com.ltw.dto.request.news.GetNewsListRequest;
import com.ltw.dto.request.news.UpdateNewsRequest;
import com.ltw.model.News;
import com.ltw.service.NewService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.Year;

@SpringBootTest
@Slf4j
public class TestNewsService {
    @Autowired
    private NewService newService;

    @Test
    public void testCreateNew(){
        CreateNewsRequest request = new CreateNewsRequest();
        request.setTitle("Đề tài khóa luận: thiết kế web bán hàng.");
        request.setDescription("asdhflkagjlakj");
        request.setFile("alkfhdlh");
        request.setYear(Year.of(2026));
        request.setSubject("Công nghệ phần mềm");

        NewDTO newDTO = newService.createNew(request);
        log.info("Create new success: {}", newDTO);
    }

    @Test
    public void testUpdateNews(){
        UpdateNewsRequest request = new UpdateNewsRequest();
        request.setId(1);
        request.setDescription("KHLTN: Xây dựng hệ thống quản lý kltn");
        request.setTitle("Đề tài khóa luận: Xây dựng hệ thống quản lý kltn");

        NewDTO newDTO = newService.updateNew(request);
        log.info("Update new success: {}", newDTO);
    }

    @Test
    public void testGetListNew(){
        GetNewsListRequest request = new GetNewsListRequest();
        request.setStart(0);
        request.setLimit(3);
        request.setGetAll(true);
        Page<News> newsPage = newService.getListNews(request);
//        log.info("Get List success: {}", newsPage);
        newsPage.getContent().forEach(news -> {
            log.info("Post info: {}", news);
        });
        log.info("Total element: {}", newsPage.getTotalElements());
    }


    @Test
    public void testGetParamNew(){
        GetNewRequest request = new GetNewRequest();
        request.setStart(0);
        request.setLimit(10);
        Page<News> newsPage = newService.getNewByParam(request,PageRequest.of(request.getStart(), request.getLimit()));
//        log.info("Get List success: {}", newsPage);
        newsPage.getContent().forEach(news -> {
            log.info("Post info: {}", news);
        });
        log.info("Total element: {}", newsPage.getTotalElements());
    }

    @Test
    public void testGetByID(){
        NewDTO newDTO = newService.getNewById(2);
        Assertions.assertNotNull(newDTO);
        log.info("New: {}", newDTO);
    }

}
