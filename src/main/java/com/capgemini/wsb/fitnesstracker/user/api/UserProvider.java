package com.capgemini.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserProvider {

    /**
     * Retrieves a user based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param userId id of the user to be searched
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    Optional<User> getUser(Long userId);

    /**
     * Retrieves a user based on their email.
     * If the user with given email is not found, then {@link Optional#empty()} will be returned.
     *
     * @param email The email of the user to be searched
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Retrieves a list of users whose email contains the specified partial email string.
     *
     * @param emailPartial the partial email string to search for
     * @return a list of users whose email contains the specified partial email string
     */
    List<User> getUsersByEmailPartial(String email);

    /**
     * Retrieves a list of users who are older than the specified age.
     *
     * @param age the age to compare against
     * @return a list of users who are older than the specified age
     */
    List<User> getUsersOlderThan(LocalDate time);

    /**
     * Retrieves all users.
     *
     * @return An {@link Optional} containing the all users,
     */
    List<User> findAllUsers();

}
