import gameEngine.*;
import java.awt.*;
import java.util.Hashtable;

public class AgentSprite extends Sprite//实现游戏中精灵的动画效果
{

    public AgentSprite(double d, double d1)
    {
        super(d, d1);
        numInstances++;
        direction = 'E';
        setAnimation("walk");
        animationDone = true;
    }

    public void destroy()
    {
        isDestroyed = true;
        numInstances--;
    }

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

    public static void clean()
    {
        numInstances = 0;
        imageTable.clear();
    }

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

    public void setAnimation(String s)
    {
        animationDone = false;
        animTimer = 0;
        animFrame = 0;
        animation = s;
    }

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
