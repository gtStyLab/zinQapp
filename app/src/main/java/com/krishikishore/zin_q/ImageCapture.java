package com.krishikishore.zin_q;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.media.ExifInterface;
import java.io.IOException;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import org.opencv.core.Mat;
import org.opencv.android.Utils;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Size;
import org.opencv.core.CvType;
import org.opencv.android.OpenCVLoader;
import android.util.Log;
import java.util.ArrayList;
import android.graphics.Color;
import java.util.Arrays;
import java.util.Comparator;
import android.support.v7.app.AlertDialog;
import android.os.Build;
import android.content.DialogInterface;






public class ImageCapture extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_ALBUM = 2;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final String TAG = "ImageCapture";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_capture);

        Button buttonLoadImage = (Button) findViewById(R.id.GalleryBtn);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (ContextCompat.checkSelfPermission(ImageCapture.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(ImageCapture.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    } else {
                        ActivityCompat.requestPermissions(ImageCapture.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                } else {
                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }
            }
        });
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

        double purplex = 0;
        double purpley = 0;
        double purpler = 0;
        double purplered = 0;
        double purplegreen = 0;
        double purpleblue = 0;
        double redx = 0;
        double redy = 0;
        double redr = 0;
        double redred = 0;
        double redgreen = 0;
        double redblue = 0;
        double yellowx = 0;
        double yellowy = 0;
        double yellowr = 0;
        double yellowred = 0;
        double yellowgreen = 0;
        double yellowblue = 0;

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

        int minRadius = 50, maxRadius = 200;

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

                if (numberOfCircles > 500) {
                    param2 += 10;
                    param1 +=10;
                }

                if (numberOfCircles == 3) {

                    double informationCircles[][] = new double[3][6];

                    for (int i=0; i < numberOfCircles; i++) {

                        ArrayList<Integer> reds = new ArrayList<Integer>();
                        ArrayList<Integer> blues = new ArrayList<Integer>();
                        ArrayList<Integer> greens = new ArrayList<Integer>();
                        int redaverage = 0;
                        int greenaverage = 0;
                        int blueaverage = 0;
                        int redsum = 0;
                        int greensum = 0;
                        int bluesum = 0;

                        double[] circleCoordinates = circles.get(0, i);
                        x = (int) circleCoordinates[0];
                        y = (int) circleCoordinates[1];
                        r = (int) circleCoordinates[2];

                        informationCircles[i][0] = x;
                        informationCircles[i][1] = y;
                        informationCircles[i][2] = r;

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

                        informationCircles[i][3] = redaverage;
                        informationCircles[i][4] = greenaverage;
                        informationCircles[i][5] = blueaverage;

                    }

                    java.util.Arrays.sort(informationCircles, new java.util.Comparator<double[]>() {
                        public int compare(double[] a, double[] b) {
                            return Double.compare(a[0], b[0]);
                        }
                    });

                     purplex = informationCircles[0][0];
                     purpley = informationCircles[0][1];
                     purpler = informationCircles[0][2];
                     purplered = informationCircles[0][3];
                     purplegreen = informationCircles[0][4];
                     purpleblue = informationCircles[0][5];

                     redx = informationCircles[1][0];
                     redy = informationCircles[1][1];
                     redr = informationCircles[1][2];
                     redred = informationCircles[1][3];
                     redgreen = informationCircles[1][4];
                     redblue = informationCircles[1][5];

                     yellowx = informationCircles[2][0];
                     yellowy = informationCircles[2][1];
                     yellowr = informationCircles[2][2];
                     yellowred = informationCircles[2][3];
                     yellowgreen = informationCircles[2][4];
                     yellowblue = informationCircles[2][5];

                    break outerloop;

                }

                /*
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
                */

                param2 += 4;
            }

            param2 = 9;
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


        redaverage = redsum / reds.size();
        greenaverage = greensum / greens.size();
        blueaverage = bluesum / blues.size();

        */

        if (numberOfCircles == 3) {

            double xval[] = {0, 2, 4};
            double redyval[] = {purplered, redred, yellowred};
            double greenyval[] = {purplegreen, redgreen, yellowgreen};
            double blueyval[] = {purpleblue, redblue, yellowblue};

            int n = xval.length;
            double m, c, sum_x = 0, sum_y = 0,
                    sum_xy = 0, sum_x2 = 0;
            for (int i = 0; i < n; i++) {
                sum_x += xval[i];
                sum_y += redyval[i];
                sum_xy += xval[i] * redyval[i];
                sum_x2 += Math.pow(xval[i], 2);
            }

            m = (n * sum_xy - sum_x * sum_y) / (n * sum_x2 - Math.pow(sum_x, 2));
            c = (sum_y - m * sum_x) / n;

            rslope = m;
            rint = c;


            int n0 = xval.length;
            double m0, c0, sum_x0 = 0, sum_y0 = 0,
                    sum_xy0 = 0, sum_x20 = 0;
            for (int i = 0; i < n0; i++) {
                sum_x0 += xval[i];
                sum_y0 += greenyval[i];
                sum_xy0 += xval[i] * greenyval[i];
                sum_x20 += Math.pow(xval[i], 2);
            }

            m0 = (n0 * sum_xy0 - sum_x0 * sum_y0) / (n0 * sum_x20 - Math.pow(sum_x0, 2));
            c0 = (sum_y0 - m0 * sum_x0) / n0;

            gslope = m0;
            gint = c0;

            int n00 = xval.length;
            double m00, c00, sum_x00 = 0, sum_y00 = 0,
                    sum_xy00 = 0, sum_x200 = 0;
            for (int i = 0; i < n00; i++) {
                sum_x00 += xval[i];
                sum_y00 += blueyval[i];
                sum_xy00 += xval[i] * blueyval[i];
                sum_x200 += Math.pow(xval[i], 2);
            }

            m00 = (n00 * sum_xy00 - sum_x00 * sum_y00) / (n00 * sum_x200 - Math.pow(sum_x00, 2));
            c00 = (sum_y00 - m00 * sum_x00) / n00;

            bslope = m00;
            bint = c00;


            Intent intentBundle = new Intent(ImageCapture.this, ExperimentalImageCapture.class);
            Bundle bundle = new Bundle();
            bundle.putDouble("PurpleX", purplex);
            bundle.putDouble("PurpleY", purpley);
            bundle.putDouble("PurpleR", purpler);
            bundle.putDouble("PurpleRed", purplered);
            bundle.putDouble("PurpleGreen", purplegreen);
            bundle.putDouble("PurpleBlue", purpleblue);

            bundle.putDouble("RedX", redx);
            bundle.putDouble("RedY", redy);
            bundle.putDouble("RedR", redr);
            bundle.putDouble("RedRed", redred);
            bundle.putDouble("RedGreen", redgreen);
            bundle.putDouble("RedBlue", redblue);

            bundle.putDouble("YellowX", yellowx);
            bundle.putDouble("YellowY", yellowy);
            bundle.putDouble("YellowR", yellowr);
            bundle.putDouble("YellowRed", yellowred);
            bundle.putDouble("YellowGreen", yellowgreen);
            bundle.putDouble("YellowBlue", yellowblue);

            bundle.putDouble("RedSlope", rslope);
            bundle.putDouble("RedInt", rint);
            bundle.putDouble("GreenSlope", gslope);
            bundle.putDouble("GreenInt", gint);
            bundle.putDouble("BlueSlope", bslope);
            bundle.putDouble("BlueInt", bint);

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
                    .setMessage("Three controls were not found. Please use another image.")
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);

            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.mImageView);
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(picturePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            Bitmap bmRotated = rotateBitmap(bitmap, orientation);

            imageView.setImageBitmap(bmRotated);

        }


    }

}
