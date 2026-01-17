package org.example;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class WebCrawler {
    private static Phaser phaser;
    private static ExecutorService executorService;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ur url");
        String url = sc.nextLine();
        System.out.println("Enter max depth u prefer");
        Long maxDepth = sc.nextLong();
        System.out.println("Enter number of threads u want");
        Integer threadCount = sc.nextInt();

        UrlStore urlStore = new UrlStore();
        urlStore.addUrl(url);
        UrlFetcher urlFetcher = new UrlFetcher();
        phaser = new Phaser(1);
        executorService = Executors.newFixedThreadPool(threadCount);

        Long start = System.currentTimeMillis();

        submitTask(urlStore,urlFetcher,0L,maxDepth);

        phaser.awaitAdvance(phaser.getPhase());
        executorService.shutdown();

        System.out.println("Time Taken :"  + (System.currentTimeMillis() - start));
    }

    public static void submitTask(UrlStore urlStore, UrlFetcher urlFetcher, Long currentDepth, Long maxDepth) {
        executorService.submit(new CrawlerTask(urlStore,urlFetcher,phaser,currentDepth,maxDepth));
    }
}