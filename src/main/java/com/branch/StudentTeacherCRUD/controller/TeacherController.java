package com.branch.StudentTeacherCRUD.controller;

import com.branch.StudentTeacherCRUD.dto.requestDto.TeacherRequestDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.TeacherResponseDto;
import com.branch.StudentTeacherCRUD.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/createTeacher")
    public TeacherResponseDto createTeacher(@ModelAttribute TeacherRequestDto teacher) throws IOException {
        return teacherService.createTeacher(teacher);
    }

    @GetMapping("/readTeacher")
    public LinkedList<TeacherResponseDto> readTeacher() {
        return teacherService.readTeachers();
    }

    @PutMapping("/updateTeacher")
    public TeacherResponseDto updateTeacher(@ModelAttribute TeacherRequestDto teacher, @RequestParam Long id) throws IOException {
        return teacherService.updateTeacher(teacher, id);
    }

    @DeleteMapping("/deleteTeacher")
    public void deleteTeacher(@RequestParam Long id) {
        teacherService.deleteTeacher(id);
    }

    @PostMapping("/mapStudentsToTeacher")
    public TeacherResponseDto mapTeacherToStudents(@RequestParam Long id, @RequestParam List<Long> ids) {
        return teacherService.assignTeacherToStudents(id, ids);
    }
}
