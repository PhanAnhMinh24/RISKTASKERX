package com.wbsrisktaskerx.wbsrisktaskerx.pojo.request;

import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordRequest {

    @NotBlank
    @Email
    String email;

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = ".*[A-Z].*")
    @Pattern(regexp = ".*[0-9].*")
    @Pattern(regexp = ".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*")
    @Pattern(regexp = "\\S+")
    String newPassword;

    @NotBlank
    String reNewPassword;

    public void validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ResetPasswordRequest>> violations = validator.validate(this);

        if (!violations.isEmpty()) {
            ConstraintViolation<ResetPasswordRequest> violation = violations.iterator().next();
            String errorCode = violation.getMessage();
            throw new AppException(ErrorCode.valueOf(errorCode));
        }

        if (!newPassword.equals(reNewPassword)) {
            throw new AppException(ErrorCode.PASSWORDS_NOT_MATCH);
        }
    }
}
