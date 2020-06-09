package gameEngine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Hashtable;

/**
 * 该游戏全程通过键盘控制，因此只写了键盘事件监听器，内部的isTyped（int i）方法
 * 用于监视键盘上以i标识的键是否有键入。对游戏中有效按键，事先定义好一个代表的数
 * 值，当监测到某个键被键入，则返回true，传递给相应的方法触发游戏事件
 *
 * 此类实现了键盘监听器接口
 */
public class Keyboard implements KeyListener
{

    /**
     * 初始化，记录最后按键一次按键的变量置零，为键盘按下和键盘类型分配两个哈希表
     */
    public Keyboard()
    {
        lastTyped = 0;
        keysPressed = new Hashtable();
        keysTyped = new Hashtable();
    }

    /**
     * 键盘按压事件监听，将得到的按键码和一个true的boolean量存入哈希表
     * @param keyevent 键盘事件
     */
    public void keyPressed(KeyEvent keyevent)
    {
        keysPressed.put(new Integer(keyevent.getKeyCode()), new Boolean(true));
        lastTyped = keyevent.getKeyCode();
    }

    /**
     * 无用
     * @param keyevent 键盘事件
     */
    public void keyTyped(KeyEvent keyevent)
    {
    }

    /**
     * 键盘松开事件监听，如果松开某个键，则将该按键码和false存入哈希表
     * @param keyevent 键盘事件
     */
    public void keyReleased(KeyEvent keyevent)
    {
        keysPressed.put(new Integer(keyevent.getKeyCode()), new Boolean(false));
        keysTyped.put(new Integer(keyevent.getKeyCode()), new Boolean(false));
    }

    /**
     * 无用
     * @param i 按键标记
     * @return 判断是否被按下
     */
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

    /**
     * 核心方法，当以整型i标记的键被按下时，返回true
     * @param i 按键标记码
     * @return i对应的按键是否被按下
     */
    public boolean isTyped(int i)
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

    /**
     * 清空按键标记码的哈希表
     */
    public void updateTyped()
    {
        keysTyped.clear();
    }

    public int lastTyped;
    private Hashtable keysPressed;
    private Hashtable keysTyped;
}
