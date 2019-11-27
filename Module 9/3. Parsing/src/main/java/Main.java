import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class Main
{
    private static String movementListFile = "data/movementList.csv";
    private static String csvTestFile = "data/csvTest.csv";
    public static List<BankCustomer> beans;
    public static void main(String[] args) throws Exception {

        FileReader fileReader = new FileReader(csvTestFile);
        beans = new CsvToBeanBuilder<BankCustomer>(fileReader).withType(BankCustomer.class)
                .withEscapeChar('\0')
                .build()
                .parse();
        int i = 1;

        beans.stream().collect(Collectors.groupingBy(BankCustomer::getCompanyName,
                        Collectors.mapping(Total::moneyFormatConvert,
                        Collectors.reducing(new Total(0.0, 0.0), Total::amount))))
                        .forEach((a, b) -> System.out.println(a + "\t" + b + "\n"));

        fileReader.close();
    }
}
