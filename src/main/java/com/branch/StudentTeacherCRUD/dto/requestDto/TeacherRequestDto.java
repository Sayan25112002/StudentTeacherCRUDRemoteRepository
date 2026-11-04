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
    @Email(message = "INVALID EMAIL ADDRESS! EMAIL ADDRESS MUST BE PROPER", groups = {Create.class, Update.class})
    private String email;

    @NotNull(message = "Contact Number must be provided", groups = {Create.class, Update.class})
    @NotBlank(message = "Contact Number must not be Blank", groups = {Create.class, Update.class})
    @Size(min=10,max=10,message = "INVALID CONTACT NUMBER! CONTACT NUMBER MUST BE EXACTLY OF 10 CHARACTERS", groups = {Create.class, Update.class})
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "INVALID CONTACT NUMBER! CONTACT NUMBER MUST BE OF ONLY OF DIGITS",
            groups = {Update.class, Create.class}
    )
    private String contactNo;

    @NotNull(message = "Password must be provided", groups = {Create.class})
    @NotBlank(message = "Password must not be Blank", groups = {Create.class})
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$",
            message = "INVALID PASSWORD! PASSWORD MUST HAVE AT LEAST ONE UPPERCASE CASE LETTER, ONE LOWER CASE LETTER, ONE DIGIT AND ONE SPECIAL CHARACTER",
            groups = {Create.class}
    )
    @Size(min = 8 ,message = "INVALID PASSWORD! PASSWORD MUST HAVE AT LEAST EIGHT CHARACTERS", groups = {Create.class})
    private String password;

    @NotNull(message = "Address must be provided", groups = {Create.class, Update.class})
    @NotBlank(message = "Address must not be Blank", groups = {Create.class, Update.class})
    private String address;

    @NotNull(message = "Designation must be provided", groups = {Create.class, Update.class})
    @NotBlank(message = "Designation must not be Blank", groups = {Create.class, Update.class})
    private String designation;

}
