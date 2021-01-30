package satoru.life;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Grid {

    private int[][] grid;

    private static final int[][] GLIDER_INT = {{1,1,1}, {1,0,0}, {0,1,0}};
    public static final Grid GLIDER = new Grid(GLIDER_INT);

    public Grid(int[][] grid){
        this.grid = grid;
    }

    public Grid(int row, int column){
        this.grid = new int[row][column];
    }

    @Override
    public String toString(){
        var sb = new StringBuilder();
        for(int[] a : grid){
            sb.append("|");
            for(int b : a){
                sb.append(b).append(' ');
            }
            sb.append("|").append(System.lineSeparator());
        }
        return sb.toString();
    }

    public int row(){
        return grid.length;
    }

    public int col(){
        return  grid[0].length;
    }

    public void set(int row, int col, int el){
        if(row < grid.length && col < grid[0].length
                && 0 <= row && 0<= col){
            grid[row][col] = el;
        }
    }

    public void blit(int posr, int posc, Grid g){
        for(int i=0; i< g.row(); i++){
            for(int j=0; j< g.col(); j++){

                set(posr+i, posc+j, g.get(i,j));
            }
        }
    }

    public int get(int row, int col){
        if(row < grid.length && col < grid[0].length
        && 0 <= row && 0<= col){
            return grid[row][col];
        }else{
            return 0;
        }
    }

    void convolve(){
        int[][] ans = new int[grid.length][grid[0].length];

        for(int i=0; i< grid.length; i++){
            for(int j=0; j< grid[0].length; j++){

                for(int x=-1; x<=1; x++){
                    for(int y=-1; y<=1; y++){
                        if(x==0 && y==0){
                            ans[i][j] += get(i+x, j+y) * 9;
                        }else{
                            ans[i][j] += get(i+x, j+y);
                        }
                    }
                }
            }
        }
        grid = ans;
        return;
    }

    void liveOrDeath(){
        for(int i=0; i< grid.length; i++){
            for(int j=0; j< grid[0].length; j++){
                var e = grid[i][j];

                if(e == 3 || e == 11 || e ==12){
                    grid[i][j] = 1;
                }else{
                    grid[i][j] = 0;
                }
            }
        }
    }

    public void lifeTrans(){
        convolve();
        liveOrDeath();
    }

    public void lifeTrans(int times){
        for(int i=0; i<times; i++){
            lifeTrans();
        }
    }

    public void exportAsImage(String path, String exst){
        var img = new BufferedImage(grid[0].length, grid.length, BufferedImage.TYPE_BYTE_BINARY);

        for(int i=0; i< grid.length; i++){
            for(int j=0; j< grid[0].length; j++){
               img.setRGB(j,i,
                       grid[i][j]==1 ? 0 : Integer.MAX_VALUE);
            }
        }
        File f = new File(path + '.' + exst);
        try {
            ImageIO.write(img, exst, f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportAsImages(String path, int time){
        for(int i=0; i<time; i++){
            exportAsImage(path + i, "png");
            lifeTrans();
        }
    }
}
