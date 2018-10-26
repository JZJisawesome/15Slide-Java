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

import java.util.Scanner;

import io.github.jzjisawesome._15slide_java.grid15.*;


/**
 *
 * @author John Jekel
 */


public class CommandUI
{    
    boolean autoSave    = true; ///<Autosave to last savefile
    boolean autoGrid    = true; ///<Autoprint the grid
    boolean autoExit    = false;///<Exit the game on win automatically
    boolean easySlide   = true; ///<Slide a tile without having to type "slide" first
    
    private boolean wantsToExit = false;
    
    public void start(Grid grid)
    {
        Scanner inputScanner = new Scanner(System.in);

        while(!wantsToExit)
        {
            System.out.print("slide»");//unicode
            handleCommand(inputScanner.nextLine(), grid);
        }
    }
    
    public void handleCommand(String inputtedLine, Grid grid)
    {
        Scanner tokenScanner = new Scanner(inputtedLine);
        
        if (easySlide && tokenScanner.hasNextInt())
        {
            swapTile(tokenScanner.nextInt(), grid);
        }
        else
        {
        
            String input = tokenScanner.next();
            
            switch (input)
            {
                case "help":
                {
                    displayHelp();
                    break;
                }
                case "demo":
                {
                    runDemo();
                    break;
                }
                case "newgame":
                {
                    System.out.println("not implemented");
                    break;
                }
                case "slide":
                {
                    swapTile(tokenScanner.nextInt(), grid);
                    break;
                }
                case "print":
                {
                    System.out.println("not implemented");
                    break;
                }
                case "save":
                {
                    System.out.println("not implemented");
                    break;
                }
                case "load":
                {
                    System.out.println("not implemented");
                    break;
                }
                case "options":
                {
                    System.out.println("not implemented");
                    break;
                }
                case "enable":
                {
                    System.out.println("not implemented");
                    break;
                }
                case "disable":
                {
                    System.out.println("not implemented");
                    break;
                }
                case "about":
                {
                    displayAbout();
                    break;
                }
                case "licence":
                {
                    displayLicence();
                    break;
                }
                case "exit":
                {
                    wantsToExit = true;//if user wants to exit
                    break;
                }
                case "debug":
                {
                    System.out.println("not implemented");
                    break;
                }
                default:
                {
                    System.out.println("not implemented");
                    break;
                }
            }
        }
    }
    
    private void swapTile(int tile, Grid grid)
    {
        if (GridHelper.validMove(tile, grid))//instead of a try catch block
        {
            GridHelper.swapTile(tile, grid);

            wantsToExit = autoExit && GridHelper.hasWon(grid);//if the game is over and autoExit is on then exit

            /*
            if (autoSave && (defaultSaveFile != ""))//if auto save is on and there is a default save file
            {
                try
                {
                    GridHelper.save(defaultSaveFile, grid);//silent save (dosent use saveGame)
                }
                catch (std::ios_base::failure &e)
                {
                    System.out.println("Warning: The autosave failed. Try saving to a new location using \"save,\" or change file permissions.") ;
                }
            }
            */
        }
        else
        {
            System.out.print("Sorry, but \"" + tile + "\" is not a valid tile. ");
            System.out.println("Please try again.");
        }

        if (autoGrid)
        {
            System.out.println();
            printGrid(grid);
            System.out.println();
        }

        if (GridHelper.hasWon(grid))
        {
            System.out.println("YOU WON!!");//todo add trophy
            System.out.println();
        }
    }
    
    public static void printGrid(Grid grid)
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
    
    private static void runDemo()
    {
        System.out.println();
        System.out.println("15Slide-Java");

        System.out.println("Welcome to 15Slide-Java, a fun slidy-tile game. The object is to make the grid like this:");
        System.out.println();
        
        printGrid(new Grid(Grid.GOAL_GRID));
        System.out.println();

        System.out.println("Move the tiles agacient to the ◉◉◉ by typing a number and pressing enter.");
        System.out.println("You can save or load a game by typing \"save\" or \"load\" and the file name.");
        System.out.println("Start over by typing \"newgame.\"");
        System.out.println("To learn about more awesome commands, type \"help.\"");
        System.out.println();

        System.out.println("ENJOY 15Slide!!!");//todo add smile
        System.out.println();
    }
    
