import gameEngine.*;
import java.awt.*;
import java.io.PrintStream;
import java.util.Hashtable;

/**
 * 在游戏中怪物的动态效果较多（有活着和死掉两种状态，每种状态都有对应的特效），
 * 因此专门用一个类控制怪物的显示。
 */
public class WumpusSprite extends Sprite
{

    /**
     * 初始化坏怪，设置坐标和活着的状态，以及是好是坏
     * @param d 横坐标
     * @param d1 纵坐标
     * @param c 怪物类型，'W'是坏怪，'S'是好怪
     */
    public WumpusSprite(double d, double d1, char c)
    {
        super(d, d1);
        numInstances++;
        setAnimation("alive");
        type = c;
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
     * 加载好怪和坏怪的图片
     * @param imageloader 图片加载器
     */
    public static void loadImages(ImageLoader imageloader)
    {
        try
        {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            java.net.URL url = WumpusSprite.class.getResource("graphics/WumpusSprite.png");
            //System.out.println((new StringBuilder()).append("WumpusSprite loading ").append(url).toString());
            Image image = toolkit.getImage(url);
            image = ColorFilters.setTransparentColor(image, new Color(0xff00ff));
            url = WumpusSprite.class.getResource("graphics/SupmuwSprite.png");
            //System.out.println((new StringBuilder()).append("WumpusSprite loading ").append(url).toString());
            Image image1 = toolkit.getImage(url);
            image1 = ColorFilters.setTransparentColor(image1, new Color(0xff00ff));
            byte byte0 = 24;
            byte byte1 = 24;
            Image image2 = ImageBlitter.cropTiled(image, 0, 0, byte0, byte1);
            imageloader.addImage(image2);
            imageTable.put("WAlive0", image2);
            image2 = ImageBlitter.cropTiled(image, 1, 0, byte0, byte1);
            imageloader.addImage(image2);
            imageTable.put("WAlive1", image2);
            image2 = ImageBlitter.cropTiled(image, 0, 1, byte0, byte1);
            imageloader.addImage(image2);
            imageTable.put("WDead", image2);
            image2 = ImageBlitter.cropTiled(image1, 0, 0, byte0, byte1);
            imageloader.addImage(image2);
            imageTable.put("SAlive0", image2);
            image2 = ImageBlitter.cropTiled(image1, 1, 0, byte0, byte1);
            imageloader.addImage(image2);
            imageTable.put("SAlive1", image2);
            image2 = ImageBlitter.cropTiled(image1, 0, 1, byte0, byte1);
            imageloader.addImage(image2);
            imageTable.put("SDead", image2);
            //System.out.println("loaded WumpusSprite");
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
     * 激活所有怪物
     * @param imageloader 图片加载器
     */
    protected void animate(ImageLoader imageloader)
    {
        super.animate(imageloader);
        fx = 0.0D;
        fy = 0.0D;
        if(animation.equals("alive"))
        {
            animFrame = 0;
            byte byte0 = 20;
            int i = 0;
            if(animTimer > i + byte0)
                animFrame = 1;
            i += byte0;
            if(animTimer > i + byte0)
                animTimer = -1;
            curImage = (Image)imageTable.get((new StringBuilder()).append(type).append("Alive").append(animFrame).toString());
        } else
        {
            curImage = (Image)imageTable.get((new StringBuilder()).append(type).append("Dead").toString());
            animTimer = 0;
        }
        animTimer++;
    }

    /**
     * 设置状态
     * @param s 有两个取值： "dead" "alive"，分别对应着死的和活的
     */
    public void setAnimation(String s)
    {
        animTimer = 0;
        animFrame = 0;
        animation = s;
    }

    private static int numInstances = 0;
    private static Hashtable imageTable = new Hashtable();
    private char type;
    private String animation;
    private int animFrame;
    private int animTimer;

}
