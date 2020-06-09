package wworld;

/**
 * 金币类，记录金币的位置和状态
 */
public class Gold
{

    public Gold(int i, int j)
    {
        x = i;
        y = j;
        taken = false;
    }

    public int x;
    public int y;
    public boolean taken;
}
