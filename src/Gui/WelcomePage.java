package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage {
    private JFrame welcomeFrame;

    public void initialize() {
        welcomeFrame = new JFrame("Cinemaghar");
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setSize(800, 600);
        welcomeFrame.setLocationRelativeTo(null);
        welcomeFrame.setLayout(new BorderLayout());

        // Simple background without gradient painting issues
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(25, 25, 112)); // Dark blue solid background

        // Header
        JLabel titleLabel = new JLabel("CINEMAGHAR", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(80, 0, 40, 0));

        // Subtitle - Fixed to ensure full text display
        JLabel subtitleLabel = new JLabel("Your Ultimate Movie Experience", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Made it bold for better visibility
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 60, 0));

        // BookMyShow button
        JButton bookMyShowButton = new JButton("BOOK MY SHOW");
        bookMyShowButton.setFont(new Font("Arial", Font.BOLD, 24));
        bookMyShowButton.setBackground(Color.ORANGE);
        bookMyShowButton.setForeground(Color.BLACK);
        bookMyShowButton.setFocusPainted(false);
        bookMyShowButton.setBorder(BorderFactory.createRaisedBevelBorder());
        bookMyShowButton.setPreferredSize(new Dimension(300, 80));
        
        // Button hover effects
        bookMyShowButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bookMyShowButton.setBackground(Color.YELLOW);
                bookMyShowButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                bookMyShowButton.setBackground(Color.ORANGE);
            }
        });

        // Button action
        bookMyShowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                welcomeFrame.dispose();
                new MovieTicketBookingSystem().initialize();
            }
        });

        // Features list - Simplified layout
        JPanel featuresPanel = new JPanel();
        featuresPanel.setLayout(new BoxLayout(featuresPanel, BoxLayout.Y_AXIS));
        featuresPanel.setOpaque(false);
        featuresPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        String[] features = {
            "Latest Bollywood & Hollywood Movies",
            "Dynamic Seat Pricing & Selection", 
            "Multiple Locations Across Cities",
            "Real-time Pricing & Discounts"
        };

        for (String feature : features) {
            JLabel featureLabel = new JLabel(feature);
            featureLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            featureLabel.setForeground(Color.WHITE);
            featureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            featuresPanel.add(featureLabel);
            featuresPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        // Center panel for button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(bookMyShowButton);

        // Footer
        JLabel footerLabel = new JLabel("Experience Cinema Like Never Before!", JLabel.CENTER);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        footerLabel.setForeground(Color.LIGHT_GRAY);
        footerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Add all components with proper spacing
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(subtitleLabel, BorderLayout.NORTH);
        centerPanel.add(featuresPanel, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(footerLabel, BorderLayout.SOUTH);

        welcomeFrame.add(mainPanel);
        welcomeFrame.setVisible(true);
    }
}