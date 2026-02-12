import java.util.*;

public class Usuario {

    private String nome;
    private String email;
    private String cidade;

    private List<Evento> participando = new ArrayList<>();

    public Usuario(String nome, String email, String cidade) {
        this.nome = nome;
        this.email = email;
        this.cidade = cidade;
    }

    public void participar(Evento e) {
        participando.add(e);
    }

    public void cancelar(Evento e) {
        participando.remove(e);
    }

    public List<Evento> getParticipando() {
        return participando;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getCidade() { return cidade; }
}
