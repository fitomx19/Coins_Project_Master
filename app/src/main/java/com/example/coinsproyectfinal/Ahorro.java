package com.example.coinsproyectfinal;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Ahorro extends AppCompatActivity {
    private TextView id_usralpha;

    int period = 0;
    private static final String url = "jdbc:mysql://10.2.22.248:1616/coinsproyect";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ahorro);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE); //nombre de usuario
        String message2 = intent.getStringExtra(MainPagelogin.EXTRA_MESSAGE3); //identificador de usuario de Main login page
        TextView identificadorusuario = findViewById(R.id.id_usr);
        identificadorusuario.setText(message2);
        //Toast.makeText(this, "dentificador de usuario " + message2, Toast.LENGTH_SHORT).show();
        info();
    }

    private void info() {
        new ahorro().execute();
    }


    public void submit(View v){
        sendDataToServer();
        EditText monto = (EditText) findViewById(R.id.monto_et);
        Spinner spin = findViewById(R.id.periodo_spin);
        monto.setText("");



    }
    private class ahorro extends AsyncTask<Void,Void,Void> {

        private String id_ahorro="",monto_p="",tiempo="";
        TextView id_usr1 = findViewById(R.id.id_usr);
        String id_usr123 = id_usr1.getText().toString();
        boolean succes = false;
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection c = DriverManager.getConnection(url, "root", "07630");

                Statement stmt = c.createStatement();
                final ResultSet rset = stmt.executeQuery("call savings('"+id_usr123+"')");
                if(rset.next()){
                    id_ahorro = rset.getString(1);
                    monto_p = rset.getString(3);
                    tiempo = rset.getString(4);

                    rset.close();
                    stmt.close();
                    c.close();
                    succes = true;
                }
                else
                {

                    succes = false;
                }

            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(Ahorro.this, "Error en el Servidor, Intente más tarde", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            super.onPostExecute(result);
            if (succes == true){
                TextView ahorro = (TextView) findViewById(R.id.id_ahorro);
                TextView ahorro2 = (TextView) findViewById(R.id.Anterior);
                TextView ahorro3 = (TextView) findViewById(R.id.Proyeccion);
                ahorro.setText(id_ahorro);
                ahorro2.setText("Ahorro Anterior: "+"$"+monto_p+ " pesos");
                int time = Integer.parseInt(tiempo);
                int ahorro_anterior = Integer.parseInt(monto_p);
                if (tiempo.equals("20")){
                    ahorro3.setText("En un mes ahorraras: "+ "$" + 20*ahorro_anterior + " pesos");
                }else if(tiempo.equals("4")){
                    ahorro3.setText("En un mes ahorraras: "+ "$" + 4*ahorro_anterior + " pesos");
                }else if(tiempo.equals("2")){
                    ahorro3.setText("En un mes ahorraras: "+ "$" + 2*ahorro_anterior + " pesos");
                }else{
                    ahorro3.setText("Continua ahorrando! Si se puede");
                }
                //Aqui ponemos los anteriores:
                //Toast.makeText(Ahorro.this, "ID_AHORRO: " + id_ahorro, Toast.LENGTH_SHORT).show();



            }else{
                Toast.makeText(Ahorro.this, "Error en el Servidor", Toast.LENGTH_SHORT).show();
            }

        }
    }



    private void sendDataToServer() {
        Intent intent = getIntent();
        String mensaje = intent.getStringExtra(MainPagelogin.EXTRA_MESSAGE3); //id de usuario
        EditText monto = (EditText) findViewById(R.id.monto_et);
        TextView id_ahorrar = findViewById(R.id.id_ahorro);

        Spinner spin = findViewById(R.id.periodo_spin);
        String spin123 =  spin.getSelectedItem().toString();
        final String ingreso = monto.getText().toString();
        final String idahorro = id_ahorrar.getText().toString();



        final String id_usr = mensaje;


        if(spin123.equals("Diario")){
            period = 20;
        }else if(spin123.equals("Semanal")){
            period = 4;
        }else if(spin123.equals("Quincenal")){
            period = 2;
        }
        final String frecuencia = Integer.toString(period);

        //terminar el web service y despues ya agregar los datos!

        StringRequest sr = new StringRequest(Request.Method.POST, Registrar_Ahorro.URL_Registrar_Ahorro, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Ahorro.this,"Registro Exitoso!",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Ahorro.this,error.toString(),Toast.LENGTH_LONG).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> h=new HashMap<>();
                //creo que si van en orden xd
                h.put("usu", id_usr);
                h.put("ahorro",idahorro);
                h.put("monto",ingreso);
                h.put("fre",frecuencia);


                return h;
            }
        };
        Volley.newRequestQueue(this).add(sr);

    }
}
