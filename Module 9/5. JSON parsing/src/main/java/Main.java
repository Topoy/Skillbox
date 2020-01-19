import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        ///////////////////////////////////////////////Создание JSON файла//////////////////////////////////////////////
        JSONObject moscowMetroMap = new JSONObject();
        //moscowMetroMap.put("stations", );
        //moscowMetroMap.put("lines", );
        //в moscowMetroMap положим станции и линии
        JSONObject stations = new JSONObject();
        JSONArray arrayStations = new JSONArray();

        TreeMap<String, String> stationsByLineNumber = new TreeMap<>();

        String previousLineNumber = "1";
        ///////////////////////////////////////////Парсинг HTML с метро/////////////////////////////////////////////////
        Document document = Jsoup.connect("https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0").maxBodySize(0).get();
        Element table = document.select("table:has(span a[title='Бульвар Рокоссовского (станция метро)'])").first();
        Elements rows = table.select("tr:nth-of-type(1n+2)"); //пропуск первого tr с названиями колонок таблицы
        rows.stream().forEach(row -> {
            String stationName = row.select("td:nth-child(2)").first().text();
            String lineName = row.select("td:nth-child(1) span").attr("title");
            String lineNumber = row.select("td:nth-child(1)").attr("data-sort-value");
           // System.out.println("Название станции: " + stationName + "; Название линии: " + lineName + "; Номер линии: " + lineNumber);
            stationsByLineNumber.put(lineNumber, stationName);

        });
        System.out.println(stationsByLineNumber);

    }
}
