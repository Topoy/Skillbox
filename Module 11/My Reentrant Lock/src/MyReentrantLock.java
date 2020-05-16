public class MyReentrantLock
{
    /*
    Объект MyReentrantLock является монитором.
    Чтобы программа не зависала не стоит делать просто wait() внутри lock(). Таким образом первый же поток зайдет в lock(),
    уснёт и тоже самое будет с другими последующими потоками. Для того, чтобы lock() работал корректно необходимо условие,
    которое будет проверять состояние потока (заблокирован/не заблокирован). Если поток не заблокирован, то пометить
    состояние как "заблокирован" и выйти из метода. Если состояние помечено как "заблокирован", то поток следует усыпить.
    Метод unlock должен выставлять состояние в "не заблокиован".
    Таким образом поток 1 начинает выполняться -> заходит в lock() -> видит, что состояние не заблокировано -> выставляет
    состояние в "заблокировано" и выходит из метода lock() -> выполняет count++ -> заходит в метод unlock() -> в методе
    unlock выставляется состояние "не заблокировано" и будит другие потоки, которые зашли за первым потоком в lock и т.к.
    поток 1 установил locked = true, то остальные потоки, зашедшие за ним - уснули. Далее поток 1 зашедший в unlock выставляет
    locked = false и будит все уснувшие потоки через notifyAll().
     */
    private static volatile boolean locked;

    public void lock()
    {
        if (locked == false)
        {
            locked = true;
            return;
        }
        try
        {
            wait();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public synchronized void unlock()
    {
        locked = false;
        notifyAll();
    }
}
