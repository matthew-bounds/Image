public class Main {

    public static void main(String[] args){
        blur();
        //compression();

    }

    public static void blur()
    {
        String fileName = "test";
        Image im = new Image(fileName);

        Pixel[][] arr = im.getImageArray();

        // create a new pixel array to assure I am properly blurring from the original image
        Pixel[][] newArr = new Pixel[arr.length][arr[0].length];

        // the range around the pixel to find the average
        int range = 5;

        for(int i = 0; i < arr.length; i++)
        {
            for(int j = 0; j < arr[i].length; j++)
            {
                Pixel averagePixel = new Pixel(0,0,0);
                int amountOfPixels = 0;

                // determine where to start and assure we don't go into negative elements
                int startRow = Math.max(i - range, 0);

                for(int r = startRow; r < i + range + 1 && r < arr.length; r++)
                {
                    int startCol = Math.max(j - range, 0);

                    for(int c = startCol; c < j + range + 1 && c < arr[i].length; c++)
                    {
                        averagePixel.R += arr[r][c].R;
                        averagePixel.G += arr[r][c].G;
                        averagePixel.B += arr[r][c].B;
                        amountOfPixels++;
                    }
                }
                averagePixel.R /= amountOfPixels;
                averagePixel.G /= amountOfPixels;
                averagePixel.B /= amountOfPixels;

                // place the new average pixel in the newArr
                newArr[i][j] = averagePixel;
            }
        }

        im.outputPixelArrayToImage(newArr, fileName+"_blurred_"+range);
    }

    public static void compression()
    {
        Image im = new Image("original");

        Pixel[][] arr = im.getImageArray();

        // determine the new dimensions of the image
        // NOTE: assumes a square image
        int newWidth = arr.length / 3;
        int newHeight = arr[0].length / 3;

        // new pixel array with new image dimensions
        Pixel[][] newArr = new Pixel[newWidth][newHeight];

        // the range around the pixel to find the average
        int rangeX = arr.length / newWidth;
        int rangeY = arr[0].length / newHeight;

        // go through the entire image but skip every "range" amount of pixels to get an average
        for(int i = 0; i < arr.length; i+=rangeX)
        {
            for(int j = 0; j < arr[i].length; j+= rangeY)
            {
                Pixel averagePixel = new Pixel(0,0,0);
                int amountOfPixels = 0;

                // determine where to start and assure we don't go into negative elements
                int startRow = Math.max(i - rangeX, 0);
                for(int r = startRow; r < i + rangeX + 1 && r < arr.length; r++)
                {
                    int startCol= Math.max(j - rangeY, 0);
                    for(int c = startCol; c < j + rangeY + 1 && c < arr[i].length; c++)
                    {
                        averagePixel.R += arr[r][c].R;
                        averagePixel.G += arr[r][c].G;
                        averagePixel.B += arr[r][c].B;
                        amountOfPixels++;
                    }
                }
                averagePixel.R /= amountOfPixels;
                averagePixel.G /= amountOfPixels;
                averagePixel.B /= amountOfPixels;

                // place the new average pixel in the newArr and using the range to keep proportion
                newArr[i/rangeX][j/rangeY] = averagePixel;

            }
        }

        im.outputPixelArrayToImage(newArr, "compressed_"+newHeight+"x"+newWidth);
    }
}
