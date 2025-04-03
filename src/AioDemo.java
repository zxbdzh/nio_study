import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AioDemo {
    public static void main(String[] args) {
        AioDemo aioDemo = new AioDemo();
        aioDemo.writeFile();
        aioDemo.readFile();
    }

    // 使用 AsynchronousFileChannel 写入文件
    public void writeFile() {
        // 使用 Paths.get() 获取文件路径
        Path path = Paths.get("test.txt");
        try (
                // 用 AsynchronousFileChannel.open() 打开文件通道, 指定写入和创建文件的选项
                AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        ) {
            //  将要写入的字符串转换为 ByteBuffer
            ByteBuffer buffer = StandardCharsets.UTF_8.encode("Aio写入");
            // 调用 write() 方法写入文件。这是一个异步操作，需要使用 Future 对象等待写入操作完成
            Future<Integer> result = fileChannel.write(buffer, 0);
            // 等待写操作完成
            result.get();

            System.out.println("写入完成");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // 读取文件
    public void readFile() {
        Path path = Paths.get("test.txt");
        try (
                AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        ) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            fileChannel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    attachment.flip();
                    System.out.println("读取到的内容" + StandardCharsets.UTF_8.decode(attachment));
                    attachment.clear();
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    System.out.println("读取失败");
                    exc.printStackTrace();
                }
            });
            Thread.sleep(1000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
