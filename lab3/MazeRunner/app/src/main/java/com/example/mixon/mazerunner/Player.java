package com.example.mixon.mazerunner;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Player {
    private Bitmap bitmap;
    int x;
    int y;
    final int STEP = 20;
    private Rect sourceRect;
    private int frameNr;
    private int currentFrame;
    private long frameTicker;
    private int framePeriod;
    private int spriteWidth;
    private int spriteHeight;
    private int coordX;
    private int coordY;
    public Player(Bitmap bitmap, int x, int y, int fps, int frameCount){
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        coordX = 1;
        coordY = 1;
        currentFrame = 0;
        frameNr = frameCount;
        spriteWidth = bitmap.getWidth() ;/// frameCount;
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        framePeriod = 1000 / fps;
        frameTicker = 0l;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setX(int x) {
        this.x = x;
    }

    public void draw(Canvas canvas) {
        Rect destRect = new Rect(x, y, x + spriteWidth, y + spriteHeight);
        canvas.drawBitmap(bitmap, sourceRect, destRect, null);
    }


    public void handleAction(int eventX, int eventY, Grid grid) {
            //int[][] maze = grid.getGrid();
            while(coordX != grid.getPlayerX() || coordY != grid.getPlayerY()) {
                x += eventX * spriteWidth;
                y += eventY * spriteHeight;
                coordX += eventX;
                coordY += eventY;
            }
            /*update(System.currentTimeMillis());*/
    }
    public void update(long gameTime){
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            currentFrame++;
            if (currentFrame >= frameNr) {
                currentFrame = 0;
            }
        }
        this.sourceRect.left = currentFrame * spriteWidth;
        this.sourceRect.right = this.sourceRect.left + spriteWidth;

    }
}
