package com.branch.StudentTeacherCRUD.dto.requestDto;

import com.branch.StudentTeacherCRUD.validations.Create;
import com.branch.StudentTeacherCRUD.validations.Update;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Valid
public class StudentRequestDto {

    @NotNull(message = "Photo must be provided", groups = {Update.class, Create.class})
    private MultipartFile photo;

    private String photoPath;

    @NotNull(message = "First Name must be provided", groups = {Update.class, Create.class})
    @NotBlank(message = "First Name must not be blank", groups = {Update.class, Create.class})
    private String firstName;

    @NotNull(message = "Last Name must be provided", groups = {Update.class, Create.class})
    @NotBlank(message = "Last Name must not be Blank", groups = {Update.class, Create.class})
    private String lastName;

    @NotNull(message = "Email must be provided", groups = {Update.class, Create.class})
    @NotBlank(message = "Email Must not be Blank", groups = {Update.class, Create.class})
    @Email(message = "It should be a proper email format", groups = {Update.class, Create.class})
    private String email;

    @NotNull(message = "Contact Number must be provided", groups = {Update.class, Create.class})
    @NotBlank(message = "Contact Number must not be Blank", groups = {Update.class, Create.class})
    @Size(min=10,max=10,message = "Contact Number must be of exactly 10 digits", groups = {Update.class, Create.class})
    private String contactNo;

    @NotNull(message = "Password must be provided", groups = {Create.class})
    @NotBlank(message = "Password must not be Blank", groups = {Create.class})
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$",
            message = "Password must be have at least one uppercase, one lowercase, one digit and one special symbol",
            groups = {Create.class}
    )
    @Size(min = 8 ,message = "Password must be at least 8 characters", groups = {Create.class})
    private String password;

    @NotNull(message = "Address must be provided", groups = {Update.class, Create.class})
    @NotBlank(message = "Address must not be Blank", groups = {Update.class, Create.class})
    private String address;

}
