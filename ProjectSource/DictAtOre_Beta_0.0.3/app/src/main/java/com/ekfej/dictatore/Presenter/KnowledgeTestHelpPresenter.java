package com.ekfej.dictatore.Presenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Lloyd on 2015. 12. 12..
 */
public class KnowledgeTestHelpPresenter {

    private String helpWord;

    private String help;
    public String GetHelp(){ return help; }

    private List<Integer> rndIndexes;

    private List<Integer> matchingPositions;
    public List<Integer> GetMatchingPositions() { return matchingPositions; };



    public KnowledgeTestHelpPresenter(String helpWord) {
        this.helpWord = helpWord;
    }



    private void RndIndexesFill(int numberOfIndexes) {
        rndIndexes = new ArrayList<>();
        for (int i = 0; i < numberOfIndexes; i++)
        {
            rndIndexes.add(i);
        }
        Collections.shuffle(rndIndexes);
    }

    public void CharCheck(String userInput){
        char[] helpArray = help.toCharArray();
        char[] userInputArray = userInput.toCharArray();
        matchingPositions = new ArrayList<Integer>();

        int checkLength = helpArray.length;
        if (checkLength > userInputArray.length) checkLength = userInputArray.length;

        for (int i = 0; i < checkLength; i++) {
            if (helpArray[i] == userInputArray[i]) {
                matchingPositions.add(i);
            }
        }
    }

    public static String ReplaceCharAt(String s, int pos, char c) {
        StringBuffer buf = new StringBuffer(s);
        buf.setCharAt(pos, c);
        return buf.toString();
    }

    public String Help() {
        try {
            int rndIndex = rndIndexes.remove(0);
            help = ReplaceCharAt(help, rndIndex, helpWord.charAt(rndIndex));
        }
        catch (NullPointerException e){
            help = helpWord.replaceAll(".", "_");
            RndIndexesFill(this.helpWord.length());
        }
        finally {
            return help;
        }
    }
}
