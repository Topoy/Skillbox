import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.ToString;

@Data
public class Customer
{
    @CsvBindByName(column = "Тип счёта", required = true)
    private String accountType;

    @CsvBindByName(column = "Номер счета", required = true)
    private String accountNumber;

    @CsvBindByName(column = "Валюта", required = true)
    private String currency;

    @CsvBindByName(column = "Дата операции", required = true)
    private String transactionDate;

    @CsvBindByName(column = "Референс проводки", required = true)
    private String reference;

    @CsvBindByName(column = "Описание операции", required = true)
    private String transactionDescription;
    public String getTransactionDescription()
    {
        return transactionDescription;
    }

    @CsvBindByName(column = "Приход", required = true)
    private String coming;
    public Double getComing()
    {
        return Double.parseDouble(coming.replace(",", "."));
    }

    @CsvBindByName(column = "Расход", required = true)
    private String costs;
    public Double getCosts()
    {
        return Double.parseDouble(costs.replace(",", "."));
    }

    public static String getCompanyName(Customer customer)
    {
        String[] descriptionParts = customer.transactionDescription.split(" {3,}");
        String[] companyDescription = descriptionParts[1].split("/");
        String[] temp = companyDescription[companyDescription.length - 1].split("\\\\");
        String name = temp[temp.length - 1];
        return name;
    }

    public static Double conversation(String num)
    {
        return Double.parseDouble(num.replace(",", "."));
    }


}
