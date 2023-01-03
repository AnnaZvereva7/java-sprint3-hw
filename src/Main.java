import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int command;
        Scanner scanner=new Scanner(System.in);
        Manager manager = new Manager();
        while (true) {
            printMenu();
            command=scanner.nextInt();
            int uinNumber;
            switch (command) {
                case 0:
                    break;
                case 1:
                    manager.addTask();
                    break;
                case 2:
                    manager.printTask();
                    break;
                case 3:
                    System.out.println("Задачу под каким номером Вы хотите обновить?");
                    uinNumber = scanner.nextInt();
                    manager.updateTask(uinNumber);
                    break;
                case 4:
                    System.out.println("Задачу под каким номером Вы хотите удалить?");
                    uinNumber = scanner.nextInt();
                    manager.deleteTask(uinNumber);
                    break;
                case 5:
                    System.out.println("Задачу под каким номером Вы хотите найти?");
                    uinNumber = scanner.nextInt();
                    manager.findTask(uinNumber);
                    break;
                case 6:
                    manager.deleteAll();
                    break;
                case 7:
                    manager.readTaskFile();
                    System.out.println("Файл считан");
                    break;
                default:
                    System.out.println("Вы ввели некорректную команду");
            }
        }
    }

    public static void printMenu () {
        System.out.println("\nЧто бы вы хотели сделать?");
        System.out.println("1 - Создать задачу");
        System.out.println("2 - Вывести все задачи");
        System.out.println("3 - Обновить задачу (по идентификатору)");
        System.out.println("4 - Удалить задачу (по идентификатору)");
        System.out.println("5 - Найти задачу (по идентификатору)");
        System.out.println("6 - Удалить все задачи");
        System.out.println("7 - Считать из файла"); //Для проверки работы только в начале
        System.out.println("0 - Выход");
    }
}