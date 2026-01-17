package org.example;

import java.util.Set;
import java.util.concurrent.Phaser;

public class CrawlerTask implements Runnable{

    private final UrlStore urlStore;
    private final UrlFetcher urlFetcher;
    private final Phaser phasor;
    private final Long maxDepth;
    private final Long currentDepth;

    public CrawlerTask(UrlStore urlStore, UrlFetcher urlFetcher, Phaser phasor, Long currentDepth, Long maxDepth) {
        this.urlStore = urlStore;
        this.urlFetcher = urlFetcher;
        this.phasor = phasor;
        this.maxDepth = maxDepth;
        this.currentDepth = currentDepth;
    }

    @Override
    public void run() {
        try {
            String url = urlStore.getUrl();
            System.out.println(Thread.currentThread().getName());
            if(url == null || currentDepth>maxDepth) return;

            Set<String> links = urlFetcher.fetchLinks(url);
            for(String link:links){
                if( urlStore.addUrl(link)){
                    phasor.register();
                    WebCrawler.submitTask(urlStore,urlFetcher,currentDepth+ 1,maxDepth);
                }

            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            phasor.arriveAndDeregister();
        }


    }
}