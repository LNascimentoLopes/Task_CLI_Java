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

        // 1. ler todas as linhas
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;

            while ((linha = reader.readLine()) != null) {

                // 2. Se a linha NÃO contém o id desejado → guarda ela
                if (!linha.startsWith("id:" + id)) {
                    linhas.add(linha);
                }
            }
        }

        // 3. reescrever o arquivo só com as linhas que sobraram
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

                // 2. Se a linha NÃO contém o id desejado → guarda ela
                if (!linha.startsWith("id:" + id)) {
                    linhas.add(linha);
                } else {
                    String novaLinha = atualizarLinhaStatus(linha, "Done");
                    linhas.add(novaLinha);

                }
            }
        }

        // 3. reescrever o arquivo só com as linhas que sobraram
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

        // 1. ler todas as linhas
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;

            while ((linha = reader.readLine()) != null) {

                // 2. Se a linha NÃO contém o id desejado → guarda ela
                if (!linha.startsWith("id:" + id)) {
                    linhas.add(linha);
                } else {
                    String novaLinha = atualizarLinhaStatus(linha, "In Progress");
                    linhas.add(novaLinha);

                }
            }
        }

        // 3. reescrever o arquivo só com as linhas que sobraram
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

        if (posStatus == -1) return linha; // não encontrou status (não altera nada)

        String antes = linha.substring(0, posStatus);

        // Descobre onde termina o status atual (antes de Created)
        int posCreated = linha.indexOf("Created:");

        // Parte depois de Created:
        String depois = linha.substring(posCreated);

        return antes + "Status:" + novoStatus + " " + depois;
    }

    public void Update (int id, String newname, String Time) throws IOException {
        File file = new File("tasks.json");

        List<String> linhas = new ArrayList<>();

        // 1. ler todas as linhas
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;

            while ((linha = reader.readLine()) != null) {

                // 2. Se a linha NÃO contém o id desejado → guarda ela
                if (!linha.startsWith("id:" + id)) {
                    linhas.add(linha);
                } else {
                    atualizarLinha(linha, newname);
                    String novaLinha;
                    novaLinha = atualizarTempo(linha, Time);
                    linhas.add(novaLinha);

                }
            }
        }

        // 3. reescrever o arquivo só com as linhas que sobraram
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String l : linhas) {
                writer.write(l);
                writer.newLine();
            }
        }

        System.out.println("Task " + id + " updated successfully");
    }
    private String atualizarLinha(String linha, String novoNome) {


        int posName = linha.indexOf("name:");

        if (posName == -1) return linha; // não encontrou nome (não altera nada)

        String antes = linha.substring(0, posName);

        // Descobre onde termina o nome atual (antes de Status)
        int posStatus = linha.indexOf("Status:");

        // Parte depois de Status:
        String depois = linha.substring(posStatus);

        return antes + "name:" + novoNome + " " + depois;
    }
    private String atualizarTempo(String linha, String novoTempo) {


        int posTime = linha.indexOf("Updated:");

        if (posTime == -1) return linha; // não encontrou tempo (não altera nada)

        String antes = linha.substring(0, posTime);

        // Descobre onde termina o nome atual (antes de Status)
        int posCreated = linha.indexOf("Created:");

        return antes + "Updated:" + novoTempo;
    }
}



