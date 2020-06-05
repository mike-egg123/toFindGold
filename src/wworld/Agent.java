package wworld;

import java.awt.Point;
import java.util.*;

public class Agent
{
    private  static final int MAX_PX = 10;

    public Agent()
    {
        score = 0;
        hasGold = false;
        hasFood = false;
        hasArrow = true;
        isDead = false;
        x = 1;
        y = 1;
        direction = 'E';
        wumpusFound = false;
        wumpusKilled = false;
        knownWumpusX = -1;
        knownWumpusY = -1;
        eastmostStench = -1;
        westmostStench = 12;
        northmostStench = -1;
        southmostStench = 12;
        supmuwFound = false;
        supmuwKilled = false;
        knownSupmuwX = -1;
        knownSupmuwY = -1;
        supmuwFriendlyProbability = 0.5D;
        eastmostMoo = -1;
        westmostMoo = 12;
        northmostMoo = -1;
        southmostMoo = 12;
        wantsToGoHome = false;
    }

    public String act(int i, WumplusEnvironment wumplusenvironment)//实现角色的动作
    {
        String s = "";
        if(i == 2)
        {
            s = move(wumplusenvironment);
            score--;
        } else
        if(i == 0)
        {
            turnLeft();
            score--;
        } else
        if(i == 1)
        {
            turnRight();
            score--;
        } else
        if(i == 3)
        {
            s = grab(wumplusenvironment);
            if(s.equals(""))
                s = shoot(wumplusenvironment);
            if(s.equals(""))
                climb();
            score--;
        } else
        if(i == 5)
            s = shoot(wumplusenvironment);
        else
        if(i == 4)
            climb();
        return s;
    }

    private void turnLeft()
    {
        switch(direction)
        {
        case 78: // 'N'
            direction = 'W';
            break;

        case 87: // 'W'
            direction = 'S';
            break;

        case 83: // 'S'
            direction = 'E';
            break;

        case 69: // 'E'
            direction = 'N';
            break;
        }
    }

    private void turnRight()
    {
        switch(direction)
        {
        case 78: // 'N'
            direction = 'E';
            break;

        case 87: // 'W'
            direction = 'N';
            break;

        case 83: // 'S'
            direction = 'W';
            break;

        case 69: // 'E'
            direction = 'S';
            break;
        }
    }

    public String move(WumplusEnvironment wumplusenvironment)
    {
        CaveNode cavenode = (CaveNode)wumplusenvironment.grid.get(new Point(x, y));
        CaveNode cavenode1 = wumplusenvironment.getNextNode(cavenode, direction);
        cavenode1.wasVisited = true;
        wumplusenvironment.unvisitedNodes.remove(cavenode1);
        if(cavenode1.hasObstacle)
        {
            switch(direction)
            {
            case 78: // 'N'
                cavenode.foundNorthWall = true;
                break;

            case 83: // 'S'
                cavenode.foundSouthWall = true;
                break;

            case 69: // 'E'
                cavenode.foundEastWall = true;
                break;

            case 87: // 'W'
                cavenode.foundWestWall = true;
                break;
            }
            cavenode1.isSafe = false;
            return "BUMP";
        }
        if(cavenode1.hasSupmuw && cavenode1.hasPit)
        {
            cavenode1.isSafe = false;
            return "BUMP";
        }
        x = cavenode1.x;
        y = cavenode1.y;
        if(cavenode1.hasWumpus || cavenode1.hasPit)
        {
            die();
            return "DIE";
        }
        if(cavenode1.hasSupmuw)
        {
            Supmuw supmuw = wumplusenvironment.supmuw;
            pinPointSupmuw(cavenode1, wumplusenvironment);
            String s = supmuw.encounter(this);
            if(s.equals("BONUSGET"))
                supmuwFriendlyProbability = 1.0D;
            return s;
        }
        cavenode1.pitProbability = 0.0D;
        cavenode1.wumpusProbability = 0.0D;
        if(cavenode1.hasStench)
        {
            if(cavenode1.x < westmostStench)
                westmostStench = cavenode1.x;
            if(cavenode1.x > eastmostStench)
                eastmostStench = cavenode1.x;
            if(cavenode1.y < southmostStench)
                southmostStench = cavenode1.y;
            if(cavenode1.y > northmostStench)
                northmostStench = cavenode1.y;
        }
        if(cavenode1.hasMoo)
        {
            if(cavenode1.x < westmostMoo)
                westmostMoo = cavenode1.x;
            if(cavenode1.x > eastmostMoo)
                eastmostMoo = cavenode1.x;
            if(cavenode1.y < southmostMoo)
                southmostMoo = cavenode1.y;
            if(cavenode1.y > northmostMoo)
                northmostMoo = cavenode1.y;
        }
        return "MOVED";
    }

