package com.ltw.constant;

public interface Constants {
    interface ErrorMessageNewsValidation{
        String NOT_FIND_NEW_BY_ID = "Không tìm thấy tin tức có ID là: ";
        String START_SIZE = "Số trang phải bắt đầu từ 0";
        String LIMIT_SIZE = "Số lượng bài viết trong một trang là từ 5 đến 50 bài viết";
    }

    interface ErrorMessageUserValidation{
        String NOT_FIND_USER_BY_ID = "Không tìm thấy người dùng có ID là: ";
        String START_SIZE = "Số trang phải bắt đầu từ 0";
        String LIMIT_SIZE = "Số lượng người trong một trang là từ 5 đến 50 người";
    }

    interface ErrorMessageNotificationValidation{
        String NOT_FIND_NOTIFICATION_BY_ID = "Không tìm thấy người dùng có ID là: ";
        String START_SIZE = "Số trang phải bắt đầu từ 0";
        String LIMIT_SIZE = "Số lượng bài thông trong một trang là từ 5 đến 50 bài";

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
