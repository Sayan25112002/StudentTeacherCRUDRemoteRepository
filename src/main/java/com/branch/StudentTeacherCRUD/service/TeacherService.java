package com.branch.StudentTeacherCRUD.service;

import com.branch.StudentTeacherCRUD.dto.requestDto.TeacherRequestDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.TeacherCreatedDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.TeacherDeletedDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.TeacherResponseDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.TeacherUpdatedDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TeacherService {

    TeacherCreatedDto createTeacher(TeacherRequestDto teacherRequestDto) throws IOException;

    List<TeacherResponseDto> readTeachers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, String searchBy, String search);

    TeacherUpdatedDto updateTeacher(TeacherRequestDto teacherRequestDto, Long id) throws IOException;

    TeacherDeletedDto deleteTeacher(Long id);

    TeacherResponseDto assignTeacherToStudents(Long id, List<Long> ids);

    String importTeachers(MultipartFile file) throws IOException;

    byte[] exportTeachers();

}

