package org.ecom.authservice.repository;

import org.ecom.authservice.model.UserDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDetailRowMapper implements RowMapper<UserDetail> {

    @Override
    public UserDetail mapRow(ResultSet rs, int rowNum) throws SQLException {

        UserDetail user = new UserDetail();

        user.setId(rs.getLong("id"));
        user.setUserName(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        //user.setPassword(rs.getString("password"));
        user.setUserType(rs.getString("user_type_id"));
        user.setIsActive(rs.getBoolean("is_active"));
        user.setPassword(rs.getString("password_hash"));

        return user;
    }
}

