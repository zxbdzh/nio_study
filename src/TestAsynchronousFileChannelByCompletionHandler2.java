import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

public class TestAsynchronousFileChannelByCompletionHandler2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        Path path = Paths.get("test.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        AtomicLong position = new AtomicLong(0);
        CountDownLatch latch = new CountDownLatch(1);

        fileChannel.read(buffer, position.get(), null, new CompletionHandler<Integer, Object>() {
            @Override
            public void completed(Integer result, Object attachment) {
                if (result > 0) {
                    position.addAndGet(result);
                    buffer.flip();
                    byte[] data = new byte[buffer.limit()];
                    buffer.get(data);
                    System.out.println(new String(data));
                    buffer.clear();
                } else {
                    latch.countDown();
                    try {
                        fileChannel.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("error: " + exc.getMessage());
                latch.countDown();
            }
        });
        latch.await();
    }
}
