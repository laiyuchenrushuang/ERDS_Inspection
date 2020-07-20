package com.tmri.size.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by jack.yan on 2017/10/30.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void client(View view){
        startActivity(new Intent(this, ClientActivity.class));
    }

    public void service(View view){
        startActivity(new Intent(this,ServiceActivity.class));
    }
}
