import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    public int gerarId() {
        File file = new File("tasks.json");

        if (!file.exists() || file.length() == 0) {
            return 1;
        }
        int maiorId = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                int pos = linha.indexOf("id:");
                if (pos == -1) continue;

                String resto = linha.substring(pos + 3).trim();
                int fim = resto.indexOf(" ");

                if (fim == -1) continue;
                String numeroStr = resto.substring(0, fim).trim();

                if (!numeroStr.matches("\\d+")) continue;

                int id = Integer.parseInt(numeroStr);

                if (id > maiorId) maiorId = id;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return maiorId + 1;
    }

    public void add(Task task) throws IOException {
        File file = new File("tasks.json");
        BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.json", true));
        writer.write(task.Format());
        writer.newLine();
        writer.close();
    }

    public void deleteById(int id) throws IOException {

        File file = new File("tasks.json");

        List<String> linhas = new ArrayList<>();

        //ler todas as linhas
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;

            while ((linha = reader.readLine()) != null) {

                //Se a linha nao contém o id desejado
                if (!linha.startsWith("id:" + id)) {
                    linhas.add(linha);
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String l : linhas) {
                writer.write(l);
                writer.newLine();
            }
        }

        System.out.println("Task " + id + " removida com sucesso!");
    }

    public void listAll() throws IOException {
        File file = new File("tasks.json");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
            }
        }
    }

    public void listDone() throws IOException {
        File file = new File("tasks.json");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                if (linha.contains("Done")){
                    System.out.println(linha);
                }
            }
        }
    }

    public void listInProgress() throws IOException {
        File file = new File("tasks.json");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;

            while ((linha = reader.readLine()) != null) {

                if (linha.contains("In Progress")){
                    System.out.println(linha);
                }
            }
        }
    }

    public void listNotStarted() throws IOException {
        File file = new File("tasks.json");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                if (linha.contains("Not Started")){
                    System.out.println(linha);
                }
            }
        }
    }

    public void MarkDone(int id) throws IOException {
        File file = new File("tasks.json");

        List<String> linhas = new ArrayList<>();

        // 1. ler todas as linhas
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;

            while ((linha = reader.readLine()) != null) {

                // 2. Se a linha nao contém o id necessario
                if (!linha.startsWith("id:" + id)) {
                    linhas.add(linha);
                } else {
                    String novaLinha = atualizarLinhaStatus(linha, "Done");
                    linhas.add(novaLinha);

                }
            }
        }

        //reescrever o arquivo só com as linhas que sobraram
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String l : linhas) {
                writer.write(l);
                writer.newLine();
            }
        }

        System.out.println("Task " + id + " marked done successfully");
    }

    public void MarkInProgress(int id) throws IOException {
        File file = new File("tasks.json");

        List<String> linhas = new ArrayList<>();

        // ler todas as linhas
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;

            while ((linha = reader.readLine()) != null) {

                // Se a linha nao o id desejado, guarda a linha
                if (!linha.startsWith("id:" + id)) {
                    linhas.add(linha);
                } else {
                    String novaLinha = atualizarLinhaStatus(linha, "In Progress");
                    linhas.add(novaLinha);

                }
            }
        }

        // reescrever o arquivo só com linhas que sobraram
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String l : linhas) {
                writer.write(l);
                writer.newLine();
            }
        }

        System.out.println("Task " + id + " marked in-progress successfully");
    }


    private String atualizarLinhaStatus(String linha, String novoStatus) {


        int posStatus = linha.indexOf("Status:");

        if (posStatus == -1) return linha; // não encontrou status

        String antes = linha.substring(0, posStatus);

        // onde termina o status atual
        int posCreated = linha.indexOf("Created:");

        //depois de Created:
        String depois = linha.substring(posCreated);

        return antes + "Status:" + novoStatus + " " + depois;
    }

    public void Update(int id, String newname, String newTime) throws IOException {
        File file = new File("tasks.json");
        List<String> linhas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;

            while ((linha = reader.readLine()) != null) {

                if (linha.replace(" ", "").startsWith("id:" + id)) {

                    String nova = atualizarLinha(linha, newname, newTime);
                    linhas.add(nova);

                } else {
                    linhas.add(linha);
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String l : linhas) {
                writer.write(l);
                writer.newLine();
            }
        }

        System.out.println("Task " + id + " updated successfully");
    }

    private String atualizarLinha(String linha, String newName, String newTime) {
        // Atualizar name
        linha = linha.replaceAll("name:[^ ]+", "name:" + newName);

        // Atualizar Updated
        linha = linha.replaceAll("Updated:[^ ]+", "Updated:" + newTime);

        return linha;
    }
}



