package com.campusconnect.repository;

import com.campusconnect.entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {

    Optional<StudentProfile> findByUserId(Long userId);

    Optional<StudentProfile> findByRollNumber(String rollNumber);

    boolean existsByRollNumber(String rollNumber);

    List<StudentProfile> findByBranch(String branch);

    List<StudentProfile> findByGraduationYear(Integer graduationYear);
}
