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

        String red6 = intentExtras.getStringExtra("Red6");
        String green6 = intentExtras.getStringExtra("Green6");
        String blue6 = intentExtras.getStringExtra("Blue6");
        String colorScore6 = intentExtras.getStringExtra("ColorScore6");
        String x6 = intentExtras.getStringExtra("XCoordinate6");
        String y6 = intentExtras.getStringExtra("YCoordinate6");
        String radius6 = intentExtras.getStringExtra("Radius6");

        //line fitting
        double xcoordinates[] = { 0.5, 1, 2, 5, 10 };
        double redplot[] = {Double.parseDouble(red1), Double.parseDouble(red2), Double.parseDouble(red3),Double.parseDouble(red4), Double.parseDouble(red5)} ;
        double greenplot[] = {Double.parseDouble(green1), Double.parseDouble(green2), Double.parseDouble(green3),Double.parseDouble(green4), Double.parseDouble(green5)};
        double blueplot[] = {Double.parseDouble(blue1), Double.parseDouble(blue2), Double.parseDouble(blue3),Double.parseDouble(blue4), Double.parseDouble(blue5)};

        int i, j;
        float m, c, sum_x = 0, sum_y = 0, sum_xy = 0, sum_x2 = 0;
        int n = xcoordinates.length;
        for (i = 0; i < n; i++) {
            sum_x += xcoordinates[i];
            sum_y += redplot[i];
            sum_xy += xcoordinates[i] * redplot[i];
            sum_x2 += (xcoordinates[i] * xcoordinates[i]);
        }

        float redslope = (n * sum_xy - sum_x * sum_y) / (n * sum_x2 - (sum_x * sum_x));
        float redint = (sum_y - redslope * sum_x) / n;

        int i2, j2;
        float m2, c2, sum_x4 = 0, sum_y2 = 0, sum_xy2 = 0, sum_x22 = 0;
        int n2 = xcoordinates.length;
        for (i2 = 0; i2 < n2; i2++) {
            sum_x4 += xcoordinates[i2];
            sum_y2 += greenplot[i2];
            sum_xy2 += xcoordinates[i2] * greenplot[i2];
            sum_x22 += (xcoordinates[i2] * xcoordinates[i2]);
        }

        float greenslope = (n2 * sum_xy2 - sum_x4 * sum_y2) / (n2 * sum_x22 - (sum_x4 * sum_x4));
        float greenint = (sum_y2 - greenslope * sum_x4) / n2;

        int i3, j3;
        float m3, c3, sum_x6 = 0, sum_y3 = 0, sum_xy3 = 0, sum_x23 = 0;
        int n3 = xcoordinates.length;
        for (i3 = 0; i3 < n3; i3++) {
            sum_x6 += xcoordinates[i3];
            sum_y3 += blueplot[i3];
            sum_xy3 += xcoordinates[i3] * blueplot[i3];
            sum_x23 += (xcoordinates[i3] * xcoordinates[i3]);
        }

        float blueslope = (n3 * sum_xy3 - sum_x6 * sum_y3) / (n3 * sum_x23 - (sum_x6 * sum_x6));
        float blueint = (sum_y3 - greenslope * sum_x6) / n3;

        double redvalue = Double.parseDouble(red6);
        double greenvalue = Double.parseDouble(green6);
        double bluevalue = Double.parseDouble(blue6);

        double redparvalue = redvalue-redslope;
        double greenparvalue = greenvalue-greenslope;
        double blueparvalue = bluevalue-blueslope;

        double finalredval = redparvalue/redint;
        double finalgreenval = greenparvalue/greenint;
        double finalblueval = blueparvalue/blueint;

        double finalsum = finalredval+finalgreenval+finalblueval;

        double experimentalcolorscore =  finalsum/3;



        View colorRectangle6 = (View)findViewById(R.id.finalcolor6);
        Drawable background6 = colorRectangle6.getBackground();
        if (background6 instanceof ShapeDrawable) {
            ((ShapeDrawable)background6).getPaint().setColor(Color.rgb(Integer.parseInt(red6), Integer.parseInt(green6), Integer.parseInt(blue6)));
        } else if (background6 instanceof GradientDrawable) {
            ((GradientDrawable)background6).setColor(Color.rgb(Integer.parseInt(red6), Integer.parseInt(green6), Integer.parseInt(blue6)));
        } else if (background6 instanceof ColorDrawable) {
            ((ColorDrawable)background6).setColor(Color.rgb(Integer.parseInt(red6), Integer.parseInt(green6), Integer.parseInt(blue6)));
        }

        double doubleColorScore6 = experimentalcolorscore;
        String colorScore6a = String.format("%.3f", doubleColorScore6);
        TextView colorScoreDisplay6 = (TextView) findViewById(R.id.colorScore6);
        colorScoreDisplay6.setText(colorScore6a);

        TextView coordinatesDisplay6 = (TextView) findViewById(R.id.coordinates6);
        coordinatesDisplay6.setText("(X, Y): (" + x6 +", " + y6 +")");

        TextView radiusDisplay6 = (TextView) findViewById(R.id.radius6);
        radiusDisplay6.setText("Radius: " + radius6 + " pixels");

        TextView redDisplay6 = (TextView) findViewById(R.id.redvalue6);
        redDisplay6.setText("Red: " + red6);

        TextView greenDisplay6 = (TextView) findViewById(R.id.greenvalue6);
        greenDisplay6.setText("Green: " + green6);

        TextView blueDisplay6 = (TextView) findViewById(R.id.bluevalue6);
        blueDisplay6.setText("Blue: " + blue6);

        TextView levelDisplay6 = (TextView) findViewById(R.id.level6);

        if (doubleColorScore6 < 1.5) {
            levelDisplay6.setText("Level: Low");
        } else if (doubleColorScore6 < 3) {
            levelDisplay6.setText("Level: Borderline");
        } else {
            levelDisplay6.setText("Level: High");
        }





        View colorRectangle1 = (View)findViewById(R.id.finalcolor);
        Drawable background1 = colorRectangle1.getBackground();
        if (background1 instanceof ShapeDrawable) {
            ((ShapeDrawable)background1).getPaint().setColor(Color.rgb(Integer.parseInt(red1), Integer.parseInt(green1), Integer.parseInt(blue1)));
        } else if (background1 instanceof GradientDrawable) {
            ((GradientDrawable)background1).setColor(Color.rgb(Integer.parseInt(red1), Integer.parseInt(green1), Integer.parseInt(blue1)));
        } else if (background1 instanceof ColorDrawable) {
            ((ColorDrawable)background1).setColor(Color.rgb(Integer.parseInt(red1), Integer.parseInt(green1), Integer.parseInt(blue1)));
        }


        TextView colorScoreDisplay1 = (TextView) findViewById(R.id.colorScore);
        colorScoreDisplay1.setText(colorScore1);

        TextView coordinatesDisplay1 = (TextView) findViewById(R.id.coordinates);
        coordinatesDisplay1.setText("(X, Y): (" + x1 +", " + y1 +")");

        TextView radiusDisplay1 = (TextView) findViewById(R.id.radius);
        radiusDisplay1.setText("Radius: " + radius1 + " pixels");

        TextView redDisplay1 = (TextView) findViewById(R.id.redvalue);
        redDisplay1.setText("Red: " + red1);

        TextView greenDisplay1 = (TextView) findViewById(R.id.greenvalue);
        greenDisplay1.setText("Green: " + green1);

        TextView blueDisplay1 = (TextView) findViewById(R.id.bluevalue);
        blueDisplay1.setText("Blue: " + blue1);

        TextView levelDisplay1 = (TextView) findViewById(R.id.level);
        levelDisplay1.setText("Level: Borderline");







        View colorRectangle2 = (View)findViewById(R.id.finalcolor2);
        Drawable background2 = colorRectangle2.getBackground();
        if (background2 instanceof ShapeDrawable) {
            ((ShapeDrawable)background2).getPaint().setColor(Color.rgb(Integer.parseInt(red2), Integer.parseInt(green2), Integer.parseInt(blue2)));
        } else if (background2 instanceof GradientDrawable) {
            ((GradientDrawable)background2).setColor(Color.rgb(Integer.parseInt(red2), Integer.parseInt(green2), Integer.parseInt(blue2)));
        } else if (background2 instanceof ColorDrawable) {
            ((ColorDrawable)background2).setColor(Color.rgb(Integer.parseInt(red2), Integer.parseInt(green2), Integer.parseInt(blue2)));
        }


        TextView colorScoreDisplay2 = (TextView) findViewById(R.id.colorScore2);
        colorScoreDisplay2.setText(colorScore2);

        TextView coordinatesDisplay2 = (TextView) findViewById(R.id.coordinates2);
        coordinatesDisplay2.setText("(X, Y): (" + x2 +", " + y2 +")");

        TextView radiusDisplay2 = (TextView) findViewById(R.id.radius2);
        radiusDisplay2.setText("Radius: " + radius2 + " pixels");

        TextView redDisplay2 = (TextView) findViewById(R.id.redvalue2);
        redDisplay2.setText("Red: " + red2);

        TextView greenDisplay2 = (TextView) findViewById(R.id.greenvalue2);
        greenDisplay2.setText("Green: " + green2);

        TextView blueDisplay2 = (TextView) findViewById(R.id.bluevalue2);
        blueDisplay2.setText("Blue: " + blue2);

        TextView levelDisplay2 = (TextView) findViewById(R.id.level2);
        levelDisplay2.setText("Level: Borderline");






        View colorRectangle3 = (View)findViewById(R.id.finalcolor3);
        Drawable background3 = colorRectangle3.getBackground();
        if (background3 instanceof ShapeDrawable) {
            ((ShapeDrawable)background3).getPaint().setColor(Color.rgb(Integer.parseInt(red3), Integer.parseInt(green3), Integer.parseInt(blue3)));
        } else if (background3 instanceof GradientDrawable) {
            ((GradientDrawable)background3).setColor(Color.rgb(Integer.parseInt(red3), Integer.parseInt(green3), Integer.parseInt(blue3)));
        } else if (background3 instanceof ColorDrawable) {
            ((ColorDrawable)background3).setColor(Color.rgb(Integer.parseInt(red3), Integer.parseInt(green3), Integer.parseInt(blue3)));
        }


        TextView colorScoreDisplay3 = (TextView) findViewById(R.id.colorScore3);
        colorScoreDisplay3.setText(colorScore3);

        TextView coordinatesDisplay3 = (TextView) findViewById(R.id.coordinates3);
        coordinatesDisplay3.setText("(X, Y): (" + x3 +", " + y3 +")");

        TextView radiusDisplay3 = (TextView) findViewById(R.id.radius3);
        radiusDisplay3.setText("Radius: " + radius3 + " pixels");

        TextView redDisplay3 = (TextView) findViewById(R.id.redvalue3);
        redDisplay3.setText("Red: " + red3);

        TextView greenDisplay3 = (TextView) findViewById(R.id.greenvalue3);
        greenDisplay3.setText("Green: " + green3);

        TextView blueDisplay3 = (TextView) findViewById(R.id.bluevalue3);
        blueDisplay3.setText("Blue: " + blue3);

        TextView levelDisplay3 = (TextView) findViewById(R.id.level3);
        levelDisplay3.setText("Level: Borderline");





        View colorRectangle4 = (View)findViewById(R.id.finalcolor4);
        Drawable background4 = colorRectangle4.getBackground();
        if (background4 instanceof ShapeDrawable) {
            ((ShapeDrawable)background4).getPaint().setColor(Color.rgb(Integer.parseInt(red4), Integer.parseInt(green4), Integer.parseInt(blue4)));
        } else if (background4 instanceof GradientDrawable) {
            ((GradientDrawable)background4).setColor(Color.rgb(Integer.parseInt(red4), Integer.parseInt(green4), Integer.parseInt(blue4)));
        } else if (background4 instanceof ColorDrawable) {
            ((ColorDrawable)background4).setColor(Color.rgb(Integer.parseInt(red4), Integer.parseInt(green4), Integer.parseInt(blue4)));
        }


        TextView colorScoreDisplay4 = (TextView) findViewById(R.id.colorScore4);
        colorScoreDisplay4.setText(colorScore4);

        TextView coordinatesDisplay4 = (TextView) findViewById(R.id.coordinates4);
        coordinatesDisplay4.setText("(X, Y): (" + x4 +", " + y4 +")");

        TextView radiusDisplay4 = (TextView) findViewById(R.id.radius4);
        radiusDisplay4.setText("Radius: " + radius4 + " pixels");

        TextView redDisplay4 = (TextView) findViewById(R.id.redvalue4);
        redDisplay4.setText("Red: " + red4);

        TextView greenDisplay4 = (TextView) findViewById(R.id.greenvalue4);
        greenDisplay4.setText("Green: " + green4);

        TextView blueDisplay4 = (TextView) findViewById(R.id.bluevalue4);
        blueDisplay4.setText("Blue: " + blue4);

        TextView levelDisplay4 = (TextView) findViewById(R.id.level4);
        levelDisplay4.setText("Level: Borderline");




        View colorRectangle5 = (View)findViewById(R.id.finalcolor5);
        Drawable background5 = colorRectangle5.getBackground();
        if (background5 instanceof ShapeDrawable) {
            ((ShapeDrawable)background5).getPaint().setColor(Color.rgb(Integer.parseInt(red5), Integer.parseInt(green5), Integer.parseInt(blue5)));
        } else if (background5 instanceof GradientDrawable) {
            ((GradientDrawable)background5).setColor(Color.rgb(Integer.parseInt(red5), Integer.parseInt(green5), Integer.parseInt(blue5)));
        } else if (background5 instanceof ColorDrawable) {
            ((ColorDrawable)background5).setColor(Color.rgb(Integer.parseInt(red5), Integer.parseInt(green5), Integer.parseInt(blue5)));
        }


        TextView colorScoreDisplay5 = (TextView) findViewById(R.id.colorScore5);
        colorScoreDisplay5.setText(colorScore5);

        TextView coordinatesDisplay5 = (TextView) findViewById(R.id.coordinates5);
        coordinatesDisplay5.setText("(X, Y): (" + x5 +", " + y5 +")");

        TextView radiusDisplay5 = (TextView) findViewById(R.id.radius5);
        radiusDisplay5.setText("Radius: " + radius5 + " pixels");

        TextView redDisplay5 = (TextView) findViewById(R.id.redvalue5);
        redDisplay5.setText("Red: " + red5);

        TextView greenDisplay5 = (TextView) findViewById(R.id.greenvalue5);
        greenDisplay5.setText("Green: " + green5);

        TextView blueDisplay5 = (TextView) findViewById(R.id.bluevalue5);
        blueDisplay5.setText("Blue: " + blue5);

        TextView levelDisplay5 = (TextView) findViewById(R.id.level5);
        levelDisplay5.setText("Level: Borderline");





    }
}
