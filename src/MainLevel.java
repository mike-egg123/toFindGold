import gameEngine.*;
import java.awt.*;
import java.util.*;
import wworld.*;

/**
 * 观察游戏进程，监视游戏中的关键事件（包括恶魔和天使的状态，金币的状态，
 * 是否拿到Bonus奖励，游戏胜负），并在每一帧之后刷新界面显示，进行图像渲染
 */
public class MainLevel extends Level
{

    /**
     * 初始化游戏进程，设置显示容器，键盘监听器，音频播放器等
     * @param component 显示容器
     */
    public MainLevel(Component component)
    {
        super(component);
        MasterPanel masterpanel = (MasterPanel)component;
        keyboard = masterpanel.getKeyboard();
        config = masterpanel.getConfig();
        midi = masterpanel.getMidiPlayer();
        midi.load("sounds/bg1.mid");
        midi.play(true);
        midi.loop(-1);
        rand = new Random();
        stepDone = true;
        mapCompleted = false;
        victoryCondition = 0;
    }

    /**
     * 加载数据，太多了不想说了，反正就所有元素呗
     */
    public void loadData()
    {
        MasterPanel masterpanel = (MasterPanel)parent;
        HUDSprite.loadImages(imgLoader);
        CaveNodeSprite.loadImages(imgLoader);
        AgentSprite.loadImages(imgLoader);
        WumpusSprite.loadImages(imgLoader);
        FXSprite.loadImages(imgLoader);
        imgLoader.waitForAll();
        //System.out.println("loaded images for MainLevel");
        wumplusEnvironment = new WumplusEnvironment(masterpanel.loadFile);
        hudPanel = new HUDSprite(0.0D, 0.0D, 'p');
        hasGoldSprite = new HUDSprite(0.0D, 48D, 'g');
        hasFoodSprite = new HUDSprite(0.0D, 120D, 'f');
        hasArrowSprite = new HUDSprite(0.0D, 192D, 'a');
        winSprite = new HUDSprite(96D, 120D, 'v');
        loseSprite = new HUDSprite(96D, 120D, 'n');
        dieSprite = new HUDSprite(96D, 120D, 'd');
        winSprite.isVisible = false;
        loseSprite.isVisible = false;
        dieSprite.isVisible = false;
        Point point = gridToPanelCoords(1, 1);
        agentSprite = new AgentSprite(point.x, point.y);
        agentGridDestX = 1;
        agentGridDestY = 1;
        entranceSprite = new FXSprite(point.x, point.y, 'E');
        Wumpus wumpus = wumplusEnvironment.wumpus;
        point = gridToPanelCoords(wumpus.x, wumpus.y);
        wumpusSprite = new WumpusSprite(point.x, point.y, 'W');
        Supmuw supmuw = wumplusEnvironment.supmuw;
        point = gridToPanelCoords(supmuw.x, supmuw.y);
        supmuwSprite = new WumpusSprite(point.x, point.y, 'S');
        Gold gold = wumplusEnvironment.gold;
        point = gridToPanelCoords(gold.x, gold.y);
        glitterSprite = new FXSprite(point.x, point.y, 'G');
        Hashtable hashtable = wumplusEnvironment.grid;
        caveNodeSprites = new Hashtable();
        wallSprites = new Hashtable();
        breezeSprites = new ArrayList();
        stenchSprites = new ArrayList();
        mooSprites = new ArrayList();
        for(int i = 0; i <= 11; i++)
        {
            for(int j = 0; j <= 11; j++)
            {
                Point point2 = new Point(j, i);
                CaveNode cavenode = (CaveNode)hashtable.get(point2);
                Point point1 = gridToPanelCoords(j, i);
                char c = ' ';
                if(cavenode.hasObstacle)
                    c = 'O';
                if(cavenode.hasPit)
                    c = 'P';
                CaveNodeSprite cavenodesprite = new CaveNodeSprite(point1.x, point1.y, c);
                CaveNodeSprite cavenodesprite1 = new CaveNodeSprite(point1.x, point1.y, 'W');
                caveNodeSprites.put(point2, cavenodesprite);
                wallSprites.put(point2, cavenodesprite1);
                if(cavenode.hasBreeze)
                {
                    FXSprite fxsprite = new FXSprite(point1.x, point1.y, 'B');
                    breezeSprites.add(fxsprite);
                }
                if(cavenode.hasStench)
                {
                    FXSprite fxsprite1 = new FXSprite(point1.x, point1.y, 'S');
                    stenchSprites.add(fxsprite1);
                }
                if(cavenode.hasMoo)
                {
                    FXSprite fxsprite2 = new FXSprite(point1.x, point1.y, 'M');
                    mooSprites.add(fxsprite2);
                }
            }

        }

        testFloor = new CaveNodeSprite(72D, 24D, 'F');
        testFloor.width = 240;
        testFloor.height = 240;
        soundTimer = 0;
    }

