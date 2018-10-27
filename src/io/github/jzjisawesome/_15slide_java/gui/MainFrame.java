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

package io.github.jzjisawesome._15slide_java.gui;

import io.github.jzjisawesome._15slide_java.grid15.GridHelper;
import io.github.jzjisawesome._15slide_java.grid15.Grid;
import java.awt.event.WindowEvent;

import javax.swing.*;
import java.awt.event.WindowListener;

/**
 *
 * @author John Jekel
 */


public class MainFrame extends JFrame
{
    public MainFrame(Grid grid)
    {
        setTitle("15Slide-Java");
        
        addWindowListener(new MainFrameWindowListener(this));
    }
    
    private void onClose()
    {
        System.exit(0);
    }
    
    private class MainFrameWindowListener implements WindowListener
    {
        MainFrame parent = null;
        
        private MainFrameWindowListener(MainFrame jFrame)
        {
            parent = jFrame;
        }
        
        public void windowOpened(WindowEvent arg0)
        {
        }

        public void windowClosing(WindowEvent arg0)
        {
            parent.onClose();
        }

        public void windowClosed(WindowEvent arg0)
        {
        }

        public void windowIconified(WindowEvent arg0)
        {
        }

        public void windowDeiconified(WindowEvent arg0)
        {
        }

        public void windowActivated(WindowEvent arg0)
        {
        }

        public void windowDeactivated(WindowEvent arg0)
        {
        }
        
    }
}
