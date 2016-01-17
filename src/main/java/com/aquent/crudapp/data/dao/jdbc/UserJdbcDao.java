package com.aquent.crudapp.data.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aquent.crudapp.data.dao.UserDao;
import com.aquent.crudapp.domain.User;

/**
 * Spring JDBC implementation of {@link UserDao}.
 */
public class UserJdbcDao implements UserDao {

    private static final String SQL_LIST_USERS = "SELECT * FROM user ORDER BY username, password, user_id";
    private static final String SQL_READ_USER = "SELECT * FROM user WHERE user_id = :userId";
    private static final String SQL_VALIDATE_USER_LOGIN = "SELECT * FROM user WHERE username = :username AND password = :password";
    private static final String SQL_DELETE_USER = "DELETE FROM user WHERE user_id = :userId";
    private static final String SQL_UPDATE_USER = "UPDATE user SET (username, password, email_address)"
                                                  + " = (:username, :password, :emailAddress)"
                                                  + " WHERE user_id = :userId";
    private static final String SQL_CREATE_USER = "INSERT INTO user (username, password, email_address)"
                                                  + " VALUES (:username, :password, :emailAddress)";
    
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<User> listUsers() {
        return namedParameterJdbcTemplate.getJdbcOperations().query(SQL_LIST_USERS, new UserRowMapper());
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User readUser(Integer userId) {
        return namedParameterJdbcTemplate.queryForObject(SQL_READ_USER, Collections.singletonMap("userId", userId), new UserRowMapper());
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public void deleteUser(Integer userId) {
        namedParameterJdbcTemplate.update(SQL_DELETE_USER, Collections.singletonMap("userId", userId));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public void updateUser(User user) {
        namedParameterJdbcTemplate.update(SQL_UPDATE_USER, new BeanPropertySqlParameterSource(user));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public Integer createUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(SQL_CREATE_USER, new BeanPropertySqlParameterSource(user), keyHolder);
        return keyHolder.getKey().intValue();
    }
    
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public User validateUserLogin(String username, String password) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("username", username)
				.addValue("password",password);
		try{
			return namedParameterJdbcTemplate.queryForObject(SQL_VALIDATE_USER_LOGIN, parameters, new UserRowMapper());
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

    /**
     * Row mapper for user records.
     */
    private static final class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmailAddress(rs.getString("email_address"));
            return user;
        }
    }

}
