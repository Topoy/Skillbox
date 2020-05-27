import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;

public class WebCrawler extends RecursiveTask<String>
{
    private static final int MAX_DEPTH = 5;
    private LinkedList<String> links = new LinkedList<>();
    private final static Set<String> processedLinks = new HashSet<>();
    private final static Set<String> linksSeen = ConcurrentHashMap.newKeySet();
    private static String tab = "";

    private String URL;
    private int depth = 0;

    public WebCrawler(String URL, int depth)
    {
        this.URL = URL;
        this.depth = depth;
    }

    @Override
    protected String compute()
    {
        tab = "\t".repeat(depth);
        StringBuilder totalURLs = new StringBuilder(tab + URL + "\n");
        Set<WebCrawler> subTasks = new LinkedHashSet<>();
        for (String pageLink : parseOnePage())
        {
            if (linksSeen.add(pageLink)) //возвращает true, если этот Set ещё не содержит такой элемент (pageLink)
                {
                    WebCrawler task = new WebCrawler(pageLink, depth);
                    task.fork();
                    subTasks.add(task);
                }

        }
        for (WebCrawler task : subTasks)
        {
                totalURLs.append(task.join());
        }
        return totalURLs.toString();
    }

    public LinkedList<String> parseOnePage()
    {
        if (!links.contains(URL))
        {
            try
            {
                Document document = Jsoup.connect(URL).maxBodySize(0).get();
                Elements pageLinks = document.select("a[href]");
                depth++;
                for (Element link : pageLinks)
                {
                    URL = link.attr("abs:href");
                    links.add(URL);
                }
            }
            catch(IOException e)
            {
                System.err.println(URL + " : " + e.getMessage());
            }
        }
        return links;
    }
/*
    public LinkedList<String> getPageLinks(int depth)
    {
        if (!links.contains(URL) && (depth < MAX_DEPTH))
        {
            tab = "\t".repeat(depth);
            System.out.println("Depth: " + depth + " ; " + tab + URL + ";");
            tab = "";

            try
            {
                links.add(URL);
                Document document = Jsoup.connect(URL).maxBodySize(0).get();
                Elements linksOnPage = document.select("a[href]");
                depth++;

                for (Element element : linksOnPage)
                {
                    this.URL = element.attr("abs:href");

                    getPageLinks(depth);
                }
            }
            catch(IOException e)
            {
                System.err.println(URL + " : " + e.getMessage());
            }
        }
        return links;
    }
    */
}
