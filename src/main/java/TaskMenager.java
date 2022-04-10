import com.sun.jdi.Value;
import org.apache.commons.lang3.ArrayUtils;
import pl.coderslab.ConsoleColors;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Scanner;

public class TaskMenager {

    public static void main(String[] args) {

        System.out.println(" ");
        createTaskTab();
        menu();

    }

    static public void menu() {
        System.out.println(ConsoleColors.BLUE + "Please select on option");
        System.out.println(ConsoleColors.WHITE + "add");
        System.out.println(ConsoleColors.WHITE + "remove");
        System.out.println(ConsoleColors.WHITE + "list");
        System.out.println(ConsoleColors.WHITE + "exit");

        Scanner scanner = new Scanner(System.in);

        createTaskTab();
        switch (scanner.nextLine()) {
            case "add":
                add();
                System.out.println(" ");
                menu();
            case "remove":
                remove(createTaskTab());
                System.out.println();
                menu();
            case "list":
                list();
                System.out.println("");
                menu();

            case "exit":

                System.out.println(ConsoleColors.RED + "Ciao!");
                break;
            default:
                System.out.println("Musisz podać dostępna opcje");
                menu();
        }
    }


    static public String[][] createTaskTab() {
        Path taskFile = Paths.get("src/main/resources/tasks.csv");
        int tasklenght = 0;
        String taskTab[][] = new String[tasklenght][3];
        try {
            for (String line : Files.readAllLines(taskFile)) {
                tasklenght++;
            }

            for (int i = 0; i < taskTab.length; i++) {
                Scanner scannerTask = new Scanner(taskFile);
                taskTab[i] = scannerTask.nextLine().split(" ", 2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return taskTab;
    }

    static public void add() {
        String addedTask[][] = Arrays.copyOf(createTaskTab(), createTaskTab().length + 1);
        Scanner newTask = new Scanner(System.in);
        StringBuilder newTaskSB = new StringBuilder();
        System.out.println("Podaj nazwę zadania");
        newTaskSB.append(newTask.nextLine()).append(", ");
        System.out.println("Podaj termin wykonania zadania");
        newTaskSB.append(newTask.nextLine()).append(", ");
        System.out.println("Czy zadanie jest ważne?");
        newTaskSB.append(newTask.next());
        String newTaskToAdded = newTaskSB.toString();
        addedTask[addedTask.length - 1] = newTaskToAdded.split(" ");

        for (int j = 0; j < addedTask.length; j++) {
            System.out.println();
            for (int k = 0; k < addedTask[j].length; k++) {
                System.out.print(addedTask[j][k] + " ");
            }
        }


        Path path1 = Paths.get("src/main/resources/tasks.csv");
        try {
            Files.writeString(path1, "\n", StandardOpenOption.APPEND);
            Files.writeString(path1, newTaskSB, StandardOpenOption.APPEND);
        } catch (IOException ex) {
            System.out.println("Nie można zapisać pliku.");
        }
    }


        private static void remove (String[][] tab) {
            try {
                Scanner taskToRemove = new Scanner(System.in);
                System.out.println("Podaj nr zadania do usuniecia");
                int index = Integer.parseInt(taskToRemove.next());
                if (index < tab.length) {
                     String tasks[][] = ArrayUtils.remove(tab, index);
                    System.out.println(tasks[1][1]);
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Element not exist in tab");
            }
        }

    static public void list() {

        Path taskFile = Paths.get("src/main/resources/tasks.csv");
        int taskNumber = 1;
        try {
            for (String line : Files.readAllLines(taskFile)) {
                System.out.println(taskNumber + ": " + line);
                taskNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


