import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;

public class Part2_EntradaSortida {
    public static void main(String[] args) {
        System.out.println("Part2");
        System.out.println("Sistema operatiu detectat: " + SO.nomSO());
        
        String[] fruites = {"plàtan", "poma", "albergínia", "cireres", "maduixa"};

        try {
            ProcessBuilder pb = new ProcessBuilder(SO.ordenar());
            pb.redirectErrorStream(true);
            Process process = pb.start();

            System.out.println("Enviem al procés 'sort':");
            
            PrintWriter writer = new PrintWriter(process.getOutputStream());
            for (String fruita : fruites) {
                System.out.println("-> " + fruita);
                writer.println(fruita);
            }
            writer.close(); 

            System.out.println("\nResposta del procés 'sort' (ordenat):");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("<- " + line);
            }

            int exitCode = process.waitFor();
            System.out.println("\nCodi de retorn: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}