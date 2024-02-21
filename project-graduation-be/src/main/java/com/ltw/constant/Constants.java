package com.ltw.constant;

public interface Constants {
    interface ErrorMessageNewsValidation{
        String NOT_FIND_NEW_BY_ID = "Không tìm thấy tin tức có ID là: ";
        String START_SIZE = "Số trang phải bắt đầu từ 0";
        String LIMIT_SIZE = "Số lượng bài viết trong một trang là từ 5 đến 50 bài viết";
    }

    interface SortType {
        String DESC = "DESC";
        String ASC = "ASC";
    }
}
