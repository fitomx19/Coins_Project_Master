package com.example.coinsproyectfinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class saber_mas extends AppCompatActivity {

    TextView cajon;
    ImageView image;
    public static final String EXTRA_MESSAGE7 = "com.example.myfirstapp.MESSAGE4";
    public static final String EXTRA_MESSAGE8 = "com.example.myfirstapp.MESSAGE5";
    public static final String EXTRA_MESSAGE9 = "com.example.myfirstapp.MESSAGE6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saber_mas);
        Intent intent = getIntent();
        String message = intent.getStringExtra(Consejos_Ahorro.EXTRA_MESSAGE3); //nombre de usuario
        String message2 = intent.getStringExtra(Consejos_Ahorro.EXTRA_MESSAGE4); // gas
        String message3 =  intent.getStringExtra(Consejos_Ahorro.EXTRA_MESSAGE5); // cuanto ahorra?
        String message4 =  intent.getStringExtra(Consejos_Ahorro.EXTRA_MESSAGE6); // cada cuanto ahorra?

        cajon = findViewById(R.id.cajon);
        image = findViewById(R.id.imagen_img);



        if (message2.equals("electricidad")) {

            cajon.setText("Una manera de ahorrar electricidad es ...");
            image.setImageResource(R.drawable.l1);

        } else if (message2.equals("agua")) {

            cajon.setText("Una manera de ahorrar en agua es:");
            image.setImageResource(R.drawable.a1);
        } else if (message2.equals("gas")) {

            cajon.setText("Una manera de ahorrar en gas es:");
            image.setImageResource(R.drawable.g1);
        } else if (message2.equals("comida")) {

            cajon.setText("Una manera de ahorrar en alimentacion es:");
            image.setImageResource(R.drawable.c1);

        } else if (message2.equals("transporte")) {

            cajon.setText("Una manera de ahorrar en transporte es:");
            image.setImageResource(R.drawable.t1);

        } else if (message2.equals("prestamo")) {

            cajon.setText("Una manera de ahorrar y evitar prestamos innecesarios es:");
            image.setImageResource(R.drawable.d1);

        } else {
            Toast.makeText(this, "mensajes: " + message + " " + message2 + " ¿que es esto? ", Toast.LENGTH_SHORT).show();
        }


    }

    public void meow(View view) {
        Intent i = new Intent(getApplicationContext(), qr.class);
        Intent intent = getIntent();
        String message_saber = intent.getStringExtra(MainPagelogin.EXTRA_MESSAGE3);
        String message_saber2 = intent.getStringExtra(Consejos_Ahorro.EXTRA_MESSAGE5);
        String message_saber3 = intent.getStringExtra(Consejos_Ahorro.EXTRA_MESSAGE6);

        i.putExtra(EXTRA_MESSAGE7, message_saber); //identificador de usuario
        i.putExtra(EXTRA_MESSAGE8, message_saber2); //monto al mes
        i.putExtra(EXTRA_MESSAGE9, message_saber3); //cada cuanto ahorra
       // Toast.makeText(this, message_saber+message_saber2+message_saber3, Toast.LENGTH_SHORT).show();
        startActivity(i);
    }
    public void show1 (View view){
        AlertDialog.Builder alerta3 = new AlertDialog.Builder(saber_mas.this);
        alerta3.setMessage("Escanea los productos con nuestro logo para saber más de ellos!")
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        dialog.cancel();

                    }
                })
                .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog titulo = alerta3.create();
        titulo.setTitle("¿Que es esto?");
        titulo.show();
    }
}