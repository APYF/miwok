package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {

    private MediaPlayer mMediaPlayer;

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    /** Handles audio focus when playing a sound file */
    private AudioManager mAudioManager;

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // abandon audio focus when playback is complete
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    public NumbersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.words_list, container, false);

        /** TODO: Insert all the code from the NumberActivity’s onCreate() method after the setContentView method call */
        super.onCreate(savedInstanceState);

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<com.example.android.miwok.Word> words = new ArrayList<com.example.android.miwok.Word>();

        words.add(new com.example.android.miwok.Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new com.example.android.miwok.Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new com.example.android.miwok.Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new com.example.android.miwok.Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new com.example.android.miwok.Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new com.example.android.miwok.Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new com.example.android.miwok.Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new com.example.android.miwok.Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new com.example.android.miwok.Word("nine", "wo’e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new com.example.android.miwok.Word("ten", "na’aacha", R.drawable.number_ten, R.raw.number_ten));

/**
 * The following is the default usage of ArrayAdapter when words array is a list of String  .. simple layout using android pre defined simple_list_item_1
 *
 ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, words);

 ListView listView = (ListView) findViewById(R.id.list);

 listView.setAdapter(itemsAdapter);
 **/

        com.example.android.miwok.WordAdapter wordAdapter = new com.example.android.miwok.WordAdapter(getActivity(), words, R.color.category_numbers);

        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(wordAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Release the media if it current exists because we want to play a different
                // sound file
                releaseMediaPlayer();


                // Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now

                    // Create and setup the {@link MediaPlayer)} for the audio resource associated
                    // with the current word
                    mMediaPlayer = MediaPlayer.create(getActivity(), words.get(i).getWordSoundResource());
                    //sart the audio file
                    mMediaPlayer.start();
                    // Setup a listener on the media player, so we can stop and release the media player
                    // once the sounds has finished playing
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        return rootView;
    }
    @Override
    public void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }
}
