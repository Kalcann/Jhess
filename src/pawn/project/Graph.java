package pawn.project;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JPanel;


public class Graph extends JPanel
{
     
    
    
    Dimension frameSize = new Dimension();
    int sFrameSize;
    
    Color primaryCol = new Color(150,80,60);
    Color secondaryCol = new Color(230,230,210);
    
    public Graph()
    {
        frameSize.width = Main.frameSize.width;
        frameSize.height = Main.frameSize.height;
    }

    public void updateSize()
    {
        frameSize.width = Main.frameSize.width;
        frameSize.height = Main.frameSize.height; 
        
        //set to scale with the largest axis
        if(frameSize.width <= frameSize.height){
            sFrameSize = frameSize.width;
        }else
            sFrameSize = frameSize.height;
    }
    
    float i = 0;
    @Override
    public void paint(Graphics g)
    {

        Graphics2D g2d = (Graphics2D) g;
        
        if(Main.game == Main.GameState.Play | Main.game == Main.GameState.Promote | Main.game == Main.GameState.Pause)
        {
                // Background colouring
        g.setColor(new Color(0,0,0));
        g.fillRect(0,0,frameSize.width,frameSize.height);
        
        
                //board base, relative bord size = 560px
        g.setColor(primaryCol);
        g.fillRect(frameSize.width/2 - rel(280), frameSize.height/2 - rel(280), rel(560), rel(560));
        
                //board inverse squares
        g.setColor(secondaryCol);
        for(int y = 0; y<4; y++)
        {
            for(int x = 0; x<4; x++)
            {
                g.fillRect(Math.round(x*frel(140) + frameSize.width/2 - frel(280)),
                           Math.round(y*frel(140) + frameSize.height/2 - frel(280)),
                           rel(70),rel(70));
            }
        }
        for(int y = 0; y<4; y++)
        {
            for(int x = 0; x<4; x++)
            { 
                g.fillRect(Math.round(x*frel(140) + frameSize.width/2 - frel(210)),
                           Math.round(y*frel(140) + frameSize.height/2 - frel(210)),
                           rel(70),rel(70));
            }
        }
        
        g.setColor(new Color(230,225,100,150));
        if(Main.lastMove[0] != 0)
        {
            
            
            g.fillRect(Math.round(Main.lastMove[1]*frel(70) + frameSize.width/2 - frel(280)),
                           Math.round(Main.lastMove[2]*frel(70) + frameSize.height/2 - frel(280)),
                           rel(70),rel(70));
            
            if(Main.lastMove[0] == 2)
                g.setColor(new Color(200,90,90,150));
            
            g.fillRect(Math.round(Main.lastMove[3]*frel(70) + frameSize.width/2 - frel(280)),
                       Math.round(Main.lastMove[4]*frel(70) + frameSize.height/2 - frel(280)),
                       rel(70),rel(70));
            
            
            if(PiecesMan.passanted[0] != -1)
            {
                if(Main.turn == 0)
                { System.out.println("black");
                    g.setColor(new Color(200,90,90,150));
                    g.fillRect(Math.round((7-PiecesMan.passanted[0])*frel(70) + frameSize.width/2 - frel(280)),
                           Math.round((7-PiecesMan.passanted[1])*frel(70) + frameSize.height/2 - frel(280)),
                           rel(70),rel(70));
                }else
                {   System.out.println("white");
                    g.setColor(new Color(200,90,90,150));
                    g.fillRect(Math.round(PiecesMan.passanted[0]*frel(70) + frameSize.width/2 - frel(280)),
                           Math.round((PiecesMan.passanted[1])*frel(70) + frameSize.height/2 - frel(280)),
                           rel(70),rel(70));
                }
            }
            
            
        }
            //pointer square
        g.setColor(new Color(90,200,90,100));
        if(Main.mouseSquare.x>=0 & Main.mouseSquare.x<8 & Main.mouseSquare.y>=0 & Main.mouseSquare.y<8)
            g.fillRect(Math.round(Main.rawMouseSquare.x*frel(70) + frameSize.width/2 - frel(280)),
                           Math.round(Main.rawMouseSquare.y*frel(70) + frameSize.height/2 - frel(280)),
                           rel(70),rel(70));
            
        
        g.setColor(Color.black);
        g.fillRect(frameSize.width/2 + rel(280),0,20,frameSize.height);
        g.fillRect(0,frameSize.height/2 + rel(280),frameSize.width,20);
        
        if(Main.glassPieces)
                {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.6f));
                }else
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));

        // drawing pieces
        for(int y = 0; y < 8;y++)
        {
            for(int x = 0; x < 8; x++)
            {
                // finky see frough pieces

                if(Main.board[y][x][0] != 0)
                {
                    if(Main.board[y][x][0] == 2)
                        if(Main.glassPieces)
                        {
                            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
                        }else
                            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));
                            
                   
                    if(Main.turn == 1)
                    {
                    // this is one line
                    g2d.drawImage(Main.assets.getScaledPiece(Main.board[y][x][1],
                        Main.board[y][x][2],new Dimension(rel(70*Main.piecesPercent),rel(70*Main.piecesPercent))), // was 64
                        Math.round(x*frel(70) + frameSize.width/2 - frel(280-35*(1-Main.piecesPercent))), // was 277
                        Math.round(y*frel(70) + frameSize.height/2 - frel(280-35*(1-Main.piecesPercent))), this);
                    }else
                    {
                        
                        g2d.drawImage(Main.assets.getScaledPiece(Main.board[y][x][1],
                        Main.board[y][x][2],new Dimension(rel(70*Main.piecesPercent),rel(70*Main.piecesPercent))), // was 64
                        Math.round((7-x)*frel(70) + frameSize.width/2 - frel(280-35*(1-Main.piecesPercent))), // was 277
                        Math.round((7-y)*frel(70) + frameSize.height/2 - frel(280-35*(1-Main.piecesPercent))), this);
                    }
                }
                
                if(Main.glassPieces)
                {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.6f));
                }else
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
                
            }
        }
        
        if(Main.pointerPiece[0] == 1)
            g.drawImage(Main.assets.getScaledPiece(Main.pointerPiece[1],
                        Main.pointerPiece[2], new Dimension(rel(70*Main.piecesPercent),rel(70*Main.piecesPercent))),
                        Main.mousePos.x-rel(35*Main.piecesPercent), Main.mousePos.y-rel(35*Main.piecesPercent), this);
        
        
        if(Main.game == Main.GameState.Promote)
        {
            g.setColor(new Color(20,20,20,200));
            g.fillRect(frameSize.width/2 - rel(282), frameSize.height/2 - rel(282), rel(564), rel(564));
        }
        
        for (Button button : Main.buttons) 
        {
            //button hover highlights
            if(button != null)
            {
                if (button.gameState == Main.game) 
                {
                    if (button.hoverCheck(Main.game, new Point(rel(Main.mousePos.x),rel(Main.mousePos.y)))) 
                    {
                        g.setColor(button.selectcol);
                        g.fillRect(frameSize.width/2 + rel(button.x - button.width/2), frameSize.height/2 + rel(button.y - button.height/2), rel(button.width), rel(button.height));
                    }
                    if(button.icon != null)
                        g.drawImage(button.icon,frameSize.width/2 + rel(button.x-button.icon.getWidth(null)/2), frameSize.height/2 + rel(button.y-button.icon.getHeight(null)/2), null);
                }
            }
        }
        }
    }
    
    public int rel(float size)
    {
                //general formula for sizes since I'm working in 600 px as default
        return (int)(sFrameSize/(600/size));
    }
    
    
    public float frel(float size)
    {
                //general formula for sizes since I'm working in 600 px as default
        return (sFrameSize/(600/size));
    }
    
}


