import gameEngine.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 帮助页面，控制多页帮助的图文显示
 */
public class HowToPlayLevel extends Level
{

    /**
     * 初始化帮助页面，设置好显示的容器，键盘监听器，配置文件监听器（已废弃），
     * 音频文件播放器并直接开始循环播放
     * @param component 准备显示该帮助页面的容器
     */
    public HowToPlayLevel(Component component)
    {
        super(component);
        MasterPanel masterpanel = (MasterPanel)component;
        keyboard = masterpanel.getKeyboard();
        config = masterpanel.getConfig();
        midi = masterpanel.getMidiPlayer();
        midi.load("sounds/bg.mid");
        midi.play(true);
        midi.loop(-1);
    }

    /**
     * 加载数据，这些是在帮助页面特有的图形，需要在此重新加载
     */
    public void loadData()
    {
        MasterPanel masterpanel = (MasterPanel)parent;
        HUDSprite.loadImages(imgLoader);
        CaveNodeSprite.loadImages(imgLoader);
        AgentSprite.loadImages(imgLoader);
        WumpusSprite.loadImages(imgLoader);
        FXSprite.loadImages(imgLoader);
        HowToPlaySprite.loadImages(imgLoader);
        imgLoader.waitForAll();
        hudPanel = new HUDSprite(0.0D, 0.0D, 'p');
        hasGoldSprite = new HUDSprite(0.0D, 48D, 'g');
        hasBonusSprite = new HUDSprite(0.0D, 120D, 'f');
        hasArrowSprite = new HUDSprite(0.0D, 192D, 'a');
        bgSprite = new HowToPlaySprite(0.0D, 10D, 'b');
        htp2Sprite = new HowToPlaySprite(72D, 96D, '2');
        htp6Sprite = new HowToPlaySprite(72D, 96D, '6');
        enterSprite = new HowToPlaySprite(168D, 264D, 'e');
        Point point = gridToPanelCoords(0, 0);
        agentSprite = new AgentSprite(point.x, point.y);
        agentSprite2 = new AgentSprite(point.x, point.y);
        entranceSprite = new FXSprite(point.x, point.y, 'E');
        wumpusSprite = new WumpusSprite(0.0D, 0.0D, 'W');
        supmuwSprite = new WumpusSprite(0.0D, 0.0D, 'S');
        supmuwSprite2 = new WumpusSprite(0.0D, 0.0D, 'S');
        supmuwSprite3 = new WumpusSprite(0.0D, 0.0D, 'S');
        glitterSprite = new FXSprite(0.0D, 0.0D, 'G');
        breezeSprites = new ArrayList();
        stenchSprites = new ArrayList();
        mooSprites = new ArrayList();
        testFloor = new CaveNodeSprite(0.0D, 0.0D, 'F');
        testFloor.width = 72;
        testFloor.height = 72;
        testFloor2 = new CaveNodeSprite(0.0D, 0.0D, 'F');
        testFloor2.width = 72;
        testFloor2.height = 72;
        testFloor3 = new CaveNodeSprite(0.0D, 0.0D, 'F');
        testFloor3.width = 72;
        testFloor3.height = 72;
        testFloor4 = new CaveNodeSprite(0.0D, 0.0D, 'F');
        testFloor4.width = 72;
        testFloor4.height = 72;
        testFloor5 = new CaveNodeSprite(0.0D, 0.0D, 'F');
        testFloor5.width = 72;
        testFloor5.height = 72;
        pitSprite = new CaveNodeSprite(240D, 168D, 'P');
        slideNum = 0;
        timer1 = 0;
        initCurSlide();
    }

    /**
     * 清除在帮助页面加载的对象，以便开始游戏
     */
    public void clean()
    {
        super.clean();
        HUDSprite.clean();
        CaveNodeSprite.clean();
        AgentSprite.clean();
        HowToPlaySprite.clean();
        FXSprite.clean();
        keyboard = null;
        config = null;
    }

