package org.ecom.authservice.repository;

public record Query(String sql, Object[] params) {}
