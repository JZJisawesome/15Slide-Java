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

package io.github.jzjisawesome._15slide_java;

import io.github.jzjisawesome._15slide_java.grid15.*;

/**
 *
 * @author John Jekel
 */


public class CommandUI
{
    public static void printGrid(Grid grid)
    {
        if (GridHelper.hasValidGridArray(grid))
        {
            System.out.println("┏━━━┳━━━┳━━━┳━━━┓");//start

            for (int i = 0; i < 4; ++i)
            {
                System.out.print("┃");//coloums
                for (int j = 0; j < 4; ++j)
                {
                    if (grid.gridArray[i][j] == 0)//if tile is no tile
                    {
                        //no tile is represented by ◉◉◉
                        //std::cout << termcolor::on_blue << termcolor::white;
                        System.out.print("◉◉◉");
                        //std::cout << termcolor::reset;
                    }
                    else
                    {
                        if (grid.gridArray[i][j] <= 9)//add extra space for single digit numbers
                            System.out.print(" ");

                        System.out.print(" " + grid.gridArray[i][j]);
                    }

                    System.out.print("┃");//coloums
                }

                System.out.println();

                if (i <= 2)//all except last line
                    System.out.println("┣━━━╋━━━╋━━━╋━━━┫");//rows
            }
            
            System.out.println("┗━━━┻━━━┻━━━┻━━━┛");
        }
        else
            throw new IllegalArgumentException("Grid invalid!");
    }
}