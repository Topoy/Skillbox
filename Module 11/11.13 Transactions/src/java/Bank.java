import java.util.HashMap;
import java.util.Random;

public class Bank
{
    private HashMap<Integer, Account> accounts;
    private final Random random = new Random();

    public synchronized boolean isFraud(int fromAccountNum, int toAccountNum, long amount)
        throws InterruptedException
    {
        Thread.sleep(1000);
        return random.nextBoolean();
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
        if (from.getIsBlocked() || to.getIsBlocked())
        {
            System.out.println("Операция заблокирована");
            return;
        }
        if (amount > 50000)
        {
            if (isFraud(from.getAccNumber(), to.getAccNumber(), amount))
            {
                from.setIsBlocked(true);
                to.setIsBlocked(true);
                System.out.println("Операция заблокирована");
                return;
            }
        }
        from.withdraw(amount);
        to.deposit(amount);
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

    public void setAccounts(HashMap<Integer, Account> accounts)
    {
        this.accounts = accounts;
    }
}
