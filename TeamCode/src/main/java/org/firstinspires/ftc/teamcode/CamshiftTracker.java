package org.firstinspires.ftc.teamcode;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Rect;
import org.opencv.core.TermCriteria;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 2017-03-19.
 */

public class CamshiftTracker {

    private final static MatOfInt channels = new MatOfInt(0);
    static Mat backproj = new Mat();
    static Mat histogram = new Mat();
    static Rect tracked = null;
    static MatOfFloat ranges = new MatOfFloat();

    static synchronized void init(Mat frame, Rect roi) {
        Mat hsv, h = new Mat(), tempHist = new Mat();

        hsv = rgb2hsv(frame);
        hsv.submat(roi);

        List<Mat> hsvImg = new ArrayList<>(1);
        hsvImg.add(hsv);

        MatOfInt hSize = new MatOfInt(0);
        Imgproc.calcHist(hsvImg, channels, new Mat(), tempHist, hSize, ranges);
        Core.normalize(tempHist, histogram);
    }

    static synchronized Rect track(Mat frame) {
        Mat hsv = rgb2hsv(frame);

        List<Mat> imgList = new ArrayList<>(1);
        imgList.add(hsv);

        Imgproc.calcBackProject(imgList, channels, histogram, backproj, ranges, 1);

        if(tracked.area() > 1) {
            Video.CamShift(backproj, tracked, new TermCriteria(TermCriteria.MAX_ITER + TermCriteria.EPS, 10, 1));
        }

        return new Rect(tracked.tl(), tracked.br());
    }

    private static Mat rgb2hsv(Mat frame) {
        Mat hsv = new Mat();

        Imgproc.cvtColor(frame, hsv, Imgproc.COLOR_RGB2HSV_FULL);

        return hsv;
    }
}
