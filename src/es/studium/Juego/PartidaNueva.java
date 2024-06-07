package es.studium.Juego;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;

public class PartidaNueva
{
	private static final long serialVersionUID = 1L;
	Frame ventana = new Frame("Cuatro en Raya: Nueva Partida");
	//Dialog pedirNombresJugadores = new Dialog(this, "Cuatro en Raya: Nueva Partida", true);
	
	String[] nombresJugadores = null;
	
	TextField txfNombre1 = new TextField(15);
	Label lblEtiqueta1 = new Label("Jugador 1:");
	TextField txfNombre2 = new TextField(15);
	Label lblEtiqueta2 = new Label("Jugador 2:");
	
	Button btnComenzarPartida = new Button("Comenzar Partida");
	
	public PartidaNueva()
	{
		ventana.setBackground(Color.CYAN); // Color de fondo del Dialog
		ventana.setLayout(new FlowLayout()); // Layout del Dialog
		ventana.setSize(240,200); // Tama�o de Dialog
		ventana.setLocationRelativeTo(null); // Centrar el Dialog
		ventana.setResizable(false); // Evitar redimensionado
		
		ventana.add(lblEtiqueta1);
		txfNombre1.selectAll(); // Reseteamos los cuadros de texto
		txfNombre1.setText("");
		ventana.add(txfNombre1);
		ventana.add(lblEtiqueta2);
		txfNombre2.selectAll(); // Reseteamos los cuadros de texto
		txfNombre2.setText("");
		ventana.add(txfNombre2);
		
		ventana.add(btnComenzarPartida);
		
	}
	
	public void MostrarVentana()
	{
		ventana.setVisible(true);
	}

	public void OcultarVentana()
	{
		ventana.setVisible(false);
	}
}
