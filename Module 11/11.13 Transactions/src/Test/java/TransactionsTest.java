import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TransactionsTest extends TestCase
{
    private HashMap<Integer, Account> accounts = new HashMap<Integer, Account>();
    private ArrayList<Account> accountList;
    private Bank bank;
    private Random random;
    private Random randomSum;

    @Before
    public void setUp()
    {
        bank = new Bank();
        accountList = bank.createAccountList(10, 1000);

        for (int i = 0; i < accountList.size(); i++)
        {
            accounts.put(i+1, accountList.get(i));
        }

        bank.setAccounts(accounts);
        random = new Random(1);
        randomSum = new Random(20);

    }

    @Test
    public void testOneTransaction() throws InterruptedException
    {
        Account firstAccount = accountList.get(0);
        Account secondAccount = accountList.get(1);
        bank.transfer(1, 2, 100);
        long actualFromAccount = firstAccount.getMoney();
        long expectedFromAccount = 900;
        long actualToAccount = secondAccount.getMoney();
        long expectedToAccount = 1100;
        assertEquals(expectedFromAccount, actualFromAccount);
        assertEquals(expectedToAccount, actualToAccount);
    }

    @Test
    public void testManyThreadTransaction() throws InterruptedException
    {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 50000; i++)
        {
            service.submit(new Runnable() {
                @Override
                public void run()
                {
                    try {
                        for (int j = 0; j < 5000; j++)
                        {
                            int fromRandom = random.nextInt(10);
                            int toRandom = random.nextInt(10);
                            int randomMoney = randomSum.nextInt(100);
                            bank.transfer(fromRandom, toRandom, randomMoney);
                        }
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.HOURS);
        long expectedSum = 10000;
        long actualSum = 0;
        for (Account account : accountList)
        {
            actualSum += account.getMoney();
        }
        assertEquals(expectedSum, actualSum);
        /*
        Создаем рандомное число. Далее вызываем трансфер для каждого потока в цикле 500 раз.
        цикл из 10 потоков:
        {
        for (int i = 0; i < 500; i++)
        {
        bank.transfer(from: randomNum, to: randomSum, sum: randomSum);
        }
        }

         */
    }

}
