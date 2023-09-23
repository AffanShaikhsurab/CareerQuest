package com.affanshaikhsurab.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.affanshaikhsurab.myapplication.R;
import com.affanshaikhsurab.myapplication.adapter.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Information extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
         String tabText[] = new String[]{"Information", "Courses"};
         setTitle(getIntent().getStringExtra("Label"));
        TabLayout tabLayout = findViewById(R.id.tabLayout_Information);
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        FragmentAdapter fragmentAdapter = new FragmentAdapter( getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(fragmentAdapter);
          new TabLayoutMediator(tabLayout, viewPager,
                  new TabLayoutMediator.TabConfigurationStrategy() {
                      @Override
                      public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                          tab.setText(tabText[position]);
                      }
                  }
          ).attach();

    }
}