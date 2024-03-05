package com.ltw.constant;

public interface Constants {
    interface ErrorMessageNewsValidation{
        String NOT_FIND_NEW_BY_ID = "Không tìm thấy tin tức có ID là: ";
        String ID_NOT_NULL = "ID không được để trống";
        String TITLE_NOT_BLANK = "Tiêu đề không được để trống";
        String DESCRIPTION_NOT_BLANK = "Mô tả không được để trống";
        String YEAR_NOT_NULL = "Năm không được để trống";
        String SUBJECT_NOT_BLANK = "Bộ môn không được để trống";
        String TITLE_SIZE = "Tiêu đề phải có ít nhất 6, nhiều nhất 255 kí tự!";
        String START_SIZE = "Số trang phải bắt đầu từ 0";
        String LIMIT_SIZE = "Số lượng bài viết trong một trang là từ 5 đến 50 bài viết";
    }

    interface ErrorMessageUserValidation{
        String NOT_FIND_USER_BY_ID = "Không tìm thấy người dùng có ID là: ";
        String ID_NOT_NULL = "ID không được để trống";
        String NAME_NOT_BLANK = "Tên không được để trống";
        String USERNAME_NOT_BLANK = "Tên đăng nhập(username) không được để trống";
        String PASSWORD_NOT_BLANK = "Mật khẩu không được để trống";
        String DOB_NOT_BLANK = "Ngày sinh không được để trống";
        String ADDRESS_NOT_BLANK = "Địa chỉ không được để trống";
        String EMAIL_NOT_BLANK = "Email không được để trống";
        String PHONE_NOT_BLANK = "Số điện thoại không được để trống";
        String SUBJECT_NOT_BLANK = "Bộ môn không được để trống";
        String ROLE_NOT_BLANK = "Vai trò không được để trống";
        String NAME_SIZE = "Tên người dùng phải có ít nhất 6, nhiều nhất 100 kí tự";
        String USERNAME_SIZE = "User name người dùng phải có ít nhất 6, nhiều nhất 100 kí tự";
        String PASSWORD_SIZE = "Mật khẩu phải có ít nhất 6, nhiều nhất 20 ký tự";
        String START_SIZE = "Số trang phải bắt đầu từ 0";
        String LIMIT_SIZE = "Số lượng người trong một trang là từ 5 đến 50 người";
    }

    interface ErrorMessageNotificationValidation{
        String NOT_FIND_NOTIFICATION_BY_ID = "Không tìm thấy người dùng có ID là: ";
        String ID_NOT_NULL = "ID không được để trống";
        String TITLE_NOT_BLANK = "Tiêu đề không được để trống";
        String DESCRIPTION_NOT_BLANK = "Giới thiệu không được để trống";
        String USER_NOT_NULL = "";
        String TITLE_SIZE = "Tiêu đề phải có ít nhất 6, nhiều nhất 255 kí tự!";
        String START_SIZE = "Số trang phải bắt đầu từ 0";
        String LIMIT_SIZE = "Số lượng bài thông báo trong một trang là từ 5 đến 50 bài";

    }

    interface ErrorMessageTopicValidation {
        String START_SIZE = "Số trang phải bắt đầu từ 0";
        String LIMIT_SIZE = "Số lượng đề tài trong một trang là từ 5 đến 50 bài";
    }

    interface ErrorMasterDataValidation {
        String START_SIZE = "Số trang phải bắt đầu từ 0";
        String LIMIT_SIZE = "Số lượng trong một trang là từ 5 đến 50";
        String NOT_FIND_MASTER_DATA_BY_ID = "Không tìm thấy  ID là: ";
    }


    interface SortType {
        String DESC = "DESC";
        String ASC = "ASC";
    }

    interface DateTimeFormatConstant {
        String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
        String DATE_FORMAT = "dd/MM/yyyy";
        String TIME_ZONE = "Asia/Ho_Chi_Minh";
    }


}
