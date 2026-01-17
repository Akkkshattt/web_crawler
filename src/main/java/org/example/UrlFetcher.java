package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class UrlFetcher {

    public Set<String> fetchLinks(String url) {
        Set<String> linksOnPage = new HashSet<>();
        Document document = null;

        try {
            document = Jsoup.connect(url).timeout(5000).get();
            Elements links = document.select("a[href]");
            for(Element e :links){
                String extractedUrl = e.absUrl("href");
                if(!extractedUrl.isEmpty()){
                    linksOnPage.add(extractedUrl);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return linksOnPage;
    }
}
