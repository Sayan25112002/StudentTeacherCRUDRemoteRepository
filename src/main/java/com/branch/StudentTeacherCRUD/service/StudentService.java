package com.branch.StudentTeacherCRUD.service;

import com.branch.StudentTeacherCRUD.dto.requestDto.StudentRequestDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.StudentResponseDto;

import java.io.IOException;
import java.util.List;

public interface StudentService {

    StudentResponseDto createStudent(StudentRequestDto studentRequestDto) throws IOException;

    List<StudentResponseDto> readStudents();

    StudentResponseDto updateStudent(StudentRequestDto studentRequestDto, Long id) throws IOException;

    void deleteStudent(Long id);

}
