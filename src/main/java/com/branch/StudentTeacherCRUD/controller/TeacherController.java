package com.branch.StudentTeacherCRUD.controller;

import com.branch.StudentTeacherCRUD.dto.requestDto.TeacherRequestDto;
import com.branch.StudentTeacherCRUD.dto.responseDto.TeacherResponseDto;
import com.branch.StudentTeacherCRUD.service.TeacherService;
import com.branch.StudentTeacherCRUD.validations.Create;
import com.branch.StudentTeacherCRUD.validations.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/createTeacher")
    public TeacherResponseDto createTeacher(@ModelAttribute @Validated(Create.class) TeacherRequestDto teacher) throws IOException {
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
    public TeacherResponseDto updateTeacher(@ModelAttribute @Validated(Update.class) TeacherRequestDto teacher, @RequestParam Long id) throws IOException {
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
