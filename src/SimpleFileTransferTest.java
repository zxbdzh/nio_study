import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

public class SimpleFileTransferTest {

    public static void main(String[] args) {
        SimpleFileTransferTest simpleFileTransferTest = new SimpleFileTransferTest();
        File source = new File("kugga.mp4");
        File des = new File("kamen rider.mp4");
        File nio = new File("nio.mp4");

        long time = simpleFileTransferTest.transferFile(source, des);
        System.out.println(time + ": 普通直接流时间");

        long timeNio = simpleFileTransferTest.transferFileWithNIO(source, nio);
        System.out.println(timeNio + ": NIO 时间");
    }

    /**
     * 使用传统 IO 方式传输文件
     *
     * @param source
     * @param des
     * @return
     * @throws IOException
     */
    private long transferFile(File source, File des) {
        long startTime = System.currentTimeMillis();
        try (
                BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(source.toPath()));
                BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(des.toPath()));
        ) {

            if (!des.exists()) des.createNewFile();

            // 创建输入输出流

            // 使用数组传输数据
            byte[] bytes = new byte[1024 * 1024];
            int len;
            while ((len = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    /**
     * 使用 NIO 方式传输文件
     *
     * @param source
     * @param des
     * @return
     * @throws IOException
     */
    private long transferFileWithNIO(File source, File des) {
        long startTime = System.currentTimeMillis();

        try (
                // 创建随机存取文件对象
                RandomAccessFile read = new RandomAccessFile(source, "rw");
                RandomAccessFile write = new RandomAccessFile(des, "rw");

                // 获取文件通道
                FileChannel readChannel = read.getChannel();
                FileChannel writeChannel = write.getChannel();
        ) {
            if (!des.exists()) des.createNewFile();

            // 创建并使用 ByteBuffer 传输数据
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);
            while (readChannel.read(byteBuffer) > 0) {
                byteBuffer.flip();
                writeChannel.write(byteBuffer);
                byteBuffer.clear();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

}
