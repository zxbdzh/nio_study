import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

public class NioFileDemo {
    public static void main(String[] args) {
        NioFileDemo nioFileDemo = new NioFileDemo();
        nioFileDemo.writeFile();
        nioFileDemo.readFile();
    }

    // 使用NIO写入文件
    public void writeFile() {
        Path path = Paths.get("test.txt");
        try (
                FileChannel fileChannel = FileChannel.open(path, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE));
        ) {
            ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode("写入NIO");
            fileChannel.write(byteBuffer);
            System.out.println("写入完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFile() {
        Path path = Paths.get("test.txt");
        try (
                FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ);
        ) {
           ByteBuffer byteBuffer = ByteBuffer.allocate(1024) ;

           int bytesRead = fileChannel.read(byteBuffer);
           while (bytesRead != -1) {
               byteBuffer.flip();
               System.out.println("读取的内容: " + StandardCharsets.UTF_8.decode(byteBuffer));
               byteBuffer.clear();
               bytesRead = fileChannel.read(byteBuffer);
           }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
