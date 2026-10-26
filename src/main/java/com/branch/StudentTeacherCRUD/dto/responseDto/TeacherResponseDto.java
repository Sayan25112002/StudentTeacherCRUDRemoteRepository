package com.branch.StudentTeacherCRUD.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

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

    private String designation;

    private Set<StudentResponseDto> students;

}
