package org.firstinspires.ftc.teamcode.modules;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class WebcamPipeline extends OpenCvPipeline {
    private String colorToDetect = "blue";

    // HSV Blue
    final Scalar LOW_BLUE = new Scalar(100, 100, 70);
    final Scalar HIGH_BLUE = new Scalar(130, 255, 255);

    final Scalar RED = new Scalar(255, 0, 0);

    Mat hsvMat1 = new Mat();
    Mat inRangeMat1 = new Mat();
    Mat morph1 = new Mat();
    Mat hierarchy = new Mat();
    Mat kernel = Mat.ones(7, 7, CvType.CV_8UC1);

    // HSV Red
    final Scalar LOW_RED1 = new Scalar(170, 100, 20);
    final Scalar HIGH_RED1 = new Scalar(180, 255, 255);

    final Scalar LOW_RED2 = new Scalar(0, 100, 20);
    final Scalar HIGH_RED2 = new Scalar(10, 255, 255);

    Mat hsvMat2 = new Mat();
    Mat inRangeMat2 = new Mat();
    Mat morph2 = new Mat();
    Mat merge = new Mat();

    int frameCount = 0;
    String propPos = "";
    Point contourCent;
    List<MatOfPoint> contours = new ArrayList<>();

    public WebcamPipeline(String colorToDetect) {
        this.colorToDetect = colorToDetect;
    }

    @Override
    public Mat processFrame(Mat input) {
        frameCount++;

        if (this.colorToDetect == "blue") {
            Imgproc.cvtColor(input, hsvMat1, Imgproc.COLOR_RGB2HSV);

            Core.inRange(hsvMat1, LOW_BLUE, HIGH_BLUE, inRangeMat1);

            Imgproc.morphologyEx(inRangeMat1, morph1, Imgproc.MORPH_CLOSE, kernel);
            Imgproc.morphologyEx(morph1, morph1, Imgproc.MORPH_OPEN, kernel);

            Imgproc.findContours(morph1, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        } else {
            Imgproc.cvtColor(input, hsvMat1, Imgproc.COLOR_RGB2HSV);
            Imgproc.cvtColor(input, hsvMat2, Imgproc.COLOR_RGB2HSV);

            Core.inRange(hsvMat1, LOW_RED1, HIGH_RED1, inRangeMat1);
            Core.inRange(hsvMat2, LOW_RED2, HIGH_RED2, inRangeMat2);

            Imgproc.morphologyEx(inRangeMat1, morph1, Imgproc.MORPH_CLOSE, kernel);
            Imgproc.morphologyEx(morph1, morph1, Imgproc.MORPH_OPEN, kernel);

            Imgproc.morphologyEx(inRangeMat2, morph2, Imgproc.MORPH_CLOSE, kernel);
            Imgproc.morphologyEx(morph2, morph2, Imgproc.MORPH_OPEN, kernel);

            Core.bitwise_or(morph1, morph2, merge);

            Imgproc.findContours(merge, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        }

        for (int i = 0; i < contours.size(); i++) {
            Imgproc.drawContours(input, contours, i, (RED), 5, 2);
        }

            // analyze
            for (int i = 0; i < contours.size(); i++) {

                if (Imgproc.contourArea(contours.get(i)) > 10000) {
                    Rect rect = Imgproc.boundingRect(contours.get(i));
                    contourCent = new Point(((rect.width - rect.x) / 2.0) + rect.x, ((rect.height - rect.y) / 2.0) + rect.y);

                    if (contourCent.x < 200) {
                        this.propPos = "Left";
                    } else if (contourCent.x > 200) {
                        this.propPos = "Center";
                    } else {
                        this.propPos = "Right";
                    }
                }
            }


        contours.clear();

        return input;
    }

    public String getPropPos() {
        return this.propPos;
    }

}
