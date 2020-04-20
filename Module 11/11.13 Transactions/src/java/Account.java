public class Account
{
    private long money;
    private Integer accNumber;
    private boolean isBlocked = false;
    private boolean isPossible = true;

    public Account(long money, Integer accNumber)
    {
        this.money = money;
        this.accNumber = accNumber;
    }

    public void deposit(long money)
    {
        if (isPossible)
        {
            this.money += money;
        }
    }
    public boolean withdraw(long money)
    {
        if (this.money < money)
        {
            isPossible = false;
            return false;
        }
        else {
            this.money -= money;
            return true;
        }
    }
    public long getMoney()
    {
        return money;
    }
    public Integer getAccNumber()
    {
        return accNumber;
    }

    public boolean getIsBlocked()
    {
        return isBlocked;
    }

    public void setIsBlocked(boolean state)
    {
        this.isBlocked = state;
    }
}
