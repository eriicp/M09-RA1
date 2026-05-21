import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Part1_LlegirSortida {
    public static void main(String[] args) {
        System.out.println("Part1");
        System.out.println("Sistema operatiu detectat: " + SO.nomSO());
        System.out.println("=== Contingut del directori ===");

        try {
            ProcessBuilder pb = new ProcessBuilder(SO.llistarFitxers());
            pb.redirectErrorStream(true);

            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("\nEl procés ha acabat amb codi: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}