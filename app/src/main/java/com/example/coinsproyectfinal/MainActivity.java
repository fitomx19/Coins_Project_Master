package com.example.coinsproyectfinal;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String EXTRA_MESSAGE2 = "com.example.myfirstapp.MESSAGE2";

    private static final String url = "jdbc:mysql://protocoins.mysql.database.azure.com:3306/coinsproyect";
    public static String name2;
    Button login,registro,otro;
    EditText usr, psw;
    TextView advice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usr = (EditText) findViewById(R.id.nombre);
        psw = (EditText) findViewById(R.id.password);
    }
    public void onClick(View view){
        System.out.println("click");
        new MyTask().execute();
    }
    public  void clickme(View view){
        Intent i = new Intent(getApplicationContext(), Web.class);
        startActivity(i);

    }
    private class MyTask extends AsyncTask<Void,Void,Void> {
        private String IPsw="",IName="",Iid="";
        private String idusr;
        String name2 = usr.getText().toString();
        String uname3 = psw.getText().toString();
        boolean succes = false;
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection c = DriverManager.getConnection(url, "royalmazan@protocoins", "n0m3l0s3.");

                Statement stmt = c.createStatement();
                final ResultSet rset = stmt.executeQuery("SELECT * FROM usuario where nom='"+name2+"' and psw ='"+uname3+"'");
                if(rset.next()){
                    IPsw = rset.getString(3);
                    IName = rset.getString(2);
                    idusr = rset.getString(1);
                    rset.close();
                    stmt.close();
                    c.close();
                    succes = true;
                    }
                else
                {
                    IName = "Datos Erroneos";
                    IPsw = "Datos Erroneos";
                    succes = false;
                }

            }
            catch (Exception e){
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            super.onPostExecute(result);
            if (succes == true){
                Intent i = new Intent(getApplicationContext(), MainPagelogin.class);
                String message = idusr;
                String message2 = IName;
                i.putExtra(EXTRA_MESSAGE, message); //identificador de usuario
                i.putExtra(EXTRA_MESSAGE2, message2); // nombre de usuario
                startActivity(i);
                finish();

            }else{
                Toast.makeText(MainActivity.this, "Datos Erroneos, Verifique Informacion", Toast.LENGTH_SHORT).show();
            }

        }
    }


    }



