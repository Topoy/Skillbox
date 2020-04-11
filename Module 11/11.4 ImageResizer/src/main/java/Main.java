import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main
{
    private static int newWidth = 300;

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        String srcFolder = "resources/src";
        String dstFolder = "resources/dst";

        File srcDir = new File(srcFolder);
        File[] files = srcDir.listFiles();

        int middle = files.length / 2;
        int quarter = files.length / 4;
        int lastQuarterOfArray = files.length - 3 * quarter;

/*
        File[] files1= new File[quarter];
        System.arraycopy(files, 0, files1, 0, files1.length);
        ImageResizer imageResizer1 = new ImageResizer(files1, newWidth, dstFolder, System.currentTimeMillis());
        imageResizer1.start();

        File[] files2 = new File[quarter];
        System.arraycopy(files, quarter, files2, 0, files2.length);
        ImageResizer imageResizer2 = new ImageResizer(files2, newWidth, dstFolder, System.currentTimeMillis());
        imageResizer2.start();

        File[] files3 = new File[quarter];
        System.arraycopy(files, middle, files3, 0, files3.length);
        ImageResizer imageResizer3 = new ImageResizer(files3, newWidth, dstFolder, System.currentTimeMillis());
        imageResizer3.start();

        File[] files4 = new File[lastQuarterOfArray];
        if (lastQuarterOfArray < middle)
        {
            System.arraycopy(files, middle + quarter, files4, 0, files4.length);
        }
        else
        {
            System.arraycopy(files, middle, files4, 0, files4.length);
        }
        ImageResizer imageResizer4 = new ImageResizer(files4, newWidth, dstFolder, System.currentTimeMillis());
        imageResizer4.start();

        imageResizer1.join();
        imageResizer2.join();
        imageResizer3.join();
        imageResizer4.join();
        System.out.println("Общее время работы программы: " + (System.currentTimeMillis() - start) + " мс");
*/
        ////////////////////////////////////////////Использование Executor Service//////////////////////////////////////

        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < files.length; i++)
        {
            int finalI = i;
            service.submit(new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        BufferedImage image = ImageIO.read(files[finalI]);
                        if (image == null)
                        {
                            return;
                        }
                        int newWidth = 300;
                        int newHeight = (int) Math.round(
                                image.getHeight() / (image.getWidth() / (double) newWidth)
                        );
                        BufferedImage newImage = new BufferedImage(
                                newWidth, newHeight, BufferedImage.TYPE_INT_RGB
                        );

                        int widthStep = image.getWidth() / newWidth;
                        int heightStep = image.getHeight() / newHeight;

                        for (int x = 0; x < newWidth; x++)
                        {
                            for (int y = 0; y < newHeight; y++) {
                                int rgb = image.getRGB(x * widthStep, y * heightStep);
                                newImage.setRGB(x, y, rgb);
                            }
                        }

                        File newFile = new File(dstFolder + "/" + files[finalI].getName());
                        ImageIO.write(newImage, "jpg", newFile);
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            });
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.HOURS);
        System.out.println("Общее время работы программы: " + (System.currentTimeMillis() - start) + " мс");
    }
}
