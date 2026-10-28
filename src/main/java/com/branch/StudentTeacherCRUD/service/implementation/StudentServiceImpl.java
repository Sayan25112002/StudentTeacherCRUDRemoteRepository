package com.branch.StudentTeacherCRUD.service.implementation;

import com.branch.StudentTeacherCRUD.dto.requestDto.StudentRequestDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.StudentResponseDto;
import com.branch.StudentTeacherCRUD.entity.Student;
import com.branch.StudentTeacherCRUD.entity.Teacher;
import com.branch.StudentTeacherCRUD.mapper.StudentMapper;
import com.branch.StudentTeacherCRUD.repository.StudentRepository;
import com.branch.StudentTeacherCRUD.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import static com.branch.StudentTeacherCRUD.service.FileUploadService.saveFile;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public StudentResponseDto createStudent(StudentRequestDto studentRequestDto) throws IOException {
        Student student = studentMapper.toStudent(studentRequestDto);
        student.setPhotoPath(saveFile(studentRequestDto.getPhoto()));
        Student savedStudent = studentRepository.save(student);
        return studentMapper.toStudentResponseDto(savedStudent);
    }

    @Override
    public List<StudentResponseDto> readStudents(Pageable pageable) {
        return studentRepository.findAll(pageable).getContent();
    }

    @Override
    public StudentResponseDto updateStudent(StudentRequestDto studentRequestDto, Long id) throws IOException {
        Student student = studentRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Student not found"));
        if(studentRequestDto.getPhoto()!=null){
            student.setPhotoPath(saveFile(studentRequestDto.getPhoto()));
        }
        if(studentRequestDto.getFirstName() != null) {
            student.setFirstName(studentRequestDto.getFirstName());
        }
        if(studentRequestDto.getLastName() != null) {
            student.setLastName(studentRequestDto.getLastName());
        }
        if(studentRequestDto.getEmail() != null) {
            student.setEmail(studentRequestDto.getEmail());
        }
        if(studentRequestDto.getContactNo() != null) {
            student.setContactNo(studentRequestDto.getContactNo());
        }
        if(studentRequestDto.getAddress() != null) {
            student.setAddress(studentRequestDto.getAddress());
        }
        return studentMapper.toStudentResponseDto(studentRepository.save(student));
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Student not found"));
        Set<Teacher> teachers = student.getTeachers();
        for (Teacher teacher : teachers) {
            teacher.getStudents().remove(student);
        }
        studentRepository.delete(student);
    }
}
