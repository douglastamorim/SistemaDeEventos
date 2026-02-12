import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.awt.Toolkit;

public class Main {

    static Scanner sc = new Scanner(System.in);

    static List<Evento> eventos;
    static Usuario usuario;

    public static void main(String[] args) throws Exception {

        eventos = EventRepository.carregar();

        usuario = loginOuCadastro();

        iniciarAlarmes();

        int op;

        do {
            System.out.println("\n==== MENU ====");
            System.out.println("1 - Cadastrar evento");
            System.out.println("2 - Listar eventos");
            System.out.println("3 - Participar evento");
            System.out.println("4 - Meus eventos");
            System.out.println("5 - Cancelar participação");
            System.out.println("0 - Sair");

            op = InputUtil.lerInt("Escolha: ");

            switch (op) {
                case 1 -> cadastrarEvento();
                case 2 -> listarEventos();
                case 3 -> participar();
                case 4 -> meusEventos();
                case 5 -> cancelarParticipacao();
            }

        } while (op != 0);

        EventRepository.salvar(eventos);
        UserRepository.salvar(usuario);

        System.out.println("Até logo!");
    }

    // ================= LOGIN =================

    static Usuario loginOuCadastro() throws Exception {

        Usuario u = UserRepository.carregar();

        if (u != null) {
            System.out.println("Bem-vindo de volta, " + u.getNome());
            return u;
        }

        System.out.println("=== Cadastro de Usuário ===");

        String nome = InputUtil.lerString("Nome: ");
        String email = InputUtil.lerString("Email: ");
        String cidade = InputUtil.lerString("Cidade: ");

        Usuario novo = new Usuario(nome, email, cidade);

        UserRepository.salvar(novo);

        return novo;
    }

    // ================= EVENTOS =================

    static void cadastrarEvento() {

        System.out.println("\n=== Novo Evento ===");

        String nome = InputUtil.lerString("Nome: ");
        String end = InputUtil.lerString("Endereço: ");
        String desc = InputUtil.lerString("Descrição: ");

        System.out.println("0-FESTA 1-ESPORTIVO 2-SHOW 3-CULTURAL 4-OUTROS");
        Categoria cat = Categoria.values()[InputUtil.lerInt("Categoria: ")];

        LocalDateTime data = InputUtil.lerData();

        boolean alarme = InputUtil.lerString("Alarme? (s/n): ")
                .equalsIgnoreCase("s");

        eventos.add(new Evento(nome, end, cat, data, desc, alarme));

        System.out.println("Evento cadastrado!");
    }

    static void listarEventos() {

        System.out.println("\n=== Eventos ===");

        eventos.stream()
                .sorted(Comparator.comparing(Evento::getHorario))
                .forEach(e -> System.out.println(eventos.indexOf(e) + " - " + e));
    }

    static void participar() {

        listarEventos();

        System.out.println("\nDigite o número do evento para participar");
        System.out.println("Digite -1 para voltar");

        int i = InputUtil.lerInt("Opção: ");

        if (i == -1) return;

        if (i < 0 || i >= eventos.size()) {
            System.out.println("❌ Índice inválido!");
            return;
        }

        usuario.participar(eventos.get(i));

        System.out.println("✅ Participação confirmada!");
    }


    static void meusEventos() {

        System.out.println("\n=== Meus Eventos ===");

        usuario.getParticipando().forEach(System.out::println);
    }

    static void cancelarParticipacao() {

        List<Evento> meus = usuario.getParticipando();

        if (meus.isEmpty()) {
            System.out.println("Você não está participando de eventos.");
            return;
        }

        System.out.println("\n=== Cancelar Participação ===");

        for (int i = 0; i < meus.size(); i++) {
            System.out.println(i + " - " + meus.get(i));
        }

        System.out.println("\nDigite o número para cancelar");
        System.out.println("Digite -1 para voltar");

        int op = InputUtil.lerInt("Opção: ");

        if (op == -1) return;

        if (op < 0 || op >= meus.size()) {
            System.out.println("❌ Índice inválido!");
            return;
        }

        usuario.cancelar(meus.get(op));

        System.out.println("✅ Cancelado com sucesso!");
    }


    // ================= ALARME =================

    static void iniciarAlarmes() {

        new Thread(() -> {
            while (true) {

                LocalDateTime agora = LocalDateTime.now();

                for (Evento e : eventos) {

                    if (e.isAlarme() &&
                            e.getHorario().withSecond(0).withNano(0)
                                    .equals(agora.withSecond(0).withNano(0))) {

                        Toolkit.getDefaultToolkit().beep();

                        System.out.println("\n🔔 EVENTO COMEÇANDO: "
                                + e.getNome());
                    }
                }

                try {
                    Thread.sleep(60000);
                } catch (Exception ignored) {}
            }
        }).start();
    }
}
