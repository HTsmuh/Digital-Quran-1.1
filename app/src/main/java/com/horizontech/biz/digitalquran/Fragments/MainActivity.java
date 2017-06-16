package com.horizontech.biz.digitalquran.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.horizontech.biz.digitalquran.Adapter.PagerAdapter;
import com.horizontech.biz.digitalquran.Database.DbBackend;
import com.horizontech.biz.digitalquran.Menu.AboutUsActivity;
import com.horizontech.biz.digitalquran.Menu.CreditsActivity;
import com.horizontech.biz.digitalquran.R;
import com.horizontech.biz.digitalquran.Menu.SettingActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {
    boolean haveConnectedWifi = false;
    boolean haveConnectedMobile = false;
    String latestVersion;
    String network;
    Date currentDate;
    Date intervalDate;
    ViewPager viewPager;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        network= String.valueOf(haveNetworkConnection());
        DbBackend db=new DbBackend(MainActivity.this);
        if (db.getCheckUpdate().equals("never")){
            network="false";
        }else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd");
            String current = sdf.format(new Date());
            try {
                currentDate = sdf.parse(current);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                intervalDate = sdf.parse(db.getEnddate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(current));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (db.getCheckUpdate().equals("never")) {
                c.add(Calendar.DATE, 0);
            } else {
                c.add(Calendar.DATE, Integer.parseInt(db.getCheckUpdate()));
            }
            sdf = new SimpleDateFormat("yyyy-MMM-dd");
            Date resultdate = new Date(c.getTimeInMillis());
            String endDate = sdf.format(resultdate);
            if (currentDate.equals(intervalDate)) {
                db.setStartdate(current);
                db.setEnddate(endDate);
                Check_Update();
            } else if (intervalDate.before(currentDate)) {
                db.setStartdate(current);
                db.setEnddate(endDate);
                Check_Update();
            }
        }
        //Toast.makeText(this, ""+db.getEnddate()+currentDate, Toast.LENGTH_SHORT).show();
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("SURAH"));
        tabLayout.addTab(tabLayout.newTab().setText("PARA"));
        tabLayout.addTab(tabLayout.newTab().setText("BOOKMARK"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
    public void select3rdTab(){
        viewPager.setCurrentItem(2);
    }
    private boolean haveNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }private String getCurrentVersion(){
        PackageManager pm = this.getPackageManager();
        PackageInfo pInfo = null;

        try {
            pInfo =  pm.getPackageInfo(this.getPackageName(),0);

        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }

        return pInfo.versionName;
    }
    public void Check_Update() {
        if (network.equals("true")) {
            //getCurrentVersion();
            String currentVersion = getCurrentVersion();
            Log.d("LOG", "Current version = " + currentVersion);
            try {
                latestVersion = new GetLatestVersion().execute().get();
                Log.d("LOG", "Latest version = " + latestVersion);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            //Toast.makeText(this, ""+network+"  "+latestVersion+"  "+currentVersion, Toast.LENGTH_SHORT).show();
            //If the versions are not the same

            if (!currentVersion.equals(latestVersion)) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("An Update is Available");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Click button action
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Cancel button action
                    }
                });

                builder.setCancelable(false);
                builder.show();
            }
        }
    }
    private class GetLatestVersion extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                //It retrieves the latest version by scraping the content of current version from play store at runtime
                String urlOfAppFromPlayStore = "https://play.google.com/store/apps/details?id=" + getPackageName();
                Document doc = Jsoup.connect(urlOfAppFromPlayStore).get();
                latestVersion = doc.getElementsByAttributeValue("itemprop","softwareVersion").first().text();

            }catch (Exception e){
                e.printStackTrace();
            }
            return latestVersion;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
       // mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_credit) {
            Intent intent = new Intent(this, CreditsActivity.class);
            startActivity(intent);
        }else  if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        }else  if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutUsActivity.class);
            startActivity(intent);
        }/*else  if (id == R.id.action_search) {
            handleMenuSearch();
        }*/
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if (network.equals("true")) {
            showCloseNow();
        }else{
            showCloseNow();
        }
    }
    public void showRateNow(){
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.logo)
                .setTitle("Please Rate us")
                .setMessage("We need your help to rate this app!")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse("market://details?id=" + getPackageName()));
                        startActivity(i);
                    }

                })
                .setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .show();
    }
    public void showCloseNow(){
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.alert)
                .setTitle("Closing Application")
                .setMessage("Are you sure you want to close this Application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }


    protected void handleMenuSearch(){
        ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_open_search));

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText)action.getCustomView().findViewById(R.id.edtSearch); //the text editor

            //this is a listener to do a search when the user clicks on search button
            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        doSearch();
                        return true;
                    }
                    return false;
                }
            });


            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_close_search));

            isSearchOpened = true;
        }
    }

    private void doSearch() {
        //
    }
}