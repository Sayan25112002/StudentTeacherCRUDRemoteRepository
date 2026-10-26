package com.branch.StudentTeacherCRUD.service.implementation;

import com.branch.StudentTeacherCRUD.dto.requestDto.TeacherRequestDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.TeacherResponseDto;
import com.branch.StudentTeacherCRUD.entity.Student;
import com.branch.StudentTeacherCRUD.entity.Teacher;
import com.branch.StudentTeacherCRUD.mapper.TeacherMapper;
import com.branch.StudentTeacherCRUD.repository.StudentRepository;
import com.branch.StudentTeacherCRUD.repository.TeacherRepository;
import com.branch.StudentTeacherCRUD.service.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final StudentRepository studentRepository;

    @Override
    public TeacherResponseDto createTeacher(TeacherRequestDto teacherRequestDto) {
        Teacher teacher = teacherMapper.toTeacher(teacherRequestDto);
        Teacher savedTeacher = teacherRepository.save(teacher);
        return teacherMapper.toTeacherResponseDto(savedTeacher);
    }


    @Override
    public LinkedList<TeacherResponseDto> readTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        LinkedList<Teacher> teacherList = new LinkedList<>(teachers);
        return teacherMapper.toTeacherResponseDtoList(teacherList);
    }


    @Override
    public TeacherResponseDto updateTeacher(TeacherRequestDto teacherRequestDto, Long id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Teacher not found"));
        if(teacherRequestDto.getName()!=null){
            teacher.setName(teacherRequestDto.getName());
        }
        if(teacherRequestDto.getEmail()!=null){
            teacher.setEmail(teacherRequestDto.getEmail());
        }
        if(teacherRequestDto.getContactNo()!=null){
            teacher.setContactNo(teacherRequestDto.getContactNo());
        }
        if(teacherRequestDto.getAddress()!=null){
            teacher.setAddress(teacherRequestDto.getAddress());
        }
        if(teacherRequestDto.getDesignation()!=null){
            teacher.setDesignation(teacherRequestDto.getDesignation());
        }
        Teacher savedTeacher = teacherRepository.save(teacher);
        return teacherMapper.toTeacherResponseDto(savedTeacher);
    }

    @Override
    public void deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Teacher not found"));
        teacherRepository.delete(teacher);
    }

    @Override
    public TeacherResponseDto assignTeacherToStudents(Long id, List<Long> ids) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Teacher not found"));
        List<Student> studentsList = studentRepository.findAllById(ids);
        Set<Student> students = new HashSet<>(studentsList);
        teacher.setStudents(students);
        for(Student student : students){
            student.getTeachers().add(teacher);
        }
        Teacher savedTeacher = teacherRepository.save(teacher);
        return teacherMapper.toTeacherResponseDto(savedTeacher);
    }
}
