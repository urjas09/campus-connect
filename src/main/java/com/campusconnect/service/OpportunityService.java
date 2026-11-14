package com.campusconnect.service;

import com.campusconnect.dto.OpportunityDto;
import com.campusconnect.entity.Opportunity;
import com.campusconnect.exception.ResourceNotFoundException;
import com.campusconnect.repository.OpportunityRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpportunityService {

    private final OpportunityRepository opportunityRepository;

    public OpportunityService(OpportunityRepository opportunityRepository) {
        this.opportunityRepository = opportunityRepository;
    }

    public OpportunityDto createOpportunity(OpportunityDto dto) {
        Opportunity opportunity = mapToEntity(dto);
        Opportunity saved = opportunityRepository.save(opportunity);
        return mapToDto(saved);
    }

    public OpportunityDto updateOpportunity(Long id, OpportunityDto dto) {
        Opportunity existing = opportunityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Opportunity not found with id: " + id));

        existing.setCompanyName(dto.getCompanyName());
        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setJobType(dto.getJobType());
        existing.setLocation(dto.getLocation());
        existing.setPackageDetails(dto.getPackageDetails());
        existing.setMinCgpa(dto.getMinCgpa());
        existing.setAllowedBranches(dto.getAllowedBranches());
        existing.setGraduationYear(dto.getGraduationYear());
        existing.setMaxBacklogs(dto.getMaxBacklogs());
        existing.setRequiredSkills(dto.getRequiredSkills());
        existing.setApplicationDeadline(dto.getApplicationDeadline());

        Opportunity updated = opportunityRepository.save(existing);
        return mapToDto(updated);
    }

    public OpportunityDto getOpportunityById(Long id) {
        Opportunity opportunity = opportunityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Opportunity not found with id: " + id));
        return mapToDto(opportunity);
    }

    public List<OpportunityDto> getAllOpportunities() {
        return opportunityRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<OpportunityDto> getActiveOpportunities() {
        return opportunityRepository.findByApplicationDeadlineGreaterThanEqualOrderByApplicationDeadlineAsc(LocalDate.now())
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public void deleteOpportunity(Long id) {
        if (!opportunityRepository.existsById(id)) {
            throw new ResourceNotFoundException("Opportunity not found with id: " + id);
        }
        opportunityRepository.deleteById(id);
    }

    public OpportunityDto mapToDto(Opportunity o) {
        OpportunityDto dto = new OpportunityDto();
        dto.setId(o.getId());
        dto.setCompanyName(o.getCompanyName());
        dto.setTitle(o.getTitle());
        dto.setDescription(o.getDescription());
        dto.setJobType(o.getJobType());
        dto.setLocation(o.getLocation());
        dto.setPackageDetails(o.getPackageDetails());
        dto.setMinCgpa(o.getMinCgpa());
        dto.setAllowedBranches(o.getAllowedBranches());
        dto.setGraduationYear(o.getGraduationYear());
        dto.setMaxBacklogs(o.getMaxBacklogs());
        dto.setRequiredSkills(o.getRequiredSkills());
        dto.setApplicationDeadline(o.getApplicationDeadline());
        return dto;
    }

    public Opportunity mapToEntity(OpportunityDto dto) {
        return new Opportunity(
                dto.getCompanyName(),
                dto.getTitle(),
                dto.getDescription(),
                dto.getJobType(),
                dto.getLocation(),
                dto.getPackageDetails(),
                dto.getMinCgpa(),
                dto.getAllowedBranches(),
                dto.getGraduationYear(),
                dto.getMaxBacklogs(),
                dto.getRequiredSkills(),
                dto.getApplicationDeadline()
        );
    }
}
