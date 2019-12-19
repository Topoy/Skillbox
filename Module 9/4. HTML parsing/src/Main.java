import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Main
{
    private static int fileName;
    public static void main(String[] args) throws IOException
    {
        Document document = Jsoup.connect("https://lenta.ru/").maxBodySize(0).get();
        Elements elements = document.select("img");

        elements.stream()
                .map(element -> element.attr("src"))
                .forEach(Main::loadImage);
    }

    private static void loadImage(String link)
    {
        if (!link.contains("http"))
        {
            link = "https:" + link;
        }
        try (InputStream in = new URL(link).openStream();)
        {
            fileName++;
            File file = new File("images");
            if (!file.exists())
            {
                file.mkdir();
            }
            if (link.contains("jpg"))
            {
                copyInImagesFolder(in, fileName, ".jpg");
            }
            else if (link.contains("png"))
            {
                copyInImagesFolder(in, fileName, ".png");
            }
            else {
                URLConnection connection = new URL(link).openConnection();
                String[] mimeTypeParts = connection.getContentType().split("/");
                String format = "." + mimeTypeParts[1];
                copyInImagesFolder(in, fileName, format);
            }
        }
        catch(IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    private static void copyInImagesFolder(InputStream in, int fileName, String fileFormat) throws IOException
    {
        Files.copy(in, Paths.get("images/" + fileName + fileFormat), StandardCopyOption.REPLACE_EXISTING);
    }
}
