package com.horizontech.biz.digitalquran;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.horizontech.biz.digitalquran.Adapter.SearchAdapter;
import com.horizontech.biz.digitalquran.Database.DbBackend;

public class SearchResult extends AppCompatActivity {

    ListView SearchList;
    SearchAdapter listAdapter;
    DbBackend dbBackend;
    String keyword;
    String[] text, ayaNo, surahNo,surahName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        keyword = bundle.getString("Search_Keyword");
        SearchList= (ListView) findViewById(R.id.Search_listView);

        dbBackend=new DbBackend(SearchResult.this);
        text = dbBackend.SearchText(keyword);
        ayaNo = dbBackend.SearchAyatNo(keyword);
        surahNo = dbBackend.SearchSurahNo(keyword);
        surahName=dbBackend.SearchSurahName(keyword);
        listAdapter = new SearchAdapter(SearchResult.this, text,surahName,surahNo,ayaNo,keyword);
        if (keyword.equals("") || keyword.equals(" ") || keyword.equals("  ")){
            SearchList.setAdapter(null);
        }else {
            SearchList.setAdapter(listAdapter);
        }
        SearchList.setEmptyView(findViewById(R.id.emptyElement));
        listAdapter.notifyDataSetChanged();

        SearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SearchResult.this, "Ayat Number: "+ ayaNo[i]+"Surah name: "+ surahNo[i], Toast.LENGTH_SHORT).show();
            }
        });
    }
}