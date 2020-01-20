public class Station
{
    final private String line;
    final private String name;

    public Station(String name, String line)
    {
        this.name = name;
        this.line = line;
    }

    public String getName()
    {
        return name;
    }

    public String getLine()
    {
        return line;
    }

}
