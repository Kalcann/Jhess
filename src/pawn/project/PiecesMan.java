package pawn.project;

import java.awt.Dimension;
import java.awt.Point;


public class PiecesMan
{
    
    public static final int PAWN = 0;
    public static final int ROOK = 1;
    public static final int KNIGHT = 2;
    public static final int BISHOP = 3;
    public static final int QUEEN = 4;
    public static final int KING = 5;
    
        
    
    public static String reason = "Valid";
    
    //  {a,b,c,d,e}
    //   a = is there a passant
    //   b = passant x
    //   c = passant y
    //   d = piece x
    //   e = piece y
    public static int[] passantW = {0,0,0};
    public static int[] passantB = {0,0,0};
    
    
    
    public static Point wKingPos = new Point(4,7);
    public static Point bKingPos = new Point(4,0);
    static int checkCol;
    
    public static int[][] checkPieces;
    public static int[] passanted;
    
    public static Boolean ValidateMove(int piece, int colour, int startX, int startY, int endX, int endY)
    {
        checkCol = colour;
        
        if((startX == endX & startY == endY) || (endX < 0 | endX >= 8 | endY < 0 | endY >= 8))
        {
            reason = "Turn Cancelled";
            return false;
        }
        if(Main.board[endY][endX][2] == colour & Main.board[endY][endX][0] == 1)
        {
            reason = "same colour";
            return false;
        }
        
                int tempX = -1;
        int tempY = -1;
            // getting king possitions
        for(int y = 0; y < 8; y++)
           for(int x = 0; x < 8; x++)
           {
               if(Main.board[y][x][0] == 1 && Main.board[y][x][1] == 5 && Main.board[y][x][2] == colour)
               {
                   tempX = x;
                   tempY = y;
               }
           }
        if(tempX == -1 & tempY == -1)
        {   // if the king is not found it is on the pointer
            if(Main.pointerPiece[0] == 1 && Main.pointerPiece[1] == 5 && Main.pointerPiece[2] == colour)
            {
                tempX = Main.mouseSquare.x;
                tempY = Main.mouseSquare.y;
            }
        }
        
        if(colour == 0)
        {
            bKingPos = new Point(tempX,tempY);
        }else
        {
            wKingPos = new Point(tempX,tempY);
        }
        
        
        if(CheckCheck(colour,tempX,tempY,endX,endY))
        {
            reason = "In Check";
            return false;
        }
        
        reason = "Invalid move";
        int lineTest;
        int lineTest2; 
        passanted = new int[]{-1,-1};
       switch(piece)
        {
            //  why are pawns sooooo complicated
            case PAWN ->
            {
                if(endX > startX+1 | endX < startX-1)
                    return false;
                
                if(colour == 1)
                {
                    if(endY > startY)
                        return false;
                    
                    if(endY < startY-1)
                    {
                        if(endY != 4 | Main.board[endY][endX][0] == 1 | Main.board[endY+1][endX][0] == 1)
                        {
                            return false;
                        }else
                        {
                            passantW = new int[]{1,endX,endY};
                        }
                    }
                    
                    
                    if(Main.board[endY][endX][0] == 1)
                    {
                        if(startX == endX)
                                return false;
                    }else
                    {
                        if(startX != endX)
                            if(passantB[0] == 1 && passantB[1] == endX & passantB[2]-1 == endY)
                            {
                                Main.board[endY+1][endX][0] = 0;
                                passanted = new int[]{endX,endY+1};
                            }else
                                return false;
                    }
                    
                    if(endY == 0)
                    {
                        Main.buttons[0].icon = Main.assets.getScaledPiece(4, 1, new Dimension(100, 100));
                        Main.buttons[1].icon = Main.assets.getScaledPiece(1, 1, new Dimension(100, 100));
                        Main.buttons[2].icon = Main.assets.getScaledPiece(3, 1, new Dimension(100, 100));
                        Main.buttons[3].icon = Main.assets.getScaledPiece(2, 1, new Dimension(100, 100));
                        Main.game = Main.GameState.Promote;
                    }
                    
                }else
                {
                    if(endY < startY)
                        return false;
                    
                    if(endY > startY+1)
                    {
                        if(endY != 3 | Main.board[endY][endX][0] == 1 | Main.board[endY-1][endX][0] == 1)
                        {
                            return false;
                        }else
                            {
                                passantB = new int[]{1,endX,endY};
                            }
                    }
                    
                    if(Main.board[endY][endX][0] == 1)
                    {
                        if(startX == endX)
                            return false;
                    }else
                    {
                        if(startX != endX)
                            if(passantW[0] == 1 && passantW[1] == endX & passantW[2]+1 == endY)
                            {
                                Main.board[endY-1][endX][0] = 0;
                                passanted = new int[]{endX,endY-1};
                            }else
                                return false;
                    }
                    
                    if(endY == 7)
                    {
                        Main.buttons[0].icon = Main.assets.getScaledPiece(4, 0, new Dimension(100, 100));
                        Main.buttons[1].icon = Main.assets.getScaledPiece(1, 0, new Dimension(100, 100));
                        Main.buttons[2].icon = Main.assets.getScaledPiece(3, 0, new Dimension(100, 100));
                        Main.buttons[3].icon = Main.assets.getScaledPiece(2, 0, new Dimension(100, 100));
                        Main.game = Main.GameState.Promote;
                    }
                }
                    
            }
            case ROOK ->
            {
                if(startX != endX & startY != endY)
                    return false;
                if(endY > startY)
                {
                    lineTest = endY-1;
                    while(lineTest != startY)
                    {
                        if(Main.board[lineTest][startX][0] == 1)
                            return false;
                        lineTest -= 1;
                    }
                }else
                if(endY < startY)
                {
                    lineTest = endY+1;
                    while(lineTest != startY)
                    {
                        if(Main.board[lineTest][startX][0] == 1)
                            return false;
                        lineTest += 1;
                    }
                }else
                if(endX > startX)
                {
                    lineTest = endX-1;
                    while(lineTest != startX)
                    {
                        if(Main.board[startY][lineTest][0] == 1)
                            return false;
                        lineTest -= 1;
                    }
                }else
                {
                    lineTest = endX+1;
                    while(lineTest != startX)
                    {
                        if(Main.board[startY][lineTest][0] == 1)
                            return false;
                        lineTest += 1;
                    }
                }
                 
            if(colour == 0)
            {
                switch (Main.bCastle) {
                    case 1 -> {
                        if(startX == 0)
                        {
                            Main.bCastle = 3;
                        }else
                            Main.bCastle = 2;
                    }
                    case 2 -> {
                        if(startX == 0 & startY == 0)
                            Main.bCastle = 0;
                    }
                    case 3 -> {
                        if(startX == 7 & startY == 0)
                            Main.bCastle = 0;
                    }
                }
            }else
                switch (Main.wCastle) {
                    case 1 -> {
                        if(startX == 0)
                        {
                            Main.wCastle = 3;
                        }else
                            Main.wCastle = 2;
                    }
                    case 2 -> {
                        if(startX == 0 & startY == 7)
                            Main.wCastle = 0;
                    }
                    case 3 -> {
                        if(startX == 7 & startY == 7)
                            Main.wCastle = 0;
                    }
                }
                    
            }
            case KNIGHT ->
            {   
                // what a beautiful test
                if(!((endY == startY+2 & (endX == startX-1 | endX == startX+1)) |
                        (endY == startY+1 & (endX == startX-2 | endX == startX+2)) |
                        (endY == startY-1 & (endX == startX-2 | endX == startX+2)) |
                        (endY == startY-2 & (endX == startX-1 |endX == startX+1 ))))
                    return false;
            }
            case BISHOP ->
            {
                if(Math.abs((double)(endX-startX)/(endY-startY)) != 1)
                    return false;
                
                if(endX > startX)
                {
                    lineTest = endX-1;
                    
                    if(endY > startY)
                    {
                        lineTest2 = endY-1;
                        while(startX != lineTest)
                        {
                            if(Main.board[lineTest2][lineTest][0] == 1)
                                return false;
                            lineTest -= 1;
                            lineTest2 -= 1;
                        }
                    }else
                    {
                        lineTest2 = endY+1;
                        while(startX != lineTest)
                        {
                            if(Main.board[lineTest2][lineTest][0] == 1)
                                return false;
                            lineTest -= 1;
                            lineTest2 += 1;
                        }
                    }
                    
                    
                }else
                {
                    lineTest = endX+1;
                    
                    if(endY > startY)
                    {
                        lineTest2 = endY-1;
                        while(startX != lineTest)
                        {
                            if(Main.board[lineTest2][lineTest][0] == 1)
                                return false;
                            lineTest += 1;
                            lineTest2 -= 1;
                        }
                    }else
                    {
                        lineTest2 = endY+1;
                        while(startX != lineTest)
                        {
                            if(Main.board[lineTest2][lineTest][0] == 1)
                                return false;
                            lineTest += 1;
                            lineTest2 += 1;
                        }
                    }
                }
            }
            case QUEEN ->
            {
                if(startX != endX & startY != endY & Math.abs((double)(endX-startX)/(endY-startY)) != 1)
                    return false;
                if(startX == endX | startY == endY)
                {
                    //reused rook code not bothering reformating
                    if(endY > startY)
                {
                    lineTest = endY-1;
                    while(lineTest != startY)
                    {
                        if(Main.board[lineTest][startX][0] == 1)
                            return false;
                        lineTest -= 1;
                    }
                }else
                if(endY < startY)
                {
                    lineTest = endY+1;
                    while(lineTest != startY)
                    {
                        if(Main.board[lineTest][startX][0] == 1)
                            return false;
                        lineTest += 1;
                    }
                }else
                if(endX > startX)
                {
                    lineTest = endX-1;
                    while(lineTest != startX)
                    {
                        if(Main.board[startY][lineTest][0] == 1)
                            return false;
                        lineTest -= 1;
                    }
                }else
                {
                    lineTest = endX+1;
                    while(lineTest != startX)
                    {
                        if(Main.board[startY][lineTest][0] == 1)
                            return false;
                        lineTest += 1;
                    }
                }
                }else
                {
                    //reused bishop code not bothering reformating
                    if(endX > startX)
                {
                    lineTest = endX-1;
                    
                    if(endY > startY)
                    {
                        lineTest2 = endY-1;
                        while(startX != lineTest)
                        {
                            if(Main.board[lineTest2][lineTest][0] == 1)
                                return false;
                            lineTest -= 1;
                            lineTest2 -= 1;
                        }
                    }else
                    {
                        lineTest2 = endY+1;
                        while(startX != lineTest)
                        {
                            if(Main.board[lineTest2][lineTest][0] == 1)
                                return false;
                            lineTest -= 1;
                            lineTest2 += 1;
                        }
                    }
                    
                    
                }else
                {
                    lineTest = endX+1;
                    
                    if(endY > startY)
                    {
                        lineTest2 = endY-1;
                        while(startX != lineTest)
                        {
                            if(Main.board[lineTest2][lineTest][0] == 1)
                                return false;
                            lineTest += 1;
                            lineTest2 -= 1;
                        }
                    }else
                    {
                        lineTest2 = endY+1;
                        while(startX != lineTest)
                        {
                            if(Main.board[lineTest2][lineTest][0] == 1)
                                return false;
                            lineTest += 1;
                            lineTest2 += 1;
                        }
                    }
                }
                }
            }
            case KING ->
            {
                if(endX < startX-1 | endX > startX+1 | endY < startY-1 | endY > startY+1)
                {
                    if(endX == startX-2)
                    { 
                        if(colour == 0)
                        {
                            if(startY != 0)
                                return false;
                            if(Main.bCastle == 0 | Main.bCastle == 3)
                                return false;
                        }else
                        {
                            if(startY != 7)
                                return false;
                            if(Main.wCastle == 0 | Main.wCastle == 3)
                                return false;
                        }
                        
                        if(Main.board[startY][1][0] == 1 | Main.board[startY][2][0] == 1 | Main.board[startY][3][0] == 1)
                            return false;
                        
                        
                        if(CheckCheck(colour,4,startY,-1,-1) || CheckCheck(colour,3,startY,-1,-1) ||
                           CheckCheck(colour,2,startY,-1,-1) || CheckCheck(colour,1,startY,-1,-1))
                            return false;
                        
                        // survived
                        if(colour == 0)
                        {
                            Main.board[0][0][0] = 0;
                            Main.board[0][3] = new int[]{1,1,0};
                            Main.bCastle = 0;
                            bKingPos = new Point(endX,endY);
                        }else
                        {
                            Main.board[7][0][0] = 0;
                            Main.board[7][3] = new int[]{1,1,1};
                            Main.wCastle = 0;
                            wKingPos = new Point(endX,endY);
                        }
                            
                    }else
                    if(endX == startX+2)
                    {
                        if(colour == 0)
                        {
                            if(startY != 0)
                                return false;
                            if(Main.bCastle == 0 | Main.bCastle == 2)
                                return false;
                        }else
                        {
                            if(startY != 7)
                                return false;
                            if(Main.wCastle == 0 | Main.wCastle == 2)
                                return false;
                        }
                        
                        if(Main.board[startY][6][0] == 1 | Main.board[startY][5][0] == 1)
                            return false;
                        
                        
                        if(CheckCheck(colour,4,startY,-1,-1) || CheckCheck(colour,5,startY,-1,-1) ||
                           CheckCheck(colour,6,startY,-1,-1))
                            return false;
                        
                        // survived
                        if(colour == 0)
                        {
                            Main.board[0][7][0] = 0;
                            Main.board[0][5] = new int[]{1,1,0};
                            Main.bCastle = 0;
                        }else
                        {
                            Main.board[7][7][0] = 0;
                            Main.board[7][5] = new int[]{1,1,1};
                            Main.wCastle = 0;
                        }
                    }else
                        return false;
                    
                    if(colour == 0)
                    {
                        Main.bCastle = 0;
                    }else
                        Main.wCastle = 0;
                }
            }
        }

        
        
        // all checks have been false therefor move is valid
        reason = "Valid";
            //setting the last move for the move colours
        if(Main.board[endY][endX][0] == 0)
        {
            Main.lastMove[0] = 1;
        }else
            Main.lastMove[0] = 2;
        
        if(Main.turn == 0)
        {
            Main.lastMove[1] = startX;
            Main.lastMove[2] = startY;
            Main.lastMove[3] = endX;
            Main.lastMove[4] = endY;
        }else
        {
            Main.lastMove[1] = 7-startX;
            Main.lastMove[2] = 7-startY;
            Main.lastMove[3] = 7-endX;
            Main.lastMove[4] = 7-endY;
        }
        
        return true;
    }
    
