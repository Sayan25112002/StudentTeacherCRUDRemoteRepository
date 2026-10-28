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
public class TeacherRequestDto {

    @NotNull(message = "Photo must be provided", groups = {Create.class, Update.class})
    private MultipartFile photo;

    private String photoPath;

    @NotNull(message = "Name must be provided", groups = {Create.class, Update.class})
    @NotBlank(message = "Name must not be blank", groups = {Create.class, Update.class})
    private String name;

    @NotNull(message = "Email must be provided", groups = {Create.class, Update.class})
    @NotBlank(message = "Email Must not be Blank", groups = {Create.class, Update.class})
    @Email(message = "It should be a proper email format", groups = {Create.class, Update.class})
    private String email;

    @NotNull(message = "Contact Number must be provided", groups = {Create.class, Update.class})
    @NotBlank(message = "Contact Number must not be Blank", groups = {Create.class, Update.class})
    @Size(min=10,max=10,message = "Contact Number must be of exactly 10 digits", groups = {Create.class, Update.class})
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

    @NotNull(message = "Address must be provided", groups = {Create.class, Update.class})
    @NotBlank(message = "Address must not be Blank", groups = {Create.class, Update.class})
    private String address;

    @NotNull(message = "Designation must be provided", groups = {Create.class, Update.class})
    @NotBlank(message = "Designation must not be Blank", groups = {Create.class, Update.class})
    private String designation;

}
