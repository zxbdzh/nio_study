import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestFileChannel3 {
    public static void main(String[] args) {
        try (
                FileChannel sourceChannel = FileChannel.open(Paths.get("test.txt"), StandardOpenOption.READ);
                FileChannel destinationChannel = FileChannel.open(Paths.get("test1.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.READ);
        ) {
            sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
