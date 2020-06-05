import gameEngine.*;
import java.awt.*;
import java.io.PrintStream;
import java.util.Hashtable;

public class HUDSprite extends Sprite
{

    public HUDSprite(double d, double d1, char c)
    {
        super(d, d1);
        numInstances++;
        type = c;
        isSelected = false;
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
            java.net.URL url = HUDSprite.class.getResource("graphics/hudPanel.png");
            //System.out.println((new StringBuilder()).append("HUDSprite loading ").append(url).toString());
            Image image = toolkit.getImage(url);
            url = HUDSprite.class.getResource("graphics/HudIconSprites.png");
            //System.out.println((new StringBuilder()).append("HUDSprite loading hudIconSprites.png : ").append(url).toString());
            Image image1 = toolkit.getImage(url);
            url = HUDSprite.class.getResource("graphics/finishSprites.png");
            //System.out.println((new StringBuilder()).append("HUDSprite loading ").append(url).toString());
            Image image2 = toolkit.getImage(url);
            image2 = ColorFilters.setTransparentColor(image2, new Color(0xff00ff));
            char c = '0';
            byte byte0 = 48;
            Image image3 = ImageBlitter.cropTiled(image1, 0, 0, c, byte0);
            image3 = ColorFilters.setTransparentColor(image3, new Color(0xff00ff));
            imageloader.addImage(image3);
            imageTable.put("hg1", image3);
            image3 = ImageBlitter.cropTiled(image1, 1, 0, c, byte0);
            image3 = ColorFilters.setTransparentColor(image3, new Color(0xff00ff));
            imageloader.addImage(image3);
            imageTable.put("hg2", image3);
            image3 = ImageBlitter.cropTiled(image1, 0, 1, c, byte0);
            image3 = ColorFilters.setTransparentColor(image3, new Color(0xff00ff));
            imageloader.addImage(image3);
            imageTable.put("hf1", image3);
            image3 = ImageBlitter.cropTiled(image1, 1, 1, c, byte0);
            image3 = ColorFilters.setTransparentColor(image3, new Color(0xff00ff));
            imageloader.addImage(image3);
            imageTable.put("hf2", image3);
            image3 = ImageBlitter.cropTiled(image1, 0, 2, c, byte0);
            image3 = ColorFilters.setTransparentColor(image3, new Color(0xff00ff));
            imageloader.addImage(image3);
            imageTable.put("ha1", image3);
            image3 = ImageBlitter.cropTiled(image1, 1, 2, c, byte0);
            image3 = ColorFilters.setTransparentColor(image3, new Color(0xff00ff));
            imageloader.addImage(image3);
            imageTable.put("ha2", image3);
            imageTable.put("panel", image);
            c = '\310';///////////////////////////////////////////////
            byte0 = 48;///////////////////////////////////////////////
            image3 = ImageBlitter.cropTiled(image2, 0, 0, c, byte0);
            image3 = ColorFilters.setTransparentColor(image3, new Color(0xff00ff));
            imageloader.addImage(image3);
            imageTable.put("win", image3);
            image3 = ImageBlitter.cropTiled(image2, 0, 1, c, byte0);
            image3 = ColorFilters.setTransparentColor(image3, new Color(0xff00ff));
            imageloader.addImage(image3);
            imageTable.put("lose", image3);
            image3 = ImageBlitter.cropTiled(image2, 0, 2, c, byte0);
            image3 = ColorFilters.setTransparentColor(image3, new Color(0xff00ff));
            imageloader.addImage(image3);
            imageTable.put("die", image3);
            //System.out.println("loaded HUDSprite");
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
        if(type == 'g' && !isSelected)
            curImage = (Image)imageTable.get("hg1");
        if(type == 'g' && isSelected)
            curImage = (Image)imageTable.get("hg2");
        if(type == 'f' && !isSelected)
            curImage = (Image)imageTable.get("hf1");
        if(type == 'f' && isSelected)
            curImage = (Image)imageTable.get("hf2");
        if(type == 'a' && !isSelected)
            curImage = (Image)imageTable.get("ha1");
        if(type == 'a' && isSelected)
            curImage = (Image)imageTable.get("ha2");
        if(type == 'p')
            curImage = (Image)imageTable.get("panel");
        if(type == 'v')
            curImage = (Image)imageTable.get("win");
        if(type == 'n')
            curImage = (Image)imageTable.get("lose");
        if(type == 'd')
            curImage = (Image)imageTable.get("die");
        fx = 0.0D;
        fy = 0.0D;
    }

    private static int numInstances = 0;
    private static Hashtable imageTable = new Hashtable();
    private char type;
    public boolean isSelected;

}
