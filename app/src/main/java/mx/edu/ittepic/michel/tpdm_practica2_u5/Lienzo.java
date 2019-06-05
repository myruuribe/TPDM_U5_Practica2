package mx.edu.ittepic.michel.tpdm_practica2_u5;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.view.View;

public class Lienzo extends View {
    CountDownTimer timer;
    Context context;
    public int numeroObjetoLocal = 0;
    public int numeroObjetoContrario = 0;

    public Lienzo(Context context) {
        super(context);
        this.context = context;
        timer=new CountDownTimer(1500,60) {
            @Override
            public void onTick(long l) {
                invalidate();
            }

            @Override
            public void onFinish() {
                timer.start();
            }
        }.start();
    }

    @Override
    public void onDraw(Canvas canvas){
        mostrarJugadorLocal(canvas);
        mostrarJugadorContrario(canvas);
    }

    public void mostrarJugadorLocal(Canvas canvas){
        Bitmap img = BitmapFactory.decodeResource(context.getResources(),R.drawable.papel);
        Paint p=new Paint();
        switch (numeroObjetoLocal){
            case 1:
                img = BitmapFactory.decodeResource(context.getResources(),R.drawable.papel);
            break;
            case 2:
                img = BitmapFactory.decodeResource(context.getResources(),R.drawable.piedra);
            break;
            case 3:
                img = BitmapFactory.decodeResource(context.getResources(),R.drawable.tijera);
            break;
        }

        canvas.drawBitmap(img,0,0, p);
    }

    public void mostrarJugadorContrario(Canvas canvas){
        Bitmap img = BitmapFactory.decodeResource(context.getResources(),R.drawable.papel);
        Paint p=new Paint();
        switch (numeroObjetoContrario){
            case 1:
                img = BitmapFactory.decodeResource(context.getResources(),R.drawable.papel);
                break;
            case 2:
                img = BitmapFactory.decodeResource(context.getResources(),R.drawable.piedra);
                break;
            case 3:
                img = BitmapFactory.decodeResource(context.getResources(),R.drawable.tijera);
                break;
        }

        canvas.drawBitmap(img,0,800, p);
    }
}
