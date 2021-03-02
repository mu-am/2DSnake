import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

class Gameplay extends JPanel implements KeyListener, ActionListener {

    private boolean started = false;
    private boolean waiting = false;
    private boolean paused = false;
    private boolean stopped = false;
    private boolean appleinsnake = false;
    private int lengthofsnake = 3;
    private int score = 0;

    private final int resol = 40;
    private final int xmin = 40;
    private final int xmax = 720-resol;
    private final int ymin = 120;
    private final int ymax = 720-resol;

    private int[] getSnakexArray() {
        int[] snakex = new int[260];
        snakex[0] = xmin+2*resol;
        snakex[1] = xmin+resol;
        snakex[2] = xmin;
        for(int i=3; i<250; i++) {
            snakex[i]=0;
        }
        return snakex;
    }
    private int[] snakex = getSnakexArray();

    private int[] getSnakeyArray() {
        int[] snakey = new int[260];
        snakey[2] = ymin;
        snakey[1] = ymin;
        snakey[0] = ymin;
        for(int i=3; i<260; i++) {
            snakey[i]=0;
        }
        return snakey;
    }
    private int[] snakey = getSnakeyArray();

    private int[] getApplexposArray() {
        int[] applex = new int[(xmax-xmin)/resol+1];
        for(int i=0; i<=((xmax-xmin)/resol); i++) {
            applex[i]=xmin+i*resol;
        }
        return applex;
    }
    private final int[] applex = getApplexposArray();

    private int[] getAppleyposArray() {
        int[] appley = new int[(ymax-ymin)/resol+1];
        for(int i=0; i<=((ymax-ymin)/resol); i++) {
            appley[i]=ymin+i*resol;
        }
        return appley;
    }
    private final int[] appley = getAppleyposArray();

    private int xpos = 5;
    private int ypos = 5;

    private boolean right = false;
    private boolean left = false;
    private boolean up = false;
    private boolean down = false;

    private String frameheadright = "Images/headright.png";
    private String frameheadleft = "Images/headleft.png";
    private String frameheadup = "Images/headup.png";
    private String frameheaddown = "Images/headdown.png";