    static int placeHold[] = {0,0,0,0,0,0};
    
    public static void KCheck()
    {
        if(placeHold[5] == 1)
        {
            Main.board[placeHold[4]][placeHold[3]] = new int[]{placeHold[0],placeHold[1],placeHold[2]};
            placeHold[5] = 0;
        }
    }
    
    public static boolean CheckCheck(int colour,int x,int y, int PointX, int PointY)
    {
        
        checkCol = colour;
        
        // placing the piece temporarily to account for it's check
        if(Main.pointerPiece[0] == 1 & PointX >= 0)
        {
            placeHold = new int[]{Main.board[PointY][PointX][0],Main.board[PointY][PointX][1],Main.board[PointY][PointX][2],PointX,PointY,1};
            Main.board[PointY][PointX] = new int[]{1,Main.pointerPiece[1],Main.pointerPiece[2]};
        }else
        {
            if(colour == 0)
            {
                placeHold = new int[]{1, 5, colour, bKingPos.x, bKingPos.y, 1};
                Main.board[bKingPos.y][bKingPos.x] = new int[]{0,0,0};
            }else
            {
                placeHold = new int[]{1, 5, colour, wKingPos.x, wKingPos.y, 1};
                Main.board[wKingPos.y][wKingPos.x] = new int[]{0,0,0};
            }
            
        }
        
        // important
        checkPieces = null;
        
        
        if(y>7 | y<0 | x>8 | x<0)
            return false;
        
            // first the pawn check
        if(colour == 0)
        {
            if(y != 7)
            {
                if(PointX == -2)
                {
                    if(Main.board[y+1][x][0] == 1 && Main.board[y+1][x][1] == 0 && Main.board[y+1][x][2] == 1)
                    {
                        KCheck();
                        return true;
                    }else
                    if(y+2 == 6 && Main.board[y+2][x][0] == 1 && Main.board[y+2][x][1] == 0 && Main.board[y+2][x][2] == 1)
                    {
                        KCheck();
                        return true;
                    }

                }else
                {
                    if(x != 7)
                        if(Main.board[y+1][x+1][0] == 1 && Main.board[y+1][x+1][1] == 0 && Main.board[y+1][x+1][2] == 1)
                            addCheckPiece(0,1,x+1,y+1);
                    if(x != 0)
                        if(Main.board[y+1][x-1][0] == 1 && Main.board[y+1][x-1][1] == 0 && Main.board[y+1][x-1][2] == 1)
                            addCheckPiece(0,1,x-1,y+1);
                }
            }      
        }else
        { 
            
            if(y != 0)
            {
                if(PointX == -2)
                {
                    if(Main.board[y-1][x][0] == 1 && Main.board[y-1][x][1] == 0 && Main.board[y-1][x][2] == 1)
                    {
                        KCheck();
                        return true;
                    }else
                    if(y-2 == 1 && Main.board[y-2][x][0] == 1 && Main.board[y-2][x][1] == 0 && Main.board[y-2][x][2] == 1)
                    {
                        KCheck();
                        return true;
                    }

                }else
                {
                    if(x != 7)
                        if(Main.board[y-1][x+1][0] == 1 && Main.board[y-1][x+1][1] == 0 && Main.board[y-1][x+1][2] == 0)
                        {
                            addCheckPiece(0,0,x+1,y-1);
                        }
                    if(x != 0)
                        if(Main.board[y-1][x-1][0] == 1 && Main.board[y-1][x-1][1] == 0 && Main.board[y-1][x-1][2] == 0)
                        {
                            addCheckPiece(0,0,x-1,y-1);
                        }
                }
            }
        }
        
        // knight check (a very beutiful one might I add)
        
        if(BK(x-2,y+1)) addCheckPiece(2,(colour-1)*-1,x-2,y+1);
        if(BK(x-1,y+2)) addCheckPiece(2,(colour-1)*-1,x-2,y+1);
        if(BK(x+1,y+2)) addCheckPiece(2,(colour-1)*-1,x-2,y+1);
        if(BK(x+2,y+1)) addCheckPiece(2,(colour-1)*-1,x-2,y+1);
        if(BK(x+2,y-1)) addCheckPiece(2,(colour-1)*-1,x-2,y+1);
        if(BK(x+1,y-2)) addCheckPiece(2,(colour-1)*-1,x-2,y+1);
        if(BK(x-1,y-2)) addCheckPiece(2,(colour-1)*-1,x-2,y+1);
        if(BK(x-2,y-1)) addCheckPiece(2,(colour-1)*-1,x-2,y+1);
        
        
            //rook or queen straight check
        int loop = 0;
        for(int a = 0; a < 4; a++)
        {
            switch(a){
                case 0 -> 
                {
                    loop = 7 - y;
                }
                case 1 ->
                {
                    loop = 7 - x;
                }
                case 2 ->
                {
                    loop = y;
                }
                case 3 ->
                {
                    loop = x;
                }
            }
            boolean blocked = false;
            for(int b = 0; b < loop; b++)
            {

                switch(a){
                    case 0 -> 
                    {
                        if(!blocked)
                            if(BR(x, y+b+1))
                            {
                                addCheckPiece(1,(colour-1)*-1,x,y+b+1);
                            }else
                            if(Main.board[y+b+1][x][0] == 1)
                                blocked = true;
                    }
                    case 1 ->
                    {
                        if(!blocked)
                            if(BR(x+b+1, y))
                            {
                                addCheckPiece(1,(colour-1)*-1,x+b+1,y);
                            }else
                            if(Main.board[y][x+b+1][0] == 1)
                                blocked = true;
                    }
                    case 2 ->
                    {
                        if(!blocked)
                            if(BR(x, y-b-1))
                            {
                                addCheckPiece(1,(colour-1)*-1,x,y-b-1);
                            }else
                            if(Main.board[y-b-1][x][0] == 1)
                                blocked = true;
                    }
                    case 3 ->
                    {
                        if(!blocked)
                            if(BR(x-b-1, y))
                            {
                                addCheckPiece(1,(colour-1)*-1,x-b-1,y);
                            }else
                            if(Main.board[y][x-b-1][0] == 1)
                                blocked = true;
                    }
                }  
            }
        }
        
        loop = 0;
            // bishop or queen diagonal check
        for(int a = 0; a < 4; a++)
        {
            switch(a){
                case 0 -> 
                {
                    if(7-y<7-x)
                    {
                        loop = 7 - y;
                    }else
                        loop = 7 - x;
                }
                case 1 ->
                {
                    if(y<7-x)
                    {
                        loop = y;
                    }else
                        loop = 7-x;
                }
                case 2 ->
                {
                    if(y<x)
                    {
                        loop = y;
                    }else
                        loop = x;
                }
                case 3 ->
                {
                    if(7-y<x)
                    {
                        loop = 7 - y;
                    }else
                        loop = x;
                }
            }
            boolean blocked = false;
            for(int b = 0; b < loop; b++)
            {

                switch(a){
                    case 0 -> 
                    {
                        if(!blocked)
                            if(BB(x+b+1, y+b+1))
                            {
                                addCheckPiece(3,(colour-1)*-1,x+b+1,y+b+1);
                            }else
                            if(Main.board[y+b+1][x+b+1][0] == 1)
                                blocked = true;
                    }
                    case 1 ->
                    {
                        if(!blocked)
                            if(BB(x+b+1, y-b-1))
                            {
                                addCheckPiece(3,(colour-1)*-1,x+b+1,y-b-1);
                            }else
                            if(Main.board[y-b-1][x+b+1][0] == 1)
                                blocked = true;
                    }
                    case 2 ->
                    {
                        if(!blocked)
                            if(BB(x-b-1, y-b-1))
                            {
                                addCheckPiece(3,(colour-1)*-1,x-b-1,y-b-1);
                            }else
                            if(Main.board[y-b-1][x-b-1][0] == 1)
                                blocked = true;
                    }
                    case 3 ->
                    {
                        if(!blocked)
                            if(BB(x-b-1, y+b+1))
                            {
                                addCheckPiece(3,(colour-1)*-1,x-b-1,y+b+1);
                            }else
                            if(Main.board[y+b+1][x-b-1][0] == 1)
                                blocked = true;
                    }
                }  
            }
        }
        // and finally the king check!!
        if(PointX != -3)
        {
            if(BKi(y+1, x)) addCheckPiece(5,(colour-1)*-1,x,y+1);
            if(BKi(y+1, x+1)) addCheckPiece(5,(colour-1)*-1,x+1,y+1);
            if(BKi(y, x+1)) addCheckPiece(5,(colour-1)*-1,x+1,y);
            if(BKi(y-1, x+1)) addCheckPiece(5,(colour-1)*-1,x+1,y-1);
            if(BKi(y-1, x)) addCheckPiece(5,(colour-1)*-1,x,y-1);
            if(BKi(y-1, x-1)) addCheckPiece(5,(colour-1)*-1,x-1,y-1);
            if(BKi(y, x-1)) addCheckPiece(5,(colour-1)*-1,x-1,y);
            if(BKi(y+1, x-1)) addCheckPiece(5,(colour-1)*-1,x-1,y+1);
        }
        
        
        if(checkPieces != null) 
        {
            KCheck();
            return true;
        }else
        {
            KCheck();
            return false;
        }
    }
    
