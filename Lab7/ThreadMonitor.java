package LogicStuff;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;

public class ThreadMonitor
{
    List<Thread> threads = new ArrayList<>();
    java.util.Timer UpdateTimer = new Timer();

    public ThreadMonitor(List<Thread> threads) {
        this.threads = threads;
    }

    public void BeginThreadMonitoring(JTextArea outputArea, int interval){

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                StringBuilder threadStats = new StringBuilder();

                for(int i=0; i<threads.size(); i++){
                    threadStats.append(String.format("THREAD #%d", i));
                    threadStats.append("\nThread id: " + threads.get(i).getId());
                    threadStats.append("\nName: " + threads.get(i).getName());
                    threadStats.append("\nPriority: " + threads.get(i).getPriority());
                    threadStats.append("\nState: " + threads.get(i).getState());
                    threadStats.append("\nRunning: " + threads.get(i).isAlive());
                    threadStats.append("\nIs interrupted: " + threads.get(i).isInterrupted());
                    threadStats.append("\nIs daemon: " + threads.get(i).isDaemon()+"\n\n\n");
                }

                outputArea.setText(threadStats.toString());
            }
        };
        UpdateTimer.schedule(task, 0, interval);
    }

    public void resetMonitor(){
        UpdateTimer.cancel();
    }
}

