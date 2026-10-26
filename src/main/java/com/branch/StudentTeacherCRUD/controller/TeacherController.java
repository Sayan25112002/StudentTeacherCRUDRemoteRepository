package com.branch.StudentTeacherCRUD.controller;

import com.branch.StudentTeacherCRUD.dto.requestDto.TeacherRequestDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.TeacherResponseDto;
import com.branch.StudentTeacherCRUD.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@RestController
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/createTeacher")
    public TeacherResponseDto createTeacher(@RequestBody TeacherRequestDto teacher) {
        return teacherService.createTeacher(teacher);
    }

    @GetMapping("/readTeacher")
    public LinkedList<TeacherResponseDto> readTeacher() {
        return teacherService.readTeachers();
    }

    @PutMapping("/updateTeacher/{id}")
    public TeacherResponseDto updateTeacher(@RequestBody TeacherRequestDto teacher, @PathVariable Long id) {
        return teacherService.updateTeacher(teacher, id);
    }

    @DeleteMapping("/deleteTeacher/{id}")
    public void deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
    }
}
