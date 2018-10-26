/* MIT License
 *
 * Copyright (c) 2018 John Jekel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
*/
package io.github.jzjisawesome._15slide_java.grid15;

import java.util.*;
import java.io.*;

/**
 *
 * @author John Jekel
 */
public class GridHelper
{
    public static boolean validMove(int tileNum, Grid grid) throws IllegalArgumentException, IndexOutOfBoundsException
    {
        if (tileNum > 0 && tileNum < 16)
            return validMove(grid.index[tileNum][0], grid.index[tileNum][1], grid);
        else
            throw new IllegalArgumentException("tileNum invalid!");
    }
    
    public static boolean validMove(int tileY, int tileX, Grid grid) throws IllegalArgumentException, IndexOutOfBoundsException
    {
        if (validGrid(grid))
        {
            if (tileY > 3 || tileY < 0 || tileX > 3 || tileX < 0)//not out off array boundries
                throw new IndexOutOfBoundsException("Coordinates out of bounds!");
            else if (tileY == grid.index[0][0] && tileX == grid.index[0][1])//not no tile itself
                return false;
            else if (tileY == grid.index[0][0] && ((tileX == grid.index[0][1] - 1) || (tileX == grid.index[0][1] + 1)))//same colum, a row beside
                return true;
            else if (tileX == grid.index[0][1] && ((tileY == grid.index[0][0] - 1) || (tileY == grid.index[0][0] + 1)))//same row, a colum beside
                return true;
            else
                return false;
        }
        else
            throw new IllegalArgumentException("Grid invalid!");
    }
    
    public static boolean hasWon(Grid grid) throws IllegalArgumentException
    {
        if (hasValidGridArray(grid))
            return Arrays.equals(grid.gridArray, Grid.GOAL_GRID);
        else
            throw new IllegalArgumentException("Grid invalid!");
    }
    
    public static boolean hasValidGridArray(Grid grid)
    {
        for(int i = 0; i < 4; ++i)
            for(int j = 0; j < 4; ++j)
            {
                if (grid.gridArray[i][j] > 15 || grid.gridArray[i][j] < 0)
                    return false;//if too high a number (out of bounds)
            }
        
        int[] numCount = new int[16];
        
        for(int i = 0; i < 4; ++i)
            for(int j = 0; j < 4; ++j)
                    numCount[grid.gridArray[i][j]] += 1;//increment each number to see how many of each
        
        for (int i = 0; i < 16; ++i)
        {
            if (numCount[i] != 1)
                return false;//if exactly 1 of each number
        }
        
        return true;//everything is good
    }
    
    public static boolean validGrid(Grid grid)
    {
        return hasValidGridArray(grid) && hasValidIndex(grid);
    }
    
    public static boolean hasValidIndex(Grid grid)
    {
        Grid tempGrid = new Grid(grid);
        reIndex(tempGrid);
        
        return Arrays.equals(grid.index, tempGrid.index);//everything is good
    }
    
    public static void swapTile(int tileNum, Grid grid) throws IllegalArgumentException, IndexOutOfBoundsException
    {
        if ((tileNum < 16 && tileNum >=0))
        {
            int tileY = grid.index[tileNum][0];
            int tileX = grid.index[tileNum][1];
            
            if (validMove(tileY, tileX, grid))
                swapTile(tileY, tileX, grid);
            else
                throw new IllegalArgumentException("Invalid Move");
        }
        else
            throw new IndexOutOfBoundsException("tileNum out of bounds!");
    }
    
    public static void swapTile(int tileY, int tileX, Grid grid)
    {
        if (validMove(tileY, tileX, grid))
        {
            int tileNum = grid.gridArray[tileY][tileX];
            
            //original location of blank tile
            int oldNoTileY = grid.index[0][0];
            int oldNoTileX = grid.index[0][1];

            grid.gridArray[grid.index[0][0]][grid.index[0][1]] = tileNum;//moves tile to no tile

            //updates location of moved tile in index
            grid.index[tileNum][0] = oldNoTileY;
            grid.index[tileNum][1] = oldNoTileX;

            grid.gridArray[tileY][tileX] = 0;//moves no Tile

            //updates location of noTile in index
            grid.index[0][0] = tileY;
            grid.index[0][1] = tileX;
        }
        else
            throw new IllegalArgumentException("Invalid Move");
    }
    
