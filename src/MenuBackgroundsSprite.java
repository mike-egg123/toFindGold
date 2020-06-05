import gameEngine.ImageLoader;
import gameEngine.Sprite;
import java.awt.*;
import java.io.PrintStream;
import java.util.Hashtable;

public class MenuBackgroundsSprite extends Sprite
{

    public MenuBackgroundsSprite(double d, double d1)
    {
        super(d, d1);
        numInstances++;
    }

    public void destroy()
    {
        isDestroyed = true;
        numInstances--;
    }

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

    public static void clean()
    {
        numInstances = 0;
        focusTable.clear();
        imageTable.clear();
    }

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
