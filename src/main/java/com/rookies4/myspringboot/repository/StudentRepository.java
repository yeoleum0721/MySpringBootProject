package com.rookies4.myspringboot.repository;

import com.rookies4.myspringboot.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//StudentRepository 인터페이스
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    //학번으로 조회하는 QueryMethod
    Optional<Student> findByStudentNumber(String studentNumber);
    //Fetch Join을 사용하여 1개의 Query만 생성되도록 처리
    @Query("SELECT s FROM Student s JOIN FETCH s.studentDetail WHERE s.id = :id")
    //학번의 존재여부
    Optional<Student> findByIdWithStudentDetail(@Param("id") Long studentid);
    //학번의 중복체크를 위한 메서드
    boolean existsByStudentNumber(String studentNumber);
}