    /**
     * 清屏
     */
    public void clean()
    {
        super.clean();
        HUDSprite.clean();
        CaveNodeSprite.clean();
        AgentSprite.clean();
        FXSprite.clean();
        keyboard = null;
        config = null;
    }

    /**
     * 游戏进程监控器，在这里对游戏进程监控，根据上一帧的情况渲染下一帧
     */
    public void timerLoop()
    {
        MasterPanel masterpanel = (MasterPanel)parent;
        if(keyboard.isTyped(config.VK_ESC))
        {
            masterpanel.startGame();
            return;
        }
        Agent agent = wumplusEnvironment.agent;
        if(stepDone && agentSprite.animationDone && !mapCompleted)
        {
            if(agent.isDead)
            {
                agentSprite.setAnimation("die");
                mapCompleted = true;
                victoryCondition = -1;
                dieSprite.isVisible = true;
                midi.load("sounds/gameOver.mid");
                midi.play(true);
                midi.loop(0);
            }
            if(agent.isVictorious)
            {
                mapCompleted = true;
                agentSprite.isVisible = false;
                if(agent.hasGold)
                {
                    victoryCondition = 2;
                    midi.stop();
                    Sound sound = new Sound("sounds/mmVictory.wav");
                    sound.play();
                    winSprite.isVisible = true;
                } else
                {
                    victoryCondition = 1;
                    midi.stop();
                    //当没有拿到金子，空着手离开洞口，播放以下音乐
                    Sound sound = new Sound("sounds/gameOver.mid");
                    sound.play();
                    loseSprite.isVisible = true;
                }
            }
            int i = -1;
            if(masterpanel.gameMode == 'H' && !agent.isDead)
            {
                if(keyboard.isTyped(config.VK_LEFT))
                    i = 0;
                if(keyboard.isTyped(config.VK_RIGHT))
                    i = 1;
                if(keyboard.isTyped(config.VK_UP))
                    i = 2;
                if(keyboard.isTyped(config.VK_ENTER))
                    i = 3;
                if(keyboard.isTyped(config.VK_SHOT))
                    i = 5;
                if(keyboard.isTyped(config.VK_CLIMB))
                    i = 4;
            } else
            if(!agent.isDead)
                i = agent.getNextAction(wumplusEnvironment);
            String s = agent.act(i, wumplusEnvironment);
            if(s.equals("BUMP"))
            {
                Sound sound1 = new Sound("sounds/tok2.wav");
                sound1.play();
            }
            if(s.equals("GOLDGET"))
            {
                agentSprite.setAnimation("gold");
                Sound sound2 = new Sound("sounds/gold.wav");
                sound2.play();
            }
            if(s.equals("FOODGET"))
            {
                Sound sound3 = new Sound("sounds/food.wav");
                sound3.play();
            }
            if(s.equals("SCREAM"))
            {
                agentSprite.setAnimation("shoot");
                Sound sound4 = new Sound("sounds/arrow.wav");
                sound4.play();
                sound4 = new Sound("sounds/wumpusDie.wav");
                sound4.play();
            }
            if(s.equals("MISS"))
            {
                agentSprite.setAnimation("shoot");
                Sound sound5 = new Sound("sounds/arrow.wav");
                sound5.play();
            }
            if(s.equals("MOVED"))
                soundTimer = 0;
            CaveNode cavenode = (CaveNode)wumplusEnvironment.grid.get(new Point(agent.x, agent.y));
            if(soundTimer <= 0)
            {
                if(cavenode.hasStench)
                {
                    int l = rand.nextInt(5) + 1;
                    Sound sound8 = new Sound((new StringBuilder()).append("sounds/stench").append(l).append(".wav").toString());
                    sound8.play();
                }
                if(cavenode.hasMoo)
                {
                    int i1 = rand.nextInt(5) + 1;
                    Sound sound9 = new Sound((new StringBuilder()).append("sounds/moo").append(i1).append(".wav").toString());
                    sound9.play();
                }
                if(cavenode.hasBreeze)
                {
                    Sound sound6 = new Sound("sounds/wind1.wav");
                    sound6.play();
                }
                if(cavenode.hasGold)
                {
                    Sound sound7 = new Sound("sounds/sparkleLoop.wav");
                    sound7.play();
                }
                soundTimer = 35;
            }
            soundTimer--;
        }
        if(mapCompleted)
        {
            Hashtable hashtable = wumplusEnvironment.grid;
            for(int j = 0; j <= 11; j++)
            {
                for(int k = 0; k <= 11; k++)
                {
                    Point point1 = new Point(k, j);
                    CaveNode cavenode1 = (CaveNode)hashtable.get(point1);
                    cavenode1.wasVisited = true;
                }

            }

            if(keyboard.isTyped(config.VK_ENTER))
            {
                masterpanel.startGame();
                return;
            }
        }
        switch(agent.direction)
        {
        case 78: // 'N'
            agentSprite.setDirection('N');
            break;

        case 83: // 'S'
            agentSprite.setDirection('S');
            break;

        case 87: // 'W'
            agentSprite.setDirection('W');
            break;

        case 69: // 'E'
            agentSprite.setDirection('E');
            break;
        }
        Point point = gridToPanelCoords(agent.x, agent.y);
        if(agentSprite.x < (double)point.x)
        {
            agentSprite.x++;
            stepDone = false;
        }
        if(agentSprite.x > (double)point.x)
        {
            agentSprite.x--;
            stepDone = false;
        }
        if(agentSprite.y < (double)point.y)
        {
            agentSprite.y++;
            stepDone = false;
        }
        if(agentSprite.y > (double)point.y)
        {
            agentSprite.y--;
            stepDone = false;
        }
        if(agentSprite.x == (double)point.x && agentSprite.y == (double)point.y)
            stepDone = true;
        hasArrowSprite.isSelected = agent.hasArrow;
        hasGoldSprite.isSelected = agent.hasGold;
        hasFoodSprite.isSelected = agent.hasFood;
        if(wumplusEnvironment.wumpus.isDead)
            wumpusSprite.setAnimation("dead");
        if(wumplusEnvironment.supmuw.isDead)
        {
            mooSprites.clear();
            supmuwSprite.setAnimation("dead");
        }
        if(agent.hasGold)
            glitterSprite.isVisible = false;
        updateWalls();
        score = agent.score;
        animateAll();
        imgLoader.waitForAll();
    }

