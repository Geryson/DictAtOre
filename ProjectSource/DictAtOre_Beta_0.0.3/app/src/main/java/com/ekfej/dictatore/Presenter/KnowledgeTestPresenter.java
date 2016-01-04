package com.ekfej.dictatore.Presenter;

import android.content.Context;

import java.util.List;
import java.util.Random;


/**
 * Created by Lloyd on 2015. 12. 11..
 */
public class KnowledgeTestPresenter extends MainPresenter {

    private Language language1;

    private Language language2;

    private Word actualWord;

    private KnowledgeTestHelpPresenter helper;

    private Random rnd = new Random();

    private Word[] words;
    private int next;
    public String GetNextWord() {
        if (next < GetWordsCount()) {
            helper = null;
            actualWord = words[next++];
            return actualWord.getWord();
        }
        else return null;
    }

    public int GetWordsCount() { return words.length; }

    public List<Integer> GetMatchingPositions() {
        if (helper == null) return null;
        return helper.GetMatchingPositions();
    }


    public KnowledgeTestPresenter(Context context, String language1, String language2, int numberOfWords) {
        super(context);
        this.language1 = new Language(language1);
        this.language2 = new Language(language2);
        GetRandomWords(numberOfWords, this.language1, this.language2);
    }



    private void GetRandomWords(int number, Language language1, Language language2) {
        String[] wordsNames = db.knowledgeTest.Expression(language1.getName(), language2.getName(), number);

        words = stringWordArray2WordWordArray(wordsNames.length, wordsNames, language1, language2);
    }

    public boolean WordCheck(String userInput) {
        if (helper != null) helper.CharCheck(userInput);

        for (Word word : actualWord.getMeaning()) {
            if (word.getWord().equals(userInput)) return true;
        }
        return false;
    }

    public String Help() {
        if (helper == null) helper = new KnowledgeTestHelpPresenter(actualWord.getMeaning().get(rnd.nextInt(actualWord.getMeaning().size())).getWord());
        return helper.Help();
    }


    public int sumpointmax() {
        int sum = 0;
        for (int i=0; i < words.length; i++ )
        {
            int max = 0;
            for (int j=0; j < words[i].getMeaning().size(); j++)
            {
                int max2 = words[i].getMeaning().get(j).getWord().length();
                if (max < max2)  { max = max2; }
            }
           sum += max;
        }
        return sum + words.length;
    }

}
