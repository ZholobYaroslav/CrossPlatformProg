package LogicStuff;

import javax.swing.*;
import java.util.ArrayList;

public interface IWalkable {

    public ArrayList<Thread> getThreads();
    public void Start(JTextField ta, ThreadMonitor tm);
    public void Initialize();
}
