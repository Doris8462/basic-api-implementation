package com.thoughtworks.rslist.domain;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class User {
@Size(max = 8)
@NotNull
    private String name;
@NotNull
    private String gender;
@Min(18)
@Max(100)
    private int age;
@Email
    private String email;
@Pattern(regexp = "1\\d{10}")
    private String phone;
    private int vote=10;

    public User(){
    }

    public User(String name, int age, String gender, String email, String phone){
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
    }

    public String getUserName() {
        return name;
    }

    public void setUserName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }
}
