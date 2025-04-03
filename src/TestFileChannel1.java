import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestFileChannel1 {
    public static void main(String[] args) {
        try (
                FileChannel sourceChannel = FileChannel.open(Paths.get("test.txt"), StandardOpenOption.READ);
                FileChannel destinationChannel = FileChannel.open(Paths.get("test1.txt"), StandardOpenOption.WRITE,StandardOpenOption.CREATE_NEW);
        ) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (sourceChannel.read(buffer) != -1) {
                buffer.flip();
                destinationChannel.write(buffer);
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
