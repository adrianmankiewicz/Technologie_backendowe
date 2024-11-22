package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;
    private final UserProvider userRepository;

    @Override
    public Optional<User> getTraining(final Long trainingId) {
        throw new UnsupportedOperationException("Not finished yet");
    }

    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    public List<Training> getTrainingsByUser(Long userId) {
        return trainingRepository.findByUserId(userId);
    }

    public List<Training> getCompletedTrainings(LocalDate date) {
        return trainingRepository.findByEndDateAfter(date);
    }

    public List<Training> getTrainingsByActivity(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType);
    }

    public Training createTraining(TrainingDto trainingDto) {
        User user = userRepository.getUser(trainingDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Training training = new Training(
                user,
                trainingDto.getStartTime(),
                trainingDto.getEndTime(),
                trainingDto.getActivityType(),
                trainingDto.getDistance(),
                trainingDto.getAverageSpeed()
        );

        return trainingRepository.save(training);
    }

    public Optional<Training> updateTraining(Long id, Training training) {
        return trainingRepository.findById(id)
                .map(existingTraining -> {
                    existingTraining.setDistance(training.getDistance());
                    existingTraining.setDuration(training.getDuration());
                    existingTraining.setActivityType(training.getActivityType());
                    return trainingRepository.save(existingTraining);
                });
    }

    public String generateMonthlyReport(User user, LocalDate month) {
        List<Training> trainings = trainingRepository.findByUserIdAndMonth(user.getId(), month);
        int totalTrainings = trainings.size();
        return "Użytkownik: " + user.getFirstName() + " " + user.getLastName() + "\n" +
               "Liczba treningów w miesiącu " + month.getMonth() + ": " + totalTrainings;
    }

}