    public String grab(WumplusEnvironment wumplusenvironment)
    {
        Gold gold = wumplusenvironment.gold;
        CaveNode cavenode = (CaveNode)wumplusenvironment.grid.get(new Point(gold.x, gold.y));
        if(x == gold.x && y == gold.y && !gold.taken)
        {
            gold.taken = true;
            cavenode.hasGold = false;
            hasGold = true;
            score += 1000;
            return "GOLDGET";
        } else
        {
            return "";
        }
    }

    public void climb()
    {
        if(x == 1 && y == 1)
            isVictorious = true;
    }

    public String shoot(WumplusEnvironment wumplusenvironment)
    {
        if(hasArrow)
        {
            hasArrow = false;
            for(CaveNode cavenode = (CaveNode)wumplusenvironment.grid.get(new Point(x, y)); !cavenode.hasObstacle; cavenode = wumplusenvironment.getNextNode(cavenode, direction))
            {
                if(cavenode.hasWumpus)
                {
                    wumplusenvironment.wumpus.isDead = true;
                    cavenode.hasWumpus = false;
                    return "SCREAM";
                }
                if(cavenode.hasSupmuw)
                {
                    wumplusenvironment.supmuw.isDead = true;
                    cavenode.hasSupmuw = false;
                    return "SCREAM";
                }
            }

            return "MISS";
        } else
        {
            return "";
        }
    }

    public void die()
    {
        score += -1000;
        isDead = true;
    }

    public int getScore()
    {
        return score;
    }

    public int getNextAction(WumplusEnvironment wumplusenvironment)
    {
        CaveNode cavenode = (CaveNode)wumplusenvironment.grid.get(new Point(x, y));
        checkAllForPits(wumplusenvironment);
        checkAllForWumpus(wumplusenvironment);
        checkAllForSupmuw(wumplusenvironment);
        if(hasArrow && projectArrowShot(wumplusenvironment))
            return 5;
        if(hasGold && hasFood)
            wantsToGoHome = true;
        if(hasGold && supmuwFriendlyProbability == 0.0D)
            wantsToGoHome = true;
        if(cavenode.hasGold)
            return 3;
        if(wantsToGoHome && x == 1 && y == 1)
            return 4;
        char c = 'I';
        if(!wantsToGoHome)
            if(!hasFood && supmuwFriendlyProbability == 1.0D && supmuwFound)
                c = shortestSafePathToPoint(wumplusenvironment, new Point(knownSupmuwX, knownSupmuwY));
            else
                c = shortestSafePathToUnvisited(wumplusenvironment);
        if(c == 'I')
        {
            wantsToGoHome = true;
            c = shortestSafePathToPoint(wumplusenvironment, new Point(1, 1));
        }
        if(c == direction)
            return 2;
        if(c == getLeftDirection(direction))
            return 0;
        return c != getRightDirection(direction) && c != getBackDirection(direction) ? -1 : 1;
    }

