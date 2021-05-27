package com.emrerenjs.bitidea.Error;

import com.emrerenjs.bitidea.Model.Error.ErrorResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest webRequest){
        System.out.println(exception.getMessage());
        System.out.println("------------------");
        exception.printStackTrace();
        System.out.println("------------------");
        ErrorResponseModel errorResponseModel = new ErrorResponseModel(500,"Sunucu Hatası","Sunucuda beklenmeyen bir hata meydana geldi..");
        return new ResponseEntity<>(errorResponseModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<Object> handleBadCredentialsException(Exception exception, WebRequest webRequest){
        ErrorResponseModel errorResponseModel = new ErrorResponseModel(401,"Hatalı Kimlik",exception.getMessage());
        return new ResponseEntity<>(errorResponseModel, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UniqueConstraintException.class)
    public final ResponseEntity<Object> handleUniqueConstraintException(Exception exception, WebRequest webRequest){
        ErrorResponseModel errorResponseModel = new ErrorResponseModel(500,"Mevcut kayıt",exception.getMessage());
        return new ResponseEntity<>(errorResponseModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleRecordNotFoundException(Exception exception,WebRequest webRequest){
        ErrorResponseModel errorResponseModel =  new ErrorResponseModel(404,"Kayıt bulunamadı","Aradığınız veya işlem yapmak istediğiniz kayıt veri tabanında bulunamadı!");
        return new ResponseEntity<>(errorResponseModel,HttpStatus.NOT_FOUND);
    }


}
