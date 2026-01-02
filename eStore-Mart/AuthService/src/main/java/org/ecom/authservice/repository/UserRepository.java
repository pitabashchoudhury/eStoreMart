package org.ecom.authservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
    public void createUser(
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

        jdbcTemplate.update(
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

}