    private static void displayHelp()
    {
        System.out.println();
        System.out.println("Commands");

        System.out.println("help\t\tDisplays a list of valid commands");
        System.out.println("demo\t\tWalks you through the game");
        System.out.println();

        System.out.println("newgame\t\tEnds the current game and starts a new one");
        System.out.println("slide\tnum\tSlides the tile with the number given");
        System.out.println("print\tstr\tPrints the current \"grid\" or the \"goal\" grid");
        System.out.println();

        System.out.println("save\tstr\tSaves the game to the specified file");
        System.out.println("load\tstr\tLoads the game from the specified file");
        System.out.println();

        System.out.println("options\t\tDisplays a list of valid options");
        System.out.println("enable\tstr\tEnables the specified option");
        System.out.println("disable\tstr\tDisables the specified option");
        System.out.println();

        System.out.println("about\t\tCool stuff about 15Slide");
        System.out.println("licence\t\tLicence information for 15Slide and other libraries");
        System.out.println("exit\t\tExit 15Slide");
    }
    
    private static void displayAbout()
    {
        System.out.println();
        System.out.println("15Slide-Java");
        System.out.println("Copyright 2018 John Jekel");
        System.out.println("See https://github.com/JZJisawesome/15Slide-Java/blob/master/LICENSE for the terms");
        System.out.println();

        System.out.println();
        System.out.println("15Slide-Java is a fun, cross-platform, slidy-tile game ported from the original 15Slide project.");
        System.out.println("It is intended to coexist with the original.");
        System.out.println("If you find some problem or want a new feature, go create a new issue at https://github.com/JZJisawesome/15Slide-Java/issues");
        System.out.println("Type \"licence\" for 15Slide-Java and other licencing.");
        System.out.println();


        System.out.println();
        System.out.println("Additional Information");
        System.out.println();

        System.out.println("15Slide Version " + Globals.SLIDE_VERSION_STRING);
        System.out.println();

        System.out.println("Running on: " + System.getProperty("os.name") + " " + System.getProperty("os.version") + " " + System.getProperty("os.arch"));
        System.out.println("Java Runtime: " + System.getProperty("java.vendor") + " " + System.getProperty("java.version"));
        System.out.println("Java VM: " + System.getProperty("java.vm.vendor") + " " + System.getProperty("java.vm.name") + " " + System.getProperty("java.vm.version"));
        System.out.println();
    }
    private static void displayLicence()
    {
        System.out.println();
        System.out.println("Licencing");
        System.out.println();


        System.out.println();
        System.out.println("15Slide-Java");
        System.out.println("MIT License");
        System.out.println();

        System.out.println("Copyright (c) 2018 John Jekel");
        System.out.println();

        System.out.println("Permission is hereby granted, free of charge, to any person obtaining a copy");
        System.out.println("of this software and associated documentation files (the \"Software\"), to deal");
        System.out.println("in the Software without restriction, including without limitation the rights");
        System.out.println("to use, copy, modify, merge, publish, distribute, sublicense, and/or sell");
        System.out.println("copies of the Software, and to permit persons to whom the Software is");
        System.out.println("furnished to do so, subject to the following conditions:");
        System.out.println();

        System.out.println("The above copyright notice and this permission notice shall be included in all");
        System.out.println("copies or substantial portions of the Software.");
        System.out.println();

        System.out.println("THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR");
        System.out.println("IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,");
        System.out.println("FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE");
        System.out.println("AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER");
        System.out.println("LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,");
        System.out.println("OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE");
        System.out.println("SOFTWARE.");
        System.out.println();
        
        
        System.out.println();
        System.out.println("15Slide (Original)");
        System.out.println("MIT License");
        System.out.println();

        System.out.println("Copyright (c) 2017-2018 John Jekel");
        System.out.println();

        System.out.println("Permission is hereby granted, free of charge, to any person obtaining a copy");
        System.out.println("of this software and associated documentation files (the \"Software\"), to deal");
        System.out.println("in the Software without restriction, including without limitation the rights");
        System.out.println("to use, copy, modify, merge, publish, distribute, sublicense, and/or sell");
        System.out.println("copies of the Software, and to permit persons to whom the Software is");
        System.out.println("furnished to do so, subject to the following conditions:");
        System.out.println();

        System.out.println("The above copyright notice and this permission notice shall be included in all");
        System.out.println("copies or substantial portions of the Software.");
        System.out.println();

        System.out.println("THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR");
        System.out.println("IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,");
        System.out.println("FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE");
        System.out.println("AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER");
        System.out.println("LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,");
        System.out.println("OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE");
        System.out.println("SOFTWARE.");
        System.out.println();
    }
}