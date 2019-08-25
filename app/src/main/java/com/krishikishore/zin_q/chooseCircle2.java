package com.krishikishore.zin_q;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.ExifInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import android.graphics.drawable.*;
import android.graphics.Rect;
import android.util.Log;
import android.graphics.Matrix;
import android.graphics.BitmapFactory;
import java.io.*;
import org.opencv.android.*;
import org.opencv.core.*;
import org.opencv.imgproc.*;
import java.util.ArrayList;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.os.Build;
import android.content.DialogInterface;

public class chooseCircle2 extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_ALBUM = 2;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final String TAG = "chooseCircle2";

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
    chooseCircle2.ScreenResolution deviceDimensions() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // getsize() is available from API 13
        if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB_MR2) {
            Display display = chooseCircle2.this.getWindowManager().getDefaultDisplay();
            android.graphics.Point size = new Point();
            display.getSize(size);
            return new chooseCircle2.ScreenResolution(size.x, size.y);
        }
        else {
            Display display = chooseCircle2.this.getWindowManager().getDefaultDisplay();
            // getWidth() & getHeight() are deprecated
            return new chooseCircle2.ScreenResolution(display.getWidth(), display.getHeight());
        }
    }

    public String createImageFromBitmap(Bitmap bitmap) {
        String fileName = "mySmallImage2";//no .png or .jpg needed
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            // remember close file output
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public void processMoveToResults(View view) {
        ImageView image = findViewById(R.id.mImageView);
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();

        createImageFromBitmap(bitmap);
        Intent goToNextActivity = new Intent(getApplicationContext(), chooseSmallCircle2.class);
        startActivity(goToNextActivity);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Intent intentExtras = getIntent();
        String red1 = intentExtras.getStringExtra("Red1");
        String green1 = intentExtras.getStringExtra("Green1");
        String blue1 = intentExtras.getStringExtra("Blue1");

        switch(event.getAction()) {
            case MotionEvent.ACTION_UP:
                int x = (int) event.getX();
                int y = (int) event.getY();
                System.out.println("Coming in touch event");
                System.out.println("x:" + x);
                System.out.println("y:" + y);


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

                    createImageFromBitmap(newbitmap);

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

                    Intent intentBundle = new Intent(chooseCircle2.this, chooseSmallCircle2.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("Red1", red1);
                    bundle.putString("Green1", green1);
                    bundle.putString("Blue1", blue1);

                    intentBundle.putExtras(bundle);
                    startActivity(intentBundle);


                    return true;

                }
                
                return false;
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
        setContentView(R.layout.activity_choose_circle2);
        try {
            RelativeLayout LinearLayoutas = (RelativeLayout) findViewById(R.id.relativelayout);
            ImageView selectionimage = (ImageView) findViewById(R.id.secondImageView);


            selectionimage.setScaleType(ImageView.ScaleType.FIT_XY);
            LinearLayoutas.setGravity(Gravity.BOTTOM);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            chooseCircle2.ScreenResolution screenRes = deviceDimensions();
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
