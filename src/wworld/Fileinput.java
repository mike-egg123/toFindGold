package wworld;

import java.io.File;
import java.util.Scanner;

public class Fileinput
{

    public Fileinput()
    {
        scanner = new Scanner(System.in);
    }

    public Fileinput(String s)
    {
        try
        {
            scanner = new Scanner(new File(s));
        }
        catch(Exception exception) { }
    }

    public String[] getParsedLine(String s)
    {
        return scanner.nextLine().split(s);
    }

    public int[] getParsedInts(String s)
    {
        String as[] = scanner.nextLine().split(s);
        int ai[] = new int[as.length];
        for(int i = 0; i < as.length; i++)
            ai[i] = Integer.parseInt(as[i]);

        return ai;
    }

    public String getLine()
    {
        return scanner.nextLine();
    }

    public int getInt()
    {
        String s = scanner.nextLine();
        Integer integer = new Integer(s);
        return integer.intValue();
    }

    public double getDouble()
    {
        String s = scanner.nextLine();
        Double double1 = new Double(s);
        return double1.doubleValue();
    }

    public boolean hasNext()
    {
        return scanner.hasNextLine();
    }

    private Scanner scanner;
}
