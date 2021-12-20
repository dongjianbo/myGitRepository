package ruanjian;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;
import javax.swing.*;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

@SuppressWarnings("serial")
public class Lucky extends JFrame {
    Toolkit tool = Toolkit.getDefaultToolkit();
    int height = tool.getScreenSize().height;
    int width = tool.getScreenSize().width;
    ImageIcon image = new ImageIcon(Lucky.class.getResource("/image/back.jpg"));
    int ser=1;
    JPanel root;
    JLabel jl = new JLabel();
    Vector<String> names = new Vector<String>();
    File nameFile;
    Random rand = new Random();
    Font font = new Font("����", Font.BOLD, 150);
    MyThread thread = new MyThread();
    Boolean bool = new Boolean(false);
    AudioStream as1;
    AudioStream as2;

    //��������
    //LinkedList<String> names1=new LinkedList<String>();
    public Lucky() {
//		this.setTitle("�ƸԿƼ�ְҵѧԺ����ý���������������˴�齱");
        root = new BackgroundPanel(image.getImage());
        try {
//			BufferedReader read=new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("�Űݻ�����.txt"), "GB2312"));
            BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\�齱����1.txt"), "UTF-8"));
            String name = "";
            while ((name = read.readLine()) != null) {
                names.add(name);
            }

            read.close();
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(this, "���������Ƿ���ڣ�");
        }

        root.setLayout(null);
        //jl.setBorder(new LineBorder(Color.BLACK));
        //jl.setBackground(Color.red);
        jl.setForeground(Color.yellow);
        jl.setBounds((width - 1000) / 2, (height - 400) / 2 + 50, 1000, 400);
        jl.setFont(font);
        jl.setHorizontalAlignment(SwingConstants.CENTER);
        root.add(jl);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.add(root);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setVisible(true);

        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (!bool) {
                        bool = true;
                        try {
                            as2 = new AudioStream(this.getClass().getClassLoader().getResourceAsStream("music/2.wav"));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        AudioPlayer.player.stop(as1);
                        AudioPlayer.player.start(as2);
                        String name = jl.getText().trim();//�н���


                        System.out.println("��"+(ser++)+"λ�н����ǣ�" + name);
                        for (int i = 0; i < names.size(); i++) {
                            if (names.get(i).equals(name)) {
                                names.remove(i);
                                i--;
                            }
                        }
                        System.out.println("��ʣ��" + names.size() + "������");
                        Collections.shuffle(names);
                    } else if (bool) {
                        bool = false;
                        thread.p();
                        try {
                            as2 = new AudioStream(this.getClass().getClassLoader().getResourceAsStream("music/2.wav"));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        AudioPlayer.player.stop(as2);
                        AudioPlayer.player.start(as1);
                    }
                }
            }

        });
        thread.start();
        try {
            as1 = new AudioStream(this.getClass().getClassLoader().getResourceAsStream("music/1.wav"));
            as2 = new AudioStream(this.getClass().getClassLoader().getResourceAsStream("music/2.wav"));
            AudioPlayer.player.start(as1);
        } catch (IOException e2) {
            e2.printStackTrace();
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Lucky();

            }
        });

    }

    /**
     * �б���ͼƬ��Panel��
     *
     * @author tntxia
     */
    class BackgroundPanel extends JPanel {

        /**
         *
         */
        private static final long serialVersionUID = -6352788025440244338L;

        private Image image = null;

        public BackgroundPanel(Image image) {
            this.image = image;
        }

        // �̶�����ͼƬ���������JPanel������ͼƬ������������
        protected void paintComponent(Graphics g) {

        	g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    class MyThread extends Thread {
        public synchronized void p() {
            this.notifyAll();
        }

        public synchronized void run() {
            while (true) {
                if (bool) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int i = rand.nextInt(names.size());
                jl.setText(names.get(i));
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
