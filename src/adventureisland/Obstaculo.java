package adventureisland;


public class Obstaculo extends ObjetoGrafico {

    public Obstaculo(String direccion) {
        super(direccion);
    }

    public Obstaculo(String direcciones[], int cantidadImagenes, int numeroImagen) {
        super(direcciones, cantidadImagenes, numeroImagen);
    }
}