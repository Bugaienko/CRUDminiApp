package org.example.utils;

/**
 * @author Sergii Bugaienko
 */

import org.example.crudProject.Employee;
import org.example.crudProject.EmployeeFields;
import org.example.crudProject.PositionEnum;

import java.util.List;
import java.util.Scanner;

public class DataUtil {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static String getString(String prompt) {
        System.out.println(prompt);
        String str = SCANNER.next();
        return str;
    }

    public static int getInt(String prompt) {
        System.out.println(prompt);
        int num = SCANNER.nextInt();
        return num;
    }

    public static void printListColumn(List<Employee> employees) {
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    public static Employee createEmployee(String prompt) {
        System.out.println(prompt);
        String name = SCANNER.next();
        String position = SCANNER.next();
        int salary = SCANNER.nextInt();
        int age = SCANNER.nextInt();
        EmployeeFields employeeFields = new EmployeeFields(name, position, salary, age);
        return createNewEmployee(employeeFields);
    }

    private static Employee createNewEmployee(EmployeeFields eF) {
        return new Employee(eF.getName(), eF.getPosition(), eF.getSalary(), eF.getAge());
    }
    private static Employee createNewEmployee(String name, String position, int salary, int age) {
        return new Employee(name, position, salary, age);
    }

    public static EmployeeFields createEmployeePart(String prompt) {
        System.out.println(prompt);
        String position = SCANNER.next();
        int salary = SCANNER.nextInt();
        int age = SCANNER.nextInt();

        return new EmployeeFields(null, position, salary, age);
//        return createNewEmployee(employeeFields);
    }

    private static PositionEnum getPosition() {
        System.out.println("Get Position");
        String value = SCANNER.next();
        try {
            PositionEnum position = PositionEnum.valueOf(value.toUpperCase());
            return position;
        } catch (IllegalArgumentException e) {
            System.out.println("Error! Position not found");
            return null;
        }
    }
}
