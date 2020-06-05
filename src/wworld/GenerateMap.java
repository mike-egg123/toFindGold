package wworld;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class GenerateMap {
    // Usage: call method "void generateMap(String path, String fileName)" to print the map information into specified path and file name. The last 3 lines of the output file correspond to "coin", "bad monster" and "good monster" in order.
    private int PROB;             // diffusion probability: 1 ~ 100
    private int MIN_POINT;        // minimum non-wall point number: 4 ~ 100
    private double P_RATIO;      // the ratio of swamp to non-wall: 0.0 ~ 1.0 (WARNING: the procedure may be endless when P_RATIO is close to 1.0)
    private boolean PRINT_MAP;  // if the procedure prints the map to the console
    public int Cx;
    public int Cy;
    public int Gx;
    public int Gy;
    public int Bx;
    public int By;

    public GenerateMap(int prob,int min_point,double p_RATIO, boolean print_map){
        this.P_RATIO = p_RATIO;
        this.MIN_POINT = min_point;
        this.PROB = prob;
        this.PRINT_MAP = print_map;
    }


    private class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Point[] getAround() {
            Point[] ret = new Point[4];
            ret[0] = new Point(x, y + 1);
            ret[1] = new Point(x + 1, y);
            ret[2] = new Point(x, y - 1);
            ret[3] = new Point(x - 1, y);
            return ret;
        }

    }

    private static int rand(int min, int max) {
        // min <= max
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public char[][] generateMap() {
        /*
         *  .   road
         *  W   wall
         *  P   swamp
         *  C   coin
         *  G   good monster
         *  B   bad monster
         */
        char[][] map = new char[12][12];
        Point C, G, B;
        while (true) {
            int cnt = 1;
            // init
            for (int i = 0; i <= 11; i++)
                for (int j = 0; j <= 11; j++) {
                    if (i == 1 && j == 1)
                        map[i][j] = '.';
                    else if (i == 0 || i == 11 || j == 0 || j == 11)
                        map[i][j] = 'W';
                    else
                        map[i][j] = ' ';
                }
            // generate '.' and 'W'
            Queue<Point> q = new LinkedList<Point>();
            q.offer(new Point(1, 1));
            while (!q.isEmpty()) {
                Point[] lis = q.poll().getAround();
                for (Point now : lis)
                    if (map[now.x][now.y] == ' ') {
                        if (rand(1, 100) <= PROB) {
                            map[now.x][now.y] = '.';
                            q.offer(now);
                            cnt++;
                        } else
                            map[now.x][now.y] = 'W';
                    }
            }
            // ensure enough '.'
            if (cnt < MIN_POINT)
                continue;
            // generate 'P'
            int pCnt = (int) (cnt * P_RATIO);
            for (int i = 1; i <= pCnt; i++) {
                while (true) {
                    int x = rand(1, 10), y = rand(1, 10);
                    if (!(x == 1 && y == 1) && map[x][y] == '.') {
                        map[x][y] = 'P';
                        break;
                    }
                }
            }
            // generate 'C'
            while (true) {
                int x = rand(1, 10), y = rand(1, 10);
                if (!(x == 1 && y == 1) && map[x][y] == '.') {
                    map[x][y] = 'C';
                    C = new Point(x, y);
                    break;
                }
            }
            // generate 'B'
            while (true) {
                int x = rand(1, 10), y = rand(1, 10);
                if (!(x == 1 && y == 1) && map[x][y] == '.') {
                    map[x][y] = 'B';
                    B = new Point(x, y);
                    break;
                }
            }
            // coin accessibility
            boolean[][] vis = new boolean[11][11];
            boolean access = false;
            vis[1][1] = true;
            q.clear();
            q.offer(new Point(1, 1));
            while (!q.isEmpty()) {
                Point[] lis = q.poll().getAround();
                for (Point now : lis) {
                    if (map[now.x][now.y] == 'C') {
                        access = true;
                        break;
                    }
                    if (map[now.x][now.y] == '.' && !vis[now.x][now.y]) {
                        vis[now.x][now.y] = true;
                        q.offer(now);
                    }
                }
                if (access)
                    break;
            }
            if (!access)
                continue;
            // generate 'G'
            while (true) {
                int x = rand(1, 10), y = rand(1, 10);
                if (!(x == 1 && y == 1) && map[x][y] == '.') {
                    map[x][y] = 'G';
                    G = new Point(x, y);
                    break;
                }
            }
            // break
            break;
        }
        // turn ' ' into 'W'
        for (int i = 1; i <= 10; i++)
            for (int j = 1; j <= 10; j++)
                if (map[i][j] == ' ')
                    map[i][j] = 'W';
        // print map
        if (PRINT_MAP) {
            for (int j = 10; j >= 1; j--) {
                for (int i = 1; i <= 10; i++)
                   System.out.print(map[i][j]);
                System.out.println("");
            }
        }
        // output to file
        //File file = new File(path, fileName);
        char finalMap[][] = new char[10][10];

        //FileWriter out = new FileWriter(file);
        for (int j = 10; j >= 1; j--) {
            for (int i = 1; i <= 10; i++) {
                char ch = map[i][j];
                if (ch == 'C' || ch == 'G' || ch == 'B')
                    ch = '.';
                finalMap[10 - j][i - 1] = ch;
            }
            //out.write("\n");
        }
//            out.write(C.x + " " + C.y + "\n");
//            out.write(B.x + " " + B.y + "\n");
//            out.write(G.x + " " + G.y);
//            out.close();
        this.Cx = C.x;
        this.Cy = C.y;

        this.Bx = B.x;
        this.By = B.y;

        this.Gx = G.x;
        this.Gy = G.y;

        return finalMap;
    }

    public static void main(String[] args) {
//        GenerateMap gEasy = new GenerateMap(80, 10, 0.1, true);
//        gEasy.generateMap("maps","map9");
    }
}