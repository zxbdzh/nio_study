import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestFileChannel2 {
    public static void main(String[] args) {
        try (
                FileChannel sourceChannel = FileChannel.open(Paths.get("test.txt"), StandardOpenOption.READ);
                FileChannel destinationChannel = FileChannel.open(Paths.get("test1.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.READ);
        ) {
            long fileSize = sourceChannel.size();
            MappedByteBuffer sourceMappedBuffer = sourceChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileSize);
            MappedByteBuffer destinationMappedBuffer = destinationChannel.map(FileChannel.MapMode.READ_WRITE, 0, fileSize);

            for (int i = 0; i < fileSize; i++) {
                byte b = sourceMappedBuffer.get(i);
                destinationMappedBuffer.put(i, b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
