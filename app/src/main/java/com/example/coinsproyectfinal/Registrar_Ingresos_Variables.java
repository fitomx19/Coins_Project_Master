package com.example.coinsproyectfinal;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class Registrar_Ingresos_Variables {
    public static final String URL_Registrar_Ingresos_Variables="http://192.168.1.67:8080/Coins_Proyect/Android/IngresoExtraAndroid.jsp";
    public static void showWarning(AppCompatActivity cmp,String msg){
        AlertDialog.Builder a = new AlertDialog.Builder(cmp);
        a.setTitle("Warning");

        a.setMessage(msg);
        a.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        a.show();
    }
}
