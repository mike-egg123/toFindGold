package wworld;

public class Action//这个类用于控制角色的行为，但后来对每种行为分别实现，该类没有用到
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
