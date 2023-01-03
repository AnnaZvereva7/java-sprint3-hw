import java.util.HashMap;
import java.util.Map;

public class Epic extends Task{
    HashMap<Integer, SubTask> subTaskHashMap;

    public Epic(String name, String description, int status, HashMap<Integer, SubTask> subTaskHashMap) {
        super(name, description, status);
        this.subTaskHashMap=subTaskHashMap;
    }

    @Override
    public String toString() {
        String result="";
        for (Map.Entry<Integer, SubTask> entry: subTaskHashMap.entrySet()) {
            SubTask subTask = entry.getValue();
            result=result+"\n    "+entry.getKey()+". "+subTask.name+" ("+ subTask.description
                    + ") - "+subTask.returnStatus();
        }
        return name+" ("+ description +") - "+returnStatus()+result;
    }

    @Override
    String returnStatus() {
        int result=0;
        for (SubTask items: subTaskHashMap.values()) {
            result+=items.status;
        }
        if (result==0) {
            return "new";
        } else if (result<(subTaskHashMap.size()*2)) {
            return "in progress";
        } else if (result==(subTaskHashMap.size())*2) {
            return "done";
        } else {
            return "undefined";
        }
    }
}
