package com.branch.StudentTeacherCRUD.controller;

import com.branch.StudentTeacherCRUD.dto.requestDto.TeacherRequestDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.TeacherResponseDto;
import com.branch.StudentTeacherCRUD.entity.Teacher;
import com.branch.StudentTeacherCRUD.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/createTeacher")
    public TeacherResponseDto createTeacher(@RequestBody TeacherRequestDto teacher, @RequestParam List<Long> ids) {
        return teacherService.createTeacher(teacher,ids);
    }

    @GetMapping("/readTeacher")
    public LinkedList<TeacherResponseDto> readTeacher() {
        return teacherService.readTeachers();
    }

    @PutMapping("/updateTeacher")
    public TeacherResponseDto updateTeacher(@RequestBody TeacherRequestDto teacher, @RequestParam Long id, @RequestParam List<Long> ids) {
        return teacherService.updateTeacher(teacher, id,ids);
    }

    @DeleteMapping("/deleteTeacher")
    public void deleteTeacher(@RequestParam Long id) {
        teacherService.deleteTeacher(id);
    }
}