    private boolean projectArrowShot(WumplusEnvironment wumplusenvironment)
    {
        Hashtable hashtable = wumplusenvironment.grid;
        CaveNode cavenode = (CaveNode)hashtable.get(new Point(x, y));
        do
        {
            if(cavenode.x == knownWumpusX && cavenode.y == knownWumpusY)
            {
                wumpusKilled = true;
                return true;
            }
            if(cavenode.x == knownSupmuwX && cavenode.y == knownSupmuwY)
                if(supmuwFriendlyProbability == 0.0D)
                {
                    supmuwKilled = true;
                    return true;
                } else
                {
                    return false;
                }
            if(cavenode.hasObstacle && cavenode.wasVisited)
                return false;
            if(!cavenode.wasVisited)
                return false;
            cavenode = wumplusenvironment.getNextNode(cavenode, direction);
        } while(true);
    }

    private void checkAllForPits(WumplusEnvironment wumplusenvironment)
    {
        Hashtable hashtable = wumplusenvironment.grid;
        for(int i = 1; i <= MAX_PX; i++)
        {
            for(int j = 1; j <= MAX_PX; j++)
            {
                Point point = new Point(j, i);
                CaveNode cavenode = (CaveNode)hashtable.get(point);
                if(cavenode.wasVisited)
                    checkAreaForPits(wumplusenvironment, cavenode);
            }

        }

    }

