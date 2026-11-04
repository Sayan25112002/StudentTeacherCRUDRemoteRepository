package com.branch.StudentTeacherCRUD.repository;

import com.branch.StudentTeacherCRUD.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    @Query("SELECT s FROM Student s WHERE s.firstName ILIKE %:firstName%")
    Page<Student> findByFirstName(String firstName, Pageable pageable);

    @Query("SELECT s FROM Student s WHERE s.lastName ILIKE %:lastName%")
    Page<Student> findByLastName(String lastName, Pageable pageable);

    @Query("SELECT s FROM Student s WHERE s.email ILIKE %:email%")
    Page<Student> findByEmail(String email, Pageable pageable);

    @Query("SELECT s FROM Student s WHERE s.contactNo ILIKE %:contactNo%")
    Page<Student> findByContactNo(String contactNo, Pageable pageable);

    @Query("SELECT s FROM Student s WHERE s.address ILIKE %:address%")
    Page<Student> findByAddress(String address, Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByContactNo(String contactNo);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByContactNoAndIdNot(String contactNo, Long id);
}
