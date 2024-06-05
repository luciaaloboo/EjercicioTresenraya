package com.example.ejerciciotresenraya;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int jugadores;
    private int[] CASILLAS;
    private Partida partida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CASILLAS = new int[9];
        CASILLAS[0] = R.id.a1;
        CASILLAS[1] = R.id.a2;
        CASILLAS[2] = R.id.a3;
        CASILLAS[3] = R.id.b1;
        CASILLAS[4] = R.id.b2;
        CASILLAS[5] = R.id.b3;
        CASILLAS[6] = R.id.c1;
        CASILLAS[7] = R.id.c2;
        CASILLAS[8] = R.id.c3;
    }

    public void aJugar(View vista) {
        ImageView imagen;

        for (int cadaCasilla : CASILLAS) {
            imagen = findViewById(cadaCasilla);
            imagen.setImageResource(R.drawable.casilla);
        }

        jugadores = 1;
        if (vista.getId() == R.id.dosjug) {
            jugadores = 2;
        }

        RadioGroup configDificultad = findViewById(R.id.configD);

        int id = configDificultad.getCheckedRadioButtonId();
        int dificultad = 0;

        if (id == R.id.normal) {
            dificultad = 1;
        } else if (id == R.id.imposible) {
            dificultad = 2;
        }

        partida = new Partida(dificultad);
        findViewById(R.id.unjug).setEnabled(false);
        findViewById(R.id.configD).setAlpha(0);
        findViewById(R.id.dosjug).setEnabled(false);

        if (jugadores == 2) {
            partida.marcar(0, 1); // Casilla 0 para que el jugador sea círculo
        }
    }

    public void toque(View mivista) {
        if (partida == null) {
            return;
        }

        int casilla = 0;
        for (int i = 0; i < 9; i++) {
            if (CASILLAS[i] == mivista.getId()) {
                casilla = i;
                break;
            }
        }

        if (!partida.comprueba_casilla(casilla)) {
            return;
        }

        marca(casilla);

        int resultado = partida.turno();
        if (resultado > 0) {
            termina(resultado);
            return;
        }

        if (jugadores == 1) {
            casilla = partida.ia();
            while (!partida.comprueba_casilla(casilla)) {
                casilla = partida.ia();
            }

            marca(casilla);
            resultado = partida.turno();
            if (resultado > 0) {
                termina(resultado);
            }
        }
    }

    private void termina(int resultado) {
        String mensaje;
        if (resultado == 1) mensaje = "Ganan los círculos";
        else if (resultado == 2) mensaje = "Ganan las aspas";
        else mensaje = "Empate";

        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        partida = null;
        findViewById(R.id.unjug).setEnabled(true);
        findViewById(R.id.configD).setAlpha(1);
        findViewById(R.id.dosjug).setEnabled(true);
    }

    private void marca(int casilla) {
        ImageView imagen = findViewById(CASILLAS[casilla]);
        int marca = partida.obtieneMarca(casilla);
        imagen.setImageResource(marca);
        partida.marcar(casilla, partida.jugador);
    }
}
