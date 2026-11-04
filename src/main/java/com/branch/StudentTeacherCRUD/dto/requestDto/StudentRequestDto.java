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
    @Email(message = "INVALID EMAIL! EMAIL ADDRESS MUST BE PROPER", groups = {Update.class, Create.class})
    private String email;

    @NotNull(message = "Contact Number must be provided", groups = {Update.class, Create.class})
    @NotBlank(message = "Contact Number must not be Blank", groups = {Update.class, Create.class})
    @Size(min=10,max=10,message = "INVALID CONTACT NUMBER! CONTACT NUMBER MUST BE OF EXACTLY 10 CHARACTERS", groups = {Update.class, Create.class})
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

    @NotNull(message = "Address must be provided", groups = {Update.class, Create.class})
    @NotBlank(message = "Address must not be Blank", groups = {Update.class, Create.class})
    private String address;

}
