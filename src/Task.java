import java.util.Objects;

public class Task {
    String name;
    String description;
    int status;

    public Task (String name, String description, int status) {
        this.name=name;
        this.description = description;
        this.status=status;
    }

   @Override
   public String toString() {
       return name+" ("+ description +") - "+returnStatus();
   }

   @Override
   public boolean equals(Object obj) {
        if (this==obj) return true;
        if (obj==null) return false;
        Task otherTask = (Task) obj;
        return Objects.equals(name, otherTask.name)
                &&Objects.equals(description, otherTask.description);
   }

    String returnStatus() {
        switch (status) {
            case 0: return "new";
            case 1: return "in progress";
            case 2: return "done";
            default: return "undefined";
        }
    }
}
