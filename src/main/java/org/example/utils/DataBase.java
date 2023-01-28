package org.example.utils;

/**
 * @author Sergii Bugaienko
 */


import org.example.crudProject.Employee;
import org.example.crudProject.EmployeeFields;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DataBase {
    private final Scanner SCANNER = new Scanner(System.in);
    private final List<Employee> employees;
    private final Map<Integer, Employee> indexMap;

    public DataBase(List<Employee> employees) {
        this.employees = new ArrayList<>();
        this.indexMap = new HashMap<>();

//        employees.stream().forEach(employee -> {
//            this.employees.add(employee);
//            indexMap.put(employee.getId(), employee);
//        });

        if (!employees.isEmpty()) {
            int lastId = 0;
            for (Employee employee : employees) {
                this.employees.add(employee);
                lastId = employee.getId();
                indexMap.put(employee.getId(), employee);
            }
            Employee.setCurrent(lastId);
        }

    }

    public void search() {
        System.out.println("# Search menu");
        System.out.println("Выборки по возрасту: (1)");
        System.out.println("Поиск по должности: (2)");
        System.out.println("Выборки по зп: (3)");
        System.out.println("Поиск по имени: (4)");
        System.out.println("Поиск по нескольким условиям: (5)");
        System.out.println("Тест random List: (6)");
        System.out.println("Выход: (9)");
        int choose = SCANNER.nextInt();
        switch (choose) {
            case 1:
                searchByAge();
                break;
            case 2:
                searchByPosition();
                break;
            case 3:
                searchBySalary();
                break;
            case 4:
                searchByName();
                break;
            case 5:
                searchMulti();
                break;

            case 9:
                System.out.println("Exit ended");
                return;
            default:
                System.out.println("возраст-> 1; Должность-> 2, ЗП-> 3, Имя-> 4, Multi-> 5; Выход-> 9");
        }
    }

    private void testByIdEnumeration(List<Employee> list, Map<Integer, Employee> map, int[] arr) {
        long startTime = System.currentTimeMillis();
        for (int j = 0; j < arr.length; j++) {
            for (Employee employee : list) {
                if (employee.getId() == j) {
                    Employee temp = employee;
//                    System.out.print(employee);
                }
            }
        }
//        System.out.println();
        System.out.println("ArrayList time (ms): " + (System.currentTimeMillis() - startTime));
    }

    public void create() {
        Employee employee = DataUtil.createEmployee("Create new Employee");
        employees.add(employee);
        DbInit.saveDb(employees);
        indexMap.put(employee.getId(), employee);
    }

    public void read() {
        DataUtil.printListColumn(employees);
    }

    public void update() {
        int searchId = DataUtil.getInt("find by id: ");
        Employee employee = findById(searchId);
        if (employee != null) {
            EmployeeFields tmpEmpl = DataUtil.createEmployeePart("Update: (position, salary, age ");
            updateByFields(employee, tmpEmpl);
            System.out.println("Updated " + employee);
            DbInit.saveDb(employees);
        }
    }
    public void updateByFields(Employee employee, EmployeeFields eF) {
        if (employee != null) {
            employee.update(eF);
        }
    }

    public Employee findById(int id) {
        return indexMap.get(id);
    }

    public void delete() {
        int searchId = DataUtil.getInt("Delete by id: ");
        Employee employee = findById(searchId);
        if (employee != null) {
            employees.remove(employee);
            indexMap.remove(employee.getId());
            DbInit.saveDb(employees);
            System.out.println("Deleted " + employee);
        }
    }


    private Employee findByName(String name) {
        for (Employee employee : employees) {
            if (employee.getName().equalsIgnoreCase(name)) {
                return employee;
            }
        }
        return null;
    }


    // MultiSearch section
    private List<Employee> searchMulti() {
        System.out.println("# Search combo menu");
        System.out.println("Должность + возраст: (1)");
        System.out.println("Должность + зп: (2)");
        System.out.println("Имя + возраст: (3)");
        System.out.println("Имя + зп: (4)");
        System.out.println("Выход: (9)");
        int choose = SCANNER.nextInt();
        switch (choose) {
            case 1:
                return searchByAge(searchByPosition());
            case 2:
                return searchBySalary(searchByPosition());
            case 3:
                return searchByAge(searchByName());
            case 4:
                return searchBySalary(searchByName());
            case 9:
                System.out.println("Exit by multi search");
                return null;
            default:
                System.out.println("pos + age-> 1; pos + salary-> 2, name + age-> 3,  name + salary-> 3, Выход-> 9");
        }
        return null;
    }

    // Search by Age section
    private List<Employee> searchByAge() {
        System.out.println("# Search by age menu");
        System.out.println("Min возраст: (1)");
        System.out.println("Max возраст: (2)");
        System.out.println("Between возраст (3)");
        System.out.println("Выход: (9)");
        int choose = SCANNER.nextInt();
        switch (choose) {
            case 1:
                return searchMinAge(employees);

            case 2:
                return searchMaxAge(employees);

            case 3:
                return searchIntervalAge(employees);

            case 9:
                System.out.println("Exit by age search");
                return null;
            default:
                System.out.println("Min-> 1; Max-> 2, Interval-> 3,  Выход-> 9");
        }
        return null;
    }

    private List<Employee> searchByAge(List<Employee> employees) {
//    private void searchByAge() {
        System.out.println("# Search by age menu");
        System.out.println("Min возраст: (1)");
        System.out.println("Max возраст: (2)");
        System.out.println("Interval возраст (3)");
        System.out.println("Выход: (9)");
        int choose = SCANNER.nextInt();
        switch (choose) {
            case 1:
                return searchMinAge(employees);

            case 2:
                return searchMaxAge(employees);

            case 3:
                return searchIntervalAge(employees);

            case 9:
                System.out.println("Exit by age search");
                return null;
            default:
                System.out.println("Min-> 1; Max-> 2, Interval-> 3,  Выход-> 9");
        }
        return null;
    }

    private List<Employee> searchIntervalAge() {
        System.out.println("Введите минимальный и максимальный возраст: ");
        int minAge = SCANNER.nextInt();
        int maxAge = SCANNER.nextInt();
        List<Employee> result = searchIntervalAge(minAge, maxAge, employees);
        System.out.println("Результат выборки intervalAge {" + minAge + " -> " + maxAge + "}");
        DataUtil.printListColumn(result);
        return result;
    }

    private List<Employee> searchIntervalAge(List<Employee> employees) {
        System.out.println("Введите минимальный и максимальный возраст: ");
        int minAge = SCANNER.nextInt();
        int maxAge = SCANNER.nextInt();
        List<Employee> result = searchIntervalAge(minAge, maxAge, employees);
        System.out.println("Результат выборки intervalAge {" + minAge + " -> " + maxAge + "}");
        DataUtil.printListColumn(result);
        return result;
    }

    private List<Employee> searchIntervalAge(int minAge, int maxAge, List<Employee> employees) {
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getAge() >= minAge && employee.getAge() <= maxAge) {
                result.add(employee);
            }
        }
        return result;
    }

    private List<Employee> searchMaxAge() {
        return searchMaxAge(employees);
    }

    private List<Employee> searchMaxAge(List<Employee> employees) {
        System.out.println("Введите максимальный возраст: ");
        int maxAge = SCANNER.nextInt();
        List<Employee> result = searchMaxAge(maxAge, employees);
        System.out.println("Результат выборки maxAge= " + maxAge);
        DataUtil.printListColumn(result);
        return result;
    }

    private List<Employee> searchMinAge() {
        return searchMinAge(employees);
    }

    private List<Employee> searchMinAge(List<Employee> employees) {
        int minAge = DataUtil.getInt("Введите минимальный возраст: ");
        List<Employee> result = searchMinAge(minAge, employees);
        System.out.println("Результат выборки minAge= " + minAge);
        DataUtil.printListColumn(result);
        return result;
    }


    private List<Employee> searchMinAge(int minAge, List<Employee> employeeList) {
        System.out.println("Search min + employeeList");
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employeeList) {
            if (employee.getAge() >= minAge) {
                result.add(employee);
            }
        }
        return result;
    }

    private List<Employee> searchMinAge(int minAge) {
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getAge() >= minAge) {
                result.add(employee);
            }
        }
        return result;
    }

    private List<Employee> searchMaxAge(int maxAge) {
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getAge() <= maxAge) {
                result.add(employee);
            }
        }
        return result;
    }

    private List<Employee> searchMaxAge(int maxAge, List<Employee> employees) {
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getAge() <= maxAge) {
                result.add(employee);
            }
        }
        return result;
    }

    // Search by Position section
    private List<Employee> searchByPosition() {
        System.out.println("# Search by position menu");
        System.out.println("Search by 1 word: (1)");
        System.out.println("Search by 2 word: (2)");
        System.out.println("Выход: (9)");
        int choose = SCANNER.nextInt();
        switch (choose) {
            case 1:
                return searchPositionContains(employees);
            case 2:
                return searchPositionContainsTwoWords(employees);
            case 9:
                System.out.println("Exit by position search");
                return null;
            default:
                System.out.println("Position-> 1; Exit-> 9");
        }
        return null;
    }

    private List<Employee> searchByPosition(List<Employee> employees) {
        System.out.println("# Search by position menu");
        System.out.println("Search by 1 word: (1)");
        System.out.println("Search by 2 word: (2)");
        System.out.println("Выход: (9)");
        int choose = SCANNER.nextInt();
        switch (choose) {
            case 1:
                return searchPositionContains(employees);
            case 2:
                return searchPositionContainsTwoWords(employees);
            case 9:
                System.out.println("Exit by position search");
                return null;
            default:
                System.out.println("1 word-> 1; 2 word-> 2; Exit-> 9");
        }
        return null;
    }

    private List<Employee> searchPositionContains() {
        String positionSearch = DataUtil.getString("Введите позицию для поиска: ");
        List<Employee> result = searchPositionContains(positionSearch, employees);
        System.out.println("Результат выборки по позиции " + positionSearch);
        DataUtil.printListColumn(result);
        return result;
    }

    private List<Employee> searchPositionContains(List<Employee> employees) {
        String positionSearch = DataUtil.getString("Введите позицию для поиска: ");
        List<Employee> result = searchPositionContains(positionSearch, employees);
        System.out.println("Результат выборки по позиции " + positionSearch);
        DataUtil.printListColumn(result);
        return result;
    }

    private List<Employee> searchPositionContainsTwoWords() {
        System.out.print("Введите два слова для поиска в позиции через пробел: ");
        String positionSearch1 = SCANNER.next();
        String positionSearch2 = SCANNER.next();
        List<Employee> result = searchPositionContains(positionSearch1, positionSearch2, employees);
        System.out.println("Результат выборки по позиции " + positionSearch1 + " + " + positionSearch2);
        DataUtil.printListColumn(result);
        return result;
    }

    private List<Employee> searchPositionContainsTwoWords(List<Employee> employees) {
        System.out.print("Введите два слова для поиска в позиции через пробел: ");
        String positionSearch1 = SCANNER.next();
        String positionSearch2 = SCANNER.next();
        List<Employee> result = searchPositionContains(positionSearch1, positionSearch2, employees);
        System.out.println("Результат выборки по позиции " + positionSearch1 + " + " + positionSearch2);
        DataUtil.printListColumn(result);
        return result;
    }

    private List<Employee> searchPositionContains(String posSearch, List<Employee> employees) {
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getPosition().toLowerCase().contains(posSearch.toLowerCase().trim())) {
                result.add(employee);
            }
        }
        return result;
    }

    private List<Employee> searchPositionContains(String posSearch1, String posSearch2, List<Employee> employees) {
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getPosition().toLowerCase().contains(posSearch1.toLowerCase().trim()) && employee.getPosition().toLowerCase().contains(posSearch2.toLowerCase().trim())) {
                result.add(employee);
            }
        }
        return result;
    }


    // Search by Salary section
    private List<Employee> searchBySalary() {
//    private void searchByAge() {
        System.out.println("# Search by age menu");
        System.out.println("Min зп: (1)");
        System.out.println("Max зп: (2)");
        System.out.println("Interval зп (3)");
        System.out.println("Выход: (9)");
        int choose = SCANNER.nextInt();
        switch (choose) {
            case 1:
                return searchMinSalary(employees);

            case 2:
                return searchMaxSalary(employees);

            case 3:
                return searchIntervalSalary(employees);
            case 9:
                System.out.println("Exit by salary search");
                return null;
            default:
                System.out.println("Min-> 1; Max-> 2, Interval-> 3,  Выход-> 9");
        }
        return null;
    }

    private List<Employee> searchBySalary(List<Employee> employees) {
//    private void searchByAge() {
        System.out.println("# Search by salary menu");
        System.out.println("Min зп: (1)");
        System.out.println("Max зп: (2)");
        System.out.println("Interval зп (3)");
        System.out.println("Выход: (9)");
        int choose = SCANNER.nextInt();
        switch (choose) {
            case 1:
                return searchMinSalary(employees);

            case 2:
                return searchMaxSalary(employees);

            case 3:
                return searchIntervalSalary(employees);
            case 9:
                System.out.println("Exit by salary search");
                return null;
            default:
                System.out.println("Min-> 1; Max-> 2, Interval-> 3,  Выход-> 9");
        }
        return null;
    }

    private List<Employee> searchMinSalary(List<Employee> employees) {
        int minSalary = DataUtil.getInt("Введите минимальную зп: ");
        List<Employee> result = searchMinSalary(minSalary, employees);
        System.out.println("Результат выборки minSalary= " + minSalary);
        DataUtil.printListColumn(result);
        return result;
    }

    private List<Employee> searchMinSalary(int minSalary, List<Employee> employeeList) {
//        System.out.println("Search min + employeeList");
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employeeList) {
            if (employee.getSalary() >= minSalary) {
                result.add(employee);
            }
        }
        return result;
    }

    private List<Employee> searchMaxSalary(List<Employee> employees) {
        int maxSalary = DataUtil.getInt("Введите максимальную зп: ");
        List<Employee> result = searchMaxSalary(maxSalary, employees);
        System.out.println("Результат выборки maxSalary= " + maxSalary);
        DataUtil.printListColumn(result);
        return result;
    }

    private List<Employee> searchMaxSalary(int maxSalary, List<Employee> employeeList) {
//        System.out.println("Search min + employeeList");
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employeeList) {
            if (employee.getSalary() <= maxSalary) {
                result.add(employee);
            }
        }
        return result;
    }

    private List<Employee> searchIntervalSalary(List<Employee> employees) {
        System.out.println("Введите минимальную и максимальную зп: ");
        int minSal = SCANNER.nextInt();
        int maxSal = SCANNER.nextInt();
        List<Employee> result = searchIntervalSalary(minSal, maxSal, employees);
        System.out.println("Результат выборки intervalSalary {" + minSal + " -> " + maxSal + "}");
        DataUtil.printListColumn(result);
        return result;
    }

    private List<Employee> searchIntervalSalary(int minSal, int maxSal, List<Employee> employees) {
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getSalary() >= minSal && employee.getSalary() <= maxSal) {
                result.add(employee);
            }
        }
        return result;
    }

    // Search by Name section
    private List<Employee> searchByName() {
        System.out.println("# Search by name menu");
        System.out.println("Search by 1 word: (1)");
        System.out.println("Search by 2 word: (2)");
        System.out.println("Выход: (9)");
        int choose = SCANNER.nextInt();
        switch (choose) {
            case 1:
                return searchNameContains(employees);
            case 2:
                return searchNameContainsTwoWords(employees);
            case 9:
                System.out.println("Exit by name search");
                return null;
            default:
                System.out.println("1 word-> 1; 2 word > 2 Exit-> 9");
        }
        return null;
    }

    public List<Employee> find() {
        String searchName = DataUtil.getString("Find by Name: ");
        List<Employee> foundList = new ArrayList<>();
        for (Employee employee : employees) {
//            if (employee.getName().equalsIgnoreCase(searchName)){
            if (employee.getName().toLowerCase().contains(searchName.toLowerCase())) {
                foundList.add(employee);
            }
        }
        DataUtil.printListColumn(foundList);
        return foundList;
    }

    private List<Employee> searchNameContains(List<Employee> employees) {
        String nameSearch = DataUtil.getString("Введите имя для поиска: ");
        List<Employee> result = searchNameContains(nameSearch, employees);
        System.out.println("Результат выборки по имени " + nameSearch);
        DataUtil.printListColumn(result);
        return result;
    }

    private List<Employee> searchNameContains(String nameSearch, List<Employee> employees) {
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getName().toLowerCase().contains(nameSearch.toLowerCase().trim())) {
                result.add(employee);
            }
        }
        return result;
    }

    private List<Employee> searchNameContains(String nameSearch1, String nameSearch2, List<Employee> employees) {
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getName().toLowerCase().contains(nameSearch1.toLowerCase().trim()) && employee.getName().toLowerCase().contains(nameSearch2.toLowerCase().trim())) {
                result.add(employee);
            }
        }
        return result;
    }

    private List<Employee> searchNameContainsTwoWords(List<Employee> employees) {
        System.out.print("Введите два слова для поиска в имени через пробел: ");
        String nameSearch1 = SCANNER.next();
        String nameSearch2 = SCANNER.next();
        List<Employee> result = searchNameContains(nameSearch1, nameSearch2, employees);
        System.out.println("Результат выборки по имени " + nameSearch1 + " + " + nameSearch2);
        DataUtil.printListColumn(result);
        return result;
    }

    public void positions() {
        List<String> positions = new ArrayList<>();
        for (Employee employee : employees) {
            positions.add(employee.getPosition());
        }
        System.out.println(new HashSet<>(positions));
    }

    public void sorting() {
        String method = DataUtil.getString("Метод сортировки:" + "[n]ame, [p]osition, [s]alary, [a]ge");
        String choice = method.trim().toLowerCase().substring(0, 1);
        Comparator<Employee> comparator = null;
        switch (choice) {
            case "n":
                comparator = (o1, o2) -> o1.getName().compareTo(o2.getName());
                break;
            case "p":
                comparator = ((o1, o2) -> o1.getPosition().compareTo(o2.getPosition()));
                break;
            case "s":
                comparator = ((o1, o2) -> o1.getSalary() - o2.getSalary());
                break;
            case "a":
                comparator = ((o1, o2) -> o1.getAge() - o2.getAge());
                break;
            default:
                // if incorrect choice - do it nothing;
                return;
        }
        if (comparator != null) {
            List<Employee> sortedList = new ArrayList<>(employees);
            sortedList.sort(comparator);
            DataUtil.printListColumn(sortedList);
        }

        // Old realization
//        Map<Integer, Employee> sortedMap = new TreeMap<>();
//        for (Employee employee : this.employees) {
//            sortedMap.put(employee.getSalary(), employee);
//        }
//        List<Employee> sortedList = new ArrayList<>();
//        for (Integer key: sortedMap.keySet()) {
//            sortedList.add(sortedMap.get(key));
//        }

    }
}