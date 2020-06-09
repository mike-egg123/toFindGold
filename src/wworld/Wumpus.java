package wworld;

/**
 * 恶魔类，记录恶魔的位置和状态
 */
public class Wumpus
{

    public Wumpus(int i, int j)
    {
        x = i;
        y = j;
        isDead = false;
    }

    public int x;
    public int y;
    public boolean isDead;
}
