import java.io.*;
import java.time.*;
import java.util.*;

public class EventRepository {

    private static final String FILE = "events.data";

    public static void salvar(List<Evento> eventos) throws Exception {

        BufferedWriter bw = new BufferedWriter(new FileWriter(FILE));

        for (Evento e : eventos) {
            bw.write(e.getNome() + ";" +
                    e.getHorario() + ";" +
                    e.isAlarme());
            bw.newLine();
        }

        bw.close();
    }

    public static List<Evento> carregar() throws Exception {

        List<Evento> lista = new ArrayList<>();

        File f = new File(FILE);
        if (!f.exists()) return lista;

        BufferedReader br = new BufferedReader(new FileReader(f));

        String l;

        while ((l = br.readLine()) != null) {

            String[] p = l.split(";");

            lista.add(new Evento(
                    p[0],
                    "",
                    Categoria.OUTROS,
                    LocalDateTime.parse(p[1]),
                    "",
                    Boolean.parseBoolean(p[2])
            ));
        }

        br.close();

        return lista;
    }
}
