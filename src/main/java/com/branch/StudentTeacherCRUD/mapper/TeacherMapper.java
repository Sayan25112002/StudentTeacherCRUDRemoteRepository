package com.branch.StudentTeacherCRUD.mapper;

import com.branch.StudentTeacherCRUD.dto.requestDto.TeacherRequestDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.TeacherResponseDto;
import com.branch.StudentTeacherCRUD.entity.Teacher;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    Teacher toTeacher(TeacherRequestDto teacherRequestDto);

    TeacherResponseDto toTeacherResponseDto(Teacher teacher);

    List<TeacherResponseDto> toTeacherResponseDtoList(List<Teacher> teachers);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTeacher(TeacherRequestDto teacherRequestDto, @MappingTarget Teacher teacher);

}
