package io.trieulh.transitionanim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.trieulh.anim.ScreenAnim;

public class ActivityB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
    }

    @Override
    public void onBackPressed() {
        ScreenAnim.exitActivity(this, null);
    }
}
