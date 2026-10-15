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

    @PostMapping("/createTeacher/{ids}")
    public TeacherResponseDto createTeacher(@RequestBody TeacherRequestDto teacher, @PathVariable List<Long> ids) {
        return teacherService.createTeacher(teacher,ids);
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
