import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FindFileWithWalkFileTree {
    public static void main(String[] args) {
        Path startingDir = Paths.get("doc");
        String targetFileName = "test.txt";
        FindFileVisitor findFileVisitor = new FindFileVisitor(targetFileName);
        try {
            Files.walkFileTree(startingDir, findFileVisitor);
            if (findFileVisitor.isFileFound()) System.out.println("File found: " + findFileVisitor.getFoundFilePath());
            else System.out.println("File not found.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class FindFileVisitor extends SimpleFileVisitor<Path> {
        private String targetFileName;
        private Path foundFilePath;

        public FindFileVisitor(String targetFileName) {
            this.targetFileName = targetFileName;
        }

        public boolean isFileFound() {
            return foundFilePath != null;
        }

        public Path getFoundFilePath() {
            return foundFilePath;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            String fileName = file.getFileName().toString();
            if (fileName.equals(targetFileName)) {
                foundFilePath = file;
                return FileVisitResult.TERMINATE;
            }
            return FileVisitResult.CONTINUE;
        }
    }
}
