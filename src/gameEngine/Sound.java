package gameEngine;

import java.io.FileNotFoundException;
import java.net.URL;
import javax.sound.sampled.*;

public class Sound
{

    public Sound(String s)//将表示文件路径的字符串s转化为路径url，并调用loadFile方法播放
    {
        try
        {
            URL url = getClass().getClassLoader().getResource(s);
            loadFile(url);
        }
        catch(Exception exception) { }
    }

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

    public void stop()
    {
        clip.stop();
        clip.setFramePosition(0);
    }

    public void pause()
    {
        clip.stop();
    }

    public void loop(int i)
    {
        clip.setLoopPoints(0, -1);
        if(i == 0)
            clip.loop(-1);
        else
            clip.loop(i);
    }

    public int getPosition()
    {
        return clip.getFramePosition();
    }

    public boolean isRunning()
    {
        return clip.isRunning();
    }

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
