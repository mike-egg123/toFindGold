package wworld;

/**
 * 该类用于约定人物行为的代表数字，在调试过程中发挥了作用，最终项目没有用到
 */
public class Action
{

    public Action()
    {
    }

    public static final int IDLE = -1;
    public static final int TURN_LEFT = 0;
    public static final int TURN_RIGHT = 1;
    public static final int GOFORWARD = 2;
    public static final int GRAB = 3;
    public static final int CLIMB = 4;
    public static final int SHOOT = 5;
}
