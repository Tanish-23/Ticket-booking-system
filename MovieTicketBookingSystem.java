// MovieTicketBookingSystem.java
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.*;

public class MovieTicketBookingSystem {
    private JFrame mainFrame;
    private LocationPanel locationPanel;
    private MovieSelectionPanel movieSelectionPanel;
    private SeatLayoutPanel seatLayoutPanel;
    private SummaryPanel summaryPanel;
    
    private Set<String> selectedSeats;
    
    // Enhanced pricing structure
    private Map<String, Map<String, Double>> pricing;
    
    // Store seat layouts for different movie-show combinations
    private Map<String, Map<String, Boolean>> seatLayouts;
    
    // Theme colors - MAKE THESE PUBLIC
    public final Color DARK_BLUE = new Color(25, 25, 112);
    public final Color PURPLE = new Color(138, 43, 226);
    public final Color YELLOW = Color.YELLOW;
    public final Color ORANGE = Color.ORANGE;
    public final Color WHITE = Color.WHITE;
    
    public void initialize() {
        selectedSeats = new HashSet<>();
        initializePricing();
        initializeSeatLayouts();
        createGUI();
    }
    
    private void initializePricing() {
        pricing = new HashMap<>();
        
        // Different base prices for different formats
        Map<String, Double> formatBasePrices = new HashMap<>();
        formatBasePrices.put("2D", 200.0);
        formatBasePrices.put("3D", 350.0);
        formatBasePrices.put("IMAX_2D", 400.0);
        formatBasePrices.put("IMAX_3D", 600.0);
        formatBasePrices.put("4DX", 800.0);
        pricing.put("BASE", formatBasePrices);
        
        // Seat type multipliers (multiplied by base price)
        Map<String, Double> seatTypeMultipliers = new HashMap<>();
        seatTypeMultipliers.put("PREMIUM", 2.0);
        seatTypeMultipliers.put("VIP", 1.8);  
        seatTypeMultipliers.put("EXECUTIVE", 1.5);
        seatTypeMultipliers.put("STANDARD", 1.0);
        seatTypeMultipliers.put("BUDGET", 0.8);
        pricing.put("MULTIPLIERS", seatTypeMultipliers);
        
        // Special movie premiums (additional charges)
        Map<String, Double> moviePremiums = new HashMap<>();
        moviePremiums.put("Kalki 2898 AD", 50.0);
        moviePremiums.put("Pushpa 2: The Rule", 40.0);
        moviePremiums.put("Singham Again", 30.0);
        moviePremiums.put("Devara: Part 1", 35.0);
        moviePremiums.put("Vettaiyan", 20.0);
        moviePremiums.put("Kanguva", 25.0);
        pricing.put("MOVIE_PREMIUMS", moviePremiums);
        
        // Show time multipliers
        Map<String, Double> showTimeMultipliers = new HashMap<>();
        showTimeMultipliers.put("10:00 AM", 0.9);
        showTimeMultipliers.put("1:30 PM", 1.0);
        showTimeMultipliers.put("4:00 PM", 1.1);
        showTimeMultipliers.put("7:30 PM", 1.3);
        showTimeMultipliers.put("10:00 PM", 1.2);
        pricing.put("SHOWTIME_MULTIPLIERS", showTimeMultipliers);
    }
    
    private void initializeSeatLayouts() {
        seatLayouts = new HashMap<>();
        
        String[] movies = {"Kalki 2898 AD", "Pushpa 2: The Rule", "Singham Again", "Devara: Part 1", "Vettaiyan", "Kanguva"};
        String[] showTimes = {"10:00 AM", "1:30 PM", "4:00 PM", "7:30 PM", "10:00 PM"};
        
        for (String movie : movies) {
            for (String showTime : showTimes) {
                String key = movie + "_" + showTime;
                Map<String, Boolean> layout = new HashMap<>();
                
                Random rand = new Random(movie.hashCode() + showTime.hashCode());
                
                for (char row = 'A'; row <= 'F'; row++) {
                    for (int num = 1; num <= 8; num++) {
                        String seat = row + String.valueOf(num);
                        boolean isOccupied;
                        
                        if (showTime.equals("7:30 PM") || showTime.equals("10:00 PM")) {
                            isOccupied = rand.nextDouble() > 0.4;
                        } else {
                            isOccupied = rand.nextDouble() > 0.7;
                        }
                        
                        if ((movie.equals("Kalki 2898 AD") || movie.equals("Pushpa 2: The Rule")) && 
                            (row == 'A' || row == 'B') && num <= 4) {
                            isOccupied = rand.nextDouble() > 0.3;
                        }
                        
                        layout.put(seat, isOccupied);
                    }
                }
                seatLayouts.put(key, layout);
            }
        }
    }
    
