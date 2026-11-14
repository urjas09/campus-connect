package com.campusconnect.service;

import com.campusconnect.entity.DeadlineReminder;
import com.campusconnect.entity.Opportunity;
import com.campusconnect.entity.StudentProfile;
import com.campusconnect.repository.ApplicationRepository;
import com.campusconnect.repository.DeadlineReminderRepository;
import com.campusconnect.repository.OpportunityRepository;
import com.campusconnect.repository.StudentProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReminderSchedulerService {

    private static final Logger log = LoggerFactory.getLogger(ReminderSchedulerService.class);

    private final OpportunityRepository opportunityRepository;
    private final StudentProfileRepository profileRepository;
    private final ApplicationRepository applicationRepository;
    private final DeadlineReminderRepository reminderRepository;
    private final EligibilityEngineService eligibilityService;

    public ReminderSchedulerService(OpportunityRepository opportunityRepository,
                                    StudentProfileRepository profileRepository,
                                    ApplicationRepository applicationRepository,
                                    DeadlineReminderRepository reminderRepository,
                                    EligibilityEngineService eligibilityService) {
        this.opportunityRepository = opportunityRepository;
        this.profileRepository = profileRepository;
        this.applicationRepository = applicationRepository;
        this.reminderRepository = reminderRepository;
        this.eligibilityService = eligibilityService;
    }

    /**
     * Runs every hour (cron: 0 0 * * * ?) in production.
     * For demo purposes, we also expose a manual trigger method.
     */
    @Scheduled(cron = "0 0 9 * * ?") // 9:00 AM every morning
    public void scheduledDailyReminderCheck() {
        log.info("Running scheduled campus drive deadline check...");
        checkAndDispatchReminders();
    }

    @Transactional
    public int checkAndDispatchReminders() {
        LocalDate today = LocalDate.now();
        LocalDate threeDaysLater = today.plusDays(3);

        List<Opportunity> closingSoon = opportunityRepository
                .findByApplicationDeadlineGreaterThanEqualOrderByApplicationDeadlineAsc(today)
                .stream()
                .filter(opp -> !opp.getApplicationDeadline().isAfter(threeDaysLater))
                .toList();

        int remindersSent = 0;
        List<StudentProfile> allStudents = profileRepository.findAll();

        for (Opportunity opp : closingSoon) {
            for (StudentProfile student : allStudents) {
                // Check if already applied
                if (applicationRepository.existsByStudentIdAndOpportunityId(student.getId(), opp.getId())) {
                    continue;
                }

                // Check if reminder was already dispatched
                if (reminderRepository.existsByOpportunityIdAndStudentId(opp.getId(), student.getId())) {
                    continue;
                }

                // Check if eligible
                var eval = eligibilityService.evaluateEligibility(student.getId(), opp.getId());
                if (eval.isEligible()) {
                    String msg = String.format("Urgent: Application for %s (%s) closes in %d days on %s. You meet all criteria!",
                            opp.getCompanyName(), opp.getTitle(),
                            java.time.temporal.ChronoUnit.DAYS.between(today, opp.getApplicationDeadline()),
                            opp.getApplicationDeadline());

                    DeadlineReminder reminder = new DeadlineReminder(
                            opp.getId(),
                            opp.getCompanyName(),
                            opp.getTitle(),
                            student.getId(),
                            student.getUser().getEmail(),
                            msg
                    );

                    reminderRepository.save(reminder);
                    remindersSent++;
                    log.info("Reminder queued for student {} for drive: {}", student.getUser().getEmail(), opp.getTitle());
                }
            }
        }

        return remindersSent;
    }

    public List<DeadlineReminder> getRemindersForStudent(Long studentId) {
        return reminderRepository.findByStudentIdOrderByReminderSentAtDesc(studentId);
    }
}
