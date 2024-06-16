package com.omni.minecraft.database.query;

public class QueryBuilder {

    private QueryStatementType statementType;

    public QueryBuilder() {}

    public static QueryBuilder builder() {
        return new QueryBuilder();
    }
}
