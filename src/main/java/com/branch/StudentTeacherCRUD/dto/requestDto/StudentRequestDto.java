package com.branch.StudentTeacherCRUD.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentRequestDto {

    private String firstName;

    private String lastName;

    private String email;

    private String contactNo;

    private String password;

    private String address;

}
