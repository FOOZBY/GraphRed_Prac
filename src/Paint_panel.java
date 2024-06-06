import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Observer;

public class Paint_panel extends JPanel {
    BufferedImage image;
    private BufferedImage resizedImage;

    public Paint_panel()
    {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeImage();
                repaint();
            }
        });
    }

    private void resizeImage() {

        if (getWidth() > 0 && getHeight() > 0) {
            resizedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = resizedImage.createGraphics();

            // Заливка новым цветом (по умолчанию белым)
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, getWidth(), getHeight());


            // Рисование исходного изображения
            g2d.drawImage(image, 0, 0, this);
            g2d.dispose();  // Освобождение ресурсов
        }
    }



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

        if (resizedImage != null) {
            System.out.println("svsv");
            image = resizedImage;
        }
        g.drawImage(image, 0, 0, this);
    }
}
