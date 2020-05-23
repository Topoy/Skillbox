import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class WebCrawler extends RecursiveTask<String>
{
    private static final int MAX_DEPTH = 5;
    private LinkedList<String> links;
    private String tab = "";

    private String URL;
    private int depth;

    public WebCrawler(String URL)
    {
        links = new LinkedList<>();
        this.URL = URL;
    }

    @Override
    protected String compute()
    {
        StringBuilder totalURLs = new StringBuilder();
        List<WebCrawler> subTasks = new LinkedList<>();

        for (String pageLink : getPageLinks(0))
        {
            WebCrawler task = new WebCrawler(pageLink);
            task.fork();
            subTasks.add(task);
            //System.out.println(pageLink);
        }

        for (WebCrawler task : subTasks)
        {
            totalURLs.append(task.join()).append(" ");
        }
        return totalURLs.toString();
    }

    public LinkedList<String> getPageLinks(int depth)
    {
        if (!links.contains(URL) && (depth < MAX_DEPTH))
        {
            for (int i = 0; i < depth; i++)
            {
                tab += "\t";
            }
            //System.out.println("Depth: " + depth + " ; " + tab + URL + ";");
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
