package pawn.project;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class AssetManager
{
    static BufferedImage Pieces;
    
    public static Image[] BlackPieces = new Image[6];
    public static Image[] WhitePieces = new Image[6];
    
    
    AssetManager() throws IOException
    {
        Pieces = ImageIO.read(new File("assets/basic.png"));
        
        int xSub = Pieces.getWidth()/6;
        int ySub = Pieces.getHeight()/2;
        
        for(int i = 0; i<6; i++)
        {
            WhitePieces[i] = Pieces.getSubimage(i*xSub, 0, xSub, ySub);
            BlackPieces[i] = Pieces.getSubimage(i*xSub, ySub, xSub, ySub);
        }
    }
    
    public Image getScaledPiece(int piece, int colour, Dimension scale)
    {
        if(colour==1)
        {
            return WhitePieces[piece].getScaledInstance(scale.width, scale.height, Image.SCALE_DEFAULT);
        }else   
            return BlackPieces[piece].getScaledInstance(scale.width, scale.height, Image.SCALE_DEFAULT);
    }
    
    public void switchPieces(String fileName) throws IOException
    {
        Pieces = ImageIO.read(new File("assets/"+fileName));
        
        int xSub = Pieces.getWidth()/6;
        int ySub = Pieces.getHeight()/2;
        
        for(int i = 0; i<6; i++)
        {
            WhitePieces[i] = Pieces.getSubimage(i*xSub, 0, xSub, ySub);
            BlackPieces[i] = Pieces.getSubimage(i*xSub, ySub, xSub, ySub);
        }
    }
}
