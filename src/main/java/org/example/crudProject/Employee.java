package org.example.crudProject;

import java.util.Arrays;

/**
 * @author Sergii Bugaienko
 */

public class Employee {
    private String name;
    private String position;
    private int salary;
    private int age;
    private int id;
    private static int currentId;

    public Employee(String name, String position, int salary, int age) {
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.age = age;
        this.id = ++currentId;
    }

    public Employee(String[] args) {
        if (args.length == 4) {
            this.name = args[0];
            this.position = args[1];
            this.salary = Integer.parseInt(args[2]);
            this.age = Integer.parseInt(args[3]);
            this.id = ++currentId;
        }

    }

    public String getName() {
        return name;
    }

    public void update(String position, int salary, int age) {
        this.position = position;
        this.salary = salary;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "" + id + " {" +
                "name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", age=" + age +
                '}';
    }

}
