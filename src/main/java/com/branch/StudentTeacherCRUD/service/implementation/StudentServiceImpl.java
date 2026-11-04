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
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

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

    @Override
    public String importStudents(MultipartFile file) {
        if(file.isEmpty()){
            throw new IllegalArgumentException("Please select a valid CSV file");
        }
        int imported=0;
        int skipped=0;
        Set<String> emails = new HashSet<>();
        Set<String> contactNos = new HashSet<>();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String headerLine = bufferedReader.readLine();
            if(headerLine == null){
                throw new IllegalArgumentException("CSV file is empty");
            }
            List<String> headers = parseCsvLine(headerLine.replace("\uFEFF",""));
            headers.replaceAll(header->header.trim().toLowerCase(Locale.ROOT));
            if(!new HashSet<>(headers).containsAll(List.of("firstName","lastName","email","contactNo","address"))){
                throw new IllegalArgumentException("CSV file contains invalid data");
            }
            String csvLine;
            while((csvLine = bufferedReader.readLine()) != null){
                if(csvLine.isBlank()){
                    continue;
                }
                List<String> values = parseCsvLine(csvLine);
                if(values.size() != headers.size()){
                    skipped++;
                    continue;
                }
                String firstName = values.get(headers.indexOf("firstName")).trim();
                String lastName = values.get(headers.indexOf("lastName")).trim();
                String email = values.get(headers.indexOf("email")).trim();
                String contactNo = values.get(headers.indexOf("contactNo")).trim();
                String address = values.get(headers.indexOf("address")).trim();
                if(firstName.isEmpty()
                        || lastName.isEmpty()
                        || email.isEmpty()
                        || contactNo.isEmpty()
                        || address.isEmpty()){
                    skipped++;
                    continue;
                }
                String normalizedEmail = email.toLowerCase(Locale.ROOT);
                if(studentRepository.existsByEmail(email)
                || studentRepository.existsByContactNo(contactNo)
                || !emails.add(normalizedEmail)
                || !contactNos.add(contactNo)){
                    skipped++;
                    continue;
                }
                Student student = Student.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .contactNo(contactNo)
                        .address(address)
                        .build();
                studentRepository.save(student);
                imported++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "Import Completed Successfully!!!";
    }

    private List<String> parseCsvLine(String replace) {
    }

    @Override
    public byte[] exportStudents() {
        return new byte[0];
    }
}
