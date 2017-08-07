package com.codesse.codetest.wordgame;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 * This is the shell implementation of the WordGame interface.
 * It is the class that you should focus on when developing your solution to the Challenge.
 */

/**
 * Explaining of my implementation.
 * I put my leaderboard in static LinkedList. For maintain leaderboard I`ve created one more method addWordToLeaderboard.
 * It returns true if word was added and false in other case. Also it use given method submitWord and check for max
 * quantity of words in leadership <=10.
 * Since java is an OOP language I've decided to make every row of leadership by object with fields: Player, Word, Score.
 * So I just add this row-object in my leaderboard checking all constrains given in task.
 * In method Main I did some simulation for this game, every task is execute.
 * Therefore I can easy put this implementation to Swing or web
 */

public class WordGameImpl implements WordGame {

    // I do not make  fields private with getters and setters just for decreasing quantity of code
    //LinkedList was chosen because it can easy insert data in middle
    static LinkedList<RowOfLeaderboard> leaderboard = new LinkedList<RowOfLeaderboard>();
    String startingWord;


    public WordGameImpl(String wordForGame, ValidWords validWords) {
        this.startingWord = wordForGame;

    }

    public WordGameImpl(String startingWord) {
        this.startingWord = startingWord;

    }

    //This class is needed for representing a row of leaderboard in object
    public class RowOfLeaderboard {
        String playerName;
        String word;
        int score;

        public RowOfLeaderboard(String playerName, String word, int score) {
            this.playerName = playerName;
            this.word = word;
            this.score = score;
        }
    }


    /*I have added this method for maintaining of leaderbord.
    * Method return true if word was added and false in other case
    * */
    public synchronized boolean addWordToLeaderboard(String playerName, String word) {

        //checking whether word is valid
        int score = submitWord(playerName, word);
        if (score == 0) {
            return false;
        }

        //checking whether leaderboard already contains this word
        for (RowOfLeaderboard row : leaderboard) {
            if (word.equals(row.word)) {
                return false;
            }
        }


        //creating a row of leaderboard
        RowOfLeaderboard rowOfLeaderboard = new RowOfLeaderboard(playerName, word, score);


        // checking whether word has score enough for leaderboard.
        // if yes - adding to appropriate position
        for (int i = 0; i < 10; i++) {
            if (i == leaderboard.size() || score > leaderboard.get(i).score) {    /*jvm do not check second condition if first is true, so Index out of bound Exception will never happened*/
                leaderboard.add(i, rowOfLeaderboard);
                // keeping only 10 positions in leaderboard
                if (leaderboard.size() > 10) {
                    leaderboard.remove(10);
                }
                return true;
            }
        }

        //if word was not added
        return false;
    }


    @Override
    public synchronized int submitWord(String playerName, String word) {

        //1)checking whether all letters in "word" are contained in "wordForGame"
        char[] availableLetters = startingWord.toCharArray();
        char[] proposedLetters = word.toCharArray();

        int count = 0;

        for (char x : proposedLetters)
            for (int i = 0; i < availableLetters.length; i++) {
                if (x == availableLetters[i]) {
                    count++;
                  /*task says that words do not contain whitespace.
                  So I am using it for "deleting" already used letters  */
                    availableLetters[i] = ' ';
                    break;
                }
            }

        if (count != word.length()) {
            return 0;
        }

        //2)checking whether "word" is valid
        if (!new ValidWordsImpl().contains(word)) {
            return 0;
        }

        return count;
    }


    @Override
    public String getPlayerNameAtPosition(int position) {
        return leaderboard.get(position).playerName;
    }

    @Override
    public String getWordEntryAtPosition(int position) {
        return leaderboard.get(position).word;
    }

    @Override
    public Integer getScoreAtPosition(int position) {
        return leaderboard.get(position).score;
    }


    /*In method main I did a simple simulation of a game. Player and his word are inputting together,
    separating by whitespace, as following example:
    Michael real
    Antony lord
    Michael woolly
    Mike world
    It is possible to put any quantity of words. For stop and showing leaderboard just press Enter
    */
    public static void main(String[] args) throws IOException {
        WordGameImpl wordGame = new WordGameImpl("areallylongword");
        System.out.println("Input playerName and word, separate by whitespace (for stop inputting just press Enter):");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String playerName_word = reader.readLine();
            String[] arr = playerName_word.split(" ");
            if (arr.length != 2) {
                break;
            }
            String playerName = arr[0];
            String word = arr[1];

            //showing whether word was added to leaderboard
            System.out.println("Word is added: " + wordGame.addWordToLeaderboard(playerName, word));
        }

        //showing leaderboard
        System.out.println("The leaderboard:");
        for (int i = 0; i < leaderboard.size(); i++) {
            System.out.println(i + 1 + ") " + leaderboard.get(i).playerName + " " + leaderboard.get(i).word + " " + leaderboard.get(i).score);
        }


    }


}
