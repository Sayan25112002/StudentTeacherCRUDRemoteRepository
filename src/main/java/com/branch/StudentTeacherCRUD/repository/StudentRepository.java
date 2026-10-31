package com.branch.StudentTeacherCRUD.repository;

import com.branch.StudentTeacherCRUD.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    Page<Student> findByFirstName(String firstName, Pageable pageable);

    Page<Student> findByLastName(String lastName, Pageable pageable);

    Page<Student> findByEmail(String email, Pageable pageable);

    Page<Student> findByContactNo(String contactNo, Pageable pageable);

    Page<Student> findByAddress(String address, Pageable pageable);

}
