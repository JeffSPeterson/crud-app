package com.aquent.crudapp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aquent.crudapp.data.dao.UserDao;
import com.aquent.crudapp.domain.User;

/**
 * Default implementation of {@link UserService}.
 */
public class DefaultUserService implements UserService {

	private UserDao userDao;
	private Validator validator;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<User> listUsers() {
		return userDao.listUsers();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public User readUser(Integer id) {
		return userDao.readUser(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public Integer createUser(User user) {
		return userDao.createUser(user);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public void deleteUser(Integer id) {
		userDao.deleteUser(id);
	}

	@Override
	public List<String> validateUser(User user) {
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		List<String> errors = new ArrayList<String>(violations.size());
		for (ConstraintViolation<User> violation : violations) {
			errors.add(violation.getMessage());
		}
		Collections.sort(errors);
		return errors;
	}
	
	@Override
	public User validateUserLogin(User user) {
		User result = userDao.validateUserLogin(user.getUsername(), user.getPassword());
		if(result != null){
			return result;
		}
			return null;
	}
}
