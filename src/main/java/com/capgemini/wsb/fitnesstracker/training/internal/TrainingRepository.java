package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

interface TrainingRepository extends JpaRepository<Training, Long> {
    default List<Training> findByUserId(Long userId) {
        return findAll().stream()
                        .filter(training -> training.getUser().getId().equals(userId))
                        .toList();
    }

    default List<Training> findByEndDateAfter(LocalDate date) {
        return findAll().stream()
                        .filter(training -> training.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(date))
                        .toList();
    }

    default List<Training> findByActivityType(ActivityType activityType) {
        return findAll().stream()
                        .filter(training -> training.getActivityType().equals(activityType))
                        .toList();
    }

    default List<Training> findByUserIdAndMonth(Long userId, LocalDate date) {
        return findAll().stream()
                        .filter(training -> training.getUser().getId().equals(userId))
                        .filter(training -> training.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonth().equals(date.getMonth()))
                        .toList();
    }
}
