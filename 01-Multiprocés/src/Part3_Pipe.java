import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class Part3_Pipe {
    public static void main(String[] args) {
        System.out.println("Sistema operatiu detectat: " + SO.nomSO());
        System.out.println("=== Fitxers .java trobats ===");

        try {
            File directoriTreball = encontrarDirectorioConJava(new File("."));
            if (directoriTreball == null) {
                directoriTreball = new File("."); // Fallback de seguretat
            }

            ProcessBuilder pbLlistar = new ProcessBuilder(SO.llistarFitxers());
            pbLlistar.directory(directoriTreball); // Li assignem la ruta infal·lible

            ProcessBuilder pbFiltrar = new ProcessBuilder(SO.filtrar(".java"));

            List<ProcessBuilder> builders = Arrays.asList(pbLlistar, pbFiltrar);
            List<Process> pipeline = ProcessBuilder.startPipeline(builders);

            Process darrerProces = pipeline.get(pipeline.size() - 1);
            
            boolean trobat = false;
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(darrerProces.getInputStream(), StandardCharsets.UTF_8))) {
                
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    trobat = true;
                }
            }

            if (!trobat) {
                System.out.println("No s'han trobat fitxers .java al directori: " + directoriTreball.getAbsolutePath());
            }

            for (Process p : pipeline) {
                p.waitFor();
            }

            System.out.println("\nPipeline completat.");

        } catch (IOException | InterruptedException e) {
            System.err.println("Error durant l'execució: " + e.getMessage());
        }
    }

    private static File encontrarDirectorioConJava(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    File result = encontrarDirectorioConJava(file);
                    if (result != null) {
                        return result;
                    }
                } else if (file.getName().endsWith(".java")) {
                    return dir;
                }
            }
        }
        return null;
    }
}