    public static boolean BlockCheck(int startX, int startY, int endX, int endY)
    {
        if(CheckCheck((Main.turn-1)*-1, endX, endY, -1, -1))
            return true;
        
        int xChange = 0;
        int yChange = 0;
        int loop;
        
        
        if(Main.board[endX][endY][2] == 1)
        {   //rook/queen
            if(endX == startX)
            {
                loop = Math.abs(endY-startY);
                yChange = (endY-startY)/loop;
            }else
            {
                loop = Math.abs(endX-startX);
                xChange = (endX-startX)/loop;
            }
                
        }else
        { //bishop/queen
            if(endX>startX)
            {
                loop = endX-startX;
                xChange = +1;
            }else
            {
                loop = startX - endX;
                xChange = -1;
            }
            if(endY>startY)
            {
                yChange = +1;
            }else
                yChange = -1;
        } 
        
        
        
        for(int i = 1; i<=loop;i++)
        {
            if(CheckCheck((Main.turn-1)*-1,(startX+i*xChange), (startY+i*yChange), -2, -2))
            {
                return !pinCheck(checkPieces, startX, startY);
            }
        }
        
        return false;
    }
    
    static boolean pinCheck(int[][] pieces,int kingX,int kingY)
    {
        boolean pinned = false;
        
        for (int[] piece : pieces) 
        {
            if(CheckCheck(piece[1], piece[2], piece[3], -1,-1))
            { 
                for(int[] cPiece : checkPieces)
                { 
                    if(cPiece[0] == 1)
                    {
                        pinned = (cPiece[2] == piece[2] & cPiece[2] == kingX) || (cPiece[3] == piece[3] & cPiece[3] == kingY);
                    }
                    if(cPiece[0] == 3)
                    {
                        pinned = Math.abs(cPiece[2]-piece[2])/(cPiece[3]-piece[3]) == 1 & Math.abs(cPiece[2]-kingX)/(cPiece[3]-kingY) == 1;
                    }
                }
            }
        }
        return pinned;
    }
    
    
    static boolean BK(int x, int y)
    { 
        if(x>7 | y>7 | x<0 | y<0)
            return false;
        
        if(checkCol == 0)
        {
            if(Main.board[y][x][0] == 1 && Main.board[y][x][1] == 2 && Main.board[y][x][2] == 1)
                return true;
        }else
            if(Main.board[y][x][0] == 1 && Main.board[y][x][1] == 2 && Main.board[y][x][2] == 0)
                return true;
    return false;
    }
    
