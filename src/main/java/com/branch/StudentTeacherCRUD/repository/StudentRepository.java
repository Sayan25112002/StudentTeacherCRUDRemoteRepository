package com.branch.StudentTeacherCRUD.repository;

import com.branch.StudentTeacherCRUD.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