    /**
     * 从素材图片中取出对应图片，实际上是通过将在游戏界面的坐标映射到素材图中的坐标的方法得到
     * @param i 游戏界面中横坐标
     * @param j 游戏界面中纵坐标
     * @return 返回对应的素材图中坐标
     */
    private Point gridToPanelCoords(int i, int j)
    {
        i = 48 + 24 * i;            //每个小方格占有24个像素，（1，1）为左下角起点
        j = 264 - 24 * j;
        return new Point(i, j);
    }

    /**
     * 更新周围墙体，因为墙体有两种不同的显示形式：未发现时的全黑形式和已发现时的墙体本身
     */
    private void updateWalls()
    {
        Hashtable hashtable = wumplusEnvironment.grid;
        for(int i = 1; i <= 10; i++)
        {
            for(int j = 1; j <= 10; j++)
            {
                Point point = new Point(j, i);
                CaveNode cavenode = (CaveNode)hashtable.get(point);
                CaveNodeSprite cavenodesprite = (CaveNodeSprite)wallSprites.get(point);
                CaveNode cavenode1 = wumplusEnvironment.getNextNode(cavenode, 'N');
                if(cavenode1.hasObstacle && cavenode1.wasVisited && !cavenode.hasObstacle)
                    cavenode.foundNorthWall = true;
                CaveNode cavenode2 = wumplusEnvironment.getNextNode(cavenode, 'S');
                if(cavenode2.hasObstacle && cavenode2.wasVisited && !cavenode.hasObstacle)
                    cavenode.foundSouthWall = true;
                CaveNode cavenode3 = wumplusEnvironment.getNextNode(cavenode, 'E');
                if(cavenode3.hasObstacle && cavenode3.wasVisited && !cavenode.hasObstacle)
                    cavenode.foundEastWall = true;
                CaveNode cavenode4 = wumplusEnvironment.getNextNode(cavenode, 'W');
                if(cavenode4.hasObstacle && cavenode4.wasVisited && !cavenode.hasObstacle)
                    cavenode.foundWestWall = true;
                cavenodesprite.hasBottomWall = cavenode.foundSouthWall;
                cavenodesprite.hasTopWall = cavenode.foundNorthWall;
                cavenodesprite.hasLeftWall = cavenode.foundWestWall;
                cavenodesprite.hasRightWall = cavenode.foundEastWall;
            }

        }

    }

