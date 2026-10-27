package com.branch.StudentTeacherCRUD.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResponseDto {

    private Long id;

    private String photoPath;

    private String firstName;

    private String lastName;

    private String email;

    private String contactNo;

    private String address;

}
