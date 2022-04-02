package opencv;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class KernelDemo {
    static {
        System.load("D:\\IdeaProjects\\demoset\\Deeplearning4j\\src\\main\\resources\\lib\\opencv\\opencv_java454.dll");
    }

    public static void main(String[] args) {
        Mat src = Imgcodecs.imread("C:\\Users\\zcx\\Desktop\\mnist\\3.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        HighGui.imshow("lena", src);
        HighGui.waitKey(0);

        Mat dst = src.clone();

        Imgproc.filter2D(src, dst, -1, getKernel1());
        HighGui.imshow("filter1", dst);
        HighGui.waitKey(0);


        Mat dst2 = src.clone();
        Imgproc.filter2D(src, dst2, -1, getKernel3());
        HighGui.imshow("filter2", dst2);
        HighGui.waitKey(0);

        System.exit(0);
    }

    public static Mat getDilate1(){
        Mat kernel = new Mat(new Size(3, 3), CvType.CV_8U);
        kernel.put(0, 0, 1);kernel.put(0, 1, 1);kernel.put(0, 2, 1);
        kernel.put(1, 0, 1);kernel.put(1, 1, 1);kernel.put(1, 2, 1);
        kernel.put(2, 0, 1);kernel.put(2, 1, 1);kernel.put(2, 2, 1);
        return kernel;
    }
    public static Mat getKernel1(){
        Mat kernel = new Mat(new Size(3, 3), CvType.CV_8S);
        kernel.put(0, 0, -1);kernel.put(0, 1, -1);kernel.put(0, 2, -1);
        kernel.put(1, 0, -1);kernel.put(1, 1, 9);kernel.put(1, 2, -1);
        kernel.put(2, 0, -1);kernel.put(2, 1, -1);kernel.put(2, 2, -1);
        return kernel;
    }

    public static Mat getKernel2(){
        Mat kernel = new Mat(new Size(3, 3), CvType.CV_8S);
        kernel.put(0,0,0);kernel.put(0,1,0);kernel.put(0,2,0);
        kernel.put(1,0,0);kernel.put(1,1,1);kernel.put(1,2,0);
        kernel.put(2,0,0);kernel.put(2,1,0);kernel.put(2,2,0);
        return kernel;
    }
    public static Mat getKernel3(){
        Mat kernel = new Mat(new Size(3, 3), CvType.CV_8S);
        kernel.put(0, 0, -1);kernel.put(0, 1, -1);kernel.put(0, 2, -1);
        kernel.put(1, 0, -1);kernel.put(1, 1, 8);kernel.put(1, 2, -1);
        kernel.put(2, 0, -1);kernel.put(2, 1, -1);kernel.put(2, 2, -1);
        return kernel;
    }
}
