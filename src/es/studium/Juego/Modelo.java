package es.studium.Juego;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Modelo
{
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/cuatroenraya";
	String login = "root";
	String password = "Studium2023;";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	
	public boolean conectar()
	{
		boolean conexionCorrecta = true;
		//Cargar el Driver
		try
		{
			Class.forName(driver);
		} catch (ClassNotFoundException e)
		{
			System.out.println("Se ha producido un error al cargar el Driver");
			conexionCorrecta = false;
		}
		//Establecer la conexión con la base dedatos
		try
		{
			connection = DriverManager.getConnection(url, login, password);
		} catch (SQLException e)
		{
			System.out.println("Se produjo un error al conectar a la Base de Datos");
			conexionCorrecta = false;
		}
		return conexionCorrecta;
	}
	
	
	public void desconectar()
	{
		try
		{
			statement.close();
			connection.close();
		} catch (SQLException e)
		{
			System.out.println("Error al cerrar " + e.toString());
		}
	}
	
	// Consulta SQL para obtener el ranking de los jugadores ordenado por el número de victorias y 
	// nos devuelve los resultados como una cadena de texto.
	public String consultarRanking()
	{
		String contenido = "";
		String sentencia = "SELECT * FROM jugador ORDER BY numeroVictoriasJugador DESC";                      
		try
		{
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			rs = statement.executeQuery(sentencia); 
			while (rs.next())													
			{
				contenido = contenido + rs.getString("nombreJugador") + "\t\t";
				contenido = contenido + rs.getString("numeroVictoriasJugador") + "\t\t";
				contenido = contenido + rs.getString("numeroDerrotasJugador") + "\n";
			}
		} catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL:" + e.toString());
		}
		return contenido;
	}
	
	// Método que comprueba si el jugador se encuentra en la base de datos, si no se encuentra, se añade
	public void comprobarJugador(String nombre)  //Parámetro nombre
	{
		String sentencia = "SELECT * FROM cuatroenraya.jugador WHERE nombreJugador = '"+ nombre +"'";
		try
		{
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			rs = statement.executeQuery(sentencia); 
			if (!rs.next())  // Si no se encuentra, se añade
			{
				Statement statement2 = null;   //Creo un nuevo statement
				statement2 = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				sentencia = "INSERT INTO cuatroenraya.jugador VALUES (null, '"+ nombre +"', 0, 0)";
				statement2.executeUpdate(sentencia);
			}
		} catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL:" + e.toString());
		}
	}
	
	
	public void sumarVictoria(String ganador)
	{
		// Sentencia SQL para seleccionar todos los campos de la tabla jugador donde el nombre 
		// del jugador coincide con el parámetro ganador.
		String sentencia = "SELECT * FROM cuatroenraya.jugador WHERE nombreJugador = '"+ ganador +"'";
		try
		{
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			rs = statement.executeQuery(sentencia); 
			if (rs.next())
			{
				int id = rs.getInt("idJugador"); //Verificamos si el jugador existe y obtenemos su id
				Statement statement2 = null;   //Creo un nuevo statement
				statement2 = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				sentencia = "UPDATE jugador SET numeroVictoriasJugador = numeroVictoriasJugador + 1 WHERE idJugador = "+ id +";";
				statement2.executeUpdate(sentencia);
			}
		} catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL:" + e.toString());
		}
	}
	
	
	public void sumarDerrota(String perdedor)
	{
		String sentencia = "SELECT * FROM cuatroenraya.jugador WHERE nombreJugador = '"+ perdedor +"'";
		try
		{
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			rs = statement.executeQuery(sentencia); 
			if (rs.next())
			{
				int id = rs.getInt("idJugador");
				Statement statement2 = null;
				statement2 = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				sentencia = "UPDATE jugador SET numeroDerrotasJugador = numeroDerrotasJugador + 1 WHERE idJugador = "+ id +";";
				statement2.executeUpdate(sentencia);
			}
		} catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL:" + e.toString());
		}
	}	
	
	public void ayuda()
	{
		try  
		{ 
		Runtime.getRuntime().exec("hh.exe AyudaJuego.chm"); 
		} 
		catch (IOException e)  
		{ 
		e.printStackTrace(); 
		} 
	}
}