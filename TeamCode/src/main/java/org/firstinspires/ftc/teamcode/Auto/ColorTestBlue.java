package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

import java.util.ArrayList;
import java.util.List;

@Autonomous(name = "Color Test B")
public class ColorTestBlue extends LinearOpMode {

    //HSV Blue
    final Scalar LOW_BLUE = new Scalar(244, 100, 100);
    final Scalar HIGH_BLUE = new Scalar(232, 73, 53);

    Mat hsvMat1 = new Mat();
    Mat hsvMat2 = new Mat();

    Mat inRangeMat1 = new Mat();
    Mat inRangeMat2 = new Mat();

    Mat morph1 = new Mat();
    Mat morph2 = new Mat();

    Mat hierarchy = new Mat();

    Mat kernel = Mat.ones(7, 7, CvType.CV_8UC1);

    List<MatOfPoint> contoursRed = new ArrayList<>();

    Mat merge = new Mat();

    @Override
    public void runOpMode() throws InterruptedException {

        OpenCvWebcam webcam1;
        OpenCvPipeline redProcessor = new OpenCvPipeline() {

            @Override
            public Mat processFrame(Mat input) {

                Imgproc.cvtColor(input, hsvMat1, Imgproc.COLOR_RGB2HSV);
                Imgproc.cvtColor(input, hsvMat2, Imgproc.COLOR_RGB2HSV);

                Core.inRange(hsvMat1, LOW_BLUE, HIGH_BLUE, inRangeMat1);
                Core.inRange(hsvMat2, LOW_BLUE, HIGH_BLUE,  inRangeMat2);

                Imgproc.morphologyEx(inRangeMat1, morph1, Imgproc.MORPH_CLOSE, kernel);
                Imgproc.morphologyEx(morph1, morph1, Imgproc.MORPH_OPEN, kernel);

                Imgproc.morphologyEx(inRangeMat2, morph2, Imgproc.MORPH_CLOSE, kernel);
                Imgproc.morphologyEx(morph2, morph2, Imgproc.MORPH_OPEN, kernel);

                Core.bitwise_or(morph1, morph2, merge);

                List<MatOfPoint> contours = new ArrayList<>();

                Imgproc.findContours(merge, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

                ColorTestBlue.this.contoursRed = contours;
                return input;
            }
        };

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam1 = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam1"), cameraMonitorViewId);

        webcam1.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                telemetry.addLine("Camera Init Successful");
                telemetry.update();

                webcam1.startStreaming(800, 600, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera Load Failed | ERROR CODE", " " + errorCode);
            }
        });

        waitForStart();

        while (opModeIsActive()) {

            List<MatOfPoint> contoursRed = ColorTestBlue.this.contoursRed;

            webcam1.setPipeline(redProcessor);

            telemetry.addLine("Detecting RED Contours");

            telemetry.addData("Webcam pipeline activity", webcam1.getPipelineTimeMs());

            telemetry.addData("Contours Detected", contoursRed.size());

            for (int i = 0; i < contoursRed.size(); i++) {
                if (Imgproc.contourArea(contoursRed.get(i)) > 1000) {
                    telemetry.addData("Element Detected! Area of Element:", Imgproc.contourArea(contoursRed.get(i)));
                } else {
                    telemetry.addData("Red Contour Area", Imgproc.contourArea(contoursRed.get(i)));
                }
            }

            telemetry.update();

        }
    }
}
