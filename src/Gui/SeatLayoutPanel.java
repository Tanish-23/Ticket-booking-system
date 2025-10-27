package GUI;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;

public class SeatLayoutPanel extends JPanel {
    private JPanel seatGridPanel;
    private MovieTicketBookingSystem mainApp;
    
    public SeatLayoutPanel(MovieTicketBookingSystem mainApp) {
        this.mainApp = mainApp;
        initializePanel();
    }
    
    private void initializePanel() {
        setLayout(new BorderLayout());
        
        // Apply theme colors
        setBackground(mainApp.DARK_BLUE);
        TitledBorder border = new TitledBorder("Seat Layout - Click to Select/Deselect (Prices vary by seat type)");
        border.setTitleColor(mainApp.YELLOW);
        setBorder(border);
        
        // Screen representation
        JLabel screenLabel = new JLabel("======= S C R E E N =======", JLabel.CENTER);
        screenLabel.setFont(new Font("Arial", Font.BOLD, 14));
        screenLabel.setBackground(Color.LIGHT_GRAY);
        screenLabel.setOpaque(true);
        screenLabel.setBorder(new LineBorder(Color.BLACK));
        screenLabel.setForeground(Color.BLACK);
        
        add(screenLabel, BorderLayout.NORTH);
        
        seatGridPanel = new JPanel(new GridLayout(6, 8, 5, 5));
        seatGridPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        seatGridPanel.setBackground(mainApp.DARK_BLUE);
        
        updateSeatDisplay();
        
        add(seatGridPanel, BorderLayout.CENTER);
        
        // Enhanced legend with prices
        JPanel legendPanel = new JPanel(new FlowLayout());
        legendPanel.setBackground(mainApp.DARK_BLUE);
        legendPanel.add(createLegendLabel("Premium (2x)", new Color(255, 165, 0)));
        legendPanel.add(createLegendLabel("VIP (1.8x)", new Color(148, 0, 211)));
        legendPanel.add(createLegendLabel("Executive (1.5x)", new Color(30, 144, 255)));
        legendPanel.add(createLegendLabel("Standard (1x)", Color.GREEN));
        legendPanel.add(createLegendLabel("Budget (0.8x)", Color.CYAN));
        legendPanel.add(createLegendLabel("Selected", Color.YELLOW));
        legendPanel.add(createLegendLabel("Occupied", Color.RED));
        
        add(legendPanel, BorderLayout.SOUTH);
    }
    
    private JLabel createLegendLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setOpaque(true);
        label.setBackground(color);
        label.setBorder(new LineBorder(Color.BLACK));
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 9));
        return label;
    }
    
    public void updateSeatLayout() {
        updateSeatDisplay();
    }
    
    public void updateSeatDisplay() {
        seatGridPanel.removeAll();
        
        Map<String, Boolean> seatLayout = mainApp.getCurrentSeatLayout();
        
        for (char row = 'A'; row <= 'F'; row++) {
            for (int num = 1; num <= 8; num++) {
                String seat = row + String.valueOf(num);
                double seatPrice = mainApp.getSeatPrice(seat);
                String seatType = mainApp.getSeatTypeName(seat);
                
                JButton seatButton = new JButton(seat);
                seatButton.setFont(new Font("Arial", Font.BOLD, 9));
                
                boolean isOccupied = seatLayout.get(seat);
                
                if (isOccupied) {
                    seatButton.setBackground(Color.RED);
                    seatButton.setEnabled(false);
                } else if (mainApp.getSelectedSeats().contains(seat)) {
                    seatButton.setBackground(Color.YELLOW);
                } else {
                    // Different colors and tooltips for different seat types
                    switch (seatType) {
                        case "PREMIUM":
                            seatButton.setBackground(new Color(255, 165, 0)); // Orange
                            seatButton.setToolTipText(String.format("Premium Seat - Rs.%.0f (2x base)", seatPrice));
                            break;
                        case "VIP":
                            seatButton.setBackground(new Color(148, 0, 211)); // Purple
                            seatButton.setToolTipText(String.format("VIP Seat - Rs.%.0f (1.8x base)", seatPrice));
                            break;
                        case "EXECUTIVE":
                            seatButton.setBackground(new Color(30, 144, 255)); // Blue
                            seatButton.setToolTipText(String.format("Executive Seat - Rs.%.0f (1.5x base)", seatPrice));
                            break;
                        case "STANDARD":
                            seatButton.setBackground(Color.GREEN);
                            seatButton.setToolTipText(String.format("Standard Seat - Rs.%.0f", seatPrice));
                            break;
                        case "BUDGET":
                            seatButton.setBackground(Color.CYAN);
                            seatButton.setToolTipText(String.format("Budget Seat - Rs.%.0f (0.8x base)", seatPrice));
                            break;
                    }
                }
                
                seatButton.setOpaque(true);
                seatButton.setBorder(new LineBorder(Color.BLACK));
                seatButton.addActionListener(e -> mainApp.seatSelected(seat));
                
                seatGridPanel.add(seatButton);
            }
        }
        
        seatGridPanel.revalidate();
        seatGridPanel.repaint();
    }
    
    public void markSeatsAsOccupied(Set<String> seats) {
        Map<String, Boolean> seatLayout = mainApp.getCurrentSeatLayout();
        for (String seat : seats) {
            seatLayout.put(seat, true);
        }
        updateSeatDisplay();
    }
}