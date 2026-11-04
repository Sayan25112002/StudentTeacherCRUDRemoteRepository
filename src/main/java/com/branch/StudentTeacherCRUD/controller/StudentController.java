package com.branch.StudentTeacherCRUD.controller;

import com.branch.StudentTeacherCRUD.dto.requestDto.StudentRequestDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.StudentCreatedDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.StudentDeletedDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.StudentResponseDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.StudentUpdatedDto;
import com.branch.StudentTeacherCRUD.service.StudentService;
import com.branch.StudentTeacherCRUD.validations.Create;
import com.branch.StudentTeacherCRUD.validations.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/createStudent")
    public StudentCreatedDto createStudent(@ModelAttribute @Validated(Create.class) StudentRequestDto student) throws IOException {
        return studentService.createStudent(student);
    }

    @GetMapping("/readStudent")
    public List<StudentResponseDto> readStudent(@RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                                                @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                                @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                @RequestParam(required = false, defaultValue = "ASC") String sortDir,
                                                @RequestParam(required = false) String searchBy,
                                                @RequestParam(required = false) String search) {
        return studentService.readStudents(pageNumber, pageSize, sortBy, sortDir, searchBy, search);
    }

    @PutMapping("/updateStudent")
    public StudentUpdatedDto updateStudent(@RequestParam Long id, @ModelAttribute @Validated(Update.class) StudentRequestDto student) throws IOException {
        return studentService.updateStudent(student,id);
    }

    @DeleteMapping("/deleteStudent")
    public StudentDeletedDto deleteStudent(@RequestParam Long id){
        return studentService.deleteStudent(id);
    }
}
