package src.Gui;
// LocationPanel.java
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class LocationPanel extends JPanel {
    private JComboBox<String> locationComboBox;
    private JComboBox<String> areaComboBox;
    private MovieTicketBookingSystem mainApp;
    
    private String[] cities = {"Mumbai", "Navi Mumbai", "Pune", "Delhi", "Bangalore"};
    private java.util.Map<String, String[]> areas = new java.util.HashMap<>();
    
    public LocationPanel(MovieTicketBookingSystem mainApp) {
        this.mainApp = mainApp;
        initializeAreas();
        initializePanel();
    }
    
    private void initializeAreas() {
        areas.put("Mumbai", new String[]{"South Mumbai", "Western Suburbs", "Central Suburbs", "Thane"});
        areas.put("Navi Mumbai", new String[]{"Vashi", "Nerul", "Seawoods", "Kharghar", "Panvel"});
        areas.put("Pune", new String[]{"Pune Central", "Hinjewadi", "Kharadi", "Viman Nagar"});
        areas.put("Delhi", new String[]{"Central Delhi", "South Delhi", "West Delhi", "North Delhi"});
        areas.put("Bangalore", new String[]{"Central Bangalore", "Whitefield", "Electronic City", "Koramangala"});
    }
    
    private void initializePanel() {
        setLayout(new GridLayout(2, 2, 10, 10));
        
        // Apply theme colors DIRECTLY
        setBackground(new Color(25, 25, 112)); // Dark blue
        TitledBorder border = new TitledBorder("Select Location");
        border.setTitleColor(Color.YELLOW);
        setBorder(border);
        
        // Labels with theme colors
        JLabel cityLabel = new JLabel("Select City:");
        cityLabel.setForeground(Color.WHITE);
        add(cityLabel);
        
        locationComboBox = new JComboBox<>(cities);
        locationComboBox.setBackground(Color.WHITE);
        locationComboBox.addActionListener(e -> updateAreas());
        add(locationComboBox);
        
        JLabel areaLabel = new JLabel("Select Area:");
        areaLabel.setForeground(Color.WHITE);
        add(areaLabel);
        
        areaComboBox = new JComboBox<>();
        areaComboBox.setBackground(Color.WHITE);
        updateAreas();
        areaComboBox.addActionListener(e -> {
            if (mainApp != null) {
                mainApp.selectionChanged();
            }
        });
        add(areaComboBox);
    }
    
    private void updateAreas() {
        String selectedCity = (String) locationComboBox.getSelectedItem();
        String[] cityAreas = areas.get(selectedCity);
        areaComboBox.setModel(new DefaultComboBoxModel<>(cityAreas));
    }
    
    public String getSelectedLocation() {
        return locationComboBox.getSelectedItem() + " - " + areaComboBox.getSelectedItem();
    }
}