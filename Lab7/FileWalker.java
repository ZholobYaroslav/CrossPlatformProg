package LogicStuff;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class FileWalker implements IWalkable {
    private int threadCount;
    private ArrayList<Thread> threads;
    private String Pattern;
    long FileSize;

    public FileWalker(){
        Pattern="";
        threadCount=1;
    }

    public FileWalker(int threadCount, String Pattern, long FileSize){
        this.threadCount = threadCount;
        this.Pattern = Pattern;
        this.FileSize = FileSize;
    }

    public void Initialize()
    {

        threads = new ArrayList<Thread>();

        CopyOnWriteArrayList<String> availableDirs = new CopyOnWriteArrayList<String>();

        availableDirs.add("A:/NET");

        boolean isRunning = true;


        class BigRunnable implements Runnable
        {

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
                            String path ="C:\\highSpeedTrash\\" + threadNo + "FILE.txt";
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

    public ArrayList<Thread> getThreads(){
        return threads;
    }

    public void Start(JTextField ta, ThreadMonitor tm){
        long startTime = System.currentTimeMillis();
        for(Thread tr : threads){
            tr.start();
        }
        /*for(Thread tr : threads)
        {
            try {
                tr.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        long elapsedTimeMillis = System.currentTimeMillis()-startTime;
        float elapsedTimeSec = elapsedTimeMillis/1000F;
        ta.setText(""+elapsedTimeSec);
        //tm.resetMonitor();

    }
}
