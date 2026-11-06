package com.branch.StudentTeacherCRUD.controller;

import com.branch.StudentTeacherCRUD.dto.requestDto.TeacherRequestDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.TeacherCreatedDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.TeacherDeletedDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.TeacherResponseDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.TeacherUpdatedDto;
import com.branch.StudentTeacherCRUD.service.TeacherService;
import com.branch.StudentTeacherCRUD.validations.Create;
import com.branch.StudentTeacherCRUD.validations.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/createTeacher")
    public TeacherCreatedDto createTeacher(@ModelAttribute @Validated(Create.class) TeacherRequestDto teacher) throws IOException {
        return teacherService.createTeacher(teacher);
    }

    @GetMapping("/readTeacher")
    public List<TeacherResponseDto> readTeacher(@RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                                      @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                      @RequestParam(required = false, defaultValue = "ASC") String sortDir,
                                                      @RequestParam(required = false) String searchBy,
                                                      @RequestParam(required = false) String search) {
        return teacherService.readTeachers(pageNumber, pageSize, sortBy, sortDir, searchBy, search);
    }

    @PutMapping("/updateTeacher")
    public TeacherUpdatedDto updateTeacher(@ModelAttribute @Validated(Update.class) TeacherRequestDto teacher, @RequestParam Long id) throws IOException {
        return teacherService.updateTeacher(teacher, id);
    }

    @DeleteMapping("/deleteTeacher")
    public TeacherDeletedDto deleteTeacher(@RequestParam Long id) {
        return teacherService.deleteTeacher(id);
    }

    @PostMapping("/mapStudentsToTeacher")
    public TeacherResponseDto mapTeacherToStudents(@RequestParam Long id, @RequestParam List<Long> ids) {
        return teacherService.assignTeacherToStudents(id, ids);
    }

    @PostMapping("/importTeachers")
    public String importTeachers(@RequestParam("file") MultipartFile file) throws IOException {
        return teacherService.importTeachers(file);
    }

    @GetMapping("/exportTeachers")
    public ResponseEntity<byte[]> exportTeachers() {
        byte[] teacherFile = teacherService.exportTeachers();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"List of Teachers.csv\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(teacherFile);
    }

    @GetMapping("/exportMappedStudentsToTeacher")
    public ResponseEntity<byte[]> exportMappedStudentsToTeacher() {
        byte[] mappedStudentFile = teacherService.exportMappedStudentsToTeachers();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"List of Mapped Students To Teacher.csv\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(mappedStudentFile);
    }

    @GetMapping("/exportMappedStudentsToOneTeacher")
    public ResponseEntity<byte[]> exportMappedStudentsToOneTeacher(@RequestParam Long id) {
        byte[] mappedStudentFile = teacherService.exportMappedStudentsToOneTeacher(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"List of Mapped Students To One Teacher\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(mappedStudentFile);
    }
}
