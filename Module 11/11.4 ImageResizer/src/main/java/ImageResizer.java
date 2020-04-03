import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageResizer extends Thread
{
    private File[] files;
    private int newWidth;
    private String dstFolder;
    private long start;

    public ImageResizer(File[] files, int newWidth, String dstFolder, long start)
    {
        this.files = files;
        this.newWidth = newWidth;
        this.dstFolder = dstFolder;
        this.start = start;
    }

    @Override
    public void run()
    {
        try
        {
            for (File file : files)
            {
                BufferedImage image = ImageIO.read(file);
                if (image == null)
                {
                    continue;
                }

                int newHeight = (int) Math.round(image.getHeight() / (image.getWidth() / (double) newWidth));

                BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

                int widthStep = image.getWidth() / newWidth;
                int heightStep = image.getHeight() / newHeight;

                for (int i = 0; i < newWidth; i++)
                {
                    for (int j = 0; j < newHeight; j++)
                    {
                        int rgb = image.getRGB(i * widthStep, j * heightStep);
                        newImage.setRGB(i, j, rgb);
                    }
                }

                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);
            }
            System.out.println("Programm finished with " + (System.currentTimeMillis() - start) + " ms");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
