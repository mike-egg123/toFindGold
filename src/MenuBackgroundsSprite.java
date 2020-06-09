import gameEngine.ImageLoader;
import gameEngine.Sprite;
import java.awt.*;
import java.io.PrintStream;
import java.util.Hashtable;

/**
 * 设置游戏菜单界面背景
 */
public class MenuBackgroundsSprite extends Sprite
{

    /**
     * 初始化游戏菜单界面
     * @param d 横坐标
     * @param d1 纵坐标
     */
    public MenuBackgroundsSprite(double d, double d1)
    {
        super(d, d1);
        numInstances++;
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
     * 加载游戏菜单界面图片，设置坐标
     */
    public static void loadImages()
    {
        try
        {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            java.net.URL url = MenuBackgroundsSprite.class.getResource("graphics/MenuBG.png");
            //System.out.println((new StringBuilder()).append("MenuBackgroundsSprite loading ").append(url).toString());
            Image image = toolkit.getImage(url);
            focusTable.put("menuBg", new Point(0, 0));
            imageTable.put("menuBg", image);
        }
        catch(Exception exception) { }
    }

    /**
     * 清屏
     */
    public static void clean()
    {
        numInstances = 0;
        focusTable.clear();
        imageTable.clear();
    }

    /**
     * 激活游戏菜单页面
     * @param imageloader 承载游戏菜单页面的元素
     */
    protected void animate(ImageLoader imageloader)
    {
        super.animate(imageloader);
        curImage = (Image)imageTable.get("menuBg");
        Point point = (Point)focusTable.get("menuBg");
        fx = 0.0D;
        fy = 50.0D;
    }

    private static int numInstances = 0;
    private static Hashtable imageTable = new Hashtable();
    private static Hashtable focusTable = new Hashtable();

}
