package edu.fje.sopadelletres;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



    //Metódo para mostrar y ocultar el menú.
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.barraacio, menu);
        return true;
    }

    //Metodo que assigna las funciones a las opciones.
    public boolean onOptionItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item1) {
            Toast.makeText(this, "Ajuda", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.item2) {
            Toast.makeText(this, "Opció 2", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.item3) {
            Toast.makeText(this, "Opció 3", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }



}
