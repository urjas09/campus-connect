package com.campusconnect.service;

import com.campusconnect.dto.StudentProfileDto;
import com.campusconnect.entity.Role;
import com.campusconnect.entity.StudentProfile;
import com.campusconnect.entity.User;
import com.campusconnect.exception.BadRequestException;
import com.campusconnect.exception.ResourceNotFoundException;
import com.campusconnect.repository.StudentProfileRepository;
import com.campusconnect.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentProfileService {

    private final StudentProfileRepository profileRepository;
    private final UserRepository userRepository;

    public StudentProfileService(StudentProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public StudentProfileDto saveOrUpdateProfile(Long userId, StudentProfileDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (user.getRole() != Role.STUDENT) {
            throw new BadRequestException("Only users with role STUDENT can have a student profile");
        }

        StudentProfile profile = profileRepository.findByUserId(userId)
                .orElse(new StudentProfile());

        // Validate roll number uniqueness across different students
        profileRepository.findByRollNumber(dto.getRollNumber()).ifPresent(existing -> {
            if (!existing.getId().equals(profile.getId())) {
                throw new BadRequestException("Roll number is already registered by another student: " + dto.getRollNumber());
            }
        });

        profile.setUser(user);
        profile.setRollNumber(dto.getRollNumber().trim().toUpperCase());
        profile.setBranch(dto.getBranch().trim().toUpperCase());
        profile.setCgpa(dto.getCgpa());
        profile.setGraduationYear(dto.getGraduationYear());
        profile.setActiveBacklogs(dto.getActiveBacklogs());
        profile.setSkills(dto.getSkills());
        profile.setCareerInterests(dto.getCareerInterests());
        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setLinkedInUrl(dto.getLinkedInUrl());
        profile.setGitHubUrl(dto.getGitHubUrl());
        profile.setResumeUrl(dto.getResumeUrl());

        StudentProfile saved = profileRepository.save(profile);
        return mapToDto(saved);
    }

    public StudentProfileDto getProfileByUserId(Long userId) {
        StudentProfile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found for user id: " + userId));
        return mapToDto(profile);
    }

    public List<StudentProfileDto> getAllProfiles() {
        return profileRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private StudentProfileDto mapToDto(StudentProfile profile) {
        StudentProfileDto dto = new StudentProfileDto();
        dto.setId(profile.getId());
        dto.setUserId(profile.getUser().getId());
        dto.setFullName(profile.getUser().getFullName());
        dto.setEmail(profile.getUser().getEmail());
        dto.setRollNumber(profile.getRollNumber());
        dto.setBranch(profile.getBranch());
        dto.setCgpa(profile.getCgpa());
        dto.setGraduationYear(profile.getGraduationYear());
        dto.setActiveBacklogs(profile.getActiveBacklogs());
        dto.setSkills(profile.getSkills());
        dto.setCareerInterests(profile.getCareerInterests());
        dto.setPhoneNumber(profile.getPhoneNumber());
        dto.setLinkedInUrl(profile.getLinkedInUrl());
        dto.setGitHubUrl(profile.getGitHubUrl());
        dto.setResumeUrl(profile.getResumeUrl());
        return dto;
    }
}
