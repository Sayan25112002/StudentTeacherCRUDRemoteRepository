package com.branch.StudentTeacherCRUD.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherResponseDto {

    private Long id;

    private String name;

    private String email;

    private String contactNo;

    private String address;

    private String department;

}
