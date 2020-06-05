import java.io.*;

public class ConfigWumpus
    implements Serializable
{

    public ConfigWumpus()
    {
        try
        {
            FileInputStream fileinputstream = new FileInputStream("YoBro.config");
            ObjectInputStream objectinputstream = new ObjectInputStream(fileinputstream);
            ConfigWumpus configwumpus = (ConfigWumpus)objectinputstream.readObject();
            objectinputstream.close();
            fileinputstream.close();
            VK_UP = configwumpus.VK_UP;
            VK_DOWN = configwumpus.VK_DOWN;
            VK_LEFT = configwumpus.VK_LEFT;
            VK_RIGHT = configwumpus.VK_RIGHT;
            VK_SHOT = configwumpus.VK_SHOT;
            VK_CLIMB = configwumpus.VK_CLIMB;
            VK_ENTER = configwumpus.VK_ENTER;
            VK_ESC = configwumpus.VK_ESC;
            System.out.println("Config file read successful");
        }
        catch(Exception exception)
        {
            VK_UP = 38;
            VK_DOWN = 40;
            VK_LEFT = 37;
            VK_RIGHT = 39;
            VK_SHOT = 16;
            VK_CLIMB = 17;
            VK_ENTER = 10;
            VK_ESC = 27;
            //System.out.println("No Config file exists. Initialized default configurations.");
        }
    }

    public void saveConfig()
    {
        try
        {
            FileOutputStream fileoutputstream = new FileOutputStream("YoBro.config");
            ObjectOutputStream objectoutputstream = new ObjectOutputStream(fileoutputstream);
            objectoutputstream.writeObject(this);
            objectoutputstream.close();
            //System.out.println("Config file write success.");
        }
        catch(Exception exception)
        {
            System.out.println((new StringBuilder()).append("Config file write failed: \n").append(exception.getMessage()).append("\n").append(exception.getStackTrace()).toString());
        }
    }

    public int VK_UP;
    public int VK_DOWN;
    public int VK_LEFT;
    public int VK_RIGHT;
    public int VK_SHOT;
    public int VK_CLIMB;
    public int VK_ENTER;
    public int VK_ESC;
}
