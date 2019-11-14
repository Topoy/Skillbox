import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BankCustomer
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

    @CsvBindByName(column = "Приход", required = true)
    private String coming;

    public String getComing() {
        return coming;
    }

    @CsvBindByName(column = "Расход", required = true)
    private String costs;

    public String getCosts() {
        return costs;
    }

    public static String getCompanyName(BankCustomer customer)
    {
        String[] description = customer.transactionDescription.trim().split(" {3,}");
        String[] transactionParametrs = description[1].split("/");
        String[] companyParametrs = transactionParametrs[transactionParametrs.length - 1].split("\\\\");
        String companyName = companyParametrs[companyParametrs.length - 1];
        return companyName;
    }


}
