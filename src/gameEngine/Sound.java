package gameEngine;

import java.io.FileNotFoundException;
import java.net.URL;
import javax.sound.sampled.*;

/**
 * 该类用于直接播放声音，通过文件路径url，找到音频文件，并控制声音的播放与停止
 * 与MidiPlayer不同的是，这个类是用于播放游戏进行时的一些即时音效的
 */
public class Sound
{

    /**
     * 将表示文件路径的字符串s转化为路径url，并调用loadFile方法播放
     * @param s 文件路径
     */
    public Sound(String s)
    {
        try
        {
            URL url = getClass().getClassLoader().getResource(s);
            loadFile(url);
        }
        catch(Exception exception) { }
    }

    /**
     * 根据url加载音效文件
     * @param paramURL 音效文件的url
     */
    public void loadFile(URL paramURL) {
        try {
          this.stream = AudioSystem.getAudioInputStream(paramURL);

          this.info = new DataLine.Info(Clip.class, this.stream.getFormat());

          this.clip = ((Clip)AudioSystem.getLine(this.info));
          this.clip.open(this.stream);
        }
        catch (FileNotFoundException localFileNotFoundException)
        {
          System.out.println("File not found.");
        }
        catch (Exception localException)
        {
          System.out.println("Error loading sound " + paramURL);
        }
      }
    public void play()
    {
        play(true);
    }

    /**
     * 控制播放音效
     * @param flag true时播放音效
     */
    public void play(boolean flag)
    {
        try
        {
            if(flag)
                clip.setFramePosition(0);
            clip.start();
        }
        catch(Exception exception)
        {
            System.out.println("Sound error: Error playing sound");
        }
    }

    /**
     * 得到此音效在预加载时的播放位置
     * @return 此音效在预加载时的播放位置
     */
    public int getPosition()
    {
        return clip.getFramePosition();
    }

    /**
     * 测试用入口函数
     * @param args
     */
    public static void main(String args[])
    {
        Sound sound = new Sound("yumyumtaco.wav");
        Sound sound1 = new Sound("soundTest2.wav");
        Mixer mixer = AudioSystem.getMixer(null);
        int i = mixer.getMaxLines(sound.info);
        if(i == -1)
            System.out.println("Maximum lines: NOT_SPECIFIED");
        else
            System.out.println((new StringBuilder()).append("Maximum lines: ").append(mixer.getMaxLines(sound.info)).toString());
        do
        {
            sound.play();
            try
            {
                Thread.sleep(500L);
                sound1.play();
            }
            catch(Exception exception)
            {
                return;
            }
            System.out.println(sound.getPosition());
        } while(true);
    }

    private Clip clip;
    private AudioInputStream stream;
    public javax.sound.sampled.DataLine.Info info;
}
