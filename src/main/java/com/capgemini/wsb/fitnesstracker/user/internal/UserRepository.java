package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                        .filter(user -> Objects.equals(user.getEmail(), email))
                        .findFirst();
    }

    /**
     * Finds and returns a list of users whose email contains the specified partial email string.
     *
     * @param emailPartial the partial email string to search for
     * @return a list of users whose email contains the specified partial email string
     */
    default List<User> findByEmailPartial(String email) {
        return findAll().stream()
                        .filter(user -> user.getEmail().toLowerCase().contains(email.toLowerCase()))
                        .toList();
    }

    /**
     * Finds and returns a list of users who are older than the specified age.
     *
     * @param age the age to compare against
     * @return a list of users who are older than the specified age
     */
    default List<User> findUsersOlderThan(LocalDate time) {
        return findAll().stream()
                        .filter(user -> user.getBirthdate().isBefore(time))
                        .toList();
    }

}