    /**
     * 监听键盘事件，根据对应的键盘输入，显示对应的帮助界面
     */
    public void timerLoop()
    {
        MasterPanel masterpanel = (MasterPanel)parent;
        if(keyboard.isTyped(config.VK_ESC))
        {
            masterpanel.startGame();
            return;
        }
        if(keyboard.isTyped(config.VK_ENTER))
        {
            slideNum++;
            initCurSlide();
        }
        testFloor.isVisible = false;
        testFloor2.isVisible = false;
        testFloor3.isVisible = false;
        testFloor4.isVisible = false;
        testFloor5.isVisible = false;
        pitSprite.isVisible = false;
        hudPanel.isVisible = false;
        hasGoldSprite.isVisible = false;
        hasBonusSprite.isVisible = false;
        hasArrowSprite.isVisible = false;
        htp2Sprite.isVisible = false;
        htp6Sprite.isVisible = false;
        agentSprite.isVisible = false;
        agentSprite2.isVisible = false;
        wumpusSprite.isVisible = false;
        supmuwSprite.isVisible = false;
        supmuwSprite2.isVisible = false;
        supmuwSprite3.isVisible = false;
        entranceSprite.isVisible = false;
        glitterSprite.isVisible = false;
        if(slideNum == 0)
        {
            agentSprite.isVisible = true;
            agentSprite.setDirection('S');
            agentSprite.x = 48D;
            agentSprite.y = 144D;
            agentSprite2.isVisible = true;
            testFloor.isVisible = true;
            testFloor.x = 216D;
            testFloor.y = 120D;
            glitterSprite.isVisible = true;
            glitterSprite.x = 216D;
            glitterSprite.y = 144D;
            if(agentSprite2.x > glitterSprite.x)
            {
                agentSprite2.x--;
                agentSprite2.setDirection('W');
            }
            if(agentSprite2.x == glitterSprite.x)
            {
                if(!agentSprite2.animation.equals("gold"))
                    agentSprite2.setAnimation("gold");
                timer1++;
                if(timer1 > 30)
                {
                    timer1 = 0;
                    agentSprite2.setAnimation("walk");
                    agentSprite2.x = 264D;
                }
            }
        } else
        if(slideNum == 1)
        {
            agentSprite.isVisible = true;
            agentSprite.x = 168D;
            agentSprite.y = 168D;
            htp2Sprite.isVisible = true;
        } else
        if(slideNum == 2)
        {
            wumpusSprite.isVisible = true;
            wumpusSprite.x = 72D;
            wumpusSprite.y = 144D;
            testFloor.isVisible = true;
            testFloor.x = 48D;
            testFloor.y = 120D;
            pitSprite.isVisible = true;
            pitSprite.x = 240D;
            pitSprite.y = 168D;
            testFloor2.isVisible = true;
            testFloor2.x = 216D;
            testFloor2.y = 144D;
        } else
        if(slideNum == 3)
        {
            supmuwSprite.isVisible = true;
            supmuwSprite.x = 72D;
            supmuwSprite.y = 144D;
            supmuwSprite2.isVisible = true;
            supmuwSprite2.x = 216D;
            supmuwSprite2.y = 96D;
            supmuwSprite3.isVisible = true;
            supmuwSprite3.x = 264D;
            supmuwSprite3.y = 216D;
            pitSprite.isVisible = true;
            pitSprite.x = 264D;
            pitSprite.y = 216D;
            wumpusSprite.isVisible = true;
            wumpusSprite.x = 240D;
            wumpusSprite.y = 96D;
            testFloor.isVisible = true;
            testFloor.x = 48D;
            testFloor.y = 120D;
            testFloor2.isVisible = true;
            testFloor2.x = 192D;
            testFloor2.y = 72D;
            testFloor3.isVisible = true;
            testFloor3.x = 216D;
            testFloor3.y = 72D;
            testFloor4.isVisible = true;
            testFloor4.x = 192D;
            testFloor4.y = 192D;
            testFloor5.isVisible = true;
            testFloor5.x = 240D;
            testFloor5.y = 192D;
            agentSprite.isVisible = true;
            agentSprite.setDirection('E');
            if(timer1 < 48)
                agentSprite.x++;
            if(timer1 >= 48 && timer1 < 54)
                agentSprite.x -= 4D;
            if(timer1 > 78)
            {
                agentSprite.x = 216D;
                timer1 = 0;
            }
            timer1++;
        } else
        if(slideNum == 4)
        {
            agentSprite.isVisible = true;
            agentSprite.x = 96D;
            agentSprite.y = 144D;
            wumpusSprite.isVisible = true;
            wumpusSprite.x = 240D;
            wumpusSprite.y = 144D;
            testFloor.isVisible = true;
            testFloor.x = 72D;
            testFloor.y = 120D;
            testFloor2.isVisible = true;
            testFloor2.x = 216D;
            testFloor2.y = 120D;
            hudPanel.isVisible = true;
            hasGoldSprite.isVisible = true;
            hasBonusSprite.isVisible = true;
            hasArrowSprite.isVisible = true;
            if(timer1 < 60)
            {
                wumpusSprite.setAnimation("alive");
                hasArrowSprite.isSelected = true;
            }
            if(timer1 >= 60 && timer1 < 80)
            {
                wumpusSprite.setAnimation("dead");
                agentSprite.setAnimation("shoot");
                hasArrowSprite.isSelected = false;
            }
            if(timer1 >= 80)
                agentSprite.setAnimation("walk");
            if(timer1 > 100)
                timer1 = 0;
            timer1++;
        } else
        if(slideNum == 5)
        {
            entranceSprite.isVisible = true;
            entranceSprite.x = 120D;
            entranceSprite.y = 192D;
            agentSprite.isVisible = true;
            htp6Sprite.isVisible = true;
            glitterSprite.isVisible = true;
            glitterSprite.x = 216D;
            glitterSprite.y = 168D;
            if(timer1 < 48)
            {
                if(!agentSprite.animation.equals("walk"))
                    agentSprite.setAnimation("walk");
                agentSprite.setDirection('E');
                agentSprite.x++;
            }
            if(timer1 >= 48 && timer1 < 78 && !agentSprite.animation.equals("gold"))
                agentSprite.setAnimation("gold");
            if(timer1 >= 78 && timer1 < 174)
            {
                if(!agentSprite.animation.equals("walk"))
                    agentSprite.setAnimation("walk");
                agentSprite.setDirection('W');
                agentSprite.x--;
            }
            if(timer1 >= 174 && timer1 < 198)
            {
                agentSprite.setDirection('S');
                agentSprite.y++;
            }
            if(timer1 > 222)
            {
                agentSprite.x = 168D;
                agentSprite.y = 168D;
                timer1 = 0;
            }
            timer1++;
        } else
        if(slideNum != 6 && slideNum != 7)
        {
            masterpanel.changeCurrentLevel("Menu");
            return;
        }
        animateAll();
        imgLoader.waitForAll();
    }

