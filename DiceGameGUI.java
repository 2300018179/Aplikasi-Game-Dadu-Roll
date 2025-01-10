import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

// Class Dice
class Dice {
    //Atribut
    private Random random;

    //Menginisialisasi objek random
    public Dice() { //konstruktor
        random = new Random();
    }

    //Menghasilkan angka acak antara 1 hingga 6
    public int roll() {
        return random.nextInt(6) + 1;
    }
}

// Class Player
class Player {
    //Atribut
    private String name;
    private int score;

    //Implemtasi Enkapsulasi
    public Player(String name) { //konstruktor
        this.name = name;
        this.score = 0;
    }

    public String getName() { //Mengembalikan nama pemain
        return name;
    }

    public int getScore() { //Mengembalikan skor pemain
        return score;
    }

    public void addScore(int score) { //Menambah skor pemain
        this.score += score;
    }

    public void resetScore() { //Mengatur ulang skor menjadi 0
        this.score = 0;
    }
}

// Class DiceGameGUI
// Implementasi Inheritance dan GUI
public class DiceGameGUI extends JFrame {
    private Player player1; //objek dari class player untuk pemain1
    private Player player2; //objek dari class player untuk pemain2
    private Dice dice; //objek dari class Dice untuk melakukan pelemparan dadu
    private JLabel scoreLabel; //Menampilkan skor
    private JTextField player1Field; //Menginputkan nama pemain1
    private JTextField player2Field; //Menginputkan nama pemain2
    private JButton rollButton; //button untuk melempar dadu
    private JButton resetButton; //button untuk mengatur ulang pemain
    private JTextArea historyArea; //mencatat riwayat lemparan dadu
    private JLabel diceResult1; //Menampilkan hasil dadu yang dilempar untuk pemain1
    private JLabel diceResult2; //Menampilkan hasil dadu yang dilempar untuk pemain1

    public DiceGameGUI() { //konstruktor
        setTitle("Dice Game");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        player1Field = new JTextField(10);
        player2Field = new JTextField(10);
        rollButton = new JButton("Roll Dice");
        resetButton = new JButton("Reset Game");
        inputPanel.add(new JLabel("Player 1 Name: "));
        inputPanel.add(player1Field);
        inputPanel.add(new JLabel("Player 2 Name: "));
        inputPanel.add(player2Field);
        inputPanel.add(rollButton);
        inputPanel.add(resetButton);
        add(inputPanel, BorderLayout.NORTH);

        
        JPanel dicePanel = new JPanel(new GridLayout(1, 2, 10, 10));
        diceResult1 = new JLabel("Dice 1: 0", SwingConstants.CENTER);
        diceResult2 = new JLabel("Dice 2: 0", SwingConstants.CENTER);
        diceResult1.setFont(new Font("Arial", Font.BOLD, 18));
        diceResult2.setFont(new Font("Arial", Font.BOLD, 18));
        dicePanel.add(diceResult1);
        dicePanel.add(diceResult2);
        dicePanel.setBorder(BorderFactory.createTitledBorder("Dice Results"));
        add(dicePanel, BorderLayout.CENTER);

      
        JPanel bottomPanel = new JPanel(new BorderLayout());
        scoreLabel = new JLabel("Scores: Player 1 - 0, Player 2 - 0");
        bottomPanel.add(scoreLabel, BorderLayout.NORTH);
        historyArea = new JTextArea(10, 30);
        historyArea.setEditable(false);
        bottomPanel.add(new JScrollPane(historyArea), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        //Implementasi Polymorphism
        rollButton.addActionListener(new RollListener());
        resetButton.addActionListener(new ResetListener());

        setVisible(true);
    }

    private class RollListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           
            if (player1 == null || player2 == null) {
                String player1Name = player1Field.getText().trim();
                String player2Name = player2Field.getText().trim();

                
                if (player1Name.isEmpty() || player2Name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter both player names!");
                    return;
                }

                player1 = new Player(player1Name);
                player2 = new Player(player2Name);
                dice = new Dice();
            }

            
            int roll1 = dice.roll();
            int roll2 = dice.roll();

            
            diceResult1.setText("Dice 1: " + roll1);
            diceResult2.setText("Dice 2: " + roll2);

            
            player1.addScore(roll1);
            player2.addScore(roll2);

            
            scoreLabel.setText("Scores: " + player1.getName() + " - " + player1.getScore() + ", " +
                    player2.getName() + " - " + player2.getScore());

            
            historyArea.append(player1.getName() + " rolled: " + roll1 + "\n");
            historyArea.append(player2.getName() + " rolled: " + roll2 + "\n");

            
            if (player1.getScore() >= 21 || player2.getScore() >= 21) {
                String winner = player1.getScore() > player2.getScore() ? player1.getName() : player2.getName();
                JOptionPane.showMessageDialog(null, winner + " wins!");
                resetGame();  
            }
        }
    }

    private class ResetListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            resetGame(); 
        }
    }

    private void resetGame() {
        player1 = null;
        player2 = null;
        dice = null; 
        scoreLabel.setText("Scores: Player 1 - 0, Player 2 - 0"); // Reset scores
        historyArea.setText(""); 
        player1Field.setText("");
        player2Field.setText("");
        diceResult1.setText("Dice 1: 0"); 
        diceResult2.setText("Dice 2: 0");
    }

    public static void main(String[] args) { //main()
        new DiceGameGUI();
    }
}