package GUI;

import engine.Game;
import engine.Player;
import model.world.Champion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class intro extends JFrame{
    JButton ew;
    JLabel background;
    JFrame f ;
    Game game;
    JPanel panel;
    //JPanel panel2 ;
    JTextArea player1;
    JTextArea player2 ;
    public intro() {
        f = new JFrame();
        f.setTitle("Marvel: Ultimate War");
        f.setBounds(10,10,1200,800);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout());
        panel=new JPanel();
        panel.setSize(1200,800);
        //panel2 = new JPanel();
        //panel2.setSize(1200 , 800);
        background=new JLabel();
        background.setIcon(new ImageIcon("intro2.jpg"));
        background.setHorizontalAlignment(SwingConstants.CENTER);
        background.setVerticalAlignment(SwingConstants.CENTER);
        ew =new JButton("Start") ;
        ew.setBounds(850,700,100,100);
        ew.setFont(new Font("Monaco", Font.BOLD, 20));
        ew.addActionListener(e->{
            try {
                enterNames();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        ew.setBounds(850,900,100,100);
        ew.setFont(new Font("Monaco", Font.BOLD, 20));
        panel.setLayout(new BorderLayout());
        panel.setAlignmentX(SwingConstants.CENTER);
        panel.setAlignmentY(SwingConstants.CENTER);
        background.add(ew);
        panel.add(background);
        f.add(panel);
        f.setVisible(true);

    }
    public void enterNames() throws IOException {
        background.remove(ew);
        String first= JOptionPane.showInputDialog(null,"Enter First Player Name","Enter Name", JOptionPane.QUESTION_MESSAGE);
        String second=JOptionPane.showInputDialog(null,"Enter Second Player Name","Enter Name", JOptionPane.QUESTION_MESSAGE);
        Player p1= new Player(first);
        Player p2= new Player(second);
        game=new Game(p1,p2);
        loadChampions();
        // f.setContentPane(panel);
        f.revalidate();
        f.repaint();


    }

    /*
     * public void startGame() { JPanel p = new JPanel();
     * p.setPreferredSize(500,500);
     *
     * }
     */
    public void loadChampions() throws IOException
    {
        panel.removeAll();
        panel.setLayout(new GridLayout(3,5));
        // panel.setPreferredSize(new Dimension(800,800));
        game.loadAbilities("Abilities.csv");
        game.loadChampions("Champions.csv");
        ArrayList<Champion> a = game.getAvailableChampions();
        player1=new JTextArea(game.getFirstPlayer().getName());
        player1.setPreferredSize(new Dimension(200,800));
        player1.setFont(new Font("Monaco",Font.BOLD,12));
        player1.setEditable(false);
        f.add(player1,BorderLayout.WEST);
        player2 = new JTextArea(game.getSecondPlayer().getName());
        player2.setPreferredSize(new Dimension(200 , 800));
        player2.setFont(new Font("Monaco" ,Font.BOLD , 12));
        player2.setEditable(false);
        f.add(player2,BorderLayout.EAST);
        JOptionPane.showMessageDialog(null,"The first champion you choose will be the leader! "+
                        game.getFirstPlayer().getName()+" choose first"
                ,"Champions selection",JOptionPane.INFORMATION_MESSAGE);
        for (int x = 0; x < a.size(); x++){
            JButton j = new JButton();
            // ActionListener z = new ActionListener();
//          j.setPreferredSize(new Dimension(20,20));
            int finalX = x;
            j.addActionListener(e -> {
                if (game.getFirstPlayer().getTeam().size()==0){
                    game.getFirstPlayer().getTeam().add(a.get(finalX));
                    game.getFirstPlayer().setLeader(a.get(finalX));
                    player1.setText(player1.getText()+"\n"+a.get(finalX).getName()+" <-Leader" );
                }
                //else if (game.getFirstPlayer().getTeam().size()<3 && game.getFirstPlayer().getTeam().size()!=0){
                else if (game.getFirstPlayer().getTeam().size()<3){
                    game.getFirstPlayer().getTeam().add(a.get(finalX));
                    player1.setText(player1.getText()+"\n"+a.get(finalX).getName());
                }
                else if (game.getSecondPlayer().getTeam().size()==0){
                    game.getSecondPlayer().getTeam().add(a.get(finalX));
                    game.getSecondPlayer().setLeader(a.get(finalX));
                    player2.setText(player2.getText()+"\n"+a.get(finalX).getName()+" <-Leader" );
                }
                else if (game.getSecondPlayer().getTeam().size()<2){
                    game.getSecondPlayer().getTeam().add(a.get(finalX));
                    player2.setText(player2.getText()+"\n"+a.get(finalX).getName());
                }
                else{
                    game.getSecondPlayer().getTeam().add(a.get(finalX));
                    new game_aha_aha(game);
                    f.dispose();
                }
                j.setEnabled(false);
            });
            j.setText("<html>" + (a.get(x).getName()) + "<br />Max HP: " + (a.get(x)).getMaxHP() + "<br /> Attack Damage: "
                    + (a.get(x)).getAttackDamage() + "<br /> Mana Points: " + (a.get(x)).getMana() + "<br /> Speed: " + (a.get(x)).getSpeed() + "<br /> Attack Range: "
                    + (a.get(x)).getAttackRange() + "<br /> Action Points: " + (a.get(x)).getMaxActionPointsPerTurn() + "</html>");
            panel.add(j);

        }
        //f.add(panel,BorderLayout.CENTER);
        f.revalidate();
        f.repaint();
    }

    public static void main(String[]args) throws IOException{
        intro i =  new intro();


    }
    /*
     * game.loadAbilities("Abilities.csv"); game.loadChampions("Champions.csv");
     * JPanel champs=new JPanel (); for (int i=0;
     * i<game.getAvailableChampions().size();i++ ){ JButton j = new JButton();
     * j.setText(game.getAvailableChampions().get(i).getName() + "/n" +
     * game.getAvailableChampions().get(i).getMaxHP()); champs.add(j); }
     * this.add(champs);
     */


    /*
     * champs.setBounds(400,330,300,700); f.add(champs); f.setLayout(new
     * BorderLayout()); f.setContentPane(background); champs.setVisible(true);
     * champs.addActionListener(e -> { try { loadChampions(); } catch (IOException
     * ex) { throw new RuntimeException(ex); } });
     */
}