package com.horizontech.biz.digitalquran.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.horizontech.biz.digitalquran.Database.DbBackend;
import com.horizontech.biz.digitalquran.Fragments.BookmarkFragment;
import com.horizontech.biz.digitalquran.R;

import java.util.List;

public class BookmarkAdapter extends ArrayAdapter<String> {
    public Context context;
    private String[] serial_no_array;
    private String[] bookmark_arabic_array;
    private String[] bookmark_english_array;
    private String[] bookmark_verse_array;
    private String[] bookmark_date_array;
    private DbBackend db;
    private BookmarkAdapter adapter;
    private List<String> items;
    private Typeface tf;

    public BookmarkAdapter(Context context, String[] serial_no, String[] bookamrk_arabic, String[] bookamrk_english, List<String> items, String[] bookamrk_date) {
        super(context, R.layout.custom_bookmark,R.id.bookmark_arabic,items);
        this.context=context;
        this.serial_no_array=serial_no;
        this.bookmark_arabic_array =bookamrk_arabic;
        this.bookmark_english_array =bookamrk_english;
        //this.bookmark_verse_array =bookamrk_verse;
        this.bookmark_date_array =bookamrk_date;
        this.items=items;
        this.adapter=this;
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/pdms.ttf");
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.custom_bookmark,parent,false);
        View v = convertView;
        if (v == null) {
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.custom_bookmark, null);
        }
        db=new DbBackend(getContext());
        TextView bookmark_serial= (TextView) row.findViewById(R.id.bookmark_serial);
        TextView bookmark_arabic= (TextView) row.findViewById(R.id.bookmark_arabic);
        TextView bookmark_english= (TextView) row.findViewById(R.id.bookmark_english);
        //TextView bookmark_verse= (TextView) row.findViewById(R.id.bookmark_aya);
        TextView bookmark_date= (TextView) row.findViewById(R.id.bookmark_date);
        ImageView bookmark_image= (ImageView) row.findViewById(R.id.bookmark_remove);
        bookmark_serial.setText(serial_no_array[position]);
        bookmark_arabic.setText(bookmark_arabic_array[position]);
        bookmark_english.setText(bookmark_english_array[position]);
        //String verse;
        String bookmark;
        //verse = "Verse Number : ";
        bookmark = "Bookmarked ";
        //bookmark_verse.setText(String.format("%s%s", verse, bookmark_verse_array[position]));
        bookmark_date.setText(String.format("%s%s", bookmark, bookmark_date_array[position]));
        bookmark_image.setImageResource(R.drawable.bookmarkremove);
        bookmark_image.setTag(position);
        bookmark_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position= (int) v.getTag();
                db.setBookmark_index(position);
                db.deleteBookmarkPara(db.getBookmark_index());
                items.remove(position);
                adapter.notifyDataSetChanged();

                Toast.makeText(context, "Bookmark Removed", Toast.LENGTH_SHORT).show();
            }
        });
        bookmark_arabic.setTypeface(tf);
        return row;
    }
}
