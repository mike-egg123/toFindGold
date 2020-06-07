import gameEngine.*;
import java.awt.*;

public class MenuLevel extends Level
{

    public MenuLevel(Component component)
    {
        super(component);
        MasterPanel masterpanel = (MasterPanel)component;
        keyboard = masterpanel.getKeyboard();
        config = masterpanel.getConfig();
        midi = masterpanel.getMidiPlayer();
        midi.load("sounds/bg.mid");
        midi.play(true);
        midi.loop(-1);
        menuChoice = 0;
        menuNum = 0;
    }

    public void loadData()
    {
        MenuBackgroundsSprite.loadImages();
        MenuSprite.loadImages(imgLoader);
        imgLoader.waitForAll();
        menuBg = new MenuBackgroundsSprite(0.0D, 48D);
        title = new MenuSprite(0.0D, 24D, 't');
        aiGame = new MenuSprite(48D, 240D, 'a');
        normalGame = new MenuSprite(48D, 264D, 'n');
        howToPlay = new MenuSprite(192D, 240D, 'h');
        quit = new MenuSprite(192D, 264D, 'q');
        loadMap = new MenuSprite(48D, 252D, 'l');
        randomMap = new MenuSprite(192D, 252D, 'r');
    }

    public void clean()
    {
        super.clean();
        MenuBackgroundsSprite.clean();
        MenuSprite.clean();
        keyboard = null;
        config = null;
    }

    public void timerLoop()
    {
        MasterPanel masterpanel = (MasterPanel)parent;
        aiGame.isSelected = false;
        normalGame.isSelected = false;
        howToPlay.isSelected = false;
        quit.isSelected = false;
        aiGame.isVisible = false;
        normalGame.isVisible = false;
        howToPlay.isVisible = false;
        quit.isVisible = false;
        randomMap.isVisible = false;
        loadMap.isVisible = false;
        if(keyboard.isTyped(config.VK_ESC))
        {
            masterpanel.startGame();
            return;
        }
        if(menuNum == 0)
        {
            aiGame.isVisible = true;
            normalGame.isVisible = true;
            howToPlay.isVisible = true;
            quit.isVisible = true;
            if(keyboard.isTyped(config.VK_UP))
                menuChoice--;
            if(keyboard.isTyped(config.VK_DOWN))
                menuChoice++;
            if(keyboard.isTyped(config.VK_LEFT))
                menuChoice -= 2;
            if(keyboard.isTyped(config.VK_RIGHT))
                menuChoice += 2;
            if(menuChoice < 0)
                menuChoice = 0;
            if(menuChoice > 3)
                menuChoice = 3;
            if(menuChoice == 0)
                aiGame.isSelected = true;
            if(menuChoice == 1)
                normalGame.isSelected = true;
            if(menuChoice == 2)
                howToPlay.isSelected = true;
            if(menuChoice == 3)
                quit.isSelected = true;
            if(menuChoice == 0 && keyboard.isTyped(config.VK_ENTER))
            {
                menuNum = 1;
                menuChoice = 0;
                masterpanel.gameMode = 'A';
            }
            if(menuChoice == 1 && keyboard.isTyped(config.VK_ENTER))
            {
                menuNum = 1;
                menuChoice = 0;
                masterpanel.gameMode = 'H';
            }
            if(menuChoice == 2 && keyboard.isTyped(config.VK_ENTER))
            {
                masterpanel.changeCurrentLevel("HowToPlay");
                return;
            }
            if(menuChoice == 3 && keyboard.isTyped(config.VK_ENTER))
            {
                System.out.println("closing the game");
                masterpanel.endGame();
                return;
            }
        } else
        if(menuNum == 1)
        {
            loadMap.isVisible = true;
            randomMap.isVisible = true;
            loadMap.isSelected = false;
            randomMap.isSelected = false;
            if(keyboard.isTyped(config.VK_LEFT))
                menuChoice--;
            if(keyboard.isTyped(config.VK_RIGHT))
                menuChoice++;
            if(menuChoice < 0)
                menuChoice = 0;
            if(menuChoice > 1)
                menuChoice = 1;
            if(menuChoice == 0)
                loadMap.isSelected = true;
            if(menuChoice == 1)
                randomMap.isSelected = true;
            if(keyboard.isTyped(config.VK_CLIMB))
            {
                menuNum = 0;
                menuChoice = 0;
            }
            if(menuChoice == 0 && keyboard.isTyped(config.VK_ENTER))
            {
//                FileDialog filedialog = new FileDialog(masterpanel.parentFrame);
//                filedialog.show();
//                if(filedialog.getDirectory() != null)
//                {
//                    masterpanel.loadFile = (new StringBuilder()).append(filedialog.getDirectory()).append(filedialog.getFile()).toString();
//                    //System.out.println((new StringBuilder()).append("Loading map: ").append(masterpanel.loadFile).toString());
//                    masterpanel.changeCurrentLevel("MainLevel");
//                    return;
//                }
                masterpanel.loadFile = "easy";
                masterpanel.changeCurrentLevel("MainLevel");
                return;
            }
            if(menuChoice == 1 && keyboard.isTyped(config.VK_ENTER))
            {
                masterpanel.loadFile = "hard";
                masterpanel.changeCurrentLevel("MainLevel");
                return;
            }
        }
        animateAll();
        imgLoader.waitForAll();
    }

    private void animateAll()
    {
        menuBg.animate(imgLoader);
        title.animate(imgLoader);
        aiGame.animate(imgLoader);
        normalGame.animate(imgLoader);
        howToPlay.animate(imgLoader);
        quit.animate(imgLoader);
        loadMap.animate(imgLoader);
        randomMap.animate(imgLoader);
    }

    public void render(Graphics2D graphics2d)
    {
        MasterPanel masterpanel = (MasterPanel)parent;
        graphics2d.setBackground(new Color(8, 8, 16));
        graphics2d.clearRect(0, 0, 340, 288);
        menuBg.render(graphics2d);
        title.render(graphics2d);
        //graphics2d.setColor(new Color(200, 0, 50));
        //graphics2d.drawString("Created by coding-fish in 2019/08", 10, 286);//加个水印时间戳
        aiGame.render(graphics2d);
        normalGame.render(graphics2d);
        howToPlay.render(graphics2d);
        quit.render(graphics2d);
        loadMap.render(graphics2d);
        randomMap.render(graphics2d);
    }

    private MenuBackgroundsSprite menuBg;
    private MenuSprite title;
    private MenuSprite aiGame;
    private MenuSprite normalGame;
    private MenuSprite howToPlay;
    private MenuSprite quit;
    private MenuSprite loadMap;
    private MenuSprite randomMap;
    private int menuChoice;
    private int menuNum;
    private Keyboard keyboard;
    private ConfigWumpus config;
    private MidiPlayer midi;
}
