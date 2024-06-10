package com.ltw.importFileExcel.thread;

import com.ltw.constant.Constants;
import com.ltw.importFileExcel.ExcelData;
import com.ltw.model.User;
import com.ltw.repository.users.UsersRepository;
import com.ltw.utils.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;

@AllArgsConstructor
public class StoreData implements Callable<ExcelData> {
    private final UsersRepository usersRepository;
    private final PasswordEncoder encoder;
    private final String userStr;
    private final int row;
    @Override
    public ExcelData call() throws Exception {
        ExcelData excelData = new ExcelData();
        if(!userStr.isEmpty()){
            String[] infoList = userStr.strip().split(",");
            String name = infoList[0].strip();
            String userCode = infoList[1].strip();
            String username = infoList[2].strip();
            String password = infoList[3].strip();
            String role = infoList[4].strip();
//            String phone = infoList[5].strip();
//            String email = infoList[6].strip();
//            String dob = infoList[7].strip();
//            String address = infoList[9].strip();

            User user = User.builder()
                    .name(name)
                    .userCode(userCode)
                    .username(username)
                    .password(encoder.encode(password))
//                    .phone(phone)
//                    .email(email)
//                    .dob(DateUtils.convertDateFromString(dob, Constants.DateTimeFormatConstant.FORMAT_DATE))
//                    .address(address)
                    .role(role)
                    .createAt(new Timestamp(System.currentTimeMillis()))
                    .updateAt(new Timestamp(System.currentTimeMillis()))
                    .build();

            List<ExcelData.ErrorDetail> errorDetailList = user.validateInformationDetailError(new CopyOnWriteArrayList<>());
            if (usersRepository.existsAllByUserCode(userCode)) {
                errorDetailList.add(ExcelData.ErrorDetail.builder().columnIndex(1).errMsg("Mã sinh viên đã tồn tại trong hệ thống!").build());
            }

            if (usersRepository.existsByUsername(username)) {
                errorDetailList.add(ExcelData.ErrorDetail.builder().columnIndex(2).errMsg("Username đã tồn tại trong hệ thống!").build());
            }

            excelData.setUser(user);
            if(!errorDetailList.isEmpty()){
                excelData.setErrorDetailList(errorDetailList);
                excelData.setErrorDetailList(errorDetailList);
                excelData.setValid(false);
            }
            excelData.setRowIndex(row);

        }
        return excelData;
    }
}
