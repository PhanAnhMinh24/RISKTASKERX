package com.wbsrisktaskerx.wbsrisktaskerx.pojo.request;

import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordRequest {
    String email;
    String newPassword;
    String reNewPassword;

    public void validate() {
        if (newPassword == null || reNewPassword == null) {
            throw new AppException(ErrorCode.PASSWORD_REQUIRED);
        }
        if (!newPassword.equals(reNewPassword)) {
            throw new AppException(ErrorCode.PASSWORDS_NOT_MATCH);
        }
        if (newPassword.length() < 8) {
            throw new AppException(ErrorCode.PASSWORD_TOO_SHORT);
        }
        if (!newPassword.matches(".*[A-Z].*")) {
            throw new AppException(ErrorCode.PASSWORD_NO_UPPERCASE);
        }
        if (!newPassword.matches(".*[0-9].*")) {
            throw new AppException(ErrorCode.PASSWORD_NO_NUMBER);
        }
        if (!newPassword.matches(".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*")) {
            throw new AppException(ErrorCode.PASSWORD_NO_SPECIAL_CHAR);
        }
        if (newPassword.contains(" ")) {
            throw new AppException(ErrorCode.PASSWORD_CONTAINS_SPACE);
        }
    }

}
