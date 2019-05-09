package com.example.gincapp_015.Activitys;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.gincapp_015.Fragments.ConvidadoFragment;
import com.example.gincapp_015.Fragments.MainFragment;
import com.example.gincapp_015.Adapter.ViewPagerAdapter;
import com.example.gincapp_015.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.include_toolbar);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_sair:
                deslogarUsuario();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void deslogarUsuario(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
