package gameEngine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Hashtable;

public class Keyboard
    implements KeyListener
{

    public Keyboard()
    {
        lastTyped = 0;
        keysPressed = new Hashtable();
        keysTyped = new Hashtable();
    }

    public void keyPressed(KeyEvent keyevent)
    {
        keysPressed.put(new Integer(keyevent.getKeyCode()), new Boolean(true));
        lastTyped = keyevent.getKeyCode();
    }

    public void keyTyped(KeyEvent keyevent)
    {
    }

    public void keyReleased(KeyEvent keyevent)
    {
        keysPressed.put(new Integer(keyevent.getKeyCode()), new Boolean(false));
        keysTyped.put(new Integer(keyevent.getKeyCode()), new Boolean(false));
    }

    public boolean isPressed(int i)
    {
        Boolean boolean1;
        try
        {
            boolean1 = (Boolean)keysPressed.get(Integer.valueOf(i));
            if(boolean1 == null)
                return false;
        }
        catch(Exception exception)
        {
            return false;
        }
        return boolean1.booleanValue();
    }

    public boolean isTyped(int i)//核心方法，当以整型i标记的键被按下时，返回true
    {
        Boolean boolean1;
        Boolean boolean2;
        try
        {
            boolean1 = (Boolean)keysPressed.get(Integer.valueOf(i));
            boolean2 = (Boolean)keysTyped.get(Integer.valueOf(i));
            if(boolean1 == null)
                return false;
        }
        catch(Exception exception)
        {
            return false;
        }
        if(boolean1.booleanValue() && (boolean2 == null || !boolean2.booleanValue()))
        {
            keysTyped.put(Integer.valueOf(i), new Boolean(true));
            return true;
        }
        return false;
    }

    public void updateTyped()
    {
        keysTyped.clear();
    }

    public int lastTyped;
    private Hashtable keysPressed;
    private Hashtable keysTyped;
}
