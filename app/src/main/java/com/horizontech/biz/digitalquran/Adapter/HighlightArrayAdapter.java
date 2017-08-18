package com.horizontech.biz.digitalquran.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.horizontech.biz.digitalquran.R;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class HighlightArrayAdapter extends ArrayAdapter<String> {
    private LayoutInflater mInflater;
    private Context mContext;
    private int mResource;
    private List<String> mObjects;
    private int mFieldId = 0;
    private String mSearchkeyword;


    public HighlightArrayAdapter(Context context, int resource, int textViewResourceId, String[] objects,String keyword) {
        super(context, resource, textViewResourceId, objects);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        mObjects = Arrays.asList(objects);
        mFieldId = textViewResourceId;
        mSearchkeyword=keyword;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        TextView text;

        if (convertView == null) {
            view = mInflater.inflate(mResource, parent, false);
        } else {
            view = convertView;
        }

        try {
            if (mFieldId == 0) {
                //  If no custom field is assigned, assume the whole resource is a TextView
                text = (TextView) view;
            } else {
                //  Otherwise, find the TextView field within the layout
                text = (TextView) view.findViewById(mFieldId);
            }
        } catch (ClassCastException e) {
            Log.e("ArrayAdapter", "You must supply a resource ID for a TextView");
            throw new IllegalStateException(
                    "ArrayAdapter requires the resource ID to be a TextView", e);
        }
        String item = getItem(position);
        text.setText(item);

        // highlight search text
        String fullText = getItem(position);
        if (mSearchkeyword != null && !mSearchkeyword.isEmpty()) {
            int startPos = fullText.toLowerCase(Locale.US).indexOf(mSearchkeyword.toLowerCase(Locale.US));
            int endPos = startPos + mSearchkeyword.length();

            if (startPos != -1) {
                Spannable spannable = new SpannableString(fullText);
                ColorStateList blueColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{mContext.getResources().getColor(R.color.colorPrimary)});
                TextAppearanceSpan highlightSpan = new TextAppearanceSpan(null, Typeface.BOLD, -1, blueColor, null);
                spannable.setSpan(highlightSpan, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                text.setText(spannable);
            } else {
                text.setText(fullText);
            }
        } else {
            text.setText(fullText);
        }

        return view;
    }
}
