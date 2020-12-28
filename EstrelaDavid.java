package tarefa_java2d;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author : Sara Tuma
 * Estudante: Universidade Católica de Angola
 * Ano: 3º ( Primeiro semestre )
 * Disciplina: Computação Grafica   ( TAREFA )
 * 2020
 */
public class EstrelaDavid extends JPanel implements Runnable{
    Thread t=new Thread(this);
    private int angulo=1; 
    BufferedImage im ,im2= null;
    
    
    public EstrelaDavid() throws IOException{
        try {
            im= ImageIO.read(new File("arvore1.png"));
        } catch (IOException ex) {
            Logger.getLogger(EstrelaDavid.class.getName()).log(Level.SEVERE, null, ex);
        }
        t.start();
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600,600);
        this.setBackground(Color.black);
        f.add(this);
        
        f.setVisible(true);
    }
    
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3f));
        
        //Desenhando a meia-lua
        Area a1,a2; //Criei as areas para armazenar as elipses
        Ellipse2D.Double el1= new Ellipse2D.Double(25, 25,100,90);
        Ellipse2D.Double el2= new Ellipse2D.Double(45, 25,100,90);
        a1=new Area(el1);
        a2= new Area(el2);
        a1.subtract(a2); //Fanzendo a subtracção das areas, teremos a meia-lua
        //Antes de pinta-la no painel, adicionei o gradiente nela
        g2.setPaint( new GradientPaint( 10, 30, Color.BLACK, 35, 100 ,Color.WHITE ,true) );
        //Pintei a area resultante
        g2.fill(a1);
        
        //Desenhando a imagem carregada com bafferImage
        g2.drawImage(im,100,100,null);
     
        //Criando o movimento de rotação da estrela
        g2.rotate(Math.toRadians(angulo),200+(100/2), 110+(120/2));
        
        //Com Buffer Desenhei a textura da estrela 
        BufferedImage buffImage = new BufferedImage( 10, 10,BufferedImage.TYPE_INT_RGB );
        Graphics2D gg = buffImage.createGraphics();
            gg.setColor( Color.darkGray );  
            gg.fillRect( 0, 0, 10, 10 );           
            gg.setColor( Color.WHITE );       
            gg.fillRect( 1, 1, 3, 3 );      
        //Colocando a textura
        g2.setPaint( new TexturePaint( buffImage,new Rectangle( 10, 10 ) ) );
        //Desenhando a estrela de David com General Path
        desenhar(g2);        
    }
    
    public void desenhar(Graphics2D g){
        GeneralPath path = new GeneralPath();
        path.moveTo(200f,200f);
        path.lineTo(300f, 200f);
        path.lineTo(250f, 110f); 
        path.lineTo(200f, 200f);
        path.closePath();
        g.draw(path);
        
        path.moveTo(200f,140f);
        path.lineTo(250f, 230f);
        path.lineTo(300f, 140f);
        path.lineTo(200f, 140f);
        path.closePath();
 
        g.fill(path);
    }


    
    public void dormir(){
        try {
            t.sleep(10);
        } catch (InterruptedException ex) {
            Logger.getLogger(EstrelaDavid.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        while(true){
            //Alterando o angulo de rotação da estrela
            this.angulo = (int) (this.angulo >= 360 ? 0 : 1 + this.angulo);
            repaint();
            dormir();
        }
    }
    
    //FUNCAO PRINCIPAL
    public static void main(String[] args) {
        try {
            new EstrelaDavid();
        } catch (IOException ex) {
            Logger.getLogger(EstrelaDavid.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
