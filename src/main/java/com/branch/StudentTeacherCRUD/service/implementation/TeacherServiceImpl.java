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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final StudentRepository studentRepository;

    @Override
    public TeacherResponseDto createTeacher(TeacherRequestDto teacherRequestDto, List<Long> ids) {
        Teacher teacher = teacherMapper.toTeacher(teacherRequestDto);
        List<Student> students = studentRepository.findAllById(ids);
        teacher.setStudents(students);
        for(Student student : students){
            student.getTeachers().add(teacher);
        }
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
    public TeacherResponseDto updateTeacher(TeacherRequestDto teacherRequestDto, Long id, List<Long> ids) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Teacher not found"));
        teacher.setName(teacherRequestDto.getName());
        teacher.setEmail(teacherRequestDto.getEmail());
        teacher.setContactNo(teacherRequestDto.getContactNo());
        teacher.setAddress(teacherRequestDto.getAddress());
        teacher.setDesignation(teacherRequestDto.getDesignation());
        List<Student> oldStudents = new ArrayList<>(teacher.getStudents());
        for(Student student : oldStudents){
            student.getTeachers().remove(teacher);
        }
        List<Student> newStudents = studentRepository.findAllById(ids);
        teacher.setStudents(newStudents);
        for(Student student : newStudents){
            if(!student.getTeachers().contains(teacher)){
                student.getTeachers().add(teacher);
            }
        }
        Teacher savedTeacher = teacherRepository.save(teacher);
        return teacherMapper.toTeacherResponseDto(savedTeacher);
    }

    @Override
    public void deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Teacher not found"));
        List<Student> students = new ArrayList<>(teacher.getStudents());
        for(Student student : students){
            student.getTeachers().remove(teacher);
        }
        teacherRepository.delete(teacher);
    }
}
