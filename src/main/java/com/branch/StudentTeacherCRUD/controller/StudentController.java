package com.branch.StudentTeacherCRUD.controller;

import com.branch.StudentTeacherCRUD.dto.requestDto.StudentRequestDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.StudentResponseDto;
import com.branch.StudentTeacherCRUD.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/createStudent")
    public StudentResponseDto createStudent(@ModelAttribute @Valid StudentRequestDto student) throws IOException {
        return studentService.createStudent(student);
    }

    @GetMapping("/readStudent")
    public List<StudentResponseDto> readStudent(){
        return studentService.readStudents();
    }

    @PutMapping("/updateStudent")
    public StudentResponseDto updateStudent(@RequestParam Long id, @ModelAttribute @Valid StudentRequestDto student) throws IOException {
        return studentService.updateStudent(student,id);
    }

    @DeleteMapping("/deleteStudent")
    public void deleteStudent(@RequestParam Long id){
        studentService.deleteStudent(id);
    }
}
