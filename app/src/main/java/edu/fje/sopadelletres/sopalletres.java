package edu.fje.sopadelletres;
//Segunda activity para volver al menu principal.
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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

public class sopalletres extends AppCompatActivity {

    private GridView letterBoard;
    private static int numColumns = 10;
    private ArrayList<Integer> selectedPositions;
    private String orientation;
    private int count = 0;
    private ArrayList<String> wordListAcertades = new ArrayList<>();

    private int puntuacion = 60;
    private final String BBDD ="putamierda";
    private final String TAULA = "asco";

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

                //deselect
                if (selectedPositions.contains(position)) {
                    v.setBackgroundColor(Color.TRANSPARENT);
                    int index=-1;
                    for (int i=0; i<selectedPositions.size(); i++) {
                        if(selectedPositions.get(i).equals(position)) {
                            index = i;
                        }
                    }
                    if(index != -1) {
                        selectedPositions.remove(index);
                    } else {
                    }

                }
                //select
                else if(selectedPositions.isEmpty() || (selectedPositions.size() == 1 && isAdjacentToAll(position))) {
                    if(selectedPositions.size() >= 1) {
                        orientation = checkCurrentOrientation(position);
                    }
                    v.setBackgroundColor(Color.GREEN);
                    selectedPositions.add(position);
                } else if(selectedPositions.size() >= 2 /*&& checkAllowed(position)*/) {
                    v.setBackgroundColor(Color.GREEN);
                    selectedPositions.add(position);
                } else {
                }
                //display selected letters
                String printThis = "";
                for(int i=0; i<selectedPositions.size(); i++) {

                    printThis += LetterAdapter.getLetter(selectedPositions.get(i));
                    System.out.println(adaptadorLletra.getWordList());
                    System.out.println((printThis)+" ::: printThis");
                    System.out.println( adaptadorLletra.getWordList().size());
                    System.out.println(count+"Count 1 ");


                    for(int y=0;y<adaptadorLletra.getWordList().size();y++){
                        if(adaptadorLletra.getWordList().get(y).equals(printThis) && !wordListAcertades.contains(printThis)){
                            System.out.println("lA PALABRA COINCIDE");
                            System.out.println(count+"Count 2");
                            count++;
                            tvAciertos.setText("Mots Esdevinats: "+count);
                            System.out.println(count+"Count 3 ");
                            wordListAcertades.add(adaptadorLletra.getWordList().get(y));
                            printThis = "";
                            selectedPositions.clear();
                            //printThis.replace(wordListAcertades.toString(), "");
                            if(count == 2){
                                //Toast.makeText(sopalletres.this, "Has guanyat", Toast.LENGTH_SHORT).show();
                                terminarJuego();
                            }
                            break;
                        }
                        else{
                            System.out.println("lA PALABRA no COINCIDE");
                        }
                    }
                    // printThis.replace(wordListAcertades.toString(), "");
                    System.out.println(wordListAcertades.toString());
                    /*
                    System.out.println((printThis)+" ::: printThis");
                    System.out.println((LetterAdapter.getLetter(selectedPositions.get(i))+" ::: getLetter"));
                    System.out.println(adaptadorLletra.getWordList().get(i)+" aaaaaaa" );
*/
                }
            }
        });

        tvPuntuacio = (TextView) findViewById(R.id.tvPuntuacio);


        //temporizador cuenta atrás para la puntuación
        CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvPuntuacio.setText(String.format(Locale.getDefault(), "%d sec.", millisUntilFinished / 1000L));
                puntuacion--;
            }

            public void onFinish() {
                tvPuntuacio.setText("Done.");
            }
        }.start();
    }

    public void terminarJuego(){


            AlertDialog.Builder builder1 = new AlertDialog.Builder(sopalletres.this);
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
            if (baseDades != null) {
                baseDades.close();
            }
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

        if (id == R.id.item1) {
            Toast.makeText(this, "Ajuda", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.item2) {
            Toast.makeText(this, "Opció 2", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.item3) {
            Toast.makeText(this, "Opció 3", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private static boolean isAdjacent(int a, int b) {
        int ax = a % numColumns, ay = a / numColumns, bx = b % numColumns, by = b / numColumns;
        return Math.abs(ax - bx) <= 1 && Math.abs(ay - by) <= 1;
    }

    private boolean isAdjacentToAll(int position) {
        boolean adjacent = false;
        for(Integer next: selectedPositions) {
            if(isAdjacent(next, position)) {
                adjacent = true;
                break;
            }
        }
        return adjacent;
    }
/*
    private boolean checkAllowed(int position) {
        int check = 0;
        switch (orientation) {
            case "horizontal":
                check = 1;
                break;
            case "vertical":
                check = numColumns;
                break;
            case "diagonalDown":
                check = numColumns+1;
                break;
            case "diagonalUp":
                check = numColumns-1;
                break;
            default:
                Toast.makeText(sopalletres.this, "Error", Toast.LENGTH_SHORT).show();
        }

        boolean allow = false;
        for(Integer next: selectedPositions) {
            if(Math.abs(next - position) == check) {
                allow = true;
                break;
            }
        }
        return allow;
    }*/

    private String checkCurrentOrientation(int position) {
        int test = Math.abs(selectedPositions.get(selectedPositions.size()-1) - position);

        if(test == 1) {
            orientation = "horizontal";
        } else if(test == numColumns) {
            orientation = "vertical";
        } else if(test == numColumns+1) {
            orientation = "diagonalDown";
        } else if(test == numColumns-1) {
            orientation = "diagonalUp";
        } else {
            Toast.makeText(sopalletres.this, "Error", Toast.LENGTH_SHORT).show();
        }
        return orientation;
    }

    //Metódo para volver al Ménu Principal
    public void MenuPrincipal(View view){
        Intent menuprincipal = new Intent(this, SopaDeLetras.class);
        startActivity(menuprincipal);

    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<String> getWordListAcertades() {
        return wordListAcertades;
    }
}