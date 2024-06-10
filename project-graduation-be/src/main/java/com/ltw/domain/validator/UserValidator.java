package com.ltw.domain.validator;

public class UserValidator {
    private final static String NAME_REGEX = "^[^<>{}\\\\\\\"|;:.,~!?@#$%^=&*\\\\\\\\)(]*$";
    public static boolean validateName(String name) {
        if (!name.matches(NAME_REGEX)) {
            return false;
        }
        return true;
    }
    public static boolean validateUsername(String username) {
        // Kiểm tra độ dài tối thiểu là 5 ký tự
        if (username.length() < 5) {
            return false;
        }

        // Kiểm tra username không chứa khoảng trắng
        if (username.contains(" ")) {
            return false;
        }

        // Kiểm tra username chỉ chứa các ký tự chữ cái (viết hoa hoặc thường),
        // số và dấu chấm, và không chứa các thuật ngữ chung hoặc phần mở rộng
        return username.matches("^[a-zA-Z0-9.]*$") && !username.contains(".com") && !username.contains(".net");
    }
}
