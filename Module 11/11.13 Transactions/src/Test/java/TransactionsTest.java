import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TransactionsTest extends TestCase
{
    public HashMap<Integer, Account> accounts = new HashMap<Integer, Account>();
    public Bank bank;
    public Account firstAccount;
    public Account secondAccount;
    public Account account3;
    public Account account4;
    public Account account5;
    public Account account6;
    public Account account7;
    public Account account8;
    public Account account9;
    public Account account10;

    private Random random;
    private Random randomSum;

    @Before
    public void setUp()
    {
        bank = new Bank();
        firstAccount = new Account(1000, 1);
        secondAccount = new Account(1000, 2);
        account3 = new Account(1000, 3);
        account4 = new Account(1000, 4);
        account5 = new Account(1000, 5);
        account6 = new Account(1000, 6);
        account7 = new Account(1000, 7);
        account8 = new Account(1000, 8);
        account9 = new Account(1000, 9);
        account10 = new Account(1000, 10);

        accounts.put(1, firstAccount);
        accounts.put(2, secondAccount);
        accounts.put(3, account3);
        accounts.put(4, account4);
        accounts.put(5, account5);
        accounts.put(6, account6);
        accounts.put(7, account7);
        accounts.put(8, account8);
        accounts.put(9, account9);
        accounts.put(10, account10);

        bank.setAccounts(accounts);
        random = new Random(1);
        randomSum = new Random(20);

    }

    @Test
    public void testOneTransaction() throws InterruptedException
    {
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
        for (int i = 0; i < 10; i++)
        {
            service.submit(new Runnable() {
                @Override
                public void run()
                {
                    for (int j = 0; j < 500; j++)
                    {
                        int fromRandom = random.nextInt(10);
                        int toRandom = random.nextInt(10);
                        int randomMoney = randomSum.nextInt(100);
                        try {
                            bank.transfer(fromRandom, toRandom, randomMoney);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.HOURS);
        long expectedSum = 10000;
        long actualSum = firstAccount.getMoney() + secondAccount.getMoney() + account3.getMoney() + account4.getMoney() +
                account5.getMoney() + account6.getMoney() + account7.getMoney() + account8.getMoney() + account9.getMoney() +
                account10.getMoney();
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
