package com.branch.StudentTeacherCRUD.repository;

import com.branch.StudentTeacherCRUD.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Page<Teacher> findByName(String name, Pageable pageable);

    Page<Teacher> findByEmail(String email, Pageable pageable);

    Page<Teacher> findByContactNo(String contactNo, Pageable pageable);

    Page<Teacher> findByAddress(String address, Pageable pageable);

    Page<Teacher> findByDesignation(String designation, Pageable pageable);

}
