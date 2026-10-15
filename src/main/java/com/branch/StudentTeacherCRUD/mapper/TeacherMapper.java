package com.branch.StudentTeacherCRUD.mapper;

import com.branch.StudentTeacherCRUD.dto.requestDto.TeacherRequestDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.TeacherResponseDto;
import com.branch.StudentTeacherCRUD.entity.Teacher;
import org.mapstruct.Mapper;

import java.util.LinkedList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    Teacher toTeacher(TeacherRequestDto teacherRequestDto);

    TeacherResponseDto toTeacherResponseDto(Teacher teacher);

    LinkedList<TeacherResponseDto> toTeacherResponseDtoList(List<Teacher> teachers);

}
