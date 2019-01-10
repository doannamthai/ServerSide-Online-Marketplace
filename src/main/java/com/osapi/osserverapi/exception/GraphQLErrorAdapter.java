package com.osapi.osserverapi.exception;


import graphql.ErrorType;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

public class GraphQLErrorAdapter implements GraphQLError{

    private GraphQLError error;

    public GraphQLErrorAdapter(GraphQLError error) {
        this.error = error;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return error.getErrorType();
    }


    @Override
    public String getMessage() {
        return (error instanceof ExceptionWhileDataFetching) ? ((ExceptionWhileDataFetching) error).getException().getMessage() : error.getMessage();
    }
}
