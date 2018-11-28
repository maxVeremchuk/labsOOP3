package com.example.mixon.mazerunner;

import android.graphics.Bitmap;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.*;

public class GridTest {
    @Test
    public void checkMazeGenaration(){
        Grid grid = new Grid(null,1225, 1925);
        grid.generateMaze();
        int[][] maze = grid.getGrid();
        int counterOf2 = 0;
        for(int i = 0; i < grid.getRows(); i++){
            for(int j = 0; j < grid.getCols(); j++){
                System.out.print(maze[i][j]);
                if(maze[i][j] == 2){ // 2 - state - not checked
                    counterOf2++;
                }
            }
            System.out.println();
        }
        Assert.assertEquals(0,counterOf2);
    }
    @Test
    public void checkMove(){
        Grid grid = new Grid(null,100, 190);
        grid.generateMaze();
        grid.setPlayer();

        grid.move(-1, 0);
        grid.move(0, -1);

        Assert.assertEquals(1, grid.getPlayerX());
        Assert.assertEquals(1, grid.getPlayerY());

        grid.move(1, 0);
        grid.move(0, 1);

        Assert.assertTrue(grid.getPlayerX() > 1 || grid.getPlayerY() > 1);
    }
}