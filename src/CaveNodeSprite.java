import gameEngine.*;
import java.awt.*;
import java.io.PrintStream;
import java.net.URL;
import java.util.Hashtable;

/**
 * 实现游戏中洞穴内部的显示，如墙壁，陷阱
 */
public class CaveNodeSprite extends Sprite
{

    /**
     * 初始化洞穴内部元素
     * @param d 元素的横坐标
     * @param d1 元素的纵坐标
     * @param c 元素的类型，取值有'O' 'P' 'F' 'W'，分别对应着内部墙壁，陷阱，地板和四周墙壁
     */
    public CaveNodeSprite(double d, double d1, char c)
    {
        super(d, d1);
        numInstances++;
        type = c;
        hasBottomWall = false;
        hasTopWall = false;
        hasRightWall = false;
        hasLeftWall = false;
    }

    /**
     * 用来销毁洞内元素
     */
    public void destroy()
    {
        isDestroyed = true;
        numInstances--;
    }

    /**
     * 用来加载洞内元素的图片，有地板图片、背景图片、陷阱图片和墙壁图片
     * @param imageloader 图片容器，承载上述图片
     */
    public static void loadImages(ImageLoader imageloader)
    {
        try
        {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            URL url = null;
            url = CaveNodeSprite.class.getResource("graphics/CaveFloorSprite.png");
            System.out.println((new StringBuilder()).append("CaveNodeSprite loading ").append(url).toString());
            Image image = toolkit.getImage(url);
            url = CaveNodeSprite.class.getResource("graphics/BGColor.png");
            System.out.println((new StringBuilder()).append("CaveNodeSprite loading ").append(url).toString());
            Image image1 = toolkit.getImage(url);
            url = CaveNodeSprite.class.getResource("graphics/PitSprite.png");
            System.out.println((new StringBuilder()).append("CaveNodeSprite loading ").append(url).toString());
            Image image2 = toolkit.getImage(url);
            image2 = ColorFilters.setTransparentColor(image2, new Color(0xff00ff));
            url = CaveNodeSprite.class.getResource("graphics/WallSprites.png");
            System.out.println((new StringBuilder()).append("CaveNodeSprite loading ").append(url).toString());
            Image image3 = toolkit.getImage(url);
            image3 = ColorFilters.setTransparentColor(image3, new Color(0xff00ff));
            imageTable.put("wall", image1);
            imageTable.put("pit", image2);
            imageTable.put("floor", image);
            imageloader.addImage(image1);
            imageloader.addImage(image2);
            imageloader.addImage(image);
            for(int i = 0; i < 16; i++)
            {
                byte byte0 = 24;
                byte byte1 = 24;
                Image image4 = ImageBlitter.cropTiled(image3, i, 0, byte0, byte1);
                imageTable.put((new StringBuilder()).append("walls").append(i).toString(), image4);
                imageloader.addImage(image4);
            }

            //System.out.println("loaded CaveNodeSprite");
        }
        catch(Exception exception) { }
    }

    /**
     * 清除元素，以便开始新的一局
     */
    public static void clean()
    {
        numInstances = 0;
        imageTable.clear();
    }

    /**
     * 激活洞内元素
     * @param imageloader 图片容器，与上面的对应
     */
    protected void animate(ImageLoader imageloader)
    {
        super.animate(imageloader);
        fx = 0.0D;
        fy = 0.0D;
    }

    /**
     * 这个是根据约定好的字母与元素的对应形式，生成对应的地图，举例如下
     * ..W...P..W
     * .......PW.
     * ....W.P.P.
     * .W.....W..
     * ....W..W.W
     * .W..W.WW..
     * ........PP
     * ......W..P
     * .W..W.....
     * ....P....W
     * 5 10
     * 7 9
     * 7 10
     * @param graphics2d 绘图类，使用它的drawImage()方法可以在GUI上画出想要的图片
     */
    protected void draw(Graphics2D graphics2d)
    {
        if(type == 'O')
        {
            CaveNodeSprite _tmp = this;
            graphics2d.drawImage((Image)imageTable.get("wall"), null, null);
        }
        if(type == 'P')
        {
            CaveNodeSprite _tmp1 = this;
            graphics2d.drawImage((Image)imageTable.get("pit"), null, null);
        }
        if(type == 'F')
        {
            for(int i = 0; i < height; i += 32)
            {
                for(int k = 0; k < width; k += 32)
                {
                    int l;
                    if(k + 32 > width)
                        l = width % 32;
                    else
                        l = 32;
                    int i1;
                    if(i + 32 > height)
                        i1 = height % 32;
                    else
                        i1 = 32;
                    CaveNodeSprite _tmp2 = this;
                    graphics2d.drawImage((Image)imageTable.get("floor"), k, i, k + l, i + i1, 0, 0, l, i1, null);
                }

            }

        }
        if(type == 'W')
        {
            int j = 0;
            if(hasBottomWall)
                j++;
            if(hasTopWall)
                j += 2;
            if(hasRightWall)
                j += 4;
            if(hasLeftWall)
                j += 8;
            CaveNodeSprite _tmp3 = this;
            graphics2d.drawImage((Image)imageTable.get((new StringBuilder()).append("walls").append(j).toString()), null, null);
        }
    }

    private static int numInstances = 0;
    private static Hashtable imageTable = new Hashtable();
    private char type;
    public int width;
    public int height;
    public boolean hasBottomWall;
    public boolean hasTopWall;
    public boolean hasRightWall;
    public boolean hasLeftWall;

}
