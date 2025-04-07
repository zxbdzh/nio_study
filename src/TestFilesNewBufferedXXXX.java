import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestFilesNewBufferedXXXX {
    public static void main(String[] args) {
        Path path = Paths.get("test.txt");

        // Read file
        try (BufferedReader reader = Files.newBufferedReader(path);) {
           String line;
           while ((line = reader.readLine()) != null) System.out.println(line);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Write file
        Path outputPath = Paths.get("test1.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
           writer.write("test");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
