package fr.alainmuller.daydreamsample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import fr.alainmuller.daydreamsample.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnDayDream = (Button) findViewById(R.id.daydream_btn);
        btnDayDream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start system's DayDream
                final Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setClassName("com.android.systemui", "com.android.systemui.Somnambulator");
                startActivity(intent);
            }
        });
    }
}
