package com.branch.StudentTeacherCRUD.dto.requestDto;

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

    private MultipartFile photo;

    private String photoPath;

    private String name;

    private String email;

    private String contactNo;

    private String password;

    private String address;

    private String designation;

}
