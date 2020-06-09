import gameEngine.*;
import java.awt.*;
import java.util.Hashtable;

/**
 * 控制帮助页面中图片的显示（将被HowToPlayLevel使用）
 */
public class HowToPlaySprite extends Sprite
{

    /**
     * 初始化帮助页面中的元素
     * @param d 横坐标
     * @param d1 纵坐标
     * @param c 类型，取值有'b' '2' '6' 'e'，分别对应着背景图片、游戏场景之一、游戏场景之二、“按回车键继续”字样
     */
    public HowToPlaySprite(double d, double d1, char c)
    {
        super(d, d1);
        numInstances++;
        type = c;
    }

    /**
     * 销毁元素
     */
    public void destroy()
    {
        isDestroyed = true;
        numInstances--;
    }

    /**
     * 加载上述图片
     * @param imageloader 承载上述图片
     */
    public static void loadImages(ImageLoader imageloader)
    {
        try
        {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            java.net.URL url = HowToPlaySprite.class.getResource("graphics/HowToPlayBG.png");
            //System.out.println((new StringBuilder()).append("HowToPlaySprite loading ").append(url).toString());
            Image image = toolkit.getImage(url);
            image = ColorFilters.setTransparentColor(image, new Color(0xff00ff));
            imageloader.addImage(image);
            imageTable.put("bg", image);
            url = HowToPlaySprite.class.getResource("graphics/HowToPlay2.png");
            //System.out.println((new StringBuilder()).append("HowToPlaySprite loading ").append(url).toString());
            image = toolkit.getImage(url);
            image = ColorFilters.setTransparentColor(image, new Color(0xff00ff));
            imageloader.addImage(image);
            imageTable.put("htp2", image);
            url = HowToPlaySprite.class.getResource("graphics/HowToPlay6.png");
            //System.out.println((new StringBuilder()).append("HowToPlaySprite loading ").append(url).toString());
            image = toolkit.getImage(url);
            image = ColorFilters.setTransparentColor(image, new Color(0xff00ff));
            imageloader.addImage(image);
            imageTable.put("htp6", image);
            url = HowToPlaySprite.class.getResource("graphics/PressEnterText.png");
            //System.out.println((new StringBuilder()).append("HowToPlaySprite loading ").append(url).toString());
            image = toolkit.getImage(url);
            image = ColorFilters.setTransparentColor(image, new Color(0xff00ff));
            imageloader.addImage(image);
            imageTable.put("enter", image);
            //System.out.println("loaded HowToPlaySprite");
        }
        catch(Exception exception) { }
    }

    /**
     * 清除上述图片
     */
    public static void clean()
    {
        numInstances = 0;
        imageTable.clear();
    }

    /**
     * 激活上述图片
     * @param imageloader 图片容器，承载上述图片
     */
    protected void animate(ImageLoader imageloader)
    {
        super.animate(imageloader);
        if(type == 'b')
            curImage = (Image)imageTable.get("bg");
        if(type == '2')
            curImage = (Image)imageTable.get("htp2");
        if(type == '6')
            curImage = (Image)imageTable.get("htp6");
        if(type == 'e')
            curImage = (Image)imageTable.get("enter");
        fx = 0.0D;
        fy = 0.0D;
    }

    private static int numInstances = 0;
    private static Hashtable imageTable = new Hashtable();
    private char type;

}
