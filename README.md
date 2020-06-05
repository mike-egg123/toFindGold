# 掘金小游戏 (To Find Gold)

## 目录
- 游戏简介
- 运行环境
- 运行方法
- 游戏玩法
- 项目目录结构
    - gameEngine
    - wworld
    - 游戏主进程
- 项目的亮点与不足
- 声明

## 游戏简介
该项目是受到扫雷游戏启发，用Java语言开发出的一款小游戏。游戏的主要任务是探索洞穴并找到黄金，最后携带黄金安全离开洞穴。
该游戏主要分为两种模式，AI自动运行，和玩家亲自操作。每种模式下均可调用已有文档中的地图，或者探索随机生成的地图。

##运行环境
该程序可以在配置java运行环境的Windows10系统中正常运行（开发时使用的软件为IntelliJ IDEA）。

##运行方法
在IDEA中打开ToFindGold文件夹，并运行src子文件夹下的Main类，可以打开游戏界面，之后用键盘控制即可。如需加载地图文档，请浏览maps子文件夹下寻找。

##游戏玩法
游戏为全键盘操作，左右方向键控制转身，上方向键前进，回车键抓取黄金，ctrl键离开洞穴，esc键返回主菜单。

在游戏中，开局只有出发点所在格子为可见，玩家依靠纯键盘操作，控制精灵的转向和前进，探索周围黑色未知区域。在地图中，会随机分布有恶魔(Wumpus)，天使(Supmuw)，和陷阱(Pit），在它们周围会有提示性的动画效果。玩家根据动画推断未知区域，运用智慧避开陷阱找到黄金，则游戏成功。若掉入陷阱，被恶魔吃掉，或空手离开洞穴，则游戏失败。

游戏内部还附加了一些玩法。可通过游戏初始界面的how to play进一步了解。

## 项目目录结构
该项目内有四个文件夹，.idea，maps，out，和src。

由于该项目开发使用IDEA作为IDE

该项目采用了面向对象的程序设计，将游戏中不同的对象和行为分别用不同的类去存储和控制。下面重点介绍 **src** 中各文件夹及各个类的作用。

### gameEngine
顾名思义，该文件夹下的所有类用于驱动游戏正常运行。
	
> 1.ColorFilters
> 
> 由于游戏素材中的大量图片为正方形，而游戏界面中只需显示对象本身，在这个类中主要构造了 setTransparentColor 和 setSemiTransparency 方法。首先约定所有图片素材的背景均为RGB下的**0xff00ff**，然后在第一个方法中将其设为透明色，在后一个方法中将其设置为透明。对每一张图片分别调用这种方法，可以实现图片只显示中央的人物而不显示背景颜色。
> 
> 2.ImageBlitter
> 
> 该类用于图像传送，为节约空间并实现动画效果，将一张图片素材分成多个大小相同，边框宽2像素的格子，在里面分别绘制动态效果的每一帧图像，每次刷新游戏界面时展示某一格子上的一幅图像。
> 该类构造了核心方法cropTiled，输入图像以及裁剪区域的左上角和右下角坐标，利用awt库中的createImage方法，返回需要显示的图像部分。
> 
> 3.ImageLoader
> 
> 调用媒体跟踪器，加载及显示图像
> 
> 4.Keyboard
> 
> 该游戏全程通过键盘控制，因此只写了键盘事件监听器，内部的keyTyped（int i）方法用于监视键盘上以i标识的键是否有键入。对游戏中有效按键，事先定义好一个代表的数值，当监测到某个键被键入，则返回true，传递给相应的方法触发游戏事件。
> 
> 
> 5.Level
> 
> 这是一个抽象类，分别被开始界面MainLevel，游戏界面MasterLevel，帮助界面HowToPlayLevel被继承。里面的抽象方法loaddata用于加载数据进入游戏界面，timerloop用于不断循环执行动作，render用于图像渲染。这些方法均在Level的子类中被重写。
> 
> 6.MidiPlayer
> 
> 媒体播放器，在每个游戏界面上调用，打开与当前界面对应文件的声音素材，并加入播放序列，当加入失败时在控制台输出错误信息。
> 
> 7.Sound
> 
> 该类用于直接播放声音，通过文件路径url，找到音频文件，并控制声音的播放与停止。
> 
> 8.Sprite
> 
> 这是一个抽象类，封装了与游戏中角色形象有关的位置参数，运动参数等信息。在之后每种角色的子类中进行了重写。

### wworld
为与单词world区分开，采取双写首字母命名。这个文件夹下的所有类用于构建游戏中的世界，以及操纵世界中各角色的行为。

> 1.Action
> 
> 该类用于约定人物行为的代表数字，在调试过程中发挥了作用，最终项目没有用到。
> 
> 2.Agent
> 
> 这个类用于控制AI 模式下角色的行为，包含实现AI 游戏的核心算法。下面就AI 涉及到的核心方法进行简单介绍：
> 
> - Agent:初始化游戏参数，包括人物是否已经获得黄金，手中是否还有武器。还有未探索格子的安全概率，这个数据将作为人物下一步决策的重要依据。
> - act:实现游戏角色的所有动作，包括转身，射箭，拿起黄金，离开洞穴。
> - move:通过角色探索格子获得的信息，对人物状态（是否存活，是否已找到黄金等），场景状态（更新未知区域的安全概率，已碰到地图的边缘等）进行更改。
> - getNextAction：依据现有状态，对人物下一步行动作出决策，若已经找到黄金，则将按最短安全路线离开洞穴；若尚未找到，则根据人物方向探索下一块未知格子；若已经到达出口，则该局游戏结束并判断胜负；若上一步判断出怪兽位置，则发生射箭行为。
> - checkAreaForPits:通过对每个格子上下左右四个格子的检查，判断出该处是否有陷阱，若已经确定相对的两个格子有陷阱的闪光，则可以保证它们之间绝对有陷阱，需要标记该处为不可到达。
> - checkAreaForWumpus:通过对每个格子上下左右四个方向格子的检查，若观察到相对两格有毒气，则它们之间绝对有恶魔，需执行射箭操作。
> - checkAreaForSupmuw:由于天使周围九个格子都有动画效果，因此难以判断出准确位置，人物在安全的情况下尽可能多的遍历各点，争取得到天使的**Bonus**。
> - shortestSafePathToUnvisited:利用图的广度优先搜索，结合对位置区域安全概率的预测，找到掘金最短路径。
> - shortestSafePathToPoint:利用图的广度优先搜索，在已经探索过的区域中找到回程最短路径。
> - aStarTravel:这里是实现智能决策的算法，判断各点安全概率，得到人物前进的优先队列。
> 
> 3.CaveNode
> 
> 这个类封装了地图中每个格子的属性，包括安全概率，是否已经到达，是否不可到达等。
> 
> 4.Fileinput
> 
> 结合键盘监听器使用，不断获取玩家的键盘输入。
> 
> 5.Gold
> 
> 金币类，记录金币的位置和状态。
> 
> 6.PriorityCaveNode
> 
> 优先队列类，当存在两条决策路径时，比较得到较优解。
> 
> 7.Supmuw
> 
> 天使类，记录天使的状态。
> 
> 8.WuplusEnvironment
> 
> - initRandonEnvironment:随机生成一张地图，并对各参数进行初始化。
> - initLoadedEnvironment：加载文档中的地图，在屏幕上生成相应界面。
> - get4AdjacentNode:分别将周围四个方向加入队列，尝试得到下一步决策。
> - get8AdjacentNode：分别将周围八个方向加入队列，从而判断出中央未知点的安全概率。
> 
> 9.Wumpus
> 
> 恶魔类，记录恶魔的位置和状态。

### **游戏主进程**

> 1.AgentSprite
> 
> 实现游戏中精灵的动画效果，如行走效果，抓取金币效果，射箭效果，死亡效果。
> 
> 2.CaveNodeSprite
> 
> 实现游戏中洞穴内部的显示，如墙壁，陷阱。
> 
> 3.ConfigWumpus
> 
> 用于加载游戏的配置文件。
> 
> 4.FXSprite
> 
> 特效类，通过调用前面构造的图像显示有关的类，利用静态多帧图片，完成地图中陷阱，恶魔，天使等动画效果的动态显示。
> 
> 5.HowToPlayLevel
> 
> 帮助页面，控制多页帮助的图文显示。
> 
> 6.HowToPlaySprite
> 
> 控制帮助页面中精灵动态图片的显示。
> 
> 7.HUDSprite
> 
> 平行板类，控制游戏中界面左侧背包中当前物品的显示。
> 
> 8.Main
> 
> 游戏主函数。继承了JFrame类，在屏幕上显示弹窗并设置窗口监视器。
> 
> 9.MainLevel
> 
> 观察游戏进程，监视游戏中的关键事件（包括恶魔和天使的状态，金币的状态，是否拿到Bonus奖励，游戏胜负），并在每一帧之后刷新界面显示，进行图像渲染。
> 
> 10.MasterPanel
> 
> JPanel的扩展类，控制游戏面板上的行为监听器。
> 
> 11.MenuBackgroundsSprite
> 
> 设置游戏菜单界面背景。
> 
> 12.MenuLevel
> 
> 菜单类，控制菜单界面的图像、声音、四个选项，并显示作者的水印。
> 
> 13.MenuSprite
> 
> 对菜单界面的选择作出第一步相应，传递一个字符给相应的方法，游戏进入对应新界面。
> 
> 14.WumpusFileOpener
> 
> 用于调试，没有在最终项目中用到。
> 
> 15.WumpusSprite
> 
> 在游戏中恶魔的动态效果较多，因此专门用一个类控制恶魔的显示。通过裁剪素材格子的对应位置，每次只显示一帧。

## 项目的亮点与不足
本游戏从扫雷得到启发，但玩法有了很大的改进，由动画效果提示危险，且任务驱动、Bonus奖励使得游戏更富趣味性。
本游戏试探性地增加了AI 玩法，玩家可以在上帝视角观察算法自主过关，核心算法中采取了对地图上每个点的安全概率进行预测，进而决策下一步的方法，是对人工智能的一种简单尝试。

但由于作者学识尚浅，开发时间也较短，本游戏仍存在几个主要问题。
随机生成地图可能会出现无解情况，作者用图的搜索进行了改进的尝试，但由于坐标引用出现问题且未得到解决，这个bug留了下来。在日后的学习种作者仍将跟踪改进，相信这个问题会在不久得到克服。
AI算法仍有可改进之处。作者进行了数十次尝试，发现AI不能保证每次必胜，且面对几种特殊情况（如恶魔挡在单行道上，毒气只能探测到一块，精灵不会作出射箭动作）决策不够机智。概率算法本身还存在很大的优化空间。
项目调试过程中构造了许多临时使用的方法，遗留在了不同类的字里行间，没有及时删除干净，不过对生成的可执行文件没有影响。

## 声明
本项目中用到的图片、声音素材，部分来源于网络，但作者已通过向网站充值购买了使用权。
