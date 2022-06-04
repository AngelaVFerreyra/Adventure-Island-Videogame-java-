package adventureisland;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;


public class Ranking extends JFrame{
    //Variables para tomar_datos
    Button b_aceptar; 
    TextField tf1;   
    
    String[] ranking;
    
    private Image imagen_fondo;
    
    public void Ranking() throws FileNotFoundException, IOException{}

    public void cargar(String nombre, int puntaje){
        FileWriter archivo = null;
        PrintWriter pw = null;
        try{
            archivo = new FileWriter("Ranking\\Ranking.dat", true);
            pw = new PrintWriter(archivo);
            
            pw.write(puntaje + " " + nombre + "\n");
            

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
                if (null != archivo){
                    archivo.close();
                }
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
    
    public void ordenar(){
        String linea = null;
        String[] datos = null;
        java.util.List<Jugador> jugadores = new ArrayList<Jugador>();
        Jugador jugador = null;
        try{
            BufferedReader br = new BufferedReader(new FileReader("Ranking\\Ranking.dat")); 
            while((linea=br.readLine())!=null){
                datos = linea.split(" ");
                jugador = new Jugador(datos[1], Integer.parseInt(datos[0]));
                jugadores.add(jugador);
            }
            br.close();

            Collections.sort(jugadores);


            FileWriter writer = new FileWriter("Ranking\\Ranking.dat"); 
            for(int i = 1; i <= 10; i++){
                writer.write(jugadores.get(jugadores.size()-i).getPuntajeJugador()+ " " + jugadores.get(jugadores.size()-i).getNombre() + System.lineSeparator());
            }
            writer.close();
        }catch(IOException e){
          System.out.println(e);   
        }
    }
    
    public void display(Graphics2D g2) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        imagen_fondo = toolkit.getImage("recursos\\ranking.png"); //120xx
        g2.drawImage(imagen_fondo, -60, 0, imagen_fondo.getWidth(null)*2, imagen_fondo.getHeight(null)*2, null);
        g2.setColor(Color.lightGray);
        g2.setFont( new Font( "Calibri", Font.PLAIN, 20) );
        
        String linea = null;
        String[] datos = null;

        try{
            BufferedReader br = new BufferedReader(new FileReader("Ranking\\Ranking.dat")); 
            int i = 30;
            while((linea=br.readLine())!=null){
                datos = linea.split(" ");
                Jugador nJugador = new Jugador();
                nJugador.setNombre(datos[1]);
                nJugador.setPuntajeJugador(Integer.parseInt(datos[0]));
                g2.drawString(nJugador.getMPuntos(6, " "), 200, 150 + i);
                g2.drawString(nJugador.getNombre(), 430, 150 + i);
                i = i + 30;
            }
            br.close();
        }catch(IOException e){
          System.out.println(e);   
        }
        
    }
}

