package gameEngine;

import java.awt.*;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;

/**
 * 由于游戏素材中的大量图片为正方形，而游戏界面中只需显示对象本身，在这个类中主要构造了
 * setTransparentColor 和 setSemiTransparency 方法。首先约定所有图片素材的背景均为
 * RGB下的0xff00ff，然后在第一个方法中将其设为透明色，在后一个方法中将其设置为透明。
 * 对每一张图片分别调用这种方法，可以实现图片只显示中央的人物而不显示背景颜色，使用该
 * 种方法的原因是我们团队的ps技术都十分辣鸡。
 */
public class ColorFilters
{

    /**
     * 写了等于没写的构造函数，这明显在水代码
     */
    public ColorFilters()
    {
    }

    /**
     * 将背景色为0xff00ff转成透明色
     * @param image 将要被转变的图片
     * @param color 颜色常量：0xff00ff
     * @return 背景色被设置成透明的图片
     */
    public static Image setTransparentColor(Image image,final Color color)
    {
        RGBImageFilter rgbimagefilter = new RGBImageFilter() {

            public int filterRGB(int i, int j, int k)
            {
                if((k | 0xff000000) == testColor)//alpha通道为黑色ff时表示透明，k与0xff00ff 按位或 得到全透明的k
                    return k & 0xffffff;
                else
                    return k;
            }

            public int testColor;
            {
 
                testColor = color.getRGB() | 0xff000000;
            }
        };
        FilteredImageSource filteredimagesource = new FilteredImageSource(image.getSource(), rgbimagefilter);
        Image image1 = Toolkit.getDefaultToolkit().createImage(filteredimagesource);
        return image1;
    }

    /**
     * 将透明色设置成透明（即不可见）
     * @param image 将要被转变的图片
     * @param d 透明度
     * @return 背景变成透明的图片
     */
    public static Image setSemiTransparency(Image image, double d)
    {
        if(d > 1.0D)
            d = 1.0D;
        if(d < 0.0D)
            d = 0.0D;
       final int alpha = (int)(255D * (1.0D - d));//将表示透明度alpha的比例转化为两位十六进制数
        
        RGBImageFilter rgbimagefilter = new RGBImageFilter() {

            public int filterRGB(int j, int k, int l)
            {
                if((l & 0xff000000) != 0)
                    return l & 0xffffff | alpha << 24;//将表示透明度的数字移动到前两位，后六位存放RGB颜色
                else
                    return l;
            }

        }
;
        FilteredImageSource filteredimagesource = new FilteredImageSource(image.getSource(), rgbimagefilter);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        toolkit.sync();
        Image image1 = toolkit.createImage(filteredimagesource);
        return image1;
    }
}
