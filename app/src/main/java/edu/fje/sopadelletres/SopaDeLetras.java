package edu.fje.sopadelletres;
//Primera activity con el boton jugar.

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class SopaDeLetras extends AppCompatActivity {


    //Metódo para mostrar y ocultar el menú.
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.barraacio, menu);
        return true;
    }

    //Metodo que assigna las funciones a las opciones.
    public boolean onOptionsItemSelected(MenuItem item) {
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

            // Explicamos porque necesitamos el permiso
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                // Acá continuamos el procesos deseado a hacer

            } else {

                // El usuario no necesitas explicación, puedes solicitar el permiso:
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
    }

    public void Jugarr(View view) {
        Intent Jugar = new Intent(this, sopalletres.class);
        startActivity(Jugar);



    }
}




