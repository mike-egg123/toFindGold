import gameEngine.*;
import java.awt.*;
import java.util.Hashtable;

/**
 * 实现游戏中精灵的动画效果，如行走效果，抓取金币效果，射箭效果，死亡效果
 */
public class AgentSprite extends Sprite
{

    /**
     * 初始化精灵行为：位置、方向、行走状态，numInstances指示着这是洞内的第几个元素
     * @param d 精灵位置横坐标
     * @param d1 精灵位置纵坐标
     */
    public AgentSprite(double d, double d1)
    {
        super(d, d1);
        numInstances++;
        direction = 'E';
        setAnimation("walk");
        animationDone = true;
    }

    /**
     * 销毁精灵
     */
    public void destroy()
    {
        isDestroyed = true;
        numInstances--;
    }

    /**
     * 加载精灵图片以及精灵相关的特效图片
     * @param imageloader 图片容器，用以存放和组合图片
     */
    public static void loadImages(ImageLoader imageloader)
    {
        try
        {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            java.net.URL url = null;
            url = AgentSprite.class.getResource("graphics/AgentSprite.png");
            System.out.println((new StringBuilder()).append("AgentSprite loading ").append(url).toString());
            Image image = toolkit.getImage(url);
            image = ColorFilters.setTransparentColor(image, new Color(0xff00ff));
            byte byte0 = 24;
            byte byte1 = 24;
            Image image1 = ImageBlitter.cropTiled(image, 0, 0, byte0, byte1);
            imageloader.addImage(image1);
            imageTable.put("walk0E", image1);
            image1 = ImageBlitter.cropTiled(image, 1, 0, byte0, byte1);
            imageloader.addImage(image1);
            imageTable.put("walk1E", image1);
            image1 = ImageBlitter.cropTiled(image, 2, 0, byte0, byte1);
            imageloader.addImage(image1);
            imageTable.put("walk0N", image1);
            image1 = ImageBlitter.cropTiled(image, 3, 0, byte0, byte1);
            imageloader.addImage(image1);
            imageTable.put("walk1N", image1);
            image1 = ImageBlitter.cropTiled(image, 4, 0, byte0, byte1);
            imageloader.addImage(image1);
            imageTable.put("walk0W", image1);
            image1 = ImageBlitter.cropTiled(image, 5, 0, byte0, byte1);
            imageloader.addImage(image1);
            imageTable.put("walk1W", image1);
            image1 = ImageBlitter.cropTiled(image, 6, 0, byte0, byte1);
            imageloader.addImage(image1);
            imageTable.put("walk0S", image1);
            image1 = ImageBlitter.cropTiled(image, 7, 0, byte0, byte1);
            imageloader.addImage(image1);
            imageTable.put("walk1S", image1);
            image1 = ImageBlitter.cropTiled(image, 0, 1, byte0, byte1);
            imageloader.addImage(image1);
            imageTable.put("gold0", image1);
            image1 = ImageBlitter.cropTiled(image, 1, 1, byte0, byte1);
            imageloader.addImage(image1);
            imageTable.put("gold1", image1);
            image1 = ImageBlitter.cropTiled(image, 2, 1, byte0, byte1);
            imageloader.addImage(image1);
            imageTable.put("gold2", image1);
            image1 = ImageBlitter.cropTiled(image, 3, 1, byte0, byte1);
            imageloader.addImage(image1);
            imageTable.put("gold3", image1);
            image1 = ImageBlitter.cropTiled(image, 4, 1, byte0, byte1);
            imageloader.addImage(image1);
            imageTable.put("shootE", image1);
            image1 = ImageBlitter.cropTiled(image, 5, 1, byte0, byte1);
            imageloader.addImage(image1);
            imageTable.put("shootN", image1);
            image1 = ImageBlitter.cropTiled(image, 6, 1, byte0, byte1);
            imageloader.addImage(image1);
            imageTable.put("shootW", image1);
            image1 = ImageBlitter.cropTiled(image, 7, 1, byte0, byte1);
            imageloader.addImage(image1);
            imageTable.put("shootS", image1);
            image1 = ImageBlitter.cropTiled(image, 0, 2, byte0, byte1);
            imageloader.addImage(image1);
            imageTable.put("die0", image1);
            image1 = ImageBlitter.cropTiled(image, 1, 2, byte0, byte1);
            imageloader.addImage(image1);
            imageTable.put("die1", image1);
            image1 = ImageBlitter.cropTiled(image, 2, 2, byte0, byte1);
            imageloader.addImage(image1);
            imageTable.put("die2", image1);
            image1 = ImageBlitter.cropTiled(image, 3, 2, byte0, byte1);
            imageloader.addImage(image1);
            imageTable.put("die3", image1);
            image1 = ImageBlitter.cropTiled(image, 4, 2, byte0, byte1);
            imageloader.addImage(image1);
            imageTable.put("die4", image1);
            image1 = ImageBlitter.cropTiled(image, 5, 2, byte0, byte1);
            imageloader.addImage(image1);
            imageTable.put("die5", image1);
            //System.out.println("loaded AgentSprite");
        }
        catch(Exception exception) { }
    }

