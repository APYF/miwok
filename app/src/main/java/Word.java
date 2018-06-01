package com.example.android.miwok;

/**
 * {@link Word}  represents a vocabulary word that the user wants to learn.
 * It contains a default translation and a Miwok translation for that word.
 *
 */

public class Word {
    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mWordImageResource = NO_IMAGE_PROVIDED;
    private int mWordSoundResource= NO_SOUND_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;
    private static final int NO_SOUND_PROVIDED = -1;


    /**
     *  Create a new  Word object.
     * @param defaultWord is the word in a language that the user is already familiar with
     * @param miwokWord is the word in the Miwok Language
     * @param wordSoundResource is the resource ID for the sound for this word
     *
     */
    public Word(String defaultWord, String miwokWord, int wordSoundResource) {
        mDefaultTranslation = defaultWord;
        mMiwokTranslation = miwokWord;
        mWordSoundResource = wordSoundResource;
    }

    /**
     * Create a new Word object
     * @param defaultWord is the word in a language that the user is already familiar with
     * @param miwokWord is the word in the Miwok Language
     * @param wordImageResource is the resource ID for the image for this word
     * @param wordSoundResource is the resource ID for the sound for this word
     */
    public Word(String defaultWord, String miwokWord, int wordImageResource, int wordSoundResource) {
        mDefaultTranslation = defaultWord;
        mMiwokTranslation = miwokWord;
        mWordImageResource = wordImageResource;
        mWordSoundResource = wordSoundResource;
    }
    /**
     *  gets the default word
     * @return
     */

    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getWordImageResource() {
        return mWordImageResource;
    }

    public int getWordSoundResource() { return mWordSoundResource; }

    /**
     * Returns whether or not there is image associated with word or not
     * @return true if there is an iamge
     */
    public boolean hasImage() {
     return mWordImageResource != NO_IMAGE_PROVIDED;
    }

    public boolean hasSound() { return mWordSoundResource != NO_SOUND_PROVIDED; }
}
