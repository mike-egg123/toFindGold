import gameEngine.*;
import java.awt.*;
import java.util.Hashtable;

/**
 * 对菜单界面的选择作出响应，传递一个字符给相应的方法，游戏进入对应新界面
 */
public class MenuSprite extends Sprite
{

    /**
     * 初始化，其中的isSelected表示该选项是否被选中
     * @param d 横坐标
     * @param d1 纵坐标
     * @param c 第一级或第二级菜单界面选择的类型，取值如下：'a' 'n' 'h' 'q' 't' 'l' 'r'
     *          分别对应着：ai模式，开始游戏，帮助说明，退出游戏，标题（就是显示，并不能被选中），简单模式，困难模式
     */
    public MenuSprite(double d, double d1, char c)
    {
        super(d, d1);
        numInstances++;
        type = c;
        isSelected = false;
    }

    /**
     * 销毁
     */
    public void destroy()
    {
        isDestroyed = true;
        numInstances--;
    }

    /**
     * 加载图片
     * @param imageloader 图片加载器
     */
    public static void loadImages(ImageLoader imageloader)
    {
        try
        {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            java.net.URL url = MenuSprite.class.getResource("graphics/MenuTextSprites.png");
            //System.out.println((new StringBuilder()).append("MenuSprite loading ").append(url).toString());
            Image image = toolkit.getImage(url);
            image = ColorFilters.setTransparentColor(image, new Color(0xff00ff));
            byte byte0 = 100;
            byte byte1 = 16;
            Image image1 = ImageBlitter.cropTiled(image, 0, 0, byte0, byte1);
            imageTable.put("ai1", image1);
            imageloader.addImage(image1);
            image1 = ImageBlitter.cropTiled(image, 1, 0, byte0, byte1);
            imageTable.put("ai2", image1);
            imageloader.addImage(image1);
            image1 = ImageBlitter.cropTiled(image, 0, 1, byte0, byte1);
            imageTable.put("norm1", image1);
            imageloader.addImage(image1);
            image1 = ImageBlitter.cropTiled(image, 1, 1, byte0, byte1);
            imageTable.put("norm2", image1);
            imageloader.addImage(image1);
            image1 = ImageBlitter.cropTiled(image, 0, 2, byte0, byte1);
            imageTable.put("htp1", image1);
            imageloader.addImage(image1);
            image1 = ImageBlitter.cropTiled(image, 1, 2, byte0, byte1);
            imageTable.put("htp2", image1);
            imageloader.addImage(image1);
            image1 = ImageBlitter.cropTiled(image, 0, 3, byte0, byte1);
            imageTable.put("quit1", image1);
            imageloader.addImage(image1);
            image1 = ImageBlitter.cropTiled(image, 1, 3, byte0, byte1);
            imageTable.put("quit2", image1);
            imageloader.addImage(image1);
            image1 = ImageBlitter.cropTiled(image, 0, 4, byte0, byte1);
            imageTable.put("load1", image1);
            imageloader.addImage(image1);
            image1 = ImageBlitter.cropTiled(image, 1, 4, byte0, byte1);
            imageTable.put("load2", image1);
            imageloader.addImage(image1);
            image1 = ImageBlitter.cropTiled(image, 0, 5, byte0, byte1);
            imageTable.put("random1", image1);
            imageloader.addImage(image1);
            image1 = ImageBlitter.cropTiled(image, 1, 5, byte0, byte1);
            imageTable.put("random2", image1);
            imageloader.addImage(image1);
            url = MenuSprite.class.getResource("graphics/TitleSprite.png");
            //System.out.println((new StringBuilder()).append("MenuSprite loading ").append(url).toString());
            image1 = toolkit.getImage(url);
            image1 = ColorFilters.setTransparentColor(image1, new Color(0xff00ff));
            imageTable.put("title", image1);
            imageloader.addImage(image1);
            //System.out.println("loaded MenuSprite");
        }
        catch(Exception exception) { }
    }

    /**
     * 清屏
     */
    public static void clean()
    {
        numInstances = 0;
        imageTable.clear();
    }

    /**
     * 激活所有元素
     * @param imageloader 图片加载器
     */
    protected void animate(ImageLoader imageloader)
    {
        super.animate(imageloader);
        if(type == 'a' && !isSelected)
            curImage = (Image)imageTable.get("ai1");
        if(type == 'a' && isSelected)
            curImage = (Image)imageTable.get("ai2");
        if(type == 'n' && !isSelected)
            curImage = (Image)imageTable.get("norm1");
        if(type == 'n' && isSelected)
            curImage = (Image)imageTable.get("norm2");
        if(type == 'h' && !isSelected)
            curImage = (Image)imageTable.get("htp1");
        if(type == 'h' && isSelected)
            curImage = (Image)imageTable.get("htp2");
        if(type == 'q' && !isSelected)
            curImage = (Image)imageTable.get("quit1");
        if(type == 'q' && isSelected)
            curImage = (Image)imageTable.get("quit2");
        if(type == 't')
            curImage = (Image)imageTable.get("title");
        if(type == 'l' && !isSelected)
            curImage = (Image)imageTable.get("load1");
        if(type == 'l' && isSelected)
            curImage = (Image)imageTable.get("load2");
        if(type == 'r' && !isSelected)
            curImage = (Image)imageTable.get("random1");
        if(type == 'r' && isSelected)
            curImage = (Image)imageTable.get("random2");
        fx = 0.0D;
        fy = 0.0D;
    }

    private static int numInstances = 0;
    private static Hashtable imageTable = new Hashtable();
    private char type;
    public boolean isSelected;

}
