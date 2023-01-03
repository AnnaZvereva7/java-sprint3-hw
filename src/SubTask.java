public class SubTask extends Task {
    int epicUin;

    public SubTask(int epicUin, String name, String description, int status) {
        super(name, description, status);
        this.epicUin=epicUin;
    }
}
