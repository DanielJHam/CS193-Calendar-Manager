import java.io.*;

public class ReaderWriter {
    public static String[] readInfo(){
        String[] output = new String[4];
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

    public static void writeError(String message){
        try(PrintWriter pw = new PrintWriter(new FileWriter("src/Error.txt", true),true)) {
            pw.println(message);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}