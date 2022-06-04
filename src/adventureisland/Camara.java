package adventureisland;

public class Camara {

	private double x,y;
        private double inicialX, inicialY;

	private double resX,resY;
        
        private AdventureIsland ai;

    public Camara(AdventureIsland ai, double x,double y) {
    	this.x=x;
    	this.y=y;
        this.inicialX = x;
        this.inicialY = y;
        this.ai = ai;
    }
    
    public void reiniciar(){
        this.x = inicialX;
        this.y = inicialY;
    }

    public void seguirPersonaje(Heroe heroe){
        if(-heroe.posX+resX/2 < this.x){
            this.x = -heroe.posX+resX/2;
            ai.getEsc().setLimiteIzqMundo(ai.getEsc().getLimiteIzqMundo() + ((int) Heroe.DEFAULT_VELOCITY));
        }
        if (this.x>0){
            this.x=0;
        }

        if(this.x < -(Escenario.ANCHO_MUNDO-resX)){
            this.x = -(Escenario.ANCHO_MUNDO-resX);
        }
    }
    
    public void setViewPort(double x,double y){
        setRegionVisible(x,y);
    }
    
    public void setRegionVisible(double x,double y){
        resX=x;
        resY=y;
    }
    public void setX(double x){
    	this.x=x;

    }
     public void setY(double y){
    	this.y=y;

    }
    public double getX(){
    	return this.x;

    }
     public double getY(){
    	return this.y;

    }

}