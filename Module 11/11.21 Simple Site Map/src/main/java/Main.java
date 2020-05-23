import java.util.concurrent.ForkJoinPool;

public class Main
{
    public static void main(String[] args)
    {
        String mainLink = "https://secure-headland-59304.herokuapp.com/";
        //WebCrawler webCrawler = new WebCrawler(mainLink);
        //webCrawler.getPageLinks(0);
        new ForkJoinPool().invoke(new WebCrawler(mainLink));
    }
}