    static boolean BKi(int y, int x)
    { 
        if(x>7 | y>7 | x<0 | y<0)
            return false;
        
        if(checkCol == 0)
        {
            if(Main.board[y][x][0] == 1 && Main.board[y][x][1] == 5 && Main.board[y][x][2] == 1)
                return true;
        }else
            if(Main.board[y][x][0] == 1 && Main.board[y][x][1] == 5 && Main.board[y][x][2] == 0)
                return true;
    return false;
    }
    
    static boolean BR(int x, int y)
    { 
        if(checkCol == 0)
        {
            if(Main.board[y][x][0] == 1 && (Main.board[y][x][1] == 1 | Main.board[y][x][1] == 4) && Main.board[y][x][2] == 1)
                return true;
        }else
            if(Main.board[y][x][0] == 1 && (Main.board[y][x][1] == 1 | Main.board[y][x][1] == 4) && Main.board[y][x][2] == 0)
                return true;
    return false;
    }
    
    static boolean BB(int x, int y)
    { 
        if(checkCol == 0)
        {
            if(Main.board[y][x][0] == 1 && (Main.board[y][x][1] == 3 | Main.board[y][x][1] == 4) && Main.board[y][x][2] == 1)
                return true;
        }else
            if(Main.board[y][x][0] == 1 && (Main.board[y][x][1] == 3 | Main.board[y][x][1] == 4) && Main.board[y][x][2] == 0)
                return true;
    return false;
    }
    
    static void addCheckPiece(int piece, int colour, int x, int y)
    {
        int[][] tempArr;
        if(checkPieces == null)
        {
            tempArr = new int[1][4];
        }else
        {
            tempArr = new int[checkPieces.length+1][4];
            System.arraycopy(checkPieces, 0, tempArr, 0, checkPieces.length);
        }
        
        tempArr[tempArr.length-1] = new int[] {piece,colour,x,y};
        checkPieces = tempArr;
    }
            
}
