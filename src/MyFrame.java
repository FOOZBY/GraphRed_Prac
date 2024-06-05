import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyFrame  extends JFrame {

    int  mode=0;
    int  xPad;
    int  xf;
    int  yf;
    int  yPad;
    boolean pressed=false;

    // текущий цвет
    Color maincolor = Color.black;

    Paint_panel paint_panel;

    boolean loading=false;
    String fileName;




    public void paint(Graphics g)
    {
        super.paint(g);
    }


    public MyFrame() {
        super.setTitle("Paint 0.9");
        super.setSize(700, 700);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLocationRelativeTo(null);
        super.setLayout(new BorderLayout());

        //////////////////////////////////////////////////////////////file menu
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem open_file_item = new JMenuItem("Open");
        JMenuItem save_file_item = new JMenuItem("Save");
        JMenuItem save_as_file_item = new JMenuItem("Save as");

        fileMenu.add(open_file_item);
        fileMenu.add(save_file_item);
        fileMenu.add(save_as_file_item);

        open_file_item.addActionListener(e -> {
            JFileChooser jf = new JFileChooser();
            int result = jf.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    // при выборе изображения подстраиваем размеры формы
                    // и панели под размеры данного изображения
                    fileName = jf.getSelectedFile().getAbsolutePath();
                    File iF = new File(fileName);
                    jf.addChoosableFileFilter(new TextFileFilter(".png"));
                    jf.addChoosableFileFilter(new TextFileFilter(".jpg"));
                    paint_panel.image = ImageIO.read(iF);
                    loading = true;
                    super.setSize(paint_panel.image.getWidth() + 40, paint_panel.image.getWidth() + 80);
                    paint_panel.setSize(paint_panel.image.getWidth(), paint_panel.image.getWidth());
                    paint_panel.repaint();
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(this, "Такого файла не существует");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Исключение ввода-вывода");
                }
            }
        });
        save_file_item.addActionListener(e -> {
            try {
                JFileChooser jf = new JFileChooser();
                // Создаем фильтры  файлов
                TextFileFilter pngFilter = new TextFileFilter(".png");
                TextFileFilter jpgFilter = new TextFileFilter(".jpg");
                if (fileName == null) {
                    // Добавляем фильтры
                    jf.addChoosableFileFilter(pngFilter);
                    jf.addChoosableFileFilter(jpgFilter);
                    int result = jf.showSaveDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        fileName = jf.getSelectedFile().getAbsolutePath();
                    }
                }
                // Смотрим какой фильтр выбран
                if (jf.getFileFilter() == pngFilter) {
                    ImageIO.write(paint_panel.image, "png", new File(fileName + ".png"));
                } else {
                    ImageIO.write(paint_panel.image, "jpeg", new File(fileName + ".jpg"));
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Ошибка ввода-вывода");
            }
        });
        save_as_file_item.addActionListener(e -> {
            try {
                JFileChooser jf = new JFileChooser();
                // Создаем фильтры для файлов
                TextFileFilter pngFilter = new TextFileFilter(".png");
                TextFileFilter jpgFilter = new TextFileFilter(".jpg");
                // Добавляем фильтры
                jf.addChoosableFileFilter(pngFilter);
                jf.addChoosableFileFilter(jpgFilter);
                int result = jf.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    fileName = jf.getSelectedFile().getAbsolutePath();
                }
                // Смотрим какой фильтр выбран
                if (jf.getFileFilter() == pngFilter) {
                    ImageIO.write(paint_panel.image, "png", new File(fileName + ".png"));
                } else {
                    ImageIO.write(paint_panel.image, "jpeg", new File(fileName + ".jpg"));
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Ошибка ввода-вывода");
            }
        });
        //////////////////////////////////////////////////////////////file menu


        //////////////////////////////////////////////////////////////paint panel
        paint_panel = new Paint_panel();
        paint_panel.setBackground(Color.white);
        paint_panel.setOpaque(true);
        //////////////////////////////////////////////////////////////paint panel


        //////////////////////////////////////////////////////////////toolbar
        JToolBar toolbar = new JToolBar("Toolbar", JToolBar.VERTICAL);

        JButton penbutton = new JButton(new ImageIcon("pencil.png"));
        penbutton.addActionListener(event -> mode = 0);

        JButton brushbutton = new JButton(new ImageIcon("brush.png"));
        brushbutton.addActionListener(event -> mode = 1);

        JButton lasticbutton = new JButton(new ImageIcon("eraser.png"));
        lasticbutton.addActionListener(event -> mode = 2);

        JButton textbutton = new JButton(new ImageIcon("text.png"));
        textbutton.addActionListener(event -> mode = 3);

        JButton linebutton = new JButton(new ImageIcon("line.png"));
        linebutton.addActionListener(event -> mode = 4);

        JButton elipse_button = new JButton(new ImageIcon("elipse.png"));
        elipse_button.addActionListener(event -> mode = 5);

        JButton rectangle_button = new JButton(new ImageIcon("rectangle.png"));
        rectangle_button.addActionListener(event -> mode = 6);


        toolbar.add(elipse_button);
        toolbar.add(linebutton);
        toolbar.add(textbutton);
        toolbar.add(lasticbutton);
        toolbar.add(brushbutton);
        toolbar.add(penbutton);
        toolbar.add(rectangle_button);
        //////////////////////////////////////////////////////////////toolbar


        //////////////////////////////////////////////////////////////color toolbar
        JToolBar color_bar = new JToolBar("Color bar", JToolBar.VERTICAL);
        color_bar.setSize(new Dimension(30,300));

        JButton colorbutton = new JButton();
        colorbutton.setBackground(maincolor);

        colorbutton.addActionListener(e -> {
            maincolor = JColorChooser.showDialog(this, "Выбор цвета", maincolor);
            colorbutton.setBackground(maincolor);
        });

        JButton redbutton = new JButton();
        redbutton.setBackground(Color.red);
        redbutton.addActionListener(e -> {
            maincolor = Color.red;
            colorbutton.setBackground(maincolor);
        });

        JButton orange_button = new JButton();
        orange_button.setBackground(Color.orange);
        orange_button.setBounds(60, 5, 15, 15);
        orange_button.addActionListener(event -> {
            maincolor = Color.orange;
            colorbutton.setBackground(maincolor);
        });

        JButton yellow_button = new JButton();
        yellow_button.setBackground(Color.yellow);
        yellow_button.setBounds(80, 5, 15, 15);
        yellow_button.addActionListener(event -> {
            maincolor = Color.yellow;
            colorbutton.setBackground(maincolor);
        });

        JButton green_button = new JButton();
        green_button.setBackground(Color.green);
        green_button.setBounds(100, 5, 15, 15);
        green_button.addActionListener(event -> {
            maincolor = Color.green;
            colorbutton.setBackground(maincolor);
        });

        JButton blue_button = new JButton();
        blue_button.setBackground(Color.blue);
        blue_button.setBounds(120, 5, 15, 15);
        blue_button.addActionListener(event -> {
            maincolor = Color.blue;
            colorbutton.setBackground(maincolor);
        });

        JButton cyan_button = new JButton();
        cyan_button.setBackground(Color.cyan);
        cyan_button.setBounds(140, 5, 15, 15);
        cyan_button.addActionListener(event -> {
            maincolor = Color.cyan;
            colorbutton.setBackground(maincolor);
        });

        JButton magenta_button = new JButton();
        magenta_button.setBackground(Color.magenta);
        magenta_button.setBounds(160, 5, 15, 15);
        magenta_button.addActionListener(event -> {
            maincolor = Color.magenta;
            colorbutton.setBackground(maincolor);
        });

        JButton white_button = new JButton();
        white_button.setBackground(Color.white);
        white_button.setBounds(180, 5, 15, 15);
        white_button.addActionListener(event -> {
            maincolor = Color.white;
            colorbutton.setBackground(maincolor);
        });

        JButton black_button = new JButton();
        black_button.setBackground(Color.black);
        black_button.setBounds(200, 5, 15, 15);
        black_button.addActionListener(event -> {
            maincolor = Color.black;
            colorbutton.setBackground(maincolor);
        });

        color_bar.add(colorbutton);
        color_bar.add(redbutton);
        color_bar.add(orange_button);
        color_bar.add(yellow_button);
        color_bar.add(green_button);
        color_bar.add(blue_button);
        color_bar.add(cyan_button);
        color_bar.add(magenta_button);
        color_bar.add(white_button);
        color_bar.add(black_button);

        //////////////////////////////////////////////////////////////color toolbar

        paint_panel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (pressed) {
                    Graphics g = paint_panel.image.getGraphics();
                    Graphics2D g2 = (Graphics2D) g;
                    // установка цвета
                    g2.setColor(maincolor);
                    switch (mode) {
                        // карандаш
                        case 0:
                            g2.drawLine(xPad, yPad, e.getX(), e.getY());
                            break;
                        // кисть
                        case 1:
                            g2.setStroke(new BasicStroke(3.0f));
                            g2.drawLine(xPad, yPad, e.getX(), e.getY());
                            break;
                        // ластик
                        case 2:
                            g2.setStroke(new BasicStroke(3.0f));
                            g2.setColor(Color.WHITE);
                            g2.drawLine(xPad, yPad, e.getX(), e.getY());
                            break;
                    }
                    xPad = e.getX();
                    yPad = e.getY();
                }
                paint_panel.repaint();
            }
        });
        paint_panel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                Graphics g = paint_panel.image.getGraphics();
                Graphics2D g2 = (Graphics2D) g;
                // установка цвета
                g2.setColor(maincolor);
                switch (mode) {
                    // карандаш
                    case 0:
                        g2.drawLine(xPad, yPad, xPad , yPad );
                        break;
                    // кисть
                    case 1:
                        g2.setStroke(new BasicStroke(3.0f));
                        g2.drawLine(xPad, yPad, xPad , yPad );
                        break;
                    // ластик
                    case 2:
                        g2.setStroke(new BasicStroke(3.0f));
                        g2.setColor(Color.WHITE);
                        g2.drawLine(xPad, yPad, xPad , yPad );
                        break;
                    // текст
                    case 3:
                        // устанавливаем фокус для панели,
                        // чтобы печатать на ней текст
                        paint_panel.requestFocus();
                        break;
                }
                xPad = e.getX();
                yPad = e.getY();

                pressed = true;
                paint_panel.repaint();
            }

            public void mousePressed(MouseEvent e) {
                xPad = e.getX();
                yPad = e.getY();
                xf = e.getX();
                yf = e.getY();
                pressed = true;
            }

            public void mouseReleased(MouseEvent e) {

                Graphics g = paint_panel.image.getGraphics();
                Graphics2D g2 = (Graphics2D) g;
                // установка цвета
                g2.setColor(maincolor);
                // Общие рассчеты для овала и прямоугольника
                int x1 = xf, x2 = xPad, y1 = yf, y2 = yPad;
                if (xf > xPad) {
                    x2 = xf;
                    x1 = xPad;
                }
                if (yf > yPad) {
                    y2 = yf;
                    y1 = yPad;
                }
                switch (mode) {
                    // линия
                    case 4:
                        g.drawLine(xf, yf, e.getX(), e.getY());
                        System.out.println(xf + ":" + yf + " " + e.getX() + ":" + e.getY());
                        break;
                    // круг
                    case 5:
                        g.drawOval(x1, y1, (x2 - x1), (y2 - y1));
                        break;
                    // прямоугольник
                    case 6:
                        g.drawRect(x1, y1, (x2 - x1), (y2 - y1));
                        break;
                }
                xf = 0;
                yf = 0;
                pressed = false;
                paint_panel.repaint();
            }
        });
        paint_panel.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                // устанавливаем фокус для панели,
                // чтобы печатать на ней текст
                paint_panel.requestFocus();
            }

            public void keyTyped(KeyEvent e) {
                if (mode == 3) {
                    Graphics g = paint_panel.image.getGraphics();
                    Graphics2D g2 = (Graphics2D) g;
                    // установка цвета
                    g2.setColor(maincolor);
                    g2.setStroke(new BasicStroke(2.0f));

                    String str = "";
                    str += e.getKeyChar();
                    g2.setFont(new Font("Arial", Font.PLAIN, 15));
                    g2.drawString(str, xPad, yPad);
                    xPad += 10;
                    // устанавливаем фокус для панели,
                    // чтобы печатать на ней текст
                    paint_panel.requestFocus();
                    paint_panel.repaint();
                }
            }
        });
        super.addComponentListener(new ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                // если делаем загрузку, то изменение размеров формы
                // отрабатываем в коде загрузки
                if (!loading) {
                    paint_panel.setSize(MyFrame.super.getWidth() - 40, MyFrame.super.getHeight() - 80);
                    BufferedImage tempImage = new BufferedImage(paint_panel.getWidth(), paint_panel.getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics2D d2 = tempImage.createGraphics();
                    d2.setColor(Color.white);
                    d2.fillRect(0, 0, paint_panel.getWidth(), paint_panel.getHeight());
                    tempImage.setData(paint_panel.image.getRaster());
                    paint_panel.image = tempImage;
                    paint_panel.repaint();
                }
                loading = false;
            }
        });


        super.setJMenuBar(menuBar);
        super.add(color_bar, BorderLayout.EAST);
        super.add(paint_panel, BorderLayout.CENTER);
        super.add(toolbar, BorderLayout.WEST);
        super.setVisible(true);
    }

}
