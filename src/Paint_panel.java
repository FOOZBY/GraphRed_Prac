import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Paint_panel extends JPanel {
    BufferedImage image;

    public Paint_panel()
    { }
    public void paintComponent (Graphics g)
    {
        if(image == null)
        {
            image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
            System.out.println(this.getWidth()+":"+this.getHeight());
            Graphics2D d2 = image.createGraphics();
            d2.setColor(Color.white);
            d2.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        super.paintComponent(g);
        g.drawImage(image, 0, 0,this);
    }
}
