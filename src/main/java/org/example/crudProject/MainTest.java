package org.example.crudProject;

import org.example.utils.DataUtil;
import org.example.utils.DbInit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergii Bugaienko
 */

public class MainTest {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        List<Employee> employeesRead = new ArrayList<>();
        try {
            employees = DbInit.initFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataUtil.printListColumn(employees);
        DbInit.saveDb(employees);
        employeesRead = DbInit.readDb();

        System.out.println("Read");
        DataUtil.printListColumn(employeesRead);
    }
}
