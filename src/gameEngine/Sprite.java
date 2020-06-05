package gameEngine;

import java.awt.*;
import java.awt.geom.*;

public abstract class Sprite//这是一个抽象类，封装了有关人物角色的属性参数，在不同的子类中被重写
{

    public Sprite()
    {
        this(0.0D, 0.0D);
    }

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

    public void setParent(Component component)
    {
        parent = component;
    }

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

    protected void draw(Graphics2D graphics2d)
    {
        graphics2d.drawImage(curImage, null, null);
    }

    protected void animate(ImageLoader imageloader)
    {
        if(isDestroyed)
            return;
        else
            return;
    }

    public void setSemiTransparency(double d)
    {
        if(parent == null)
            semiTransparency = 1.0D;
        if(d > 1.0D)
            d = 1.0D;
        if(d < 0.0D)
            d = 0.0D;
        colorTransformChanged = true;
        semiTransparency = d;
    }

    public double getSemiTransparency()
    {
        return semiTransparency;
    }

    public void scale(double d, double d1)
    {
        scaleX = d;
        scaleY = d1;
        transformChanged = true;
    }

    public void rotate(double d)
    {
        rotation = d;
        transformChanged = true;
    }

    protected void updateTransform()
    {
        transform = new AffineTransform();
        transform.scale(scaleX, scaleY);
        transform.rotate((rotation / 360D) * 3.1415926535897931D * 2D);
    }

    public boolean boxCollision(Sprite sprite)//原打算用于实现碰撞效果，由于未知的原因没有实现
    {
        if(transformChanged)
            updateTransform();
        if(sprite.transformChanged)
            sprite.updateTransform();
        int i = (int)((x - fx * boxScale) + 0.5D);
        int j = (int)(width * boxScale + 0.5D);
        int k = (int)((y - fy * boxScale) + 0.5D);
        int l = (int)(height * boxScale + 0.5D);
        AffineTransform affinetransform = AffineTransform.getTranslateInstance(x, y);
        affinetransform.concatenate(transform);
        affinetransform.translate(0.0D - x, 0.0D - y);
        Point2D apoint2d[] = new Point2D[4];
        apoint2d[0] = affinetransform.transform(new Point(i, k), apoint2d[0]);
        apoint2d[1] = affinetransform.transform(new Point(i + j, k), apoint2d[1]);
        apoint2d[2] = affinetransform.transform(new Point(i + j, k + l), apoint2d[2]);
        apoint2d[3] = affinetransform.transform(new Point(i, k + l), apoint2d[3]);
        int i1 = (int)((sprite.x - sprite.fx * sprite.boxScale) + 0.5D);
        int j1 = (int)(sprite.width * sprite.boxScale + 0.5D);
        int k1 = (int)((sprite.y - sprite.fy * sprite.boxScale) + 0.5D);
        int l1 = (int)(sprite.height * sprite.boxScale + 0.5D);
        AffineTransform affinetransform1 = AffineTransform.getTranslateInstance(sprite.x, sprite.y);
        affinetransform1.concatenate(sprite.transform);
        affinetransform1.translate(0.0D - sprite.x, 0.0D - sprite.y);
        Point2D apoint2d1[] = new Point2D[4];
        apoint2d1[0] = affinetransform1.transform(new Point(i1, k1), apoint2d1[0]);
        apoint2d1[1] = affinetransform1.transform(new Point(i1 + j1, k1), apoint2d1[1]);
        apoint2d1[2] = affinetransform1.transform(new Point(i1 + j1, k1 + l1), apoint2d1[2]);
        apoint2d1[3] = affinetransform1.transform(new Point(i1, k1 + l1), apoint2d1[3]);
        if(axisOfSeparation(apoint2d, apoint2d1))
            return axisOfSeparation(apoint2d1, apoint2d);
        else
            return false;
    }

    private boolean axisOfSeparation(Point2D apoint2d[], Point2D apoint2d1[])
    {
        for(int i = 0; i < 4; i++)
        {
            Point2D point2d = apoint2d[i];
            Point2D point2d1 = apoint2d[(i + 1) % 4];
            int j = (int)(point2d1.getX() - point2d.getX());
            int k = (int)(point2d1.getY() - point2d.getY());
            int l = k;
            int i1 = 0 - j;
            int j1 = j * i1 - k * l;
            if(j1 == 0)
                j1 = 1;
            boolean flag = true;
            int k1 = 0;
            do
            {
                if(k1 >= 4)
                    break;
                Point2D point2d2 = apoint2d1[k1];
                double d = ((double)j * (point2d2.getY() - point2d.getY()) + (double)k * (point2d.getX() - point2d2.getX())) / (double)j1;
                if(d < 0.0D)
                {
                    flag = false;
                    break;
                }
                k1++;
            } while(true);
            if(flag)
                return false;
        }

        return true;
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
