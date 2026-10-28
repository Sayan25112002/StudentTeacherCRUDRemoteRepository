package com.branch.StudentTeacherCRUD.repository;

import com.branch.StudentTeacherCRUD.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
}
