/**
 * "//" - переход между станциями или линиями
 * "|,-" - путь вдоль одной линии
 * (FIRST LINE) - название линии
 *                                                 (FIRST LINE)  /////////////  (SECOND LINE)        (FIFTH LINE)
 *                                                  black river  /////////////   mayakovskaya          rayskaya
 *                                                      |                             \                    |
 *                                                      |                              \                   |
 *                                                      |                               \                  |
 *                                                      |           (THIRD LINE)         \                 |
 * (FORTH LINE) pushkinskaya --- centralnaya // petrogradskaya // ozerki --- liteyniy // begovaya // perekrestnaya
 *                                                      |                                  /
 *                                                      |                                 /
 *                                                      |                                /
 *                                                 gorkovskaya                 vasileostrovskaya
 */


import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteCalculatorTest extends TestCase
{
    private StationIndex stationIndex;
    private List<Station> connectedStations;
    private ArrayList<String> stationNames = new ArrayList<>();
    private RouteCalculator calculator;

    private Line line1;
    private Line line2;
    private Line line3;
    private Line line4;
    private Line line5;

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
    private Station perekrestnaya;
    private Station rayskaya;

    @Override
    protected void setUp() throws Exception
    {
        stationIndex = new StationIndex();
        calculator  = new RouteCalculator(stationIndex);
        List<Station> route = new ArrayList<>();
        connectedStations = new ArrayList<>();
        stationNames.addAll(Arrays.asList("Чёрная речка", "Петроградская", "Горьковская", "Маяковская", "Беговая",
                                          "Василеостровская", "Озерки", "Литейный"));

        line1 = new Line(1, "Первая");
        line2 = new Line(2, "Вторая");
        line3 = new Line(3, "Третья");
        line4 = new Line(4, "Четвёртая");
        line5 = new Line(5, "Пятая");

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
        perekrestnaya = new Station("Перекрёстная", line5);
        rayskaya = new Station("Райская", line5);



        route.add(blackRiver);
        route.add(petrGrad);
        route.add(mayakovskaya);
        route.add(begovaya);

        ///////////////////////////////////Добавление линий тестового метро/////////////////////////////////////////////
        stationIndex.addLine(line1);
        stationIndex.addLine(line2);
        stationIndex.addLine(line3);
        stationIndex.addLine(line4);
        stationIndex.addLine(line5);

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
        stationIndex.addStation(perekrestnaya);
        stationIndex.addStation(rayskaya);

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
        line5.addStation(perekrestnaya);
        line5.addStation(rayskaya);

        //////////////////////////////////Добавление соединений станций тестового метро/////////////////////////////////

        stationIndex.addConnection(Arrays.asList(blackRiver, mayakovskaya));
        stationIndex.addConnection(Arrays.asList(petrGrad, centralnaya, ozerki));
        stationIndex.addConnection(Arrays.asList(begovaya, liteyniy, perekrestnaya));

        //List<Station> actualShortestRoute = calculator.getShortestRoute(petrGrad, vasileostrovskaya);
        //System.out.println(actualShortestRoute);
        //System.out.println(blackRiver.getLine().getStations());
    }

    public void test_calculate_duration()
    {
        List<Station> path = calculator.getShortestRoute(blackRiver, liteyniy);
        double actual = RouteCalculator.calculateDuration(path);
        double expected = 8.5;
        assertEquals(expected, actual);
    }

    public void test_calculate_duration_on_same_station()
    {
        List<Station> path = calculator.getShortestRoute(blackRiver, blackRiver);
        double actual = RouteCalculator.calculateDuration(path);
        double expected = 0.0;
        assertEquals(expected, actual);
    }

    public void test_shortest_route()
    {
        List<Station> actualShortestRoute = calculator.getShortestRoute(blackRiver, vasileostrovskaya);
        List<Station> expectedShortestRoute = Arrays.asList(blackRiver, mayakovskaya, begovaya, vasileostrovskaya);
        assertEquals(expectedShortestRoute, actualShortestRoute);
    }

    public void test_route_with_one_connection()
    {
        List<Station> actualRouteWithOneConnection = calculator.getShortestRoute(blackRiver, begovaya);
        List<Station> expectedRouteWithOneConnection = Arrays.asList(blackRiver, mayakovskaya, begovaya);
        assertEquals(expectedRouteWithOneConnection, actualRouteWithOneConnection);
    }

    public void test_route_via_connected_line()
    {
        //List<Station> actualRouteViaConnectedLine = calculator.getShortestRoute(blackRiver, pushkinskaya);
        List<Station> actualRouteViaConnectedLine = calculator.getRouteViaConnectedLine(blackRiver, liteyniy);
        //List<Station> expectedRouteViaConnectedLine = Arrays.asList(mayakovskaya, begovaya);
        //assertEquals(expectedRouteViaConnectedLine, actualRouteViaConnectedLine);
        System.out.println(actualRouteViaConnectedLine);
    }

    public void test_route_with_two_connections()
    {
        List<Station> actualRouteWithTwoConnections = calculator.getShortestRoute(gorkovskaya, rayskaya);
        System.out.println(actualRouteWithTwoConnections);
        List<Station> expectedRouteWithTwoConnections = Arrays.asList(gorkovskaya, petrGrad, ozerki, liteyniy, perekrestnaya, rayskaya);
        assertEquals(expectedRouteWithTwoConnections, actualRouteWithTwoConnections);
    }
}
