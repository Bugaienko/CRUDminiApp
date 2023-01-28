package org.example.crudProject;

/**
 * @author Sergii Bugaienko
 */

public class EmployeeFields {
    private String name;
    private String position;
    private int salary;
    private int age;

    public EmployeeFields(String name, String position, int salary, int age) {
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public int getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }
}
