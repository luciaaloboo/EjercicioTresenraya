package com.example.ejerciciotresenraya;

import java.util.Random;

public class Partida {

    public Partida(int dificultad) {
        this.dificultad = dificultad;
        jugador = 1;
        casillas = new int[9];
        for (int i = 0; i < 9; i++) {
            casillas[i] = 0;
        }
    }

    public boolean comprueba_casilla(int casilla) {
        if (casillas[casilla] != 0) {
            return false;
        } else {
            casillas[casilla] = jugador;
        }
        return true;
    }

    public void marcar(int casilla, int jugador) {
        casillas[casilla] = jugador;
    }

    public int turno() {
        boolean empate = true;
        for (int i = 0; i < COMBINACIONES.length; i++) {
            int[] combinacion = COMBINACIONES[i];
            int marca = casillas[combinacion[0]];
            if (marca != 0) {
                boolean tresEnRaya = true;
                for (int j = 1; j < combinacion.length; j++) {
                    if (casillas[combinacion[j]] != marca) {
                        tresEnRaya = false;
                        break;
                    }
                }
                if (tresEnRaya) {
                    return marca;
                }
            } else {
                empate = false;
            }
        }

        if (empate) {
            return 3;
        }

        jugador = (jugador == 1) ? 2 : 1; // Cambia el turno
        return 0; // Vuelve a 0 si no hay ganador
    }


    public int dosEnRaya(int jugador_en_turno) {
        int casilla = -1;
        int cuantas_lleva = 0;
        for (int i = 0; i < COMBINACIONES.length; i++) {
            for (int pos : COMBINACIONES[i]) {
                if (casillas[pos] == jugador_en_turno) cuantas_lleva++;
                if (casillas[pos] == 0) casilla = pos;
            }
            if (cuantas_lleva == 2 && casilla != -1) return casilla;
            casilla = -1;
            cuantas_lleva = 0;
        }
        return -1;
    }

    public int ia() {
        int casilla;
        casilla = dosEnRaya(2);
        if (casilla != -1) return casilla;
        if (dificultad > 0) {
            casilla = dosEnRaya(1);
            if (casilla != -1) return casilla;
        }

        if (dificultad == 2) {
            if (casillas[0] == 0) return 0;
            if (casillas[2] == 0) return 2;
            if (casillas[6] == 0) return 6;
            if (casillas[8] == 0) return 8;
        }
        Random casilla_azar = new Random();
        casilla = casilla_azar.nextInt(9);
        return casilla;
    }

    public final int dificultad;
    public int jugador;
    private int[] casillas;
    private final int[][] COMBINACIONES = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    public int obtieneMarca(int casilla) {
        if (casillas[casilla] == 1) {
            return R.drawable.circulo;
        } else if (casillas[casilla] == 2) {
            return R.drawable.cruz;
        } else {
            return R.drawable.casilla;
        }
    }
}

