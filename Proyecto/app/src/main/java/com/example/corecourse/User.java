package com.example.corecourse;

public class User {

    private int age;
    private String gender, name, surname;

    public User() {
        this(0, "", "", "");
    }
    public User(int age, String gender, String name, String surname) {
        this.age = age;
        this.gender = gender;
        this.name = name;
        this.surname = surname;
    }

    // Getters y setters de toda la vida
    public int getAge() {
        return age;
    }
    public String getGender() {
        return gender;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Usuario: " + name + " " + surname +
                ".\nGénero: " + gender +
                ".\nEdad: " + age;
    }

}
