import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("Пожалуйста, введите путь директории, которую хотите скопировать");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String origin = reader.readLine();
        System.out.println("Пожалуйста, введите путь директории, в которую хотите скопировать");
        String target = reader.readLine();

        Path targetPath = Paths.get(target);
        Path originPath = Paths.get(origin);

        Files.walkFileTree(originPath, new SimpleFileVisitor<>()
        {
           @Override
           public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException
           {
               Files.createDirectories(targetPath.resolve(originPath.relativize(dir)));
               return FileVisitResult.CONTINUE;
           }
           /*
           preVisitDirectory необходим для выполнения заданного функционала перед заходом в конкретную папку.
           Данный функционал состоит в следующем: создаётся директория в папке куда пользователь хочет скопировать файлы.
           С помощью метода resolve к пути targetPath прибавляется разница между путем originPath и путем dir. Таким
           образом сохраняется структура исходной папки.
           Например: копируемая папка: C:/test (содержимое папки: C:/test/New folder)
                     папка в которую копируют: C:/test1
                     С помощью метода createDirectories будет создана папка С:/test1/New folder
            */
           @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException
           {
               Files.copy(file, targetPath.resolve(originPath.relativize(file)), REPLACE_EXISTING);
               return FileVisitResult.CONTINUE;
               /*
               visitFile выполняет заданный функционал во время посещения папок/файлов. В данном случае происходит
               копирование указнного файла в папку куда копируют файл с соответствующей ему поддиректорией, которая была
               создана в preVisitDirectory.
                */
           }
        });
    }
}
