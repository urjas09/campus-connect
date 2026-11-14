# CampusConnect – Intelligent Campus Opportunity & Career Readiness Platform

A full-stack placement management system built with Spring Boot and vanilla JavaScript as my final-year project. I built this to solve a real problem in my college — students often miss placement drives, don't know why they were rejected, or apply to drives they're not eligible for without realizing it.

---

## What it does

CampusConnect is not just a job board. It has an intelligent layer on top that helps students understand *why* they're eligible or not, *what skills* they're missing, and *how ready* they are for placement season. Placement officers get analytics to track the entire cohort.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 4.1.2, Spring Data JPA |
| Database | MySQL 8.0 (Hibernate ORM) |
| Security | spring-security-crypto (BCrypt password hashing) |
| Validation | Jakarta Bean Validation (`@Valid`, `@DecimalMin`, etc.) |
| Scheduler | Spring `@Scheduled` (cron-based deadline reminders) |
| Frontend | HTML5, CSS3, Vanilla JavaScript (fetch API) |
| Build | Apache Maven |

---

## 5 Distinctive Features

### 1. Eligibility Engine (`/api/eligibility/check`)
Instead of just showing all drives, the system runs a 6-point check per student per drive: deadline validity, minimum CGPA, branch filter, graduation year, backlog limit, and skill requirements. It returns a transparent match percentage broken into Academic Score (60%) + Skill Score (40%).

### 2. Skill Gap Analyzer (`/api/skills/analyze`)
Compares a student's skills to what a drive requires. Skills are marked as AVAILABLE, NEEDS_IMPROVEMENT (you have a related skill, e.g. "mysql" for "sql"), or MISSING. It also generates learning tips for each missing skill.

### 3. Application Journey Tracker (`/api/applications`)
Tracks applications through 6 stages: APPLIED → ASSESSMENT → TECHNICAL_INTERVIEW → HR_INTERVIEW → SELECTED / REJECTED. Every status change is logged to an audit history table with timestamps so both student and officer can see the full timeline.

### 4. Placement Readiness Score (`/api/readiness/student/{id}`)
A composite score out of 100 across 4 pillars: Academic (30 pts), Profile Completeness (20 pts), Skill Breadth (30 pts), and Application Momentum (20 pts). Assigns bands: Campus Star (≥85), Competitive (≥70), Needs Prep (≥50), Critical (<50).

### 5. Recommendation Engine (`/api/recommendations/student/{id}`)
Recommends drives ranked by an affinity score: Skill Overlap (40 pts) + Career Interest Match (35 pts) + CGPA Margin (25 pts). Already-applied drives are excluded. Each recommendation includes a human-readable "Why Recommended" explanation.

---

## Project Structure

```
src/main/java/com/campusconnect/
├── config/           # DataSeeder (auto seeds empty DB on startup)
├── controller/       # REST controllers (one per feature)
├── dto/              # Data Transfer Objects (request/response shapes)
├── entity/           # JPA entities (database tables)
├── exception/        # Custom exceptions + GlobalExceptionHandler
├── repository/       # Spring Data JPA repositories
└── service/          # Business logic lives here

src/main/resources/
├── static/           # HTML + CSS pages (no React, no frameworks)
│   ├── login.html, register.html, dashboard.html
│   ├── profile.html, opportunities.html
│   ├── eligibility.html, skill-gap.html
│   ├── recommendations.html, readiness.html
│   ├── applications.html, analytics.html
│   └── css/style.css
└── application.properties  (not committed — see .example file)
```

---

## How to Run Locally

### Prerequisites
- Java 17+ (I used Eclipse Temurin 17)
- MySQL 8.0 running on port 3306
- Maven (or use the included `mvnw` wrapper)

### Setup Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/<your-username>/campus-connect.git
   cd campus-connect
   ```

2. **Create the database** in MySQL Workbench or CLI:
   ```sql
   CREATE DATABASE campus_connect;
   ```

3. **Configure credentials**  
   Copy the example file and fill in your MySQL password:
   ```bash
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```
   Edit `application.properties`:
   ```
   spring.datasource.password=your_mysql_password_here
   ```

4. **Run the app**
   ```bash
   ./mvnw spring-boot:run       # Linux/Mac
   .\mvnw spring-boot:run       # Windows PowerShell
   ```

5. **Open in browser**  
   Navigate to `http://localhost:8080/login.html`

   If the database is empty, the app auto-seeds 2 students, 1 placement officer, and 4 job drives on startup. Use these logins:
   - Student: `aarav@college.edu` / `password123`
   - Officer: `officer@college.edu` / `admin123`

---

## API Endpoints Summary

| Feature | Method | Endpoint |
|---|---|---|
| Register | POST | `/api/auth/register` |
| Login | POST | `/api/auth/login` |
| Student Profile | POST/GET | `/api/students/profile/{userId}` |
| List All Students | GET | `/api/students` |
| Opportunities | GET/POST | `/api/opportunities` |
| Eligibility Check | GET | `/api/eligibility/check?studentId=&opportunityId=` |
| Skill Gap | GET | `/api/skills/analyze?studentId=&opportunityId=` |
| Apply | POST | `/api/applications/apply?studentId=&opportunityId=` |
| Update Status | PUT | `/api/applications/{id}/status` |
| Readiness Score | GET | `/api/readiness/student/{id}` |
| Recommendations | GET | `/api/recommendations/student/{id}` |
| Analytics | GET | `/api/analytics/overview` |
| Trigger Reminders | POST | `/api/reminders/trigger` |
| Get Reminders | GET | `/api/reminders/student/{profileId}` |

---

## Git Commit Structure

Each feature was built, tested via API, and committed separately — no big-bang commits.

```
434e1a7  Initial setup: Spring Boot + MySQL + health endpoint
85e3f36  Add user authentication with BCrypt password hashing
47f809a  Add student profile module with CGPA and skill fields
27d1cf6  Add opportunity management with job type and deadline
5c95cbe  Add eligibility engine with 6-point check and match score
2a547d1  Add skill gap analyzer with AVAILABLE/NEEDS_IMPROVEMENT/MISSING status
da9286e  Add application journey tracker with 6-stage pipeline and audit log
c7c3217  Add placement readiness score with 4-pillar breakdown
527a162  Add recommendation engine with affinity scoring
69f541b  Add scheduler for deadline reminders with deduplication
d0eda6f  Add placement analytics dashboard with stage funnel, selection rate, and branch breakdown
```

---

## What I Learned

- **Spring Data JPA**: how `@Entity`, `@OneToOne`, `@ManyToOne` map to database tables; writing custom JPQL queries with `@Query`
- **REST API design**: difference between POST (create) and PUT (update), using `ResponseEntity` to control HTTP status codes, `@RestControllerAdvice` for centralized error handling
- **BCrypt**: why we never store plain text passwords; how salting works and why the same password gives a different hash each time
- **Spring Scheduler**: `@EnableScheduling` + `@Scheduled(cron = "...")` for running background tasks without any external tools
- **Java Streams**: `Collectors.groupingBy`, `Comparator.comparingDouble`, method references (`::`) for clean functional-style data processing
- **Bean Validation**: `@NotBlank`, `@DecimalMin`, `@Min` to validate incoming JSON before it hits the database
- **Responsive CSS**: CSS Grid `auto-fill` + `minmax()` for layouts that adapt from desktop to phone without media query bloat

---

## Security Note

`application.properties` is listed in `.gitignore` and is **never committed**. The committed `application.properties.example` file has placeholder values only. The database password never appears in the git history.

---

*Built by [Your Name], [Branch], [College Name], [Year]*
