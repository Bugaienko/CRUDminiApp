package org.example.crudProject;

import java.io.Serializable;


/**
 * @author Sergii Bugaienko
 */

public class Employee implements Serializable {
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
//        System.out.println("Создан 0 Empl");
    }

    public Employee(EmployeeFields ef) {
        this.name = ef.getName();
        this.position = ef.getPosition();
        this.salary = ef.getSalary();
        this.age = ef.getAge();
        this.id = ++currentId;
    }

    public Employee(String[] args) {
        if (args.length == 4) {
            this.name = args[0];
            this.position = args[1];
            this.salary = Integer.parseInt(args[2]);
            this.age = Integer.parseInt(args[3]);
            this.id = ++currentId;
//            System.out.println("Создан Empl STR " + this.id);
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

    public void update(EmployeeFields eF) {
        if (eF.getName() != null && eF.getName().length() > 0 ) {
            this.name = eF.getName();
        }
        if (eF.getPosition() != null && eF.getPosition().length() > 0) {
            this.position = eF.getPosition();
        }
        if (eF.getSalary() > 0) {
            this.salary = eF.getSalary();
        }
        if (eF.getAge() > 0) {
            this.age = eF.getAge();
        }

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

    public static void setCurrent(int id) {
        currentId = id;
    }

}
