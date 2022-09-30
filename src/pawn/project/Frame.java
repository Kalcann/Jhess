package pawn.project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;


public class Frame extends JFrame
{
    
    public Graph graphics;
    
    public Frame(int xSize,int ySize)
    {     
        setVisible(true);
        setSize(xSize,ySize);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(345,355));
        
        
        graphics = new Graph();
        graphics.setBackground(Color.black);
        
        addMouseListener(Main.input);
        addKeyListener(Main.input);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Main.updateSize();
                graphics.updateSize();
            }
        });
        
        add(graphics);
    }
    
    
}
