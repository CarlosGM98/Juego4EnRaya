package es.studium.Juego;

import java.awt.*;

public class Tablero {
    Frame ventana = new Frame("4 en Raya");
    Button[][] casillas;

    public Tablero() {
        // Crear el panel del tablero
        Panel panelTablero = new Panel();  // Creamos un panel
        panelTablero.setLayout(new GridLayout(7, 7));    // Se establece 7 x 7
        casillas = new Button[7][7];  // Se crea la matríz de botones 7 x 7

        // Crear y añadir botones al panel del tablero
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                casillas[i][j] = new Button();     // Se crea un nuevo botón para cada posición en la matriz
                panelTablero.add(casillas[i][j]);  // Se añade cada botón al tablero
            }
        }

        ventana.add(panelTablero);
        ventana.setSize(500, 500);
        ventana.setLocationRelativeTo(null); // Centrar la ventana
		ventana.setResizable(false); // Evitar redimensionado
    }

    public void MostrarVentana() {
        ventana.setVisible(true);
    }

    public void OcultarVentana() {
        ventana.setVisible(false);
    }

    public Button[][] getCasillas() {   //Devuelve la matriz de botones
        return casillas;
    }
}
    
    




