package com.workflow.exception;

public class UnAuthoriseException extends Exception {

    private static final long serialVersionUuid = 4336138446385716141L;

    public UnAuthoriseException(){
        super();
    }
    public UnAuthoriseException(String message){
        super(message);
    }

}
