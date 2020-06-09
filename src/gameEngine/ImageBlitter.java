package gameEngine;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.*;

/**
 * 该类用于图像传送，为节约空间并实现动画效果，将一张图片素材分成多个大小相同，
 * 边框宽2像素的格子，在里面分别绘制动态效果的每一帧图像，每次刷新游戏界面时
 * 展示某一格子上的一幅图像。
 *
 * 该类构造了核心方法cropTiled，输入图像以及裁剪区域的左上角和右下角坐标，
 * 利用awt库中的createImage方法，返回需要显示的图像部分
 */
public class ImageBlitter
{
    private static final int MARGIN_PX = 2;//在同一幅图像中，两帧放在两个大小相同的格子里，格子边框宽为2px

    public ImageBlitter()
    {
    }

    /**
     * 在开发过程中用于测试的方法，最终在项目中没有用到，参数同下
     * @param image
     * @param i
     * @param j
     * @param k
     * @param l
     * @return
     */
    public static Image crop(Image image, int i, int j, int k, int l)
    {
        java.awt.image.ImageProducer imageproducer = image.getSource();
        FilteredImageSource filteredimagesource = new FilteredImageSource(imageproducer, new CropImageFilter(i, j, k, l));
        Image image1 = Toolkit.getDefaultToolkit().createImage(filteredimagesource);
        return image1;
    }

    /**
     * 该方法是对CropImageFilter的修改，由于每次显示的不同图像是以格子分开，
     * 因此两次显示的切换中以一个格子为单位而非一个像素
     * @param image 被裁剪的图片，即多合一的素材图
     * @param i 横坐标
     * @param j 纵坐标
     * @param k 宽度
     * @param l 高度
     * @return 裁剪后的图片，为单个的图片
     */
    public static Image cropTiled(Image image, int i, int j, int k, int l)
    {
        java.awt.image.ImageProducer imageproducer = image.getSource();
        int i1 = 1;
        FilteredImageSource filteredimagesource = new FilteredImageSource(imageproducer, new CropImageFilter(i1 + i1 * MARGIN_PX * i + i * k, i1 + i1 * MARGIN_PX * j + j * l, k, l));//将裁剪区域左上角调整到需要的位置
        Image image1 = Toolkit.getDefaultToolkit().createImage(filteredimagesource);
        return image1;
    }
}
