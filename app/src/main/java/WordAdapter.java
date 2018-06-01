package com.example.android.miwok;

import android.app.Activity;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.miwok.R;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<com.example.android.miwok.Word> {

    private int mColorResourceId;

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context The current context. Used to inflate the layout file.
     * @param words A List of Word objects to display in a list
     * @param colorResourceID resource ID of the color to set the background of the words to
     */
    public WordAdapter(Activity context, ArrayList<com.example.android.miwok.Word> words, int colorResourceID) {
        super(context, 0, words);
        mColorResourceId = colorResourceID;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        com.example.android.miwok.Word currentWord = getItem(position);

        TextView defaultWordTextView = (TextView) listItemView.findViewById(R.id.default_word);
        defaultWordTextView.setText(currentWord.getDefaultTranslation());
        defaultWordTextView.setBackgroundResource(mColorResourceId);

        TextView miwokWordTextView = (TextView) listItemView.findViewById(R.id.miwok_word);
        miwokWordTextView.setText(currentWord.getMiwokTranslation());
        miwokWordTextView.setBackgroundResource(mColorResourceId);

        ImageView wordImageView = (ImageView) listItemView.findViewById(R.id.word_image);
        if (currentWord.hasImage()) {
            wordImageView.setImageResource(currentWord.getWordImageResource());
        }
        else {
            wordImageView.setVisibility(View.GONE);
        }

        return listItemView;
    }

}
