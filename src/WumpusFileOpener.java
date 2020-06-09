import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * 调试用类，废弃
 */
public class WumpusFileOpener extends FileDialog
    implements WindowListener
{

    public WumpusFileOpener(Frame frame)
    {
        super(frame);
        parentFrame = frame;
    }

    public void windowActivated(WindowEvent windowevent)
    {
    }

    public void windowClosed(WindowEvent windowevent)
    {
    }

    public void windowClosing(WindowEvent windowevent)
    {
        parentFrame.toFront();
    }

    public void windowDeactivated(WindowEvent windowevent)
    {
    }

    public void windowDeiconified(WindowEvent windowevent)
    {
    }

    public void windowIconified(WindowEvent windowevent)
    {
    }

    public void windowOpened(WindowEvent windowevent)
    {
    }

    private Frame parentFrame;
}
