package org.example.utils;

import org.example.crudProject.Employee;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

/**
 * @author Sergii Bugaienko
 */

public class DbInit {
    private final static String fileDbName = "db5.txt";
    private final static String fileInitTxt = "initDB";

    public static List<Employee> init() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("John", "Boss", 2000, 45));
        employees.add(new Employee("Gina", "Assistant", 950, 21));
        employees.add(new Employee("Svetlana", "CafeMaker", 950, 20));
        employees.add(new Employee("Johanna", "Front-end prog", 1700, 25));
        employees.add(new Employee("Tomas", "Back-end prog", 1800, 28));
        employees.add(new Employee("Tomara", "Back-end prog", 1750, 23));
        employees.add(new Employee("John", "Assistant", 1500, 24));

        return employees;
    }

    public static List<Employee> getEmployeesFromDb() {
        List<Employee> list = new ArrayList<>();
        try {
            File fileDb = connectFile(fileDbName);
            if (fileDb.length() <= 1) {
                System.out.println("Пустой " + fileDb.length());
                list = initFromFile();
                saveDb(list);
            } else {
//                System.out.println(fileDb.length());
                System.out.println("Не пустой " + fileDb.length());
                list = readDb();
            }
        } catch (URISyntaxException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;

    }

    public static void saveDb(List<Employee> employees) {

        try (FileOutputStream fos = new FileOutputStream(connectFile(fileDbName)); ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            oos.writeObject(employees);

        } catch (URISyntaxException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Employee> readDb() {
        List<Employee> employeeList = new ArrayList<>();
        try (FileInputStream fin = new FileInputStream(connectFile(fileDbName)); ObjectInputStream ois = new ObjectInputStream(fin);) {
            employeeList = (List<Employee>) ois.readObject();
        } catch (IOException | URISyntaxException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return employeeList;
    }


    public static List<Employee> initFromFile() {

        List<Employee> employees = new ArrayList<>();

        try {
            File file = connectFile(fileInitTxt);
            List<String[]> listParams = new ArrayList<>();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] newEmployStrings = line.split(", ");
                listParams.add(newEmployStrings);
//                System.out.println(Arrays.toString(newEmployStrings));
                Employee temp = new Employee(newEmployStrings);
                employees.add(temp);
            }

        } catch (FileNotFoundException | URISyntaxException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public static File connectFile(String filename) throws URISyntaxException, IllegalAccessException {
        URL resource = DbInit.class.getClassLoader().getResource(filename);
        if (resource == null) {
            throw new IllegalAccessException();
        } else {
            return new File(resource.toURI());
        }
    }

    public static List<Employee> createBigRandomList(int size) {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String name = "";
            String position = "";
            int salary = new Random().nextInt(2000) + 500;
            int age = new Random().nextInt(50) + 15;
            for (int j = 0; j < 6; j++) {
                char tmp = (char) (new Random().nextInt(25) + 65);
                char tmp2 = (char) (new Random().nextInt(25) + 65);
                name += tmp;
                position += tmp2;
            }
            employees.add(new Employee(name, position, salary, age));
        }
        return employees;
    }
}
