import java.io.File;

public class Main
{
    private static int newWidth = 300;

    public static void main(String[] args)
    {
        String srcFolder = "resources/src";
        String dstFolder = "resources/dst";

        File srcDir = new File(srcFolder);
        File[] files = srcDir.listFiles();

        int middle = files.length / 2;
        int quarter = files.length / 4;
        int lastQuarterOfArray = files.length - 3 * quarter;


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
        System.arraycopy(files, middle + quarter, files4, 0, files4.length);
        ImageResizer imageResizer4 = new ImageResizer(files4, newWidth, dstFolder, System.currentTimeMillis());
        imageResizer4.start();



    }
}
