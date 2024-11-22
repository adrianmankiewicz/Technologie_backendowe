package com.capgemini.wsb.fitnesstracker.scheduler;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MonthlyReportScheduler {

    private final UserProvider userProvider;
    private final TrainingServiceImpl trainingService;
    private final EmailSender emailSender;

    @Scheduled(cron = "0 0 0 1 * ?") // Co miesiąc pierwszego dnia o północy
    public void sendMonthlyReports() {
        List<User> users = userProvider.findAllUsers();
        LocalDate now = LocalDate.now();
        users.forEach(user -> {
            String report = trainingService.generateMonthlyReport(user, now);
            EmailDto email = new EmailDto(user.getEmail(), "Miesięczny raport treningów", report);
            emailSender.send(email);
        });
    }
}