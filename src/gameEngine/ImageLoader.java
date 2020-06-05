package gameEngine;

import java.awt.*;

public class ImageLoader
{

    public ImageLoader(Component component)
    {
        parent = component;
        reset();
    }

    public void reset()
    {
        mt = new MediaTracker(parent);
        nextId = 0;
    }

    public void addImage(Image image)
    {
        mt.addImage(image, nextId);//开始加载以当前id标识的图像
        nextId++;
    }

    public void waitForAll()//开始加载由此媒体跟踪器跟踪的所有图像，在完成加载所有图像之前，该方法一直处于等待
    {
        try
        {
            mt.waitForAll();
        }
        catch(InterruptedException interruptedexception)
        {
            Thread.currentThread().interrupt();
        }
        reset();
    }

    private MediaTracker mt;
    private int nextId;//整型对象id表示image的标识
    private Component parent;
}
