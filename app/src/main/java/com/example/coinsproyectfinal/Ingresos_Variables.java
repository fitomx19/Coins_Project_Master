package com.example.coinsproyectfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class Ingresos_Variables extends AppCompatActivity {

    EditText monto;
    TextView idusr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresos__variables);
        Intent intent = getIntent();

        String message2 = intent.getStringExtra(MainPagelogin.EXTRA_MESSAGE3); //identificador de usuario de Main login page
        TextView tv_usr = findViewById(R.id.tv_idusr);
        tv_usr.setText(message2);
      //  Toast.makeText(this, "Ingresos variables, id usuario: " +message2, Toast.LENGTH_SHORT).show();
    }
    public void submit(View v){
        sendDataToServer();


    }

    private void sendDataToServer() {
        Intent intent = getIntent();
        final String mensaje = intent.getStringExtra(MainPagelogin.EXTRA_MESSAGE3); //nombre de usuario
        final EditText monto = (EditText) findViewById(R.id.et_monto);
        final String mount = monto.getText().toString();
        final String id_usr = mensaje;

        //terminar el web service y despues ya agregar los datos!
        StringRequest sr = new StringRequest(Request.Method.POST, Registrar_Ingresos_Variables.URL_Registrar_Ingresos_Variables, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Ingresos_Variables.this,"Registro Exitoso!",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Ingresos_Variables.this,/*error.toString()*/"Registro completado",Toast.LENGTH_LONG).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> h=new HashMap<>();
                //creo que si van en orden xd
                h.put("id_usr",mensaje);
                h.put("monto",mount);

                return h;
            }
        };
        Volley.newRequestQueue(this).add(sr);

    }
}