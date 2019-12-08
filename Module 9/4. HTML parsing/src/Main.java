import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Main
{
    //private static File file = new File("images");

    public static void main(String[] args) throws IOException
    {
        ArrayList<String> links = new ArrayList<>(); //список ссылок на картинки

        Document doc = Jsoup.connect("https://lenta.ru/").get();

        Elements elements = doc.select("img");

        elements.forEach(element -> System.out.println(element.attr("src")));

        elements.forEach(element -> links.add(element.attr("src")));

        links.forEach(System.out::println);

        for (String link : links) {
            loadImage(link);
        }

    }

    public static void loadImage(String link)
    {
        try
        {
            BufferedImage image = ImageIO.read(new URL(link));
            ImageIO.write(image, "jpg", new File("images/" + link.substring(link.length()-10)));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
