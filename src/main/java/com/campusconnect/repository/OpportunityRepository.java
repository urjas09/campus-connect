package com.campusconnect.repository;

import com.campusconnect.entity.JobType;
import com.campusconnect.entity.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {

    List<Opportunity> findByJobType(JobType jobType);

    List<Opportunity> findByApplicationDeadlineGreaterThanEqualOrderByApplicationDeadlineAsc(LocalDate date);

    @Query("SELECT o FROM Opportunity o WHERE o.applicationDeadline >= :today " +
           "AND o.graduationYear = :gradYear " +
           "AND o.minCgpa <= :cgpa " +
           "AND o.maxBacklogs >= :backlogs")
    List<Opportunity> findEligibleBaseOpportunities(
            @Param("today") LocalDate today,
            @Param("gradYear") Integer gradYear,
            @Param("cgpa") Double cgpa,
            @Param("backlogs") Integer backlogs
    );
}
