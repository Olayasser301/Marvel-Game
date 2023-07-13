package GUI;

import engine.Game;
import engine.PriorityQueue;
import exceptions.*;
import model.abilities.*;
import model.effects.Effect;
import model.world.Champion;
import model.world.Cover;
import model.world.Direction;
import model.world.Hero;
import engine.Game;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class game_aha_aha extends JFrame implements ActionListener {
    JPanel board = new JPanel();
    JPanel actions = new JPanel();
    JTextArea JA = new JTextArea();
    JPanel panel = new JPanel();
    Game g;
    JPanel btns;
    JButton attackbtn;
    JButton dwn;
    JButton up;
    JButton lft;
    JButton rght;

    JButton dwn1;
    JButton up1;
    JButton lft1;
    JButton rght1;
    JButton leaderbtn;
    JButton ab1;
    JButton ab2;
    JButton ab3;
    JButton ab4;
    JButton dwn2;
    JButton up2;
    JButton lft2;
    JButton rght2;

    public game_aha_aha(Game game) {
        this.g = game;

        g.placeChampions();
        //g.placeCovers();
        g.prepareChampionTurns();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.actions.setLayout(new GridLayout(3, 3));
        this.setVisible(true);
        // this.setSize(new Dimension(8000,8000));
        this.setBounds(100, 100, 1000, 800);
        this.JA.setPreferredSize(new Dimension(500, this.getHeight() - 200));
        panel.setPreferredSize(new Dimension(500, 200));
        panel.setLayout(new FlowLayout(FlowLayout.LEADING));
        board.setLayout(new GridLayout(5, 5));
        this.board.setBackground(Color.black);
        this.board.setPreferredSize(new Dimension(200, this.getHeight()));
        String s;
        for (int i = 4; i >= 0; i--) {
            for (int j = 0; j < 5; j++) {
                s = "";
                JButton btn = new JButton();
                board.add(btn);
                if (g.getBoard()[i][j] != null) {
                    if (g.getBoard()[i][j] instanceof Cover) {
                        btn.setText("<html> Cover" + "<br> HP: " + ((Cover) g.getBoard()[i][j]).getCurrentHP());
                        btn.setBackground(Color.magenta);
                    } else {
                        btn.setText("<html>" + ((Champion) g.getBoard()[i][j]).getName() + "<br> HP: " + ((Champion) g.getBoard()[i][j]).getCurrentHP());
                        btn.setBackground(Color.blue);
                        s += "<html> Mana: " + ((Champion) g.getBoard()[i][j]).getMana() +
                                "<br> Speed: " + ((Champion) g.getBoard()[i][j]).getSpeed() +
                                "<br> Max Action points: " + ((Champion) g.getBoard()[i][j]).getMaxActionPointsPerTurn() +
                                "<br> Type: " + g.getBoard()[i][j].getClass().getSimpleName() +
                                "<br> Leader: ";
                        if (g.getBoard()[i][j].equals(g.getCurrentChampion())) {
                            btn.setBackground(Color.yellow);
                            s += "yes";
                        } else s += "no";
                        for (Effect e : ((Champion) g.getBoard()[i][j]).getAppliedEffects())
                            s += "<br> " + e.getName() + ", Duration:" + e.getDuration();
                        btn.setToolTipText(s);
                    }
                }
            }
        }

        this.actions.setPreferredSize(new Dimension(200, 200));
        JButton movebtn = new JButton("Move");
        this.actions.add(movebtn);
        movebtn.addActionListener(e -> move());
        rght1 = new JButton("Right");
        lft1 = new JButton("Left");
        up1 = new JButton("UP");
        dwn1 = new JButton("Down");
        up1.addActionListener(u -> {
            try {
                g.move(Direction.UP);
                update();
            } catch (NotEnoughResourcesException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
            } catch (UnallowedMovementException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Unallowed Movement", JOptionPane.ERROR_MESSAGE);
            }
        });
        dwn1.addActionListener(u -> {
            try {
                g.move(Direction.DOWN);
                update();
            } catch (NotEnoughResourcesException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
            } catch (UnallowedMovementException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Unallowed Movement", JOptionPane.ERROR_MESSAGE);
            }
        });
        rght1.addActionListener(u -> {
            try {
                g.move(Direction.RIGHT);
                update();
            } catch (NotEnoughResourcesException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
            } catch (UnallowedMovementException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Unallowed Movement", JOptionPane.ERROR_MESSAGE);
            }
        });
        lft1.addActionListener(u -> {
            try {
                g.move(Direction.LEFT);
                update();
            } catch (NotEnoughResourcesException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
            } catch (UnallowedMovementException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Unallowed Movement", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(up1);
        panel.add(dwn1);
        panel.add(rght1);
        panel.add(lft1);
        dwn1.setVisible(false);
        up1.setVisible(false);
        rght1.setVisible(false);
        lft1.setVisible(false);
        attackbtn = new JButton("Attack");
        attackbtn.addActionListener(e -> attack());
        this.actions.add(attackbtn);

        rght = new JButton("Right");
        lft = new JButton("Left");
        up = new JButton("UP");
        dwn = new JButton("Down");
        up.addActionListener(u -> {
            try {
                g.attack(Direction.UP);
                update();
            } catch (NotEnoughResourcesException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
            } catch (ChampionDisarmedException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Current Champion is Disarmed", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidTargetException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Target", JOptionPane.ERROR_MESSAGE);
            }
        });
        dwn.addActionListener(u -> {
            try {
                g.attack(Direction.DOWN);
                update();
            } catch (NotEnoughResourcesException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
            } catch (ChampionDisarmedException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Current Champion is Disarmed", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidTargetException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Target", JOptionPane.ERROR_MESSAGE);
            }
        });
        rght.addActionListener(u -> {
            try {
                g.attack(Direction.RIGHT);
                update();
            } catch (NotEnoughResourcesException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
            } catch (ChampionDisarmedException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Current Champion is Disarmed", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidTargetException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Target", JOptionPane.ERROR_MESSAGE);
            }
        });
        lft.addActionListener(u -> {
            try {
                g.attack(Direction.LEFT);
                update();
            } catch (NotEnoughResourcesException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
            } catch (ChampionDisarmedException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Current Champion is Disarmed", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidTargetException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Target", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(up);
        panel.add(dwn);
        panel.add(rght);
        panel.add(lft);
        dwn.setVisible(false);
        up.setVisible(false);
        rght.setVisible(false);
        lft.setVisible(false);
        //btns.setPreferredSize(new Dimension(500,200));
        JButton castbtn = new JButton("<html> Cast" + "<br> Ability");
        castbtn.addActionListener(e -> castAbility());
        this.actions.add(castbtn);

        if (g.getCurrentChampion().getAbilities().size() > 3) {
            ab4 = new JButton(g.getCurrentChampion().getAbilities().get(3).getName());
            panel.add(ab4);
        }
        leaderbtn = new JButton("<html>" + "Cast Leader" + "<br />" + "Ability" + "<html>");
        leaderbtn.addActionListener(e -> {
            try {
                g.useLeaderAbility();
                update();
            } catch (LeaderAbilityAlreadyUsedException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Leader ability was already used", JOptionPane.ERROR_MESSAGE);
            } catch (LeaderNotCurrentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "This champion is currently not the leader ", JOptionPane.ERROR_MESSAGE);
            }
        });
        this.actions.add(leaderbtn);
        this.add(board, BorderLayout.CENTER);
        this.JA.setEditable(false);
        this.JA.setText(g.getFirstPlayer().getName());
        this.JA.append("  V.S  " + g.getSecondPlayer().getName());
        JA.append("\n" + "\n" + g.getFirstPlayer().getName() + " champions:" + "\n");
        for (Champion c : g.getFirstPlayer().getTeam()) {
            JA.append(" " + c.getName());
            if (c.equals(g.getFirstPlayer().getLeader()))
                JA.append(": Leader");
            JA.append("\n");
        }
        JA.append(g.getSecondPlayer().getName() + " champions:" + "\n");
        for (Champion c : g.getSecondPlayer().getTeam()) {
            JA.append("  " + c.getName());
            if (c.equals(g.getSecondPlayer().getLeader()))
                JA.append(": Leader");
            JA.append("\n");
        }
        JA.append("Current Champion: " + "\n" + g.getCurrentChampion().getName() + "\n" +
                "Type: " + g.getCurrentChampion().getClass().getSimpleName() + "\n" +
                "Current HP: " + g.getCurrentChampion().getCurrentHP() + "\n" +
                "Mana: " + g.getCurrentChampion().getMana() + "\n" +
                "Action Points: " + g.getCurrentChampion().getCurrentActionPoints() + "\n" +
                "Attack Range: " + g.getCurrentChampion().getAttackRange() + "\n" +
                "Attack Damage: " + g.getCurrentChampion().getAttackDamage() + "\n" + "\n" +
                "Abilities:");
        for (Ability a : g.getCurrentChampion().getAbilities()) {
            JA.append("\n" + a.getName() + "\n" + "Type: " + a.getClass().getSimpleName() + "\n" +
                    "Area of Effect: " + a.getCastArea().name() + "\n" +
                    "Cast Range: " + a.getCastRange() + "\n" +
                    "Mana Cost: " + a.getManaCost() + "\n" +
                    "Required Action Points: " + a.getRequiredActionPoints() + "\n" +
                    "Base Cooldown: " + a.getBaseCooldown() + "\n" +
                    "Current Cooldown: " + a.getCurrentCooldown() + "\n");
            if (a instanceof HealingAbility)
                JA.append("Heal Amount:" + ((HealingAbility) a).getHealAmount() + "\n");
            else if (a instanceof CrowdControlAbility) {
                JA.append("Effect: " + ((CrowdControlAbility) a).getEffect().getName() + "\n");
            } else
                JA.append("Damage Amount: " + ((DamagingAbility) a).getDamageAmount() + "\n");
        }
        PriorityQueue turnordr = new PriorityQueue(g.getTurnOrder().size());
        turnordr.insert(g.getTurnOrder().remove());
        JA.append("Next: ");
        while (!g.getTurnOrder().isEmpty()) {
            JA.append(((Champion) g.getTurnOrder().peekMin()).getName() + ", ");
            turnordr.insert(g.getTurnOrder().remove());
        }
        while (!turnordr.isEmpty())
            g.getTurnOrder().insert(turnordr.remove());

        this.add(actions, BorderLayout.EAST);
        this.add(panel, BorderLayout.SOUTH);
        this.add(JA, BorderLayout.WEST);
        JButton endTurnButton = new JButton("End Turn");
        this.actions.add(endTurnButton);
        endTurnButton.setPreferredSize(new Dimension(500, 500));
        endTurnButton.addActionListener(e -> endTurn());
        this.revalidate();
        this.repaint();

    }

    public void update() {
        if (g.checkGameOver()!=null){
            gameOver();
            return;
        }
        board.removeAll();
        String s = "";
        for (int i = 4; i >= 0; i--) {
            for (int j = 0; j < 5; j++) {
                s = "";
                JButton btn = new JButton();
                board.add(btn);
                if (g.getBoard()[i][j] != null) {
                    if (g.getBoard()[i][j] instanceof Cover) {
                        btn.setText("<html> Cover" + "<br> HP: " + ((Cover) g.getBoard()[i][j]).getCurrentHP());
                        btn.setBackground(Color.magenta);
                    } else {
                        btn.setText("<html>" + ((Champion) g.getBoard()[i][j]).getName() + "<br> HP: " + ((Champion) g.getBoard()[i][j]).getCurrentHP());
                        btn.setBackground(Color.blue);
                        s += "<html> Mana: " + ((Champion) g.getBoard()[i][j]).getMana() +
                                "<br> Speed: " + ((Champion) g.getBoard()[i][j]).getSpeed() +
                                "<br> Max Action points: " + ((Champion) g.getBoard()[i][j]).getMaxActionPointsPerTurn() +
                                "<br> Type: " + g.getBoard()[i][j].getClass().getSimpleName() +
                                "<br> Leader: ";
                        if (g.getBoard()[i][j].equals(g.getCurrentChampion())) {
                            btn.setBackground(Color.yellow);
                            s += "yes";
                        } else s += "no";
                        for (Effect e : ((Champion) g.getBoard()[i][j]).getAppliedEffects())
                            s += "<br> " + e.getName() + ", Duration:" + e.getDuration();
                        btn.setToolTipText(s);
                    }
                }
            }
        }
        panel.removeAll();
        this.JA.setText(g.getFirstPlayer().getName());
        this.JA.append("  V.S  " + g.getSecondPlayer().getName());
        JA.append("\n" + "\n" + g.getFirstPlayer().getName() + " champions:" + "\n");
        for (Champion c : g.getFirstPlayer().getTeam()) {
            JA.append(" " + c.getName());
            if (c.equals(g.getFirstPlayer().getLeader()))
                JA.append(": Leader");
            JA.append("\n");
        }
        JA.append("Leader Ability used: ");
        if (g.isFirstLeaderAbilityUsed()) {
            JA.append("Yes" + "\n");
        } else {
            JA.append("No" + "\n");
        }
        JA.append(g.getSecondPlayer().getName() + " champions:" + "\n");
        for (Champion c : g.getSecondPlayer().getTeam()) {
            JA.append("  " + c.getName());
            if (c.equals(g.getSecondPlayer().getLeader()))
                JA.append(": Leader");
            JA.append("\n");
        }

        JA.append("Leader Ability used: ");
        if (g.isSecondLeaderAbilityUsed()) {
            JA.append("Yes" + "\n");
        } else {
            JA.append("No" + "\n");
        }
        JA.append("Current Champion: " + "\n" + g.getCurrentChampion().getName() + "\n" +
                "Type: " + g.getCurrentChampion().getClass().getSimpleName() + "\n" +
                "Current HP: " + g.getCurrentChampion().getCurrentHP() + "\n" +
                "Mana: " + g.getCurrentChampion().getMana() + "\n" +
                "Action Points: " + g.getCurrentChampion().getCurrentActionPoints() + "\n" +
                "Attack Range: " + g.getCurrentChampion().getAttackRange() + "\n" +
                "Attack Damage: " + g.getCurrentChampion().getAttackDamage() + "\n" + "\n" +
                "Abilities:");
        for (Ability a : g.getCurrentChampion().getAbilities()) {
            JA.append("\n" + a.getName() + "\n" + "Type: " + a.getClass().getSimpleName() + "\n" +
                    "Area of Effect: " + a.getCastArea().name() + "\n" +
                    "Cast Range: " + a.getCastRange() + "\n" +
                    "Mana Cost: " + a.getManaCost() + "\n" +
                    "Required Action Points: " + a.getRequiredActionPoints() + "\n" +
                    "Base Cooldown: " + a.getBaseCooldown() + "\n" +
                    "Current Cooldown: " + a.getCurrentCooldown() + "\n");
            if (a instanceof HealingAbility)
                JA.append("Heal Amount:" + ((HealingAbility) a).getHealAmount() + "\n");
            else if (a instanceof CrowdControlAbility) {
                JA.append("Effect: " + ((CrowdControlAbility) a).getEffect().getName() + "\n");
            } else
                JA.append("Damage Amount: " + ((DamagingAbility) a).getDamageAmount() + "\n");
        }
        JA.append("Applied Effects: " + '\n');
        for (Effect e : g.getCurrentChampion().getAppliedEffects())
            JA.append(e.getName() + " Duration:" + e.getDuration() + "\n");
        PriorityQueue turnordr = new PriorityQueue(g.getTurnOrder().size());
        turnordr.insert(g.getTurnOrder().remove());
        JA.append("Next: ");
        while (!g.getTurnOrder().isEmpty()) {
            JA.append(((Champion) g.getTurnOrder().peekMin()).getName() + ", ");
            turnordr.insert(g.getTurnOrder().remove());
        }
        while (!turnordr.isEmpty())
            g.getTurnOrder().insert(turnordr.remove());

        this.revalidate();
        this.repaint();
    }

    public void attack() {
        panel.removeAll();
        panel.add(up);
        panel.add(dwn);
        panel.add(rght);
        panel.add(lft);
        rght.setPreferredSize(new Dimension(100, 50));
        lft.setPreferredSize(new Dimension(100, 50));
        up.setPreferredSize(new Dimension(100, 50));
        dwn.setPreferredSize(new Dimension(100, 50));

        dwn.setVisible(true);
        up.setVisible(true);
        rght.setVisible(true);
        lft.setVisible(true);

        /**/
    }

    public void move() {
        panel.removeAll();
        panel.add(up1);
        panel.add(dwn1);
        panel.add(rght1);
        panel.add(lft1);
        rght1.setPreferredSize(new Dimension(100, 50));
        lft1.setPreferredSize(new Dimension(100, 50));
        up1.setPreferredSize(new Dimension(100, 50));
        dwn1.setPreferredSize(new Dimension(100, 50));

        dwn1.setVisible(true);
        up1.setVisible(true);
        rght1.setVisible(true);
        lft1.setVisible(true);

    }

    public void castAbility() {
        panel.removeAll();
        ab1 = new JButton(g.getCurrentChampion().getAbilities().get(0).getName());
        ab2 = new JButton(g.getCurrentChampion().getAbilities().get(1).getName());
        ab3 = new JButton(g.getCurrentChampion().getAbilities().get(2).getName());
        panel.add(ab1);
        panel.add(ab2);
        panel.add(ab3);
        rght2 = new JButton("Right");
        lft2 = new JButton("Left");
        up2 = new JButton("UP");
        dwn2 = new JButton("Down");
        panel.add(up2);
        panel.add(dwn2);
        panel.add(rght2);
        panel.add(lft2);
        dwn2.setVisible(false);
        up2.setVisible(false);
        rght2.setVisible(false);
        lft2.setVisible(false);
        ab1.addActionListener(e -> {
            Ability a = g.getCurrentChampion().getAbilities().get(0);
            if (a.getCastArea() == AreaOfEffect.SELFTARGET || a.getCastArea() == AreaOfEffect.SURROUND || a.getCastArea() == AreaOfEffect.TEAMTARGET) {
                try {
                    g.castAbility(a);
                    update();
                } catch (NotEnoughResourcesException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
                } catch (AbilityUseException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Cannot use Ability", JOptionPane.ERROR_MESSAGE);
                } catch (CloneNotSupportedException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (a.getCastArea() == AreaOfEffect.DIRECTIONAL) {
                dwn2.setVisible(true);
                up2.setVisible(true);
                rght2.setVisible(true);
                lft2.setVisible(true);
                dwn2.addActionListener(e1 -> {
                    try {
                        g.castAbility(a, Direction.DOWN);
                        update();
                    } catch (NotEnoughResourcesException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
                    } catch (AbilityUseException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Cannot use Ability", JOptionPane.ERROR_MESSAGE);
                    } catch (CloneNotSupportedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                up2.addActionListener(e1 -> {
                    try {
                        g.castAbility(a, Direction.UP);
                        update();
                    } catch (NotEnoughResourcesException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
                    } catch (AbilityUseException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Cannot use Ability", JOptionPane.ERROR_MESSAGE);
                    } catch (CloneNotSupportedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                lft2.addActionListener(e1 -> {
                    try {
                        g.castAbility(a, Direction.LEFT);
                        update();
                    } catch (NotEnoughResourcesException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
                    } catch (AbilityUseException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Cannot use Ability", JOptionPane.ERROR_MESSAGE);
                    } catch (CloneNotSupportedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                rght2.addActionListener(e1 -> {
                    try {
                        g.castAbility(a, Direction.RIGHT);
                        update();
                    } catch (NotEnoughResourcesException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
                    } catch (AbilityUseException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Cannot use Ability", JOptionPane.ERROR_MESSAGE);
                    } catch (CloneNotSupportedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            } else {
                int x = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter X Coordinates", "Insert Location of Target Cell", JOptionPane.QUESTION_MESSAGE));
                int y = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Y Coordinates", "Insert Location of Target Cell", JOptionPane.QUESTION_MESSAGE));
                try {
                    g.castAbility(a, y, x);
                    update();
                } catch (NotEnoughResourcesException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
                } catch (AbilityUseException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Cannot use Ability", JOptionPane.ERROR_MESSAGE);
                } catch (InvalidTargetException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Target", JOptionPane.ERROR_MESSAGE);
                } catch (CloneNotSupportedException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });
        ab2.addActionListener(e -> {
            Ability a = g.getCurrentChampion().getAbilities().get(1);
            if (a.getCastArea() == AreaOfEffect.SELFTARGET || a.getCastArea() == AreaOfEffect.SURROUND || a.getCastArea() == AreaOfEffect.TEAMTARGET) {
                try {
                    g.castAbility(a);
                    update();
                } catch (NotEnoughResourcesException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
                } catch (AbilityUseException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Cannot use Ability", JOptionPane.ERROR_MESSAGE);
                } catch (CloneNotSupportedException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (a.getCastArea() == AreaOfEffect.DIRECTIONAL) {
                dwn2.setVisible(true);
                up2.setVisible(true);
                rght2.setVisible(true);
                lft2.setVisible(true);
                dwn2.addActionListener(e1 -> {
                    try {
                        g.castAbility(a, Direction.DOWN);
                        update();
                    } catch (NotEnoughResourcesException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
                    } catch (AbilityUseException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Cannot use Ability", JOptionPane.ERROR_MESSAGE);
                    } catch (CloneNotSupportedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                up2.addActionListener(e1 -> {
                    try {
                        g.castAbility(a, Direction.UP);
                        update();
                    } catch (NotEnoughResourcesException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
                    } catch (AbilityUseException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Cannot use Ability", JOptionPane.ERROR_MESSAGE);
                    } catch (CloneNotSupportedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                lft2.addActionListener(e1 -> {
                    try {
                        g.castAbility(a, Direction.LEFT);
                        update();
                    } catch (NotEnoughResourcesException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
                    } catch (AbilityUseException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Cannot use Ability", JOptionPane.ERROR_MESSAGE);
                    } catch (CloneNotSupportedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                rght2.addActionListener(e1 -> {
                    try {
                        g.castAbility(a, Direction.RIGHT);
                        update();
                    } catch (NotEnoughResourcesException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
                    } catch (AbilityUseException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Cannot use Ability", JOptionPane.ERROR_MESSAGE);
                    } catch (CloneNotSupportedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            } else {
                int x = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter X Coordinates", "Insert Location of Target Cell", JOptionPane.QUESTION_MESSAGE));
                int y = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Y Coordinates", "Insert Location of Target Cell", JOptionPane.QUESTION_MESSAGE));
                try {
                    g.castAbility(a, y, x);
                    update();
                } catch (NotEnoughResourcesException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
                } catch (AbilityUseException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Cannot use Ability", JOptionPane.ERROR_MESSAGE);
                } catch (InvalidTargetException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Target", JOptionPane.ERROR_MESSAGE);
                } catch (CloneNotSupportedException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });
        ab3.addActionListener(e -> {
            Ability a = g.getCurrentChampion().getAbilities().get(2);
            if (a.getCastArea() == AreaOfEffect.SELFTARGET || a.getCastArea() == AreaOfEffect.SURROUND || a.getCastArea() == AreaOfEffect.TEAMTARGET) {
                try {
                    g.castAbility(a);
                    update();
                } catch (NotEnoughResourcesException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
                } catch (AbilityUseException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Cannot use Ability", JOptionPane.ERROR_MESSAGE);
                } catch (CloneNotSupportedException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (a.getCastArea() == AreaOfEffect.DIRECTIONAL) {
                dwn2.setVisible(true);
                up2.setVisible(true);
                rght2.setVisible(true);
                lft2.setVisible(true);
                dwn2.addActionListener(e1 -> {
                    try {
                        g.castAbility(a, Direction.DOWN);
                        update();
                    } catch (NotEnoughResourcesException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
                    } catch (AbilityUseException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Cannot use Ability", JOptionPane.ERROR_MESSAGE);
                    } catch (CloneNotSupportedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                up2.addActionListener(e1 -> {
                    try {
                        g.castAbility(a, Direction.UP);
                        update();
                    } catch (NotEnoughResourcesException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
                    } catch (AbilityUseException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Cannot use Ability", JOptionPane.ERROR_MESSAGE);
                    } catch (CloneNotSupportedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                lft2.addActionListener(e1 -> {
                    try {
                        g.castAbility(a, Direction.LEFT);
                        update();
                    } catch (NotEnoughResourcesException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
                    } catch (AbilityUseException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Cannot use Ability", JOptionPane.ERROR_MESSAGE);
                    } catch (CloneNotSupportedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                rght2.addActionListener(e1 -> {
                    try {
                        g.castAbility(a, Direction.RIGHT);
                        update();
                    } catch (NotEnoughResourcesException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
                    } catch (AbilityUseException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Cannot use Ability", JOptionPane.ERROR_MESSAGE);
                    } catch (CloneNotSupportedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            } else {
                int x = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter X Coordinates", "Insert Location of Target Cell", JOptionPane.QUESTION_MESSAGE));
                int y = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Y Coordinates", "Insert Location of Target Cell", JOptionPane.QUESTION_MESSAGE));
                try {
                    g.castAbility(a, y, x);
                    update();
                } catch (NotEnoughResourcesException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Not enough resources", JOptionPane.ERROR_MESSAGE);
                } catch (AbilityUseException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Cannot use Ability", JOptionPane.ERROR_MESSAGE);
                } catch (InvalidTargetException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Target", JOptionPane.ERROR_MESSAGE);
                } catch (CloneNotSupportedException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });
        ab1.setPreferredSize(new Dimension(150, 50));
        ab2.setPreferredSize(new Dimension(150, 50));
        ab3.setPreferredSize(new Dimension(150, 50));

    }
    public void gameOver(){
        this.dispose();
        JFrame win = new JFrame();
        win.setBounds(100,100,1000,800);
        win.setVisible(true);
        JLabel winner=new JLabel(g.checkGameOver().getName()+" is the winner!");
        JPanel winn=new JPanel();
        winn.add(winner);
        win.add(winn);
    }
    public void endTurn() {
        /*
         * String lastChamp = this.g.getCurrentChampion().getName(); for(int i = 0; i <
         * 5; i++) { for(int j = 0; j < 5; j++) { if() { Champion c = (Champion)
         * this.g.getBoard()[i][j]; if (c.getName().equals(lastChamp)) {
         *
         * } } } }
         */
        this.g.endTurn();
        this.update();
    }

    public static void main(String[] args) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == attackbtn) {
        }
    }
}