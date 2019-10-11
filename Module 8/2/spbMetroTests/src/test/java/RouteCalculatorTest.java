import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteCalculatorTest extends TestCase
{
    List<Station> route;
    private StationIndex stationIndex;
    private List<Station> connectedStations;
    private ArrayList<String> stationNames = new ArrayList<>();
    private RouteCalculator calculator;

    private Line line1;
    private Line line2;
    private Line line3;
    private Line line4;

    private Station blackRiver;
    private Station petrGrad;
    private Station gorkovskaya;
    private Station mayakovskaya;
    private Station begovaya;
    private Station vasileostrovskaya;
    private Station ozerki;
    private Station liteyniy;
    private Station centralnaya;
    private Station pushkinskaya;

    @Override
    protected void setUp() throws Exception
    {
        stationIndex = new StationIndex();
        calculator  = new RouteCalculator(stationIndex);
        route = new ArrayList<>();
        connectedStations = new ArrayList<>();
        stationNames.addAll(Arrays.asList("Чёрная речка", "Петроградская", "Горьковская", "Маяковская", "Беговая",
                                          "Василеостровская", "Озерки", "Литейный"));

        line1 = new Line(1, "Первая");
        line2 = new Line(2, "Вторая");
        line3 = new Line(3, "Третья");
        line4 = new Line(4, "Четвёртая");

        blackRiver = new Station("Чёрная речка", line1);
        petrGrad = new Station("Петроградская", line1);
        gorkovskaya = new Station("Горьковская", line1);
        mayakovskaya = new Station("Маяковская", line2);
        begovaya = new Station("Беговая", line2);
        vasileostrovskaya = new Station("Василеостровская", line2);
        ozerki = new Station("Озерки", line3);
        liteyniy = new Station("Литейный", line3);
        centralnaya = new Station("Центральная", line4);
        pushkinskaya = new Station("Пушкинская", line4);


        route.add(blackRiver);
        route.add(petrGrad);
        route.add(mayakovskaya);
        route.add(begovaya);

        ///////////////////////////////////Добавление линий тестового метро/////////////////////////////////////////////
        stationIndex.addLine(line1);
        stationIndex.addLine(line2);
        stationIndex.addLine(line3);

        //////////////////////////Добавление станций тестового метро к stationIndex/////////////////////////////////////
        stationIndex.addStation(blackRiver);
        stationIndex.addStation(petrGrad);
        stationIndex.addStation(gorkovskaya);
        stationIndex.addStation(mayakovskaya);
        stationIndex.addStation(begovaya);
        stationIndex.addStation(vasileostrovskaya);
        stationIndex.addStation(ozerki);
        stationIndex.addStation(liteyniy);
        stationIndex.addStation(centralnaya);
        stationIndex.addStation(pushkinskaya);

        /////////////////////////Добавление привязки станций тестового метро к линиям метро/////////////////////////////
        line1.addStation(blackRiver);
        line1.addStation(petrGrad);
        line1.addStation(gorkovskaya);
        line2.addStation(mayakovskaya);
        line2.addStation(begovaya);
        line2.addStation(vasileostrovskaya);
        line3.addStation(ozerki);
        line3.addStation(liteyniy);
        line4.addStation(centralnaya);
        line4.addStation(pushkinskaya);

        //////////////////////////////////Добавление соединений станций тестового метро/////////////////////////////////

        stationIndex.addConnection(Arrays.asList(blackRiver, mayakovskaya));
        stationIndex.addConnection(Arrays.asList(petrGrad, centralnaya, ozerki));

        stationIndex.addConnection(Arrays.asList(begovaya, liteyniy));
        //stationIndex.addConnection(Arrays.asList(petrGrad, centralnaya));
        //stationIndex.addConnection(Arrays.asList(ozerki, centralnaya));


        //List<Station> actualShortestRoute = calculator.getShortestRoute(petrGrad, vasileostrovskaya);
        //System.out.println(actualShortestRoute);
        //System.out.println(blackRiver.getLine().getStations());
    }

    public void testCalculateDuration()
    {
        double actual = RouteCalculator.calculateDuration(route);
        double expected = 8.5;
        assertEquals(expected, actual);
    }

    public void testGetShortestRoute()
    {
        List<Station> actualShortestRoute = calculator.getShortestRoute(blackRiver, vasileostrovskaya);
        List<Station> expectedShortestRoute = Arrays.asList(blackRiver, mayakovskaya, begovaya, vasileostrovskaya);
        assertEquals(expectedShortestRoute, actualShortestRoute);
    }

    public void testGetRouteWithOneConnection()
    {
        List<Station> actualRouteWithOneConnection = calculator.getRouteWithOneConnection(blackRiver, begovaya);
        List<Station> expectedRouteWithOneConnection = Arrays.asList(blackRiver, mayakovskaya, begovaya);
        assertEquals(expectedRouteWithOneConnection, actualRouteWithOneConnection);
    }
    public void testGetRouteViaConnectedLine()
    {
        List<Station> actualRouteViaConnectedLine = calculator.getRouteViaConnectedLine(blackRiver, liteyniy);
        List<Station> expectedRouteViaConnectedLine = Arrays.asList(mayakovskaya, begovaya);
        assertEquals(expectedRouteViaConnectedLine, actualRouteViaConnectedLine);
        System.out.println(actualRouteViaConnectedLine);
    }

    public void testGetRouteWithTwoConnections()
    {
        List<Station> actualRouteWithTwoConnections = calculator.getRouteWithTwoConnections(gorkovskaya, pushkinskaya);
        System.out.println(actualRouteWithTwoConnections);
        //List<Station> expectedRouteWithTwoConnections = Arrays.asList(blackRiver, petrGrad, centralnaya, pushkinskaya);
        //assertEquals(expectedRouteWithTwoConnections, actualRouteWithTwoConnections);
    }



    @Override
    protected void tearDown() throws Exception
    {

    }

    public static void printRoute(List<Station> route)
    {
        Station previousStation = null;
        for(Station station : route)
        {
            if(previousStation != null)
            {
                Line prevLine = previousStation.getLine();
                Line nextLine = station.getLine();
                if(!prevLine.equals(nextLine))
                {
                    System.out.println("\tПереход на станцию " +
                            station.getName() + " (" + nextLine.getName() + " линия)");
                }
            }
            System.out.println("\t" + station.getName());
            previousStation = station;
        }
    }
/*
    private static RouteCalculator getRouteCalculator()
{
    createStationIndex();
    return new RouteCalculator(stationIndex);
}

    private static String getJsonFile()
    {
        StringBuilder builder = new StringBuilder();
        try
        {
            List<String> lines = Files.readAllLines(Paths.get(dataFile));
            lines.forEach(line -> builder.append(line));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return builder.toString();
    }

    private static void createStationIndex()
    {
        stationIndex = new StationIndex();
        try
        {
            JSONParser parser = new JSONParser();
            JSONObject jsonData = (JSONObject) parser.parse(getJsonFile());

            JSONArray linesArray = (JSONArray) jsonData.get("lines");
            parseLines(linesArray);

            JSONObject stationsObject = (JSONObject) jsonData.get("stations");
            parseStations(stationsObject);

            JSONArray connectionsArray = (JSONArray) jsonData.get("connections");
            parseConnections(connectionsArray);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void parseLines(JSONArray linesArray)
    {
        linesArray.forEach(lineObject -> {
            JSONObject lineJsonObject = (JSONObject) lineObject;
            Line line = new Line(
                    ((Long) lineJsonObject.get("number")).intValue(),
                    (String) lineJsonObject.get("name")
            );
            stationIndex.addLine(line);
        });
    }

    private static void parseStations(JSONObject stationsObject)
    {
        stationsObject.keySet().forEach(lineNumberObject ->
        {
            int lineNumber = Integer.parseInt((String) lineNumberObject);
            Line line = stationIndex.getLine(lineNumber);
            JSONArray stationsArray = (JSONArray) stationsObject.get(lineNumberObject);
            stationsArray.forEach(stationObject ->
            {
                Station station = new Station((String) stationObject, line);
                stationIndex.addStation(station);
                line.addStation(station);
            });
        });
    }

    private static void parseConnections(JSONArray connectionsArray)
    {
        connectionsArray.forEach(connectionObject ->
        {
            JSONArray connection = (JSONArray) connectionObject;
            List<Station> connectionStations = new ArrayList<>();
            connection.forEach(item ->
            {
                JSONObject itemObject = (JSONObject) item;
                int lineNumber = ((Long) itemObject.get("line")).intValue();
                String stationName = (String) itemObject.get("station");

                Station station = stationIndex.getStation(stationName, lineNumber);
                if(station == null)
                {
                    throw new IllegalArgumentException("core.Station " +
                            stationName + " on line " + lineNumber + " not found");
                }
                connectionStations.add(station);
            });
            stationIndex.addConnection(connectionStations);
        });
    }

    private static void printRoute(List<Station> route)
    {
        Station previousStation = null;
        for(Station station : route)
        {
            if(previousStation != null)
            {
                Line prevLine = previousStation.getLine();
                Line nextLine = station.getLine();
                if(!prevLine.equals(nextLine))
                {
                    System.out.println("\tПереход на станцию " +
                            station.getName() + " (" + nextLine.getName() + " линия)");
                }
            }
            System.out.println("\t" + station.getName());
            previousStation = station;
        }
    }
    */
}
