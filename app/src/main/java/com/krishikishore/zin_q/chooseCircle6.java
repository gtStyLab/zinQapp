package com.krishikishore.zin_q;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class chooseCircle6 extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_ALBUM = 2;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final String TAG = "chooseCircle6";

    public class ScreenResolution {
        int width;
        int height;
        public ScreenResolution(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    chooseCircle6.ScreenResolution deviceDimensions() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // getsize() is available from API 13
        if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB_MR2) {
            Display display = chooseCircle6.this.getWindowManager().getDefaultDisplay();
            android.graphics.Point size = new Point();
            display.getSize(size);
            return new chooseCircle6.ScreenResolution(size.x, size.y);
        }
        else {
            Display display = chooseCircle6.this.getWindowManager().getDefaultDisplay();
            // getWidth() & getHeight() are deprecated
            return new chooseCircle6.ScreenResolution(display.getWidth(), display.getHeight());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Intent intentExtras = getIntent();
        String red1 = intentExtras.getStringExtra("Red1");
        String green1 = intentExtras.getStringExtra("Green1");
        String blue1 = intentExtras.getStringExtra("Blue1");
        String colorScore1 = intentExtras.getStringExtra("ColorScore1");
        String x1 = intentExtras.getStringExtra("XCoordinate1");
        String y1 = intentExtras.getStringExtra("YCoordinate1");
        String radius1 = intentExtras.getStringExtra("Radius1");

        String red2 = intentExtras.getStringExtra("Red2");
        String green2 = intentExtras.getStringExtra("Green2");
        String blue2 = intentExtras.getStringExtra("Blue2");
        String colorScore2 = intentExtras.getStringExtra("ColorScore2");
        String x2 = intentExtras.getStringExtra("XCoordinate2");
        String y2 = intentExtras.getStringExtra("YCoordinate2");
        String radius2 = intentExtras.getStringExtra("Radius2");

        String red3 = intentExtras.getStringExtra("Red3");
        String green3 = intentExtras.getStringExtra("Green3");
        String blue3 = intentExtras.getStringExtra("Blue3");
        String colorScore3 = intentExtras.getStringExtra("ColorScore3");
        String x3 = intentExtras.getStringExtra("XCoordinate3");
        String y3 = intentExtras.getStringExtra("YCoordinate3");
        String radius3 = intentExtras.getStringExtra("Radius3");

        String red4 = intentExtras.getStringExtra("Red4");
        String green4 = intentExtras.getStringExtra("Green4");
        String blue4 = intentExtras.getStringExtra("Blue4");
        String colorScore4 = intentExtras.getStringExtra("ColorScore4");
        String x4 = intentExtras.getStringExtra("XCoordinate4");
        String y4 = intentExtras.getStringExtra("YCoordinate4");
        String radius4 = intentExtras.getStringExtra("Radius4");

        String red5 = intentExtras.getStringExtra("Red5");
        String green5 = intentExtras.getStringExtra("Green5");
        String blue5 = intentExtras.getStringExtra("Blue5");
        String colorScore5 = intentExtras.getStringExtra("ColorScore5");
        String x5 = intentExtras.getStringExtra("XCoordinate5");
        String y5 = intentExtras.getStringExtra("YCoordinate5");
        String radius5 = intentExtras.getStringExtra("Radius5");
        switch(event.getAction()) {
            case MotionEvent.ACTION_UP:
                int x = (int) event.getX();
                int y = (int) event.getY();
                System.out.println("Coming in touch event");
                System.out.println("x:" + x);
                System.out.println("y:" + y);

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


                double testHeight = bitmapDrawable.getIntrinsicHeight();
                double testWidth = bitmapDrawable.getIntrinsicWidth();
                double anothertestHeight = image.getHeight();
                double anothertestWidth = image.getWidth();


                double Xratio = testWidth / anothertestWidth;
                double Yratio = testHeight / anothertestHeight;

                int ih = image.getMeasuredHeight();//height of imageView
                int iw = image.getMeasuredWidth();//width of imageView
                int iH = image.getDrawable().getIntrinsicHeight();//original height of underlying image
                int iW = image.getDrawable().getIntrinsicWidth();//original width of underlying image

                if (ih / iH <= iw / iW) {
                    iw = iW * ih / iH;//rescaled width of image within ImageView
                } else {
                    ih = iH * iw / iW;//rescaled height of image within ImageView
                }


                int actualImageViewHeight = image.getHeight();
                int actualImageViewWidth = image.getWidth();


                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenheight = displayMetrics.heightPixels;
                int screenwidth = displayMetrics.widthPixels;


                int intrinsicHeight = bitmapDrawable.getIntrinsicHeight();
                int intrinsicWidth = bitmapDrawable.getIntrinsicWidth();

                int scaledHeight = imageBounds.height();
                int scaledWidth = imageBounds.width();

                double heightRatio = intrinsicHeight / scaledHeight;
                double widthRatio = intrinsicWidth / scaledWidth;

                int leftcoordinate = image.getLeft();
                int topcoordinate = image.getTop();

                int[] viewCoords = new int[2];
                image.getLocationOnScreen(viewCoords);

                int scaledImageOffsetX = (int) (event.getX() - viewCoords[0]);
                int scaledImageOffsetY = (int) (event.getY() - viewCoords[1]);

                int originalImageOffsetX = (int) (scaledImageOffsetX * Xratio);
                int originalImageOffsetY = (int) (scaledImageOffsetY * Yratio);

                int imageX = x - viewCoords[0];
                int imageY = y - viewCoords[1];

                int updatedx = (int) ((int) imageX * Xratio);
                int updatedy = (int) ((int) imageY * Yratio);

                int imageviewHeight = image.getHeight();
                int imageviewWidth = image.getWidth();

                float[] f = new float[9];
                image.getImageMatrix().getValues(f);

                final float scaleX = f[Matrix.MSCALE_X];
                final float scaleY = f[Matrix.MSCALE_Y];

                final Drawable d = bitmapDrawable;
                final int origW = d.getIntrinsicWidth();
                final int origH = d.getIntrinsicHeight();
                boolean inside = false;

                if (x > viewCoords[0] && x < viewCoords[0] + imageviewWidth && y > viewCoords[1] && y < viewCoords[1] + imageviewHeight && event.getAction() == MotionEvent.ACTION_UP) {

                    inside = true;
                    Log.e(TAG, "onTouchEvent: drawable touched ");


                    if (!OpenCVLoader.initDebug()) {
                        Log.e(TAG, "Cannot connect to OpenCV Manager");
                    }
                    Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();

                    if ((updatedx - 100 + 200) > bitmap.getWidth()) {
                        updatedx = bitmap.getWidth() - 200;
                    }

                    if ((updatedy - 100 + 200) > bitmap.getHeight()) {
                        updatedy = bitmap.getHeight() - 200;
                    }

                    if ((updatedx + 100) < 0) {
                        updatedx = 0;
                    }

                    if ((updatedy + 100) < 0) {
                        updatedy = 0;
                    }

                    if ((updatedx - 100) < 0) {
                        updatedx = 100;
                    }

                    if ((updatedy - 100) < 0) {
                        updatedy = 100;
                    }

                    Bitmap newbitmap = Bitmap.createBitmap(bitmap, updatedx - 100, updatedy - 100, 200, 200);

                    Mat mat = new Mat(newbitmap.getWidth(), newbitmap.getHeight(),
                            CvType.CV_8UC1);
                    Mat grayMat = new Mat(newbitmap.getWidth(), newbitmap.getHeight(),
                            CvType.CV_8UC1);

                    Utils.bitmapToMat(newbitmap, mat);

                    int colorChannels = (mat.channels() == 3) ? Imgproc.COLOR_BGR2GRAY
                            : ((mat.channels() == 4) ? Imgproc.COLOR_BGRA2GRAY : 1);

                    Imgproc.cvtColor(mat, grayMat, colorChannels);

                    Imgproc.GaussianBlur(grayMat, grayMat, new Size(9, 9), 2, 2);

                    double dp = 2d;
                    double minDist = 100;

                    int minRadius = 10, maxRadius = 200;

                    double param1 = 20, param2 = 5;

                    outerloop:
                    for (int k = 0; k < 25; k++) {
                        param1 += 4;
                        for (int j = 0; j < 25; j++) {

                            Mat circles = new Mat(newbitmap.getWidth(),
                                    newbitmap.getHeight(), CvType.CV_8UC1);


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
                                rcenter = (int) (circleCoordinates[2]);

                                if (xcenter <= 0 || ycenter <= 0 || rcenter <= 0 || x <= 0 || y <= 0)
                                {
                                    AlertDialog.Builder builder;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                                    } else {
                                        builder = new AlertDialog.Builder(this);
                                    }
                                    builder.setTitle("Circle Detection Failed")
                                            .setMessage("Sample was not found. Please try clicking again or taking another picture.")
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            })
                                            .setIcon(android.R.drawable.ic_dialog_alert).show();

                                    return true;

                                }

                                informationCircles[0] = xcenter;
                                informationCircles[1] = ycenter;
                                informationCircles[2] = rcenter;

                                for (int xv = xcenter - rcenter; xv < xcenter + rcenter; xv++) {
                                    for (int yv = ycenter - rcenter; yv < ycenter + rcenter; yv++) {
                                        double dx = xv - xcenter;
                                        double dy = yv - ycenter;
                                        double distanceSquared = dx * dx + dy * dy;

                                        if (distanceSquared <= (rcenter * rcenter)) {
                                            if (xv >=0 && yv >= 0 && xv<newbitmap.getWidth() && yv<newbitmap.getHeight()) {
                                                int pixel = newbitmap.getPixel(xv, yv);
                                                int redValue = Color.red(pixel);
                                                int greenValue = Color.green(pixel);
                                                int blueValue = Color.blue(pixel);
                                                if ( ( (redValue-greenValue)*(redValue-greenValue) ) + ( (redValue-blueValue)*(redValue-blueValue) ) + ( (greenValue-blueValue)*(greenValue-blueValue) )  >5000 ) {

                                                    reds.add(redValue);
                                                    greens.add(greenValue);
                                                    blues.add(blueValue);
                                                }

                                            }

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

                                if (reds.size() ==0 || greens.size() ==0 || blues.size() ==0 ) {
                                    AlertDialog.Builder builder;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                                    } else {
                                        builder = new AlertDialog.Builder(this);
                                    }
                                    builder.setTitle("Circle Detection Failed")
                                            .setMessage("Try clicking closer to the center of the sample.")
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            })
                                            .setIcon(android.R.drawable.ic_dialog_alert).show();

                                    return true;
                                }

                                redaverage = redsum / reds.size();
                                greenaverage = greensum / greens.size();
                                blueaverage = bluesum / blues.size();

                                informationCircles[3] = redaverage;
                                informationCircles[4] = greenaverage;
                                informationCircles[5] = blueaverage;

                                break outerloop;

                            }

                            param2 += 3;
                        }

                        param2 = 3;
                    }

            if (numberOfCircles == 1) {

                double xval[] = {0, 2, 4};

                redaverage = redsum / reds.size();
                greenaverage = greensum / greens.size();
                blueaverage = bluesum / blues.size();

                colorscore = ((((redaverage - 61) / 42.9) + ((greenaverage - 48) / 26.6) - ((blueaverage - 79.6) / 7.3)) / 3);


                Intent intentBundle = new Intent(chooseCircle6.this, ResultsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Red6", Integer.toString((int) redaverage));
                bundle.putString("Green6", Integer.toString((int) greenaverage));
                bundle.putString("Blue6", Integer.toString((int) blueaverage));
                bundle.putString("ColorScore6", Double.toString(colorscore));
                bundle.putString("XCoordinate6", Integer.toString((int) xcenter));
                bundle.putString("YCoordinate6", Integer.toString((int) ycenter));
                bundle.putString("Radius6", Integer.toString((int) rcenter));

                bundle.putString("Red5", red5);
                bundle.putString("Green5", green5);
                bundle.putString("Blue5", blue5);
                bundle.putString("ColorScore5", colorScore5);
                bundle.putString("XCoordinate5", x5);
                bundle.putString("YCoordinate5", y5);
                bundle.putString("Radius5", radius5);

                bundle.putString("Red4", red4);
                bundle.putString("Green4", green4);
                bundle.putString("Blue4", blue4);
                bundle.putString("ColorScore4", colorScore4);
                bundle.putString("XCoordinate4", x4);
                bundle.putString("YCoordinate4", y4);
                bundle.putString("Radius4", radius4);

                bundle.putString("Red3", red3);
                bundle.putString("Green3", green3);
                bundle.putString("Blue3", blue3);
                bundle.putString("ColorScore3", colorScore3);
                bundle.putString("XCoordinate3", x3);
                bundle.putString("YCoordinate3", y3);
                bundle.putString("Radius3", radius3);

                bundle.putString("Red2", red2);
                bundle.putString("Green2", green2);
                bundle.putString("Blue2", blue2);
                bundle.putString("ColorScore2", colorScore2);
                bundle.putString("XCoordinate2", x2);
                bundle.putString("YCoordinate2", y2);
                bundle.putString("Radius2", radius2);

                bundle.putString("Red1", red1);
                bundle.putString("Green1", green1);
                bundle.putString("Blue1", blue1);
                bundle.putString("ColorScore1", colorScore1);
                bundle.putString("XCoordinate1", x1);
                bundle.putString("YCoordinate1", y1);
                bundle.putString("Radius1", radius1);

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
                        .setMessage("Sample was not found. Please try clicking again or taking another picture.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert).show();

                return true;

            }

                    return true;

                }

                if (!inside) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(this);
                    }
                    builder.setTitle("Click Inside Image")
                            .setMessage("You have clicked outside of the image. Please click inside of the image.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert).show();

                    return false;

                }
                return false;
            case MotionEvent.ACTION_MOVE:
                return false;
            case MotionEvent.ACTION_DOWN:
                return false;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_circle6);
        try {
            RelativeLayout LinearLayoutas = (RelativeLayout) findViewById(R.id.relativelayout);
            ImageView selectionimage = (ImageView) findViewById(R.id.secondImageView);


            selectionimage.setScaleType(ImageView.ScaleType.FIT_XY);
            LinearLayoutas.setGravity(Gravity.BOTTOM);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            chooseCircle6.ScreenResolution screenRes = deviceDimensions();
            selectionimage.getLayoutParams().height = (int)(screenRes.height);
            selectionimage.getLayoutParams().width = screenRes.width;

            Bitmap bitmap = BitmapFactory.decodeStream(this.openFileInput("myImage"));

            selectionimage.setImageBitmap(bitmap);;
        }catch (FileNotFoundException e )
        {
            e.printStackTrace();
        }
    }
}
