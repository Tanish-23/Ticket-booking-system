package src.Gui;
// SummaryPanel.java
import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.util.Map;

public class SummaryPanel extends JPanel {
    private JTextArea summaryTextArea;
    private MovieTicketBookingSystem mainApp;
    
    public SummaryPanel(MovieTicketBookingSystem mainApp) {
        this.mainApp = mainApp;
        initializePanel();
    }
    
    private void initializePanel() {
        setLayout(new BorderLayout());
        
        // Apply theme colors
        setBackground(mainApp.getDarkBlue());
        TitledBorder border = new TitledBorder("Booking Summary & Pricing");
        border.setTitleColor(mainApp.getYellow());
        setBorder(border);
        
        summaryTextArea = new JTextArea(15, 25);
        summaryTextArea.setEditable(false);
        summaryTextArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        summaryTextArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        summaryTextArea.setBackground(Color.WHITE);
        summaryTextArea.setForeground(Color.BLACK);
        
        updateSummary();
        
        JScrollPane scrollPane = new JScrollPane(summaryTextArea);
        scrollPane.setBackground(mainApp.getDarkBlue());
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public void updateSummary() {
        StringBuilder summary = new StringBuilder();
        LocationPanel locationPanel = mainApp.getLocationPanel();
        MovieSelectionPanel moviePanel = mainApp.getMovieSelectionPanel();
        
        summary.append("LOCATION:\n");
        summary.append("  ").append(locationPanel.getSelectedLocation()).append("\n\n");
        
        summary.append("MOVIE DETAILS:\n");
        summary.append("  Movie: ").append(moviePanel.getSelectedMovie()).append("\n");
        summary.append("  Format: ").append(moviePanel.getSelectedFormat().replace("_", " ")).append("\n");
        summary.append("  Language: ").append(moviePanel.getSelectedLanguage()).append("\n");
        summary.append("  Show Time: ").append(moviePanel.getSelectedShowTime()).append("\n\n");
        
        // Show current pricing factors
        Map<String, Object> pricingDetails = mainApp.getPricingDetails();
        double basePrice = ((Map<String, Double>)pricingDetails.get("BASE")).get(moviePanel.getSelectedFormat());
        double moviePremium = ((Map<String, Double>)pricingDetails.get("MOVIE_PREMIUMS")).getOrDefault(moviePanel.getSelectedMovie(), 0.0);
        double showTimeMultiplier = ((Map<String, Double>)pricingDetails.get("SHOWTIME_MULTIPLIERS")).get(moviePanel.getSelectedShowTime());
        
        summary.append("PRICING FACTORS:\n");
        summary.append("  Base Price: Rs.").append(String.format("%.0f", basePrice)).append("\n");
        if (moviePremium > 0) {
            summary.append("  Movie Premium: +Rs.").append(String.format("%.0f", moviePremium)).append("\n");
        }
        summary.append("  Show Time Multiplier: ").append(String.format("%.1fx", showTimeMultiplier)).append("\n");
        summary.append("  Final Base: Rs.").append(String.format("%.0f", (basePrice + moviePremium) * showTimeMultiplier)).append("\n\n");
        
        summary.append("SELECTED SEATS:\n");
        java.util.Set<String> selectedSeats = mainApp.getSelectedSeats();
        if (selectedSeats.isEmpty()) {
            summary.append("  No seats selected\n");
        } else {
            double totalAmount = 0;
            for (String seat : selectedSeats) {
                double seatPrice = mainApp.getSeatPrice(seat);
                String seatType = mainApp.getSeatTypeName(seat);
                double multiplier = ((Map<String, Double>)pricingDetails.get("MULTIPLIERS")).get(seatType);
                
                summary.append("  ").append(seat).append(" (")
                      .append(seatType).append(" ").append(String.format("%.1fx", multiplier))
                      .append(") - Rs.").append(String.format("%.0f", seatPrice)).append("\n");
                totalAmount += seatPrice;
            }
            summary.append("\n  SUBTOTAL: Rs.").append(String.format("%.0f", totalAmount)).append("\n");
        }
        
        double totalAmount = mainApp.calculateTotalAmount();
        
        summary.append("\nTOTAL AMOUNT: Rs.").append(String.format("%.0f", totalAmount)).append("\n\n");
        
        // Seat type explanation
        summary.append("SEAT TYPES:\n");
        summary.append("  A: Premium (2.0x) - Best view\n");
        summary.append("  B: VIP (1.8x) - Great view\n");
        summary.append("  C: Executive (1.5x) - Good view\n");
        summary.append("  D-E: Standard (1.0x) - Regular\n");
        summary.append("  F: Budget (0.8x) - Economy\n");
        
        summaryTextArea.setText(summary.toString());
    }
}