package wworld;

import java.util.Vector;

public class PriorityCaveNode
    implements Comparable
{

    public PriorityCaveNode(CaveNode cavenode, int i, Vector vector, char c)
    {
        node = cavenode;
        cost = i;
        directions = new Vector(vector);
        directions.add(Character.valueOf(c));
    }

    public int compareTo(Object obj)
    {
        PriorityCaveNode prioritycavenode = (PriorityCaveNode)obj;
        if(cost < prioritycavenode.cost)
            return -1;
        return cost != prioritycavenode.cost ? 1 : 0;
    }

    public int cost;
    public CaveNode node;
    public Vector directions;
}
