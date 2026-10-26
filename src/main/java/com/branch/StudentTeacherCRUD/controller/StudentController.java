package com.branch.StudentTeacherCRUD.controller;

import com.branch.StudentTeacherCRUD.dto.requestDto.StudentRequestDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.StudentResponseDto;
import com.branch.StudentTeacherCRUD.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/createStudent")
    public StudentResponseDto createStudent(@RequestBody StudentRequestDto student){
        return studentService.createStudent(student);
    }

    @GetMapping("/readStudent")
    public List<StudentResponseDto> readStudent(){
        return studentService.readStudents();
    }

    @PutMapping("/updateStudent")
    public StudentResponseDto updateStudent(@RequestParam Long id, @RequestBody StudentRequestDto student){
        return studentService.updateStudent(student,id);
    }

    @DeleteMapping("/deleteStudent")
    public void deleteStudent(@RequestParam Long id){
        studentService.deleteStudent(id);
    }
}
