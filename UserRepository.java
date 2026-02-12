import java.io.*;

public class UserRepository {

    private static final String FILE = "user.data";

    public static void salvar(Usuario u) throws Exception {

        BufferedWriter bw = new BufferedWriter(new FileWriter(FILE));

        bw.write(u.getNome() + ";" + u.getEmail() + ";" + u.getCidade());

        bw.close();
    }

    public static Usuario carregar() throws Exception {

        File f = new File(FILE);

        if (!f.exists()) return null;

        BufferedReader br = new BufferedReader(new FileReader(f));

        String[] p = br.readLine().split(";");

        br.close();

        return new Usuario(p[0], p[1], p[2]);
    }
}
