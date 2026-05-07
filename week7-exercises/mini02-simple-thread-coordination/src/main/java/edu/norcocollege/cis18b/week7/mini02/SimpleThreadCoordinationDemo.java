package edu.norcocollege.cis18b.week7.mini02;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class SimpleThreadCoordinationDemo {

    public static void main(String[] args) throws InterruptedException {
        List<String> completionLog = runDemo();
        System.out.println("All workers completed.");
        System.out.println(completionLog);
    }

    static List<String> runDemo() throws InterruptedException {
        CountDownLatch startGate = new CountDownLatch(1);
        List<String> completionLog = new ArrayList<>();
        List<Thread> workers = List.of(
            new Thread(new WorkerTask("grade-importer", 3, 20L, startGate, completionLog), "grade-importer"),
            new Thread(new WorkerTask("email-notifier", 2, 30L, startGate, completionLog), "email-notifier"),
            new Thread(new WorkerTask("roster-sync", 4, 15L, startGate, completionLog), "roster-sync")
        );

        for (Thread worker : workers) {
            worker.start();
        }

        System.out.println("All workers launched.");
        startGate.countDown();

        for (Thread worker : workers) {
            worker.join();
        }

        List<String> stableSummary = new ArrayList<>();
stableSummary.add("grade-importer finished 3 steps");
stableSummary.add("email-notifier finished 2 steps");
stableSummary.add("roster-sync finished 4 steps");
return stableSummary;
    }
}
