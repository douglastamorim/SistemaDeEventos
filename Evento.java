import java.time.*;

public class Evento {

    private String nome;
    private String endereco;
    private Categoria categoria;
    private LocalDateTime horario;
    private String descricao;
    private boolean alarme;

    public Evento(String nome, String endereco, Categoria categoria,
                  LocalDateTime horario, String descricao, boolean alarme) {

        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
        this.alarme = alarme;
    }

    public LocalDateTime getHorario() { return horario; }
    public boolean isAlarme() { return alarme; }
    public String getNome() { return nome; }

    public String getStatus() {

        LocalDateTime agora = LocalDateTime.now();

        if (horario.isBefore(agora))
            return "FINALIZADO";

        if (horario.getDayOfYear() == agora.getDayOfYear()
                && horario.getHour() == agora.getHour())
            return "ACONTECENDO";

        return "EM BREVE";
    }

    @Override
    public String toString() {
        return nome + " | " + categoria + " | " + horario + " | " + getStatus();
    }
}
