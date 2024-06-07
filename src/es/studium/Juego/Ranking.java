package es.studium.Juego;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.TextArea;

public class Ranking extends Frame {
    private static final long serialVersionUID = 1L;
    TextArea txaRanking = new TextArea(20, 10);  //Tamaño inicial 20 filas y 10 columnas
    Button btnVolver = new Button("Volver");
    Modelo modelo = new Modelo();

    public Ranking() 
    {
        setTitle("4 en Raya: Ranking");
        setBackground(Color.BLUE);
        setLayout(new BorderLayout());
        txaRanking.append("Nombre\t\tVictorias\t\tDerrotas\n");
        add(txaRanking, "Center");
        add(btnVolver, "South");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void ConsultarRanking() {
        if (modelo.conectar()) {
            txaRanking.append(modelo.consultarRanking()); //Llamo al método consultarRanking y añado el contenido
            modelo.desconectar();
        } else {
            txaRanking.append("Error al conectar con la base de datos.");
        }
        txaRanking.setEditable(false); // Para bloquear la escritura en el TextArea
    }

    public void MostrarRanking() {
        this.setVisible(true);
    }

    public void OcultarRanking() {
        this.setVisible(false);
    }
}
	
