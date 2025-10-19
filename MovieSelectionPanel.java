// MovieSelectionPanel.java
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class MovieSelectionPanel extends JPanel {
    private JComboBox<String> movieComboBox;
    private JComboBox<String> formatComboBox;
    private JComboBox<String> languageComboBox;
    private JComboBox<String> showTimeComboBox;
    private MovieTicketBookingSystem mainApp;
    
    private String[] latestMovies = {
        "Kalki 2898 AD", "Pushpa 2: The Rule", "Singham Again", 
        "Devara: Part 1", "Vettaiyan", "Kanguva"
    };
    
    private String[] formats = {"2D", "3D", "IMAX 2D", "IMAX 3D", "4DX"};
    private String[] languages = {"Hindi", "English", "Tamil", "Telugu", "Malayalam", "Kannada"};
    private String[] showTimes = {"10:00 AM", "1:30 PM", "4:00 PM", "7:30 PM", "10:00 PM"};
    
    public MovieSelectionPanel(MovieTicketBookingSystem mainApp) {
        this.mainApp = mainApp;
        initializePanel();
    }
    
    private void initializePanel() {
        setLayout(new GridLayout(4, 2, 10, 10));
        setBorder(new TitledBorder("Movie Selection"));
        
        add(new JLabel("Select Movie:"));
        movieComboBox = new JComboBox<>(latestMovies);
        movieComboBox.addActionListener(e -> mainApp.selectionChanged());
        add(movieComboBox);
        
        add(new JLabel("Select Format:"));
        formatComboBox = new JComboBox<>(formats);
        formatComboBox.addActionListener(e -> mainApp.selectionChanged());
        add(formatComboBox);
        
        add(new JLabel("Select Language:"));
        languageComboBox = new JComboBox<>(languages);
        languageComboBox.addActionListener(e -> mainApp.selectionChanged());
        add(languageComboBox);
        
        add(new JLabel("Select Show Time:"));
        showTimeComboBox = new JComboBox<>(showTimes);
        showTimeComboBox.addActionListener(e -> mainApp.selectionChanged());
        add(showTimeComboBox);
    }
    
    public String getSelectedMovie() {
        return (String) movieComboBox.getSelectedItem();
    }
    
    public String getSelectedFormat() {
        String format = (String) formatComboBox.getSelectedItem();
        return format.replace(" ", "_").toUpperCase();
    }
    
    public String getSelectedLanguage() {
        return (String) languageComboBox.getSelectedItem();
    }
    
    public String getSelectedShowTime() {
        return (String) showTimeComboBox.getSelectedItem();
    }
}