package LogicStuff;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AdvancedFileWalker implements IWalkable
{
    private int threadCount;
    private String Pattern;
    long FileSize;

    ThreadPoolExecutor executor;
    private ArrayList<Thread> threads;

    public AdvancedFileWalker(int threadCount, String Pattern, long FileSize)
    {
        this.threadCount = threadCount;
        this.Pattern = Pattern;
        this.FileSize = FileSize;
    }


    @Override
    public ArrayList<Thread> getThreads() {
        return threads;
    }

    @Override
    public void Start(JTextField ta, ThreadMonitor tm)
    {
        long startTime = System.currentTimeMillis();
        for(int i=0; i<threads.size(); i++) {
            executor.submit(threads.get(i));
        }

        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
        }

        long elapsedTimeMillis = System.currentTimeMillis()-startTime;
        float elapsedTimeSec = elapsedTimeMillis/1000F;
        ta.setText(""+elapsedTimeSec);
        tm.resetMonitor();
    }

    @Override
    public void Initialize()
    {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);
        threads = new ArrayList<Thread>();

        CopyOnWriteArrayList<String> availableDirs = new CopyOnWriteArrayList<String>();

        availableDirs.add("A:/NET");//("C:/Ruby27-x64");

        boolean isRunning = true;


        class BigRunnable implements Runnable{

            int threadNo;

            public BigRunnable(int threadNo){
                this.threadNo = threadNo;
            }

            @Override
            public void run() {
                {
                    System.out.println("THREAD ID " + Thread.currentThread().getId() + "ENGAGED");

                    while(!availableDirs.isEmpty()){
                        synchronized (availableDirs) {
                            if(availableDirs.isEmpty()) {
                                try {
                                    availableDirs.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if(!availableDirs.isEmpty()){
                            String path ="A:\\University\\3year\\Cross-platform\\IdeaProjects\\Labs\\Lab07remCOPY\\highSpeedTrash\\" + threadNo + "FILE.txt";
                            String res ="";

                            synchronized (availableDirs) {
                                if(!availableDirs.isEmpty()) {
                                    var FileProcesser = new DirStat(availableDirs.remove(0), Pattern, FileSize);
                                    res = FileProcesser.getStatistics();


                                    availableDirs.addAll(Arrays.asList(FileProcesser.getDirectories()));

                                    availableDirs.notifyAll();
                                }
                            }

                            try {
                                Files.write(Paths.get(path), res.getBytes(), StandardOpenOption.CREATE,
                                        StandardOpenOption.APPEND);
                            } catch (IOException e) {
                                //exception handling left as an exercise for the reader
                            }

                        }
                    }
                    synchronized (availableDirs) {
                        availableDirs.notifyAll();
                    }
                }
            }

        }

        //Arrays.fill(AllThreads, new Thread(threadRun));

        for(int i =0; i<threadCount; i++){
            System.out.println("Attempting to run thread #"+(i+1));
            Runnable r = new BigRunnable(i);
            threads.add(new Thread(r));
        }
    }
}
