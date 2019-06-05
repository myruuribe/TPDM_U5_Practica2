package mx.edu.ittepic.michel.tpdm_practica2_u5;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.spec.ECField;
import java.util.Random;

public class Juego extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager; //Variable tipo SensorManager
    private Sensor mAccelerometer; //Variable tipo Sensor
    boolean mover = false;
    Random rn = new Random();
    String telefonoActual = "";
    Lienzo lienzo;
    DatabaseReference serviciofirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lienzo = new Lienzo(this);
        setContentView(lienzo);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        serviciofirebase=FirebaseDatabase.getInstance().getReference();
        objetoMandado();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.values[0] < -5){
            mover = true;
        }

        if(event.values[0] > 5 && mover == true){
            int max = 3;
            int min = 1;
            Toast.makeText(Juego.this,"Mover",Toast.LENGTH_LONG).show();
            int numero = rn.nextInt(max - min + 1) + min;
            serviciofirebase.child("objeto").removeValue();
            serviciofirebase.child("objeto").push().setValue(numero).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Juego.this,"SE MANDO EL NUMERO",Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Juego.this,"ERROR",Toast.LENGTH_LONG).show();
                }
            });
            lienzo.numeroObjetoLocal = numero;
        }
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void objetoMandado(){
        serviciofirebase.child("objeto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                    String objeto = locationSnapshot.getValue().toString();
                    String[] objetoJuego = objeto.split("-");

                    try{
                        String telefono = objetoJuego[0];
                        int num = Integer.parseInt(objetoJuego[1]);
                        if(!telefonoActual.equals(telefono)){
                            lienzo.numeroObjetoContrario= num;
                        }

                    }
                    catch (Exception e){

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
