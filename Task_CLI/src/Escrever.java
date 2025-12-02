import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.BufferedWriter;

public class Escrever {
    public Escrever(String[] arguments) {
        Arguments = arguments;
    }

    public String[] Arguments;

    public void CriarArquivo() throws IOException {
        File file = new File("tasks.json");

        if (!file.exists()) {
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("[]");
            writer.close();
        }

    }
}
