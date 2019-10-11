import core.Line;
import core.Station;

import java.util.*;
import java.util.stream.Collectors;

public class StationIndex
{
    HashMap<Integer, Line> number2line;
    TreeSet<Station> stations;
    TreeMap<Station, TreeSet<Station>> connections;

    public StationIndex()
    {
        number2line = new HashMap<>();
        stations = new TreeSet<>();
        connections = new TreeMap<>();
    }

    public void addStation(Station station)
    {
        stations.add(station);
    } //добавляет новую станцию к хранилищу станций

    public void addLine(Line line) //добавляет новую линию метро с указанием номера и названия
    { number2line.put(line.getNumber(), line); }

    public void addConnection(List<Station> stations)
    {
        for(Station station : stations)
        {
            if(!connections.containsKey(station)) {
                connections.put(station, new TreeSet<>()); //если connections пустой, то он заполняется станциями
                // из списка станций (List<Stations> stations)
            }
            TreeSet<Station> connectedStations = connections.get(station); //connectedStations получил пустой TreeSet
            connectedStations.addAll(stations.stream()
                .filter(s -> !s.equals(station)).collect(Collectors.toList()));
            //если данный элемент стрима stations не равен текущему station, то фильтр его пропускает.
            //затем такие элементы собираются в коллекцию
        }
    }

    public Line getLine(int number)
    {
        return number2line.get(number);
    } //получить линию

    public Station getStation(String name) //получить станциию по названию самой станции
    {
        for(Station station : stations)
        {
            if(station.getName().equalsIgnoreCase(name)) {
                return station;
            }
        }
        return null;
    }

    public Station getStation(String name, int lineNumber) //получение станции по названию и номеру линии
    {
        Station query = new Station(name, getLine(lineNumber));
        Station station = stations.ceiling(query);
        return station.equals(query) ? station : null;
    }

    public Set<Station> getConnectedStations(Station station)
    {
        if(connections.containsKey(station)) {
            return connections.get(station);
        }
        return new TreeSet<>();
    }
}
