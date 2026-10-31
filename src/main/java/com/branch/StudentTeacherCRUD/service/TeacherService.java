package com.branch.StudentTeacherCRUD.service;

import com.branch.StudentTeacherCRUD.dto.requestDto.TeacherRequestDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.TeacherResponseDto;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public interface TeacherService {

    TeacherResponseDto createTeacher(TeacherRequestDto teacherRequestDto) throws IOException;

    List<TeacherResponseDto> readTeachers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, String searchBy, String search);

    TeacherResponseDto updateTeacher(TeacherRequestDto teacherRequestDto, Long id) throws IOException;

    void deleteTeacher(Long id);

    TeacherResponseDto assignTeacherToStudents(Long id, List<Long> ids);

}

