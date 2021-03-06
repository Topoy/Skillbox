import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main
{
    private static String dataFile = "map.json";
    private static List<List<Station>> connectionList = new ArrayList<>();
    private static List<Station> stationList = new ArrayList<>();
    private static Map<String, String> lineList = new LinkedHashMap<>();
    private static String link = "https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0";

    public static void main(String[] args) throws IOException
    {
        ///////////////////////////////////////////Парсинг HTML с метро/////////////////////////////////////////////////
        Document document = Jsoup.connect(link).maxBodySize(0).get();
        Element table = document.select("table:has(span a[title='Бульвар Рокоссовского (станция метро)'])").first();
        Elements rows = table.select("tr:nth-of-type(1n+2)"); //пропуск первого tr с названиями колонок таблицы
        rows.forEach(row -> {
            String stationName = row.select("td:nth-child(2)").first().text();
            String lineName = row.select("td:nth-child(1) span").attr("title");
            String lineNumber = row.select("td:first-child span:nth-child(1)").text();
            if (row.select("td:first-child").attr("data-sort-value").equals("8.9"))
            {
                String additionalLineNumber = row.select("td:first-child span:nth-child(4)").text();
                stationList.add(new Station(stationName, additionalLineNumber));
                lineList.put(additionalLineNumber, lineName);
            }
            stationList.add(new Station(stationName, lineNumber));
            lineList.put(lineNumber, lineName);
            try {
                connectionParser(row, stationName, lineNumber);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ///////////////////////////////////////////////Создание JSON файла//////////////////////////////////////////////
        LinkedHashMap<String, JSONArray> stations = new LinkedHashMap<>();
        JSONArray stationsArray;
        LinkedHashSet<JSONObject> jsonLines = new LinkedHashSet<>();
        LinkedList<JSONArray> jsonConnections = new LinkedList<>();
        //////////////////////////////////////////////Парсинг станций///////////////////////////////////////////////////
        Map<String, List<Station>> stationsByLine = stationList.stream()
                .collect(Collectors.groupingBy(
                        Station::getLine,
                        () -> new TreeMap<>(Main.comparator),
                        Collectors.toList()));
        for (Map.Entry<String, List<Station>> item : stationsByLine.entrySet())
        {
            stationsArray = new JSONArray();
            for (Station station : item.getValue())
            {
                stationsArray.add(station.getName());
            }
            stations.put(item.getKey(), stationsArray);
        }
        /////////////////////////////////////////////////Парсинг линий//////////////////////////////////////////////////
        for (Map.Entry<String, String> item : lineList.entrySet())
        {
            JSONObject lineDetails = new JSONObject();
            lineDetails.put("number", item.getKey());
            lineDetails.put("name", item.getValue());
            jsonLines.add(lineDetails);
        }
        ////////////////////////////////////////////////Парсинг соединений//////////////////////////////////////////////
        for (List<Station> item : connectionList)
        {
            JSONArray connectionDetails = new JSONArray();
            for (Station item1 : item)
            {
                JSONObject connectionObject = new JSONObject();
                connectionObject.put("line", item1.getLine());
                connectionObject.put("station", item1.getName());
                connectionDetails.add(connectionObject);
            }
            jsonConnections.add(connectionDetails);
        }

        LinkedHashMap<String, Object> moscowMetroStations = new LinkedHashMap<>();
        moscowMetroStations.put("stations", stations);
        moscowMetroStations.put("lines", jsonLines);
        moscowMetroStations.put("connections", jsonConnections);

        String advancedJson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
                                               .toJson(moscowMetroStations, LinkedHashMap.class);

        try (FileWriter file = new FileWriter("map.json"))
        {
            file.write(advancedJson);
            file.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        parseStations();
    }

    ///////////////////////////////////////////////////////Компаратор для записи в JSON/////////////////////////////////
    private static Comparator<String> comparator = new Comparator<String>()
    {
        double additionalNum = 0.0;
        @Override
        public int compare(String o1, String o2)
        {
            {
                return Double.compare(extractDouble(o1) - extractDouble(o2), 0.0);
            }
        }
        double extractDouble(String s)
        {
            if (s.contains("А"))
            {
                additionalNum = 0.1;
            }
            String num = s.replaceAll("\\D", "");
            double result = num.isEmpty() ? 0 : (Double.parseDouble(num) + additionalNum);
            additionalNum = 0;
            return result;
        }
    };

    private static void connectionParser(Element row, String stationName, String lineName) throws IOException
    {
        List<String> connectionsLine = row.select("td:nth-child(4) span").eachText();
        List<String> connectionNames = advancedConvertConnectionName(row);
        if (connectionNames.size() != 0)
        {
            List<Station> temp = new ArrayList<>();
            temp.add(new Station(stationName, lineName));
            for (int i = 0; i < connectionNames.size(); i++)
            {
                temp.add(new Station(connectionNames.get(i), connectionsLine.get(i)));
            }
            connectionList.add(temp);
        }
    }

    private static String getJsonFile()
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            List<String> lines = Files.readAllLines(Paths.get(dataFile));
            lines.forEach(sb::append);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return sb.toString();
    }

    private static void parseStations()
    {
        try
        {
            JSONParser parser = new JSONParser();
            JSONObject jsonData = (JSONObject) parser.parse(getJsonFile());
            JSONObject stationsObject = (JSONObject) jsonData.get("stations");
            stationsObject.keySet().forEach(lineNumberObject ->
            {
                String[] stationNumbers = stationsObject.get(lineNumberObject).toString().split(",");
                System.out.println("Номер линии: " + lineNumberObject.toString() + "; Количество станций: " + stationNumbers.length);
            });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private static List<String> advancedConvertConnectionName(Element rows)
    {
        final String regex = "(Переход на станцию|Кросс-платформенная пересадка на станцию)\\s+(?<station>.+)\\s+(\\p{IsCyrillic}+\\s+линии)";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        List<String> fullConnectionsNames = rows.select("td:nth-child(4) a").eachAttr("title");
        List<String> pureConnectionsNames = new ArrayList<>();
        for (String string : fullConnectionsNames)
        {
            final Matcher matcher = pattern.matcher(string);
            while (matcher.find()) {
                pureConnectionsNames.add(matcher.group(2));
            }
        }
        return pureConnectionsNames;
    }
}
