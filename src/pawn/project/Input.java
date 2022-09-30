package pawn.project;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;


public class Input implements MouseListener, KeyListener
{

    @Override
    public void mouseClicked(MouseEvent e)
    {
        //steal button code and check what to promote to and the run StalenCheck()
        if(e.getButton() == 1)
        {
            for (Button button : Main.buttons) 
            {
                if(button != null)
                    if(button.hoverCheck(Main.game, Main.mousePos))
                    { 
                        if(null != Main.game)
                        switch (Main.game) 
                        {
                            case Promote -> 
                            {
                                System.out.println("wtf");
                                Main.board[Main.pointerPiece[4]][Main.pointerPiece[3]][0] = 0;
                                Main.board[Main.pointerPiece[4] + Main.turn*-2+1][Main.pointerPiece[3]] = new int[]{1,button.buttonId,Main.pointerPiece[2]};
                                Main.turn = Main.turn*-2+1;
                                Main.game = Main.GameState.Play;
                            }
                        }
                    }
            }
        }
    }
    @Override
    public void mousePressed(MouseEvent e){
        if(e.getButton() == 1)
        if(Main.game == Main.GameState.Play)
        {
            if((Main.mouseSquare.x>=0 & Main.mouseSquare.x<8 & Main.mouseSquare.y>=0 &
               Main.mouseSquare.y>=0) && (Main.board[Main.mouseSquare.y][Main.mouseSquare.x][0] == 1 &
                    Main.turn == Main.board[Main.mouseSquare.y][Main.mouseSquare.x][2]))
            {

                Main.pointerPiece[1] = Main.board[Main.mouseSquare.y][Main.mouseSquare.x][1];
                Main.pointerPiece[2] = Main.board[Main.mouseSquare.y][Main.mouseSquare.x][2];
                Main.pointerPiece[3] = Main.mouseSquare.x;
                Main.pointerPiece[4] = Main.mouseSquare.y;

                Main.board[Main.mouseSquare.y][Main.mouseSquare.x][0] = 2;


                Main.pointerPiece[0] = 1;
            }
        }
    }
    @Override
    public void mouseReleased(MouseEvent e){
        if(e.getButton() == 1)
        if(Main.pointerPiece[0] == 1)
        {
            
                if(PiecesMan.ValidateMove(Main.pointerPiece[1], Main.pointerPiece[2], Main.pointerPiece[3], Main.pointerPiece[4], Main.mouseSquare.x, Main.mouseSquare.y))
                {
                    if(Main.game == Main.GameState.Promote)
                    {
                        Main.pointerPiece[0] = 0;
                    }else
                        ChecknStale();
                    
                }else
                {
                    Main.board[Main.pointerPiece[4]][Main.pointerPiece[3]][0] = 1;
                    Main.pointerPiece[0] = 0;
                    System.out.println(PiecesMan.reason);
                }
            
        }
    }
    @Override
    public void mouseEntered(MouseEvent e){}
    
