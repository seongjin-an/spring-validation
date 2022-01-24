package com.black.space.dto;

import com.black.space.annotation.YearMonth;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class User {
    @NotBlank
    private String name;
    @Min(value = 0)
    private int age;
    @Email
    private String email;
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "failed to match with pattern!")
    private String phoneNumber;
//    @Size(min = 6, max = 6)
    @YearMonth
    private String reqYearMonth;
    @Valid
    private List<Car> cars;

//    @AssertTrue(message = "failed to match with pattern: yyyyMM")
//    public boolean isReqYearMonthValidation(){
//        System.out.println("assert true call");
////        this.reqYearMonth = getReqYearMonth() + "01";
//        try {
//            LocalDate localDate = LocalDate.parse(getReqYearMonth() + "01", DateTimeFormatter.ofPattern("yyyyMMdd"));
//        }catch(Exception error){
//            return false;
//        }
//        return true;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getReqYearMonth() {
        return reqYearMonth;
    }

    public void setReqYearMonth(String reqYearMonth) {
        this.reqYearMonth = reqYearMonth;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", reqYearMonth='" + reqYearMonth + '\'' +
                ", cars=" + cars +
                '}';
    }
}
