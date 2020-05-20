public class Main
{
    private static int count = 10;
    private static MyReentrantLock reentrantLock = new MyReentrantLock();

    public static void main(String[] args) throws InterruptedException
    {

        Thread t1 = new Thread(() ->
        {
            for (int i = 0; i < 10000; i++)
            {
                try {
                    reentrantLock.lock();
                    reentrantLock.lock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
                reentrantLock.unlock();
                reentrantLock.unlock();
            }
        });

        Thread t2 = new Thread(() ->
        {
           for (int i = 0; i < 10000; i++)
           {
               try {
                   reentrantLock.lock();
                   reentrantLock.lock();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               count--;
               reentrantLock.unlock();
               reentrantLock.unlock();
           }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(count);
    }
}
