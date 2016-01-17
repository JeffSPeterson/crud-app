package com.aquent.crudapp.service;

import java.util.List;

import com.aquent.crudapp.domain.User;

/**
 * User operations.
 */
public interface UserService {

    /**
     * Retrieves all of the user records.
     *
     * @return list of user records
     */
    List<User> listUsers();

    /**
     * Creates a new user record.
     *
     * @param user the values to save
     * @return the new user ID
     */
    Integer createUser(User user);

    /**
     * Retrieves a user record by ID.
     *
     * @param id the user ID
     * @return the user record
     */
    User readUser(Integer id);

    /**
     * Updates an existing user record.
     *
     * @param user the new values to save
     */
    void updateUser(User user);

    /**
     * Deletes a user record by ID.
     *
     * @param id the user ID
     */
    void deleteUser(Integer id);

    /**
     * Validates populated user data.
     *
     * @param user the values to validate
     * @return list of error messages
     */
    List<String> validateUser(User user);
    
    /**
     * Validates user login.
     *
     * @param user the values to validate
     * @return user
     */
    User validateUserLogin(User user);
}