    /**
     * 初始化当前页面
     */
    public void initCurSlide()
    {
        if(slideNum == 0)
        {
            agentSprite2.x = 264D;
            agentSprite2.y = 144D;
            timer1 = 0;
        } else
        if(slideNum != 1)
            if(slideNum == 2)
            {
                FXSprite fxsprite = new FXSprite(72D, 120D, 'S');
                stenchSprites.add(fxsprite);
                fxsprite = new FXSprite(48D, 144D, 'S');
                stenchSprites.add(fxsprite);
                fxsprite = new FXSprite(96D, 144D, 'S');
                stenchSprites.add(fxsprite);
                fxsprite = new FXSprite(72D, 168D, 'S');
                stenchSprites.add(fxsprite);
                FXSprite fxsprite3 = new FXSprite(240D, 144D, 'B');
                breezeSprites.add(fxsprite3);
                fxsprite3 = new FXSprite(216D, 168D, 'B');
                breezeSprites.add(fxsprite3);
                fxsprite3 = new FXSprite(264D, 168D, 'B');
                breezeSprites.add(fxsprite3);
                fxsprite3 = new FXSprite(240D, 192D, 'B');
                breezeSprites.add(fxsprite3);
            } else
            if(slideNum == 3)
            {
                stenchSprites.clear();
                breezeSprites.clear();
                FXSprite fxsprite1 = new FXSprite(240D, 72D, 'S');
                stenchSprites.add(fxsprite1);
                fxsprite1 = new FXSprite(216D, 96D, 'S');
                stenchSprites.add(fxsprite1);
                fxsprite1 = new FXSprite(264D, 96D, 'S');
                stenchSprites.add(fxsprite1);
                fxsprite1 = new FXSprite(240D, 120D, 'S');
                stenchSprites.add(fxsprite1);
                FXSprite fxsprite4 = new FXSprite(48D, 120D, 'M');
                mooSprites.add(fxsprite4);
                fxsprite4 = new FXSprite(72D, 120D, 'M');
                mooSprites.add(fxsprite4);
                fxsprite4 = new FXSprite(96D, 120D, 'M');
                mooSprites.add(fxsprite4);
                fxsprite4 = new FXSprite(48D, 144D, 'M');
                mooSprites.add(fxsprite4);
                fxsprite4 = new FXSprite(96D, 144D, 'M');
                mooSprites.add(fxsprite4);
                fxsprite4 = new FXSprite(48D, 168D, 'M');
                mooSprites.add(fxsprite4);
                fxsprite4 = new FXSprite(72D, 168D, 'M');
                mooSprites.add(fxsprite4);
                fxsprite4 = new FXSprite(96D, 168D, 'M');
                mooSprites.add(fxsprite4);
                fxsprite4 = new FXSprite(192D, 72D, 'M');
                mooSprites.add(fxsprite4);
                fxsprite4 = new FXSprite(216D, 72D, 'M');
                mooSprites.add(fxsprite4);
                fxsprite4 = new FXSprite(240D, 72D, 'M');
                mooSprites.add(fxsprite4);
                fxsprite4 = new FXSprite(192D, 96D, 'M');
                mooSprites.add(fxsprite4);
                fxsprite4 = new FXSprite(240D, 96D, 'M');
                mooSprites.add(fxsprite4);
                fxsprite4 = new FXSprite(192D, 120D, 'M');
                mooSprites.add(fxsprite4);
                fxsprite4 = new FXSprite(216D, 120D, 'M');
                mooSprites.add(fxsprite4);
                fxsprite4 = new FXSprite(240D, 120D, 'M');
                mooSprites.add(fxsprite4);
                fxsprite4 = new FXSprite(240D, 192D, 'M');
                mooSprites.add(fxsprite4);
                fxsprite4 = new FXSprite(264D, 192D, 'M');
                mooSprites.add(fxsprite4);
                fxsprite4 = new FXSprite(288D, 192D, 'M');
                mooSprites.add(fxsprite4);
                fxsprite4 = new FXSprite(240D, 216D, 'M');
                mooSprites.add(fxsprite4);
                fxsprite4 = new FXSprite(288D, 216D, 'M');
                mooSprites.add(fxsprite4);
                fxsprite4 = new FXSprite(240D, 240D, 'M');
                mooSprites.add(fxsprite4);
                fxsprite4 = new FXSprite(264D, 240D, 'M');
                mooSprites.add(fxsprite4);
                fxsprite4 = new FXSprite(288D, 240D, 'M');
                mooSprites.add(fxsprite4);
                FXSprite fxsprite5 = new FXSprite(240D, 216D, 'B');
                breezeSprites.add(fxsprite5);
                fxsprite5 = new FXSprite(288D, 216D, 'B');
                breezeSprites.add(fxsprite5);
                fxsprite5 = new FXSprite(264D, 192D, 'B');
                breezeSprites.add(fxsprite5);
                fxsprite5 = new FXSprite(264D, 240D, 'B');
                breezeSprites.add(fxsprite5);
                agentSprite.x = 216D;
                agentSprite.y = 216D;
                timer1 = 0;
            } else
            if(slideNum == 4)
            {
                stenchSprites.clear();
                breezeSprites.clear();
                mooSprites.clear();
                FXSprite fxsprite2 = new FXSprite(240D, 120D, 'S');
                stenchSprites.add(fxsprite2);
                fxsprite2 = new FXSprite(216D, 144D, 'S');
                stenchSprites.add(fxsprite2);
                fxsprite2 = new FXSprite(264D, 144D, 'S');
                stenchSprites.add(fxsprite2);
                fxsprite2 = new FXSprite(240D, 168D, 'S');
                stenchSprites.add(fxsprite2);
                timer1 = 0;
            } else
            if(slideNum == 5)
            {
                stenchSprites.clear();
                timer1 = 0;
                agentSprite.x = 168D;
                agentSprite.y = 168D;
            } else
            if(slideNum != 6);
    }

