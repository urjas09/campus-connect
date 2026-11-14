package com.campusconnect.repository;

import com.campusconnect.entity.DeadlineReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeadlineReminderRepository extends JpaRepository<DeadlineReminder, Long> {

    List<DeadlineReminder> findByStudentIdOrderByReminderSentAtDesc(Long studentId);

    boolean existsByOpportunityIdAndStudentId(Long opportunityId, Long studentId);
}
