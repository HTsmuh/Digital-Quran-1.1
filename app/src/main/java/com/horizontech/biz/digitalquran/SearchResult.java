package com.horizontech.biz.digitalquran;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.horizontech.biz.digitalquran.Adapter.HighlightArrayAdapter;
import com.horizontech.biz.digitalquran.Database.DbBackend;
import com.horizontech.biz.digitalquran.Fragments.MainActivity;
import com.horizontech.biz.digitalquran.Menu.AboutUsActivity;
import com.horizontech.biz.digitalquran.Menu.CreditsActivity;
import com.horizontech.biz.digitalquran.Menu.SettingActivity;

public class SearchResult extends AppCompatActivity {

    MenuItem mSearchAction;
    boolean isSearchOpened = false;
    EditText edtSearch;
    ListView SearchList;
    HighlightArrayAdapter listAdapter;
    String srch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        showMenuSearch();

        SearchList= (ListView) findViewById(R.id.Search_listView);

        DbBackend dbBackend=new DbBackend(SearchResult.this);
        String[] terms = dbBackend.SurahTranslationSearch();
        listAdapter = new HighlightArrayAdapter(SearchResult.this,R.layout.custom_search_single_row,R.id.searchText, terms);
        SearchList.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

        edtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                /*//Log.d("Msg","Output : "+srch);
                String regexStr = "^[0-9]*$";
                if(edtSearch.getText().toString().trim().matches(regexStr))
                {   //write code here for success
                    srch="    ";
                    SearchList.setAdapter(null);
                    SearchList.setEmptyView(findViewById(R.id.emptyElement));
                }else{// write code for failure
                    srch=edtSearch.getText().toString();
                }*/
                SearchResult.this.listAdapter.getFilter().filter(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_close_search));
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
        }else  if (id == R.id.action_search) {
            handleMenuSearch();
        }
        return super.onOptionsItemSelected(item);
    }
    protected void showMenuSearch(){
        ActionBar action = getSupportActionBar(); //get the actionbar
        //open the search entry
        action.setDisplayShowCustomEnabled(true); //enable it to display a
        // custom view in the action bar.
        action.setCustomView(R.layout.search_bar);//add the custom view
        action.setDisplayShowTitleEnabled(false); //hide the title
        edtSearch = (EditText)action.getCustomView().findViewById(R.id.edtSearch); //the text editor
        }
    protected void handleMenuSearch(){
        if(!isSearchOpened){
            isSearchOpened = false;
            Intent intent=new Intent(SearchResult.this,MainActivity.class);
            startActivity(intent);
        } else {
            isSearchOpened = true;
        }
    }
}