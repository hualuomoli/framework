package com.github.hualuomoli.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(MainActivity.this, com.github.hualuomoli.demo.MainActivity.class);
        startActivity(intent);
        // end
    }

}
