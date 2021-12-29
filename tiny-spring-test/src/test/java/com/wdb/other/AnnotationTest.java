package com.wdb.other;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;

@Retention(RetentionPolicy.RUNTIME)
@interface A{
}

@A
@Retention(RetentionPolicy.RUNTIME)
@interface B{
}


public class AnnotationTest {

    @B
    public static class Data{

    }

    public static void main(String[] args) {

        if(Data.class.isAnnotationPresent(B.class)){
            System.out.println("B");
        }
        if(Data.class.isAnnotationPresent(A.class) ){
            System.out.println("A");
        }

        for (Annotation annotation : Data.class.getAnnotations()) {
            annotation.annotationType().isAnnotationPresent(A.class);
        }

    }
}
