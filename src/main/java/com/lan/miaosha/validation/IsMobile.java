package com.lan.miaosha.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsMobileValidator.class}) //调用这个校验器来进行验证 ， 该校验类需要继承ConstraintValidator
public @interface IsMobile {

    boolean required() default false;

    String message() default "手机号码有误！！！"; //如果校验不通过， 显示的信息

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
