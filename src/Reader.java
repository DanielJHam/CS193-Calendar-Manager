import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
public class Reader {

    public static String[] readInfo(){
        String[] output = new String[5];
            try (BufferedReader br = new BufferedReader(new FileReader("src/info.txt"));
                 PrintWriter pw = new PrintWriter(new FileWriter("src/Error.txt"), true)) {
                for (int index = 0; index < output.length; index++) {
                    String line = br.readLine();
                    int colon = line.indexOf(':');
                    if (colon < 0) {
                       pw.println("Info is done wrong");
                       pw.flush();
                       throw new IOException();
                    }
                    output[index] = line.substring(colon + 1);
                }
                return output;
            } catch (IOException i) {
                i.printStackTrace();
            }

        return output;
    }
}