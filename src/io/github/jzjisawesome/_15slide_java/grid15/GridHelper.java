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

/**
 *
 * @author John Jekel
 */
final public class GridHelper
{
    public static boolean validMove(int tileY, int tileX, Grid grid)
    {
        if (hasValidGridArray(grid))
        {
            if (tileY > 3 || tileY < 0 || tileX > 3 || tileX < 0)//not out off array boundries
                return false;//TODO should throw an exception for this
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
    
    public static boolean hasWon(Grid grid)
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
                if (grid.gridArray[i][j] > 3 || grid.gridArray[i][j] < 0)
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
    
    public static boolean hasValidIndex(Grid grid)
    {
        Grid tempGrid = new Grid(grid);
        reIndex(tempGrid);
        
        return Arrays.equals(grid.index, tempGrid.index);//everything is good
    }
    
    public static boolean validGrid(Grid grid)
    {
        return hasValidGridArray(grid) && hasValidIndex(grid);
    }
    
    public static boolean solvableGrid(Grid grid)
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
    
    public static void reIndex(Grid grid)
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
}
