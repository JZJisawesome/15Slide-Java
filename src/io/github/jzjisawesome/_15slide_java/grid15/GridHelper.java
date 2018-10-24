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
