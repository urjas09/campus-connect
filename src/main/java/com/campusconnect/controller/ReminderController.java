package com.campusconnect.controller;

import com.campusconnect.entity.DeadlineReminder;
import com.campusconnect.service.ReminderSchedulerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reminders")
@CrossOrigin(origins = "*")
public class ReminderController {

    private final ReminderSchedulerService reminderService;

    public ReminderController(ReminderSchedulerService reminderService) {
        this.reminderService = reminderService;
    }

    @PostMapping("/trigger")
    public ResponseEntity<Map<String, Object>> manualTrigger() {
        int count = reminderService.checkAndDispatchReminders();
        Map<String, Object> resp = new HashMap<>();
        resp.put("status", "SUCCESS");
        resp.put("remindersDispatched", count);
        resp.put("message", "Checked all active drives closing within 72 hours against eligible student profiles.");
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<DeadlineReminder>> getStudentReminders(@PathVariable Long studentId) {
        return ResponseEntity.ok(reminderService.getRemindersForStudent(studentId));
    }
}