    private void createGUI() {
        mainFrame = new JFrame("Cinemaghar - Movie Ticket Booking System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout(10, 10));
        mainFrame.getContentPane().setBackground(DARK_BLUE);

        // Header with theme colors
        JLabel headerLabel = new JLabel("CINEMAGHAR - BOOK YOUR EXPERIENCE", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(YELLOW);
        headerLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(DARK_BLUE);
        mainFrame.add(headerLabel, BorderLayout.NORTH);
        
        // Main panel with theme
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(DARK_BLUE);
        
        // Initialize ALL panels first
        locationPanel = new LocationPanel(this);
        movieSelectionPanel = new MovieSelectionPanel(this);
        seatLayoutPanel = new SeatLayoutPanel(this);
        summaryPanel = new SummaryPanel(this);
        
        // Top panel with location and movie selection
        JPanel topPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        topPanel.setBackground(DARK_BLUE);
        topPanel.add(locationPanel);
        topPanel.add(movieSelectionPanel);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // Center panel with seats and summary
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        centerPanel.setBackground(DARK_BLUE);
        centerPanel.add(seatLayoutPanel);
        centerPanel.add(summaryPanel);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Confirm button with theme colors
        JButton confirmButton = new JButton("CONFIRM BOOKING");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 16));
        confirmButton.setBackground(ORANGE);
        confirmButton.setForeground(Color.BLACK);
        confirmButton.setFocusPainted(false);
        confirmButton.addActionListener(e -> confirmBooking());
        
        // Hover effects for button
        confirmButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                confirmButton.setBackground(YELLOW);
                confirmButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                confirmButton.setBackground(ORANGE);
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(DARK_BLUE);
        buttonPanel.add(confirmButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainFrame.add(mainPanel);
        mainFrame.setSize(1000, 700);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
    
    // REST OF THE METHODS (seatSelected, selectionChanged, updateDisplay, confirmBooking, etc.)
    // ... [Keep all your existing methods here unchanged]
    
    public double calculateTotalAmount() {
        String format = movieSelectionPanel.getSelectedFormat();
        String movie = movieSelectionPanel.getSelectedMovie();
        String showTime = movieSelectionPanel.getSelectedShowTime();
        
        double basePrice = pricing.get("BASE").get(format);
        double moviePremium = pricing.get("MOVIE_PREMIUMS").getOrDefault(movie, 0.0);
        double showTimeMultiplier = pricing.get("SHOWTIME_MULTIPLIERS").get(showTime);
        
        double total = 0;
        for (String seat : selectedSeats) {
            String seatType = getSeatType(seat);
            double seatMultiplier = pricing.get("MULTIPLIERS").get(seatType);
            
            double seatPrice = (basePrice + moviePremium) * seatMultiplier * showTimeMultiplier;
            total += seatPrice;
        }
        return total;
    }
    
    public double getSeatPrice(String seat) {
        String format = movieSelectionPanel.getSelectedFormat();
        String movie = movieSelectionPanel.getSelectedMovie();
        String showTime = movieSelectionPanel.getSelectedShowTime();
        String seatType = getSeatType(seat);
        
        double basePrice = pricing.get("BASE").get(format);
        double moviePremium = pricing.get("MOVIE_PREMIUMS").getOrDefault(movie, 0.0);
        double showTimeMultiplier = pricing.get("SHOWTIME_MULTIPLIERS").get(showTime);
        double seatMultiplier = pricing.get("MULTIPLIERS").get(seatType);
        
        return (basePrice + moviePremium) * seatMultiplier * showTimeMultiplier;
    }
    
    private String getSeatType(String seat) {
        char row = seat.charAt(0);
        switch (row) {
            case 'A': return "PREMIUM";
            case 'B': return "VIP";
            case 'C': return "EXECUTIVE";
            case 'D': 
            case 'E': return "STANDARD";
            case 'F': return "BUDGET";
            default: return "STANDARD";
        }
    }
    
    public String getSeatTypeName(String seat) {
        return getSeatType(seat);
    }
    
    public Map<String, Boolean> getCurrentSeatLayout() {
        String movie = movieSelectionPanel.getSelectedMovie();
        String showTime = movieSelectionPanel.getSelectedShowTime();
        String key = movie + "_" + showTime;
        
        Map<String, Boolean> layout = seatLayouts.get(key);
        if (layout == null) {
            layout = new HashMap<>();
            Random rand = new Random();
            for (char row = 'A'; row <= 'F'; row++) {
                for (int num = 1; num <= 8; num++) {
                    String seat = row + String.valueOf(num);
                    layout.put(seat, rand.nextDouble() > 0.7);
                }
            }
            seatLayouts.put(key, layout);
        }
        return layout;
    }
    
    public Map<String, Object> getPricingDetails() {
        Map<String, Object> details = new HashMap<>();
        details.put("BASE", pricing.get("BASE"));
        details.put("MULTIPLIERS", pricing.get("MULTIPLIERS"));
        details.put("MOVIE_PREMIUMS", pricing.get("MOVIE_PREMIUMS"));
        details.put("SHOWTIME_MULTIPLIERS", pricing.get("SHOWTIME_MULTIPLIERS"));
        return details;
    }
    
    public Set<String> getSelectedSeats() { return selectedSeats; }
    public LocationPanel getLocationPanel() { return locationPanel; }
    public MovieSelectionPanel getMovieSelectionPanel() { return movieSelectionPanel; }
}