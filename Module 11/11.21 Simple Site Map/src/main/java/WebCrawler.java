import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class WebCrawler extends RecursiveTask<String>
{
    private static final int MAX_DEPTH = 5;
    private LinkedList<String> links = new LinkedList<>();
    private final static Set<String> processedLinks = new HashSet<>();
    private String tab = "";

    private String URL;
    private int depth;

    public WebCrawler(String URL)
    {
        this.URL = URL;
    }

    @Override
    protected String compute()
    {
        StringBuilder totalURLs = new StringBuilder(URL + "\n");
        Set<WebCrawler> subTasks = new LinkedHashSet<>();

        for (String pageLink : parseOnePage())
        {
            synchronized (pageLink)
            {
                if (!processedLinks.contains(pageLink))
                {
                    WebCrawler task = new WebCrawler(pageLink);
                    processedLinks.add(pageLink);
                    task.fork();
                    subTasks.add(task);
                }
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
                Elements pageLinks = document.select("ul a[href]");

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

    public LinkedList<String> getPageLinks(int depth)
    {
        if (!links.contains(URL) && (depth < MAX_DEPTH))
        {
            for (int i = 0; i < depth; i++)
            {
                tab += "\t";
            }
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
}
