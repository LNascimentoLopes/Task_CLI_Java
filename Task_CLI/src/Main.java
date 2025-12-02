import java.io.IOException;
import java.time.LocalDateTime;

public class Main extends TaskRepository {
    static void main(String[] args) throws IOException {
        TaskRepository add = new TaskRepository();
        TaskRepository delete = new TaskRepository();
        TaskRepository listAll = new TaskRepository();
        TaskRepository Status = new TaskRepository();
        TaskRepository Update = new TaskRepository();

        switch (args[0]) {
            case "add":
                Task GatherDataAdd = new Task(args[1], add.gerarId());
                add.add(GatherDataAdd);
                System.out.println("Task added successfully!");
                break;
            case "update":
                Update.Update(Integer.parseInt(args[1]), args[2], LocalDateTime.now().toString());
                break;
            case "delete":
                delete.deleteById(Integer.parseInt(args[1]));
                break;
            case "mark-done":
                Status.MarkDone(Integer.parseInt(args[1]));
                break;
            case "mark-in-progress":
                Status.MarkInProgress(Integer.parseInt(args[1]));
                break;
            case "list":
                if (args.length > 1) {
                    switch (args[1]) {
                        case "done":
                            listAll.listDone();
                            break;
                        case "in-progress":
                            listAll.listInProgress();
                            break;
                        case "not-started":
                            listAll.listNotStarted();
                            break;
                        default:
                            System.out.println("Comando invalido");
                            break;
                    }
                    break;
                } else {
                    listAll.listAll();
                    break;
                }
        }
    }
}
