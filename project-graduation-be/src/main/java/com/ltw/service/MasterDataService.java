package com.ltw.service;

import com.ltw.constant.Constants;
import com.ltw.dto.entity.masterData.MasterDataDTO;
import com.ltw.dto.entity.masterData.MasterDataDeleteDTO;
import com.ltw.dto.entity.masterData.MasterDataUpdateValueDto;
import com.ltw.dto.entity.notification.NotificationDeleteDTO;
import com.ltw.dto.request.masterData.CreateMasterDataRequest;
import com.ltw.dto.request.masterData.DeleteMasterDataRequest;
import com.ltw.dto.request.masterData.UpdateMasterDataRequest;
import com.ltw.dto.request.notification.DeleteNotificationRequest;
import com.ltw.dto.response.ErrorDetail;
import com.ltw.exception.DataNotFoundException;
import com.ltw.model.MasterData;
import com.ltw.repository.masterData.CustomMasterDataQuery;
import com.ltw.repository.masterData.MasterDataRepository;
import com.ltw.service.mapper.MasterDataUpdateMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("MasterDataService")
@RequiredArgsConstructor
@Slf4j
public class MasterDataService {
    private final MasterDataRepository masterDataRepository;
    private final ModelMapper modelMapper;
    private final MasterDataUpdateMapper updateMapper;

    public MasterDataDTO createMasterData(CreateMasterDataRequest request) {
        MasterData masterData = modelMapper.map(request, MasterData.class);
        masterData.setType(request.getType());
        masterData.setName(request.getName());
        masterData.setCode(request.getCode());
        return modelMapper.map(masterDataRepository.saveAndFlush(masterData), MasterDataDTO.class);
    }

    public MasterDataDTO updateMasterData(UpdateMasterDataRequest request) {
        Optional<MasterData> masterData = masterDataRepository.findById(request.getId());
        if (!masterData.isPresent()) {
            throw new DataNotFoundException(Constants.ErrorMasterDataValidation.NOT_FIND_MASTER_DATA_BY_ID + request.getId());
        }
        MasterDataUpdateValueDto updateValueDto = modelMapper.map(request, MasterDataUpdateValueDto.class);
        MasterData updateValue = masterData.get();
        updateMapper.updateMasterDataFromDto(updateValueDto, updateValue);
//        updateValue.setName(request.getName()); // them nay thi trong mapper van se set gia tri moi vao name
        return modelMapper.map(masterDataRepository.saveAndFlush(updateValue), MasterDataDTO.class);
    }

    public List<ErrorDetail> deleteMasterData(DeleteMasterDataRequest request) {
        log.info("deleteMasterData: {}", request.getIds());
        List<MasterDataDeleteDTO> masterDataDTOS = masterDataRepository.findAllById(request.getIds()).stream()
                .map(e -> modelMapper.map(e, MasterDataDeleteDTO.class)).toList();
        List<ErrorDetail> errorDetails = new ArrayList<>();
        for (Integer requestId : request.getIds()) {
            boolean isExist = masterDataDTOS.stream().anyMatch(e -> e.getId().equals(requestId));
            if (!isExist) {
                ErrorDetail errorDetail = new ErrorDetail();
                errorDetail.setId(requestId.toString());
                errorDetail.setMessage(Constants.ErrorMessageNotificationValidation.NOT_FIND_NOTIFICATION_BY_ID + requestId);
                errorDetails.add(errorDetail);
            }
        }

        if (errorDetails.isEmpty()) {
            masterDataRepository.deleteAllById(request.getIds());
            return null;
        }

        return errorDetails;
    }

    public MasterDataDTO getById(Integer id) {
        Optional<MasterData> masterData = masterDataRepository.findById(id);
        if (!masterData.isPresent()) {
            throw new DataNotFoundException(Constants.ErrorMasterDataValidation.NOT_FIND_MASTER_DATA_BY_ID + id);
        }
        return modelMapper.map(masterData.get(), MasterDataDTO.class);
    }

    public Page<MasterData> getMasterDataParam(CustomMasterDataQuery.MasterDataFilterParam param, PageRequest pageRequest){
        Specification<MasterData> specification = CustomMasterDataQuery.getFilterMasterData(param);
        return masterDataRepository.findAll(specification, pageRequest);
    }


}
