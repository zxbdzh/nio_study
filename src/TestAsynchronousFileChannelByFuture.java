import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TestAsynchronousFileChannelByFuture {
    public static void main(String[] args) {
        try {
            AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(Paths.get("kugga.mp4"), StandardOpenOption.READ);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            long position = 0;
            Future<Integer> result = fileChannel.read(buffer, position);
            while (!result.isDone()) {
                // 执行其他操作
                System.out.println("执行其他操作");
            }
            int bytesRead = result.get();
            System.out.println("Bytes read: " + bytesRead);
        } catch (IOException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
