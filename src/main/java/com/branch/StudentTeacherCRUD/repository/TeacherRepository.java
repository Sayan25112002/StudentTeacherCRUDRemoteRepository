package com.branch.StudentTeacherCRUD.repository;

import com.branch.StudentTeacherCRUD.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("SELECT t FROM Teacher t WHERE t.name ILIKE %:name%")
    Page<Teacher> findByName(String name, Pageable pageable);

    @Query("SELECT t FROM Teacher t WHERE t.email ILIKE %:email%")
    Page<Teacher> findByEmail(String email, Pageable pageable);

    @Query("SELECT t FROM Teacher t WHERE t.contactNo ILIKE %:contactNo%")
    Page<Teacher> findByContactNo(String contactNo, Pageable pageable);

    @Query("SELECT t FROM Teacher t WHERE t.address ILIKE %:address%")
    Page<Teacher> findByAddress(String address, Pageable pageable);

    @Query("SELECT t FROM Teacher t WHERE t.designation ILIKE %:designation%")
    Page<Teacher> findByDesignation(String designation, Pageable pageable);

}
