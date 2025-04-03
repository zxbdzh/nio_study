import java.io.*;

public class BioFileDemo {
    public static void main(String[] args) {
        BioFileDemo bioFileDemo = new BioFileDemo();
        bioFileDemo.writeFile();
        bioFileDemo.readFile();
    }

    // 使用BIO吸入文件
    public void writeFile() {
        String fileName = "test.txt";
        try (
                FileWriter fileWriter = new FileWriter(fileName);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        ) {
            bufferedWriter.write("BIO写入");
            bufferedWriter.newLine();

            System.out.println("写入完成");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 使用BIO读取文件
    public void readFile() {
        String fileName = "test.txt";
        try (
                FileReader fileReader = new FileReader(fileName);
                BufferedReader bufferedWriter = new BufferedReader(fileReader);
        ) {
            String line;
            while ((line = bufferedWriter.readLine()) != null) System.out.println("读取的内容: " + line);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
