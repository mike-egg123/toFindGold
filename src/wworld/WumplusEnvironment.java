package wworld;

import java.awt.Point;
import java.util.*;
import java.io.*;

public class WumplusEnvironment
{

    public WumplusEnvironment(String s)
    {
        grid = new Hashtable();
        unvisitedNodes = new Vector();
        if(s == "easy")
            initRandomEasyEnvironment();
        else if(s == "hard"){
            //String str = "maps/map1";
            initRandomHardEnvironment();
        }

        createCaveBorder();
        initAllPercepts();
        supmuw.updateMode();
        agent = new Agent();
    }

    private void initAllPercepts()
    {
        for(int i = 0; i <= 11; i++)
        {
            for(int j = 0; j <= 11; j++)
            {
                CaveNode cavenode = (CaveNode)grid.get(new Point(j, i));
                initPercepts(cavenode);
            }

        }

    }

    private void initPercepts(CaveNode cavenode)
    {
        if(cavenode.hasPit)
        {
            Vector vector = get4AdjacentNodes(cavenode);
            for(Iterator iterator = vector.iterator(); iterator.hasNext();)
            {
                CaveNode cavenode1 = (CaveNode)iterator.next();
                cavenode1.hasBreeze = true;
            }

        }
        if(cavenode.x == wumpus.x && cavenode.y == wumpus.y)
        {
            cavenode.hasStench = true;
            Vector vector1 = get4AdjacentNodes(cavenode);
            for(Iterator iterator1 = vector1.iterator(); iterator1.hasNext();)
            {
                CaveNode cavenode2 = (CaveNode)iterator1.next();
                cavenode2.hasStench = true;
            }

        }
        if(cavenode.x == supmuw.x && cavenode.y == supmuw.y)
        {
            cavenode.hasMoo = true;
            Vector vector2 = get8AdjacentNodes(cavenode);
            for(Iterator iterator2 = vector2.iterator(); iterator2.hasNext();)
            {
                CaveNode cavenode3 = (CaveNode)iterator2.next();
                cavenode3.hasMoo = true;
            }

        }
    }

    private int[][] noway = new int[12][12];//如果是陷阱，怪兽wumpus或者墙，这里不可能属于有效路径，标记为1
    //private int[] pathx = new int[100];
    //private int[] pathy = new int[100];
    private int[] visited = new int[101];   //为便于访问，采取一维数组
    private int pathlength = 0;
    private int cnt = 0;
    private void checkmap1()
    {

        for(int i = 1; i <= 10; i++)
        {
            for(int j = 1; j <= 10; j++)
            {
                Point testPoint = new Point(i, j);
                CaveNode test = new CaveNode(testPoint.x, testPoint.y);
                grid.put(testPoint, test);
                if(test.hasPit || test.hasWumpus || test.hasObstacle)
                {
                    noway[i][j] = 1;cnt++;
                }

            }
        }
        for(int i = 0; i <= 11; i++)noway[i][0] = noway[i][11] = 1;
        for(int i = 0; i <= 11; i++)noway[0][i] = noway[11][i] = 1;
        for(int i = 0; i < 100; i++)visited[i] = 0;
        System.out.println("initialize map success!" + cnt);
    }
    private void checkmap2(int x, int y)
    {
        CaveNode checknode = (CaveNode)grid.get(new Point(x, y));

        if(noway[x][y] == 0 && visited[x + (y - 1) * 10] == 0)
        {
            visited[x  + (y - 1) * 10] = 1;
            System.out.println("now point: " + checknode.x + "," + checknode.y);
            if(x == gold.x && y == gold.y)
            {
                havevalidpath = 1;
                return;
            }
            else
            {
                checkmap2(x + 1, y);
                checkmap2(x, y + 1);
                checkmap2(x, y - 1);
                checkmap2(x - 1, y);
            }
            visited[x  + (y - 1) * 10] = 0;
            return;
        }
        else
            return;

    }

