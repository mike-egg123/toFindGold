package gameEngine;

import java.io.*;
import javax.sound.midi.*;

/**
 * 媒体播放器，在每个游戏界面上调用，打开与当前界面对应文件的声音素材（背景音乐），
 * 并加入播放序列，当加入失败时在控制台输出错误信息
 */
public class MidiPlayer
{

    /**
     * 初始化，指示加载完成的变量给false，然后给seqr加载一个midi文件
     */
    public MidiPlayer()
    {
        loaded = false;
        try
        {
            seqr = MidiSystem.getSequencer();
        }
        catch(Exception exception)
        {
            System.out.println("MIDI error: It appears your system doesn't have a MIDI device or your device is not working.");
        }
    }

    /**
     * 带参构造函数，实际使用的是这个
     * @param s 根据此字符串，加载对应路径的midi文件
     */
    public MidiPlayer(String s)
    {
        this();
        load(s);
    }

    /**
     * 将文件路径s转化为字符串url，并添加到播放序列
     * @param s 如上描述，就是一个文件路径
     */
    public void load(String s)
    {
        unload();
        try
        {
            java.net.URL url = getClass().getClassLoader().getResource(s);
            seq = MidiSystem.getSequence(url);
            seqr.open();
            seqr.setSequence(seq);
            loaded = true;
            defaultTempo = seqr.getTempoInBPM();
        }
        catch(IOException ioexception)
        {
            System.out.println((new StringBuilder()).append("MIDI error: Problem occured while reading ").append(midiFile.getName()).append(".").toString());
        }
        catch(InvalidMidiDataException invalidmididataexception)
        {
            System.out.println((new StringBuilder()).append("MIDI error: ").append(midiFile.getName()).append(" is not a valid MIDI file or is unreadable.").toString());
        }
        catch(Exception exception)
        {
            System.out.println("MIDI error: Unexplained error occured while loading midi.");
        }
    }

    /**
     * 关闭加载进来的midi文件
     */
    public void unload()
    {
        stop();
        seqr.close();
        midiFile = null;
        loaded = false;
    }


    /**
     * 播放音乐
     * @param flag true代表着播放
     */
    public void play(boolean flag)
    {
        if(flag)
            seqr.setTickPosition(seqr.getLoopStartPoint());
        seqr.start();
    }

    /**
     * 将序列中正在播放的音频停下
     */
    public void stop()
    {
        if(seqr.isOpen())
            seqr.stop();
    }

    /**
     * 调用了下面重载的的loop
     * @param i 表示循环播放次数
     */
    public void loop(int i)
    {
        loop(i, 0L, -1L);
    }

    /**
     * 控制循环播放
     * @param i 循环播放次数
     * @param l 起点
     * @param l1 终点
     */
    public void loop(int i, long l, long l1)
    {
        if(l < 0L)
            l = 0L;
        if(l1 > seqr.getSequence().getTickLength())
            l1 = -1L;
        else
        if(l1 == 0L)
            l1 = -1L;
        if(l >= l1 && l1 != -1L)
            l = l1 - 1L;
        seqr.setLoopStartPoint(l);
        seqr.setLoopEndPoint(l1);
        if(i == -1)
            seqr.setLoopCount(-1);
        else
            seqr.setLoopCount(i);
    }

    /**
     * 测试用入口函数
     * @param args
     */
    public static void main(String args[])
    {
        MidiPlayer midiplayer = new MidiPlayer("lazarus.mid");
        midiplayer.play(true);
        midiplayer.loop(-1);
        do
            ;
        while(true);
    }

    private Sequence seq;
    private Sequencer seqr;
    private File midiFile;
    private String midiID;
    private boolean loaded;
    private float defaultTempo;
}
