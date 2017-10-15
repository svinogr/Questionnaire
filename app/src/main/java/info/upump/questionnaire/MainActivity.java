package info.upump.questionnaire;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.List;

import info.upump.questionnaire.db.DataBaseHelper;
import info.upump.questionnaire.entity.Question;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Fragment fragment;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        DataBaseHelper helper = DataBaseHelper.getHelper(this);
        helper.create_db();
        toggle.syncState();
        drawer.openDrawer(GravityCompat.START);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mailto) {
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@upump.info"});
            email.putExtra(Intent.EXTRA_SUBJECT, "Questionaire");
            email.putExtra(Intent.EXTRA_TEXT, "");
            //email.setType("message/rfc822");
            email.setType("plain/text");
            startActivity(Intent.createChooser(email, "Choose an Email client :"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String tag=null;
        switch (item.getItemId()){
            case R.id.nav_captain:
                fragment = getSupportFragmentManager().findFragmentByTag(CaptainFragment.TAG);
                tag = CaptainFragment.TAG;
                System.out.println(CaptainFragment.TAG);
                if(fragment==null) {
                    fragment = new CaptainFragment();
                }
                break;
            case R.id.nav_senior_assistant:
                fragment = getSupportFragmentManager().findFragmentByTag(SeniorAssistantFragment.TAG);
                tag = SeniorAssistantFragment.TAG;
                System.out.println(SeniorAssistantFragment.TAG);
                if(fragment==null) {
                    fragment = new SeniorAssistantFragment();
                }

                break;
            case R.id.nav_watch_mate_assistant:
                fragment = getSupportFragmentManager().findFragmentByTag(WatchMateFragment.TAG);
                tag = WatchMateFragment.TAG;
                System.out.println(WatchMateFragment.TAG);
                if(fragment==null) {
                    fragment = new WatchMateFragment();
                }

                break;
            case R.id.nav_question:
                fragment = getSupportFragmentManager().findFragmentByTag(QuestionFragment.TAG);
                tag = QuestionFragment.TAG;
                System.out.println(QuestionFragment.TAG);
                if(fragment==null) {
                    fragment = new QuestionFragment();
                }

                break;


        }
        item.setChecked(true);
        setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainContainer, fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        return true;
    }
}
