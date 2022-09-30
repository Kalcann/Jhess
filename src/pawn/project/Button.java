package pawn.project;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;

public class Button 
{
    
    public Main.GameState gameState;
    public int buttonId;
    
    public int x;
    public int y;
    public int width;
    public int height;
    
    public Color selectcol;
    
    public Image icon;
    
    public int day = -1;
    public int clientID = -1;
    
    public Button(Main.GameState game, int ButtonId, int X, int Y, int Width, int Height, Image bicon, Color selectCol)
    {
        
        
        gameState = game;
        buttonId = ButtonId;
        x = X;
        y = Y;
        width = Width;
        height = Height;
        
        icon = bicon;
        
        selectcol = selectCol;
        
    }
    public boolean hoverCheck(Main.GameState gameState, Point MousePos)
    {
        boolean selected;
        
        if(gameState == Main.game)
        {
            selected = MousePos.x > x + Main.frameSize.width/2 - width/2 & MousePos.x < x + Main.frameSize.width/2 + width/2 &
                       MousePos.y > y+ Main.frameSize.height/2 - height/2 & MousePos.y < y + Main.frameSize.height/2 + height/2;
        }else
            selected = false;
        
        return selected;
    }
    
    
}
