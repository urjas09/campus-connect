package com.campusconnect.repository;

import com.campusconnect.entity.Application;
import com.campusconnect.entity.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByStudentId(Long studentId);

    List<Application> findByOpportunityId(Long opportunityId);

    Optional<Application> findByStudentIdAndOpportunityId(Long studentId, Long opportunityId);

    boolean existsByStudentIdAndOpportunityId(Long studentId, Long opportunityId);

    long countByCurrentStatus(ApplicationStatus status);

    long countByStudentId(Long studentId);
}
