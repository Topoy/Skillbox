import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        long bytes_in_one_kilobyte = 1024;
        long bytes_in_one_megabyte = bytes_in_one_kilobyte * 1024;
        while (true) {
            System.out.println("Пожалуйста, напишите путь к папке");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String path = reader.readLine();
            if (path.equals("exit"))
            {
                break;
            }
            File folder = new File(path);
            System.out.println("Размер файлов в папке составляет: " + folderSize(folder)/bytes_in_one_megabyte + " мегабайт");
        }
    }
    public static long folderSize(File directory)
    {
        long size = 0;
        for (File file : directory.listFiles())
        {
            if (file.isFile())
            {
                size += file.length();
            }
            else
            {
                size += folderSize(file);
            }
        }
        return size;
    }
}
