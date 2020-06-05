package gameEngine;

import java.io.*;
import javax.sound.midi.*;

public class MidiPlayer
{

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

    public MidiPlayer(String s)
    {
        this();
        load(s);
    }

    public void load(String s)//将文件路径s转化为字符串url，并添加到播放序列
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

    public void unload()
    {
        stop();
        seqr.close();
        midiFile = null;
        loaded = false;
    }

    public void setMidiID(String s)
    {
        midiID = s;
    }//接口，但没有用到

    public String getMidiID()//接口，但没有用到
    {
        return new String(midiID);
    }

    public void play(boolean flag)
    {
        if(flag)
            seqr.setTickPosition(seqr.getLoopStartPoint());
        seqr.start();
    }

    public void stop()
    {
        if(seqr.isOpen())
            seqr.stop();
    }

    public boolean isRunning()
    {
        return seqr.isRunning();
    }

    public float getTempo()
    {
        return seqr.getTempoInBPM();
    }

    public void loop(int i)
    {
        loop(i, 0L, -1L);
    }

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

    public void resetTempo()
    {
        changeTempo(defaultTempo);
    }

    public void changeTempo(float f)
    {
        double d = f / seqr.getTempoInBPM();
        seqr.setLoopStartPoint((long)((double)seqr.getLoopStartPoint() * d));
        seqr.setLoopEndPoint((long)((double)seqr.getLoopEndPoint() * d));
        seqr.setTempoInBPM(f);
    }

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
