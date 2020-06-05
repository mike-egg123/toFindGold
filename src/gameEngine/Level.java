package gameEngine;

import java.awt.Component;
import java.awt.Graphics2D;


public abstract class Level//这是一个抽象类，类里面的方法在其子类中被重写
{

    public Level(Component component)
    {
        parent = component;
        imgLoader = new ImageLoader(component);
    }

    public abstract void loadData();

    public void clean()
    {
        imgLoader = null;
        parent = null;
    }

    public abstract void timerLoop();//用于不断循环执行动作

    public abstract void render(Graphics2D graphics2d);

    public Component parent;
    protected ImageLoader imgLoader;
}
