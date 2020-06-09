package wworld;

/**
 * 天使类，记录天使的状态
 */
public class Supmuw
{

    /**
     * 初始化天使
     * @param i 横坐标
     * @param j 纵坐标
     * @param cavenode 将要绑定的洞穴元素
     */
    public Supmuw(int i, int j, CaveNode cavenode)
    {
        x = i;
        y = j;
        isDead = false;
        hasFood = true;
        supmuwNode = cavenode;
    }

    /**
     * 指示天使所处的状态
     * @param agent ai玩家
     * @return "BONUSGET"表示得到奖励，"DIE"表示此时的天使是坏的，经过将会死亡，"SUPMUWWAVES"表示已经没有奖励了
     */
    public String encounter(Agent agent)
    {
        if(isFriendly && hasFood)
        {
            hasFood = false;
            agent.hasFood = true;
            getClass();
            agent.score += 100;
            return "BONUSGET";
        }
        if(!isFriendly)
        {
            agent.die();
            return "DIE";
        } else
        {
            return "SUPMUWWAVES";
        }
    }

    /**
     * 更新天使的模式，好或者坏，在洞里或者不在洞里
     */
    public void updateMode()
    {
        if(supmuwNode.hasStench)
            isFriendly = false;
        else
            isFriendly = true;
        if(supmuwNode.hasPit)
            isInPit = true;
        else
            isInPit = false;
    }

    public int x;
    public int y;
    public boolean isDead;
    public boolean isInPit;
    public boolean isFriendly;
    public boolean hasFood;
    private CaveNode supmuwNode;
    private final int scoreForFood = 100;
}
