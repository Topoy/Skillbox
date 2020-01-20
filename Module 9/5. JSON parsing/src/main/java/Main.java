import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main
{
    private static List<Station> stationList = new ArrayList<>();

    public static void main(String[] args) throws IOException

    {
        ///////////////////////////////////////////Парсинг HTML с метро/////////////////////////////////////////////////
        Document document = Jsoup.connect("https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0").maxBodySize(0).get();
        Element table = document.select("table:has(span a[title='Бульвар Рокоссовского (станция метро)'])").first();
        Elements rows = table.select("tr:nth-of-type(1n+2)"); //пропуск первого tr с названиями колонок таблицы
        rows.stream().forEach(row -> {
            String stationName = row.select("td:nth-child(2)").first().text();
            String lineName = row.select("td:nth-child(1) span").attr("title");
            String lineNumber = row.select("td:first-child").attr("data-sort-value");
            System.out.println("Название станции: " + stationName + "; Название линии: " + lineName + "; Номер линии: " + lineNumber);
            stationList.add(new Station(stationName, lineNumber));
        });

        for (Station station : stationList)
        {
            System.out.println("Название станции: " + station.getName() + "; Номер линии: " + station.getLine());
        }

        ///////////////////////////////////////////////Создание JSON файла//////////////////////////////////////////////
        JSONObject moscowMetroMap = new JSONObject();
        JSONObject stations = new JSONObject();
        JSONArray stationsArray;

        Map<String, List<Station>> stationsByLine = stationList.stream().collect(Collectors.groupingBy(Station::getLine));

        for (Map.Entry<String, List<Station>> item : stationsByLine.entrySet())
        {
            stationsArray = new JSONArray();
            System.out.println(item.getKey());
            for (Station station : item.getValue())
            {
                System.out.println(station.getName());
                stationsArray.add(station.getName());
            }
            stations.put(item.getKey(), stationsArray);
        }

        moscowMetroMap.put("stations", stations);

        try (FileWriter file = new FileWriter("map.json"))
        {
            file.write(moscowMetroMap.toJSONString());
            file.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
