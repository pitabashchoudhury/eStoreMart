package org.ecom.authservice.repository;

import org.ecom.authservice.model.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 🔹 Find user_type.id by code (ADMIN / CUSTOMER)
    public Integer findUserTypeId(String code) {
        String sql = """
                  SELECT id
                  FROM auth.user_type
                  WHERE code = ?
                    AND is_active = true
                """;

        return jdbcTemplate.queryForObject(sql, Integer.class, code);
    }

    // 🔹 Insert user
    public Integer createUser(
            String username,
            String passwordHash,
            String email,
            Integer userTypeId,
            String createdBy
    ) {
        String sql = """
                  INSERT INTO auth.users
                  (username, password_hash, email, user_type_id, created_by)
                  VALUES (?, ?, ?, ?, ?)
                """;

        return jdbcTemplate.update(
                sql,
                username,
                passwordHash,
                email,
                userTypeId,
                createdBy
        );
    }

    // 🔹 Check existing username/email
    public boolean existsByUsernameOrEmail(String username, String email) {
        String sql = """
                  SELECT COUNT(*)
                  FROM auth.users
                  WHERE username = ? OR email = ?
                """;

        Integer count = jdbcTemplate.queryForObject(
                sql, Integer.class, username, email);

        return count != null && count > 0;
    }

    public Optional<UserDetail> getUserDetail(String email) {

        Query query = SqlQueryBuilder
                .from("auth.users")
                .select("id", "username", "email","password_hash", "user_type_id", "is_active")
                .where("email = ?", email)
                .build();

        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            query.sql(),
                            new UserDetailRowMapper(),
                            query.params()
                    )
            );
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

}
