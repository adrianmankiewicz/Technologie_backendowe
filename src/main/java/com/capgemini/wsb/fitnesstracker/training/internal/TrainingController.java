package com.capgemini.wsb.fitnesstracker.training.internal;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.wsb.fitnesstracker.training.api.Training;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    @Autowired
    private TrainingServiceImpl trainingService;

    @GetMapping
    public List<Training> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    @GetMapping("/{userId}")
    public List<Training> getTrainingsByUser(@PathVariable Long userId) {
        return trainingService.getTrainingsByUser(userId);
    }

    @GetMapping("/finished/{afterDate}")
    public List<Training> getFinishedTrainings(@PathVariable String afterDate) {
        return trainingService.getCompletedTrainings(LocalDate.parse(afterDate));
    }

    @GetMapping("/activityType")
    public List<Training> getTrainingsByActivity(@RequestParam ActivityType activityType) {
        return trainingService.getTrainingsByActivity(activityType);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Training createTraining(@RequestBody TrainingDto trainingDto) {
        return trainingService.createTraining(trainingDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Training> updateTraining(@PathVariable Long id, @RequestBody Training training) {
        return trainingService.updateTraining(id, training)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
