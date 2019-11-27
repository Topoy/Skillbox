import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main
{
    private static String pathCSV = "data/movementList.csv";
    private static List<Customer> bean;

    public static void main(String[] args) throws IOException
    {
        bean = new CsvToBeanBuilder<Customer>(new FileReader(pathCSV))
                .withType(Customer.class)
                .withEscapeChar('\0')
                .build()
                .parse();
/*
        for (Customer customer : bean)
        {
            System.out.println(Customer.getCompanyName(customer) + ";\tПриход: " + customer.getComing() + ";\tРасход: " + customer.getCosts());
        }
*/
        bean.stream().collect(Collectors.groupingBy(Customer::getCompanyName, Collectors.summingDouble(Customer::getComing)))
        .forEach((a, b) -> System.out.println(a + "\t" + b));


    }
}
