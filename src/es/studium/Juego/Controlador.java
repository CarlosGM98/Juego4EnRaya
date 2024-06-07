package es.studium.Juego;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Controlador implements WindowListener, ActionListener
{
	VistaPrincipal vistaPrincipal;
	PartidaNueva vistaPartidaNueva = new PartidaNueva();
	Ranking vistaRanking = new Ranking();
	Modelo modelo = new Modelo();
	Tablero tablero = new Tablero();
	private Button[][] botonesTablero;
	private Label turnoLabel;
	private int turno; // 0 para jugador 1, 1 para jugador 2
	private String jugador1;
	private String jugador2;
	private String ganador = "";
	private String perdedor = "";
	private String jugadorActual = "";
	// Dialogo para el final de la partida
	Dialog dlgFinPartida;
	Dialog dlgError = new Dialog(vistaPartidaNueva.ventana, "Error");
	Label lblError = new Label();

	public Controlador(Modelo m, VistaPrincipal v)
	{
		modelo = m;
		vistaPrincipal = v;
		vistaPrincipal.addWindowListener(this);
		vistaPrincipal.btnPartidaNueva.addActionListener(this);
		vistaPrincipal.btnRanking.addActionListener(this);
		vistaPrincipal.btnAyuda.addActionListener(this);
		vistaPrincipal.btnSalir.addActionListener(this);
		vistaRanking.addWindowListener(this);
		vistaRanking.btnVolver.addActionListener(this);
		vistaPartidaNueva.ventana.addWindowListener(this);
		vistaPartidaNueva.btnComenzarPartida.addActionListener(this);
		tablero.ventana.addWindowListener(this);
		dlgError.add(lblError);
	}

	private void inicializarTablero(Tablero tablero)
	{
		turno = 0;  //Establecemos que el turno inicial es el del jugador 1
		turnoLabel = new Label("Turno del jugador " + jugador1 + "(rojo)");

		// Obtener el tablero de botones del Tablero
		botonesTablero = tablero.getCasillas();

		for (int i = 0; i < 7; i++)
		{
			for (int j = 0; j < 7; j++)
			{
				botonesTablero[i][j].setBackground(Color.WHITE);
				botonesTablero[i][j].addActionListener(this);  //Añado ActionListener a cada botón
			}
		}
		// Agregar componentes al frame
		tablero.ventana.add(turnoLabel, BorderLayout.NORTH);  //Añado la etiqueta del turno arriba
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object botonPulsado = ae.getSource();  // Identifico que botón presionó el usuario

		if (botonPulsado.equals(vistaPrincipal.btnSalir))  //Para salir de la aplicación
		{
			System.exit(0);
		} else if (botonPulsado.equals(vistaPrincipal.btnRanking))  //Muestra la vista del Ranking
		{
			vistaRanking.MostrarRanking();
			vistaRanking.txaRanking.setText("");    // Se limpia y actualiza el área del texto
			vistaRanking.txaRanking.append("Nombre\t\tVictorias\t\tDerrotas\n");
			vistaRanking.ConsultarRanking();
			vistaPrincipal.setVisible(false);
		} else if (botonPulsado.equals(vistaRanking.btnVolver))  //Oculta el Ranking y volvemos al menú
		{
			vistaRanking.OcultarRanking();
			vistaPrincipal.setVisible(true);
		} else if (botonPulsado.equals(vistaPrincipal.btnAyuda))  //Muestra la ayuda
		{
			modelo.ayuda();
			vistaPrincipal.setVisible(true);
		} else if (botonPulsado.equals(vistaPrincipal.btnPartidaNueva))  //Muestra la vista de partida nueva
		{
			vistaPartidaNueva.MostrarVentana();
			vistaPrincipal.setVisible(false);
		} else if (botonPulsado.equals(vistaPartidaNueva.btnComenzarPartida))
		{                //Valido los nombres de los jugadoes y si son válidos empiezo la partida
			jugador1 = vistaPartidaNueva.txfNombre1.getText();  //Obtengo el nombre de los jugadores
			jugador2 = vistaPartidaNueva.txfNombre2.getText();
			
			if (jugador1.equals("") || jugador2.equals(""))  // Verifico si los campos están vacios
			{
				lblError.setText("Los campos no pueden estar vacíos");
				dlgError.setLayout(new FlowLayout());
				dlgError.addWindowListener(this);
				dlgError.setSize(250, 70);
				dlgError.setResizable(false);
				dlgError.setLocationRelativeTo(null);
				dlgError.setVisible(true);
			} else if (jugador1.equals(jugador2))  //Verifico si tienen el mismo nombre
			{
				lblError.setText("Los jugadores no pueden tener el mismo nombre");
				dlgError.setLayout(new FlowLayout());
				dlgError.addWindowListener(this);
				dlgError.setSize(300, 70);
				dlgError.setResizable(false);
				dlgError.setLocationRelativeTo(null);
				dlgError.setVisible(true);
			} else   // Si no salta error:
			{
				modelo.conectar(); // Para registrar los nombres en la BD
				modelo.comprobarJugador(jugador1);
				modelo.comprobarJugador(jugador2);
				modelo.desconectar();
				tablero = new Tablero();
				tablero.ventana.addWindowListener(this);
				inicializarTablero(tablero);
				tablero.MostrarVentana();
				vistaPartidaNueva.OcultarVentana();
			}
		} else
		{
			// Lógica de Juego
			Button botonPresionado = (Button) ae.getSource(); //Obtenemos el botón presionado
			int columna = -1;    //Recorremos el tablero para encontrar la columna en la que se encuentra el
			for (int i = 0; i < 7; i++)  //botón presionado. 
			{
				for (int j = 0; j < 7; j++)
				{
					if (botonesTablero[i][j] == botonPresionado)
					{
						columna = j;   //Si lo encuentra le asigno valor a la variable columna
						break;
					}
				}
			}
			if (columna != -1)   //Si la columna está vacia se sigue con la lógica del juego
			{
				int fila = obtenerFilaVacia(columna);  // Llamo al método (obtenerFilaVacia) que devuelve la primera 
				if (fila != -1)           // fila vacía en la columna seleccionada. Recorro la columna desde abajo 
				{                         // hacia arriba hasta encontrar una casilla vacía.
					botonesTablero[fila][columna].setBackground(turno == 0 ? Color.RED : Color.YELLOW);
					if (verificarVictoria(fila, columna))  //Actualizo la casilla del color del turno del jugador
					{   // Si la jugada resulta en victoria:
						// Me da el nombre del ganador
						if (turno == 0)
						{
							ganador = jugador1;
							perdedor = jugador2;
						} else
						{
							ganador = jugador2;
							perdedor = jugador1;
						}
						modelo.conectar();
						modelo.sumarVictoria(ganador);   // Se actualiza el Ranking
						modelo.sumarDerrota(perdedor);
						modelo.desconectar();
						dlgFinPartida = new Dialog(tablero.ventana, "¡El jugador " + ganador + " gana!", true);
						dlgFinPartida.setLayout(new FlowLayout());
						dlgFinPartida.addWindowListener(this);
						dlgFinPartida.setSize(250, 70);
						dlgFinPartida.setResizable(false);
						dlgFinPartida.setLocationRelativeTo(null);
						dlgFinPartida.setVisible(true);
					} else if (verificarEmpate())
					{
						dlgFinPartida = new Dialog(tablero.ventana, "¡Empate!", true);
						dlgFinPartida.setLayout(new FlowLayout());
						dlgFinPartida.addWindowListener(this);
						dlgFinPartida.setSize(250, 70);
						dlgFinPartida.setResizable(false);
						dlgFinPartida.setLocationRelativeTo(null);
						dlgFinPartida.setVisible(true);
					} else
					{    // Si no hay victoria ni empate se cambia el turno
						turno = (turno + 1) % 2;  // El turno alterna entre 0 y 1
						// Para ver el nombre mientras se juega
						if (turno == 0)
						{
							jugadorActual = jugador1;
						} else
						{
							jugadorActual = jugador2;
						}
						turnoLabel.setText(
								"Turno del jugador " + (jugadorActual) + (turno == 0 ? " (rojo)" : " (amarillo)"));
					}               // Si turno = 0: da rojo, si turno = 1, amarillo
				}
			}
		}
	}

	private int obtenerFilaVacia(int columna)
	{
		for (int i = 6; i >= 0; i--)  //Recorro las filas de la columna dada de abajo hacia arriba
		{
			if (botonesTablero[i][columna].getBackground().equals(Color.WHITE)) //Verifico si esta vacia
			{
				return i;  // Si lo está, me da el índice de esa fila
			}
		}
		return -1;  // Si no hay celda vacia me devuelve -1, no hay espacio
	}

	private boolean verificarVictoria(int fila, int columna)  // Para verificar 4 casillas juntas del mismo color
	{
		// Comprobación horizontal
		int contador = 0;
		for (int j = 0; j < 7; j++)  //Recorro la fila
		{
			if (botonesTablero[fila][j].getBackground().equals(botonesTablero[fila][columna].getBackground()))
			{ // Si el color de la celda actual es el mismo que el de la ficha recién colocada, incrementa contador.
				contador++;
				if (contador == 4)  // SI el contador llega a 4, es victoria
					return true;
			} else
			{
				contador = 0;
			}
		}

		// Comprobación vertical
		contador = 0;
		for (int i = 0; i < 7; i++)
		{
			if (botonesTablero[i][columna].getBackground().equals(botonesTablero[fila][columna].getBackground()))
			{
				contador++;
				if (contador == 4)
					return true;
			} else
			{
				contador = 0;
			}
		}

		// Comprobación diagonal principal
		contador = 0;
		for (int i = -3; i <= 3; i++) // Se recorre en diagoanl
		{							// Se comprueba que está en los límites del tablero
			if (fila + i >= 0 && fila + i < 7 && columna + i >= 0 && columna + i < 7)
			{
				if (botonesTablero[fila + i][columna + i].getBackground()
						.equals(botonesTablero[fila][columna].getBackground()))
				{
					contador++;
					if (contador == 4)
						return true;
				} else
				{
					contador = 0;
				}
			}
		}

		// Comprobación de la otra diagonal
		contador = 0;
		for (int i = -3; i <= 3; i++)
		{
			if (fila + i >= 0 && fila + i < 7 && columna - i >= 0 && columna - i < 7)
			{
				if (botonesTablero[fila + i][columna - i].getBackground()
						.equals(botonesTablero[fila][columna].getBackground()))
				{
					contador++;
					if (contador == 4)
						return true;
				} else
				{
					contador = 0;
				}
			}
		}

		return false;
	}

	private boolean verificarEmpate()
	{
		for (int i = 0; i < 7; i++)
		{
			for (int j = 0; j < 7; j++)
			{							// Si hay casillas vacias no ha termiando
				if (botonesTablero[i][j].getBackground().equals(Color.WHITE))
				{
					return false;
				}
			}
		}
		return true;  //Tablero lleno
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		if (e.getSource() == vistaRanking)
		{
			vistaRanking.OcultarRanking();
			vistaPrincipal.setVisible(true);
		} else if (e.getSource() == vistaPartidaNueva.ventana)
		{
			vistaPartidaNueva.OcultarVentana();
			vistaPrincipal.setVisible(true);
		} else if (e.getSource() == dlgError)
		{
			dlgError.setVisible(false);
		} else if (e.getSource() == tablero.ventana)
		{
			tablero.OcultarVentana();
			vistaPrincipal.setVisible(true);
		} else if (e.getSource() == dlgFinPartida)
		{
			dlgFinPartida.setVisible(false);
			tablero.OcultarVentana();
			vistaPrincipal.setVisible(true);
		} else
		{
			System.exit(0);
		}
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
	}
}
