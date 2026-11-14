package com.campusconnect.controller;

import com.campusconnect.dto.OpportunityDto;
import com.campusconnect.service.OpportunityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/opportunities")
@CrossOrigin(origins = "*")
public class OpportunityController {

    private final OpportunityService opportunityService;

    public OpportunityController(OpportunityService opportunityService) {
        this.opportunityService = opportunityService;
    }

    @PostMapping
    public ResponseEntity<OpportunityDto> createOpportunity(@Valid @RequestBody OpportunityDto dto) {
        OpportunityDto created = opportunityService.createOpportunity(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OpportunityDto> updateOpportunity(
            @PathVariable Long id,
            @Valid @RequestBody OpportunityDto dto) {
        OpportunityDto updated = opportunityService.updateOpportunity(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OpportunityDto> getOpportunityById(@PathVariable Long id) {
        return ResponseEntity.ok(opportunityService.getOpportunityById(id));
    }

    @GetMapping
    public ResponseEntity<List<OpportunityDto>> getAllOpportunities(
            @RequestParam(defaultValue = "false") boolean activeOnly) {
        if (activeOnly) {
            return ResponseEntity.ok(opportunityService.getActiveOpportunities());
        }
        return ResponseEntity.ok(opportunityService.getAllOpportunities());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpportunity(@PathVariable Long id) {
        opportunityService.deleteOpportunity(id);
        return ResponseEntity.noContent().build();
    }
}
