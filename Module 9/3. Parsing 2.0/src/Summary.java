public class Summary
{
    private double income;
    private double withdraw;

    public Summary(double income, double withdraw)
    {
        this.income = income;
        this.withdraw = withdraw;
    }

    private static Double convert(String num)
    {
        return Double.parseDouble(num.replace(",", "."));
    }

    public static Summary sum(Summary sum1, Summary sum2)
    {
        return new Summary(sum1.income + sum2.income, sum1.withdraw + sum2.withdraw);
    }





}
