package gameEngine;

import java.awt.*;
import java.awt.geom.*;

/**
 * 这是一个抽象类，封装了与游戏中角色形象有关的位置参数，运动参数等信息。在之后每种角色的子类中进行了重写
 */
public abstract class Sprite
{

    /**
     * 无参构造函数，设置坐标为（0.0，0.0）
     */
    public Sprite()
    {
        this(0.0D, 0.0D);
    }

    /**
     * 设置该图形元素的各种属性
     * @param d 横坐标
     * @param d1 纵坐标
     */
    public Sprite(double d, double d1)
    {
        x = d;
        y = d1;
        z = 0.0D;
        width = 0.0D;
        height = 0.0D;
        depth = 0.0D;
        scaleX = 1.0D;
        scaleY = 1.0D;
        boxScale = 1.0D;
        rotation = 0.0D;
        transform = new AffineTransform();
        transformChanged = false;
        colorTransformChanged = false;
        semiTransparency = 0.0D;
        isVisible = true;
        isDestroyed = false;
    }

    /**
     * 渲染该图形元素
     * @param graphics2d 绘图类对象
     * @return 是否渲染成功
     */
    public boolean render(Graphics2D graphics2d)
    {
        if(isDestroyed)
            return false;
        if(semiTransparency == 1.0D || !isVisible)
            return true;
        if(transformChanged)
            updateTransform();
        AffineTransform affinetransform = graphics2d.getTransform();
        AffineTransform affinetransform1 = graphics2d.getTransform();
        affinetransform1.translate(x, y);
        affinetransform1.concatenate(transform);
        affinetransform1.translate(0.0D - fx, 0.0D - fy);
        graphics2d.setTransform(affinetransform1);
        draw(graphics2d);
        graphics2d.setTransform(affinetransform);
        return true;
    }

    /**
     * 将当前图片绘制到GUI上
     * @param graphics2d 绘图类对象
     */
    protected void draw(Graphics2D graphics2d)
    {
        graphics2d.drawImage(curImage, null, null);
    }

    /**
     * 激活该图形元素，使其可以加载图片
     * @param imageloader 图形加载器
     */
    protected void animate(ImageLoader imageloader)
    {
        if(isDestroyed)
            return;
        else
            return;
    }

    /**
     * 更新图片的透明度
     */
    protected void updateTransform()
    {
        transform = new AffineTransform();
        transform.scale(scaleX, scaleY);
        transform.rotate((rotation / 360D) * 3.1415926535897931D * 2D);
    }

    public Component parent;
    public double x;
    public double y;
    public double z;
    protected double fx;
    protected double fy;
    protected double fz;
    public double width;
    public double height;
    public double depth;
    protected double scaleX;
    protected double scaleY;
    protected double rotation;
    protected AffineTransform transform;
    protected boolean transformChanged;
    protected double r;
    protected double boxScale;
    protected int collisionType;
    public boolean isDestroyed;
    public boolean isVisible;
    protected Image curImage;
    protected Image curImageTrans;
    protected AffineTransform colorTransform;
    protected boolean colorTransformChanged;
    protected double semiTransparency;
}
