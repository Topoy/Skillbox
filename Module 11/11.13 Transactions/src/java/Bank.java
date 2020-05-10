import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Bank
{
    private HashMap<Integer, Account> accounts;
    private final Random random = new Random();

    private synchronized boolean isFraud(int fromAccountNum, int toAccountNum, long amount)
        throws InterruptedException
    {
        //Thread.sleep(1000);
        //return random.nextBoolean();
        return true;
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами.
     * Если сумма транзакции > 50000, то после совершения транзакции,
     * она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка
     * счетов (как – на ваше усмотрение)
     */
    public void transfer(int fromAccountNum, int toAccountNum, long amount) throws InterruptedException
    {
        Account from = accounts.get(fromAccountNum);
        Account to = accounts.get(toAccountNum);

        if (from.getAccNumber() < to.getAccNumber())
        {
            synchronized (from)
            {
                synchronized (to)
                {
                    if (isBlockedCondition(from, to))
                    {
                        return;
                    }
                    isFraudCondition(from, to, amount);
                    doTransfer(from, to, amount);
                }
            }
        }
        else
        {
            synchronized (to)
            {
                synchronized (from)
                {
                    if (isBlockedCondition(from, to))
                    {
                        return;
                    }
                    isFraudCondition(from, to, amount);
                    doTransfer(from, to, amount);
                }
            }
        }
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(int accountNum)
    {
        Account account = accounts.get(accountNum);
        return account.getMoney();
    }

    public HashMap<Integer, Account> getAccounts()
    {
        return accounts;
    }

    private static HashMap<Integer, Account> createAccounts()
    {
        HashMap<Integer, Account> accountMap = new HashMap<>();
        for (int i = 0; i < 100; i++)
        {
            long money = (long) (70000 + 30000 * Math.random());
            Account account = new Account(money, i);
            accountMap.put(i, account);
        }
        return accountMap;
    }

    public ArrayList<Account> createAccountList(int amount, long cash)
    {
        ArrayList<Account> accountList = new ArrayList<>();
        for (int i = 1; i <= amount; i++)
        {
            accountList.add(new Account(cash, i));
        }
        return accountList;
    }

    public void setAccounts(HashMap<Integer, Account> accounts)
    {
        this.accounts = accounts;
    }

    private boolean isBlockedCondition(Account from, Account to)
    {
        if (from.getIsBlocked() || to.getIsBlocked())
        {
            System.out.println("Операция заблокирована");
            return true;
        }
        return false;
    }
    
    private void isFraudCondition(Account from, Account to, long amount) throws InterruptedException
    {
        if (amount > 50000)
        {
            if (isFraud(from.getAccNumber(), to.getAccNumber(), amount))
            {
                from.setIsBlocked(true);
                to.setIsBlocked(true);
            }
        }
    }

    private void doTransfer(Account from, Account to, long amount)
    {
        if (from.withdraw(amount))
        {
            to.deposit(amount);
        }
    }

    /*
    Идея состояла в том, чтобы сократить код за счёт повторяющегося обрамления проверок последовательности выполнения
    потоков
    private void dislockMethod(Account from, Account to, long amount, boolean method)
    {
        boolean b = false;
        if (from.getAccNumber() < to.getAccNumber())
        {
            synchronized (from)
            {
                synchronized (to)
                {
                    b = method;
                }
            }
        }
        else
        {
            synchronized (to)
            {
                synchronized (from)
                {
                    b = method;
                }
            }
        }

    }
    */

}
