package edu.fje.sopadelletres;
//Primera activity con el boton jugar.

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.webkit.WebView;

import java.util.ArrayList;

public class SopaDeLetras extends AppCompatActivity {

protected WebView webView;

    private final String BBDD ="putamierda";
    private final String TAULA = "asco";

    private ListView lista;

    //Metode per mostrar y ocultar el menú.
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.barraacio, menu);
        return true;
    }

    //Metode que assigna les funcions a les opcions.
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item1) {
            Intent ajuda = new Intent(this, WebViewActivity.class);
            startActivity(ajuda);
            //Toast.makeText(this, "Ajuda", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.item2) {
            Toast.makeText(this, "Opció 2", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.item3) {
            Toast.makeText(this, "Opció 3", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    //AQUI POSEM QUE DEMANI PER LLEGIR ELS CONTACTES
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // La App esta en ejecución
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                !=PackageManager.PERMISSION_GRANTED)

        {


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {




            } else {

                // El usuario no necesitas explicación, puedes solicitar el permiso:
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        PERMISSIONS_REQUEST_READ_CONTACTS);
            }

        }
        bd();
    }

    public void bd(){

//Creació de la BBDD/taula, query per accedir a les dades i mostrarles despres
        lista = (ListView) findViewById(R.id.puntuacions);
        ArrayList<String> resultats = new ArrayList<String>();

        SQLiteDatabase baseDades = null;
        try {

            baseDades = this.openOrCreateDatabase(BBDD, MODE_PRIVATE, null);


            baseDades.execSQL("CREATE TABLE IF NOT EXISTS "
                    + TAULA
                    + " (data VARCHAR ,"
                    + " puntuacio INT) ;");

            Cursor c = baseDades.rawQuery("SELECT data, puntuacio"
                            + " FROM " + TAULA
                            + " WHERE puntuacio > 1 LIMIT 5;",
                    null);



            int columnaData = c.getColumnIndex("data");
            int columnaPuntuacio = c.getColumnIndex("puntuacio");



            if (c != null) {
                if(c.getCount() > 0) {
                    if (c.isBeforeFirst()) {
                        c.moveToFirst();
                        int i = 0;

                        do {
                            i++;
                            String data = c.getString(columnaData);
                            int puntuacio = c.getInt(columnaPuntuacio);

                            String nomColumnaPuntuacio = c.getColumnName(columnaPuntuacio);


                            resultats.add("" + i
                                    + " - " + nomColumnaPuntuacio + ": " + puntuacio + " | "+data);

                        } while (c.moveToNext());
                    }
                }
            }

        } finally {
            if (baseDades != null) {
                baseDades.close();
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                resultats );

        lista.setAdapter(arrayAdapter);
    }



    public void Jugarr(View view) {
        Intent Jugar = new Intent(this, sopalletres.class);
        startActivity(Jugar);



    }

}




