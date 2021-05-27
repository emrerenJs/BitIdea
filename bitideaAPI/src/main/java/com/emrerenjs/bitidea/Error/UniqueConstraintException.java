package com.emrerenjs.bitidea.Error;

public class UniqueConstraintException extends RuntimeException{

    public UniqueConstraintException(String errorMessage){
        super(errorMessage);
    }

}