    /**
     * 激活所有元素，太多了不想说了
     */
    private void animateAll()
    {
        hudPanel.animate(imgLoader);
        hasGoldSprite.animate(imgLoader);
        hasFoodSprite.animate(imgLoader);
        hasArrowSprite.animate(imgLoader);
        winSprite.animate(imgLoader);
        loseSprite.animate(imgLoader);
        dieSprite.animate(imgLoader);
        agentSprite.animate(imgLoader);
        wumpusSprite.animate(imgLoader);
        supmuwSprite.animate(imgLoader);
        FXSprite fxsprite;
        for(Iterator iterator = breezeSprites.iterator(); iterator.hasNext(); fxsprite.animate(imgLoader))
            fxsprite = (FXSprite)iterator.next();

        FXSprite fxsprite1;
        for(Iterator iterator1 = stenchSprites.iterator(); iterator1.hasNext(); fxsprite1.animate(imgLoader))
            fxsprite1 = (FXSprite)iterator1.next();

        FXSprite fxsprite2;
        for(Iterator iterator2 = mooSprites.iterator(); iterator2.hasNext(); fxsprite2.animate(imgLoader))
            fxsprite2 = (FXSprite)iterator2.next();

        glitterSprite.animate(imgLoader);
        entranceSprite.animate(imgLoader);
    }

    /**
     * 将所有元素画在游戏界面
     * @param graphics2d 绘图类对象，实现上述功能就靠它了
     */
    public void render(Graphics2D graphics2d)
    {
        MasterPanel masterpanel = (MasterPanel)parent;
        graphics2d.setBackground(new Color(0, 0, 0));
        graphics2d.clearRect(0, 0, 340, 288);
        testFloor.render(graphics2d);
        hudPanel.render(graphics2d);
        hasGoldSprite.render(graphics2d);
        hasFoodSprite.render(graphics2d);
        hasArrowSprite.render(graphics2d);
        for(int i = 0; i <= 11; i++)
        {
            for(int j = 0; j <= 11; j++)
            {
                Point point = new Point(j, i);
                ((CaveNodeSprite)caveNodeSprites.get(point)).render(graphics2d);
                ((CaveNodeSprite)wallSprites.get(point)).render(graphics2d);
            }

        }

        wumpusSprite.render(graphics2d);
        supmuwSprite.render(graphics2d);
        agentSprite.render(graphics2d);
        FXSprite fxsprite;
        for(Iterator iterator = breezeSprites.iterator(); iterator.hasNext(); fxsprite.render(graphics2d))
            fxsprite = (FXSprite)iterator.next();

        FXSprite fxsprite1;
        for(Iterator iterator1 = stenchSprites.iterator(); iterator1.hasNext(); fxsprite1.render(graphics2d))
            fxsprite1 = (FXSprite)iterator1.next();

        FXSprite fxsprite2;
        for(Iterator iterator2 = mooSprites.iterator(); iterator2.hasNext(); fxsprite2.render(graphics2d))
            fxsprite2 = (FXSprite)iterator2.next();

        glitterSprite.render(graphics2d);
        entranceSprite.render(graphics2d);
        Hashtable hashtable = wumplusEnvironment.grid;
        if(masterpanel.gameMode == 'H')
            graphics2d.setColor(new Color(0, 0, 0, 255));
        else
            graphics2d.setColor(new Color(0, 0, 0, 128));
        for(int k = 0; k <= 11; k++)
        {
            for(int l = 0; l <= 11; l++)
            {
                Point point1 = new Point(l, k);
                CaveNode cavenode = (CaveNode)hashtable.get(point1);
                if(!cavenode.wasVisited)
                {
                    Point point2 = gridToPanelCoords(point1.x, point1.y);
                    graphics2d.drawRect(point2.x, point2.y, 24, 24);
                    graphics2d.fillRect(point2.x, point2.y, 24, 24);
                }
            }

        }

        graphics2d.setColor(new Color(255, 255, 255));
        graphics2d.drawString((new StringBuilder()).append("YOUR SCORE: ").append(score).toString(), 72, 10);
        winSprite.render(graphics2d);
        loseSprite.render(graphics2d);
        dieSprite.render(graphics2d);
    }

    private MenuSprite quit;
    private HUDSprite hudPanel;
    private HUDSprite hasGoldSprite;
    private HUDSprite hasFoodSprite;
    private HUDSprite hasArrowSprite;
    private HUDSprite winSprite;
    private HUDSprite loseSprite;
    private HUDSprite dieSprite;
    private AgentSprite agentSprite;
    private WumpusSprite wumpusSprite;
    private WumpusSprite supmuwSprite;
    private FXSprite entranceSprite;
    private FXSprite glitterSprite;
    private ArrayList breezeSprites;
    private ArrayList stenchSprites;
    private ArrayList mooSprites;
    private Hashtable caveNodeSprites;
    private Hashtable wallSprites;
    private WumplusEnvironment wumplusEnvironment;
    private boolean stepDone;
    private boolean mapCompleted;
    private int victoryCondition;
    private int score;
    private int soundTimer;
    private Random rand;
    private CaveNodeSprite testFloor;
    private int agentGridDestX;
    private int agentGridDestY;
    private Keyboard keyboard;
    private ConfigWumpus config;
    private MidiPlayer midi;
}
