package com.branch.StudentTeacherCRUD.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String contactNo;

    private String password;

    private String address;

    private String designation;

    @ManyToMany(mappedBy = "teachers")
    private List<Student> students = new ArrayList<>();

}
