import gameEngine.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MasterPanel extends JPanel
{
    private class TimerListener
        implements ActionListener
    {

        public void actionPerformed(ActionEvent actionevent)
        {
            Object obj = actionevent.getSource();
            if(obj == timer)
            {
                synchronized(this)
                {
                    if(timerReady)
                    {
                        timerReady = false;
                        if(midiPlayer == null)
                            System.out.println("MidiPlayer is null");
                        currentLevel.timerLoop();
                        repaint();
                        timerReady = true;
                    }
                }
                ticks++;
                long l = System.currentTimeMillis();
                if(l - startTime >= 500L)
                {
                    prevFPS = (1000D * (double)ticks) / (1.0D * (double)l - (double)startTime);
                    //System.out.println(prevFPS);
                    startTime = l;
                    ticks = 0;
                }
                lastTime = l;
            }
        }

        long startTime;
        long lastTime;
        int ticks;

        private TimerListener()
        {

            startTime = System.currentTimeMillis();
            lastTime = startTime;
            ticks = 0;
        }

    }


    public MasterPanel(JFrame jframe)
    {
        super(true);
        parentFrame = jframe;
        setBackground(Color.black);
        setFocusable(true);
        keyboard = new Keyboard();
        addKeyListener(keyboard);
        config = new ConfigWumpus();
        Toolkit.getDefaultToolkit().sync();
        imageLoader = new ImageLoader(this);
        midiPlayer = new MidiPlayer();
        TimerListener timerlistener = new TimerListener();
        prevFPS = 0.0D;
        timerReady = true;
        timer = new Timer(0, timerlistener);
        setFPS(60);
        startGame();
    }

    public void startGame()
    {
        changeCurrentLevel("Menu");
    }

    public void changeCurrentLevel(String s)
    {
        timer.stop();
        if(currentLevel != null)
            currentLevel.clean();
        changingLevel = true;
        if(s.equals("Menu"))
            currentLevel = new MenuLevel(this);
        else
        if(s.equals("MainLevel"))
            currentLevel = new MainLevel(this);
        else
        if(s.equals("HowToPlay"))
        {
            currentLevel = new HowToPlayLevel(this);
        } else
        {
            System.out.println("Change Level Failed.");
            return;
        }
        currentLevel.loadData();
        timer.start();
    }

    public void endGame()
    {
        timer.stop();
        changingLevel = true;
        if(currentLevel != null)
            currentLevel.clean();
        System.exit(0);
    }

    public void setFPS(int i)
    {
        int j = (int)(1000D / (double)i + 0.5D);
        timer.setDelay(j);
    }

    public Keyboard getKeyboard()
    {
        return keyboard;
    }

    public ConfigWumpus getConfig()
    {
        return config;
    }

    public MidiPlayer getMidiPlayer()
    {
        return midiPlayer;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D graphics2d = (Graphics2D)g;
        graphics2d.setFont(new Font("Arial", 0, 8));
        graphics2d.scale(2D, 2D);
        if(currentLevel != null && !changingLevel)
            currentLevel.render(graphics2d);
        changingLevel = false;
        g.dispose();
    }

    public Frame parentFrame;
    private Timer timer;
    public double prevFPS;
    private Keyboard keyboard;
    private ConfigWumpus config;
    private MidiPlayer midiPlayer;
    public char gameMode;
    public String loadFile;
    private Level currentLevel;
    private boolean changingLevel;
    public ImageLoader imageLoader;
    boolean timerReady;



}
