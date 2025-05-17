import javax.swing.*;
public class app {
    public static void main(String[] args) throws Exception {
        int boardW = 360;
        int boardH = 640;
        JFrame frame = new JFrame("Flappy Bird");

        frame.setSize(boardW,boardH);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack();
        flappyBird.requestFocus();
        frame.setVisible(true);
    }
}
