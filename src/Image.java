import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class Pixel{

    public int R;
    public int G;
    public int B;

    public Pixel(int r, int g, int b)
    {
        R = r;
        G = g;
        B = b;
    }

    public int getR() {
        return R;
    }

    public void setR(int r) {
        R = r;
    }

    public int getG() {
        return G;
    }

    public void setG(int g) {
        G = g;
    }

    public int getB() {
        return B;
    }

    public void setB(int b) {
        B = b;
    }
}

public class Image
{
    private Pixel [][] imageArray;
    private BufferedImage image;

    public Image(String fileName)
    {
        image = null;

        try {
            image = ImageIO.read(new File("src/"+fileName + ".jpg"));
        }
        catch (IOException e)
        {
            System.out.println("Can't find the image");
            System.exit(0);
        }

        int width = image.getWidth();
        int height = image.getHeight();
        int[] imagePixels = image.getRGB(0, 0, width, height, null, 0, width);

        imageArray = new Pixel[height][width];

        int countW = 0;
        int countH = 0;

        for (int i = 0; i < imagePixels.length; i++)
        {
            Color pixel = new Color(imagePixels[i]);

            if(i % width == 0 && i != 0)
            {
                countW = 0;
                countH++;
            }

            imageArray[countH][countW] = new Pixel(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
            countW++;
        }
    }

    public Pixel[][] getImageArray()
    {
        return imageArray;
    }

    public void outputPixelArrayToImage(Pixel[][] newImage, String outFileName)
    {
        BufferedImage image2 = new BufferedImage(newImage[0].length, newImage.length, BufferedImage.TYPE_3BYTE_BGR);
        for(int i = 0; i < newImage.length; i++)
        {
            for(int j = 0; j < newImage[0].length; j++)
            {
                int a = 255;
                int r = newImage[i][j].R;
                int g = newImage[i][j].G;
                int b = newImage[i][j].B;
                int p = (a<<24) | (r<<16) | (g<<8) | b;
                image2.setRGB(j, i, p);
            }
        }

        try {
            File f = new File("src/" + outFileName + ".jpg");
            ImageIO.write(image2, "jpg", f);
        }
        catch (IOException e)
        {
            System.out.println("Problems writing new image");
        }
    }


}
