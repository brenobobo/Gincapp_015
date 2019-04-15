package com.example.gincapp_015;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.gincapp_015.Fragments.ConvidadoFragment;
import com.example.gincapp_015.Fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout = findViewById(R.id.tabs);


        viewPager = findViewById(R.id.pager);
        // adicionando os fragmentos

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new MainFragment(), "Gincanas");
        adapter.AddFragment(new ConvidadoFragment(), "Convidados");
        //adpter setup

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);







    }
}
