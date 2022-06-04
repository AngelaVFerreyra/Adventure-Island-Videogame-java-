 package adventureisland;


import java.awt.Image;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

class Imagen extends JPanel{
	JLabel lbl_texto;

	public Imagen(String archivo, int wi, int he) throws IOException{
            lbl_texto = new JLabel();

            ImageIcon image = new ImageIcon(getClass().getResource(archivo));
            Image image2=image.getImage();
            Image newImg=image2.getScaledInstance(wi,he, java.awt.Image.SCALE_SMOOTH);
            image = new ImageIcon(newImg);

            if (image != null){
                    lbl_texto.setIcon(image);
            }else{
                    lbl_texto.setText("Imposible mostrar la imagen");
            }
            this.add(lbl_texto);
	}

}