    @Override
    public void mouseExited(MouseEvent e){}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
            Main.glassPieces = !Main.glassPieces;
        try{
        if(e.getKeyCode() == KeyEvent.VK_P)
            if(Main.perfectPieces)
            {
                Main.perfectPieces = false;
                Main.assets.switchPieces("basic.png");
            }else
            {
                Main.perfectPieces = true;
                Main.assets.switchPieces("perfect.png");
            }
        }catch(IOException ex){}
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if(Main.piecesPercent > 0.4f)
                Main.piecesPercent -= 0.1f;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            
            Main.piecesPercent += 0.1f;
        }
        if(e.getKeyCode() == KeyEvent.VK_W)
            System.out.println(PiecesMan.CheckCheck(1, Main.mouseSquare.x,Main.mouseSquare.y,-1,-1));
        
        
        if(e.getKeyCode() == KeyEvent.VK_B)
            System.out.println(PiecesMan.CheckCheck(0, Main.mouseSquare.x,Main.mouseSquare.y,-1,-1));
    }

    @Override
    public void keyReleased(KeyEvent e){}
    
    
    
    void ChecknStale()
    {
        Main.board[Main.mouseSquare.y][Main.mouseSquare.x] = new int[]{1,Main.pointerPiece[1],Main.pointerPiece[2]};
                    
        Main.board[Main.pointerPiece[4]][Main.pointerPiece[3]][0] = 0;

        int kingX;
        int kingY;
        Main.pointerPiece[0] = 0;
        if(Main.turn == 1)
        {
            PiecesMan.passantB[0] = 0;
            kingX = PiecesMan.bKingPos.x;
            kingY = PiecesMan.bKingPos.y;
            Main.turn = 0;
        }else
        {
            PiecesMan.passantW[0] = 0;
            kingX = PiecesMan.wKingPos.x;
            kingY = PiecesMan.wKingPos.y;
            Main.turn = 1;
        }

        if(PiecesMan.CheckCheck(Main.turn, kingX, kingY, -1, -1))
        {

            int[][] checkPieces = PiecesMan.checkPieces;

            boolean mate = true;

            if(BK(kingY+1, kingX)) mate = false;
            if(BK(kingY+1, kingX+1)) mate = false;
            if(BK(kingY, kingX+1)) mate = false;
            if(BK(kingY-1, kingX+1)) mate = false;
            if(BK(kingY-1, kingX)) mate = false;
            if(BK(kingY-1, kingX-1)) mate = false;
            if(BK(kingY, kingX-1)) mate = false;
            if(BK(kingY+1, kingX-1)) mate = false;

            // testing if blockable
            if(mate && checkPieces.length == 1)
            {
                if(PiecesMan.BlockCheck(kingX, kingY, checkPieces[0][2], checkPieces[0][3]))
                {
                    mate = false;
                }
            }

            //System.out.println(mate);

            if(mate) 
                if(Main.turn == 0) 
                {
                    Main.EndGame("White Wins");
                }else  
                    Main.EndGame("Black Wins");

        }else
        {
            boolean stale = true;
            System.out.println("test stale");
            if(BK(kingY+1, kingX)) stale = false;
            if(BK(kingY+1, kingX+1)) stale = false;
            if(BK(kingY, kingX+1)) stale = false;
            if(BK(kingY-1, kingX+1)) stale = false;
            if(BK(kingY-1, kingX)) stale = false;
            if(BK(kingY-1, kingX-1)) stale = false;
            if(BK(kingY, kingX-1)) stale = false;
            if(BK(kingY+1, kingX-1)) stale = false;

            if(stale)
            { 
                for(int y = 0; y < 8;y++)
                {
                    for(int x = 0; x < 8; x++)
                    { //System.out.println("looped");
                        if(Main.board[y][x][0] == 1 && Main.board[y][x][2] == Main.turn)
                        {

                            PiecesMan.CheckCheck(Main.turn, x, y, -2, -2);

                            switch(Main.board[y][x][1])
                            {
                                case 0 -> //pawn
                                {


                                    if(!PiecesMan.pinCheck(new int[][]{{0, Main.turn, x, y}}, kingX, kingY))
                                    {
                                       if(Main.turn == 0)
                                        {
                                            if(SP(y+1, x, false) | SP(y+1, x-1, true) | SP(y+1, x+1, true))
                                                stale = false;
                                        }else
                                        {
                                            if(SP(y-1, x, false) | SP(y-1, x-1, true) | SP(y-1, x+1, true))
                                                stale = false;
                                        }
                                    }
                                }
                                case 1 -> // rook
                                {
                                    if(!PiecesMan.pinCheck(new int[][]{{0, Main.turn, x, y}}, kingX, kingY))
                                    {
                                        if(SC(y+1, x)) stale = false;
                                        if(SC(y, x+1)) stale = false;
                                        if(SC(y-1, x)) stale = false;
                                        if(SC(y, x-1)) stale = false;
                                    }
                                }
                                case 3 -> // bishop
                                {
                                    if(!PiecesMan.pinCheck(new int[][]{{0, Main.turn, x, y}}, kingX, kingY))
                                    {
                                        if(SC(y+1, x+1)) stale = false;
                                        if(SC(y-1, x+1)) stale = false;
                                        if(SC(y-1, x-1)) stale = false;
                                        if(SC(y+1, x-1)) stale = false;
                                    }
                                }
                                case 4 -> // queen
                                {
                                    if(!PiecesMan.pinCheck(new int[][]{{0, Main.turn, x, y}}, kingX, kingY))
                                    {
                                        if(SC(y+1, x)) stale = false;
                                        if(SC(y+1, x+1)) stale = false;
                                        if(SC(y, x+1)) stale = false;
                                        if(SC(y-1, x+1)) stale = false;
                                        if(SC(y-1, x)) stale = false;
                                        if(SC(y-1, x-1)) stale = false;
                                        if(SC(y+1, x-1)) stale = false;
                                        if(SC(y, x-1)) stale = false;
                                    }
                                }
                            }
                        }
                    }
                }
            }


            if(stale)
                Main.EndGame("Stale Mate");
        }
    }
    
    
    
    

    boolean BK(int y, int x)
    {
        if(x>7 | x<0 | y>7 | y<0)
            return false;
        
        if((Main.board[y][x][0] == 1 & Main.board[y][x][2] == Main.turn) ||
                PiecesMan.CheckCheck(Main.turn, x, y, -1, -1))
        {
            
            if(Main.board[y][x][2] == (Main.turn-1)*-1)
            {
                return(PiecesMan.CheckCheck((Main.turn-1)*-1, x, y, -3, -3));
            }else
                return false;
        }else
            return true;
    }
    
    boolean SC(int y, int x)
    {
        if(x>7 | x<0 | y>7 | y<0)
            return false;
        
        return Main.board[y][x][0] == 0 || (Main.board[y][x][0] == 1 & Main.board[y][x][2] == (Main.turn-1)*-1);
    }
    
    boolean SP(int y, int x, boolean attack)
    {
        if(x>7 | x<0 | y>7 | y<0)
            return false;
        
        if(attack)
        {
            return Main.board[y][x][0] == 1 & Main.board[y][x][2] == (Main.turn-1)*-1;
        }else
        {
            return Main.board[y][x][0] == 0;
        }
    }
}
