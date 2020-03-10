package com.example.viewbindingmeetsrxjava.pojo;

public class Person {

    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        return new Person(name, age);
    }

    public int getAge() {
        return age;
    }

    public Person setAge(int age) {
        return new Person(name, age);
    }
}
