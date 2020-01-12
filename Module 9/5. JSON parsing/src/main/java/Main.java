import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        Document document = Jsoup.connect("https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0").maxBodySize(0).get();
        Element table = document.select("table").get(3);

        Elements lines = table.select("tr");
        lines.forEach(System.out::println);
        lines.stream().skip(1).forEach(line ->
        {
            Elements elements = line.select("td");
            String stationName = elements.get(1).text();
            String lineName = elements.get(0).child(1).attr("title");  //child() - подпункты выбранного элемента. 1 строка = 1 подпункт
            System.out.println("Станция: " + stationName + "; Линия: " + lineName);
        });

    }
}
