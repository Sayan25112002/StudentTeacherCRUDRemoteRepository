package com.branch.StudentTeacherCRUD.service.implementation;

import com.branch.StudentTeacherCRUD.dto.requestDto.StudentRequestDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.StudentCreatedDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.StudentDeletedDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.StudentResponseDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.StudentUpdatedDto;
import com.branch.StudentTeacherCRUD.entity.Student;
import com.branch.StudentTeacherCRUD.entity.Teacher;
import com.branch.StudentTeacherCRUD.exception.ContactNumberAlreadyExistsException;
import com.branch.StudentTeacherCRUD.exception.EmailAlreadyExistsException;
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
    public StudentCreatedDto createStudent(StudentRequestDto studentRequestDto) throws IOException {
        Student student = studentMapper.toStudent(studentRequestDto);
        student.setPhotoPath(saveFile(studentRequestDto.getPhoto()));
        if(studentRepository.existsByEmail(studentRequestDto.getEmail())){
            throw new EmailAlreadyExistsException("ERROR! DUPLICATE EMAIL. EMAIL ALREADY EXISTS.");
        }
        if(studentRepository.existsByContactNo(studentRequestDto.getContactNo())){
            throw new ContactNumberAlreadyExistsException("ERROR! DUPLICATE CONTACT NUMBER. CONTACT NUMBER ALREADY EXISTS.");
        }
        studentRepository.save(student);
        return StudentCreatedDto.builder()
                .message("STUDENT CREATED SUCCESSFULLY!!!")
                .build();
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
        }else {
            students = switch(searchBy){
                case "firstName" -> studentRepository.findByFirstName(search,pageable).getContent();
                case "lastName" -> studentRepository.findByLastName(search,pageable).getContent();
                case "email" -> studentRepository.findByEmail(search,pageable).getContent();
                case "contactNo" -> studentRepository.findByContactNo(search,pageable).getContent();
                case "address" -> studentRepository.findByAddress(search,pageable).getContent();
                default -> throw new IllegalStateException("Unexpected value: " + searchBy);
            };
        }
        return studentMapper.toStudentResponseDtoList(students);
    }

    @Override
    public StudentUpdatedDto updateStudent(StudentRequestDto studentRequestDto, Long id) throws IOException {
        Student student = studentRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Student not found"));
        if(studentRequestDto.getPhoto() != null){
            student.setPhotoPath(saveFile(studentRequestDto.getPhoto()));
        }
        studentMapper.updateStudent(studentRequestDto,student);
        if(studentRepository.existsByEmailAndIdNot(studentRequestDto.getEmail(),id)){
            throw new EmailAlreadyExistsException("ERROR! DUPLICATE EMAIL. EMAIL ALREADY EXISTS.");
        }
        if(studentRepository.existsByContactNoAndIdNot(studentRequestDto.getContactNo(),id)){
            throw new ContactNumberAlreadyExistsException("ERROR! DUPLICATE CONTACT NUMBER. CONTACT NUMBER ALREADY EXISTS.");
        }
        studentRepository.save(student);
        return StudentUpdatedDto.builder()
                .message("STUDENT UPDATED SUCCESSFULLY!!!")
                .build();
    }

    @Override
    public StudentDeletedDto deleteStudent(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Student not found"));
        Set<Teacher> teachers = student.getTeachers();
        for (Teacher teacher : teachers) {
            teacher.getStudents().remove(student);
        }
        studentRepository.delete(student);
        return StudentDeletedDto.builder()
                .message("STUDENT DELETED SUCCESSFULLY!!!")
                .build();
    }
}