    /**
     * 拆分素材图，将在帮助页面的坐标转成在素材图中的坐标
     * @param i 在帮助页面的横坐标
     * @param j 在帮助页面的纵坐标
     * @return 返回的是在素材图中的坐标
     */
    private Point gridToPanelCoords(int i, int j)
    {
        i = 48 + 24 * i;
        j = 264 - 24 * j;
        return new Point(i, j);
    }

    /**
     * 调用各个对象的激活函数，激活所有的元素
     */
    private void animateAll()
    {
        hudPanel.animate(imgLoader);
        hasGoldSprite.animate(imgLoader);
        hasBonusSprite.animate(imgLoader);
        hasArrowSprite.animate(imgLoader);
        agentSprite.animate(imgLoader);
        agentSprite2.animate(imgLoader);
        wumpusSprite.animate(imgLoader);
        supmuwSprite.animate(imgLoader);
        supmuwSprite2.animate(imgLoader);
        supmuwSprite3.animate(imgLoader);
        bgSprite.animate(imgLoader);
        htp2Sprite.animate(imgLoader);
        htp6Sprite.animate(imgLoader);
        enterSprite.animate(imgLoader);
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
     * 将所有元素画在GUI上面
     * @param graphics2d 绘图类对象，调用其中的方法可以将一些图片和文字画在GUI上面
     */
    public void render(Graphics2D graphics2d)
    {
        MasterPanel masterpanel = (MasterPanel)parent;
        graphics2d.setBackground(new Color(8, 8, 16));
        graphics2d.clearRect(0, 0, 340, 288);
        bgSprite.render(graphics2d);
        htp2Sprite.render(graphics2d);
        htp6Sprite.render(graphics2d);
        testFloor.render(graphics2d);
        testFloor2.render(graphics2d);
        testFloor3.render(graphics2d);
        testFloor4.render(graphics2d);
        testFloor5.render(graphics2d);
        pitSprite.render(graphics2d);
        hudPanel.render(graphics2d);
        hasGoldSprite.render(graphics2d);
        hasBonusSprite.render(graphics2d);
        hasArrowSprite.render(graphics2d);
        wumpusSprite.render(graphics2d);
        supmuwSprite.render(graphics2d);
        supmuwSprite2.render(graphics2d);
        supmuwSprite3.render(graphics2d);
        agentSprite.render(graphics2d);
        agentSprite2.render(graphics2d);
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
        graphics2d.setColor(new Color(255, 255, 255));
        if(slideNum == 0)
        {
            graphics2d.setFont(new Font("宋体", Font.BOLD, 8));
            graphics2d.drawString("你将控制一个英雄，", 24, 96);
            graphics2d.drawString("你的目标是找到金币，", 192, 96);
            graphics2d.drawString("金币只属于勇敢的和智慧的人。", 192, 111);
        } else
        if(slideNum == 1)
        {
            drawCenteredString("你探索的洞穴十分黑暗，你只能", 168, 48, graphics2d);
            drawCenteredString("看到你所到过的地方，", 168, 63, graphics2d);
            drawCenteredString("墙壁也是隐藏起来的，你无法穿过它们。", 168, 78, graphics2d);
        } else
        if(slideNum == 2)
        {
            drawCenteredString("这个是臭臭怪，", 72, 48, graphics2d);
            drawCenteredString("在你经过它的时候它将杀死你。", 72, 63, graphics2d);
            drawCenteredString("在它的四周环绕着恶臭气味。", 72, 78, graphics2d);
            //drawCenteredString("You can kill it with your arrow.", 72, 93, graphics2d);
            drawCenteredString("这是一个陷阱。", 240, 72, graphics2d);
            drawCenteredString("在陷阱四周环绕着微风。", 240, 87, graphics2d);
            drawCenteredString("陷阱同样可以杀死你，", 240, 102, graphics2d);
            drawCenteredString("因此，请远离它。", 240, 117, graphics2d);
        } else
        if(slideNum == 3)
        {
            drawCenteredString("这个是哞哞怪，", 72, 48, graphics2d);
            drawCenteredString("它喜欢说：\"Moo\"！", 72, 63, graphics2d);
            drawCenteredString("哞哞怪是友好的，", 72, 78, graphics2d);
            drawCenteredString("经过它身边时，你会得到鸡腿并且加分。", 80, 93, graphics2d);
            drawCenteredString("然而，当它站在臭臭怪的恶臭中时，", 240, 48, graphics2d);
            drawCenteredString("它会变得邪恶。", 240, 63, graphics2d);
            drawCenteredString("同时，当哞哞怪在陷阱中时，", 240, 168, graphics2d);
            drawCenteredString("他将把你拖进深渊！", 240, 183, graphics2d);
        } else
        if(slideNum == 4)
        {
            drawCenteredString("在你的背包里有一支箭矢，", 168, 48, graphics2d);
            drawCenteredString("当你遇到臭臭怪时你可以射死他，", 168, 63, graphics2d);
            drawCenteredString("但请记住，你只有一支！", 168, 78, graphics2d);
            drawCenteredString("并且，请不要误伤了友好的哞哞怪。",168,95,graphics2d);
        } else
        if(slideNum == 5)
        {
            drawCenteredString("当你看到亮闪闪的金子时，按下enter将其收入囊中，", 168, 48, graphics2d);
            drawCenteredString("回到出发地，", 168, 63, graphics2d);
            drawCenteredString("再按下enter，你就获得了胜利！", 168, 78, graphics2d);
        } else
        if(slideNum == 6)
        {
            drawCenteredString("你将使用键盘操作：", 168, 48, graphics2d);
            drawCenteredString("向左转 ...................LEFT", 168, 78, graphics2d);
            drawCenteredString("向右转 ...............RIGHT", 168, 93, graphics2d);
            drawCenteredString("向前走 ...............UP", 168, 108, graphics2d);
            drawCenteredString("获得金子 ...............ENTER", 168, 123, graphics2d);
            drawCenteredString("发射箭矢 .............SHIFT", 168, 138, graphics2d);
            drawCenteredString("爬出洞穴 ........CTRL", 168, 153, graphics2d);
            drawCenteredString("回到菜单 ............ESC", 168, 168, graphics2d);
        } else
        if(slideNum == 7)
        {
            drawCenteredString("关于", 168, 48, graphics2d);
            //drawCenteredString("Design: coding-fish", 168, 78, graphics2d);
            drawCenteredString("此游戏由脱发疗养院团队制作，", 168, 93, graphics2d);
            drawCenteredString("其中的音效和图片均下载自互联网，", 168, 108, graphics2d);
            drawCenteredString("如果其中的内容侵犯了您的权利，", 168, 123, graphics2d);
            drawCenteredString("请及时与我们联系，", 168, 138, graphics2d);
            drawCenteredString("同时，如果您有更好的意见和建议，", 168, 153, graphics2d);
            drawCenteredString("请告知我们，以便改进", 168, 168, graphics2d);
            drawCenteredString("联系邮箱：YouWillNeverContactUs@250.com", 168, 183, graphics2d);
        }
        enterSprite.render(graphics2d);
    }

    /**
     * 将字符串居中画出
     * @param s 将要被画的字符串
     * @param i 原横坐标
     * @param j 原纵坐标
     * @param g 绘图类，将要使用其drawString()方法将调整至居中的字符串画在GUI上
     */
    public void drawCenteredString(String s, int i, int j, Graphics g)
    {
        g.setFont(new Font("宋体", Font.BOLD, 8));
        FontMetrics fontmetrics = g.getFontMetrics();
        i -= fontmetrics.stringWidth(s) / 2;
        g.drawString(s, i, j);
    }

    private HUDSprite hudPanel;
    private HUDSprite hasGoldSprite;
    private HUDSprite hasBonusSprite;
    private HUDSprite hasArrowSprite;
    private HowToPlaySprite bgSprite;
    private HowToPlaySprite htp2Sprite;
    private HowToPlaySprite htp6Sprite;
    private HowToPlaySprite enterSprite;
    private AgentSprite agentSprite;
    private AgentSprite agentSprite2;
    private WumpusSprite wumpusSprite;
    private WumpusSprite supmuwSprite;
    private WumpusSprite supmuwSprite2;
    private WumpusSprite supmuwSprite3;
    private FXSprite entranceSprite;
    private FXSprite glitterSprite;
    private ArrayList breezeSprites;
    private ArrayList stenchSprites;
    private ArrayList mooSprites;
    private CaveNodeSprite testFloor;
    private CaveNodeSprite testFloor2;
    private CaveNodeSprite testFloor3;
    private CaveNodeSprite testFloor4;
    private CaveNodeSprite testFloor5;
    private CaveNodeSprite pitSprite;
    private int slideNum;
    private int timer1;
    private Keyboard keyboard;
    private ConfigWumpus config;
    private MidiPlayer midi;
}
