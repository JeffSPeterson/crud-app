package com.aquent.crudapp.data.dao;

import java.util.List;

import com.aquent.crudapp.domain.User;

/**
 * Operations on the "user" table.
 */
public interface UserDao {

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
     * Validates User Login
     *
     * @param username the user's username
     * @param password the user's password
     * @return boolean found
     */
    User validateUserLogin(String username, String password);

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
}
