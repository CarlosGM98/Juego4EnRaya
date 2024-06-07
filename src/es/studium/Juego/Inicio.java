package es.studium.Juego;


public class Inicio
{

	public static void main(String[] args)
	{
		Modelo modelo = new Modelo();
		VistaPrincipal vistaPrincipal = new VistaPrincipal();
		new Controlador(modelo, vistaPrincipal);
	}
}
