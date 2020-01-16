package edu.fje.sopadelletres;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import java.util.ArrayList;

public class LetterAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> wordList = new ArrayList<>();
    private String[] listapalabra;
    private static LetterSearch wordSearch;
    private static int row, col;

    //Contructor on es declaren les lletres que s'utilitzen
    public LetterAdapter(Context c) {
        wordList.add("HOLA");
        wordList.add("ADEU");
        context = c;
        listapalabra= wordList.toArray(new String[0]);
        wordSearch = new LetterSearch(listapalabra, 10);
    }

    @Override
    public int getCount() {
        return wordSearch.getSize()*wordSearch.getSize();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //Printa la sopa de lletres
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            textView = new TextView(context);
            textView.setLayoutParams(new GridView.LayoutParams(85, 85));
            textView.setPadding(8, 8, 8, 8);
        } else {
            textView = (TextView) convertView;
        }
        row = position/wordSearch.getSize();
        col = position%wordSearch.getSize();
        textView.setText(Character.toString(wordSearch.getWordSearch()[row][col]));
        return textView;
    }

    public static String getLetter(int position) {
        row = position / wordSearch.getSize();
        col = position % wordSearch.getSize();
        return Character.toString(wordSearch.getWordSearch()[row][col]);
    }

    public ArrayList<String> getWordList() {
        return wordList;
    }
}