    private void initRandomEasyEnvironment()
    {

        Random random = new Random();
//        do {
//            havevalidpath = 0;
//            for(int i = 1; i <= 10; i++)
//            {
//                for(int j = 1; j <= 10; j++)
//                {
//                    Point point = new Point(j, i);
//                    CaveNode cavenode = new CaveNode(point.x, point.y);
//                    grid.put(point, cavenode);
//                    if(i == 1 && j == 1)            //出发点（也是出口）
//                    {
//                        cavenode.isSafe = true;
//                        cavenode.wasVisited = true;
//                        cavenode.pitProbability = 0.0D;
//                        cavenode.wumpusProbability = 0.0D;
//                        cavenode.supmuwProbability = 0.0D;
//                    } else
//                    {
//                        unvisitedNodes.add(cavenode);
//                    }
//                    int j1 = random.nextInt(100);
//                    if(!cavenode.hasGold && !cavenode.hasEntrance)
//                    {
//                        getClass();
//                        if(j1 <= 7)
//                            initPitNode(cavenode);
//                    }
//                    int j2 = random.nextInt(100);
//                    if(cavenode.hasPit || cavenode.hasGold || cavenode.hasEntrance)
//                        continue;
//                    getClass();
//                    if(j2 <= 15)
//                        initObstacleNode(cavenode);
//                }
//
//            }
//
//            boolean flag = false; //whether create a wumpus point
//            boolean flag1 = false;//whether create a supmuw point
//            boolean flag2 = false;//whether create a gold point
//            do
//            {
//                if(flag2)
//                    break;
//                int k = random.nextInt(10) + 1;
//                int k1 = random.nextInt(10) + 1;
//                CaveNode cavenode3 = (CaveNode)grid.get(new Point(k,k1));
//                if(!cavenode3.hasObstacle && !cavenode3.hasPit)
//                {
//                    initGoldNode(cavenode3);
//                    flag2 = true;
//                }
//            } while(true);
//            do
//            {
//                if(flag)
//                    break;
//                int l = random.nextInt(10) + 1;
//                int l1 = random.nextInt(10) + 1;
//                CaveNode cavenode1 = (CaveNode)grid.get(new Point(l, l1));
//                if(!cavenode1.hasEntrance && !cavenode1.hasObstacle)
//                {
//                    initWumpusNode(cavenode1);
//                    flag = true;
//                }
//            } while(true);
//            do
//            {
//                if(flag1)
//                    break;
//                int i1 = random.nextInt(10) + 1;
//                int i2 = random.nextInt(10) + 1;
//                CaveNode cavenode2 = (CaveNode)grid.get(new Point(i1, i2));
//                if(!cavenode2.hasObstacle && !cavenode2.hasEntrance)
//                {
//                    initSupmuwNode(cavenode2);
//                    flag1 = true;
//                }
//            } while(true);
//            //checkmap1();
//            //checkmap2(1, 1);
//        } while(havevalidpath == 1);///////////////////////
        //System.out.println("gold.x = " + gold.x + "gold.y = " + gold.y);//正常
        //以下是修改后的随机加载地图代码，通过直接调用指定加载地图代码，生成随机下标，来实现随机加载地图。
//        String[] strArr = new String[4];
//        for(int i = 0;i < 4;i++){
//            strArr[i] = "maps/map" + (i + 1);
//        }
//        String mapStr = strArr[random.nextInt(4)];
//        String mapFileName = "map";
//        String pathName = "./maps";
//        String mapStr = pathName + "/" + mapFileName;
        GenerateMap gEasy = new GenerateMap(80, 10, 0.1, false);
        char[][] arrs = new char[10][10];
        arrs = gEasy.generateMap();
        //System.out.println(mapStr);
        initLoadedEnvironment(arrs,gEasy.Cx,gEasy.Cy,gEasy.Bx,gEasy.By,gEasy.Gx,gEasy.Gy);
    }
    private void initRandomHardEnvironment(){
//        Random random = new Random();
//        String[] strArr = new String[4];
//        for(int i = 0;i < 4;i++){
//            strArr[i] = "maps/map" + (i + 5);
//        }
//        String mapStr = strArr[random.nextInt(4)];
//        String mapFileName = "map";
//        String pathName = "./maps";
//        String mapStr = pathName + "/" + mapFileName;
        GenerateMap gHard = new GenerateMap(80, 10, 0.15, false);
        char[][] arrs = new char[10][10];
        arrs = gHard.generateMap();
        //System.out.println(mapStr);
        initLoadedEnvironment(arrs,gHard.Cx,gHard.Cy,gHard.Bx,gHard.By,gHard.Gx,gHard.Gy);
    }

    private void initLoadedEnvironment(char[][] arrs, int cx,int cy,int bx,int by,int gx,int gy)
    {
        //Fileinput fileinput = new Fileinput(s);
        for(int i = 10; i >= 1; i--)
        {
            //String s1 = fileinput.getLine();
            for(int k = 1; k <= 10; k++)
            {
                Point point = new Point(k, i);
                CaveNode cavenode1 = new CaveNode(point.x, point.y);
                grid.put(point, cavenode1);
                if(i == 1 && k == 1)
                {
                    cavenode1.isSafe = true;
                    cavenode1.wasVisited = true;
                    cavenode1.pitProbability = 0.0D;
                    cavenode1.wumpusProbability = 0.0D;
                    cavenode1.supmuwProbability = 0.0D;
                } else
                {
                    unvisitedNodes.add(cavenode1);
                }
                char c = arrs[10 - i][k - 1];
                if(c == 'W')
                    initObstacleNode(cavenode1);
                if(c == 'P')
                    initPitNode(cavenode1);
            }

        }

        //int ai[] = fileinput.getParsedInts(" ");
        int j = cx;
        int l = cy;
        CaveNode cavenode = (CaveNode)grid.get(new Point(j, l));
        initGoldNode(cavenode);
        //int ai1[] = fileinput.getParsedInts(" ");
        j = bx;
        l = by;
        cavenode = (CaveNode)grid.get(new Point(j, l));
        initWumpusNode(cavenode);
        //int ai2[] = fileinput.getParsedInts(" ");
        j = gx;
        l = gy;
        cavenode = (CaveNode)grid.get(new Point(j, l));
        initSupmuwNode(cavenode);
    }

