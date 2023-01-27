package org.example.utils;

import org.example.crudProject.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Sergii Bugaienko
 */

public class DbInit {
    public static List<Employee> init() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("John", "Boss", 2000, 45));
        employees.add(new Employee("Gina", "Assistant", 950, 21));
        employees.add(new Employee("Svetlana", "CafeMaker", 950, 20));
        employees.add(new Employee("Johanna", "Front-end prog", 1700, 25));
        employees.add(new Employee("Tomas", "Back-end prog", 1800, 28));
        employees.add(new Employee("Tomara", "Back-end prog", 1750, 23));
        employees.add(new Employee("John", "Assistant", 1500,24));

        return employees;
    }

    public static List<Employee> createBigRandomList(int size) {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String name = "";
            String position = "";
            int salary = new Random().nextInt(2000) + 500;
            int age = new Random().nextInt(50) + 15;
            for (int j = 0; j < 6; j++) {
                char tmp = (char) (new Random().nextInt(25)+ 65);
                char tmp2 = (char) (new Random().nextInt(25) + 65);
                name += tmp;
                position += tmp2;
            }
            employees.add(new Employee(name, position, salary, age));
        }
        return employees;
    }
}
