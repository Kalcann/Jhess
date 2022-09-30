package pawn.project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.IOException;
import javax.swing.JOptionPane;




public class Main {
    
    
    public static Input input = new Input();
    public static Frame frame;
    public static AssetManager assets;
    
    public static Point rawMousePos;
    public static Point mousePos;
    public static Point rawMouseSquare;
    public static Point mouseSquare;
                // the +14x and +37y compensate for the size deformation
                // meaning Im actually working in 600x600 
    public static Dimension frameSize = new Dimension(614,637);
    static int sFrameSize;
    public static boolean glassPieces = false;
    public static boolean perfectPieces = false;
    
    public static float piecesPercent = 0.9f;
    
    // 1 = white
    // 0 = black
    public static int turn = 1;
    
    //  {a,b,c,d,e}
    //   a = drawImage?
    //   b = piece
    //   c = colour
    //   d = startX
    //   e = StartY
    public static int[] pointerPiece = {0,0,0,0,0};
    
    //  {a,b,c,d,e}
    //   a = type: 0=none, 1=normal, 2=attack
    //   b = startX
    //   c = startY
    //   d = endX
    //   e = endY
    public static int[] lastMove = {0,0,0,0,0};
    public static int[] relLastMove = {0,0,0,0,0};
    
    //   int where:
    //   0 = can't castle
    //   1 = can castle both sides
    //   2 = can castle queen side only
    //   3 = can castle king side only
    public static int bCastle = 1;
    public static int wCastle = 1;
    
    //   [y][x][a,b,c] where:
    //      a = state: 0=empty, 1=normal, 2=ghost
    //      b = piece
    //      c = colour  
    public static int[][][] board = {{{1,1,0},{1,2,0},{1,3,0},{1,4,0},{1,5,0},{1,3,0},{1,2,0},{1,1,0}},
                                    {{1,0,0},{1,0,0},{1,0,0},{1,0,0},{1,0,0},{1,0,0},{1,0,0},{1,0,0}},
                                    {{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0}},
                                    {{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0}},
                                    {{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0}},
                                    {{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0}},
                                    {{1,0,1},{1,0,1},{1,0,1},{1,0,1},{1,0,1},{1,0,1},{1,0,1},{1,0,1}},
                                    {{1,1,1},{1,2,1},{1,3,1},{1,4,1},{1,5,1},{1,3,1},{1,2,1},{1,1,1}}};
    
    public static int[][][] board2 = {{{1,1,0},{0,2,0},{0,3,0},{1,4,0},{1,5,0},{0,3,0},{0,2,0},{1,1,0}},
                                    {{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0}},
                                    {{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0}},
                                    {{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0}},
                                    {{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0}},
                                    {{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0}},
                                    {{0,0,1},{0,0,1},{0,0,1},{0,0,1},{0,0,1},{0,0,1},{0,0,1},{0,0,1}},
                                    {{1,1,1},{0,2,1},{0,3,1},{1,4,1},{1,5,1},{0,3,1},{0,2,1},{1,1,1}}};
    
    public static int[][][] board3 = {{{0,1,1},{0,2,0},{0,4,0},{0,4,0},{1,5,0},{0,3,0},{0,5,0},{0,5,0}},
                                    {{1,0,1},{0,4,1},{0,0,0},{1,1,1},{0,1,1},{0,5,1},{0,4,1},{0,0,0}},
                                    {{0,0,0},{0,0,0},{1,0,1},{0,0,1},{0,0,0},{0,5,1},{0,1,0},{0,0,0}},
                                    {{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,3,1}},
                                    {{0,0,1},{0,0,1},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0}},
                                    {{1,0,0},{0,1,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0}},
                                    {{0,0,0},{0,0,0},{0,0,1},{0,0,1},{0,0,1},{0,1,1},{1,1,1},{0,0,1}},
                                    {{0,0,1},{0,0,1},{0,3,1},{0,4,1},{1,5,1},{0,3,1},{0,2,1},{0,5,1}}};
    
    public static Button[] buttons;
    
    public static enum GameState{
        Main,
        Play,
        Pause,
        Promote,
    }
    
    public static GameState game = GameState.Play;
    
    public static void main(String[] args) throws IOException
    {
        boolean run = true;
        //board = board3;
        
        assets = new AssetManager();
        frame = new Frame(frameSize.width,frameSize.height);
        updateSize();
        
        buttons = new Button[]{new Button(GameState.Promote,4,-210,0,120,120,null,new Color(200,200,200, 200)),
                               new Button(GameState.Promote,1,-70,0,120,120,null,new Color(200,200,200, 200)),
                               new Button(GameState.Promote,3,70,0,120,120,null,new Color(200,200,200, 200)),
                               new Button(GameState.Promote,2,210,0,120,120,null,new Color(200,200,200, 200))};
        
        
        while(run)
        {
            rawMousePos = MouseInfo.getPointerInfo().getLocation();
            mousePos = new Point(rawMousePos.x - frame.getX() - 6, rawMousePos.y - frame.getY() - 30);
            
            rawMouseSquare = new Point((int)((mousePos.x - (frameSize.width/2 - frel(280)) + frel(70))/frel(70)) -1,
                                    (int)((mousePos.y - (frameSize.height/2 - frel(280)) + frel(70))/frel(70)) -1);
            
            if(turn == 1)
            {
                mouseSquare = rawMouseSquare;
            }else
                mouseSquare = new Point(7-rawMouseSquare.x,7-rawMouseSquare.y);
            
            
            
            
            //System.out.println(pointerPiece[1]);
            
            
            frame.graphics.repaint();
        }
    }
    
    public static void updateSize()
    {
        frameSize.width = frame.getSize().width - 14;
        frameSize.height = frame.getSize().height - 37;
        //set to scale with the largest axis
        if(frameSize.width <= frameSize.height){
            sFrameSize = frameSize.width;
        }else
            sFrameSize = frameSize.height;
    }
    
    public static float frel(float size)
    {
                //general formula for sizes since I'm working in 600 px as default
        return Math.round(sFrameSize/(600/size));
    }
    
    public static int rel(float size)
    {
                //general formula for sizes since I'm working in 600 px as default
        System.out.println(((600/size)));
        return (int)Math.round(sFrameSize/(600/size));
    }
    
    public static void EndGame(String message)
    {
        JOptionPane.showMessageDialog(frame, message);
        System.exit(0);
    }
    
}
