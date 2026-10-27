package com.branch.StudentTeacherCRUD.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String photoPath;

    private String firstName;

    private String lastName;

    private String email;

    private String contactNo;

    private String password;

    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "studentTeacherTable",
            joinColumns = @JoinColumn(name="student_id"),
            inverseJoinColumns = @JoinColumn(name="teacher_id")
    )
    private Set<Teacher> teachers = new HashSet<>();

}
