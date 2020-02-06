public class Connection
{
    final private String lineNumber;
    final private String stationName;

    public Connection(String lineNumber, String stationName)
    {
        this.lineNumber = lineNumber;
        this.stationName = stationName;
    }

    public String getLineNumber()
    {
        if (lineNumber.equals("Infinity"))
        {
            return "отсутствует";
        }
        else return lineNumber;
    }
    public String getStationName()
    {
        if (stationName.equals(" "))
        {
            return "отсутствует";
        }
        else return stationName;
    }

}
