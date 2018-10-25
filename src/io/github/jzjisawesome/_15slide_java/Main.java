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

import io.github.jzjisawesome._15slide_java.grid15.GridHelper;
import io.github.jzjisawesome._15slide_java.grid15.Grid;
import io.github.jzjisawesome._15slide_java.CommandUI;


//import java.io.*;

/**
 * @todo documentation :)
 *
 * @author John Jekel
 */
public class Main
{
    static Grid gameGrid = GridHelper.generateRandomGrid();
    static CommandUI terminalUI = new CommandUI();
    /**
     * @param args The command line arguments
     */
    public static void main(String[] args)
    {
        System.out.println("15Slide-Java");
        
        System.out.println("Type \"help\" for a list of commands.");
        System.out.println("If it's your first time playing, type \"demo.\"");
        
        System.out.println();
        CommandUI.printGrid(gameGrid);
        System.out.println();
        System.out.println();
        
        terminalUI.start(gameGrid);
    }
}
