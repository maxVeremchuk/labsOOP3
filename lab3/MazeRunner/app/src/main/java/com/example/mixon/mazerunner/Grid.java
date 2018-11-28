package com.example.mixon.mazerunner;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Grid {
    private Bitmap bitmap;
    private int spriteWidth;
    private int spriteHeight;
    private Rect sourceRect;
    private int playerX;
    private int playerY;
    private int exit;
    private boolean isWin;
    final int SIZE = 20;
    final int WALLSIZE = 20;
    public static int leftShift;
    public static int topShift;
    int rows;
    int cols;
    int[][] grid;
    Grid(Bitmap bitmap, int width, int height){
        this.bitmap = bitmap;
        isWin = false;
        if(bitmap != null) {
            spriteWidth = bitmap.getWidth();
            spriteHeight = bitmap.getHeight();
            sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        }
        else{
            spriteWidth = 20;
            spriteHeight = 20;
        }
        this.cols = (height)/spriteHeight;
        this.rows = (width)/ spriteWidth;
        if(rows % 2 == 0){
            rows--;
        }

        if(cols % 2 == 0){
            cols--;
        }
        leftShift = (width - rows*spriteWidth)/2;
        topShift = (height - cols*spriteHeight)/2;
        grid = new int[this.rows][this.cols];
    }
    public int[][] getGrid(){
        return grid;
    }
    public int getCols(){
        return cols;
    }
    public int getRows(){
        return rows;
    }
    public void generateMaze(){
        isWin = false;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                grid[i][j] = 1;
            }
        }
        for(int i = 1; i < rows; i+=2){
            for(int j = 1; j < cols; j+= 2){
                grid[i][j] = 2;
            }
        }

        int visitedCells = 1;

        int x = (int)(Math.random() * (rows - 1));
        if(x % 2 == 0){
            x++;
        }
        int y = (int)(Math.random() * (cols - 1));
        if(y % 2 == 0){
            y++;
        }
        int dir;
        int dirX = 0, dirY = 0;
        grid[x][y] = 0;
        while(visitedCells < (rows / 2) * (cols / 2)){
            dir = (int)(Math.random() * 4);
            switch(dir){
                case 0: {
                    dirX = -2;
                    dirY = 0;
                    break;
                }
                case 1: {
                    dirX = 2;
                    dirY = 0;
                    break;
                }
                case 2: {
                    dirX = 0;
                    dirY = -2;
                    break;
                }
                case 3: {
                    dirX = 0;
                    dirY = 2;
                    break;
                }
            }
            if(x + dirX >= rows || x + dirX < 0){
                dirX *= (-1);
            }
            if(y + dirY >= cols || y + dirY < 0){
                dirY *= (-1);
            }
            if(grid[x + dirX][y + dirY] == 2){
                grid[x + dirX][y + dirY] = 0;
                grid[x + (dirX / 2)][y + (dirY / 2)] = 0;
                visitedCells++;
            }
            x += dirX;
            y += dirY;
        }
        exit = (int)(Math.random() * (rows - 1));
        if(exit % 2 ==0){
            exit++;
        }
        grid[exit][cols - 1] = 0;
    }
    public boolean getIsWin(){
        return isWin;
    }
    public void setPlayer(){
        playerX = 1;
        playerY = 1;
        //grid[playerX][playerY] = 3;
    }
    public void move(int x, int y){
       if(x == 0) {
           if((grid[playerX][playerY + y] == 0)){
               playerY += y;
               if(playerY == cols - 1 && playerX == exit){
                   isWin = true;
                   return;
               }
           }
          while(((grid[playerX + 1][playerY] == 1) && grid[playerX - 1][playerY] == 1)
                  && (grid[playerX][playerY + y] == 0)){
              playerY += y;
              if(playerY == cols - 1 && playerX == exit){
                  isWin = true;
                  break;
              }
          }
       }else{
           if((grid[playerX + x][playerY] == 0)) {
               playerX += x;
           }
           while((grid[playerX][playerY + 1] == 1) && grid[playerX][playerY - 1] == 1
                   && (grid[playerX + x][playerY] == 0)){
               playerX += x;
           }
       }
    }
    public int getPlayerY(){
        return playerY;
    }
    public int getPlayerX(){
        return playerX;
    }
    public void draw(Canvas canvas) {

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(grid[i][j] == 1) {
                    Rect destRect = new Rect(leftShift + i*spriteWidth, topShift + j *spriteHeight,
                            leftShift + i*spriteWidth + spriteWidth, topShift + j*spriteHeight + spriteHeight);
                    canvas.drawBitmap(bitmap, sourceRect, destRect, null);
                }
            }
        }
    }
}
