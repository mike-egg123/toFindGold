package wworld;

public class Map {
    final int mapNums = 8;
    final String[] mapStrings = new String[mapNums];
    private static Map gameMap = new Map();
    private Map(){
        for(int i = 0;i < mapNums;i++){
            mapStrings[i] = "maps/map" + (i + 1);
        }
    }
    public String[] getRealMapStrings(int begin,int end){
        String[] realStrings = new String[end - begin];
        int cnt = 0;
        for(int i = begin;i < end;i++){
            realStrings[cnt++] = mapStrings[i];
        }
        return realStrings;
    }

    public static Map getGameMap() {
        return gameMap;
    }
}
