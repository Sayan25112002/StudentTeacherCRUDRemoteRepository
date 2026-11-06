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
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
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

    @Override
    public String importTeachers(MultipartFile file) throws IOException {
        if(file.isEmpty()){
            throw new IllegalStateException("Empty file provided");
        }
        int imported = 0;
        int skipped = 0;
        Set<String> emails = new HashSet<>();
        Set<String> contacts = new HashSet<>();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))){
            String headerLine = bufferedReader.readLine();
            if(headerLine==null){
                throw new IllegalStateException("CSV File is empty");
            }
            List<String> headers = parseCsvLine(headerLine.replace("\uFEFF",""));
            headers.replaceAll(String::trim);
            if(!new HashSet<>(headers).containsAll(List.of("NAME","EMAIL ADDRESS","CONTACT NUMBER","PASSWORD","ADDRESS","DESIGNATION"))){
               throw new IllegalStateException("CSV File contain invalid data");
            }
            String csvLine;
            while ((csvLine = bufferedReader.readLine()) != null){
                if(csvLine.isBlank()){
                    continue;
                }
                List<String> values = parseCsvLine(csvLine);
                if(values.size()!=headers.size()){
                    skipped++;
                    continue;
                }
                String name = values.get(headers.indexOf("NAME")).trim();
                String email = values.get(headers.indexOf("EMAIL ADDRESS")).trim();
                String contact = values.get(headers.indexOf("CONTACT NUMBER")).trim();
                String password = values.get(headers.indexOf("PASSWORD")).trim();
                String address = values.get(headers.indexOf("ADDRESS")).trim();
                String designation = values.get(headers.indexOf("DESIGNATION")).trim();
                if (name.isEmpty()
                        ||email.isEmpty()
                        ||contact.isEmpty()
                        ||password.isEmpty()
                        ||address.isEmpty()
                        ||designation.isEmpty()
                ){
                    skipped++;
                    continue;
                }
                String normalizedEmail = email.toLowerCase(Locale.ROOT);
                if(teacherRepository.existsByEmail(email)
                        ||teacherRepository.existsByContactNo(contact)
                        ||!emails.add(normalizedEmail)
                        ||!contacts.add(contact)
                ){
                    skipped++;
                    continue;
                }
                Teacher teacher = Teacher.builder()
                        .name(name)
                        .email(email)
                        .contactNo(contact)
                        .password(password)
                        .address(address)
                        .designation(designation)
                        .build();
                teacherRepository.save(teacher);
                imported++;
            }
        }catch(Exception e){
            throw new IllegalStateException(e);
        }
        return "Import Completed Successfully!!!";
    }

    @Override
    public byte[] exportTeachers() {
        List<Teacher> teachers = teacherRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        StringBuilder csv = new StringBuilder();
        csv.append("ID,NAME,EMAIL ADDRESS,CONTACT NUMBER,PASSWORD,ADDRESS,DESIGNATION\r\n");
        for(Teacher teacher : teachers){
            csv.append(csvValue(String.valueOf(teacher.getId()))).append(",");
            csv.append(csvValue(teacher.getName())).append(",");
            csv.append(csvValue(teacher.getEmail())).append(",");
            csv.append(csvValue(teacher.getContactNo())).append(",");
            csv.append(csvValue(teacher.getPassword())).append(",");
            csv.append(csvValue(teacher.getAddress())).append(",");
            csv.append(csvValue(teacher.getDesignation())).append("\r\n");
        }
        return ("\uFEFF"+csv).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public byte[] exportMappedStudentsToTeachers() {
        List<Teacher> teachers = teacherRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        StringBuilder csv = new StringBuilder();
        csv.append("TEACHER ID,TEACHER NAME,TEACHER EMAIL,DESIGNATION,").append("STUDENT ID,STUDENT NAME,STUDENT EMAIL\r\n");
        for(Teacher teacher : teachers){
            if(teacher.getStudents().isEmpty()){
                csv.append(csvValue(String.valueOf(teacher.getId()))).append(",");
                csv.append(csvValue(teacher.getName())).append(",");
                csv.append(csvValue(teacher.getEmail())).append(",");
                csv.append(csvValue(teacher.getDesignation())).append("\r\n");
            } else{
                for(Student student : teacher.getStudents()){
                    csv.append(csvValue(String.valueOf(teacher.getId()))).append(",");
                    csv.append(csvValue(teacher.getName())).append(",");
                    csv.append(csvValue(teacher.getEmail())).append(",");
                    csv.append(csvValue(teacher.getDesignation())).append(",");
                    csv.append(csvValue(String.valueOf(student.getId()))).append(",");
                    csv.append(csvValue(student.getFirstName()+" "+student.getLastName())).append(",");
                    csv.append(csvValue(student.getEmail())).append("\r\n");
                    }
            }
        }
        return ("\uFEFF"+csv).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public byte[] exportMappedStudentsToOneTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Teacher with id " + id + " not found!"));
        StringBuilder csv = new StringBuilder();
        csv.append("TEACHER ID,TEACHER NAME,DESIGNATION")
                .append("STUDENT ID,STUDENT NAME,STUDENT EMAIL\r\n");
        for(Student student : teacher.getStudents()){
            csv.append(csvValue(String.valueOf(teacher.getId()))).append(",");
            csv.append(csvValue(teacher.getName())).append(",");
            csv.append(csvValue(teacher.getDesignation())).append(",");
            csv.append(csvValue(String.valueOf(student.getId()))).append(",");
            csv.append(csvValue(student.getFirstName()+" "+student.getLastName())).append(",");
            csv.append(csvValue(student.getEmail())).append("\r\n");
        }
        return ("\uFEFF"+csv).getBytes(StandardCharsets.UTF_8);
    }

    private List<String> parseCsvLine(String s) {
        List<String> values = new ArrayList<>();
        StringBuilder value = new StringBuilder();
        boolean insideQuote = false;
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(c=='"'){
                if(insideQuote && i+1<s.length() && s.charAt(i+1)=='"'){
                    value.append('"');
                    i++;
                }else {
                    insideQuote = !insideQuote;
                }
            }else if(c==',' && !insideQuote){
                values.add(value.toString());
                value.setLength(0);
            }else {
                value.append(c);
            }
        }
        values.add(value.toString());
        return values;
    }

    private String csvValue(String s) {
        return "\""+(s==null?"":s.replace("\"", "\"\""))+"\"";
    }
}
