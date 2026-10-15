package com.branch.StudentTeacherCRUD.service;

import com.branch.StudentTeacherCRUD.dto.requestDto.TeacherRequestDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.TeacherResponseDto;

import java.util.LinkedList;
import java.util.List;

public interface TeacherService {

    TeacherResponseDto createTeacher(TeacherRequestDto teacherRequestDto);

    LinkedList<TeacherResponseDto> readTeachers();

    TeacherResponseDto updateTeacher(TeacherRequestDto teacherRequestDto, Long id);

    void deleteTeacher(Long id);

}
