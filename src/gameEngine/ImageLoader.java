package gameEngine;

import java.awt.*;

/**
 * 调用媒体跟踪器，加载及显示图像
 */
public class ImageLoader
{

    /**
     * 初始化，将此图像加载器附着在一个父类容器上
     * @param component
     */
    public ImageLoader(Component component)
    {
        parent = component;
        reset();
    }

    /**
     * 重置此图像加载器，即将下标归零
     */
    public void reset()
    {
        mt = new MediaTracker(parent);
        nextId = 0;
    }

    /**
     * 往图像加载器中添加图片，加载以当前id标识的图像
     * @param image 被添加的图片
     */
    public void addImage(Image image)
    {
        mt.addImage(image, nextId);
        nextId++;
    }

    /**
     * 开始加载由此媒体跟踪器跟踪的所有图像，在完成加载所有图像之前，该方法一直处于等待
     */
    public void waitForAll()
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
