import gameEngine.*;
import java.awt.*;
import java.util.Hashtable;

public class MenuSprite extends Sprite
{

    public MenuSprite(double d, double d1, char c)
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

    public static void clean()
    {
        numInstances = 0;
        imageTable.clear();
    }

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
