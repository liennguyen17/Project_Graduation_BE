package com.ltw.dto.request.masterData;

import com.ltw.constant.Constants;
import com.ltw.repository.masterData.CustomMasterDataQuery;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class GetMasterDataRequest extends CustomMasterDataQuery.MasterDataFilterParam {
    @Min(value = 0, message = Constants.ErrorMasterDataValidation.START_SIZE)
    private Integer start = 0;
    @Range(min = 5, max = 50, message = Constants.ErrorMasterDataValidation.LIMIT_SIZE)
    private Integer limit = 10;
}
