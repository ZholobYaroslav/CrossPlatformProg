package LogicStuff;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class MainForm
{
    private static JButton button1;
    private static JPanel Lab7;
    private static JTextArea textArea1;
    private static JSpinner spinner1;
    private static JTextField textField1; //TEMPLATE
    private static JTextField textField2; //TIME
    private static JTextField textField3; //FILE SIZE
    private static JRadioButton useExecutionServiceRadioButton;
    private static JRadioButton useRegularThreadsRadioButton;
    private static JLabel threadsLabel;
    private static JLabel template;
    private static JLabel fileSize;
    private static JLabel time;
    private static IWalkable Anthony;

    public MainForm()
    {

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(useExecutionServiceRadioButton.isSelected()){
                    Anthony = new AdvancedFileWalker((int)spinner1.getValue(), textField1.getText(), Long.parseLong(textField3.getText()));
                }else{
                    Anthony =  new FileWalker((int)spinner1.getValue(), textField1.getText(), Long.parseLong(textField3.getText()));
                }

                Anthony.Initialize();

                ThreadMonitor tm = new ThreadMonitor(Anthony.getThreads());
                tm.BeginThreadMonitoring(textArea1, 10);

                Thread waiter = new Thread(()->Anthony.Start(textField2, tm));
                waiter.start();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lab 7");
        // RIGHT-BUTTON PART
        button1 = new JButton("Start");
        frame.add(button1);
        button1.setBounds(420,30,140,50);

        fileSize = new JLabel("File size (Bytes)");
        fileSize.setBounds(440, 100, 100,30);
        frame.add(fileSize);

        textField3 = new JTextField(""); // FILE SIZE
        textField3.setBounds(420, 140, 140,30);
        frame.add(textField3);

        useExecutionServiceRadioButton = new JRadioButton("Execution Service");
        useExecutionServiceRadioButton.setBounds(420, 200, 140,30);
        frame.add(useExecutionServiceRadioButton);
        useRegularThreadsRadioButton = new JRadioButton("Regular Threads");
        useRegularThreadsRadioButton.setBounds(420, 240, 140,30);
        frame.add(useRegularThreadsRadioButton);

        time = new JLabel("Time:");
        time.setBounds(420, 320, 50,30);
        frame.add(time);

        textField2 = new JTextField(""); // TIME
        textField2.setBounds(420, 350, 140,30);
        frame.add(textField2);
        //
        // LEFT-BUTTON PART
        textArea1 = new JTextArea();//("",5,30);

        textArea1.setBounds(20,30, 380, 350);

        JScrollPane scrollableTextArea = new JScrollPane(textArea1);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.getContentPane().add(scrollableTextArea);
        frame.add(textArea1);


        spinner1 = new JSpinner();
        spinner1.setBounds(20, 400, 40,30);
        frame.add(spinner1);

        threadsLabel = new JLabel(" - Threads");
        threadsLabel.setBounds(70, 400, 70,30);
        frame.add(threadsLabel);

        textField1 = new JTextField(""); // TEMPLATE
        textField1.setBounds(160, 400, 110,30);
        frame.add(textField1);

        template = new JLabel(" - Template");
        template.setBounds(280, 400, 70,30);
        frame.add(template);
        //
        Lab7 = new JPanel();
        Lab7.setBounds(30, 30, 400, 400);
        Lab7.setBackground(Color.LIGHT_GRAY);
        frame.add(Lab7);
        ButtonGroup bg = new ButtonGroup();
        bg.add(useExecutionServiceRadioButton);
        bg.add(useRegularThreadsRadioButton);

        insteadOfConstructor();
        frame.getContentPane().add(button1);
        frame.getContentPane().add(Lab7);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 500);
        frame.setVisible(true);
        System.out.println("End");
    }
    public static void insteadOfConstructor()
    {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(useExecutionServiceRadioButton.isSelected())
                {
                    Anthony = new AdvancedFileWalker((int)spinner1.getValue(), textField1.getText(), Long.parseLong(textField3.getText()));
                }else
                {
                    Anthony =  new FileWalker((int)spinner1.getValue(), textField1.getText(), Long.parseLong(textField3.getText()));
                }

                Anthony.Initialize();
                var res = Anthony.getThreads();
                for (Thread t: res)
                {
                    try {
                        t.join();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }

                ThreadMonitor tm = new ThreadMonitor(Anthony.getThreads());
                tm.BeginThreadMonitoring(textArea1, 10);

                Thread waiter = new Thread(()->Anthony.Start(textField2, tm));
                waiter.start();
            }
        });
    }
}
