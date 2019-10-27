package com.example.coinsproyectfinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.health.SystemHealthManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;


public class Consejos_Ahorro extends AppCompatActivity {
    public static final String EXTRA_MESSAGE3 = "com.example.myfirstapp.MESSAGE3";
    public static final String EXTRA_MESSAGE4 = "com.example.myfirstapp.MESSAGE4";
    public static final String EXTRA_MESSAGE5 = "com.example.myfirstapp.MESSAGE5";
    public static final String EXTRA_MESSAGE6 = "com.example.myfirstapp.MESSAGE6";
    private static final String url = "jdbc:mysql://protocoins.mysql.database.azure.com:3306/coinsproyect";
    private ImageButton variables_luz;
    private ImageButton variables_agua;
    private ImageButton variables_gas;
    private ImageButton varibales_comida;
    private ImageButton variables_prestamo;
    private ImageButton variables_transporte;
    boolean succes = false;



    //aqui el sql para obtener la informacion
    //call gastosfijos('"+Usr+"')


    private class ahorro extends AsyncTask<Void,Void,Void> {

        private String electricidad = "", agua = "", gas = "", comida = "", prestamo = "", transporte = "",monto="",test="",ahorro_monto="",tiempo="",monto_ahorrado="";
        Intent intent = getIntent();
        String Mariana1;
        String message2 = intent.getStringExtra(MainPagelogin.EXTRA_MESSAGE3); //identificador de usuario de Main login page
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection c = DriverManager.getConnection(url, "royalmazan@protocoins", "n0m3l0s3.");
                Statement stmt = c.createStatement();
                ResultSet rset = stmt.executeQuery("call gastosfijos('"+message2+"')");

