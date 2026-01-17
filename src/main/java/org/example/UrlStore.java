package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class UrlStore {

    private final ConcurrentHashMap<String,Boolean> visitedUrl = new ConcurrentHashMap<>();
    private final BlockingQueue<String> urlToVisit = new LinkedBlockingQueue<>();

    public Boolean addUrl(String url){
        if(visitedUrl.putIfAbsent(url,true)==null){
            urlToVisit.add(url);
            return true;
        }
        return false;
    }

    public String getUrl() throws InterruptedException {
        return urlToVisit.take();
    }

    public Boolean isQueueEmpty(){
        return urlToVisit.isEmpty();
    }
}
