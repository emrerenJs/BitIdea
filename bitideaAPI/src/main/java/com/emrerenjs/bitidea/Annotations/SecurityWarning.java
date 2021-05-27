package com.emrerenjs.bitidea.Annotations;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface SecurityWarning {
    String message() default "Güvenlik ihlali oluşturacak bir test fonksiyonudur. Daha sonrasında güvenli hale getirilmeli veya silinmelidir.";
}
