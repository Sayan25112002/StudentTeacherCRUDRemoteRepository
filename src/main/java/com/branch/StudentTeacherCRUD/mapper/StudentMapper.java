package com.branch.StudentTeacherCRUD.mapper;

import com.branch.StudentTeacherCRUD.dto.requestDto.StudentRequestDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.StudentResponseDto;
import com.branch.StudentTeacherCRUD.entity.Student;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    Student toStudent(StudentRequestDto studentRequestDto);

    StudentResponseDto toStudentResponseDto(Student student);

    List<StudentResponseDto> toStudentResponseDtoList(List<Student> students);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStudent(StudentRequestDto studentRequestDto, @MappingTarget Student student);

}
