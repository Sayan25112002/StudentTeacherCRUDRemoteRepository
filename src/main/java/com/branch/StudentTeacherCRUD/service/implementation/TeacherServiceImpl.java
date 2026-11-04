package com.branch.StudentTeacherCRUD.service.implementation;

import com.branch.StudentTeacherCRUD.dto.requestDto.TeacherRequestDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.TeacherCreatedDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.TeacherDeletedDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.TeacherResponseDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.TeacherUpdatedDto;
import com.branch.StudentTeacherCRUD.entity.Student;
import com.branch.StudentTeacherCRUD.entity.Teacher;
import com.branch.StudentTeacherCRUD.exception.ContactNumberAlreadyExistsException;
import com.branch.StudentTeacherCRUD.exception.EmailAlreadyExistsException;
import com.branch.StudentTeacherCRUD.mapper.TeacherMapper;
import com.branch.StudentTeacherCRUD.repository.StudentRepository;
import com.branch.StudentTeacherCRUD.repository.TeacherRepository;
import com.branch.StudentTeacherCRUD.service.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static com.branch.StudentTeacherCRUD.service.FileUploadService.saveFile;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final StudentRepository studentRepository;

    @Override
    public TeacherCreatedDto createTeacher(TeacherRequestDto teacherRequestDto) throws IOException {
        Teacher teacher = teacherMapper.toTeacher(teacherRequestDto);
        teacher.setPhotoPath(saveFile(teacherRequestDto.getPhoto()));
        if(teacherRepository.existsByEmail(teacherRequestDto.getEmail())) {
            throw new EmailAlreadyExistsException("ERROR! DUPLICATE EMAIL. EMAIL ALREADY EXISTS.");
        }
        if(teacherRepository.existsByContactNo(teacherRequestDto.getContactNo())) {
            throw new ContactNumberAlreadyExistsException("ERROR! DUPLICATE CONTACT NUMBER. CONTACT NUMBER ALREADY EXISTS.");
        }
        teacherRepository.save(teacher);
        return TeacherCreatedDto.builder()
                .message("TEACHER CREATED SUCCESSFULLY !!!")
                .build();
    }

    @Override
    public List<TeacherResponseDto> readTeachers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, String searchBy, String search) {
        List<Teacher> teachers;
        Sort sort;
        if(sortDir.equalsIgnoreCase("ASC")) {
            sort = Sort.by(sortBy).ascending();
        }else {
            sort = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, sort);
        if(search==null){
            teachers = teacherRepository.findAll(pageable).getContent();
        }else {
            teachers = switch (searchBy){
                case "name" -> teacherRepository.findByName(search,pageable).getContent();
                case "email" -> teacherRepository.findByEmail(search,pageable).getContent();
                case "contactNo"  -> teacherRepository.findByContactNo(search,pageable).getContent();
                case "address"  -> teacherRepository.findByAddress(search,pageable).getContent();
                case "designation"  -> teacherRepository.findByDesignation(search,pageable).getContent();
                default -> throw new IllegalStateException("Unexpected value: " + searchBy.toLowerCase());
            };
        }
        return teacherMapper.toTeacherResponseDtoList(teachers);
    }

    @Override
    public TeacherUpdatedDto updateTeacher(TeacherRequestDto teacherRequestDto, Long id) throws IOException {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Teacher not found"));
        if(teacherRequestDto.getPhoto()!=null){
            teacher.setPhotoPath(saveFile(teacherRequestDto.getPhoto()));
        }
        teacherMapper.updateTeacher(teacherRequestDto,teacher);
        if(teacherRepository.existsByEmailAndIdNot(teacherRequestDto.getEmail(),id)) {
            throw new EmailAlreadyExistsException("ERROR! DUPLICATE EMAIL. EMAIL ALREADY EXISTS.");
        }
        if(teacherRepository.existsByContactNoAndIdNot(teacherRequestDto.getContactNo(),id)) {
            throw new ContactNumberAlreadyExistsException("ERROR! DUPLICATE CONTACT NUMBER. CONTACT NUMBER ALREADY EXISTS.");
        }
        teacherRepository.save(teacher);
        return TeacherUpdatedDto.builder()
                .message("TEACHER UPDATED SUCCESSFULLY !!!")
                .build();
    }

    @Override
    public TeacherDeletedDto deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Teacher not found"));
        teacherRepository.delete(teacher);
        return TeacherDeletedDto.builder()
                .message("TEACHER DELETED SUCCESSFULLY !!!")
                .build();
    }

    @Override
    public TeacherResponseDto assignTeacherToStudents(Long id, List<Long> ids) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Teacher not found"));
        List<Student> studentsList = studentRepository.findAllById(ids);
        Set<Student> students = new HashSet<>(studentsList);
        for(Student student : teacher.getStudents()){
            student.getTeachers().remove(teacher);
        }
        teacher.setStudents(students);
        for(Student student : students){
            student.getTeachers().add(teacher);
        }
        Teacher savedTeacher = teacherRepository.save(teacher);
        return teacherMapper.toTeacherResponseDto(savedTeacher);
    }
}
