package com.black.space.validator;

import com.black.space.annotation.YearMonth;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class YearMonthValidator implements ConstraintValidator<YearMonth, String> {
    private String pattern;

    @Override
    public void initialize(YearMonth constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //yyyyMM
        System.out.println("isValid true call");
//        this.reqYearMonth = getReqYearMonth() + "01";
        try {
            LocalDate localDate = LocalDate.parse(value.concat("01"), DateTimeFormatter.ofPattern(this.pattern));
        }catch(Exception error){
            return false;
        }
        return true;
    }
}
