import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        long bytesInOneKilobyte = 1024;
        long bytesInOneMegabyte = bytesInOneKilobyte * 1024;
        while (true) {
            System.out.println("Пожалуйста, напишите путь к папке");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String path = reader.readLine();
            if (path.equals("exit"))
            {
                break;
            }

            System.out.format("Размер файлов в папке составляет: %.2f" + " мегабайт%n",
                              (double) folderSizeVisit(path)/bytesInOneMegabyte);

        }
    }
    public static long folderSize(File directory)
    {
        long size = 0;
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                size += file.length();
            } else {
                size += folderSize(file);
            }
        }
        return size;
    }

    public static long folderSizeVisit(String path) throws IOException
    {
        final long[] size = {0};
        Path folder = Paths.get(path);
        Files.walkFileTree(folder, new SimpleFileVisitor<>()
        {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                size[0] += attrs.size();
                return FileVisitResult.CONTINUE;
            }
            public @Override FileVisitResult visitFileFailed(Path file, IOException e)
            {
                System.out.println("Пропущен файл: " + file + ". Причина: " + e);
                return FileVisitResult.CONTINUE;
            }
        }
        );

        return size[0];
    }
}
