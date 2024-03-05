package com.ltw.controller;

import com.ltw.constant.ErrorCodeDefs;
import com.ltw.dto.entity.masterData.MasterDataDTO;
import com.ltw.dto.entity.topic.TopicDTO;
import com.ltw.dto.request.masterData.CreateMasterDataRequest;
import com.ltw.dto.request.masterData.DeleteMasterDataRequest;
import com.ltw.dto.request.masterData.GetMasterDataRequest;
import com.ltw.dto.request.masterData.UpdateMasterDataRequest;
import com.ltw.dto.response.BaseItemResponse;
import com.ltw.dto.response.BaseListResponse;
import com.ltw.dto.response.BaseResponse;
import com.ltw.dto.response.ErrorDetail;
import com.ltw.model.MasterData;
import com.ltw.repository.masterData.CustomMasterDataQuery;
import com.ltw.service.MasterDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/master-data")
@RequiredArgsConstructor
public class MasterDataController {
    private final MasterDataService masterDataService;
    private final ModelMapper modelMapper;

    @PostMapping
    public BaseItemResponse<MasterDataDTO> createMasterData(@Valid @RequestBody CreateMasterDataRequest request){
        return BaseResponse.successData(masterDataService.createMasterData(request));
    }

    @PutMapping
    public BaseItemResponse<MasterDataDTO> updateMasterData(@Valid @RequestBody UpdateMasterDataRequest request){
        return BaseResponse.successData(masterDataService.updateMasterData(request));
    }

    @DeleteMapping
    public BaseResponse deleteMasterData(@Valid @RequestBody DeleteMasterDataRequest request){
        List<ErrorDetail> errorDetails = masterDataService.deleteMasterData(request);
        if(errorDetails == null){
            return BaseResponse.successData("Xóa thông báo thành công");
        }
        return BaseResponse.error(ErrorCodeDefs.ERR_VALIDATION, ErrorCodeDefs.getMessage(ErrorCodeDefs.ERR_VALIDATION), errorDetails);
    }

    @GetMapping("{id}")
    public BaseItemResponse<MasterDataDTO> getByIdMasterData(@PathVariable("id") Integer id){
        return BaseResponse.successData(masterDataService.getById(id));
    }

    @PostMapping("/filter")
    public BaseListResponse<MasterDataDTO> filterMasterData(@Valid @RequestBody GetMasterDataRequest request){
        Page<MasterData> masterDataPage = masterDataService.getMasterDataParam(request, PageRequest.of(request.getStart(), request.getLimit()));
        return BaseResponse.successListData(masterDataPage.getContent().stream().map(e -> modelMapper.map(e, MasterDataDTO.class)).collect(Collectors.toList()), (int) masterDataPage.getTotalElements());
    }
}
