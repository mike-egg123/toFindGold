import gameEngine.*;
import java.awt.*;
import java.util.Hashtable;

public class HowToPlaySprite extends Sprite
{

    public HowToPlaySprite(double d, double d1, char c)
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

    public static void clean()
    {
        numInstances = 0;
        imageTable.clear();
    }

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
