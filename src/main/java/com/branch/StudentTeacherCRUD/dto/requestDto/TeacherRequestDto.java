package com.branch.StudentTeacherCRUD.dto.requestDto;

import jakarta.persistence.Column;
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
public class TeacherRequestDto {

    @NotNull(message = "Photo must be provided")
    private MultipartFile photo;

    private String photoPath;

    @NotNull(message = "Name must be provided")
    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotNull(message = "Email must be provided")
    @NotBlank(message = "Email Must not be Blank")
    @Email(message = "It should be a proper email format")
    @Column(unique = true)
    private String email;

    @NotNull(message = "Contact Number must be provided")
    @NotBlank(message = "Contact Number must not be Blank")
    @Column(unique = true)
    @Size(min=10,max=10,message = "Contact Number must be of exactly 10 digits")
    private String contactNo;

    @NotNull(message = "Password must be provided")
    @NotBlank(message = "Password must not be Blank")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$",
            message = "Password must be have at least one uppercase, one lowercase, one digit and one special symbol"
    )
    @Size(min = 8 ,message = "Password must be at least 8 characters")
    private String password;

    @NotNull(message = "Address must be provided")
    @NotBlank(message = "Address must not be Blank")
    private String address;

    @NotNull(message = "Designation must be provided")
    @NotBlank(message = "Designation must not be Blank")
    private String designation;

}
