package com.branch.StudentTeacherCRUD.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String photoPath;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String contactNo;

    private String password;

    private String address;

    private String designation;

    @ManyToMany(mappedBy = "teachers", fetch = FetchType.EAGER)
    private Set<Student> students = new HashSet<>();

}
