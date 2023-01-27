package org.example.crudProject;

import org.example.utils.DataBase;
import org.example.utils.DbInit;

import java.util.Scanner;

/**
 * @author Sergii Bugaienko
 */
import java.util.Scanner;

public class CommandLine {

    public void exec() {
        DataBase db = new DataBase(DbInit.initFromFile());
        Scanner scanner = new Scanner(System.in);
        System.out.println("CRUD app v.12.HW");

        while (true) {
            printChoiceVariants();
            System.out.println("# Main menu");
            String cmd = scanner.next();
            ChoiceEnum choice2;
            try {
                choice2 = ChoiceEnum.getEnumByKey(cmd.trim().toLowerCase().substring(0, 1));
            } catch (IllegalArgumentException e) {
                System.out.println("No found2");
                choice2 = ChoiceEnum.READ;
            }
            try {
                switch (choice2) {
                    case CREATE:
                        db.create();
                        break;
                    case READ:
                        db.read();
                        break;
                    case UPDATE:
                        db.update();
                        break;
                    case DELETE:
                        db.delete();
                        break;
                    case EXIT:
                        System.out.println("Exit of command line.");
                        return;
                    case SEARCH:
                        db.search();
                        break;
                    case FIND:
                        db.find();
                        break;
                    case POSITION:
                        db.positions();
                        break;
                    case SORT:
                        db.sorting();
                        break;

                    default:
                        printChoiceVariants();
                }
            } catch (NullPointerException e) {
                System.out.println(e);
            }
        }
    }

    private void printChoiceVariants() {
        System.out.println();
        System.out.print("List of command: ");
        for (ChoiceEnum choiceEnum : ChoiceEnum.values()) {
            System.out.print(choiceEnum.description + ", ");
        }
        System.out.println();
    }
}
