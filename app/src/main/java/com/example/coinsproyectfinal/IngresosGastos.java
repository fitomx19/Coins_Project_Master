package com.example.coinsproyectfinal;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class IngresosGastos extends AppCompatActivity {
    private static final String url = "jdbc:mysql://protocoins.mysql.database.azure.com:3306/coinsproyect";
    public static final String EXTRA = "com.example.myfirstapp.EXTRA";
    int period = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresos_gastos);
        Intent intent = getIntent();
        String mensaje = intent.getStringExtra(MainPagelogin.EXTRA_MESSAGE3); //nombre de usuario

        Toast.makeText(this, "Ingresa tus Gastos e Ingresos Fijos!", Toast.LENGTH_SHORT).show();

    }
    public void submit(View v){
                sendDataToServer();
        EditText ing = (EditText) findViewById(R.id.Ingresos_input);
        EditText  luz = findViewById(R.id.ele);
        EditText  agua = findViewById(R.id.agu);
        EditText  comida= findViewById(R.id.comi);
        EditText  gas= findViewById(R.id.ga);
        EditText  deudas = findViewById(R.id.deuda);
        EditText  transporte = findViewById(R.id.transp);
        Spinner spin = findViewById(R.id.spinner123);
        ing.setText("");
        luz.setText("");
        agua.setText("");
        comida.setText("");
        gas.setText("");
        deudas.setText("");
        transporte.setText("");

        
    }


        private void sendDataToServer() {
            Intent intent = getIntent();
            String mensaje = intent.getStringExtra(MainPagelogin.EXTRA_MESSAGE3); //nombre de usuario
            EditText ing = (EditText) findViewById(R.id.Ingresos_input);
            EditText  luz = findViewById(R.id.ele);
            EditText  agua = findViewById(R.id.agu);
            EditText  comida= findViewById(R.id.comi);
            EditText  gas= findViewById(R.id.ga);
            EditText  deudas = findViewById(R.id.deuda);
            EditText  transporte = findViewById(R.id.transp);
            Spinner spin = findViewById(R.id.spinner123);
            String spin123 =  spin.getSelectedItem().toString();
            final String ingreso = ing.getText().toString();
            final String electricidad = luz.getText().toString();
            final String water = agua.getText().toString();
            final String food = comida.getText().toString();
            final String gaz = gas.getText().toString();
            final String lodges = deudas.getText().toString();
            final String car = transporte.getText().toString();
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

        StringRequest sr = new StringRequest(Request.Method.POST, Registrar_Ingresos.URL_Registrar_Ingresos, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(IngresosGastos.this,"Registro Exitoso!",Toast.LENGTH_LONG).show();
                //aqui va otro intent
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(IngresosGastos.this,error.toString(),Toast.LENGTH_LONG).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> h=new HashMap<>();
             //creo que si van en orden xd
                h.put("monto",ingreso);
                h.put("fre",frecuencia);
                h.put("usu", id_usr);
                h.put("elec",electricidad);
                h.put("agua",water);
                h.put("gas",food);
                h.put("food",gaz);
                h.put("deudas",lodges);
                h.put("coche",car);
                return h;
            }
        };
        Volley.newRequestQueue(this).add(sr);

    }
   }


