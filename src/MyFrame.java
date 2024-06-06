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
        //////////////////////////////////////////////////////////////paint panel
        paint_panel = new Paint_panel();
        paint_panel.setBackground(Color.white);
        paint_panel.setOpaque(true);


        //////////////////////////////////////////////////////////////paint panel





        super.setTitle("Paint 0.9");
        super.setSize(1000, 800);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLocationRelativeTo(null);
        super.setLayout(new BorderLayout());

        //////////////////////////////////////////////////////////////file menu
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenuItem exitMenu = new JMenuItem("Exit");
        menuBar.add(fileMenu);

        JMenuItem open_file_item = new JMenuItem("Open");
        JMenuItem save_file_item = new JMenuItem("Save");
        JMenuItem save_as_file_item = new JMenuItem("Save as");

        fileMenu.add(open_file_item);
        fileMenu.add(save_file_item);
        fileMenu.add(save_as_file_item);
        fileMenu.add(exitMenu);

        open_file_item.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(new TextFileFilter(".png"));
            fileChooser.addChoosableFileFilter(new TextFileFilter(".jpg"));
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    // при выборе изображения подстраиваем размеры формы
                    // и панели под размеры данного изображения
                    fileName = fileChooser.getSelectedFile().getAbsolutePath();
                    File file = new File(fileName);
                    BufferedImage im = ImageIO.read(file);
                    Graphics g = paint_panel.image.getGraphics();
                    g.drawImage(im,0,0, this);
                    loading = true;
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

        exitMenu.addActionListener(e -> {
            System.out.println("sdfvds");
            int choice = JOptionPane.showConfirmDialog(null, "Выйти без сохранения?", "Выход", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                super.dispose();
            } else if (choice == JOptionPane.NO_OPTION) {
                save_as_file_item.doClick();
            }
        });

        //////////////////////////////////////////////////////////////file menu





        //////////////////////////////////////////////////////////////toolbar
        JToolBar toolbar = new JToolBar("Toolbar", JToolBar.VERTICAL);

        JButton pencil_button = new JButton(new ImageIcon("pencil.png"));
        pencil_button.addActionListener(event -> mode = 0);

        JButton brush_button = new JButton(new ImageIcon("brush.png"));
        brush_button.addActionListener(event -> mode = 1);

        JButton eraser_button = new JButton(new ImageIcon("eraser.png"));
        eraser_button.addActionListener(event -> mode = 2);

        JButton text_button = new JButton(new ImageIcon("text.png"));
        text_button.addActionListener(event -> mode = 3);

        JButton line_button = new JButton(new ImageIcon("line.png"));
        line_button.addActionListener(event -> mode = 4);

        JButton elipse_button = new JButton(new ImageIcon("elipse.png"));
        elipse_button.addActionListener(event -> mode = 5);

        JButton rectangle_button = new JButton(new ImageIcon("rectangle.png"));
        rectangle_button.addActionListener(event -> mode = 6);


        toolbar.add(elipse_button);
        toolbar.add(line_button);
        toolbar.add(text_button);
        toolbar.add(eraser_button);
        toolbar.add(brush_button);
        toolbar.add(pencil_button);
        toolbar.add(rectangle_button);
        //////////////////////////////////////////////////////////////toolbar


        //////////////////////////////////////////////////////////////color toolbar
        JToolBar color_bar = new JToolBar("Color bar", JToolBar.VERTICAL);
        color_bar.setPreferredSize(new Dimension(30,0));
        color_bar.setLayout(new FlowLayout());

        JButton color_button = new JButton();
        color_button.setBackground(maincolor);
        color_button.setPreferredSize(new Dimension(28,28));

        color_button.addActionListener(e -> {
            maincolor = JColorChooser.showDialog(this, "Выбор цвета", maincolor);
            color_button.setBackground(maincolor);
        });

        JButton red_button = new JButton();
        red_button.setBackground(Color.red);
        red_button.setPreferredSize(new Dimension(22,22));
        red_button.addActionListener(e -> {
            maincolor = Color.red;
            color_button.setBackground(maincolor);
        });

        JButton orange_button = new JButton();
        orange_button.setBackground(Color.orange);
        orange_button.setPreferredSize(new Dimension(22,22));
        orange_button.addActionListener(event -> {
            maincolor = Color.orange;
            color_button.setBackground(maincolor);
        });

        JButton yellow_button = new JButton();
        yellow_button.setBackground(Color.yellow);
        yellow_button.setPreferredSize(new Dimension(22,22));
        yellow_button.addActionListener(event -> {
            maincolor = Color.yellow;
            color_button.setBackground(maincolor);
        });

        JButton green_button = new JButton();
        green_button.setBackground(Color.green);
        green_button.setPreferredSize(new Dimension(22,22));
        green_button.addActionListener(event -> {
            maincolor = Color.green;
            color_button.setBackground(maincolor);
        });

        JButton blue_button = new JButton();
        blue_button.setBackground(Color.blue);
        blue_button.setPreferredSize(new Dimension(22,22));
        blue_button.addActionListener(event -> {
            maincolor = Color.blue;
            color_button.setBackground(maincolor);
        });

        JButton cyan_button = new JButton();
        cyan_button.setBackground(Color.cyan);
        cyan_button.setPreferredSize(new Dimension(22,22));
        cyan_button.addActionListener(event -> {
            maincolor = Color.cyan;
            color_button.setBackground(maincolor);
        });

        JButton magenta_button = new JButton();
        magenta_button.setBackground(Color.magenta);
        magenta_button.setPreferredSize(new Dimension(22,22));
        magenta_button.addActionListener(event -> {
            maincolor = Color.magenta;
            color_button.setBackground(maincolor);
        });

        JButton white_button = new JButton();
        white_button.setBackground(Color.white);
        white_button.setPreferredSize(new Dimension(22,22));
        white_button.addActionListener(event -> {
            maincolor = Color.white;
            color_button.setBackground(maincolor);
        });

        JButton black_button = new JButton();
        black_button.setBackground(Color.black);
        black_button.setPreferredSize(new Dimension(22,22));
        black_button.addActionListener(event -> {
            maincolor = Color.black;
            color_button.setBackground(maincolor);
        });

        color_bar.add(color_button);
        color_bar.add(red_button);
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
                        String text = JOptionPane.showInputDialog(null, "Введите сообщение:");
                        g2.setColor(maincolor);
                        g2.drawString(text, xPad, yPad);
                        // установка цвета

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
                        System.out.println("#line:" + xf + ":" + yf + ";" + e.getX() + ":" + e.getY());
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
        });

        super.setJMenuBar(menuBar);
        super.add(color_bar, BorderLayout.EAST);
        super.add(paint_panel, BorderLayout.CENTER);
        super.add(toolbar, BorderLayout.WEST);
        super.setVisible(true);
    }

}
