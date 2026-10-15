package com.branch.StudentTeacherCRUD.service;

import com.branch.StudentTeacherCRUD.dto.requestDto.StudentRequestDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.StudentResponseDto;

import java.util.List;

public interface StudentService {

    StudentResponseDto createStudent(StudentRequestDto studentRequestDto);

    List<StudentResponseDto> readStudents();

    StudentResponseDto updateStudent(StudentRequestDto studentRequestDto, Long id);

    void deleteStudent(Long id);

}
