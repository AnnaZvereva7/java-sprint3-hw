import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Manager {
    int uin = 1;
    HashMap<Integer, Task> taskHashMap = new HashMap<>();
    HashMap<Integer, Epic> epicHashMap = new HashMap<>();

    public void addTask() {
        Scanner scanner=new Scanner(System.in);
        String name;
        String description;
        int epicUin;
        System.out.println("Введите название задачи");
        name = scanner.nextLine();
        System.out.println("Введите описание задачи");
        description = scanner.nextLine();
        //проверка что такая задача уже есть
        if (!checkSame(name,description)) {
            System.out.println("У задачи будут подзадачи? 1- ДА 2-НЕТ");
            int choice = scanner.nextInt();
            if (choice==2) {
                taskHashMap.put(uin, new Task(name, description, 0));
                uin+=1;
            } else {
                HashMap<Integer, SubTask> subTaskHashMap = new HashMap<>();
                epicUin=uin;
                uin+=1;
                while (true) {
                    subTaskHashMap.put(uin, addSubTask(epicUin));
                    uin+=1;
                    System.out.println("Вы хотите ввести еще одну подзадачу? 1- ДА 2- НЕТ");
                    if (scanner.nextInt()==2) {
                        break;
                    }
                }
                epicHashMap.put(epicUin,new Epic(name, description, 0, subTaskHashMap));
            }
        }
    }

    public boolean checkSame (String name, String description) {
        Task checkingTask = new Task(name, description, 0);
        Scanner scanner=new Scanner(System.in);
        for (Map.Entry<Integer,Task> entry: taskHashMap.entrySet()) {
            if (entry.getValue().equals(checkingTask)) {
                System.out.println("Такая задача уже существует.\n"+entry.getKey()+". "+entry.getValue());
                System.out.println("Вы все равно хотите ввести новую задачу? 1- ДА, продолжить ввод 2- НЕТ, прервать ввод");
                if (scanner.nextInt()==2)  return true;
            }
        }
        for (Map.Entry<Integer,Epic> entry: epicHashMap.entrySet()) {
            if (entry.getValue().equals(checkingTask)) {
                System.out.println("Такая задача уже существует.\n"+entry.getKey()+". "+entry.getValue());
                System.out.println("Вы все равно хотите ввести новую задачу? 1- ДА, продолжить ввод 2- НЕТ, прервать ввод");
                if (scanner.nextInt()==2)  return true;
            } else {
                Epic workEpic=entry.getValue();
                for(Map.Entry<Integer, SubTask> entrySubTask: workEpic.subTaskHashMap.entrySet()) {
                    if (entrySubTask.getValue().equals(checkingTask)) {
                        System.out.println("Такая подзадача существует.\n" + entrySubTask.getKey()+". "+entrySubTask.getValue()
                                +"\nОтносится к задаче "+entry.getKey()+". "+workEpic.name+" ("+workEpic.description+")");
                        System.out.println("Вы все равно хотите ввести новую задачу? 1- ДА, продолжить ввод 2- НЕТ, прервать ввод");
                        if (scanner.nextInt()==2)  return true;
                    }
                }
            }
        }
        return false;
    }

    public SubTask addSubTask(int epicUin) {
        Scanner scanner = new Scanner(System.in);
        String name;
        String description;
        System.out.println("Введите название подзадачи");
        name = scanner.nextLine();
        System.out.println("Введите описание подзадачи");
        description = scanner.nextLine();
        return  new SubTask(epicUin, name, description, 0);
    }

    public void printTask() {
        if (taskHashMap.isEmpty()&&epicHashMap.isEmpty()) {
            System.out.println("Список задач пуст");
        } else {
            taskHashMap.forEach((key, value) -> System.out.println(key+". "+value));
            epicHashMap.forEach((key, value) -> System.out.println(key+". "+value));
        }
    }

    public void updateTask(int uinNumber) {
        Task workTask = null;
        Epic workEpic = null;
        SubTask workSubTask = null;

        for(Map.Entry<Integer,Task> entryTask: taskHashMap.entrySet()) {
            if (entryTask.getKey() == uinNumber) {
                workTask = entryTask.getValue();
                workTask=getNewValues(workTask.name, workTask.description, workTask.status,1);
                taskHashMap.put(uinNumber,workTask);
            }
        }
        for(Map.Entry<Integer,Epic> entryEpic: epicHashMap.entrySet()) {
            if (entryEpic.getKey()==uinNumber) {
                workEpic=entryEpic.getValue();
                workTask=getNewValues(workEpic.name, workEpic.description, workEpic.status, 2);
                workEpic.name= workTask.name;
                workEpic.description=workTask.description;
                workEpic.status= workTask.status;
                epicHashMap.put(uinNumber,workEpic);
            } else {
                HashMap<Integer, SubTask> workSubTaskHashMap = entryEpic.getValue().subTaskHashMap;
                for (Map.Entry<Integer,SubTask> entrySubTask: workSubTaskHashMap.entrySet()) {
                    if (entrySubTask.getKey()==uinNumber) {
                        workSubTask=entrySubTask.getValue();
                        workTask=getNewValues(workSubTask.name, workSubTask.description, workSubTask.status, 3);
                        workSubTask.name=workTask.name;
                        workSubTask.description=workTask.description;
                        workSubTask.status=workTask.status;
                        entrySubTask.setValue(workSubTask);
                    }
                }
            }
        }
        if (workTask==null&&workSubTask==null&&workEpic==null) {
             System.out.println("Задачи с таким номером не существует");
        } else {
            System.out.println("Новые параметры установлены");
        }
    }

    public Task getNewValues (String currentName, String currentDescription, int currentStatus, int classNumber ) { //classNumber 1-Task, 2-Epic, 3-Subtask
        int command;
        System.out.println("Что Вы хотите изменить? \n1- Наименование 2- Описание 3- Статус выполнения");
        while (true) {
            Scanner scanner=new Scanner(System.in);
            command=scanner.nextInt();
            if (command==1) {
                Scanner scanner2=new Scanner(System.in);
                System.out.println("Текущее название задачи: "+currentName);
                System.out.println("Введите новое название задачи");
                currentName = scanner2.nextLine();
                System.out.println("Вы хотите еще что-то поменять? " +
                        "\n1- Наименование 2- Описание 3- Статус выполнения 0- Выход");
            } else if (command==2) {
                Scanner scanner2=new Scanner(System.in);
                System.out.println("Текущее описание задачи: "+currentDescription);
                System.out.println("Введите новое описание задачи");
                currentDescription = scanner2.nextLine();
                System.out.println("Вы хотите еще что-то поменять? " +
                        "\n1- Наименование 2- Описание 3- Статус выполнения 0- Выход");
            } else if (command==3) {
                if (classNumber==2) {//Epic
                    System.out.println("Для задачи с подзадачами нельзя установить статус, он будет вычислен автоматически");
                } else {
                    System.out.println("Текущий статус задачи: "+currentStatus);
                    System.out.println("Введите новый статус задачи: \n0- NEW 1- IN_PROGRESS 2- DONE");
                    while (true) {
                        Scanner scanner2=new Scanner(System.in);
                        currentStatus=scanner2.nextInt();
                        if (!(currentStatus==0||currentStatus==1||currentStatus==2)) {
                            System.out.println("Вы ввели некорректный статус, выберите один из трех вариантов " +
                                    "\n0- NEW 1- IN_PROGRESS 2- DONE");
                        } else {
                            break;
                        }
                    }
                }
                System.out.println("Вы хотите еще что-то поменять? " +
                        "1- Наименование 2- Описание 3- Статус выполнения 0- Выход");
            } else if (command==0) {
                break;
            } else {
                System.out.println("Вы ввели некорректную команду. Выберите один из вариантов:" +
                        "\n1- Изменить наименование \n2- Изменить описание \n3- Изменить статус выполнения \n0- Выйти");
            }
        }
        return new Task(currentName, currentDescription, currentStatus);
    }

    public void deleteTask(int uinNumber) {
        int counter=0;
        if (taskHashMap.containsKey(uinNumber)) {
            taskHashMap.remove(uinNumber);
            counter+=1;
            System.out.println("Задача удалена");
        }
        if (epicHashMap.containsKey(uinNumber)) {
            epicHashMap.remove(uinNumber);
            counter+=1;
            System.out.println("Задача удалена");
        }
        Epic workEpic=null;
        int workUin=0;
        for (Map.Entry<Integer,Epic> entry: epicHashMap.entrySet()) {
            if (entry.getValue().subTaskHashMap.containsKey(uinNumber)) {
                workEpic = entry.getValue();
                workUin = entry.getKey();
                workEpic.subTaskHashMap.remove(uinNumber);
                counter+=1;
                System.out.println("Подзадача удалена");
            }
        }
        if (!(workEpic==null&&workUin==0)) {
            if (workEpic.subTaskHashMap.isEmpty()) {
                Task movingTask = new Task(workEpic.name, workEpic.description, workEpic.status);
                epicHashMap.remove(workUin);
                taskHashMap.put(workUin,movingTask);
                System.out.println("Из задачи: "+movingTask+" - были удалены все подзадачи");
            }
        }

        if (counter==0) {
            System.out.println("Задач с таким номером нет");
        }
    }

    public void findTask (int uinNumber) {
        int counter = 0;
        if (taskHashMap.containsKey(uinNumber)) {
            Task thisTask=taskHashMap.get(uinNumber);
            System.out.println(thisTask);
            counter+=1;
        } else if (epicHashMap.containsKey(uinNumber)) {
            Epic thisEpic=epicHashMap.get(uinNumber);
            System.out.println(thisEpic);
            counter+=1;
        } else {
            for(Map.Entry<Integer, Epic> entry: epicHashMap.entrySet()) {
                if (entry.getValue().subTaskHashMap.containsKey(uinNumber)) {
                    System.out.println("Подзадача: "+entry.getValue().subTaskHashMap.get(uinNumber)+" - относится к задаче: "
                            +entry.getValue().name+" ("+entry.getValue().description+")");
                    counter+=1;
                }
            }
        }
        if (counter==0) {
            System.out.println("Задач с таким номером нет");
        }
    }

    public void deleteAll() {
        taskHashMap.clear();
        epicHashMap.clear();
        System.out.println("Список задач пуст");
    }

    public void readTaskFile() {
        String path = "resources/taskManager.csv";

        ReadingFile readingFile = new ReadingFile();
        List<String> listOfTask = readingFile.readFileContents(path);
        for (int i = 0; i < listOfTask.size(); i++) {
            String[] lineContents = listOfTask.get(i).split(",");
            int uinFromFile=Integer.parseInt(lineContents[0]);
            int epicUinFromFile=Integer.parseInt(lineContents[4]);
            int statusFromFile=Integer.parseInt(lineContents[3]);
            String nameFromFile=lineContents[1];
            String descriptionFromFile=lineContents[2];

            if (epicUinFromFile==0) {
                Task newTask = new Task(nameFromFile, descriptionFromFile, statusFromFile);
                taskHashMap.put(uinFromFile, newTask);
                uin+=1;
            }  else if (uinFromFile==epicUinFromFile) {
                HashMap<Integer, SubTask> subTaskHashMap = new HashMap<>();
                Epic newEpic = new Epic(nameFromFile, descriptionFromFile, statusFromFile, subTaskHashMap);
                epicHashMap.put(epicUinFromFile, newEpic);
                uin+=1;
            } else {
                Epic thisEpic = epicHashMap.get(epicUinFromFile);
                thisEpic.subTaskHashMap.put(uinFromFile,
                        new SubTask(epicUinFromFile, nameFromFile, descriptionFromFile, statusFromFile ));
                epicHashMap.put(epicUinFromFile, thisEpic);
                uin+=1;
            }
        }
    }
}



