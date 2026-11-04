package com.branch.StudentTeacherCRUD.service;

import com.branch.StudentTeacherCRUD.dto.requestDto.StudentRequestDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.StudentCreatedDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.StudentDeletedDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.StudentResponseDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.StudentUpdatedDto;

import java.io.IOException;
import java.util.List;

public interface StudentService {

    StudentCreatedDto createStudent(StudentRequestDto studentRequestDto) throws IOException;

    List<StudentResponseDto> readStudents(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, String searchBy, String search);

    StudentUpdatedDto updateStudent(StudentRequestDto studentRequestDto, Long id) throws IOException;

    StudentDeletedDto deleteStudent(Long id);

}

