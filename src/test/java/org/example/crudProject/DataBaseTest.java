package org.example.crudProject;

import org.example.utils.DataBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Sergii Bugaienko
 */

// В приложение мной была добавлена новая сущность EmployeeFields, являющаяся по сути копией  Employee по содержащимся полям,
// но сбивающая счетчик id при ее создании.

//    Вторая проблема моего теста. Он работает с "базой данных". Протестировав, например, update Employee -
//    при следующем запуске в базе уже перезаписан обновленный Employee. Наверное, это не корректное поведение, когда тест меняет базу?

public class DataBaseTest {
    private static List<Employee> employees;
    private static DataBase dataBase;


    @BeforeAll
    public static void init() {
        employees = setEmployeesListForTest();
        dataBase = new DataBase(employees);
    }

    @ParameterizedTest
    @MethodSource("dataForSearchingById")
    public void testFindById(Employee target, int id, boolean result) {
        Employee findedEmployee = dataBase.findById(id);
        Assertions.assertEquals(result, target.equals(findedEmployee));
    }

    public static Stream<Arguments> dataForSearchingById() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(employees.get(0), 1, true));
        out.add(Arguments.arguments(employees.get(5), 4, false));
        out.add(Arguments.arguments(new Employee("Serg", "test", 1500, 59), 1, false));
        out.add(Arguments.arguments(new Employee("Serg", "test", 1500, 59), 100, false));
        return out.stream();
    }

    @ParameterizedTest
    @MethodSource("dataForUpdateEmployeeTest")
    public void updateEmployeeTest (Employee employee, EmployeeFields eF, EmployeeFields result, boolean[] results) {
        dataBase.updateByFields(employee, eF);
        System.out.println(employee);
        Assertions.assertEquals(results[0], employee.getName().equals(result.getName()));
        Assertions.assertEquals(results[1], employee.getPosition().equals(result.getPosition()));
        Assertions.assertEquals(results[2], employee.getSalary() == result.getSalary());
        Assertions.assertEquals(results[3], employee.getAge() == (result.getAge()));
    }

    public static Stream<Arguments> dataForUpdateEmployeeTest() {
        List<Arguments> out = new ArrayList<>();
        EmployeeFields eF = new EmployeeFields(null, "BigBoss", 2001, 44);
        Employee employee = employees.get(0);
        EmployeeFields result = new EmployeeFields(employee.getName(), eF.getPosition(), eF.getSalary(), eF.getAge());
        boolean[] results = {true, true, true, true};
        out.add(Arguments.arguments(employee, eF, result, results));

        EmployeeFields ef1 = new EmployeeFields("Sergey", "Small Boss", 2002, 28);
        Employee employee1 = employees.get(1);
        EmployeeFields result1 = new EmployeeFields("Sergey", "Small Boss", 2002, 28);
        boolean[] results1 = {true, true, true, true};
        out.add(Arguments.arguments(employee1, ef1, result1, results1));

        EmployeeFields eF2 = new EmployeeFields("Sergey", "", -5, 31);
        Employee employee2 = employees.get(2);
        EmployeeFields result2 = new EmployeeFields("Sergey", employee2.getPosition(), employee2.getSalary(), 31);
        boolean[] results2 = {true, true, true, true};
        out.add(Arguments.arguments(employee2, eF2, result2, results2));

        return out.stream();
    }

    // Второй вариант теста, без использование объекта с верными результати.
    @ParameterizedTest
    @MethodSource("dataForUpdateEmployeeTestV2")
    public  void  updateEmployeeTestV2 (Employee employee, EmployeeFields eF, boolean[] results) {
        dataBase.updateByFields(employee, eF);
        Assertions.assertEquals(results[0], employee.getName().equals(eF.getName()));
        Assertions.assertEquals(results[1], employee.getPosition().equals(eF.getPosition()));
        Assertions.assertEquals(results[2], employee.getSalary() == eF.getSalary());
        Assertions.assertEquals(results[3], employee.getAge() == eF.getAge());
    }

    public static Stream<Arguments> dataForUpdateEmployeeTestV2() {
        List<Arguments> out = new ArrayList<>();
        EmployeeFields eF = new EmployeeFields(null, "BigBoss", 2001, 44);
        Employee employee = employees.get(0);
        boolean[] results = {false, true, true, true};
        out.add(Arguments.arguments(employee, eF, results));

        EmployeeFields ef1 = new EmployeeFields("Sergey", "Small Boss", 2002, 28);
        Employee employee1 = employees.get(1);
        boolean[] results1 = {true, true, true, true};
        out.add(Arguments.arguments(employee1, ef1,  results1));

        EmployeeFields eF2 = new EmployeeFields("Sergey", "", -5, 31);
        Employee employee2 = employees.get(2);
        boolean[] results2 = {true, false, false, true};
        out.add(Arguments.arguments(employee2, eF2,  results2));

        return out.stream();
    }

    private static List<Employee> setEmployeesListForTest() {
        List<Employee> employees1 = new ArrayList<>();
        employees1.add(new Employee("John", "Boss", 2000, 45));
        employees1.add(new Employee("Gina", "Assistant", 950, 21));
        employees1.add(new Employee("Svetlana", "CafeMaker", 950, 20));
        employees1.add(new Employee("Johanna", "Front-end prog", 1700, 25));
        employees1.add(new Employee("Tomas", "Back-end prog", 1800, 28));
        employees1.add(new Employee("Tomara", "Back-end prog", 1750, 23));
        employees1.add(new Employee("John", "Assistant", 1500,24));
        return employees1;
    }

}
