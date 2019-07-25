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


public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intentExtras = getIntent();
        String red = intentExtras.getStringExtra("Red");
        String green = intentExtras.getStringExtra("Green");
        String blue = intentExtras.getStringExtra("Blue");
        String colorScore = intentExtras.getStringExtra("ColorScore");
        String x = intentExtras.getStringExtra("XCoordinate");
        String y = intentExtras.getStringExtra("YCoordinate");
        String radius = intentExtras.getStringExtra("Radius");

        System.out.println(red);
        System.out.println(green);
        System.out.println(blue);
        System.out.println(colorScore);
        System.out.println(x);
        System.out.println(y);
        System.out.println(radius);

        View colorRectangle = (View)findViewById(R.id.finalcolor);
        Drawable background = colorRectangle.getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable)background).getPaint().setColor(Color.rgb(Integer.parseInt(red), Integer.parseInt(green), Integer.parseInt(blue)));
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable)background).setColor(Color.rgb(Integer.parseInt(red), Integer.parseInt(green), Integer.parseInt(blue)));
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable)background).setColor(Color.rgb(Integer.parseInt(red), Integer.parseInt(green), Integer.parseInt(blue)));
        }

        double doubleColorScore = Double.parseDouble(colorScore);
        colorScore = String.format("%.3f", doubleColorScore);
        TextView colorScoreDisplay = (TextView) findViewById(R.id.colorScore);
        colorScoreDisplay.setText(colorScore);

        TextView coordinatesDisplay = (TextView) findViewById(R.id.coordinates);
        coordinatesDisplay.setText("(X, Y): (" + x +", " + y +")");

        TextView radiusDisplay = (TextView) findViewById(R.id.radius);
        radiusDisplay.setText("Radius: " + radius + " pixels");

        TextView redDisplay = (TextView) findViewById(R.id.redvalue);
        redDisplay.setText("Red: " + red);

        TextView greenDisplay = (TextView) findViewById(R.id.greenvalue);
        greenDisplay.setText("Green: " + green);

        TextView blueDisplay = (TextView) findViewById(R.id.bluevalue);
        blueDisplay.setText("Blue: " + blue);

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
