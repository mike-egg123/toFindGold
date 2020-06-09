import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 游戏主函数。继承了JFrame类，在屏幕上显示弹窗并设置窗口监视器
 */
public class Main extends JFrame implements WindowListener, WindowFocusListener, ComponentListener
{

    /**
     * 初始化显示窗体
     * @param flag 是否为全屏模式
     */
    public Main(boolean flag)
    {
        super("FIND YOUR GOLD!!!");
        screenX = 680;
        screenY = 576;
        setSize(690, 614);
        addWindowListener(this);
        addWindowFocusListener(this);
        addComponentListener(this);
        grDev = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        oldDisplay = grDev.getDisplayMode();
        if(flag)
        {
            //System.out.println("Trying to start program i n Fullscreen mode.");
            if(grDev.isFullScreenSupported())
            {
                //System.out.println("FullScreen is supported");
                setUndecorated(true);
                DisplayMode displaymode = new DisplayMode(800, 600, 32, 0);
                try
                {
                    grDev.setFullScreenWindow(this);
                    grDev.setDisplayMode(displaymode);
                    //System.out.println("Change resolution: Success!");
                }
                catch(Exception exception1)
                {
                    System.out.println((new StringBuilder()).append("Change resolution: FAIL! ").append(exception1).toString());
                    flag = false;
                }
            }
            setExtendedState(6);
        }
        screenP = new MasterPanel(this);
        bgFraming = new JPanel();
        bgFraming.setBackground(Color.black);
        add(bgFraming);
        bgFraming.setLayout(null);
        bgFraming.add(screenP);
        screenP.setSize(screenX, screenY);
        setDefaultCloseOperation(3);
        setVisible(true);
        recenterScreen();
        try
        {
            screenP.requestFocusInWindow();
        }
        catch(Exception exception) { }
        //System.out.println("Game Window successfully created!!!");
    }

    /**
     * 使得显示容器在窗体的中间，但是后面改了位置后发现这个方法似乎失效了。。
     */
    public void recenterScreen()
    {
        Dimension dimension = getSize();
        int i = dimension.width;
        int j = dimension.height;
        screenP.setLocation(0, 0);
    }

    /**
     *以下方法纯粹是在使得Main方法不是一个抽象类，因为Main继承了一些监听器抽象类，
     * 而对其中的一些抽象方法并没有实际使用
     */
    public void windowActivated(WindowEvent windowevent)
    {
        //System.out.println("Window Activated");
    }

    public void windowClosed(WindowEvent windowevent)
    {
        //System.out.println("Adventure Kitteh program terminated. Restoring original display settings.");
        grDev.setDisplayMode(oldDisplay);
    }

    public void windowClosing(WindowEvent windowevent)
    {
        //System.out.println("Window closing");
    }

    public void windowDeactivated(WindowEvent windowevent)
    {
        //System.out.println("Window deactivated");
    }

    public void windowDeiconified(WindowEvent windowevent)
    {
        //System.out.println("Window Deiconified");
        try
        {
            recenterScreen();
            screenP.requestFocusInWindow();
        }
        catch(Exception exception) { }
    }

    public void windowIconified(WindowEvent windowevent)
    {
        //System.out.println("Window Iconified");
    }

    public void windowOpened(WindowEvent windowevent)
    {
        //System.out.println("Window opened");
    }

    public void windowGainedFocus(WindowEvent windowevent)
    {
       //System.out.println("Window gained focus");
        try
        {
            recenterScreen();
            screenP.requestFocusInWindow();
        }
        catch(Exception exception) { }
    }

    public void windowLostFocus(WindowEvent windowevent)
    {
        //System.out.println("Window lost focus");
    }

    public void componentHidden(ComponentEvent componentevent)
    {
    }

    public void componentMoved(ComponentEvent componentevent)
    {
    }
    public void componentResized(ComponentEvent componentevent)
    {
        recenterScreen();
    }

    public void componentShown(ComponentEvent componentevent)
    {
    }

    /**
     * 主函数入口，也是整个游戏的入口
     * @param args 没用哈哈哈哈哈哈哈哈哈哈哈
     */
    public static void main(String args[])
    {
        String args1[] = args;
        int i = args1.length;
        for(int j = 0; j < i; j++)
        {
            String s = args1[j];
            //System.out.println(s);
        }

        Main wumplusmain;
        if(args.length == 0)
            wumplusmain = new Main(false);
        else
        if(args[0].equals("fullscreen"))
            wumplusmain = new Main(true);
    }

    private MasterPanel screenP;
    private JPanel bgFraming;
    private int screenX;
    private int screenY;
    private GraphicsDevice grDev;
    private DisplayMode oldDisplay;
}
