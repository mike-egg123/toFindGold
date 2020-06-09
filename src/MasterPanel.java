import gameEngine.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * JPanel的扩展类，控制游戏面板上的行为监听器
 */
public class MasterPanel extends JPanel
{
    /**
     * 私有的计时监听器
     */
    private class TimerListener implements ActionListener
    {

        /**
         * 判断当前触发事件源并执行相应的操作
         * @param actionevent 触发事件
         */
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


    /**
     * 初始化，设置监听器
     * @param jframe 显示游戏界面的窗体
     */
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

    /**
     * 这个开始游戏指的是进入到菜单页面，也就是刚开始运行的那个页面，并不是真正的开始游戏
     */
    public void startGame()
    {
        changeCurrentLevel("Menu");
    }

    /**切换当前页面
     * @param s 页面标识字符串，取值有三种"Menu" "MainLevel" "HowToPlay"，分别对应着
     *          菜单页面，游戏页面和帮助页面
     */
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

    /**
     * 退出整个游戏，即游戏窗口关闭
     */
    public void endGame()
    {
        timer.stop();
        changingLevel = true;
        if(currentLevel != null)
            currentLevel.clean();
        System.exit(0);
    }

    /**
     * 设置刷新帧率
     * @param i 帧率，一般为60帧
     */
    public void setFPS(int i)
    {
        int j = (int)(1000D / (double)i + 0.5D);
        timer.setDelay(j);
    }

    /**
     * 以下是getters
     */
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

    /**
     * 将容器画在GUI上
     * @param g 绘图类对象
     */
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
