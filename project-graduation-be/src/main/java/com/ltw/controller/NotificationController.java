package com.ltw.controller;

import com.ltw.constant.ErrorCodeDefs;
import com.ltw.dto.entity.notification.NotificationDTO;
import com.ltw.dto.request.notification.CreateNotificationRequest;
import com.ltw.dto.request.notification.DeleteNotificationRequest;
import com.ltw.dto.request.notification.GetNotificationRequest;
import com.ltw.dto.request.notification.UpdateNotificationRequest;
import com.ltw.dto.response.BaseItemResponse;
import com.ltw.dto.response.BaseListResponse;
import com.ltw.dto.response.BaseResponse;
import com.ltw.dto.response.ErrorDetail;
import com.ltw.model.Notification;
import com.ltw.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGE')")
    public BaseItemResponse<NotificationDTO> createNotification(@Valid @RequestBody CreateNotificationRequest request) {
        return BaseResponse.successData(notificationService.createNotification(request));
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGE')")
    public BaseItemResponse<NotificationDTO> updateNotification(@Valid @RequestBody UpdateNotificationRequest request) {
        return BaseResponse.successData(notificationService.updateNotification(request));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGE')")
    public BaseResponse deleteNotification(@Valid @RequestBody DeleteNotificationRequest request) {
        List<ErrorDetail> errorDetailList = notificationService.deleteNotification(request);
        if (errorDetailList == null) {
            return BaseResponse.successData("Xóa thông báo thành công");
        }
        return BaseResponse.error(ErrorCodeDefs.ERR_VALIDATION, ErrorCodeDefs.getMessage(ErrorCodeDefs.ERR_VALIDATION), errorDetailList);
    }

    @GetMapping("{id}")
    public BaseItemResponse<NotificationDTO> getByIdNotification(@PathVariable("id") Integer id) {
        return BaseResponse.successData(notificationService.getById(id));
    }

    @PostMapping("/filter")
    public BaseListResponse<NotificationDTO> filterNotification(@Valid @RequestBody GetNotificationRequest request) {
        Page<Notification> notificationPage = notificationService.getNotificationParam(request, PageRequest.of(request.getStart(), request.getLimit()));
        return BaseResponse.successListData(notificationPage.getContent().stream().map(e -> modelMapper.map(e, NotificationDTO.class)).collect(Collectors.toList()), (int) notificationPage.getTotalElements());
    }
}
