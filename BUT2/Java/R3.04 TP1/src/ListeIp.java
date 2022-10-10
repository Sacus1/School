import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class ListeIp {
    public Set<AdresseIp> ips;

    public ListeIp(boolean isSorted) {
        if (isSorted) {
            ips = new TreeSet<>();
        } else {
            ips = new HashSet<>();
        }
    }

    public void chargerFichier(String name) throws IOException {
        File file = new File(name);
        if (file.exists()) {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                ips.add(new AdresseIp(line.split(" ")[0]));
            }
            br.close();
            fr.close();
        } else {
            throw new FileNotFoundException();
        }
    }
}
