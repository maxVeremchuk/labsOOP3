package com.example.mixon.mazerunner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class MainGamePanel extends SurfaceView implements
        SurfaceHolder.Callback {

    private MainThread thread;
    private Player player;
    private Grid grid;
    private int startPointX;
    private int startPointY;
    private boolean isWin;
    private Display display;

    Bitmap exitBitmap;
    int exitWidth;
    int exitHeight;
    Rect exitSourceRect;
    Rect exitDestRect;

    Bitmap replayBitmap;
    int replayWidth;
    int replayHeight;
    Rect replaySourceRect;
    Rect replayDestRect;

    public MainGamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();

        exitBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.exit);
        exitWidth = exitBitmap.getWidth();
        exitHeight = exitBitmap.getHeight();
        exitSourceRect = new Rect(0, 0, exitWidth, exitHeight);
        exitDestRect = new Rect((display.getWidth()- exitWidth)/2, display.getHeight()/2 + exitHeight/2,
                (display.getWidth()+ exitWidth)/2, display.getHeight()/2 + exitHeight + exitHeight/2);

        replayBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.replay);
        replayWidth = replayBitmap.getWidth();
        replayHeight = replayBitmap.getHeight();
        replaySourceRect = new Rect(0, 0, replayWidth, replayHeight);
        replayDestRect = new Rect((display.getWidth()- replayWidth)/2, display.getHeight()/2 + replayHeight/2 + exitHeight + 30,
                (display.getWidth()+ replayWidth)/2, display.getHeight()/2 + replayHeight + replayHeight/2 + exitHeight + 30);

        isWin = false;
        grid = new Grid(BitmapFactory.decodeResource(getResources(), R.drawable.border), display.getWidth(), display.getHeight());
        grid.generateMaze();
        grid.setPlayer();
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.circle),
                60 + Grid.leftShift, 60 + Grid.topShift, 5 , 5);

        thread = new MainThread(getHolder(), this);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if(grid.getIsWin()) {
                if((int)event.getX() > exitDestRect.left  &&
                        (int)event.getX() < exitDestRect.right  &&
                             (int)event.getY() > exitDestRect.top  &&
                                 (int)event.getY() < exitDestRect.bottom){
                    thread.setRunning(false);
                    System.exit(0);
                    return true;
                }else if((int)event.getX() > replayDestRect.left  &&
                            (int)event.getX() < replayDestRect.right  &&
                                 (int)event.getY() > replayDestRect.top  &&
                                    (int)event.getY() < replayDestRect.bottom) {
                    grid.generateMaze();
                    grid.setPlayer();
                    player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.circle),
                            60 + Grid.leftShift, 60 + Grid.topShift, 5 , 5);
                        return true;
                }
            }
            startPointX = (int)event.getX();
            startPointY = (int)event.getY();
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {

            if(grid.getIsWin()){
                return true;
            }
            if(startPointY - event.getY() > 10 &&
                    Math.abs(startPointY - event.getY()) > Math.abs(startPointX - event.getX())){
                grid.move(0, -1);
                player.handleAction(0, -1, grid);
            } else if(startPointY - event.getY() < -10 &&
                    Math.abs(startPointY - event.getY()) > Math.abs(startPointX - event.getX())){
                grid.move(0, 1);
                player.handleAction(0, 1, grid);
            } else if(startPointX - event.getX() > 10 &&
                    Math.abs(startPointY - event.getY()) < Math.abs(startPointX - event.getX())){
                grid.move(-1, 0);
                player.handleAction(-1, 0, grid);
            }else if(startPointX - event.getX() < -10 &&
                    Math.abs(startPointY - event.getY()) < Math.abs(startPointX - event.getX())){
                grid.move(1, 0);
                player.handleAction(1, 0, grid);
            }

            return true;
        }
            return false;
    }

    protected void render(Canvas canvas) {
        if(canvas == null){
            return;
        }
        canvas.drawColor(Color.BLACK);

        if(grid.getIsWin()) {
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPaint(paint);

            paint.setColor(Color.YELLOW);
            paint.setTextSize(200);
            canvas.drawText("Gratz", display.getWidth()/2 - 200, display.getHeight()/2, paint);


            canvas.drawBitmap(exitBitmap, exitSourceRect, exitDestRect, null);
            canvas.drawBitmap(replayBitmap, replaySourceRect, replayDestRect, null);
        }else{
            player.draw(canvas);
            grid.draw(canvas);

        }

    }
    public void update(){
        player.update(System.currentTimeMillis());
    }
}