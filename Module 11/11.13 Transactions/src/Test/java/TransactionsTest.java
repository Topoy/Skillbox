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
    private HashMap<Integer, Account> reachAccounts = new HashMap<>();
    private ArrayList<Account> accountList;
    private ArrayList<Account> reachAccountList;
    private Bank bank;
    private Bank reachBank;
    private Random random;
    private Random randomSum;

    @Before
    public void setUp()
    {
        bank = new Bank();
        reachBank = new Bank();
        accountList = bank.createAccountList(10, 1000);
        reachAccountList = reachBank.createAccountList(10, 1000000);

        //Создали список аккаунтов среднего класса
        for (int i = 0; i < accountList.size(); i++)
        {
            accounts.put(i+1, accountList.get(i));
        }

        //Создали список аккаунтов богатого класса
        for (int i = 0; i < reachAccountList.size(); i++)
        {
            reachAccounts.put(i+1, reachAccountList.get(i));
        }

        bank.setAccounts(accounts);
        reachBank.setAccounts(reachAccounts);
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

    @Test
    public void testIsFraudMethod() throws InterruptedException
    {
        ExecutorService service = Executors.newFixedThreadPool(8);
        long balanceStart = reachBank.getBalance(1);

        for (int i = 0; i < 10; i++)
        {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        if (!Thread.currentThread().isInterrupted())
                        {
                            System.err.println(Thread.currentThread().getId() + " start");
                            int toRandom = random.nextInt(10);
                            reachBank.transfer(1, toRandom, 100000);
                            System.err.println(Thread.currentThread().getId() + " end: " + reachAccountList.get(0).getMoney());
                        }
                        /*
                        for (int i = 0; i < 10; i++)
                        {
                            int toRandom = random.nextInt(10);
                            reachBank.transfer(1, toRandom, 100000);
                            System.out.println(reachAccountList.get(0).getMoney());
                        }
                        */
                    }
                    catch (InterruptedException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            });
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.HOURS);
        /*
        boolean expectedState = true;
        boolean actualState = reachAccountList.get(0).getIsBlocked();
        assertEquals(expectedState, actualState);
        */
        long balanceEnd = reachBank.getBalance(1);
        assertEquals(balanceStart - 100000, balanceEnd);
    }


}
