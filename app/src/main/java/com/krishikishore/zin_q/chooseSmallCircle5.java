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

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class chooseSmallCircle5 extends AppCompatActivity {

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
    chooseSmallCircle5.ScreenResolution deviceDimensions() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // getsize() is available from API 13
        if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB_MR2) {
            Display display = chooseSmallCircle5.this.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return new chooseSmallCircle5.ScreenResolution(size.x, size.y);
        }
        else {
            Display display = chooseSmallCircle5.this.getWindowManager().getDefaultDisplay();
            // getWidth() & getHeight() are deprecated
            return new chooseSmallCircle5.ScreenResolution(display.getWidth(), display.getHeight());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Intent intentExtras = getIntent();
        String red1 = intentExtras.getStringExtra("Red1");
        String green1 = intentExtras.getStringExtra("Green1");
        String blue1 = intentExtras.getStringExtra("Blue1");
        String colorScore1 = intentExtras.getStringExtra("colorScore1");
        String red2 = intentExtras.getStringExtra("Red2");
        String green2 = intentExtras.getStringExtra("Green2");
        String blue2 = intentExtras.getStringExtra("Blue2");
        String colorScore2 = intentExtras.getStringExtra("colorScore2");
        String red3 = intentExtras.getStringExtra("Red3");
        String green3 = intentExtras.getStringExtra("Green3");
        String blue3 = intentExtras.getStringExtra("Blue3");
        String colorScore3 = intentExtras.getStringExtra("colorScore3");
        String red4 = intentExtras.getStringExtra("Red4");
        String green4 = intentExtras.getStringExtra("Green4");
        String blue4 = intentExtras.getStringExtra("Blue4");
        String colorScore4 = intentExtras.getStringExtra("colorScore4");

        switch (event.getAction()) {
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
                ArrayList<Integer> xvalues = new ArrayList<Integer>();
                ArrayList<Integer> yvalues = new ArrayList<Integer>();

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

                    if ((updatedx - 5 + 10) > bitmap.getWidth()) {
                        updatedx = bitmap.getWidth() - 10;
                    }

                    if ((updatedy - 5 + 10) > bitmap.getHeight()) {
                        updatedy = bitmap.getHeight() - 10;
                    }

                    if ((updatedx + 5) < 0) {
                        updatedx = 0;
                    }

                    if ((updatedy + 5) < 0) {
                        updatedy = 0;
                    }

                    if ((updatedx - 5) < 0) {
                        updatedx = 5;
                    }

                    if ((updatedy - 5) < 0) {
                        updatedy = 5;
                    }

                    Bitmap newbitmap = Bitmap.createBitmap(bitmap, updatedx - 5, updatedy - 5, 10, 10);

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
                    double colorscore5 = ((((redaverage - 61) / 42.9) + ((greenaverage - 48) / 26.6) - ((blueaverage - 79.6) / 7.3)) / 3);


                    Intent intentBundle = new Intent(chooseSmallCircle5.this, chooseCircle6.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Red1", red1);
                    bundle.putString("Green1", green1);
                    bundle.putString("Blue1", blue1);
                    bundle.putString("colorScore1", colorScore1);
                    bundle.putString("Red2", red2);
                    bundle.putString("Green2", green2);
                    bundle.putString("Blue2", blue2);
                    bundle.putString("colorScore2", colorScore2);
                    bundle.putString("Red3", red3);
                    bundle.putString("Green3", green3);
                    bundle.putString("Blue3", blue3);
                    bundle.putString("colorScore3", colorScore3);
                    bundle.putString("Red4", red4);
                    bundle.putString("Green4", green4);
                    bundle.putString("Blue4", blue4);
                    bundle.putString("colorScore4", colorScore4);
                    bundle.putString("Red5", Integer.toString((int) redaverage));
                    bundle.putString("Green5", Integer.toString((int) greenaverage));
                    bundle.putString("Blue5", Integer.toString((int) blueaverage));
                    bundle.putString("colorScore5", Integer.toString((int) colorscore5));

                    intentBundle.putExtras(bundle);
                    startActivity(intentBundle);

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
        setContentView(R.layout.activity_choose_small_circle5);
        try {

            RelativeLayout LinearLayoutas = (RelativeLayout) findViewById(R.id.relativelayout);
            ImageView selectionimage = (ImageView) findViewById(R.id.secondImageView);


            selectionimage.setScaleType(ImageView.ScaleType.FIT_XY);
            LinearLayoutas.setGravity(Gravity.BOTTOM);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            chooseSmallCircle5.ScreenResolution screenRes = deviceDimensions();
            selectionimage.getLayoutParams().height = (int)(screenRes.height);
            selectionimage.getLayoutParams().width = screenRes.width;

            Bitmap bitmap = BitmapFactory.decodeStream(this.openFileInput("mySmallImage2"));

            selectionimage.setImageBitmap(bitmap);;
        }catch (FileNotFoundException e )
        {
            e.printStackTrace();
        }
    }

}