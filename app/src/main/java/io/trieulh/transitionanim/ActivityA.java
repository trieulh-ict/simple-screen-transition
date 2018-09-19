package io.trieulh.transitionanim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.trieulh.transitionanim.fragments.FragmentA;

public class ActivityA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        getSupportFragmentManager().beginTransaction().add(R.id.layout_container, new FragmentA()).commit();
    }
}