    private void checkAreaForPits(WumplusEnvironment wumplusenvironment, CaveNode cavenode)
    {
        Vector vector = wumplusenvironment.get4AdjacentNodes(cavenode);
        if(!cavenode.hasBreeze)
        {
            for(Iterator iterator = vector.iterator(); iterator.hasNext();)
            {
                CaveNode cavenode1 = (CaveNode)iterator.next();
                cavenode1.pitProbability = 0.0D;
            }

        } else
        {
            Iterator iterator1 = vector.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                CaveNode cavenode2 = (CaveNode)iterator1.next();
                if(!cavenode2.wasVisited && cavenode2.pitProbability > 0.0D && cavenode2.pitProbability < 1.0D)
                    cavenode2.pitProbability = 0.5D;
            } while(true);
            for(int i = 0; i < 4; i++)
            {
                CaveNode cavenode3 = (CaveNode)vector.get(i);
                CaveNode cavenode4 = (CaveNode)vector.get((i + 1) % 4);
                CaveNode cavenode5 = (CaveNode)vector.get((i + 2) % 4);
                CaveNode cavenode6 = (CaveNode)vector.get((i + 3) % 4);
                boolean flag = (cavenode6.pitProbability == 0.0D);
                boolean flag1 = (cavenode5.pitProbability == 0.0D);
                boolean flag2 = (cavenode4.pitProbability == 0.0D);
                if(flag && flag2 && flag1)
                    cavenode3.pitProbability = 1.0D;
            }

        }
    }

    private void checkAllForWumpus(WumplusEnvironment wumplusenvironment)
    {
        Hashtable hashtable = wumplusenvironment.grid;
        for(int i = 1; i <= MAX_PX; i++)
        {
            for(int j = 1; j <= MAX_PX; j++)
            {
                Point point = new Point(j, i);
                CaveNode cavenode = (CaveNode)hashtable.get(point);
                if(cavenode.wasVisited && checkAreaForWumpus(wumplusenvironment, cavenode))
                    return;
            }

        }

    }

    private boolean checkAreaForWumpus(WumplusEnvironment wumplusenvironment, CaveNode cavenode)
    {
        Vector vector = wumplusenvironment.get4AdjacentNodes(cavenode);
        if(!cavenode.hasStench)
        {
            for(Iterator iterator = vector.iterator(); iterator.hasNext();)
            {
                CaveNode cavenode1 = (CaveNode)iterator.next();
                cavenode1.wumpusProbability = 0.0D;
            }

        } else
        {
            Vector vector1 = wumplusenvironment.get8AdjacentNodes(cavenode);
            Iterator iterator1 = vector1.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                CaveNode cavenode2 = (CaveNode)iterator1.next();
                if(!cavenode2.wasVisited && cavenode2.wumpusProbability > 0.0D && cavenode2.wumpusProbability < 1.0D)
                    cavenode2.wumpusProbability = 0.5D;
            } while(true);
            for(int i = 0; i < 8; i += 2)
            {
                CaveNode cavenode3 = (CaveNode)vector1.get(i);
                CaveNode cavenode4 = (CaveNode)vector1.get((i + 1) % 8);
                CaveNode cavenode5 = (CaveNode)vector1.get((i + 2) % 8);
                CaveNode cavenode6 = (CaveNode)vector1.get((i + 3) % 8);
                CaveNode cavenode7 = (CaveNode)vector1.get((i + 4) % 8);
                CaveNode cavenode8 = (CaveNode)vector1.get((i + 5) % 8);
                CaveNode cavenode9 = (CaveNode)vector1.get((i + 6) % 8);
                CaveNode cavenode10 = (CaveNode)vector1.get((i + 7) % 8);
                boolean flag = cavenode9.wumpusProbability == 0.0D;
                boolean flag1 = cavenode7.wumpusProbability == 0.0D;
                boolean flag2 = cavenode5.wumpusProbability == 0.0D;
                if(cavenode9.wasVisited && !cavenode9.hasStench && cavenode5.wasVisited && !cavenode5.hasStench && cavenode7.wasVisited && !cavenode7.hasStench)
                {
                    noPointWumpus(cavenode3, wumplusenvironment);
                    return true;
                }
                if(flag2 && cavenode4.wasVisited && cavenode4.hasStench)
                {
                    noPointWumpus(cavenode3, wumplusenvironment);
                    return true;
                }
                if(flag && cavenode10.wasVisited && cavenode10.hasStench)
                {
                    noPointWumpus(cavenode3, wumplusenvironment);
                    return true;
                }
            }

        }
        return false;
    }

    private void noPointWumpus(CaveNode cavenode, WumplusEnvironment wumplusenvironment)
    {
        Hashtable hashtable = wumplusenvironment.grid;
        for(int i = 0; i <= 11; i++)
        {
            for(int j = 0; j <= 11; j++)
            {
                Point point = new Point(j, i);
                CaveNode cavenode1 = (CaveNode)hashtable.get(point);
                cavenode1.wumpusProbability = 0.0D;
            }

        }

        if(!hasArrow)
            cavenode.wumpusProbability = 1.0D;
        wumpusFound = true;
        knownWumpusX = cavenode.x;
        knownWumpusY = cavenode.y;
    }

    private void checkAllForSupmuw(WumplusEnvironment wumplusenvironment)
    {
        Hashtable hashtable = wumplusenvironment.grid;
        for(int i = 1; i <= 10; i++)
        {
            for(int j = 1; j <= 10; j++)
            {
                Point point = new Point(j, i);
                CaveNode cavenode2 = (CaveNode)hashtable.get(point);
                if(cavenode2.wasVisited)
                    if(!checkAreaForSupmuw(wumplusenvironment, cavenode2));
            }

        }

        if(supmuwFriendlyProbability > 0.0D && supmuwFriendlyProbability < 1.0D && eastmostMoo != -1)
            if(knownSupmuwX != -1 && Math.abs(knownSupmuwX - knownWumpusX) == 1 && Math.abs(knownSupmuwY - knownWumpusY) == 0)
            {
                supmuwFriendlyProbability = 0.0D;
                CaveNode cavenode = (CaveNode)hashtable.get(new Point(knownSupmuwX, knownSupmuwY));
                if(hasArrow)
                    cavenode.supmuwProbability = 0.0D;
            } else
            if(knownSupmuwX != -1 && Math.abs(knownSupmuwX - knownWumpusX) == 0 && Math.abs(knownSupmuwY - knownWumpusY) == 1)
            {
                supmuwFriendlyProbability = 0.0D;
                CaveNode cavenode1 = (CaveNode)hashtable.get(new Point(knownSupmuwX, knownSupmuwY));
                if(hasArrow)
                    cavenode1.supmuwProbability = 0.0D;
            } else
            if(knownSupmuwX != -1 && knownWumpusX != -1 && knownSupmuwX < eastmostStench - 1)
                supmuwFriendlyProbability = 1.0D;
            else
            if(knownSupmuwX != -1 && knownWumpusX != -1 && knownSupmuwX > westmostStench + 1)
                supmuwFriendlyProbability = 1.0D;
            else
            if(knownSupmuwX != -1 && knownWumpusX != -1 && knownSupmuwY < southmostStench - 1)
                supmuwFriendlyProbability = 1.0D;
            else
            if(knownSupmuwX != -1 && knownWumpusX != -1 && knownSupmuwY > northmostStench + 1)
                supmuwFriendlyProbability = 1.0D;
    }

    private boolean checkAreaForSupmuw(WumplusEnvironment wumplusenvironment, CaveNode cavenode)
    {
        Vector vector = wumplusenvironment.get8AdjacentNodes(cavenode);
        if(!cavenode.hasMoo)
        {
            for(Iterator iterator = vector.iterator(); iterator.hasNext();)
            {
                CaveNode cavenode1 = (CaveNode)iterator.next();
                cavenode1.supmuwProbability = 0.0D;
            }

        } else
        {
            Iterator iterator1 = vector.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                CaveNode cavenode2 = (CaveNode)iterator1.next();
                if(!cavenode2.wasVisited && cavenode2.supmuwProbability > 0.0D && cavenode2.supmuwProbability < 1.0D)
                    cavenode2.supmuwProbability = 0.5D;
            } while(true);
            for(int i = 0; i < 8; i += 2)
            {
                CaveNode cavenode3 = (CaveNode)vector.get(i);
                CaveNode cavenode4 = (CaveNode)vector.get((i + 1) % 8);
                CaveNode cavenode5 = (CaveNode)vector.get((i + 2) % 8);
                CaveNode cavenode6 = (CaveNode)vector.get((i + 3) % 8);
                CaveNode cavenode7 = (CaveNode)vector.get((i + 4) % 8);
                CaveNode cavenode8 = (CaveNode)vector.get((i + 5) % 8);
                CaveNode cavenode9 = (CaveNode)vector.get((i + 6) % 8);
                CaveNode cavenode10 = (CaveNode)vector.get((i + 7) % 8);
                boolean flag = cavenode9.supmuwProbability == 0.0D;
                boolean flag1 = cavenode7.supmuwProbability == 0.0D;
                boolean flag2 = cavenode5.supmuwProbability == 0.0D;
                if(cavenode9.wasVisited && cavenode9.hasMoo && cavenode5.wasVisited && cavenode5.hasMoo && flag1)
                {
                    pinPointSupmuw(cavenode3, wumplusenvironment);
                    return true;
                }
                if(cavenode4.wasVisited && cavenode4.hasMoo && cavenode10.wasVisited && cavenode10.hasMoo && cavenode3.wasVisited && cavenode3.hasMoo)
                {
                    pinPointSupmuw(cavenode3, wumplusenvironment);
                    return true;
                }
                if(cavenode10.wasVisited && cavenode10.hasMoo && cavenode4.wasVisited && cavenode4.hasMoo && cavenode9.wasVisited && cavenode9.hasMoo && cavenode5.wasVisited && cavenode5.hasMoo)
                {
                    pinPointSupmuw(cavenode3, wumplusenvironment);
                    System.out.println("case 3");
                    return true;
                }
                if(cavenode9.wasVisited && !cavenode9.hasMoo && cavenode7.wasVisited && !cavenode7.hasMoo)
                {
                    pinPointSupmuw(cavenode4, wumplusenvironment);
                    return true;
                }
                if(cavenode5.wasVisited && !cavenode5.hasMoo && cavenode7.wasVisited && !cavenode7.hasMoo)
                {
                    pinPointSupmuw(cavenode10, wumplusenvironment);
                    return true;
                }
            }

        }
        return false;
    }

    private void pinPointSupmuw(CaveNode cavenode, WumplusEnvironment wumplusenvironment)
    {
        Hashtable hashtable = wumplusenvironment.grid;
        for(int i = 0; i <= 11; i++)
        {
            for(int j = 0; j <= 11; j++)
            {
                Point point = new Point(j, i);
                CaveNode cavenode1 = (CaveNode)hashtable.get(point);
                cavenode1.supmuwProbability = 0.0D;
            }

        }

        cavenode.supmuwProbability = 1.0D;
        supmuwFound = true;
        knownSupmuwX = cavenode.x;
        knownSupmuwY = cavenode.y;
    }

    private char shortestSafePathToUnvisited(WumplusEnvironment wumplusenvironment)
    {
        Hashtable hashtable = new Hashtable();
        for(int i = 0; i <= 11; i++)
        {
            for(int j = 0; j <= 11; j++)
                hashtable.put(new Point(j, i), Boolean.valueOf(false));

        }

        PriorityQueue priorityqueue = new PriorityQueue();
        Hashtable hashtable1 = wumplusenvironment.grid;
        PriorityCaveNode prioritycavenode = new PriorityCaveNode((CaveNode)hashtable1.get(new Point(x, y)), 0, new Vector(), direction);
        priorityqueue.add(prioritycavenode);
        Point point;
        for(; !priorityqueue.isEmpty(); hashtable.put(point, Boolean.valueOf(true)))
        {
            PriorityCaveNode prioritycavenode1 = (PriorityCaveNode)priorityqueue.remove();
            CaveNode cavenode = prioritycavenode1.node;
            char c = ((Character)prioritycavenode1.directions.lastElement()).charValue();
            int k = prioritycavenode1.cost;
            point = new Point(cavenode.x, cavenode.y);
            if(!cavenode.wasVisited)
                try
                {
                    return ((Character)prioritycavenode1.directions.get(1)).charValue();
                }
                catch(Exception exception)
                {
                    return 'I';
                }
            if(!((Boolean)hashtable.get(point)).booleanValue() && !cavenode.hasObstacle)
            {
                char c1 = getLeftDirection(c);
                char c2 = getRightDirection(c);
                char c3 = getBackDirection(c);
                aStarTravel(priorityqueue, wumplusenvironment, prioritycavenode1, c, k, null);
                aStarTravel(priorityqueue, wumplusenvironment, prioritycavenode1, c1, k + 1, null);
                aStarTravel(priorityqueue, wumplusenvironment, prioritycavenode1, c2, k + 1, null);
                aStarTravel(priorityqueue, wumplusenvironment, prioritycavenode1, c3, k + 2, null);
            }
        }

        return 'I';
    }

    private void aStarTravel(PriorityQueue priorityqueue, WumplusEnvironment wumplusenvironment, PriorityCaveNode prioritycavenode, char c, int i, Point point)
    {
        CaveNode cavenode = prioritycavenode.node;
        CaveNode cavenode1 = wumplusenvironment.getNextNode(cavenode, c);
        double d = cavenode1.supmuwProbability * supmuwFriendlyProbability;
        double d1 = cavenode1.supmuwProbability * (1.0D - supmuwFriendlyProbability);
        if(supmuwKilled)
            d1 = 0.0D;
        double d2 = cavenode1.wumpusProbability;
        if(wumpusKilled)
            d2 = 0.0D;
        double d3 = 1.0D - (1.0D - cavenode1.pitProbability) * (1.0D - d2) * (1.0D - d1);
        if(d3 == 1.0D)
            return;
        double d4 = 1.0D / (double)wumplusenvironment.unvisitedNodes.size();
        if(hasGold)
            d4 = 0.0D;
        if(hasFood)
            d = 0.0D;
        int j = (int)(1.0D + d3 * 1000D + d4 * -1000D + d * -100D);
        if(cavenode1.wasVisited && point == null)
            j += 100;
        if(point != null)
        {
            j += Math.abs(cavenode1.x - point.x) + Math.abs(cavenode1.y - point.y);
            if(!cavenode1.wasVisited)
                j += 100;
        }
        if(j < 501)
        {
            PriorityCaveNode prioritycavenode1 = new PriorityCaveNode(cavenode1, i + j, prioritycavenode.directions, c);
            priorityqueue.add(prioritycavenode1);
        }
    }

    private char getLeftDirection(char c)
    {
        if(c == 'N')
            return 'W';
        if(c == 'W')
            return 'S';
        if(c == 'S')
            return 'E';
        return c != 'E' ? 'I' : 'N';
    }

    private char getRightDirection(char c)
    {
        if(c == 'N')
            return 'E';
        if(c == 'W')
            return 'N';
        if(c == 'S')
            return 'W';
        return c != 'E' ? 'I' : 'S';
    }

    private char getBackDirection(char c)
    {
        if(c == 'N')
            return 'S';
        if(c == 'W')
            return 'E';
        if(c == 'S')
            return 'N';
        return c != 'E' ? 'I' : 'W';
    }

    private char shortestSafePathToPoint(WumplusEnvironment wumplusenvironment, Point point)
    {
        Hashtable hashtable = new Hashtable();
        for(int i = 0; i <= 11; i++)
        {
            for(int j = 0; j <= 11; j++)
                hashtable.put(new Point(j, i), Boolean.valueOf(false));

        }

        PriorityQueue priorityqueue = new PriorityQueue();
        Hashtable hashtable1 = wumplusenvironment.grid;
        PriorityCaveNode prioritycavenode = new PriorityCaveNode((CaveNode)hashtable1.get(new Point(x, y)), 0, new Vector(), direction);
        priorityqueue.add(prioritycavenode);
        Point point1;
        for(; !priorityqueue.isEmpty(); hashtable.put(point1, Boolean.valueOf(true)))
        {
            PriorityCaveNode prioritycavenode1 = (PriorityCaveNode)priorityqueue.remove();
            CaveNode cavenode = prioritycavenode1.node;
            char c = ((Character)prioritycavenode1.directions.lastElement()).charValue();
            int k = prioritycavenode1.cost;
            point1 = new Point(cavenode.x, cavenode.y);
            if(cavenode.x == point.x && cavenode.y == point.y)
                try
                {
                    return ((Character)prioritycavenode1.directions.get(1)).charValue();
                }
                catch(Exception exception)
                {
                    return 'I';
                }
            if(!((Boolean)hashtable.get(point1)).booleanValue() && !cavenode.hasObstacle)
            {
                char c1 = getLeftDirection(c);
                char c2 = getRightDirection(c);
                char c3 = getBackDirection(c);
                aStarTravel(priorityqueue, wumplusenvironment, prioritycavenode1, c, k, point);
                aStarTravel(priorityqueue, wumplusenvironment, prioritycavenode1, c1, k + 1, point);
                aStarTravel(priorityqueue, wumplusenvironment, prioritycavenode1, c2, k + 1, point);
                aStarTravel(priorityqueue, wumplusenvironment, prioritycavenode1, c3, k + 2, point);
            }
        }

        return 'I';
    }

    public int score;
    private final int scoreForAction = -1;
    private final int scoreForGold = 1000;
    private final int scoreForDying = -1000;
    public boolean isDead;
    public boolean isVictorious;
    public boolean hasGold;
    public boolean hasFood;
    public boolean hasArrow;
    private boolean wantsToGoHome;
    public int x;
    public int y;
    public char direction;
    private boolean wumpusFound;
    private boolean wumpusKilled;
    private int knownWumpusX;
    private int knownWumpusY;
    private int eastmostStench;
    private int westmostStench;
    private int northmostStench;
    private int southmostStench;
    private boolean supmuwFound;
    private double supmuwFriendlyProbability;
    private boolean supmuwKilled;
    private int knownSupmuwX;
    private int knownSupmuwY;
    private int eastmostMoo;
    private int westmostMoo;
    private int northmostMoo;
    private int southmostMoo;
}