    /**
     * 清除精灵状态，以便开始新的一局
     */
    public static void clean()
    {
        numInstances = 0;
        imageTable.clear();
    }

    /**
     * 激活精灵，使其可以根据键盘的输入做出对应的动作
     * @param imageloader 图片容器，与之前的那个一致，承载游戏里的所有图片
     */
    protected void animate(ImageLoader imageloader)
    {
        super.animate(imageloader);
        fx = 0.0D;
        fy = 0.0D;
        if(animation.equals("walk"))
        {
            animationDone = true;
            animFrame = 0;
            byte byte0 = 20;
            int i = 0;
            if(animTimer > i + byte0)
                animFrame = 1;
            i += byte0;
            if(animTimer > i + byte0)
                animTimer = -1;
            curImage = (Image)imageTable.get((new StringBuilder()).append("walk").append(animFrame).append(direction).toString());
        } else
        if(animation.equals("gold"))
        {
            animFrame = 0;
            int j = 0;
            byte byte1 = 5;
            if(animTimer > j + byte1)
                animFrame = 1;
            j += byte1;
            byte1 = 5;
            if(animTimer > j + byte1)
                animFrame = 2;
            j += byte1;
            byte1 = 5;
            if(animTimer > j + byte1)
                animFrame = 3;
            j += byte1;
            byte1 = 15;
            if(animTimer > j + byte1)
                setAnimation("walk");
            curImage = (Image)imageTable.get((new StringBuilder()).append("gold").append(animFrame).toString());
        } else
        if(animation.equals("shoot"))
        {
            if(animTimer > 30)
                setAnimation("walk");
            curImage = (Image)imageTable.get((new StringBuilder()).append("shoot").append(direction).toString());
        } else
        if(animation.equals("die"))
        {
            animFrame = 0;
            int k = 0;
            byte byte2 = 10;
            if(animTimer > k + byte2)
                animFrame = 1;
            k += byte2;
            byte2 = 10;
            if(animTimer > k + byte2)
                animFrame = 2;
            k += byte2;
            byte2 = 10;
            if(animTimer > k + byte2)
                animFrame = 3;
            k += byte2;
            byte2 = 10;
            if(animTimer > k + byte2)
                animFrame = 4;
            k += byte2;
            byte2 = 10;
            if(animTimer > k + byte2)
            {
                animFrame = 5;
                animTimer--;
            }
            curImage = (Image)imageTable.get((new StringBuilder()).append("die").append(animFrame).toString());
        } else
        {
            //System.out.println("Agent error: bad animation");
        }
        animTimer++;
    }

    /**
     * 设置精灵状态
     * @param s 取值有"walk" "gold" "shoot" "die"，分别对应四种状态
     */
    public void setAnimation(String s)
    {
        animationDone = false;
        animTimer = 0;
        animFrame = 0;
        animation = s;
    }

    /**
     * 设置精灵方向
     * @param c 取值有'N' 'S' 'E' 'W' ，分别对应着北、南、东、西
     */
    public void setDirection(char c)
    {
        if(c == 'N' || c == 'S' || c == 'E' || c == 'W')
            direction = c;
        else
            System.out.println("bad direction given to agent.");
    }

    private static int numInstances = 0;
    private static Hashtable imageTable = new Hashtable();
    private char direction;
    public String animation;
    private int animFrame;
    private int animTimer;
    public boolean animationDone;

}
