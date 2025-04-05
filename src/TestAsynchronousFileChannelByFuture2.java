import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TestAsynchronousFileChannelByFuture2 {
    public static void main(String[] args) {
        Path path = Paths.get("test.txt");
        try (
                AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ)
        ) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            long position = 0;

            while (true) {
                Future<Integer> result = fileChannel.read(buffer, position);

                while (!result.isDone()) {
                    System.out.println("执行其他操作");
                }

                int bytesRead = result.get();
                if (bytesRead <= 0) break;

                position += bytesRead;
                buffer.flip();

                byte[] data = new byte[buffer.limit()];
                buffer.get(data);
                System.out.println(new String(data));

                buffer.clear();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
