import java.util.concurrent.ForkJoinPool;

public class Main
{
    public static void main(String[] args)
    {
        String mainLink = "https://secure-headland-59304.herokuapp.com/";
        String someLink = "https://secure-headland-59304.herokuapp.com/child-C";
        WebCrawler webCrawler = new WebCrawler(mainLink);
        //webCrawler.parseOnePage();
        String result = new ForkJoinPool().invoke(webCrawler);
        System.out.println(result);

    }
}