    public void createCaveBorder()
    {
        for(int i = 0; i <= 11; i++)
        {
            Point point = new Point(0, i);
            CaveNode cavenode = new CaveNode(point.x, point.y);
            cavenode.hasObstacle = true;
            grid.put(point, cavenode);
            initPercepts(cavenode);
            point = new Point(11, i);
            cavenode = new CaveNode(point.x, point.y);
            cavenode.hasObstacle = true;
            grid.put(point, cavenode);
            initPercepts(cavenode);
        }

        for(int j = 1; j <= 10; j++)
        {
            Point point1 = new Point(j, 0);
            CaveNode cavenode1 = new CaveNode(point1.x, point1.y);
            cavenode1.hasObstacle = true;
            grid.put(point1, cavenode1);
            initPercepts(cavenode1);
            point1 = new Point(j, 11);
            cavenode1 = new CaveNode(point1.x, point1.y);
            cavenode1.hasObstacle = true;
            grid.put(point1, cavenode1);
            initPercepts(cavenode1);
        }

    }

    private void initObstacleNode(CaveNode cavenode)
    {
        cavenode.hasObstacle = true;
    }

    private void initSupmuwNode(CaveNode cavenode)
    {
        cavenode.hasSupmuw = true;
        cavenode.hasMoo = true;
        supmuw = new Supmuw(cavenode.x, cavenode.y, cavenode);
    }

    private void initWumpusNode(CaveNode cavenode)
    {
        cavenode.hasWumpus = true;
        cavenode.hasStench = true;
        wumpus = new Wumpus(cavenode.x, cavenode.y);
    }

    private void initPitNode(CaveNode cavenode)
    {
        cavenode.hasPit = true;
    }

    private void initGoldNode(CaveNode cavenode)
    {
        cavenode.hasGold = true;
        gold = new Gold(cavenode.x, cavenode.y);
    }

    public CaveNode getNextNode(CaveNode cavenode, char c)
    {
        int i = cavenode.x;
        int j = cavenode.y;
        switch(c)
        {
        case 78: // 'N'
            j++;
            break;

        case 83: // 'S'
            j--;
            break;

        case 69: // 'E'
            i++;
            break;

        case 87: // 'W'
            i--;
            break;

        default:
            //System.out.println((new StringBuilder()).append("WumplusEnvironment.getNextNode: Bad direction given: ").append(c).toString());
            break;
        }
        return (CaveNode)grid.get(new Point(i, j));
    }

    public Vector get4AdjacentNodes(CaveNode cavenode)
    {
        Vector vector = new Vector();
        vector.add(grid.get(new Point(cavenode.x, cavenode.y + 1)));
        vector.add(grid.get(new Point(cavenode.x + 1, cavenode.y)));
        vector.add(grid.get(new Point(cavenode.x, cavenode.y - 1)));
        vector.add(grid.get(new Point(cavenode.x - 1, cavenode.y)));
        return vector;
    }

    public Vector get8AdjacentNodes(CaveNode cavenode)
    {
        Vector vector = new Vector();
        vector.add(grid.get(new Point(cavenode.x, cavenode.y + 1)));
        vector.add(grid.get(new Point(cavenode.x + 1, cavenode.y + 1)));
        vector.add(grid.get(new Point(cavenode.x + 1, cavenode.y)));
        vector.add(grid.get(new Point(cavenode.x + 1, cavenode.y - 1)));
        vector.add(grid.get(new Point(cavenode.x, cavenode.y - 1)));
        vector.add(grid.get(new Point(cavenode.x - 1, cavenode.y - 1)));
        vector.add(grid.get(new Point(cavenode.x - 1, cavenode.y)));
        vector.add(grid.get(new Point(cavenode.x - 1, cavenode.y + 1)));
        return vector;
    }

    public boolean areNodesAdjacent(CaveNode cavenode, CaveNode cavenode1)
    {
        if(cavenode == null || cavenode1 == null)
        {
            return false;
        } else
        {
            Vector vector = get4AdjacentNodes(cavenode);
            return vector.contains(cavenode1);
        }
    }

    public Hashtable grid;
    public Vector unvisitedNodes;
    public Agent agent;
    public Supmuw supmuw;
    public Wumpus wumpus;
    public Gold gold;
    private final int pitPercent = 7;
    private final int wallPercent = 15;
    public final int MAX_WIDTH = 10;
    public final int MAX_HEIGHT = 10;
    public int havevalidpath = 0;//////////////////////////////
}
