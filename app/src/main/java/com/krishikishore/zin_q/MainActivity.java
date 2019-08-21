package com.krishikishore.zin_q;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void moveToCapture(View view) {
        Intent intent = new Intent(MainActivity.this, ImageCapture.class);
        startActivity(intent);
    }

    public void moveToInstructions(View view) {
        Intent intent = new Intent(MainActivity.this, InstructionsActivity.class);
        startActivity(intent);
    }

    public void moveToCredits(View view) {
        Intent intent = new Intent(MainActivity.this, CreditsActivity.class);
        startActivity(intent);
    }

}
