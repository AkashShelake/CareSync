package com.akash.CareSync.chartnotes.controller;

import com.akash.CareSync.chartnotes.entity.Encounter;
import com.akash.CareSync.chartnotes.service.EncounterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rest/v1/encounters")
public class EncounterController {
    private final EncounterService encounterService;

    EncounterController(EncounterService encounterService) {
        this.encounterService = encounterService;
    }

    @PostMapping("/appointment/{appointmentId}")
    public ResponseEntity<Encounter> createEncounterForAppointment(
            @PathVariable Long appointmentId,
            @RequestBody Encounter encounter) {
        Encounter createdEncounter = encounterService.createEncounterWithAppointment(appointmentId, encounter);
        return ResponseEntity.ok(createdEncounter);
    }

    @PostMapping("/direct")
    public ResponseEntity<Encounter> createDirectEncounter(@RequestBody Encounter encounter) {
        Encounter createdEncounter = encounterService.createDirectEncounter(encounter);
        return ResponseEntity.ok(createdEncounter);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Encounter> updateEncounter(
            @PathVariable Long id,
            @RequestBody Encounter updatedEncounter) {
        Optional<Encounter> encounter = encounterService.updateEncounter(id, updatedEncounter);
        return encounter.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Encounter> getEncounterById(@PathVariable Long id) {
        Optional<Encounter> encounter = encounterService.getEncounterById(id);
        return encounter.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Encounter>> getAllEncounters(
            @RequestParam(required = false) Long provider_id,
            @RequestParam(required = false) Long patient_id,
            @RequestParam(required = false) String status) {
        List<Encounter> encounters = encounterService.getEncountersWithFilters(provider_id, patient_id, status);
        return ResponseEntity.ok(encounters);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteEncounter(@PathVariable Long id) {
        boolean deleted = encounterService.softDeleteEncounter(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Encounter> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        Optional<Encounter> encounter = encounterService.updateStatus(id, status);
        return encounter.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}