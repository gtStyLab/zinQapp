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



        View colorRectangle = (View)findViewById(R.id.finalcolor);
        Drawable background = colorRectangle.getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable)background).getPaint().setColor(Color.rgb(Integer.parseInt(red6), Integer.parseInt(green6), Integer.parseInt(blue6)));
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable)background).setColor(Color.rgb(Integer.parseInt(red6), Integer.parseInt(green6), Integer.parseInt(blue6)));
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable)background).setColor(Color.rgb(Integer.parseInt(red6), Integer.parseInt(green6), Integer.parseInt(blue6)));
        }

        double doubleColorScore = experimentalcolorscore;
        String colorScore = String.format("%.3f", doubleColorScore);
        TextView colorScoreDisplay = (TextView) findViewById(R.id.colorScore);
        colorScoreDisplay.setText(colorScore);

        TextView coordinatesDisplay = (TextView) findViewById(R.id.coordinates);
        coordinatesDisplay.setText("(X, Y): (" + x6 +", " + y6 +")");

        TextView radiusDisplay = (TextView) findViewById(R.id.radius);
        radiusDisplay.setText("Radius: " + radius6 + " pixels");

        TextView redDisplay = (TextView) findViewById(R.id.redvalue);
        redDisplay.setText("Red: " + red6);

        TextView greenDisplay = (TextView) findViewById(R.id.greenvalue);
        greenDisplay.setText("Green: " + green6);

        TextView blueDisplay = (TextView) findViewById(R.id.bluevalue);
        blueDisplay.setText("Blue: " + blue6);

        TextView levelDisplay = (TextView) findViewById(R.id.level);

        if (doubleColorScore < 1.5) {
            levelDisplay.setText("Level: Low");
        } else if (doubleColorScore < 3) {
            levelDisplay.setText("Level: Borderline");
        } else {
            levelDisplay.setText("Level: High");
        }


    }
}
