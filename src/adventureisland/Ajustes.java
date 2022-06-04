package adventureisland;

import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Choice;
import java.awt.TextField;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Ajustes extends Frame implements ActionListener{
   private Panel p_teclas;
    private GridLayout lasTeclas;
    
    private Label l_pantalla;
    private Choice c_pantalla;
    private Label l_musica1;
    private Choice c_musica;
    private Label l_musica2;
    private Button b_musica;
    private Label l_sonido1;
    private Choice c_sonido;
    private Label l_sonido2;
    private Button b_sonido;
    private Label l_pausar;
    private Button b_pausar;
    private Label l_iniciar;
    private Button b_iniciar;
    private Label l_izquierda;
    private Button b_izquierda;
    private Label l_derecha;
    private Button b_derecha;
    private Label l_saltar;
    private Button b_saltar;
    private Label l_lanzar;
    private Button b_lanzar;
    private Label l_nombre;
    private TextField text_nombre;
    private Panel p_guardarReset;
    private Button b_guardar;
    private Button b_reset;
    

    private Properties appProperties = new Properties();
    
    public static final Properties leerConfiguracion(){
        try{
            Properties archivoConfiguracion = new Properties();
            archivoConfiguracion.load(new FileInputStream("jgame.properties"));
            return archivoConfiguracion;
        } catch(IOException e){
            System.out.println("Error en metodo PropertiesFile: "+e);
	}
        return null;
    }
    
    public Ajustes(){
        super("Configuracion");
        appProperties = leerConfiguracion();
        p_teclas = new Panel();
        p_guardarReset = new Panel();
        
	lasTeclas = new GridLayout(14,2);
        
        l_pantalla = new Label("Tamaño Pantalla",Label.LEFT);
        l_musica1 = new Label("Musica",Label.LEFT);
        l_musica2 = new Label("Activar/Desactivar Musica",Label.LEFT);
        l_sonido1 = new Label("Sonido",Label.LEFT);
        l_sonido2 = new Label("Activar/Desactivar Sonido",Label.LEFT);
        l_pausar = new Label("Pausar/Reanudar Juego",Label.LEFT);
        l_iniciar = new Label("Iniciar", Label.LEFT);
        l_izquierda = new Label("Izquierda",Label.LEFT);
	l_derecha = new Label("Derecha",Label.LEFT);
        l_saltar = new Label("Saltar",Label.LEFT);
        l_lanzar = new Label("Lanzar",Label.LEFT);
        l_nombre = new Label("Ingrese nombre", Label.LEFT);
        
        
        c_pantalla = new Choice();
	c_pantalla.add("Ventana");
	c_pantalla.add("Pantalla completa");

        c_musica = new Choice();
	c_musica.add("Original");
	c_musica.add("Opcional");
        
        c_sonido = new Choice();
        c_sonido.add("Activado");
        c_sonido.add("Desactivado");
        
        b_musica = new Button(appProperties.getProperty("teclaMusica"));
        b_sonido = new Button(appProperties.getProperty("teclaSonido"));
        b_pausar = new Button(appProperties.getProperty("teclaPausar"));
        b_iniciar = new Button(appProperties.getProperty("teclaIniciar"));
        b_izquierda = new Button(appProperties.getProperty("teclaIzquierda"));
	b_derecha = new Button(appProperties.getProperty("teclaDerecha"));
        b_saltar = new Button(appProperties.getProperty("teclaSaltar"));
        b_lanzar = new Button(appProperties.getProperty("teclaLanzar"));
        text_nombre = new TextField(appProperties.getProperty("nombreJugador"));
        b_guardar = new Button("Guardar");
	b_reset = new Button("Reset");
        
        
        if (appProperties.getProperty("fullScreen").equals("true")){
            c_pantalla.select(1);
        }else{
          c_pantalla.select(0);
        }
        
        if (appProperties.getProperty("sonidoChoice").equals("true")){
            c_sonido.select(0);
        }else{
            c_sonido.select(1);
            
        }
        
        switch (appProperties.getProperty("musica")){
            case "0":
              c_musica.select(0);
              break;
            case "1":
              c_musica.select(1);
              break;
            case "2":
              c_musica.select(2);
              break;
            case "3":
              c_musica.select(3);
              break;
        }
        
        b_musica.addActionListener(this); 
        b_sonido.addActionListener(this); 
        b_pausar.addActionListener(this);
        b_iniciar.addActionListener(this);
	b_izquierda.addActionListener(this);
	b_derecha.addActionListener(this);
        b_saltar.addActionListener(this);
        b_lanzar.addActionListener(this);
        text_nombre.addActionListener(this);
        b_guardar.addActionListener(this);
	b_reset.addActionListener(this);	
        
	b_musica.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e)
            {
                b_musica.setLabel(Character.toString(e.getKeyChar()));
            }

	});
        b_sonido.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e)
            {
                b_sonido.setLabel(Character.toString(e.getKeyChar()));
            }

	});
        b_pausar.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e)
            {
                b_pausar.setLabel(Character.toString(e.getKeyChar()));
            }

	});
        b_iniciar.addKeyListener(new KeyAdapter(){
            @Override
           public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                   b_iniciar.setLabel("Enter");
                }
                else{
                    b_iniciar.setLabel(Character.toString(e.getKeyChar()));
                }
            }
	});
        b_izquierda.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode()==KeyEvent.VK_LEFT){
                    b_izquierda.setLabel("<");
                }
                else{
                    if (e.getKeyCode()==KeyEvent.VK_RIGHT){
                        b_izquierda.setLabel(">");
                    }else{
                        b_izquierda.setLabel(Character.toString(e.getKeyChar()));
                    }
                }
            }
	});
	b_derecha.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode()==KeyEvent.VK_LEFT){
                    b_derecha.setLabel("<");
                }
                else{
                    if (e.getKeyCode()==KeyEvent.VK_RIGHT){
                        b_derecha.setLabel(">");
                    }else{
                        b_derecha.setLabel(Character.toString(e.getKeyChar()));
                    }
                }
            }
	});
        b_saltar.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e)
            {
                b_saltar.setLabel(Character.toString(e.getKeyChar()));
            }

	});
        
        b_lanzar.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e)
            {
                b_lanzar.setLabel(Character.toString(e.getKeyChar()));
            }

	});
        
	p_teclas.setLayout(lasTeclas);
	p_teclas.add(l_pantalla);
	p_teclas.add(c_pantalla);
	p_teclas.add(l_musica1);
	p_teclas.add(c_musica);
        p_teclas.add(l_musica2);
        p_teclas.add(b_musica);
        p_teclas.add(l_sonido1);
        p_teclas.add(c_sonido);
        p_teclas.add(l_sonido2);
        p_teclas.add(b_sonido);
        p_teclas.add(l_pausar);
	p_teclas.add(b_pausar);
        p_teclas.add(l_iniciar);
	p_teclas.add(b_iniciar);
	p_teclas.add(l_izquierda);
	p_teclas.add(b_izquierda);
	p_teclas.add(l_derecha);
	p_teclas.add(b_derecha);
        p_teclas.add(l_saltar);
	p_teclas.add(b_saltar);
        p_teclas.add(l_lanzar);
	p_teclas.add(b_lanzar);
        p_teclas.add(l_nombre);
        p_teclas.add(text_nombre);
        
	p_guardarReset.add(b_guardar);
	p_guardarReset.add(b_reset);
        
	//asignar layout y agregar objetos a 'this'
	this.add(p_teclas,BorderLayout.CENTER);
	this.add(p_guardarReset,BorderLayout.SOUTH);
	this.pack();
	this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    
    @Override
    public void actionPerformed(ActionEvent evt){
	if (evt.getActionCommand().equals(b_guardar.getActionCommand())){
            if (c_pantalla.getSelectedItem​().equals("Pantalla completa")){
                appProperties.setProperty("fullScreen","true");
            }else{
                appProperties.setProperty("fullScreen","false");
            }
            
            if (c_sonido.getSelectedItem​().equals("Activado")){
                    appProperties.setProperty("sonidoChoice","true");
            }else{
                    appProperties.setProperty("sonidoChoice","false");
            }
            
            if (c_musica.getSelectedItem​().equals("Original")){
                appProperties.setProperty("musica","0");
            }else{
                appProperties.setProperty("musica","1");
            }
            
            appProperties.setProperty("teclaIniciar",b_iniciar.getLabel​());
            appProperties.setProperty("teclaMusica",b_musica.getLabel​());
            appProperties.setProperty("teclaSonido",b_sonido.getLabel​());
            appProperties.setProperty("teclaPausar",b_pausar.getLabel​());
            appProperties.setProperty("teclaIzquierda",b_izquierda.getLabel​());
            appProperties.setProperty("teclaDerecha",b_derecha.getLabel​());
            appProperties.setProperty("teclaSaltar",b_saltar.getLabel​());
            appProperties.setProperty("teclaLanzar",b_lanzar.getLabel​());
            appProperties.setProperty("nombreJugador", text_nombre.getText());
            
            try{
                FileOutputStream out=new FileOutputStream("jgame.properties");
                appProperties.store(out,"jgame.properties");
                out.close();
            }catch(IOException e){
                System.out.print("Error guardar Properties");
            }
           
           
	}else if (evt.getActionCommand().equals(b_reset.getActionCommand())){
           b_iniciar.setLabel("Enter");
           b_musica.setLabel("w");
           b_sonido.setLabel("q");
           b_pausar.setLabel("p");
           b_izquierda.setLabel("<");
           b_derecha.setLabel(">");
           b_saltar.setLabel("x");
           b_lanzar.setLabel("z");
           text_nombre.setText("Jugador");
           c_musica.select(0);
           c_sonido.select(0);
           c_pantalla.select(0);
	}
    }
}
