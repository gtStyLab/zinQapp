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

public class chooseSmallCircle extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_ALBUM = 2;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final String TAG = "chooseCircle";

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
    chooseSmallCircle.ScreenResolution deviceDimensions() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // getsize() is available from API 13
        if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB_MR2) {
            Display display = chooseSmallCircle.this.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return new chooseSmallCircle.ScreenResolution(size.x, size.y);
        }
        else {
            Display display = chooseSmallCircle.this.getWindowManager().getDefaultDisplay();
            // getWidth() & getHeight() are deprecated
            return new chooseSmallCircle.ScreenResolution(display.getWidth(), display.getHeight());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                int x = (int) event.getX();
                int y = (int) event.getY();
                System.out.println("Coming in touch event");
                System.out.println("x:" + x);
                System.out.println("y:" + y);


                int redaverage = 0;
                int greenaverage = 0;
                int blueaverage = 0;
                int redsum = 0;
                int greensum = 0;
                int bluesum = 0;
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

                if (x > viewCoords[0] && x < viewCoords[0] + imageviewWidth && y > viewCoords[1] && y < viewCoords[1] + imageviewHeight && event.getAction() == MotionEvent.ACTION_UP) {

                    Log.e(TAG, "onTouchEvent: drawable touched ");


                    if (!OpenCVLoader.initDebug()) {
                        Log.e(TAG, "Cannot connect to OpenCV Manager");
                    }
                    Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();

                    int TargetWidth = 20;


                    if ((updatedx - TargetWidth/2 + TargetWidth) > bitmap.getWidth()) {
                        updatedx = bitmap.getWidth() - TargetWidth;
                    }

                    if ((updatedy - TargetWidth/2 + TargetWidth) > bitmap.getHeight()) {
                        updatedy = bitmap.getHeight() - TargetWidth;
                    }

                    if ((updatedx + TargetWidth/2) < 0) {
                        updatedx = 0;
                    }

                    if ((updatedy + TargetWidth/2) < 0) {
                        updatedy = 0;
                    }

                    if ((updatedx - TargetWidth/2) < 0) {
                        updatedx = TargetWidth/2;
                    }

                    if ((updatedy - TargetWidth/2) < 0) {
                        updatedy = TargetWidth/2;
                    }

                    Bitmap newbitmap = Bitmap.createBitmap(bitmap, updatedx - TargetWidth/2, updatedy - TargetWidth/2, TargetWidth, TargetWidth);

                    for (int l = 0; l < newbitmap.getWidth(); l++) {
                        for (int m = 0; m < newbitmap.getHeight(); m++) {
                            int pixel = newbitmap.getPixel(l, m);
                            reds.add(Color.red(pixel));
                            greens.add(Color.green(pixel));
                            blues.add(Color.blue(pixel));
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

                    Intent intentBundle = new Intent(chooseSmallCircle.this, chooseCircle2.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Red1", Integer.toString((int) redaverage));
                    bundle.putString("Green1", Integer.toString((int) greenaverage));
                    bundle.putString("Blue1", Integer.toString((int) blueaverage));

                    intentBundle.putExtras(bundle);
                    startActivity(intentBundle);


                    return false;
                }
                    case MotionEvent.ACTION_MOVE:
                        return false;
                    case MotionEvent.ACTION_DOWN:
                        return false;
                }
                return false;
        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_small_circle);
        try {

            RelativeLayout LinearLayoutas = (RelativeLayout) findViewById(R.id.relativelayout);
            ImageView selectionimage = (ImageView) findViewById(R.id.secondImageView);


            selectionimage.setScaleType(ImageView.ScaleType.FIT_XY);
            LinearLayoutas.setGravity(Gravity.BOTTOM);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            chooseSmallCircle.ScreenResolution screenRes = deviceDimensions();
            selectionimage.getLayoutParams().height = (int)(screenRes.height);
            selectionimage.getLayoutParams().width = screenRes.width;

            Bitmap bitmap = BitmapFactory.decodeStream(this.openFileInput("mySmallImage1"));

            selectionimage.setImageBitmap(bitmap);;
        }catch (FileNotFoundException e )
        {
            e.printStackTrace();
        }
    }



}