                if (rset.next()) {
                    electricidad = rset.getString(1);
                    agua = rset.getString(2);
                    gas = rset.getString(3);
                    comida = rset.getString(4);
                    prestamo = rset.getString(5);
                    transporte = rset.getString(6);

                    //ingresos torales

                    rset = stmt.executeQuery("call ingresostot ('"+message2+"')");
                    rset.next();
                    monto = rset.getString(1);
                    System.out.println(monto);

                    //Ahorrors

                    rset = stmt.executeQuery("call savings ('"+message2+"')");
                    rset.next();
                    ahorro_monto = rset.getString(2);
                    tiempo = rset.getString(4);
                    monto_ahorrado = rset.getString(3);
                    System.out.println("ahorro monto: " + ahorro_monto + "tiempo: " + tiempo);
                    rset.close();
                    stmt.close();
                    c.close();
                    succes = true;
                }else{
                    succes = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(Consejos_Ahorro.this, "Error en el Servidor, Intente más tarde", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

               @Override
               protected void onPostExecute(Void result) {
                   super.onPostExecute(result);
                   final Double luz,gaz,food,lodge,car;
                   final Double water;

                   final int Mariana = Integer.parseInt(tiempo);
                   String mensaje_luz,mensaje_agua,mensaje_gas,mensaje_comida,mensaje_prestamo,mensaje_transporte;
                   System.out.print(Mariana);

                   luz=Double.parseDouble(electricidad)/Double.parseDouble(monto);
                   water=Double.parseDouble(agua)/Double.parseDouble(monto);
                   gaz=Double.parseDouble(gas)/Double.parseDouble(monto);
                   food=Double.parseDouble(comida)/Double.parseDouble(monto);
                   lodge=Double.parseDouble(prestamo)/Double.parseDouble(monto);
                   car=Double.parseDouble(transporte)/Double.parseDouble(monto);

                   //Esto de decimal format me lo habia fusilado de un proyecto de un morro de cuarto semestre que ahorita ya esta conmigo xd en le mismg grado

                   final DecimalFormat df = new DecimalFormat("#.00");
                   //Aqui se va a hacer un p***** d***** por tanta variable pero meh! me gusta ser flojo no?
                   //Aqui va los mensajes de cada rubro empezando en orden respectivo con la luz
                   //METODO DE LA LUZ

                   //METODO DEL AGUA



                   if(succes==true) {
                       variables_luz = findViewById(R.id.luz_btn);
                       variables_agua = findViewById(R.id.agua_btn);
                       variables_gas = findViewById(R.id.gas_btn);
                       varibales_comida = findViewById(R.id.comida_btn);
                       variables_prestamo = findViewById(R.id.prestamos_btn);
                       variables_transporte = findViewById(R.id.transporte_btn);

                       if (Mariana==20){
                           Mariana1="dias";
                       }else if(Mariana==4){
                           Mariana1="semanas";
                       }else  if (Mariana==2){
                           Mariana1 = "quincenas";
                       }

                       //OTRA ESCUPIDERA DE CODIGO XD Pero ni modo es necesaria
                       if(luz*100 <= 13.5){
                            mensaje_luz = " Estas dentro de el rango de gasto de energia electrica pero sin embargo checa nuestros consejos de ahorro electrico, nunca estan de más.";
                       }else{
                            mensaje_luz = "Estas sobrepasando el gasto recomendado para este rubro de gastos, ten cuidado en la CDMX el gasto excesivo de energia electrica disminuye en gran medida el subsidio de la misma, porque no usas enegias renovables como un sistema de celdas solares " +
                                "con tu ahorro podras comprarlo entre " + 40000/Integer.parseInt(ahorro_monto+1) + " y " + 30000/Integer.parseInt(ahorro_monto+1) + " " +Mariana1 +  " de ahorro constante dependiendo el precio, animate a la larga sera un gran ahorro!";
                       }
                        if(water*100 <= 13.5){
                            mensaje_agua =  "Estas dentro de el rango de gasto de agua potable pero sin embargo checa nuestros consejos de ahorro de agua, recuerda que cuidar el agua es lo más importante para poder conservar nuestro planeta azul ♥";
                        }else{
                            mensaje_agua = "Estas sobrepasando el gasto recomendado para este rubro de gastos, este en particular es muy importante " + "Para ahorrar agua como tal existen una amplia gama de productos " + "son muy baratos si ahorras constantemente lo conseguiras entre " + 1000/Integer.parseInt(ahorro_monto) + " y  " +4000/Integer.parseInt(ahorro_monto) + " " +Mariana1 +  "animate! Cuida el agua por lo que más quieras!.";
                        }
                        if(gaz*100 <= 13.5){
                           mensaje_gas = "Estas dentro de el rango de gasto de de Gas Natural,LP Y Combustibles de cualquier forma revisa nuestros consejos de ahorro , recuerda siempre reportar fugas!";
                       }else{
                            mensaje_gas =" Estas sobrepasando el gasto recomendado para este rubro de gastos revisa  tus tomas de gas ,una grandiosa opcion para ahorrar es un calentador solar";
                       }
                       if(food*100 <= 20){
                            mensaje_comida = "Estas consumiendo actualmente el " +df.format(food*100) + "de tu salario en consumo de Alimentos y bebidas " + "Estas dentro de el rango de gasto de de consumo de alimentos y bebidas sanos para tu economia de cualquier forma revisa nuestros consejos de ahorro ";
                       }else {
                            mensaje_comida = " Estas sobrepasando el gasto recomendado para este rubro de gastos revisa nuestros tips no siempre la comida más cara es la mejor ;)!\n" +
                                    "    No esta demás recomendarte Un filtro de agua o una botella rellenable";
                       }
                       if(car*100 <= 13.66){
                            mensaje_transporte = "Estas dentro de el rango de gastos en transporte eso quiere decir que estas optimizando de una forma correcta los metodos de transporte que tienes, Felicidades! de cualquier forma revisa nuestros consejos de ahorro.";
                       }else{
                            mensaje_transporte = "Estas sobrepasando el gasto recomendado para este rubro prueba conseguir una bicleta o bicleta electrica\n" +
                                    "te pueden ayudar mucho en tu bolsillo y para bajar unos kilitos!";
                       }
                       if(lodge*100 <= 30){
                            mensaje_prestamo = "Estas consumiendo actualmente el " + df.format(lodge*100) + "de tu salario en Deudas " + "Estas dentro de el rango de gastos en deudas Felicidades! segun datos de El Juridico donde  más del 60% de las personas deben más del una tercera parte de sus ingresos mensualesde  cualquier forma revisa nuestros consejos de ahorro para ser uno de los mejores!" ;
                       }else{
                            mensaje_prestamo="Estas sobrepasando el gasto recomendado para este rubro de gastos revisa nuestros consejos urgentemente! :c";
                       }
                        final String msg_luz = mensaje_luz;
                        final String msg_agua = mensaje_agua;
                        final String msg_gas = mensaje_gas;
                        final String msg_comida = mensaje_comida;
                        final String msg_prestamo = mensaje_prestamo;
                        final String msg_transporte = mensaje_transporte;

                       variables_luz.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               AlertDialog.Builder alerta = new AlertDialog.Builder(Consejos_Ahorro.this);
                               alerta.setMessage(msg_luz)
                                       .setCancelable(false)
                                       .setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {
                                               dialog.cancel();
                                           }
                                       })
                                       .setPositiveButton("Conocer mas...", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {
                                               Intent i = new Intent(getApplicationContext(), saber_mas.class);
                                               Intent intent = getIntent();
                                               String message_saber = intent.getStringExtra(MainPagelogin.EXTRA_MESSAGE3);
                                               String message_saber3 = monto_ahorrado;
                                               String message_saber4 = Integer.toString(Mariana);
                                               String message_saber2 =  "electricidad";
                                               i.putExtra(EXTRA_MESSAGE3, message_saber); //identificador de usuario
                                               i.putExtra(EXTRA_MESSAGE4, message_saber2); // gas
                                               i.putExtra(EXTRA_MESSAGE5, message_saber3); // cuanto ahorra?
                                               i.putExtra(EXTRA_MESSAGE6, message_saber4); // cada cuanto ahorra?
                                               startActivity(i);
                                           }

                                       });
                               AlertDialog titulo = alerta.create();
                               titulo.setTitle("¿Como Ahorrar Elctricidad?");
                               titulo.show();


                           }
                       });

                       variables_agua.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               AlertDialog.Builder alerta = new AlertDialog.Builder(Consejos_Ahorro.this);
                               alerta.setMessage(msg_agua)
                                       .setCancelable(false)
                                       .setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {
                                               dialog.cancel();
                                           }
                                       })
                                       .setPositiveButton("Conocer más", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {
                                               Intent i = new Intent(getApplicationContext(), saber_mas.class);
                                               Intent intent = getIntent();
                                               String message_saber = intent.getStringExtra(MainPagelogin.EXTRA_MESSAGE3);
                                               String message_saber3 = monto_ahorrado;
                                               String message_saber4 = Integer.toString(Mariana);
                                               String message_saber2 =  "agua";
                                               i.putExtra(EXTRA_MESSAGE3, message_saber); //identificador de usuario
                                               i.putExtra(EXTRA_MESSAGE4, message_saber2); // gas
                                               i.putExtra(EXTRA_MESSAGE5, message_saber3); // cuanto ahorra?
                                               i.putExtra(EXTRA_MESSAGE6, message_saber4); // cada cuanto ahorra?
                                               startActivity(i);
                                           }

                                       });
                               AlertDialog titulo = alerta.create();
                               titulo.setTitle("¿Como Ahorrar Agua?");
                               titulo.show();


                           }
                       });
                       variables_gas.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               AlertDialog.Builder alerta = new AlertDialog.Builder(Consejos_Ahorro.this);
                               alerta.setMessage(msg_gas)
                                       .setCancelable(false)
                                       .setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {
                                               dialog.cancel();
                                           }
                                       })
                                       .setPositiveButton("Conocer más", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {
                                               Intent i = new Intent(getApplicationContext(), saber_mas.class);
                                               Intent intent = getIntent();
                                               String message_saber = intent.getStringExtra(MainPagelogin.EXTRA_MESSAGE3);
                                               String message_saber3 = monto_ahorrado;
                                               String message_saber4 = Integer.toString(Mariana);
                                               String message_saber2 =  "gas";
                                               i.putExtra(EXTRA_MESSAGE3, message_saber); //identificador de usuario
                                               i.putExtra(EXTRA_MESSAGE4, message_saber2); // gas
                                               i.putExtra(EXTRA_MESSAGE5, message_saber3); // cuanto ahorra?
                                               i.putExtra(EXTRA_MESSAGE6, message_saber4); // cada cuanto ahorra?
                                               startActivity(i);
                                           }

                                       });
                               AlertDialog titulo = alerta.create();
                               titulo.setTitle("¿Como Ahorrar Gas?");
                               titulo.show();


                           }
                       });
                       varibales_comida.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               AlertDialog.Builder alerta = new AlertDialog.Builder(Consejos_Ahorro.this);
                               alerta.setMessage(msg_comida)
                                       .setCancelable(false)
                                       .setNegativeButton("Conocer más", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {
                                               Intent i = new Intent(getApplicationContext(), saber_mas.class);
                                               Intent intent = getIntent();
                                               String message_saber = intent.getStringExtra(MainPagelogin.EXTRA_MESSAGE3);
                                               String message_saber3 = monto_ahorrado;
                                               String message_saber4 = Integer.toString(Mariana);
                                               String message_saber2 =  "comida";
                                               i.putExtra(EXTRA_MESSAGE3, message_saber); //identificador de usuario
                                               i.putExtra(EXTRA_MESSAGE4, message_saber2); // gas
                                               i.putExtra(EXTRA_MESSAGE5, message_saber3); // cuanto ahorra?
                                               i.putExtra(EXTRA_MESSAGE6, message_saber4); // cada cuanto ahorra?
                                               startActivity(i);
                                           }
                                       })
                                       .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {
                                               dialog.cancel();
                                           }

                                       });
                               AlertDialog titulo = alerta.create();
                               titulo.setTitle("¿Como Ahorrar en Alimentos?");
                               titulo.show();


                           }
                       });
                       variables_prestamo.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               AlertDialog.Builder alerta = new AlertDialog.Builder(Consejos_Ahorro.this);
                               alerta.setMessage(msg_prestamo)
                                       .setCancelable(false)
                                       .setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {
                                               dialog.cancel();
                                           }
                                       })
                                       .setPositiveButton("Conocer más", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {
                                               Intent i = new Intent(getApplicationContext(), saber_mas.class);
                                               Intent intent = getIntent();
                                               String message_saber = intent.getStringExtra(MainPagelogin.EXTRA_MESSAGE3);
                                               String message_saber3 = monto_ahorrado;
                                               String message_saber4 = Integer.toString(Mariana);
                                               String message_saber2 =  "prestamo";
                                               i.putExtra(EXTRA_MESSAGE3, message_saber); //identificador de usuario
                                               i.putExtra(EXTRA_MESSAGE4, message_saber2); // gas
                                               i.putExtra(EXTRA_MESSAGE5, message_saber3); // cuanto ahorra?
                                               i.putExtra(EXTRA_MESSAGE6, message_saber4); // cada cuanto ahorra?
                                               startActivity(i);
                                           }

                                       });
                               AlertDialog titulo = alerta.create();
                               titulo.setTitle("¿Como Ahorrar en Prestamos?");
                               titulo.show();


                           }
                       });
                       variables_transporte.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               AlertDialog.Builder alerta = new AlertDialog.Builder(Consejos_Ahorro.this);
                               alerta.setMessage(msg_transporte)
                                       .setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {
                                               dialog.cancel();
                                           }
                                       })
                                       .setCancelable(false)
                                       .setPositiveButton("Conocer más", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {
                                               Intent i = new Intent(getApplicationContext(), saber_mas.class);
                                               Intent intent = getIntent();
                                               String message_saber = intent.getStringExtra(MainPagelogin.EXTRA_MESSAGE3);
                                               String message_saber3 = monto_ahorrado;
                                               String message_saber4 = Integer.toString(Mariana);
                                               String message_saber2 =  "transporte";
                                               i.putExtra(EXTRA_MESSAGE3, message_saber); //identificador de usuario
                                               i.putExtra(EXTRA_MESSAGE4, message_saber2); // gas
                                               i.putExtra(EXTRA_MESSAGE5, message_saber3); // cuanto ahorra?
                                               i.putExtra(EXTRA_MESSAGE6, message_saber4); // cada cuanto ahorra?
                                               startActivity(i);
                                           }

                                       });
                               AlertDialog titulo = alerta.create();
                               titulo.setTitle("¿Como Ahorrar en Transporte?");
                               titulo.show();


                           }
                       });
                   }else{
                       Toast.makeText(Consejos_Ahorro.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                   }

               }

           }





    private void info() {
        new ahorro().execute();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consejos__ahorro);
        info();

    }

}
