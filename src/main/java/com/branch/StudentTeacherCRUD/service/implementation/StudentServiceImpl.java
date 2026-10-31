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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    public List<StudentResponseDto> readStudents(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, String searchBy, String search) {
        List<Student> students;
        Sort sort;
        if(sortDir.equalsIgnoreCase("ASC")){
            sort = Sort.by(sortBy).ascending();
        }else {
            sort = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, sort);
        if(search == null){
            students = studentRepository.findAll(pageable).getContent();
        }else if(searchBy.equalsIgnoreCase("firstName")){
            students = studentRepository.findByFirstName(search,pageable).getContent();
        }else if(searchBy.equalsIgnoreCase("lastName")) {
            students = studentRepository.findByLastName(search, pageable).getContent();
        }else if(searchBy.equalsIgnoreCase("email")) {
            students = studentRepository.findByEmail(search, pageable).getContent();
        }else if(searchBy.equalsIgnoreCase("contactNo")) {
            students = studentRepository.findByContactNo(search, pageable).getContent();
        }else if(searchBy.equalsIgnoreCase("address")) {
            students = studentRepository.findByAddress(search, pageable).getContent();
        }else {
            throw new IllegalArgumentException("Invalid Search Arguments");
        }
        return studentMapper.toStudentResponseDtoList(students);
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
