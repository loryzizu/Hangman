package it.lorenzoranucci.hangman.threads;

import android.os.AsyncTask;
import android.view.MotionEvent;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.video.Video;

import it.lorenzoranucci.hangman.threads.BackgroundUpdaterThread;
import it.lorenzoranucci.hangman.workers.Hangman;

/**
 * Created by Lorenzo on 01/07/2015.
 */
public class FaceMaskGeneratorMOG2Thread {


//    /**
//     * Face destination point (where user touched the screen the last time)
//     */
//    private Point faceDestination = new Point(0, 0);
//    /**
//     * Image used for background replacement
//     */
//    private Mat background;
//    /**
//     * Set the threshold used to compute foreground-backgruond difference (detect face's pixels)
//     */
//    private int threshold = 16;
//    /**
//     * The less is the quality the more the frames are resized before to be edited
//     */
//    private int quality = 10;
//
//    /**
//     * True when user want to use the current frame as background
//     */
//    private boolean isToSetBackground = false;
//    /**
//     * True when it's time to send a new frame to the background updater thread
//     */
//    private boolean isCurrentFrameToDeliver = false;
//
//    /**
//     * True when TimerTask have to stop
//     */
//    private boolean isTimerTaskToStop=false;
//
//    /**
//     * Thread that update the background
//     */
//    private BackgroundUpdaterThread backgroundUpdaterThread;
//
//
//
//
//
//
//    private BackgroundSubtractorMOG2 backgroundSubtractorMog2;
//    private Mat faceMask;
//
//    /**
//     * @param mJavaDetector It should be a cascade classifier of human faces
//     */
//    protected FaceMaskGeneratorMOG2Thread(CascadeClassifier mJavaDetector) {
//        super(mJavaDetector);
//    }
//
//    public Mat decapitate(CameraBridgeViewBase.CvCameraViewFrame currentFrame) {
//        Mat currentFrameMat = currentFrame.rgba();
////        Mat imageNew = currentFrame.gray();
//
//        Size s = currentFrameMat.size();
//    Mat imageNew = new Mat(s, CvType.CV_8UC3);
//    Imgproc.cvtColor(currentFrameMat, imageNew ,Imgproc.COLOR_RGBA2RGB);
//
//        frameWidth = currentFrameMat.width();
//        frameHeight = currentFrameMat.height();
//        Rect sourceFaceROI = null;
//        Rect destFaceROI = null;
//        Mat lastFaceFrame = null;
//        if (isToSetBackground ) {
//            isToSetBackground=false;
//            background = currentFrameMat.clone();
//            getFaceMask2(imageNew,1);
//        }
//        else if (this.faceMask != null) {
//            /*check if is there a face in the current frame*/
//            Rect tempSourceFaceROI;
//            if ((tempSourceFaceROI = faceDetect(currentFrameMat)) != null) {
//                /*compute and position the region of interest of the face*/
//                sourceFaceROI = tempSourceFaceROI;
//                destFaceROI = getDestFaceROI(sourceFaceROI.width, sourceFaceROI.height, currentFrameMat);
//                lastFaceFrame = new Mat();
//                currentFrameMat.copyTo(lastFaceFrame);
//            }
//            if (lastFaceFrame != null
//                    && destFaceROI != null) {
//                getFaceMask(imageNew,0);
//                /*replace face region of interest with the old background*/
//                copyMatToMat(currentFrameMat, background, sourceFaceROI, sourceFaceROI, faceMask.submat(sourceFaceROI));
//                /*replace face destination region of interest with the captured face*/
//                copyMatToMat(currentFrameMat, lastFaceFrame, destFaceROI, sourceFaceROI, faceMask.submat(sourceFaceROI));
//
//            }
//        }
//        return currentFrameMat;
//    }
//
//    /**
//     * Detect the face's pixel and return a mat of them.
//     * @param currentFrame Current captured frame
//     * @param faceROI Face region of interest
//     * @return Mask of point that belong to the foreground(face)
//     */
//    protected Mat getFaceMask(CameraBridgeViewBase.CvCameraViewFrame currentFrame, Rect faceROI){
//        Mat currentFrameMat=currentFrame.rgba();
//        backgroundSubtractorMog2.setVarThreshold(getThreshold());
//        backgroundSubtractorMog2.apply(currentFrameMat,faceMask, 0);
//
//    }
//
//    @Override
//    public void initBackground(CameraBridgeViewBase.CvCameraViewFrame currentFrame){
//        super.initBackground(currentFrame);
//
//        Mat currentFrameMat=currentFrame.rgba();
//        if(backgroundSubtractorMog2==null){
//            backgroundSubtractorMog2= Video.createBackgroundSubtractorMOG2(500,getThreshold(),true);
//        }
//        if(faceMask==null){
//            Size s = currentFrameMat.size();
//            this.faceMask= new Mat(s, CvType.CV_8UC1);
//        }
//        backgroundSubtractorMog2.apply(currentFrameMat,faceMask, 1);
//    }
//
//    private void getFaceMask(Mat currentFrame, double learningRate) {
//
//
//
//
//    }
//
//
//
//
//
//
//    /**
//     * Resize the region of interest
//     *
//     * @param roi A rectangular region of interest
//     * @return Resized ROI
//     */
//    private Rect incrementROISize(Rect roi) {
//        double h = roi.height;
//        double hdiff = (h / 100) * 90;//90% higher
//        double h2 = h + hdiff;
//
//        double w = roi.width;
//        double wdiff = (w / 100) * 45;//45% wider
//        double w2 = w + wdiff;
//
//        double x = roi.x;
//        double x2 = x - (wdiff / 2);
//        double y = roi.y;
//        double y2 = y - (hdiff * 5 / 7);
//
//
//        if (x2 < 0) {
//            w2 -= Math.abs(x2);
//            x2 = 0;
//        }
//        if (y2 < 0) {
//            h2 -= Math.abs(y2);
//            y2 = 0;
//        }
//
//        double xn = x2 + w2;
//        double yn = y2 + h2;
//
//        if (xn > frameWidth) {
//            double diff = xn - frameWidth;
//            w2 -= diff + 1;
//        }
//        if (yn > frameHeight) {
//            double diff = yn - frameHeight;
//            h2 -= diff + 1;
//        }
//
//        return new Rect((int) x2, (int) y2, (int) w2, (int) h2);
//
//    }
//
//
//    /**
//     * Print sourceROI of source into destinationROI of destination
//     *
//     * @param destination    Image to edit
//     * @param source         Source image
//     * @param destinationROI Region of interest of destination image
//     * @param sourceROI      Region of interest of source image
//     * @param mask           Matrix mask of point to be drawn
//     */
//    private void copyMatToMat(Mat destination, Mat source, Rect destinationROI, Rect sourceROI, Mat mask) {
//        if (destination != null
//                && source != null
//                && destinationROI.height == sourceROI.height
//                && destinationROI.width == sourceROI.width
//                && sourceROI.width <= source.width()
//                && sourceROI.height <= source.height()
//                && destinationROI.width <= destination.width()
//                && destinationROI.height <= destination.height()
//                ) {
//            Mat tempS = source.clone().submat(sourceROI);
//
//            if (mask != null) {
//                tempS.copyTo(destination.submat(destinationROI), mask);
//            } else {
//                tempS.copyTo(destination.submat(destinationROI));
//            }
//            tempS.release();
//        }
//    }
//
//    /**
//     * Map the point of the user touch with the equivalent point in the image
//     *
//     * @param event User touch
//     * @param screenWidth Screen width
//     * @param screenHeight Screen height
//     * @param scale Scale property by OpenCv
//     */
//    public void setFaceDestination(MotionEvent event, int screenWidth, int screenHeight, float scale) {
//        if (frameWidth != null && frameHeight != null) {
//            double bitmapWidth = frameWidth * scale;
//            double bitmapHeight = frameHeight * scale;
//            double borderWidth = (screenWidth - bitmapWidth) / 2;
//            double borderHeight = (screenHeight - bitmapHeight) / 2;
//            faceDestination.x = (event.getX() - borderWidth) / scale;
//            faceDestination.y = (event.getY() - borderHeight) / scale;
//        }
//    }
//
//
//    /**
//     * Return the region of interest of the face destination
//     *
//     * @param faceWidth Face width
//     * @param faceHeight Face heigth
//     * @param currentFrame Current frame
//     * @return the region of interest of the face destination
//     */
//    private  Rect getDestFaceROI(int faceWidth, int faceHeight, Mat currentFrame) {
//        Rect rect = new Rect(0, 0, faceWidth, faceHeight);
//        if (faceDestination != null && currentFrame != null) {
//            int x0 = (int) faceDestination.x;
//            int y0 = (int) faceDestination.y;
//            int currentFrameWidth = currentFrame.cols();
//            int currentFrameHeight = currentFrame.rows();
//            if (currentFrameWidth > faceWidth && currentFrameHeight > faceHeight) {
//                x0 = x0 - (faceWidth / 2);
//                y0 = y0 - (faceHeight / 2);
//
//                int xn = x0 + faceWidth;
//                int yn = y0 + faceHeight;
//
//                if (x0 < 0) {
//                    x0 = 0;
//                } else if (xn > currentFrame.width()) {
//                    int diff = xn - currentFrame.width() + 1;
//                    x0 = x0 - diff;
//                }
//                if (y0 < 0) {
//                    y0 = 0;
//                } else if (yn > currentFrame.height()) {
//                    int diff = yn - currentFrame.height() + 1;
//                    y0 = y0 - diff;
//                }
//                rect.x = x0;
//                rect.y = y0;
//            }
//        }
//        return rect;
//    }
//
//    /**
//     * Set the threshold used to compute foreground-backgruond difference (detect face's pixels)
//     *
//     * @param t value
//     */
//    public void setThreshold(int t) {
//        this.threshold = t * 2;
//    }
//
//    /**
//     * Set quality.
//     * The less is the quality the more the frames are resized before to be edited
//     *
//     * @param q value
//     */
//    public void setQuality(int q) {
//        this.quality = q + 10;
//    }
//
//    /**
//     * @return The threshold used to compute foreground-backgruond difference (detect face's pixels)
//     */
//    public int getThreshold() {
//        return this.threshold;
//    }
//
//    /**
//     * @return /**
//     * Get quality.
//     * The less is the quality the more the frames are resized before to be edited
//     */
//    public int getQuality() {
//        return this.quality;
//    }
//
//
//    private class Ellipse {
//        Point center;
//        double semiMajorAxis;
//        double semiMinorAxis;
//
//        Ellipse(double centerA, double centerB, double semiMajorAxis, double semiMinorAxis) {
//            this.center = new Point(centerA, centerB);
//            this.semiMajorAxis = semiMajorAxis;
//            this.semiMinorAxis = semiMinorAxis;
//        }
//
//        boolean contains(Point point) {
//            double distance = (((point.x - center.x) * (point.x - center.x)) / (semiMajorAxis * semiMajorAxis)) +
//                    (((point.y - center.y) * (point.y - center.y)) / (semiMinorAxis * semiMinorAxis));
//            return distance <= 1;
//        }
//    }
//
//    public void setBackground() {
//        this.isToSetBackground = true;
//    }
//
//    public void setNullBackground() {
//        this.background = null;
//    }
//
//
//    /**
//     * Stop background update thread and its scheduler
//     */
//    public void stop() {
//        backgroundUpdaterThread.stopThread();
//        isTimerTaskToStop=true;
//    }
//
//    /**
//     * Start background update thread and its scheduler
//     */
//    public void start() {
//        if (backgroundUpdaterThread == null) {
//            this.backgroundUpdaterThread = new BackgroundUpdaterThread();
//            this.backgroundUpdaterThread.setListener(this);
//        }
//        if (!backgroundUpdaterThread.isAlive()) {
//            backgroundUpdaterThread.start();
//        }
//        new TimerTask().execute(BACKGROUND_UPDATE_DELAY);
//    }
//
//
//    /**
//     * Temporize the background updater thread
//     */
//    private class TimerTask extends AsyncTask<Long, Void, String>{
//        /**
//         * @param seconds how many seconds to wait
//         * @return Operation
//         */
//        @Override
//        protected String doInBackground(Long... seconds) {
//            try {
//                Thread.sleep(seconds[0], 0);
//                return "Send";
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                return "Relaunch";
//            }
//        }
//
//        /**
//         * Called when it is time to send the current frame to BackgroundUpdaterThread.
//         * Re-launch the timer background task.
//         * @param operation Send or Relaunch(timer)
//         */
//        @Override
//        protected void onPostExecute(String operation) {
//            if(isTimerTaskToStop){
//                return;
//            }
//            else if(operation.equals("Send")){
//                isCurrentFrameToDeliver = true;
//            }
//            new TimerTask().execute(BACKGROUND_UPDATE_DELAY);
//        }
//    }
//
//    /**
//     * Callback for updated background retrieving
//     *
//     * @param backgroundFrame The updated background
//     */
//    @Override
//    public void onBackgroundUpdated(Mat backgroundFrame) {
//        background = backgroundFrame.clone();
//    }
//
//
}



