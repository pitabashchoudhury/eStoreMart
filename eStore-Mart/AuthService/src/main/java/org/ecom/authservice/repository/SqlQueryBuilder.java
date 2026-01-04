package org.ecom.authservice.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;


public class SqlQueryBuilder {

    private final String table;
    private final List<String> columns = new ArrayList<>();
    private final List<String> whereClauses = new ArrayList<>();
    private final List<Object> params = new ArrayList<>();
    private String orderBy;
    private Integer limit;

    private SqlQueryBuilder(String table) {
        this.table = table;
    }

    public static SqlQueryBuilder from(String table) {
        return new SqlQueryBuilder(table);
    }

    public SqlQueryBuilder select(String... cols) {
        columns.addAll(List.of(cols));
        return this;
    }

    public SqlQueryBuilder where(String condition, Object value) {
        whereClauses.add(condition);
        params.add(value);
        return this;
    }

    public SqlQueryBuilder orderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public SqlQueryBuilder limit(int limit) {
        this.limit = limit;
        return this;
    }

    public Query build() {

        StringJoiner sql = new StringJoiner(" ");

        sql.add("SELECT")
                .add(columns.isEmpty() ? "*" : String.join(", ", columns))
                .add("FROM")
                .add(table);

        if (!whereClauses.isEmpty()) {
            sql.add("WHERE")
                    .add(String.join(" AND ", whereClauses));
        }

        if (orderBy != null) {
            sql.add("ORDER BY " + orderBy);
        }

        if (limit != null) {
            sql.add("LIMIT " + limit);
        }

        return new Query(sql.toString(), params.toArray());
    }
}

