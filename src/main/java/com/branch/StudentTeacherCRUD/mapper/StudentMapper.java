package com.branch.StudentTeacherCRUD.mapper;

import com.branch.StudentTeacherCRUD.dto.requestDto.StudentRequestDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.StudentResponseDto;
import com.branch.StudentTeacherCRUD.entity.Student;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    Student toStudent(StudentRequestDto studentRequestDto);

    StudentResponseDto toStudentResponseDto(Student student);

    List<StudentResponseDto> toStudentResponseDtoList(List<Student> students);

}
