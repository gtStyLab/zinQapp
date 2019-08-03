package com.krishikishore.zin_q;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.MotionEvent;
import android.widget.*;
import android.graphics.drawable.*;
import android.graphics.Rect;
import android.util.Log;
import android.graphics.Matrix;
import android.graphics.BitmapFactory;
import java.io.*;
import android.content.Context;
import org.opencv.android.*;
import org.opencv.core.*;
import org.opencv.imgproc.*;
import java.util.ArrayList;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.os.Build;
import android.content.DialogInterface;





public class chooseCircle extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_ALBUM = 2;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final String TAG = "ImageCapture";

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        double rint = 0;
        double rslope = 0;
        double gint = 0;
        double gslope = 0;
        double bint = 0;
        double bslope = 0;
        int numberOfCircles = 0;
        int xcenter = 0;
        int ycenter = 0;
        int rcenter = 0;
        int redaverage = 0;
        int greenaverage = 0;
        int blueaverage = 0;
        int redsum = 0;
        int greensum = 0;
        int bluesum = 0;
        double colorscore = 0;
        ArrayList<Integer> reds = new ArrayList<Integer>();
        ArrayList<Integer> blues = new ArrayList<Integer>();
        ArrayList<Integer> greens = new ArrayList<Integer>();

        ImageView image = findViewById(R.id.secondImageView);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
        Rect imageBounds = bitmapDrawable.getBounds();

        double Xratio = bitmapDrawable.getIntrinsicWidth() / image.getWidth();
        double Yratio = bitmapDrawable.getIntrinsicHeight() / image.getHeight();

        //float scaledcoords[] = getPointerCoords(image, event);


        int intrinsicHeight = bitmapDrawable.getIntrinsicHeight();
        int intrinsicWidth = bitmapDrawable.getIntrinsicWidth();

        int scaledHeight = imageBounds.height();
        int scaledWidth = imageBounds.width();

        double heightRatio = intrinsicHeight / scaledHeight;
        double widthRatio = intrinsicWidth / scaledWidth;

        int scaledImageOffsetX = (int) (event.getX() - imageBounds.left);
        int scaledImageOffsetY = (int) (event.getY() - imageBounds.top);

        int originalImageOffsetX = (int) (scaledImageOffsetX * widthRatio);
        int originalImageOffsetY = (int) (scaledImageOffsetY * heightRatio);


        int[] viewCoords = new int[2];
        image.getLocationOnScreen(viewCoords);

        int imageX = x - viewCoords[0];
        int imageY = y - viewCoords[1];

        int updatedx = (int) ((int) imageX * Xratio);
        int updatedy = (int) ((int) imageY * Yratio);

        int imageviewHeight = image.getHeight();
        int imageviewWidth = image.getWidth();

        if (imageX > viewCoords[0] && imageX < viewCoords[0] + imageviewWidth && imageY > viewCoords[1] && imageY < viewCoords[1] + imageviewHeight &&
                event.getAction() == MotionEvent.ACTION_DOWN) {

            Log.e(TAG, "onTouchEvent: drawable touched ");


            if (!OpenCVLoader.initDebug()) {
                Log.e(TAG, "Cannot connect to OpenCV Manager");
            }
            Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();

            bitmap = Bitmap.createBitmap(bitmap, updatedx-600, updatedy-600, 1200, 1200);

            Mat mat = new Mat(bitmap.getWidth(), bitmap.getHeight(),
                    CvType.CV_8UC1);
            Mat grayMat = new Mat(bitmap.getWidth(), bitmap.getHeight(),
                    CvType.CV_8UC1);

            Utils.bitmapToMat(bitmap, mat);

            int colorChannels = (mat.channels() == 3) ? Imgproc.COLOR_BGR2GRAY
                    : ((mat.channels() == 4) ? Imgproc.COLOR_BGRA2GRAY : 1);

            Imgproc.cvtColor(mat, grayMat, colorChannels);

            Imgproc.GaussianBlur(grayMat, grayMat, new Size(9, 9), 2, 2);

            double dp = 2d;
            double minDist = 100;

            int minRadius = 10, maxRadius = 200;

            double param1 = 40, param2 = 10;

            outerloop:
            for (int k = 0; k < 25; k++) {
                param1 += 4;
                for (int j = 0; j < 25; j++) {

                    Mat circles = new Mat(bitmap.getWidth(),
                            bitmap.getHeight(), CvType.CV_8UC1);


                    Imgproc.HoughCircles(grayMat, circles,
                            Imgproc.CV_HOUGH_GRADIENT, dp, minDist, param1,
                            param2, minRadius, maxRadius);

                    numberOfCircles = (circles.rows() == 0) ? 0 : circles.cols();

                    System.out.println("Number of circles:" + numberOfCircles);
                    System.out.println("param1:" + param1);
                    System.out.println("param2:" + param2);

                    if (numberOfCircles == 1) {

                        double informationCircles[] = new double[6];

                        double[] circleCoordinates = circles.get(0, 0);
                        xcenter = (int) circleCoordinates[0];
                        ycenter = (int) circleCoordinates[1];
                        rcenter = (int) circleCoordinates[2];

                        informationCircles[0] = xcenter;
                        informationCircles[1] = ycenter;
                        informationCircles[2] = rcenter;

                        for (int xv = xcenter - rcenter; xv < xcenter + rcenter; xv++) {
                            for (int yv = ycenter - rcenter; yv < ycenter + rcenter; yv++) {
                                double dx = xv - xcenter;
                                double dy = yv - ycenter;
                                double distanceSquared = dx * dx + dy * dy;

                                if (distanceSquared <= (rcenter * rcenter)) {
                                    int pixel = bitmap.getPixel(xv, yv);
                                    int redValue = Color.red(pixel);
                                    int greenValue = Color.green(pixel);
                                    int blueValue = Color.blue(pixel);
                                    reds.add(redValue);
                                    greens.add(greenValue);
                                    blues.add(blueValue);

                                }
                            }
                        }

                        for (int n : reds) {
                            redsum += n;
                        }
                        for (int n : greens) {
                            greensum += n;
                        }
                        for (int n : blues) {
                            bluesum += n;
                        }

                        redaverage = redsum / reds.size();
                        greenaverage = greensum / greens.size();
                        blueaverage = bluesum / blues.size();

                        informationCircles[3] = redaverage;
                        informationCircles[4] = greenaverage;
                        informationCircles[5] = blueaverage;

                        break outerloop;

                    }

                    /*
                    if (numberOfCircles == 1) {
                        for (int i = 0; i < numberOfCircles; i++) {
                            double[] circleCoordinates = circles.get(0, i);
                            xcenter = (int) circleCoordinates[0];
                            ycenter = (int) circleCoordinates[1];
                            rcenter = (int) circleCoordinates[2];

                            for (int xv = xcenter - rcenter; xv < xcenter + rcenter; xv++) {
                                for (int yv = ycenter - rcenter; yv < ycenter + rcenter; yv++) {
                                    double dx = xv - xcenter;
                                    double dy = yv - ycenter;
                                    double distanceSquared = dx * dx + dy * dy;

                                    if (distanceSquared <= (rcenter * rcenter)) {
                                        int pixel = bitmap.getPixel(xv, yv);
                                        int redValue = Color.red(pixel);
                                        int greenValue = Color.green(pixel);
                                        int blueValue = Color.blue(pixel);
                                        reds.add(redValue);
                                        greens.add(greenValue);
                                        blues.add(blueValue);

                                    }
                                }
                            }

                            break outerloop;

                        }
                        */



                param2 += 3;
            }

            param2 = 3;
        }

        /*
        for (int n : reds) {
            redsum += n;
        }
        for (int n : greens) {
            greensum += n;
        }
        for (int n : blues) {
            bluesum += n;
        }
        */


        redaverage = redsum / reds.size();
        greenaverage = greensum / greens.size();
        blueaverage = bluesum / blues.size();


        if (numberOfCircles == 1) {

            double xval[] = {0, 2, 4};

            colorscore = ((((redaverage - 61) / 42.9) + ((greenaverage - 48) / 26.6) - ((blueaverage - 79.6) / 7.3)) / 3);


            Intent intentBundle = new Intent(chooseCircle.this, ResultsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("Red", Integer.toString((int) redaverage));
            bundle.putString("Green", Integer.toString((int) greenaverage));
            bundle.putString("Blue", Integer.toString((int) blueaverage));
            bundle.putString("ColorScore", Double.toString(colorscore));
            bundle.putString("XCoordinate", Integer.toString((int) xcenter));
            bundle.putString("YCoordinate", Integer.toString((int) ycenter));
            bundle.putString("Radius", Integer.toString((int) rcenter));

            intentBundle.putExtras(bundle);
            startActivity(intentBundle);

        } else {

            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("Circle Detection Failed")
                    .setMessage("Sample was not found. Please use another image. Try zooming in or zooming out of sample.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent goToNextActivity = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(goToNextActivity);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }


            return true;
        }

        return false;

    }

    final float[] getPointerCoords(ImageView view, MotionEvent e)
    {
        final int index = e.getActionIndex();
        final float[] coords = new float[] { e.getX(index), e.getY(index) };
        Matrix matrix = new Matrix();
        view.getImageMatrix().invert(matrix);
        matrix.postTranslate(view.getScrollX(), view.getScrollY());
        matrix.mapPoints(coords);
        return coords;
    }
/*
    public void processMoveToResults(View view) {

        double rint = 0;
        double rslope = 0;
        double gint = 0;
        double gslope = 0;
        double bint = 0;
        double bslope = 0;

        int numberOfCircles = 0;

        int x = 0;
        int y = 0;
        int r = 0;

        int redaverage = 0;
        int greenaverage = 0;
        int blueaverage = 0;
        int redsum = 0;
        int greensum = 0;
        int bluesum = 0;
        double colorscore = 0;

        if (!OpenCVLoader.initDebug()) {
            Log.e(TAG, "Cannot connect to OpenCV Manager");
        }
        ImageView image = findViewById(R.id.mImageView);
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();

        Mat mat = new Mat(bitmap.getWidth(), bitmap.getHeight(),
                CvType.CV_8UC1);
        Mat grayMat = new Mat(bitmap.getWidth(), bitmap.getHeight(),
                CvType.CV_8UC1);

        Utils.bitmapToMat(bitmap, mat);

        int colorChannels = (mat.channels() == 3) ? Imgproc.COLOR_BGR2GRAY
                : ((mat.channels() == 4) ? Imgproc.COLOR_BGRA2GRAY : 1);

        Imgproc.cvtColor(mat, grayMat, colorChannels);

        Imgproc.GaussianBlur(grayMat, grayMat, new Size(9, 9), 2, 2);

        double dp = 2d;
        double minDist = 100;

        int minRadius = 10, maxRadius = 200;

        double param1 = 40, param2 = 10;

        outerloop:
        for (int k = 0; k < 25; k++) {
            param1 +=4;
            for (int j = 0; j < 25; j++) {

                Mat circles = new Mat(bitmap.getWidth(),
                        bitmap.getHeight(), CvType.CV_8UC1);


                Imgproc.HoughCircles(grayMat, circles,
                        Imgproc.CV_HOUGH_GRADIENT, dp, minDist, param1,
                        param2, minRadius, maxRadius);

                numberOfCircles = (circles.rows() == 0) ? 0 : circles.cols();

                System.out.println("Number of circles:" + numberOfCircles);
                System.out.println("param1:" + param1);
                System.out.println("param2:" + param2);

                if (numberOfCircles == 1) {

                    double informationCircles[] = new double[6];

                    ArrayList<Integer> reds = new ArrayList<Integer>();
                    ArrayList<Integer> blues = new ArrayList<Integer>();
                    ArrayList<Integer> greens = new ArrayList<Integer>();

                    double[] circleCoordinates = circles.get(0, 0);
                    x = (int) circleCoordinates[0];
                    y = (int) circleCoordinates[1];
                    r = (int) circleCoordinates[2];

                    informationCircles[0] = x;
                    informationCircles[1] = y;
                    informationCircles[2] = r;

                    for (int xv = x - r; xv < x + r; xv++) {
                        for (int yv = y - r; yv < y + r; yv++) {
                            double dx = xv - x;
                            double dy = yv - y;
                            double distanceSquared = dx * dx + dy * dy;

                            if (distanceSquared <= (r * r)) {
                                int pixel = bitmap.getPixel(xv, yv);
                                int redValue = Color.red(pixel);
                                int greenValue = Color.green(pixel);
                                int blueValue = Color.blue(pixel);
                                reds.add(redValue);
                                greens.add(greenValue);
                                blues.add(blueValue);

                            }
                        }
                    }

                    for (int n : reds) {
                        redsum += n;
                    }
                    for (int n : greens) {
                        greensum += n;
                    }
                    for (int n : blues) {
                        bluesum += n;
                    }

                    redaverage = redsum / reds.size();
                    greenaverage = greensum / greens.size();
                    blueaverage = bluesum / blues.size();

                    informationCircles[3] = redaverage;
                    informationCircles[4] = greenaverage;
                    informationCircles[5] = blueaverage;

                    break outerloop;

                }


                if (numberOfCircles == 4) {
                    for (int i = 0; i < numberOfCircles; i++) {
                        double[] circleCoordinates = circles.get(0, i);
                        x = (int) circleCoordinates[0];
                        y = (int) circleCoordinates[1];
                        r = (int) circleCoordinates[2];

                        for (int xv = x - r; xv < x + r; xv++) {
                            for (int yv = y - r; yv < y + r; yv++) {
                                double dx = xv - x;
                                double dy = yv - y;
                                double distanceSquared = dx * dx + dy * dy;

                                if (distanceSquared <= (r * r)) {
                                    int pixel = bitmap.getPixel(xv, yv);
                                    int redValue = Color.red(pixel);
                                    int greenValue = Color.green(pixel);
                                    int blueValue = Color.blue(pixel);
                                    reds.add(redValue);
                                    greens.add(greenValue);
                                    blues.add(blueValue);

                                }
                            }
                        }

                        break outerloop;

                    }

                }


                param2 += 3;
            }

            param2 = 3;
        }


        for (int n : reds) {
            redsum += n;
        }
        for (int n : greens) {
            greensum += n;
        }
        for (int n : blues) {
            bluesum += n;
        }


        redaverage = redsum / reds.size();
        greenaverage = greensum / greens.size();
        blueaverage = bluesum / blues.size();



        if (numberOfCircles == 1) {

            double xval[] = {0, 2, 4};

            colorscore = ((((redaverage - 61) / 42.9) + ((greenaverage - 48) / 26.6) - ((blueaverage - 79.6) / 7.3)) / 3);


            Intent intentBundle = new Intent(ImageCapture.this, ResultsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("Red", Integer.toString((int) redaverage));
            bundle.putString("Green", Integer.toString((int) greenaverage));
            bundle.putString("Blue", Integer.toString((int) blueaverage));
            bundle.putString("ColorScore", Double.toString(colorscore));
            bundle.putString("XCoordinate", Integer.toString((int) x));
            bundle.putString("YCoordinate", Integer.toString((int) y));
            bundle.putString("Radius", Integer.toString((int) r));

            intentBundle.putExtras(bundle);
            startActivity(intentBundle);

        } else {

            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("Circle Detection Failed")
                    .setMessage("Sample was not found. Please use another image. Try zooming in or zooming out of sample.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent goToNextActivity = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(goToNextActivity);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }

    }
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_circle);
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(this.openFileInput("myImage"));

            ImageView selectionimage = (ImageView) findViewById(R.id.secondImageView);
            selectionimage.setImageBitmap(bitmap);;
        }catch (FileNotFoundException e )
        {
            e.printStackTrace();
        }
    }

}
