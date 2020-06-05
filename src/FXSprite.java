import gameEngine.*;
import java.awt.*;
import java.io.PrintStream;
import java.net.URL;
import java.util.Hashtable;

public class FXSprite extends Sprite
{

    public FXSprite(double d, double d1, char c)
    {
        super(d, d1);
        numInstances++;
        type = c;
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
            URL url = FXSprite.class.getResource("graphics/BreezeSprite.png");
            //System.out.println((new StringBuilder()).append("FXSprite loading ").append(url).toString());
            Image image = toolkit.getImage(url);
            image = ColorFilters.setTransparentColor(image, new Color(0xff00ff));
            image = ColorFilters.setSemiTransparency(image, 0.5D);
            url = FXSprite.class.getResource("graphics/StenchSprite.png");
            //System.out.println((new StringBuilder()).append("FXSprite loading ").append(url).toString());
            Image image1 = toolkit.getImage(url);
            image1 = ColorFilters.setTransparentColor(image1, new Color(0xff00ff));
            image1 = ColorFilters.setSemiTransparency(image1, 0.5D);
            url = FXSprite.class.getResource("graphics/MooSprite.png");
            //System.out.println((new StringBuilder()).append("FXSprite loading ").append(url).toString());
            Image image2 = toolkit.getImage(url);
            image2 = ColorFilters.setTransparentColor(image2, new Color(0xff00ff));
            image2 = ColorFilters.setSemiTransparency(image2, 0.5D);
            url = FXSprite.class.getResource("graphics/GlitterSprite.png");
            //System.out.println((new StringBuilder()).append("FXSprite loading ").append(url).toString());
            Image image3 = toolkit.getImage(url);
            image3 = ColorFilters.setTransparentColor(image3, new Color(0xff00ff));
            image3 = ColorFilters.setSemiTransparency(image3, 0.25D);
            url = FXSprite.class.getResource("graphics/EntranceSprite.png");
            //System.out.println((new StringBuilder()).append("FXSprite loading ").append(url).toString());
            Image image4 = toolkit.getImage(url);
            image4 = ColorFilters.setTransparentColor(image4, new Color(0xff00ff));
            image4 = ColorFilters.setSemiTransparency(image4, 0.75D);
            byte byte0 = 24;
            byte byte1 = 24;
            for(int i = 0; i < 11; i++)
            {
                Image image5 = ImageBlitter.cropTiled(image, i, 0, byte0, byte1);
                imageloader.addImage(image5);
                imageTable.put((new StringBuilder()).append("breeze").append(i).toString(), image5);
            }

            for(int j = 0; j < 24; j++)
            {
                Image image6 = ImageBlitter.cropTiled(image1, j, 0, byte0, byte1);
                imageloader.addImage(image6);
                imageTable.put((new StringBuilder()).append("stench").append(j).toString(), image6);
            }

            for(int k = 0; k < 6; k++)
            {
                Image image7 = ImageBlitter.cropTiled(image2, k, 0, byte0, byte1);
                imageloader.addImage(image7);
                imageTable.put((new StringBuilder()).append("moo").append(k).toString(), image7);
            }

            for(int l = 0; l < 6; l++)
            {
                Image image8 = ImageBlitter.cropTiled(image3, l, 0, byte0, byte1);
                imageloader.addImage(image8);
                imageTable.put((new StringBuilder()).append("glitter").append(l).toString(), image8);
            }

            imageTable.put("entrance", image4);
            //System.out.println("loaded FXSprite");
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
        if(type == 'B')
        {
            animFrame = 0;
            byte byte0 = 5;
            animFrame = animTimer / byte0;
            if(animFrame >= 11)
            {
                animFrame = 0;
                animTimer = 0;
            }
            curImage = (Image)imageTable.get((new StringBuilder()).append("breeze").append(animFrame).toString());
        } else
        if(type == 'S')
        {
            animFrame = 0;
            byte byte1 = 5;
            animFrame = animTimer / byte1;
            if(animFrame >= 24)
            {
                animFrame = 0;
                animTimer = 0;
            }
            curImage = (Image)imageTable.get((new StringBuilder()).append("stench").append(animFrame).toString());
        } else
        if(type == 'M')
        {
            animFrame = 0;
            byte byte2 = 5;
            int i = 0;
            if(animTimer > byte2 + i)
                animFrame = 1;
            i += byte2;
            if(animTimer > byte2 + i)
                animFrame = 2;
            byte2 = 15;
            i += byte2;
            if(animTimer > byte2 + i)
                animFrame = 3;
            byte2 = 5;
            i += byte2;
            if(animTimer > byte2 + i)
                animFrame = 4;
            i += byte2;
            if(animTimer > byte2 + i)
                animFrame = 5;
            i += byte2;
            if(animTimer > byte2 + i)
                animTimer = 0;
            curImage = (Image)imageTable.get((new StringBuilder()).append("moo").append(animFrame).toString());
        } else
        if(type == 'G')
        {
            animFrame = 0;
            byte byte3 = 5;
            animFrame = animTimer / byte3;
            if(animFrame >= 6)
            {
                animFrame = 0;
                animTimer = 0;
            }
            curImage = (Image)imageTable.get((new StringBuilder()).append("glitter").append(animFrame).toString());
        } else
        if(type == 'E')
            curImage = (Image)imageTable.get("entrance");
        else
            System.out.println("FX error");
        animTimer++;
    }

    private static int numInstances = 0;
    private static Hashtable imageTable = new Hashtable();
    private char type;
    private String animation;
    private int animFrame;
    private int animTimer;

}
