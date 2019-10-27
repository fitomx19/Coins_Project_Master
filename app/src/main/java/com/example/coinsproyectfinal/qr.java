package com.example.coinsproyectfinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


 //ARREGLAR EL BOTON QUE EJECUTE EL ASYNCTASK

    public class qr extends AppCompatActivity implements ZXingScannerView.ResultHandler {
        private ZXingScannerView escanerView;
        String test;
        String nom;
        int extr;
        String plan;
        String ahorro;
        String p, usr, nombremeta, montometa;


        private static final String url = "jdbc:mysql://192.168.1.67:1616/coinsproyect?autoReconnect=true&useSSL=false";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_qr);
            Toast.makeText(this, "Escanea el codigo QR", Toast.LENGTH_LONG).show();
            escanerView = new ZXingScannerView(this);
            setContentView(escanerView);
            escanerView.setResultHandler(this);
            escanerView.startCamera();
            Intent intent = getIntent();
            String message = intent.getStringExtra(saber_mas.EXTRA_MESSAGE7); //nombre de usuario
            String message2 = intent.getStringExtra(saber_mas.EXTRA_MESSAGE8); // cuanto?
            String message3 = intent.getStringExtra(saber_mas.EXTRA_MESSAGE9); // cada cuando?

            int multiplicacion = Integer.parseInt(message2) * Integer.parseInt(message3);
            int calentador_solar = 7000 / multiplicacion;

        }


        public void EscanerQR(View view) {

        }

        @Override
        protected void onPause() {
            super.onPause();
            escanerView.stopCamera();
        }

        @Override
        public void handleResult(Result result) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Escaner de Ahorro Coins!");
            Intent intent = getIntent();
            String message = intent.getStringExtra(saber_mas.EXTRA_MESSAGE7); //nombre de usuario
            String message2 = intent.getStringExtra(saber_mas.EXTRA_MESSAGE8); // cuanto?
            String message3 = intent.getStringExtra(saber_mas.EXTRA_MESSAGE9); // cada cuando?
            System.out.println(message + " " + message2 + " " + message3);

            //ID USR
            String id_usr = message;
            int multiplicacion = Integer.parseInt(message2) * Integer.parseInt(message3);
            final int calentador1= 7000;
            final int filtro1 = 11000;
            final int wc1 = 3500;
            final int foco1 = 380;
            final int calentador_solar = 7000 / multiplicacion;
            int filtro = 11000 / multiplicacion;
            int wc = 3500 / multiplicacion;
            int focos = 400 / multiplicacion;
            String mensaje_focos;

            String period;

            //asignar tiempo
            if(message3.equals("2")){
                period = "quincenal";
            }else if(message3.equals("4")){
                period = "semanal";
            }else if(message3.equals("20")){
                period = "diario";
            }else{
                period = "???";
            }

            if (focos < 1){
                mensaje_focos = "Necesitas ahorrar por menos de un mes, vamos es facíl!";
            }else {
                mensaje_focos="Si continuas ahorrando por " +Integer.toString(focos)+" meses podras conseguirlo!" ;
            }

            // Este metodo era pasaber cualk de los parametrosa de call saving jalo desde consejos luego saber + y finalmente qr,  Toast.makeText(this, "'cuanto? " + message2 + " : " + multiplicacion, Toast.LENGTH_SHORT).show();

            test = result.getText();
            if (test.equals("http://l.ead.me/bb7nt3")) { //AHORRO DEL GAS
                builder.setMessage("Si continuas ahorrando por " + calentador_solar + " meses podras conseguirlo! con tu ahorro de "+"$" + message2+".00 "  +period+"\nPrecio Preferencial $6500 pesos");

                Toast.makeText(this, "Calentador solar Ecotec para 4 Personas ", Toast.LENGTH_LONG).show();
                LayoutInflater factory = LayoutInflater.from(this);
                final View view = factory.inflate(R.layout.tv_image, null);
                builder.setView(view);

                builder.setPositiveButton("Agregar a Meta de Ahorro", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nom="Calentador Solar";
                        extr=calentador1;
                        new MyTask().execute();

                    }
                });
                builder.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
            } else if (test.equals("http://l.ead.me/bb7qFM")) {   //AHORRO DEL AGUA
                builder.setMessage("Si continuas ahorrando por " + wc + " meses podras conseguirlo! con tu ahorro de "+"$" + message2+".00 "  +period+"\nPrecio Preferencial $3500 pesos");

                Toast.makeText(this, "WC Ahorrador ", Toast.LENGTH_LONG).show();
                LayoutInflater factory = LayoutInflater.from(this);
                final View view = factory.inflate(R.layout.tv_image2, null);
                builder.setView(view);

                builder.setPositiveButton("Agregar a Meta de Ahorro", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(qr.this, "Agregado!", Toast.LENGTH_LONG).show();
                        nom="WC Ahorrador";
                        extr=wc1;
                        new MyTask().execute();

                    }
                });
                builder.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
            } else if (test.equals("http://l.ead.me/bb7qF1")) { //Ahorrador comida
                builder.setMessage("Si continuas ahorrando por " + filtro + " meses podras conseguirlo! con tu ahorro de "+"$" + message2+".00 "  +period+"\nPrecio Preferencial $10500 pesos");

                Toast.makeText(this, "Filtro de Agua Osmosis Inversa ", Toast.LENGTH_LONG).show();
                LayoutInflater factory = LayoutInflater.from(this);
                final View view = factory.inflate(R.layout.tv_image3, null);
                builder.setView(view);

                builder.setPositiveButton("Agregar a Meta de Ahorro", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(qr.this, "Agregado!", Toast.LENGTH_LONG).show();
                        nom="Filtro Agua";
                        extr=filtro1;
                        new MyTask().execute();

                    }
                });
                builder.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

            } else if (test.equals("http://l.ead.me/bb8BPA")) {
                builder.setMessage(mensaje_focos +  " con tu ahorro de "+"$" + message2+".00 "  +period+"\nPrecio Preferencial $380 pesos");

                Toast.makeText(this, "Focos Ahorradores ", Toast.LENGTH_LONG).show();
                LayoutInflater factory = LayoutInflater.from(this);
                final View view = factory.inflate(R.layout.tv_image4, null);
                builder.setView(view);

                builder.setPositiveButton("Agregar a Meta de Ahorro", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(qr.this, "Agregado!", Toast.LENGTH_LONG).show();
                        nom="Focos Ahorradores";
                        extr=foco1;
                        new MyTask().execute();

                    }
                });
                builder.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

            }else {
                Toast.makeText(this, "Escanea un Producto Valido", Toast.LENGTH_SHORT).show();
            }
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            escanerView.resumeCameraPreview(this);
        }

        private class MyTask extends AsyncTask<Void, Void, Void> {
            Intent intent = getIntent();
            String message = intent.getStringExtra(saber_mas.EXTRA_MESSAGE7); //nombre de usuario
            boolean succes = false;
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection c = DriverManager.getConnection(url, "root", "07630");

                    Statement stmt = c.createStatement();
                    ResultSet rset = stmt.executeQuery("select * from usuario where id_usr = '"+message+"'");

                    if (rset.next()) {
                        plan=rset.getString(4);
                        stmt.execute("update usuario set meta = '"+nom+"' where id_plan='"+plan+"'" );
                        stmt.execute("update usuario set montometa = '"+extr+"' where id_plan='"+plan+"'");
                        stmt.execute("update usuario set montometafinal = '"+extr+"' where id_plan='"+plan+"'");
                        stmt.execute("update planahorro set ahorro = 0 where id_plan='"+plan+"'" );
                        rset.close();
                        stmt.close();
                        c.close();
                        succes = true;
                     } else {
                        succes = false;
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {


                super.onPostExecute(result);
                if (succes == true) {
                    Toast.makeText(qr.this, "Agregado!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(qr.this, "Datos Erroneos, Verifique Informacion", Toast.LENGTH_SHORT).show();
                }

            }
        }

    }
