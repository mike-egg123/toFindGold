package gameEngine;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.*;

public class ImageBlitter
{
    private static final int MARGIN_PX = 2;//在同一幅图像中，两帧放在两个大小相同的格子里，格子边框宽为2px

    public ImageBlitter()
    {
    }

    public static Image crop(Image image, int i, int j, int k, int l)//在开发过程中用于测试的方法，最终在项目中没有用到
    {
        java.awt.image.ImageProducer imageproducer = image.getSource();
        FilteredImageSource filteredimagesource = new FilteredImageSource(imageproducer, new CropImageFilter(i, j, k, l));
        Image image1 = Toolkit.getDefaultToolkit().createImage(filteredimagesource);
        return image1;
    }

    public static Image cropTiled(Image image, int i, int j, int k, int l)//该方法是对CropImageFilter的修改，由于每次显示的不同图像是以格子分开，因此两次显示的切换中以一个格子为单位而非一个像素
    {
        java.awt.image.ImageProducer imageproducer = image.getSource();
        int i1 = 1;
        FilteredImageSource filteredimagesource = new FilteredImageSource(imageproducer, new CropImageFilter(i1 + i1 * MARGIN_PX * i + i * k, i1 + i1 * MARGIN_PX * j + j * l, k, l));//将裁剪区域左上角调整到需要的位置
        Image image1 = Toolkit.getDefaultToolkit().createImage(filteredimagesource);
        return image1;
    }
}
