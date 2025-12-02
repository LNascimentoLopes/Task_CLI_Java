import java.time.LocalDateTime;

public class Task {
    private int id;
    private String nome;
    private String status = "Not Started";
    private LocalDateTime Created = LocalDateTime.now();
    private LocalDateTime Updated;

    public Task() {
    }
    public Task(String status){
        this.status = status;
    }

    public Task(String nomeRecebido, int IdRecebido) {
        this.nome = nomeRecebido;
        this.id = IdRecebido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status==null){
            this.status ="Not Started";
        }else {
            this.status = status;
        }

    }

    public LocalDateTime getCreated() {
        return Created;
    }

    public void setCreated(LocalDateTime created) {
        Created = created;
    }


    public String Format(){
        return "id:"+this.id+" "+
                "name:"+this.nome+" "+
                "Status:"+this.status+" "+
                "Created:"+this.Created+" "+
                "Updated:"+this.Updated;
    }

}
