package com.chzzkzzal.zzal.domain.zzal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@NotEmpty(message = "제목은 필수 입력 항목입니다.")
@Size(min = 1, max = 30, message = "제목은 1자 이상 30자 이하로 입력해주세요.")
public @interface ValidTitle {
	String message() default "제목 형식이 올바르지 않습니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
