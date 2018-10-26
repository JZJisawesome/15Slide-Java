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

import java.util.*;

import io.github.jzjisawesome._15slide_java.grid15.*;
import java.io.*;


/**
 *
 * @author John Jekel
 */


public class CommandUI
{    
    private boolean autoSave    = Globals.AUTOSAVE_DEFAULT; ///<Autosave to last savefile
    private boolean autoGrid    = true; ///<Autoprint the grid
    private boolean autoExit    = false;///<Exit the game on win automatically
    private boolean easySlide   = true; ///<Slide a tile without having to type "slide" first
    
    private boolean wantsToExit = false;
    private String defaultSaveFile = "";
    
    public void start(Grid grid)
    {
        Scanner inputScanner = new Scanner(System.in);

        while(!wantsToExit)
        {
            System.out.print("slide»");
            handleCommand(inputScanner.nextLine(), grid);
        }
    }
    
    public void handleCommand(String inputtedLine, Grid grid)
    {
        Scanner tokenScanner = new Scanner(inputtedLine);
        
        if (easySlide && tokenScanner.hasNextInt())
        {
            try
            {
                swapTile(tokenScanner.nextInt(), grid);
            }
            catch (NoSuchElementException e)
            {
                System.err.println("Sorry, but you did not type a tile number. Please try again.");
            }
        }
        else
        {
            String input = "";
            
            try
            {
                input = tokenScanner.next();
            }
            catch (NoSuchElementException e) {}//no need to do anything
            
            
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
                    if (Globals.CHEAT_MODE)
                        System.out.println("BUT WHY? YOU WERE SO CLOSE!");
                    
                    grid = GridHelper.generateRandomGrid();
                    
                    System.out.println();
                    CommandUI.printGrid(grid);
                    System.out.println();
                    break;
                }
                case "slide":
                {
                    try
                    {
                        swapTile(tokenScanner.nextInt(), grid);
                    }
                    catch (NoSuchElementException e)
                    {
                        System.err.println("Sorry, but you did not type a tile number. Please try again.");
                    }
                    
                    break;
                }
                case "print":
                {
                    System.out.println();
                    
                    try
                    {
                        input = tokenScanner.next();
                        
                        if (input.equals("grid"))
                            printGrid(grid);
                        else if (input.equals("goal"))
                            printGrid(new Grid (Grid.GOAL_GRID));
                        else
                        {
                            System.err.println("Sorry, but \"" + input + "\" is not a valid grid. ");
                            System.err.println("Try \"grid\" or \"goal.\"");
                        }
                    }
                    catch (NoSuchElementException e)
                    {
                        System.err.println("Sorry, but you did not type a grid. ");
                        System.err.println("Try \"grid\" or \"goal.\"");
                    }
                        
                    System.out.println();
                    break;
                }
                case "save":
                {
                    try
                    {
                        saveGame(tokenScanner.next(), grid);
                    }
                    catch (NoSuchElementException e)
                    {
                        System.err.println("Sorry, but you did not type a save file. ");
                        System.err.println("Try \"help\" for information");
                    }
                    
                    break;
                }
                case "load":
                {
                    try
                    {
                        loadGame(tokenScanner.next(), grid);
                    }
                    catch (NoSuchElementException e)
                    {
                        System.err.println("Sorry, but you did not type a save file. ");
                        System.err.println("Try \"help\" for information");
                    }
                    break;
                }
                case "options":
                {
                    displayOptions();
                    break;
                }
                case "enable":
                case "disable":
                {
                    try
                    {
                        handleOptions(tokenScanner.next(), input.equals("enable"));
                    }
                    catch (NoSuchElementException e)
                    {
                        System.err.println("Sorry, but you did not type an option. ");
                        System.err.println("Try typing \"options\" for a list.");
                    }
                    
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
                    System.err.println("not implemented");
                    break;
                }
                case "":
                    break;
                default:
                {
                    System.err.println("Sorry, but \"" + input + "\" is not a valid command. ");
                    System.err.println("Try typing \"help\" for a list.");
                    break;
                }
            }
        }
    }
    
    private void swapTile(int tile, Grid grid)
    {
        if (tile > 0 && tile < 16 && GridHelper.validMove(tile, grid))//instead of a try catch block
        {
            GridHelper.swapTile(tile, grid);

            wantsToExit = autoExit && GridHelper.hasWon(grid);//if the game is over and autoExit is on then exit

            
            if (autoSave && !(defaultSaveFile.equals("")))//if auto save is on and there is a default save file
            {
                try
                {
                    GridHelper.save(defaultSaveFile, grid);//silent save (dosent use saveGame)
                }
                catch (FileNotFoundException e)
                {
                    System.err.println("Warning: The autosave failed. Try saving to a new location using \"save\", or change file permissions.") ;
                }
            }
            
        }
        else
            System.err.println("Sorry, but \"" + tile + "\" is not a valid tile. Please try again.");

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
            
            if (Globals.CHEAT_MODE)
            {
                System.out.println("BUT YOU CHEATED (CHEAT_MODE = true)");
            }
        }
    }
    
    private void handleOptions(String option, boolean optionSetting)
    {
        switch (option)
        {
            case "autoSave":
            {
                this.autoSave = optionSetting;
                break;
            }
            case "autoGrid":
            {
                this.autoGrid = optionSetting;
                break;
            }
            case "autoExit":
            {
                this.autoExit = optionSetting;
                break;
            }
            case "easySlide":
            {
                this.easySlide = optionSetting;
                break;
            }
            default:
            {
                System.err.println("Sorry, but \"" + option + "\" is not a valid option. ");
                System.err.println("Try typing \"options\" for a list.");
            }
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
    
    private void saveGame(String saveFile, Grid grid)
    {
        System.out.println("Saving game to " + saveFile + "...");

        try
        {
            GridHelper.save(saveFile, grid);

            System.out.println("Save Complete!");
            System.out.println();

            //we only get here if above works
            defaultSaveFile = saveFile;
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Something went wrong while saving. ");
            System.err.println("Try a diffrent file name/location, or change permissions to allow writing.");
        }
    }
    
    private void loadGame(String saveFile, Grid grid)
    {
        System.out.println("Loading game from " + saveFile + "...");

        try
        {
            GridHelper.load(saveFile, grid);

            System.out.println("Load Sucessfull!");
            System.out.println();

            //we only get here if above works
            defaultSaveFile = saveFile;
        }
        catch (FileNotFoundException | IllegalArgumentException | NoSuchElementException e)
        {
            System.err.println("Something went wrong while loading. ");
            System.err.println("Try a diffrent file name/location, or change permissions to allow reading.");
        }
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
    
    private static void displayOptions()
    {
        System.out.println();
        System.out.println("Options");
        System.out.println();

        System.out.println("autoSave bool\tIf enabled, automatically saves the game to the last savefile. Enabled by default.");
        System.out.println("autoGrid bool\tIf enabled, autoprints the grid after certain commands. Enabled by default.");
        System.out.println("autoExit bool\tIf enabled, automatically exits the game after you win. Disabled by default.");
        System.out.println("easySlide bool\tAllows you to just type in a number rather than \"slide\" first. Enabled by default.");
        System.out.println();
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
        if (Globals.CHEAT_MODE)
            System.out.println("CHEATING BUILD");
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