package com.krishikishore.zin_q;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.content.res.AppCompatResources;
import android.support.v4.graphics.drawable.*;
import android.widget.ImageView;
import android.view.View;
import android.graphics.drawable.*;
import android.support.v4.content.ContextCompat;
import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import java.util.ArrayList;


public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ArrayList<Integer> redsarray = new ArrayList<Integer>();
        ArrayList<Integer> bluesarray = new ArrayList<Integer>();
        ArrayList<Integer> greensarray = new ArrayList<Integer>();

        Intent intentExtras = getIntent();
        String red1 = intentExtras.getStringExtra("Red1");
        String green1 = intentExtras.getStringExtra("Green1");
        String blue1 = intentExtras.getStringExtra("Blue1");


        String red2 = intentExtras.getStringExtra("Red2");
        String green2 = intentExtras.getStringExtra("Green2");
        String blue2 = intentExtras.getStringExtra("Blue2");


        String red3 = intentExtras.getStringExtra("Red3");
        String green3 = intentExtras.getStringExtra("Green3");
        String blue3 = intentExtras.getStringExtra("Blue3");

        String red4 = intentExtras.getStringExtra("Red4");
        String green4 = intentExtras.getStringExtra("Green4");
        String blue4 = intentExtras.getStringExtra("Blue4");

        String red5 = intentExtras.getStringExtra("Red5");
        String green5 = intentExtras.getStringExtra("Green5");
        String blue5 = intentExtras.getStringExtra("Blue5");

        String red6 = intentExtras.getStringExtra("Red6");
        String green6 = intentExtras.getStringExtra("Green6");
        String blue6 = intentExtras.getStringExtra("Blue6");


        //line fitting
        double xcoordinates[] = { Math.log10(0.5), Math.log10(1), Math.log10(2), Math.log10(5), Math.log10(10) };
        double redplot[] = {Double.parseDouble(red1), Double.parseDouble(red2), Double.parseDouble(red3),Double.parseDouble(red4), Double.parseDouble(red5)} ;
        double greenplot[] = {Double.parseDouble(green1), Double.parseDouble(green2), Double.parseDouble(green3),Double.parseDouble(green4), Double.parseDouble(green5)};
        double blueplot[] = {Double.parseDouble(blue1), Double.parseDouble(blue2), Double.parseDouble(blue3),Double.parseDouble(blue4), Double.parseDouble(blue5)};

        int i, j;
        float m, c, sum_x = 0, sum_y = 0, sum_xy = 0, sum_x2 = 0;
        int n = xcoordinates.length;
        for (i = 0; i < n; i++) {
            sum_x += redplot[i];
            sum_y += xcoordinates[i];
            sum_xy += xcoordinates[i] * redplot[i];
            sum_x2 += (redplot[i] * redplot[i]);
        }

        float redslope = (n * sum_xy - sum_x * sum_y) / (n * sum_x2 - (sum_x * sum_x));
        float redint = (sum_y - redslope * sum_x) / n;

        int i2, j2;
        float m2, c2, sum_x4 = 0, sum_y2 = 0, sum_xy2 = 0, sum_x22 = 0;
        int n2 = xcoordinates.length;
        for (i2 = 0; i2 < n2; i2++) {
            sum_x4 += greenplot[i2];
            sum_y2 += xcoordinates[i2];
            sum_xy2 += xcoordinates[i2] * greenplot[i2];
            sum_x22 += (greenplot[i2] * greenplot[i2]);
        }

        float greenslope = (n2 * sum_xy2 - sum_x4 * sum_y2) / (n2 * sum_x22 - (sum_x4 * sum_x4));
        float greenint = (sum_y2 - greenslope * sum_x4) / n2;

        int i3, j3;
        float m3, c3, sum_x6 = 0, sum_y3 = 0, sum_xy3 = 0, sum_x23 = 0;
        int n3 = xcoordinates.length;
        for (i3 = 0; i3 < n3; i3++) {
            sum_x6 += blueplot[i3];
            sum_y3 += xcoordinates[i3];
            sum_xy3 += xcoordinates[i3] * blueplot[i3];
            sum_x23 += (blueplot[i3] * blueplot[i3]);
        }

        float blueslope = (n3 * sum_xy3 - sum_x6 * sum_y3) / (n3 * sum_x23 - (sum_x6 * sum_x6));
        float blueint = (sum_y3 - greenslope * sum_x6) / n3;

        double redvalue = Double.parseDouble(red6);
        double greenvalue = Double.parseDouble(green6);
        double bluevalue = Double.parseDouble(blue6);

        double intervalred = ((redvalue)*(redslope)) + redint;
        double intervalgreen = ((greenvalue)*(greenslope)) + greenint;
        double intervalblue = ((bluevalue)*(blueslope)) + blueint;

        double coloraverage = (intervalred+intervalgreen+intervalblue)/3;

        double finalconcentration = Math.pow(10, coloraverage);
        finalconcentration = finalconcentration*4;


        View colorRectangle6 = (View)findViewById(R.id.color6);
        Drawable background6 = colorRectangle6.getBackground();
        if (background6 instanceof ShapeDrawable) {
            ((ShapeDrawable)background6).getPaint().setColor(Color.rgb(Integer.parseInt(red6), Integer.parseInt(green6), Integer.parseInt(blue6)));
        } else if (background6 instanceof GradientDrawable) {
            ((GradientDrawable)background6).setColor(Color.rgb(Integer.parseInt(red6), Integer.parseInt(green6), Integer.parseInt(blue6)));
        } else if (background6 instanceof ColorDrawable) {
            ((ColorDrawable)background6).setColor(Color.rgb(Integer.parseInt(red6), Integer.parseInt(green6), Integer.parseInt(blue6)));
        }

        double doubleColorScore6 = finalconcentration;
        String colorScore6a = String.format("%.3f", doubleColorScore6);
        TextView colorScoreDisplay6 = (TextView) findViewById(R.id.concentration);
        colorScoreDisplay6.setText("Concentration: " + colorScore6a);

        TextView redDisplay6 = (TextView) findViewById(R.id.red6);
        redDisplay6.setText(red6);

        TextView greenDisplay6 = (TextView) findViewById(R.id.green6);
        greenDisplay6.setText(green6);

        TextView blueDisplay6 = (TextView) findViewById(R.id.blue6);
        blueDisplay6.setText(blue6);

        TextView levelDisplay6 = (TextView) findViewById(R.id.level);

        if (doubleColorScore6 < 8) {
            levelDisplay6.setText("Level: Low");
        } else if (doubleColorScore6 < 15) {
            levelDisplay6.setText("Level: Borderline");
        } else {
            levelDisplay6.setText("Level: High");
        }


        View colorRectangle1 = (View)findViewById(R.id.color1);
        Drawable background1 = colorRectangle1.getBackground();
        if (background1 instanceof ShapeDrawable) {
            ((ShapeDrawable)background1).getPaint().setColor(Color.rgb(Integer.parseInt(red1), Integer.parseInt(green1), Integer.parseInt(blue1)));
        } else if (background1 instanceof GradientDrawable) {
            ((GradientDrawable)background1).setColor(Color.rgb(Integer.parseInt(red1), Integer.parseInt(green1), Integer.parseInt(blue1)));
        } else if (background1 instanceof ColorDrawable) {
            ((ColorDrawable)background1).setColor(Color.rgb(Integer.parseInt(red1), Integer.parseInt(green1), Integer.parseInt(blue1)));
        }


        TextView redDisplay1 = (TextView) findViewById(R.id.red1);
        redDisplay1.setText(red1);

        TextView greenDisplay1 = (TextView) findViewById(R.id.green1);
        greenDisplay1.setText(green1);

        TextView blueDisplay1 = (TextView) findViewById(R.id.blue1);
        blueDisplay1.setText(blue1);





        View colorRectangle2 = (View)findViewById(R.id.color2);
        Drawable background2 = colorRectangle2.getBackground();
        if (background2 instanceof ShapeDrawable) {
            ((ShapeDrawable)background2).getPaint().setColor(Color.rgb(Integer.parseInt(red2), Integer.parseInt(green2), Integer.parseInt(blue2)));
        } else if (background2 instanceof GradientDrawable) {
            ((GradientDrawable)background2).setColor(Color.rgb(Integer.parseInt(red2), Integer.parseInt(green2), Integer.parseInt(blue2)));
        } else if (background2 instanceof ColorDrawable) {
            ((ColorDrawable)background2).setColor(Color.rgb(Integer.parseInt(red2), Integer.parseInt(green2), Integer.parseInt(blue2)));
        }


        TextView redDisplay2 = (TextView) findViewById(R.id.red2);
        redDisplay2.setText(red2);

        TextView greenDisplay2 = (TextView) findViewById(R.id.green2);
        greenDisplay2.setText(green2);

        TextView blueDisplay2 = (TextView) findViewById(R.id.blue2);
        blueDisplay2.setText(blue2);





        View colorRectangle3 = (View)findViewById(R.id.color3);
        Drawable background3 = colorRectangle3.getBackground();
        if (background3 instanceof ShapeDrawable) {
            ((ShapeDrawable)background3).getPaint().setColor(Color.rgb(Integer.parseInt(red3), Integer.parseInt(green3), Integer.parseInt(blue3)));
        } else if (background3 instanceof GradientDrawable) {
            ((GradientDrawable)background3).setColor(Color.rgb(Integer.parseInt(red3), Integer.parseInt(green3), Integer.parseInt(blue3)));
        } else if (background3 instanceof ColorDrawable) {
            ((ColorDrawable)background3).setColor(Color.rgb(Integer.parseInt(red3), Integer.parseInt(green3), Integer.parseInt(blue3)));
        }

        TextView redDisplay3 = (TextView) findViewById(R.id.red3);
        redDisplay3.setText(red3);

        TextView greenDisplay3 = (TextView) findViewById(R.id.green3);
        greenDisplay3.setText(green3);

        TextView blueDisplay3 = (TextView) findViewById(R.id.blue3);
        blueDisplay3.setText(blue3);





        View colorRectangle4 = (View)findViewById(R.id.color4);
        Drawable background4 = colorRectangle4.getBackground();
        if (background4 instanceof ShapeDrawable) {
            ((ShapeDrawable)background4).getPaint().setColor(Color.rgb(Integer.parseInt(red4), Integer.parseInt(green4), Integer.parseInt(blue4)));
        } else if (background4 instanceof GradientDrawable) {
            ((GradientDrawable)background4).setColor(Color.rgb(Integer.parseInt(red4), Integer.parseInt(green4), Integer.parseInt(blue4)));
        } else if (background4 instanceof ColorDrawable) {
            ((ColorDrawable)background4).setColor(Color.rgb(Integer.parseInt(red4), Integer.parseInt(green4), Integer.parseInt(blue4)));
        }


        TextView redDisplay4 = (TextView) findViewById(R.id.red4);
        redDisplay4.setText(red4);

        TextView greenDisplay4 = (TextView) findViewById(R.id.green4);
        greenDisplay4.setText(green4);

        TextView blueDisplay4 = (TextView) findViewById(R.id.blue4);
        blueDisplay4.setText(blue4);




        View colorRectangle5 = (View)findViewById(R.id.color5);
        Drawable background5 = colorRectangle5.getBackground();
        if (background5 instanceof ShapeDrawable) {
            ((ShapeDrawable)background5).getPaint().setColor(Color.rgb(Integer.parseInt(red5), Integer.parseInt(green5), Integer.parseInt(blue5)));
        } else if (background5 instanceof GradientDrawable) {
            ((GradientDrawable)background5).setColor(Color.rgb(Integer.parseInt(red5), Integer.parseInt(green5), Integer.parseInt(blue5)));
        } else if (background5 instanceof ColorDrawable) {
            ((ColorDrawable)background5).setColor(Color.rgb(Integer.parseInt(red5), Integer.parseInt(green5), Integer.parseInt(blue5)));
        }



        TextView redDisplay5 = (TextView) findViewById(R.id.red5);
        redDisplay5.setText(red5);

        TextView greenDisplay5 = (TextView) findViewById(R.id.green5);
        greenDisplay5.setText(green5);

        TextView blueDisplay5 = (TextView) findViewById(R.id.blue5);
        blueDisplay5.setText(blue5);



    }

    public void anotherTest(View view) {
        Intent intent = new Intent(ResultsActivity.this, ImageCapture.class);
        startActivity(intent);
    }
}
