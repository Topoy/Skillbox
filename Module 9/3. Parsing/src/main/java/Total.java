import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Total
{
    private double income;
    private double withdraw;

    public Total(Double income, Double withdraw)
    {
        this.income = income;
        this.withdraw = withdraw;
    }

    private static Double conversation(String numb)
    {
        return Double.parseDouble(numb.replace(',', '.'));
    }

    public static Total moneyFormatConvert(BankCustomer customerMoney)
    {
        return new Total(conversation(customerMoney.getComing()), conversation(customerMoney.getCosts()));
    }

    public static Total amount(Total total1, Total total2)
    {
        return new Total(total1.income + total2.income, total1.withdraw + total2.withdraw);
    }



}
