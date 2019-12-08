package edu.fje.sopadelletres;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import java.util.ArrayList;

public class LetterAdapter extends BaseAdapter {
    private Context context;
    //DBHelper dbHelper;
    FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
    SQLiteDatabase db = dbHelper.getReadableDatabase();
    private ArrayList<String> wordList = new ArrayList<>();
    private String[] listapalabra;//= wordList.toArray(new String[0]);
    private static LetterSearch wordSearch;
    private static int row, col;

    public LetterAdapter(Context c) {
        dbHelper = new DBHelper(c);
        db = dbHelper.getReadableDatabase();
        //Columnas
        String [] proyeccion = {
                FeedReaderContract.FeedEntry
        };
        //Respuesta
        Cursor cursor = db.query(
                DBHelper.entidadjuegosopaletras.nombretabla,
                proyeccion,
                null,
                null,
                null,
                null,
                null
        );
        Log.d(
                "tag",
                "Hace la busqueda" + cursor
        );
        // recoger los datos
        while (cursor.moveToNext()){
            Log.d("tag","Entra en el while");
            String palabra = cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            DBHelper.entidadjuegosopaletras.columnapalabra
                    )
            );
            Log.d("tag","palabra: "+palabra);
            wordList.add(palabra);
        }
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
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
}
