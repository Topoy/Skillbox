import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
        try
        {
            fileName++;
            InputStream in = new URL(link).openStream();
            File file = new File("images");
            if (!file.exists())
            {
                file.mkdir();
            }
            if (link.contains("jpg"))
            {
                Files.copy(in, Paths.get("images/" + fileName + ".jpg"), StandardCopyOption.REPLACE_EXISTING);
            }
            else if (link.contains("png"))
            {
                Files.copy(in, Paths.get("images/" + fileName + ".png"), StandardCopyOption.REPLACE_EXISTING);
            }
            else Files.copy(in, Paths.get("images/" + fileName + ".gif"), StandardCopyOption.REPLACE_EXISTING);
            in.close();
        }
        catch(IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }
}
