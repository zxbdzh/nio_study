import java.nio.file.Path;
import java.nio.file.Paths;

public class TestPathsAndPath {
    public static void main(String[] args) {
        Path path = Paths.get("doc/test.txt");

        // 获取文件名
        System.out.println("File name: " + path.getFileName());

        // 获取父目录
        System.out.println("Parent: " + path.getParent());

        // 获取根目录
        System.out.println("Root: " + path.getRoot());

        // 将路径与另一个路径结合
        Path newPath = path.resolve("config/app.properties");
        System.out.println("Resolved path: " + newPath);

        // 将相对路径转为绝对路径
        Path absolutePath = path.toAbsolutePath();
        System.out.println(absolutePath);

        // 计算两个路径之间的相对路径
        Path basePath = Paths.get("/docs/");
        Path targetPath = Paths.get("/docs/image/girl");
        Path relativePath = basePath.relativize(targetPath);
        System.out.println("Relative path: " + relativePath);
    }
}
