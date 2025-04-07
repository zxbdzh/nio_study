import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestPathsAndFiles {
    public static void main(String[] args) throws IOException {
        // 创建一个Path示例
        Path path = Paths.get("doc/test1.txt");

        // 创建一个新文件
        Files.createFile(path);

        // 检查文件是否存在
        boolean exists = Files.exists(path);
        System.out.println("File exists: " + exists);

        // 删除文件
        Files.delete(path);
    }
}
