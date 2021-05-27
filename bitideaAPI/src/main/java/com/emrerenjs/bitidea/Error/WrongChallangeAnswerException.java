package com.emrerenjs.bitidea.Error;

public class WrongChallangeAnswerException extends RuntimeException{
    public WrongChallangeAnswerException(String errorMessage){
        super(errorMessage);
    }
}
