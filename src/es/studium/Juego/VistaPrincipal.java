package es.studium.Juego;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;

public class VistaPrincipal extends Frame  //Hereda de frame, esta clase es una ventana
{
	private static final long serialVersionUID = 1L;
	Label lblMenuPrincipal = new Label("MENÚ PRINCIPAL", 1); // El 1 para que salga centrada
	Panel pnlBotonera = new Panel(); // Panel para botonera principal para organizar componentes
	Panel pnlBotoneraSalir = new Panel(); // Panel para botón Salir
	Button btnPartidaNueva = new Button("Partida Nueva");
	Button btnRanking = new Button("Ranking");
	Button btnAyuda = new Button("Ayuda");
	Button btnSalir = new Button("Salir");
	
	public VistaPrincipal()
	{
		setTitle("CUATRO EN RAYA"); // Título
		setBackground(Color.CYAN); // Color de fondo del Frame
		setLayout(new BorderLayout()); // Layout del Frame
		pnlBotonera.setLayout(new GridLayout(4,1)); // Layout del Panel
		pnlBotonera.add(lblMenuPrincipal); // Añadir botón a panel
		pnlBotonera.add(btnPartidaNueva); // Añadir botón a panel
		pnlBotonera.add(btnRanking); // Añadir botón a panel
		pnlBotonera.add(btnAyuda); // Añadir botón a panel
		add(pnlBotonera, "Center"); // Añadir Panel a Frame
		pnlBotoneraSalir.add(btnSalir); // Añadir botón a panel
		add(pnlBotoneraSalir, "South"); // Añadir Panel a Frame
		setSize(500,300); // Tamaño de Frame
		setLocationRelativeTo(null); // Centrar la ventana
		setResizable(false); // Evitar redimensionado
		MostrarPrincipal(); // Mostrarlo
	}
	
	public void MostrarPrincipal()
	{
		this.setVisible(true);
	}
	
	public void OcultarPrincipal()
	{
		this.setVisible(false);
	}
}