    public Gameplay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        Timer timer = new Timer(100, this);
        timer.start();
    }

    public void paint(Graphics g) {

        // draw title image border
        g.setColor(new Color(74, 117, 44));
        g.fillRect(0,0,760,80);

        //draw border for gameplay
        g.setColor(new Color(87, 138, 52));
        g.fillRect(0,80,760,680);

        //draw background for the gameplay
        ImageIcon backgroundIm= new ImageIcon("Images/Background.png");
        backgroundIm.paintIcon(this, g, 40, 120);

        //draw score
        ImageIcon appleimage = new ImageIcon("Images/apple.png");
        appleimage.paintIcon(this, g, 40, 15);
        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.BOLD, 40));
        g.drawString(""+score, 120,55);


        //draw snakebody and tail
        ImageIcon bodyver, bodyhor, bodycurve1, bodycurve2, bodycurve3, bodycurve4;
        ImageIcon tailright, tailleft, tailup, taildown;
        for(int a=1; a<lengthofsnake; a++) {
            if(a<lengthofsnake-1) {
                if(snakex[a]==snakex[a-1] && snakex[a]==snakex[a+1]) {
                    //vertical
                    bodyver = new ImageIcon("Images/bodyver.png");
                    bodyver.paintIcon(this, g, snakex[a], snakey[a]);
                } else if(snakey[a]==snakey[a-1] && snakey[a]==snakey[a+1]) {
                    //horizontal
                    bodyhor = new ImageIcon("Images/bodyhor.png");
                    bodyhor.paintIcon(this, g, snakex[a], snakey[a]);
                } else if ((snakey[a]==snakey[a-1] && snakex[a]==snakex[a+1]) || (snakey[a]==snakey[a+1] && snakex[a]==snakex[a-1])) {
                    if((snakey[a]>snakey[a+1] && snakex[a]<snakex[a-1]) || (snakey[a]>snakey[a-1] && snakex[a]<snakex[a+1])) {
                        //number 1
                        if((snakex[a]==xmin && ((snakex[a-1]==xmax && snakex[a+1]==xmin) || (snakex[a-1]==xmin && snakex[a+1]==xmax))) &&
                                (snakey[a]==ymax && ((snakey[a-1]==ymin && snakey[a+1]==ymax) || (snakey[a-1]==ymax && snakey[a+1]==ymin)))) {
                            bodycurve3 = new ImageIcon("Images/bodycurve3.png");
                            bodycurve3.paintIcon(this, g, snakex[a], snakey[a]);
                        } else if(snakex[a]==xmin && ((snakex[a-1]==xmax && snakex[a+1]==xmin) || (snakex[a-1]==xmin && snakex[a+1]==xmax))) {
                            bodycurve2 = new ImageIcon("Images/bodycurve2.png");
                            bodycurve2.paintIcon(this, g, snakex[a], snakey[a]);
                        } else if(snakey[a]==ymax && ((snakey[a-1]==ymin && snakey[a+1]==ymax) || (snakey[a-1]==ymax && snakey[a+1]==ymin))) {
                            bodycurve4 = new ImageIcon("Images/bodycurve4.png");
                            bodycurve4.paintIcon(this, g, snakex[a], snakey[a]);
                        } else {
                            bodycurve1 = new ImageIcon("Images/bodycurve1.png");
                            bodycurve1.paintIcon(this, g, snakex[a], snakey[a]);
                        }
                    } else if((snakey[a]>snakey[a+1] && snakex[a]>snakex[a-1]) || (snakey[a]>snakey[a-1] && snakex[a]>snakex[a+1])) {
                        //number 2
                        if((snakex[a]==xmax && ((snakex[a-1]==xmin && snakex[a+1]==xmax) || (snakex[a-1]==xmax && snakex[a+1]==xmin))) &&
                                (snakey[a]==ymax && ((snakey[a-1]==ymin && snakey[a+1]==ymax) || (snakey[a-1]==ymax && snakey[a+1]==ymin)))) {
                            bodycurve4 = new ImageIcon("Images/bodycurve4.png");
                            bodycurve4.paintIcon(this, g, snakex[a], snakey[a]);
                        } else if(snakex[a]==xmax && ((snakex[a-1]==xmin && snakex[a+1]==xmax) || (snakex[a-1]==xmax && snakex[a+1]==xmin))) {
                            bodycurve1 = new ImageIcon("Images/bodycurve1.png");
                            bodycurve1.paintIcon(this, g, snakex[a], snakey[a]);
                        } else if(snakey[a]==ymax && ((snakey[a-1]==ymin && snakey[a+1]==ymax) || (snakey[a-1]==ymax && snakey[a+1]==ymin))) {
                            bodycurve3 = new ImageIcon("Images/bodycurve3.png");
                            bodycurve3.paintIcon(this, g, snakex[a], snakey[a]);
                        } else {
                            bodycurve2 = new ImageIcon("Images/bodycurve2.png");
                            bodycurve2.paintIcon(this, g, snakex[a], snakey[a]);
                        }
                    } else if((snakey[a]<snakey[a+1] && snakex[a]>snakex[a-1]) || (snakey[a]<snakey[a-1] && snakex[a]>snakex[a+1])) {
                        //number 3
                        if((snakex[a]==xmax && ((snakex[a-1]==xmax && snakex[a+1]==xmin) || (snakex[a-1]==xmin && snakex[a+1]==xmax))) &&
                                (snakey[a]==ymin && ((snakey[a-1]==ymax && snakey[a+1]==ymin) || (snakey[a-1]==ymin && snakey[a+1]==ymax)))) {
                            bodycurve1 = new ImageIcon("Images/bodycurve1.png");
                            bodycurve1.paintIcon(this, g, snakex[a], snakey[a]);
                        } else if(snakex[a]==xmax && ((snakex[a-1]==xmax && snakex[a+1]==xmin) || (snakex[a-1]==xmin && snakex[a+1]==xmax))) {
                            bodycurve4 = new ImageIcon("Images/bodycurve4.png");
                            bodycurve4.paintIcon(this, g, snakex[a], snakey[a]);
                        } else if(snakey[a]==ymin && ((snakey[a-1]==ymax && snakey[a+1]==ymin) || (snakey[a-1]==ymin && snakey[a+1]==ymax))) {
                            bodycurve2 = new ImageIcon("Images/bodycurve2.png");
                            bodycurve2.paintIcon(this, g, snakex[a], snakey[a]);
                        } else {
                            bodycurve3 = new ImageIcon("Images/bodycurve3.png");
                            bodycurve3.paintIcon(this, g, snakex[a], snakey[a]);
                        }
                    } else if((snakey[a]<snakey[a+1] && snakex[a]<snakex[a-1]) || (snakey[a]<snakey[a-1] && snakex[a]<snakex[a+1])) {
                        //number 4
                        if((snakex[a]==xmin && ((snakex[a-1]==xmax && snakex[a+1]==xmin) || (snakex[a-1]==xmin && snakex[a+1]==xmax))) &&
                                (snakey[a]==ymin && ((snakey[a-1]==ymax && snakey[a+1]==ymin) || (snakey[a-1]==ymin && snakey[a+1]==ymax)))) {
                            bodycurve2 = new ImageIcon("Images/bodycurve2.png");
                            bodycurve2.paintIcon(this, g, snakex[a], snakey[a]);
                        } else if(snakex[a]==xmin && ((snakex[a-1]==xmax && snakex[a+1]==xmin) || (snakex[a-1]==xmin && snakex[a+1]==xmax))) {
                            bodycurve3 = new ImageIcon("Images/bodycurve3.png");
                            bodycurve3.paintIcon(this, g, snakex[a], snakey[a]);
                        } else if(snakey[a]==ymin && ((snakey[a-1]==ymax && snakey[a+1]==ymin) || (snakey[a-1]==ymin && snakey[a+1]==ymax))) {
                            bodycurve1 = new ImageIcon("Images/bodycurve1.png");
                            bodycurve1.paintIcon(this, g, snakex[a], snakey[a]);
                        } else {
                            bodycurve4 = new ImageIcon("Images/bodycurve4.png");
                            bodycurve4.paintIcon(this, g, snakex[a], snakey[a]);
                        }
                    }
                }
            }

            if(a==lengthofsnake-1) {
                if(snakey[a]==snakey[a-1] && snakex[a]<snakex[a-1]) {
                    //tailright
                    if(snakex[a]==xmin && snakex[a-1]==xmax) {
                        tailleft = new ImageIcon("Images/tailleft.png");
                        tailleft.paintIcon(this, g, snakex[a], snakey[a]);
                    } else {
                        tailright = new ImageIcon("Images/tailright.png");
                        tailright.paintIcon(this, g, snakex[a], snakey[a]);
                    }
                } else if(snakey[a]==snakey[a-1] && snakex[a]>snakex[a-1]) {
                    //tailleft
                    if(snakex[a]==xmax && snakex[a-1]==xmin) {
                        tailright = new ImageIcon("Images/tailright.png");
                        tailright.paintIcon(this, g, snakex[a], snakey[a]);
                    } else {
                        tailleft = new ImageIcon("Images/tailleft.png");
                        tailleft.paintIcon(this, g, snakex[a], snakey[a]);
                    }
                } else if(snakey[a]>snakey[a-1] && snakex[a]==snakex[a-1]) {
                    //tailup
                    if(snakey[a]==ymax && snakey[a-1]==ymin) {
                        taildown = new ImageIcon("Images/taildown.png");
                        taildown.paintIcon(this, g, snakex[a], snakey[a]);
                    } else {
                        tailup = new ImageIcon("Images/tailup.png");
                        tailup.paintIcon(this, g, snakex[a], snakey[a]);
                    }
                } else if(snakey[a]<snakey[a-1] && snakex[a]==snakex[a-1]) {
                    //taildown
                    if(snakey[a]==ymin && snakey[a-1]==ymax) {
                        tailup = new ImageIcon("Images/tailup.png");
                        tailup.paintIcon(this, g, snakex[a], snakey[a]);
                    } else {
                        taildown = new ImageIcon("Images/taildown.png");
                        taildown.paintIcon(this, g, snakex[a], snakey[a]);
                    }
                }
            }
        }

        // draw snakehead
        ImageIcon headright, headleft, headup, headdown;
        if(!started) {
            headright = new ImageIcon(frameheadright);
            headright.paintIcon(this, g, snakex[0], snakey[0]);
        } else {
            if(right) {
                headright = new ImageIcon(frameheadright);
                headright.paintIcon(this, g, snakex[0], snakey[0]);
            } else if(left) {
                headleft = new ImageIcon(frameheadleft);
                headleft.paintIcon(this, g, snakex[0], snakey[0]);
            } else if(up) {
                headup = new ImageIcon(frameheadup);
                headup.paintIcon(this, g, snakex[0], snakey[0]);
            } else if(down) {
                headdown = new ImageIcon(frameheaddown);
                headdown.paintIcon(this, g, snakex[0], snakey[0]);
            }
        }

        //draw apple
        if((applex[xpos]==snakex[0]) && (appley[ypos]==snakey[0])) {
            score++;
            lengthofsnake++;
            Random random = new Random();
            xpos = random.nextInt((xmax-xmin)/resol+1);
            ypos = random.nextInt((ymax-ymin)/resol+1);
            do {
                for(int b=1; b<lengthofsnake; b++) {
                    if(appleinsnake) break;
                    if((applex[xpos]==snakex[b]) && (appley[ypos]==snakey[b])) {
                        appleinsnake = true;
                        break;
                    }
                }
                if(!appleinsnake) break;

                xpos = random.nextInt((xmax-xmin)/resol+1);
                ypos = random.nextInt((ymax-ymin)/resol+1);
                appleinsnake = false;

                for(int b=1; b<lengthofsnake; b++) {
                    if((applex[xpos]==snakex[b]) && (appley[ypos]==snakey[b])) {
                        appleinsnake = true;
                        break;
                    }
                }
            } while(appleinsnake);
        }
        appleimage.paintIcon(this, g, applex[xpos]-5, appley[ypos]-5);

        //game over
        for(int b=1; b<lengthofsnake; b++) {
            if(snakex[b]==snakex[0] && snakey[b]==snakey[0]) {

                stopped = true;
                right = false;
                left = false;
                up = false;
                down = false;

                ImageIcon gameoverimage = new ImageIcon("Images/gameovertt.png");
                gameoverimage.paintIcon(this, g, 120, 120);
            }
        }

        waiting = false;

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!paused) {
            if(right) {
                for(int r=lengthofsnake-2; r>=0; r--) {
                    snakey[r+1] = snakey[r];
                }
                for(int r=lengthofsnake-1; r>=0; r--) {
                    if(r==0) {
                        snakex[r] = snakex[r] + resol;
                    } else {
                        snakex[r] = snakex[r-1];
                    }
                    if(snakex[r] > xmax) {
                        snakex[r] = xmin;
                    }
                }
                repaint();
            }
            if(left){
                for(int r=lengthofsnake-2; r>=0; r--) {
                    snakey[r+1] = snakey[r];
                }
                for(int r=lengthofsnake-1; r>=0; r--) {
                    if(r==0) {
                        snakex[r] = snakex[r] - resol;
                    } else {
                        snakex[r] = snakex[r-1];
                    }
                    if(snakex[r] < xmin) {
                        snakex[r] = xmax;
                    }
                }
                repaint();
            }
            if(up) {
                for(int r=lengthofsnake-2; r>=0; r--) {
                    snakex[r+1] = snakex[r];
                }
                for(int r=lengthofsnake-1; r>=0; r--) {
                    if(r==0) {
                        snakey[r] = snakey[r] - resol;
                    } else {
                        snakey[r] = snakey[r-1];
                    }
                    if(snakey[r] < ymin) {
                        snakey[r] = ymax;
                    }
                }
                repaint();
            }
            if(down) {
                for(int r=lengthofsnake-2; r>=0; r--) {
                    snakex[r+1] = snakex[r];
                }
                for(int r=lengthofsnake-1; r>=0; r--) {
                    if(r==0) {
                        snakey[r] = snakey[r] + resol;
                    } else {
                        snakey[r] = snakey[r-1];
                    }
                    if(snakey[r] > ymax) {
                        snakey[r] = ymin;
                    }
                }
                repaint();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(stopped) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                started = false;
                score = 0;
                lengthofsnake = 3;
                snakex = getSnakexArray();
                snakey = getSnakeyArray();
                right = false;
                left = false;
                up = false;
                down = false;
                stopped = false;
                repaint();
            }
        }

        if(!stopped && started) {
            if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                if(!paused) {
                    paused = true;
                } else {
                    paused = false;
                }
            }
        }

        if(!stopped && !waiting && !paused) {
            if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                started = true;
                if(left==true) {
                    right = false;
                    left = true;
                } else {
                    right = true;
                    left = false;
                }
                up = false;
                down = false;
                waiting = true;
            }
            if(started) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    started = true;
                    if(right==true) {
                        left = false;
                        right = true;
                    } else {
                        right = false;
                        left = true;
                    }
                    up = false;
                    down = false;
                    waiting = true;
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_UP) {
                started = true;
                if(down==true) {
                    up = false;
                    down = true;
                } else {
                    up = true;
                    down = false;
                }
                right = false;
                left = false;
                waiting = true;
            }
            if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                started = true;
                if(up==true) {
                    down = false;
                    up = true;
                } else {
                    up = false;
                    down = true;
                }
                right = false;
                left = false;
                waiting = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

}



public class Main {
    public static void main(String[] args) {

        JFrame obj = new JFrame();
        Gameplay gameplay = new Gameplay();

        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.getContentPane().setPreferredSize(new Dimension(760,760));
        obj.setBackground(Color.BLACK);
        obj.pack();
        obj.setLocationRelativeTo(null);
        obj.setResizable(false);
        obj.setVisible(true);
        obj.getContentPane().add(gameplay);

    }
}