    public static Grid generateRandomGrid()
    {
        //FIXME get rid of single dimentional intermediary array
        //we have to have it because otherwise std::shuffle only shuffles rows themselves, not inside or in between
        int[] linearGrid =
        {
            1,  2,  3,  4,
            5,  6,  7,  8,
            9,  10, 11, 12,
            13, 14, 15, 0
        };

        Grid tempGrid = new Grid();//a normal gridArray

        do
        {
            simpleShuffle(linearGrid);//shuffle grid randomly with rd

            //copy 2d to 3d array
            for (int i = 0; i < 4; ++i)
                for (int j = 0; j < 4; ++j)
                    tempGrid.gridArray[i][j] = linearGrid[(i * 4) + j];
            
            reIndex(tempGrid);//so solvableGrid does not get mad
        }
        while (!solvableGrid(tempGrid));//check if grid is solvable

        return tempGrid;
    }
    
    private static void simpleShuffle(int[] linearGrid)
    {
        //Based on https://stackoverflow.com/a/1520212
        //A Durstenfeld shuffle
        
        Random generator = new Random(System.currentTimeMillis());
        
        for (int i = 15; i > 0; --i)
        {
          int index = generator.nextInt(i + 1);
          // Simple swap
          int a = linearGrid[index];
          linearGrid[index] = linearGrid[i];
          linearGrid[i] = a;
        }
    }
    
    public static boolean solvableGrid(Grid grid) throws IllegalArgumentException
    {
        if (validGrid(grid))
        {
            //calculate number of inversions
            int[] linearGrid = new int[16];
            int numberOfInversions = 0;

            //copy to 1 dimentional array
            for (int i = 0; i < 4; ++i)
                for (int j = 0; j < 4; ++j)
                    linearGrid[(i * 4) + j] = grid.gridArray[i][j];

            //look for inversions by comparing pairs of tiles
            //Starts with 1st and 2nd then 1st and 3rd up to 1st and 16th, then repeats with 2nd and 3rd and so on
            for (int i = 0; i < 15; ++i)//last number cannot have any inversions so we might as well skip it
            {
                for (int j = 1; j < (16 - i); ++j)//start at the tile after i until the end of the grid
                {
                    if ((linearGrid[i] != 0) && (linearGrid[i + j] != 0))//if none of the two compared tiles are the no tile
                        if ((linearGrid[i] > linearGrid[i + j]))//if first tile is greater than other tile
                            ++numberOfInversions;
                }
            }

            //find if inversions are even
            boolean evenInversions = (numberOfInversions % 2) == 0;

            //find if no tile y coordinate is on an even row from the bottom
            //this uses [0][0] as y coordinate, onece flipped coordinate issue is fixed this should be changed to [0][1]
            //works for now
            boolean noTileOnEvenRow = ((4 - grid.index[0][0]) % 2) == 0;

            return evenInversions != noTileOnEvenRow;
        }
        else
            throw new IllegalArgumentException("Grid invalid!");
    }
    
    public static void reIndex(Grid grid) throws IllegalArgumentException
    {
        if (hasValidGridArray(grid))
        {
            for(int i = 0; i < 4; ++i)
                for(int j = 0; j < 4; ++j)
                {
                    grid.index[grid.gridArray[i][j]][0] = i;//find tile's y coordinate and copy to index
                    grid.index[grid.gridArray[i][j]][1] = j;//find tile's x coordinate and copy to index
                }
        }
        else
            throw new IllegalArgumentException("Grid invalid!");
    }
    
    public static void save(String saveFile, Grid grid) throws FileNotFoundException, IOException
    {
        if (hasValidGridArray(grid))
        {
            PrintWriter saveFileWriter = new PrintWriter(saveFile);

            for(int i = 0; i < 4; ++i)
                for(int j = 0; j < 4; ++j)
                    saveFileWriter.print(grid.gridArray[i][j] + " ");
            
            saveFileWriter.close();
        }
        else
            throw new IllegalArgumentException("Grid invalid!");
    }
    
    public static void load(String saveFile, Grid grid) throws FileNotFoundException, IOException
    {
        Scanner saveFileScanner = new Scanner(new File(saveFile));

        for(int i = 0; i < 4; ++i)
            for(int j = 0; j < 4; ++j)
                grid.gridArray[i][j] = saveFileScanner.nextInt();

        saveFileScanner.close();

        reIndex(grid);
    }
}
