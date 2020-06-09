package gameEngine;

import java.awt.Component;
import java.awt.Graphics2D;


/**
 * 这是一个抽象类，分别被开始界面MainLevel，游戏界面MasterLevel，帮助界面HowToPlayLevel被继承。
 * 里面的抽象方法loaddata用于加载数据进入游戏界面，timerloop用于不断循环执行动作，render用于图像渲染。
 * 这些方法均在Level的子类中被重写
 */
public abstract class Level
{

    /**
     * 初始化，设置父类容器，以及图像加载器
     * @param component
     */
    public Level(Component component)
    {
        parent = component;
        imgLoader = new ImageLoader(component);
    }

    /**
     * 抽象方法，用于加载数据进入游戏界面
     */
    public abstract void loadData();

    /**
     * 清除此Level子类对象
     */
    public void clean()
    {
        imgLoader = null;
        parent = null;
    }

    /**
     * 因为要轮询键盘事件，故要给一个循环，由这个方法执行
     */
    public abstract void timerLoop();

    /**
     * 用于图像渲染
     * @param graphics2d 绘图类对象
     */
    public abstract void render(Graphics2D graphics2d);

    public Component parent;
    protected ImageLoader imgLoader;
}
