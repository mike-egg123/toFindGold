package wworld;

import java.awt.Point;
import java.util.*;
import java.io.*;

/**
 * 地图生成类，可以根据难度生成地图
 */
public class WumplusEnvironment
{

    /**
     * 根据难度生成地图
     * @param s 难度标识符，"easy" "hard"表示简单和困难
     */
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

    /**
     * 初始化所有对象
     */
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

    /**
     * 初始化洞穴元素对象
     * @param cavenode 洞穴元素对象
     */
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

    /**
     * 随机生成简单地图（陷阱较少）
     */
    private void initRandomEasyEnvironment()
    {
        GenerateMap gEasy = new GenerateMap(80, 10, 0.1, false);
        char[][] arrs = new char[10][10];
        arrs = gEasy.generateMap();
        initLoadedEnvironment(arrs,gEasy.Cx,gEasy.Cy,gEasy.Bx,gEasy.By,gEasy.Gx,gEasy.Gy);
    }

    /**
     * 随机生成简单地图（陷阱较多）
     */
    private void initRandomHardEnvironment(){
        GenerateMap gHard = new GenerateMap(80, 10, 0.15, false);
        char[][] arrs = new char[10][10];
        arrs = gHard.generateMap();
        initLoadedEnvironment(arrs,gHard.Cx,gHard.Cy,gHard.Bx,gHard.By,gHard.Gx,gHard.Gy);
    }

    /**
     * 根据给的存放地图信息的二维数组生成地图
     * @param arrs 存放地图信息的二维数组
     * @param cx 金子横坐标
     * @param cy 金子纵坐标
     * @param bx 坏怪横坐标
     * @param by 坏怪纵坐标
     * @param gx 好怪横坐标
     * @param gy 好怪纵坐标
     */
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

    /**
     * 构建洞穴边界，全是墙
     */
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

    /**
     * 初始化在路上的障碍物，即路上墙
     * @param cavenode 将要绑定的洞穴元素
     */
    private void initObstacleNode(CaveNode cavenode)
    {
        cavenode.hasObstacle = true;
    }

    /**
     * 初始化好怪
     * @param cavenode 将要绑定的洞穴元素
     */
    private void initSupmuwNode(CaveNode cavenode)
    {
        cavenode.hasSupmuw = true;
        cavenode.hasMoo = true;
        supmuw = new Supmuw(cavenode.x, cavenode.y, cavenode);
    }

    /**
     * 初始化坏怪
     * @param cavenode 将要绑定的洞穴元素
     */
    private void initWumpusNode(CaveNode cavenode)
    {
        cavenode.hasWumpus = true;
        cavenode.hasStench = true;
        wumpus = new Wumpus(cavenode.x, cavenode.y);
    }

    /**
     * 初始化陷阱
     * @param cavenode 将要绑定的洞穴元素
     */
    private void initPitNode(CaveNode cavenode)
    {
        cavenode.hasPit = true;
    }

    /**
     * 初始化金子
     * @param cavenode 将要绑定的洞穴元素
     */
    private void initGoldNode(CaveNode cavenode)
    {
        cavenode.hasGold = true;
        gold = new Gold(cavenode.x, cavenode.y);
    }

    /**
     * 得到玩家的下一个位置坐标
     * @param cavenode 绑定了当前位置的洞穴元素
     * @param c 方向
     * @return 绑定下一个位置坐标的洞穴元素
     */
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

    /**
     * 分别将周围四个方向加入队列，尝试得到下一步决策
     * @param cavenode 绑定了当前位置的洞穴元素
     * @return 加入了四个方向信息的队列
     */
    public Vector get4AdjacentNodes(CaveNode cavenode)
    {
        Vector vector = new Vector();
        vector.add(grid.get(new Point(cavenode.x, cavenode.y + 1)));
        vector.add(grid.get(new Point(cavenode.x + 1, cavenode.y)));
        vector.add(grid.get(new Point(cavenode.x, cavenode.y - 1)));
        vector.add(grid.get(new Point(cavenode.x - 1, cavenode.y)));
        return vector;
    }

    /**
     * 分别将周围八个方向加入队列，尝试得到下一步决策
     * @param cavenode 绑定了当前位置的洞穴元素
     * @return 加入了八个方向信息的队列
     */
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
    public int havevalidpath = 0;
}
