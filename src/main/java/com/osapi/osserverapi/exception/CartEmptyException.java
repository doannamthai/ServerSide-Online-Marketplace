package com.osapi.osserverapi.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartEmptyException extends RuntimeException implements GraphQLError {
    private Map<String, Object> extensions = new HashMap<>();

    public CartEmptyException(String message, Long cart_id) {
        super(message);
        extensions.put("invalid cart_id", cart_id);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return extensions;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DataFetchingException;
    }
}
