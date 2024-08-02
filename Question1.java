import java.awt.*;
import java.awt.geom.Line2D;
import javax.swing.*;

public class Question1 extends JPanel {

    // Flight coordinates
    private int[][] flight1 = {{1, 1}, {2, 2}, {3, 3}};
    private int[][] flight2 = {{1, 1}, {2, 4}, {3, 2}};
    private int[][] flight3 = {{1, 1}, {4, 2}, {3, 4}};

    private int[][][] flights = {flight1, flight2, flight3};

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(2));

        Color[] colors = {Color.RED, Color.BLUE, Color.ORANGE};

        // Get the height of the panel
        int height = getHeight();

        for (int i = 0; i < flights.length; i++) {
            g2.setColor(colors[i]);
            int[][] flight = flights[i];
            for (int j = 0; j < flight.length - 1; j++) {
                int x1 = flight[j][0] * 100;
                int y1 = height - (flight[j][1] * 100);
                int x2 = flight[j + 1][0] * 100;
                int y2 = height - (flight[j + 1][1] * 100);
                Line2D line = new Line2D.Double(x1, y1, x2, y2);
                g2.draw(line);
                g2.fillOval(x1 - 5, y1 - 5, 10, 10);
                g2.fillOval(x2 - 5, y2 - 5, 10, 10);
            }
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Flight Paths");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Question1());
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
