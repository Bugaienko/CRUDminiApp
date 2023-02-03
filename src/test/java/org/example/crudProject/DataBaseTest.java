package org.example.crudProject;

import org.example.utils.DataBase;
import org.example.utils.DataUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Sergii Bugaienko
 */

// В приложение мной была добавлена новая сущность EmployeeFields, являющаяся по сути копией  Employee по содержащимся полям,
// но не затрагивающая счетчик id при ее создании.

//    Вторая проблема моего теста. Он работает с "базой данных". Протестировав, например, update Employee -
//    при следующем запуске в базе уже перезаписан обновленный Employee. Наверное, это не корректное поведение, когда тест меняет базу?
//TODO вынести сохранение БД из методов, которые нужно тестировать
// DONE

    //Третье проблема. Не поддаются тестированию методы, коотрые помечены как приватные. Можно ли как-то обойти? Не хочется делать методы публичными...


    // Проюлема 4. Тесты меняют список сотрудников. Например, тест на апдейт затрагивает возраст.
// И в последующем тесте на выборку по возрасту тяжело прописать "правильные результаты
    //TODO написать метод восстанавливающий "базу" перед каждым тестом
    // DONE

public class DataBaseTest {
    private static List<Employee> employees;
    private static DataBase dataBase;


    @BeforeAll
    public static void init() {
        employees = setEmployeesListForTest();
        dataBase = new DataBase(employees);
    }




    @BeforeEach
    public void restoreEmployeesList() {
        List<EmployeeFields> baseFields = startingEmployeesParams();
        Predicate<Integer> moreThen = i -> (i <= 7);
        employees = employees.stream().filter(e -> moreThen.test(e.getId())).collect(Collectors.toList());
        int minLength = Math.min(employees.size(), baseFields.size());
        for (int i = 0; i < minLength; i++) {
            dataBase.updateByFields(employees.get(i), baseFields.get(i));
        }
    }

    @ParameterizedTest
    @MethodSource("dataForSearchingById")
    public void testFindById(Employee target, int id, boolean result) {
        Employee findedEmployee = dataBase.findById(id);
        Assertions.assertEquals(result, target.equals(findedEmployee));
    }

    @ParameterizedTest
    @MethodSource("dataForUpdateEmployeeTest")
    public void updateEmployeeTest (Employee employee, EmployeeFields eF, EmployeeFields result, boolean[] results) {
        dataBase.updateByFields(employee, eF);
//        System.out.println(employee);
        Assertions.assertEquals(results[0], employee.getName().equals(result.getName()));
        Assertions.assertEquals(results[1], employee.getPosition().equals(result.getPosition()));
        Assertions.assertEquals(results[2], employee.getSalary() == result.getSalary());
        Assertions.assertEquals(results[3], employee.getAge() == (result.getAge()));
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

    @ParameterizedTest
    @MethodSource("dataForCreateNewEmployeeTest")
    public void createNewEmployeeTest(EmployeeFields eFTarget) {
        int lastId = employees.get(employees.size()-1).getId();
        Employee newEmployee = DataUtil.createNewEmployee(eFTarget);
//        Employee.setCurrent(newEmployee.getId()-1);
        Assertions.assertTrue(lastId < newEmployee.getId(), "Id passed");
        Assertions.assertEquals(newEmployee.getName(), eFTarget.getName(), "Name passed");
        Assertions.assertEquals(newEmployee.getPosition(), eFTarget.getPosition(), "Position passed");
        Assertions.assertEquals(newEmployee.getSalary(), eFTarget.getSalary(), "Salary passed");
        Assertions.assertEquals(newEmployee.getAge(), eFTarget.getAge(), "Age passed");
    }

    @Test
    public void createNullNewEmployeeTest() {
        EmployeeFields efNull = null;
        Assertions.assertThrows(NullPointerException.class, () -> DataUtil.createNewEmployee(efNull));
    }

    @ParameterizedTest
    @MethodSource("dataForDeleteSearch")
    public void deleteTest(int idSearch, boolean result){
        Employee employeeSearch = dataBase.findById(idSearch);
        Assertions.assertEquals(result, dataBase.delete(employeeSearch) == idSearch);
    }

    @ParameterizedTest
    @MethodSource("dataForDeleteNullSearch")
    public void deleteNullTest(int idSearch) {
        Employee employeeSearch = dataBase.findById(idSearch);
        Assertions.assertThrows(NullPointerException.class, () -> dataBase.delete(employeeSearch));
    }



    @ParameterizedTest
    @MethodSource("dataForSearchMinAgeTest")
    public void searchMinAgeTest(int minAge, int rightLength, List<Employee> rightList) {
        List<Employee> result = dataBase.searchMinAge(minAge, employees);
        Assertions.assertEquals(result.size(), rightLength);
        Assertions.assertEquals(result, rightList, "Lists are equals");
    }

    public static Stream<Arguments> dataForSearchingById() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(employees.get(0), 1, true));
        out.add(Arguments.arguments(employees.get(5), 4, false));
        out.add(Arguments.arguments(new Employee("Serg", "test", 1500, 59), 1, false));
        out.add(Arguments.arguments(new Employee("Serg", "test", 1500, 59), 100, false));
        return out.stream();
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



    public static Stream<Arguments> dataForSearchMinAgeTest() {
        List<Arguments> out = new ArrayList<>();
        int minAge = 22;
        int rightLength = 5;
        List<Employee> rightList = new ArrayList<>();
        rightList.add(employees.get(0));
        rightList.add(employees.get(3));
        rightList.add(employees.get(4));
        rightList.add(employees.get(5));
        rightList.add(employees.get(6));
        out.add(Arguments.arguments(minAge, rightLength, rightList));

        int minAge2 = 99;
        int rightLength2 = 0;
        List<Employee> rightList2 = new ArrayList<>();

        out.add(Arguments.arguments(minAge2, rightLength2, rightList2));

        return out.stream();
    }

    public static Stream<Arguments> dataForCreateNewEmployeeTest() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new EmployeeFields("TestCreat", "tester", 1455, 18)));
        out.add(Arguments.arguments(new EmployeeFields("Test Creat", "tester boss", 2800, 25)));
        out.add(Arguments.arguments(new EmployeeFields("Test Creat", null, 2800, 25)));

        return out.stream();
    }

    public static Stream<Arguments> dataForDeleteSearch() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(7, true));

        return out.stream();
    }

    public static Stream<Arguments> dataForDeleteNullSearch() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(99, false));

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
        System.out.println("Employees list for Tests:");
        DataUtil.printListColumn(employees1);
        return employees1;
    }

    private static List<EmployeeFields> startingEmployeesParams() {
        List<EmployeeFields> employeeFields = new ArrayList<>();
        employeeFields.add(new EmployeeFields("John", "Boss", 2000, 45));
        employeeFields.add(new EmployeeFields("Gina", "Assistant", 950, 21));
        employeeFields.add(new EmployeeFields("Svetlana", "CafeMaker", 950, 20));
        employeeFields.add(new EmployeeFields("Johanna", "Front-end prog", 1700, 25));
        employeeFields.add(new EmployeeFields("Tomas", "Back-end prog", 1800, 28));
        employeeFields.add(new EmployeeFields("Tomara", "Back-end prog", 1750, 23));
        employeeFields.add(new EmployeeFields("John", "Assistant", 1500,24));

        return employeeFields;
    }

}
