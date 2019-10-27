package com.example.coinsproyectfinal;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class gastos_variables extends AppCompatActivity {
    private static final String url = "jdbc:mysql://protocoins.mysql.database.azure.com:3306/coinsproyect";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos_variables);
        // rs=stmt.executeQuery("call idgf('"+Usr+"')");
        Intent intent = getIntent();
        String message2 = intent.getStringExtra(MainPagelogin.EXTRA_MESSAGE3);

    }
    private class tarea extends AsyncTask<Void,Void,Void> {
        private String idgf="",IName="",Iid="";
        private String idusr;
        Intent intent = getIntent();
        String message2 = intent.getStringExtra(MainPagelogin.EXTRA_MESSAGE3);

        boolean succes = false;

        final EditText nombre = (EditText) findViewById(R.id.id_nom);
        final EditText monto = (EditText) findViewById(R.id.et_gastos);
        final String mount = monto.getText().toString();
        final String nom = nombre.getText().toString();
        final int monto2 = Integer.parseInt(mount);
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection c = DriverManager.getConnection(url, "royalmazan@protocoins", "n0m3l0s3.");

                Statement stmt = c.createStatement();
                ResultSet rset = stmt.executeQuery("call idgf('"+message2+"')");
                if(rset.next()){
                    idgf = rset.getString(1);

                    stmt.execute("INSERT INTO gastosvariables (`NGasto`,`Gasto`,`status`,`tipo`) VALUES ('"+nom+"','"+mount+"','1','0')");
                    stmt.execute("update gastosfijos set Sum=Sum+'"+monto2+"' where id_gf='"+idgf+"'");
                    stmt.execute("update gastosfijos set Extra=Extra+'"+monto2+"' where id_gf='"+idgf+"'");
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
                Toast.makeText(gastos_variables.this, "Error en el Servidor, Intente más tarde", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            super.onPostExecute(result);
            if (succes == true){


                Toast.makeText(gastos_variables.this, "Correcto " + idgf, Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(gastos_variables.this, "Datos Erroneos, Verifique Informacion", Toast.LENGTH_SHORT).show();
            }

        }
    }
    public void info(View view) {
        new tarea().execute();
    }

}
