package edu.fje.sopadelletres;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;

public class Game extends AppCompatActivity {

    private GridView letterBoard;
    private static int numColumns = 10;
    private ArrayList<Integer> selectedPositions;
    private String orientation;
    private int count = 0;
    private ArrayList<String> wordListAcertades = new ArrayList<>();
    private int puntuacion = 60;
    private final String BBDD ="SopadeLletres";
    private final String TAULA = "Puntuacio";
    private TextView tvPuntuacio;
    private TextView tvAciertos;

    LetterAdapter adaptadorLletra = new LetterAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sopalletres);

        tvAciertos = (TextView) findViewById(R.id.tvAciertos);
        tvAciertos.setText("Mots Esdevinats: "+count);

        letterBoard = (GridView) findViewById(R.id.gridView);
        letterBoard.setAdapter(new LetterAdapter(this));
        letterBoard.setNumColumns(numColumns);

        selectedPositions = new ArrayList<Integer>();

        letterBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                //Desel·leccionar una casella de la sopa de lletres
                if (selectedPositions.contains(position)) {
                    v.setBackgroundColor(Color.TRANSPARENT);
                    int index=-1;
                    for (int i=0; i<selectedPositions.size(); i++) {
                        if(selectedPositions.get(i).equals(position)) { index = i; }
                    }
                    if(index != -1) { selectedPositions.remove(index);
                    } else {
                    }
                }
                //Seleccionar una lletra de la sopa de lletres
                else if(selectedPositions.isEmpty() || (selectedPositions.size() == 1 && isAdjacentToAll(position))) {
                    if(selectedPositions.size() >= 1) {
                        orientation = checkCurrentOrientation(position);
                    }
                    v.setBackgroundColor(Color.GREEN);
                    selectedPositions.add(position);
                } else if(selectedPositions.size() >= 2) {
                    v.setBackgroundColor(Color.GREEN);
                    selectedPositions.add(position);
                } else {
                }
                //Algoritme que et notifica quan has guanyat la partida
                String printThis = "";
                for(int i=0; i<selectedPositions.size(); i++) {
                    printThis += LetterAdapter.getLetter(selectedPositions.get(i));
                    for(int y=0;y<adaptadorLletra.getWordList().size();y++){
                        if(adaptadorLletra.getWordList().get(y).equals(printThis) && !wordListAcertades.contains(printThis)){
                            count++;
                            tvAciertos.setText("Mots Esdevinats: "+count);
                            wordListAcertades.add(adaptadorLletra.getWordList().get(y));
                            printThis = "";
                            selectedPositions.clear();
                            if(count == 2){ terminarJuego();}
                            break;
                        } else { System.out.println("lA PALABRA no COINCIDE"); }
                    }
                }
            }
        });
        tvPuntuacio = (TextView) findViewById(R.id.tvPuntuacio);
        CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
            //Funcio que determina el temps que has trigat en resoldre el joc
            public void onTick(long millisUntilFinished) {
                tvPuntuacio.setText(String.format(Locale.getDefault(), "%d sec.", millisUntilFinished / 1000L));
                puntuacion--;
            }
            //Quan has esgotat el temps et surt un "Done al comptador de temps"
            public void onFinish() {
                tvPuntuacio.setText("Done.");
            }
        }.start();
    }

    //Quan has resolt el joc et llença una notificacio del temps que has trigat transformat en el punts que has rebut
    public void terminarJuego(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Game.this);
        builder1.setMessage("Has guanyat, la teva puntuació ha sigut de "+puntuacion+" punts");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "REBUT",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        AlertDialog alert11 = builder1.create();
        // Introduce el resultado dentro de la BBDD
        ResultadosdentroBBDD(puntuacion,String.valueOf(java.time.LocalDate.now()));
        alert11.show();
    }

    protected void ResultadosdentroBBDD(int punt,String data){
        SQLiteDatabase baseDades = null;
        try {
            baseDades = this.openOrCreateDatabase(BBDD, MODE_PRIVATE, null);
            baseDades.execSQL("INSERT INTO "
                    + TAULA
                    + " (data, puntuacio)"
                    + " VALUES (\'"+data+"\', "+punt+");");

        } finally {
            if (baseDades != null) { baseDades.close(); }
        }
    }

    //Metódo para mostrar y ocultar el menú.
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.barraacio, menu);
        return true;
    }

    //Metodo que assigna las funciones a las opciones.
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item1)      { Toast.makeText(this, "Ajuda", Toast.LENGTH_SHORT).show(); }
        else if (id == R.id.item2) { Toast.makeText(this, "Opció 2", Toast.LENGTH_SHORT).show(); }
        else if (id == R.id.item3) { Toast.makeText(this, "Opció 3", Toast.LENGTH_SHORT).show(); }
        return super.onOptionsItemSelected(item);
    }

    //Funcio que s'utilitza per seleccionar la casella adllacent a la que has seleccionat y no qualsevol
    private static boolean isAdjacent(int a, int b) {
        int ax = a % numColumns, ay = a / numColumns, bx = b % numColumns, by = b / numColumns;
        return Math.abs(ax - bx) <= 1 && Math.abs(ay - by) <= 1;
    }

    private boolean isAdjacentToAll(int position) {
        boolean adjacent = false;
        for(Integer next: selectedPositions) {
            if(isAdjacent(next, position)) { adjacent = true; break; }
        }
        return adjacent;
    }

    //Determina la direccio en que selleciones les caselles
    private String checkCurrentOrientation(int position) {
        int test = Math.abs(selectedPositions.get(selectedPositions.size()-1) - position);
        if(test == 1)                 { orientation = "horizontal"; }
        else if(test == numColumns)   { orientation = "vertical"; }
        else if(test == numColumns+1) { orientation = "diagonalDown"; }
        else if(test == numColumns-1) { orientation = "diagonalUp";}
        else { Toast.makeText(Game.this, "Error", Toast.LENGTH_SHORT).show(); }
        return orientation;
    }
}