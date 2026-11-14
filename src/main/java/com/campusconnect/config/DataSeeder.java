package com.campusconnect.config;

import com.campusconnect.entity.*;
import com.campusconnect.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

// DataSeeder runs once at startup.
// If the database is empty, it inserts a few sample students and opportunities
// so there's always meaningful data for a demo without manual SQL.
@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final StudentProfileRepository profileRepository;
    private final OpportunityRepository opportunityRepository;

    public DataSeeder(UserRepository userRepository,
                      StudentProfileRepository profileRepository,
                      OpportunityRepository opportunityRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.opportunityRepository = opportunityRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Only seed if the database is completely empty
        if (userRepository.count() > 0) {
            System.out.println("[DataSeeder] Data already exists, skipping seed.");
            return;
        }

        System.out.println("[DataSeeder] Empty database detected. Inserting sample data...");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // ── Sample Users ──────────────────────────────────────────────
        User user1 = new User();
        user1.setFullName("Aarav Sharma");
        user1.setEmail("aarav@college.edu");
        user1.setPassword(encoder.encode("password123"));
        user1.setRole(Role.STUDENT);
        userRepository.save(user1);

        User user2 = new User();
        user2.setFullName("Priya Nair");
        user2.setEmail("priya@college.edu");
        user2.setPassword(encoder.encode("password123"));
        user2.setRole(Role.STUDENT);
        userRepository.save(user2);

        User user3 = new User();
        user3.setFullName("Dr. Meera Iyer");
        user3.setEmail("officer@college.edu");
        user3.setPassword(encoder.encode("admin123"));
        user3.setRole(Role.PLACEMENT_OFFICER);
        userRepository.save(user3);

        // ── Sample Student Profiles ───────────────────────────────────
        StudentProfile profile1 = new StudentProfile();
        profile1.setUser(user1);
        profile1.setRollNumber("CS2024001");
        profile1.setBranch("CSE");
        profile1.setGraduationYear(2026);
        profile1.setCgpa(8.7);
        profile1.setActiveBacklogs(0);
        profile1.setSkills("java,spring boot,sql,python,data structures");
        profile1.setCareerInterests("backend,software engineering");
        profileRepository.save(profile1);

        StudentProfile profile2 = new StudentProfile();
        profile2.setUser(user2);
        profile2.setRollNumber("EC2024002");
        profile2.setBranch("ECE");
        profile2.setGraduationYear(2026);
        profile2.setCgpa(7.9);
        profile2.setActiveBacklogs(1);
        profile2.setSkills("c++,python,machine learning,matlab");
        profile2.setCareerInterests("embedded systems,data science");
        profileRepository.save(profile2);

        // ── Sample Opportunities ──────────────────────────────────────
        Opportunity opp1 = new Opportunity();
        opp1.setTitle("Backend Software Engineer Intern");
        opp1.setCompanyName("Google");
        opp1.setJobType(JobType.INTERNSHIP);
        opp1.setDescription("Work on scalable backend systems. Good communication skills required.");
        opp1.setRequiredSkills("java,spring boot,sql,data structures");
        opp1.setMinCgpa(7.5);
        opp1.setMaxBacklogs(0);
        opp1.setGraduationYear(2026);
        opp1.setApplicationDeadline(LocalDate.now().plusDays(30));
        opportunityRepository.save(opp1);

        Opportunity opp2 = new Opportunity();
        opp2.setTitle("Full Stack Developer");
        opp2.setCompanyName("Amazon");
        opp2.setJobType(JobType.FULL_TIME);
        opp2.setDescription("Build customer-facing features on AWS-backed platforms.");
        opp2.setRequiredSkills("java,react,sql,spring boot,system design");
        opp2.setMinCgpa(8.0);
        opp2.setMaxBacklogs(0);
        opp2.setGraduationYear(2026);
        opp2.setApplicationDeadline(LocalDate.now().plusDays(15));
        opportunityRepository.save(opp2);

        Opportunity opp3 = new Opportunity();
        opp3.setTitle("AI Research Intern");
        opp3.setCompanyName("Microsoft Research");
        opp3.setJobType(JobType.INTERNSHIP);
        opp3.setDescription("Research in natural language processing and computer vision.");
        opp3.setRequiredSkills("python,machine learning,deep learning,tensorflow");
        opp3.setMinCgpa(8.5);
        opp3.setMaxBacklogs(0);
        opp3.setGraduationYear(2026);
        opp3.setApplicationDeadline(LocalDate.now().plusDays(20));
        opportunityRepository.save(opp3);

        Opportunity opp4 = new Opportunity();
        opp4.setTitle("Data Analyst");
        opp4.setCompanyName("Flipkart");
        opp4.setJobType(JobType.FULL_TIME);
        opp4.setDescription("Analyze sales data and build dashboards. Deadline closing soon.");
        opp4.setRequiredSkills("python,sql,excel,data visualization");
        opp4.setMinCgpa(7.0);
        opp4.setMaxBacklogs(2);
        opp4.setGraduationYear(2026);
        opp4.setApplicationDeadline(LocalDate.now().plusDays(2)); // urgency for reminder demo
        opportunityRepository.save(opp4);

        System.out.println("[DataSeeder] Seeded: Aarav Sharma (CSE), Priya Nair (ECE), Dr. Meera Iyer (Officer)");
        System.out.println("[DataSeeder] Also seeded 4 opportunities: Google, Amazon, Microsoft Research, Flipkart");
        System.out.println("[DataSeeder] Student login: aarav@college.edu / password123");
        System.out.println("[DataSeeder] Officer login: officer@college.edu / admin123");
    }
}
