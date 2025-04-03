import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestFileChannel3_1 {
    public static void main(String[] args) {
        Path sourcePath = Paths.get("test.txt");
        Path destinationPath = Paths.get("test1.txt");

        try (
                FileChannel sourceChannel = FileChannel.open(sourcePath, StandardOpenOption.READ);
                FileChannel destinationChannel = FileChannel.open(destinationPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        ) {
            long position = 0;
            long count = sourceChannel.size();

            // 循环传输
            while (position < count) {
                long transferred = sourceChannel.transferTo(position, count - position, destinationChannel);
                position += transferred;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
