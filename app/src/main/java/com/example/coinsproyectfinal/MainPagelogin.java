package com.example.coinsproyectfinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.callback.OnPieSelectListener;
import com.razerdp.widget.animatedpieview.data.IPieInfo;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MainPagelogin extends AppCompatActivity {
    private String hiduke="";
    private String price="";
    private String errmsg="";
    static String please;
    private TableLayout tabla;
    private int id_fila,num_celda=1;


   private Button what_it_is;
   private Button what_it_is2;
   private Button variables;

    private static final String url = "jdbc:mysql://protocoins.mysql.database.azure.com:3306/coinsproyect";
    public static final String EXTRA_MESSAGE3 = "com.example.myfirstapp.MESSAGE3";

   //

    private class MyTask3 extends AsyncTask<Void,Void,Void> {
        private String IPsw="",IName="",name="";
        private int id;
        boolean succes = false;
        TextView usr = findViewById(R.id.textView);
        private String luz,agua,gas,comida,transporte,prestamos,gastosextra;
        private String monto,ivsg;
        private int mount;

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection c = DriverManager.getConnection(url, "royalmazan@protocoins", "n0m3l0s3.");

                Statement stmt = c.createStatement();

                name = usr.getText().toString();


                 ResultSet rset = stmt.executeQuery("SELECT * FROM usuario where id_usr='"+name+"'");

                if (rset.next()){
                    IPsw = rset.getString(8);   //Nombre de la meta
                    please = IPsw;
                    succes = true;
                    rset = stmt.executeQuery("call gastosfijos ('"+name+"')");
                    if(rset.next()){
                        //desglose de los gastos fijos
                        //TOCA DESMENUZAR EL ASYNTASK 4 PARA HACER TODOm EN LA MISMA ASYNTASK XD
                        luz = rset.getString(1);   //ojito
                        agua = rset.getString(2);
                        gas = rset.getString(3);
                        comida = rset.getString(4);
                        prestamos = rset.getString(5);
                        transporte = rset.getString(6);
                        //suma de gastos
                        gastosextra = rset.getString(7);
                    rset = stmt.executeQuery("call monto ('"+name+"')");
                    rset.next();
                     monto = rset.getString(1);
                        rset = stmt.executeQuery("call ingresosvsgastos ('"+name+"')");
                        rset.next();
                        ivsg = rset.getString(1);
                       //cerramos conexion ala bd
                        rset.close();
                        stmt.close();
                        c.close();
                        //cerramos la cerrada de base de datos

                    }//if anidado 1
                }//if principal
            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(MainPagelogin.this, "Error en el Servidor, Intente más tarde", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            super.onPostExecute(result);
            Toast.makeText(MainPagelogin.this, "Bienvenido de nuevo!", Toast.LENGTH_SHORT).show();
            TextView meta = findViewById(R.id.meta_ahorro);
            TextView light = findViewById(R.id.electricidad_tv);
            TextView water = findViewById(R.id.agua);
            TextView gasolina = findViewById(R.id.gas);
            TextView food = findViewById(R.id.comida);
            TextView lodge = findViewById(R.id.deudas);
            TextView transport = findViewById(R.id.transporte);
            TextView xt = findViewById(R.id.transporte123);


            //
            String bottle="";

            //ponemos en los contenedores el contenido de las consultas sql
            Double monto_int = Double.parseDouble(monto);
            if (monto_int <= 50){
                bottle = "te falta muy poco";
            }else if (monto_int <= 25){
                bottle = "estas a nada de conseguirlo!";
            }else  if(monto_int >=51){
                bottle = "continua ahorrando";
            }else if (monto_int == 0){
                Toast.makeText(MainPagelogin.this, "Felicidades, cumpliste tu meta de ahorro genera una nueva meta ahora mismo", Toast.LENGTH_LONG).show();
            }
            meta.setText("Tu meta es" + " " + IPsw + " y te falta el " + monto_int + "% de tu meta " + bottle);
            light.setText(luz);
            water.setText(agua);
            gasolina.setText(gas);
            food.setText(comida);
            lodge.setText(prestamos);
            xt.setText(gastosextra);
            transport.setText(transporte);


            Double luzd = Double.parseDouble(luz);
            Double waterd = Double.parseDouble(agua);
            Double gasolinad = Double.parseDouble(gas);
            Double foodd = Double.parseDouble(comida);
            Double lodged = Double.parseDouble(prestamos);
            Double ganancia = Double.parseDouble(ivsg);
            Double transportedd = Double.parseDouble(transporte);
            //          Double xtd = Double.parseDouble(transporte);
            // Double ix = Double.parseDouble(ingresosx);
            Double gx = Double.parseDouble(gastosextra);

            //aqui aparentemente pondremos la grafiquini
            // Actualizacio: Por cierto pense que no se podria jajaja pero aparentemente si se pudo xD
            AnimatedPieView mAnimatedPieView = findViewById(R.id.animatedpieview);
            AnimatedPieViewConfig config = new AnimatedPieViewConfig();
            config.startAngle(-20)// Starting angle offset
                    .addData(new SimplePieInfo(luzd, Color.parseColor("#B30C1B"), "Energia Electrica"))//Data (bean that implements the IPieInfo interface)
                    .addData(new SimplePieInfo(waterd, Color.parseColor("#FF4F5E"), "Agua Potable")).drawText(true).strokeMode(false).canTouch(true).splitAngle(1)
                    .addData(new SimplePieInfo(gasolinad, Color.parseColor("#FF7665"), "Gas LP y Natural")).autoSize(true)
                    .addData(new SimplePieInfo(foodd, Color.parseColor("#B30C7B"), "Alimentos y Bebidas")).pieRadius(160)
                    .addData(new SimplePieInfo(lodged, Color.parseColor("#FF45C1"), "Prestamos y Deudas")).textMargin(8)
                    .addData(new SimplePieInfo(transportedd, Color.parseColor("#F34EFF"), "Transporte y Gasolina"))
                    .selectListener(new OnPieSelectListener<IPieInfo>() {
                        @Override
                        public void onSelectPie(@NonNull IPieInfo pieInfo, boolean isFloatUp) {
                            Toast.makeText(MainPagelogin.this, pieInfo.getDesc() + " - " + "$" + pieInfo.getValue() + " mnx", Toast.LENGTH_SHORT).show();
                        }
                    })


                    .duration(2000).textSize(20);// draw pie animation duration


            mAnimatedPieView.applyConfig(config);
            mAnimatedPieView.start();

            //segunda tabla de proyecciones pero ahora en forma de tabla
            if(ganancia >= 0 ){
                AnimatedPieView mAnimatedPieView2 = findViewById(R.id.animatedpieview2);
                AnimatedPieViewConfig config2 = new AnimatedPieViewConfig();
                config2.startAngle(-20)// Starting angle offset
                        .addData(new SimplePieInfo(luzd, Color.parseColor("#B30C1B"), "Energia Electrica"))//Data (bean that implements the IPieInfo interface)
                        .addData(new SimplePieInfo(waterd, Color.parseColor("#FF4F5E"), "Agua Potable")).drawText(true).strokeMode(false).canTouch(true).splitAngle(1)
                        .addData(new SimplePieInfo(gasolinad, Color.parseColor("#FF7665"), "Gas LP y Natural")).autoSize(true)
                        .addData(new SimplePieInfo(foodd, Color.parseColor("#B30C7B"), "Alimentos y Bebidas")).pieRadius(160)
                        .addData(new SimplePieInfo(lodged, Color.parseColor("#FF45C1"), "Prestamos y Deudas")).textMargin(8)
                        .addData(new SimplePieInfo(transportedd, Color.parseColor("#F34EFF"), "Transporte y Gasolina"))
                        .addData(new SimplePieInfo(ganancia, Color.parseColor("#24248f"), "Ingresos!"))
                        .selectListener(new OnPieSelectListener<IPieInfo>() {
                            @Override
                            public void onSelectPie(@NonNull IPieInfo pieInfo, boolean isFloatUp) {
                                Toast.makeText(MainPagelogin.this, pieInfo.getDesc() + " - " + "$" + pieInfo.getValue() + " mnx", Toast.LENGTH_SHORT).show();
                            }
                        })


                        .duration(2000).textSize(20);// draw pie animation duration


                mAnimatedPieView2.applyConfig(config2);
                mAnimatedPieView2.start();
            }
            else{
                Toast.makeText(MainPagelogin.this, "No podemos mostrar la tabla de Ingresos vs Gastos porque los ingresos son inferiores a los gastos", Toast.LENGTH_LONG).show();
            }


                }
            }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pagelogin);
        //metodo neptuniano para recuperar la informacion de las cajas de texto invisibles
        TextView nomusr1 = findViewById(R.id.nom_usr);
        TextView idusr1 = findViewById(R.id.textView);
        //fin del metodo neptuniando

        //metodo para recuperar la informacion de la ventana previa
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE); //nombre de usuario
        String message2 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2); //identificador de usuario
        String message_test = intent.getStringExtra(IngresosGastos.EXTRA); //identificador de usuario
        String id_usuario1;

        //probemos que funcione
        if(message2.equals(null)){
            id_usuario1 = message_test;
        }else{
            id_usuario1 = message;
        }

        idusr1.setText(id_usuario1);
        nomusr1.setText("Bienvenido" + " " +message2 + " "+ "disfruta la aplicacion!" );

        //fin del metodo para recuperar la informacion de la ventana previa
        info();
        //PRIMER BOTON DE AYUDA DE LA GRAFICA
        what_it_is = (Button) findViewById(R.id.WHAT1);
        what_it_is.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(MainPagelogin.this);
                alerta.setMessage("En esta grafica se muestran de forma detallada el porcentaje de cada rubo del total de gastos , esto es meramente informativo y muy variable dependiendo cada familia.")
                        .setCancelable(false)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }

                        });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("¿Como leer la grafica de gastos mensuales?");
                titulo.show();


            }
        });
        what_it_is2 = (Button) findViewById(R.id.WHAT2);
        what_it_is2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta1 = new AlertDialog.Builder(MainPagelogin.this);
                alerta1.setMessage("Pasa tu cursor por la seccion azul, esta muestra el resultado la resta de tus gastos fijos a tus ingresos fijos por otro lado se muestra el porcentaje de gasto de tu salario en cada rubro. No olvides visitar consejos de ahorro personalizados para tener una mejor vision de que hacer para reducir estos gastos!")
                        .setCancelable(false)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }

                        });
                AlertDialog titulo = alerta1.create();
                titulo.setTitle("¿Como leer la grafica de Ingresos vs Gastos?");
                titulo.show();


            }
        });
        variables = (Button) findViewById(R.id.iv_gv_btn);
        variables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta3 = new AlertDialog.Builder(MainPagelogin.this);
                alerta3.setMessage("Selecciona entre ingresar un gasto variable que es todo gasto que no este contemplado al inicio del mes y el ingreso variable que es todo ingreso que no este contemplado al inicio del mes")
                        .setCancelable(false)
                        .setPositiveButton("Ingreso Variable", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(getApplicationContext(), Ingresos_Variables.class);
                                TextView usr = findViewById(R.id.textView);
                                String message =  usr.getText().toString();
                                i.putExtra(EXTRA_MESSAGE3, message); //identificador de usuario
                                startActivity(i);

                            }
                        })
                        .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Gasto Variable", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(getApplicationContext(), gastos_variables.class);
                                TextView usr = findViewById(R.id.textView);
                                String message =  usr.getText().toString();
                                i.putExtra(EXTRA_MESSAGE3, message); //identificador de usuario
                                startActivity(i);
                            }
                        });

                AlertDialog titulo = alerta3.create();
                titulo.setTitle("¿Que operacion buscas realizar?");
                titulo.show();


            }
        });


    }


    private void info() {
            new MyTask3().execute();
    }

    public void actualizar(View view) {
        new MyTask3().execute();
    }

    public void IngresosGastos2(View view){
        Intent i = new Intent(getApplicationContext(), IngresosGastos.class);
        TextView usr = findViewById(R.id.textView);
        String message =  usr.getText().toString();
        i.putExtra(EXTRA_MESSAGE3, message); //identificador de usuario
        startActivity(i);
    }
    public void ahorro(View view){
        Intent i = new Intent(getApplicationContext(), Ahorro.class);
        TextView usr = findViewById(R.id.textView);
        String message =  usr.getText().toString();
        i.putExtra(EXTRA_MESSAGE3, message); //identificador de usuario
        startActivity(i);
    }
    public void consejos_ahorro(View view){
        Intent i = new Intent(getApplicationContext(), Consejos_Ahorro.class);
        TextView usr = findViewById(R.id.textView);
        String message =  usr.getText().toString();
        i.putExtra(EXTRA_MESSAGE3, message); //identificador de usuario
        startActivity(i);
    }





}
