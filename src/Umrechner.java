import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

public class Umrechner {
    private static final LinkedHashMap<String, Double> factorsLength = new LinkedHashMap<>();
    private static final LinkedHashMap<String, Double> factorsWeight = new LinkedHashMap<>();

    // Initialisierung der Umrechnungsfaktoren
    static {
        factorsLength.put("Meter", 1.0);
        factorsLength.put("Millimeter", 1000.0);
        factorsLength.put("Zentimeter", 100.0);
        factorsLength.put("Kilometer", 0.001);
        factorsLength.put("Mikrometer", 1000000.0);
        factorsLength.put("Nanometer", 1000000000.0);
        factorsLength.put("Yard", 1.09361);
        factorsLength.put("Fuß", 3.28084);
        factorsLength.put("Zoll", 39.3701);
        factorsLength.put("Seemeilen", 0.0005399568);
        factorsLength.put("Giraffenzungen", 2.1053);
        factorsLength.put("Lichtjahre", 0.0000000000000001057);
        factorsLength.put("Fußballfelder", 0.009524);
        // zum Hinzufügen neuer Einheiten kopieren und Einheitenname sowie Faktor ersetzen: factorslength.put("Meter", 1.0);
        factorsWeight.put("Kilogramm", 1000.0);
        factorsWeight.put("Gramm", 1.0);
        factorsWeight.put("Milligramm", 0.001);
        factorsWeight.put("Mikrogramm", 0.000001);
        factorsWeight.put("Tonnen", 1000000.0);
        factorsWeight.put("Pfund", 500.0);
        factorsWeight.put("Pfund-USA", 453.0);
        factorsWeight.put("Karat", 0.2);
        factorsWeight.put("Unzen", 28.35);
        factorsWeight.put("Menschenbabys", 3240.0);
        factorsWeight.put("Elefantenbabys", 95000.0);
        factorsWeight.put("Goldbarren", 12400.0);
        // zum Hinzufügen neuer Einheiten kopieren und Einheitenname sowie Faktor ersetzen: factorsWeight.put("Meter", 1.0);
    }

    public static void main(String[] args) {
        // Hauptfenster erstellen
        JFrame frame = new JFrame("Einheiten-Umrechner");
        // Programm wird gestoppt beim Schließen des Fensters
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Fenster(mindest)größe festlegen
        frame.setMinimumSize(new Dimension(400, 350));
        // Fensterposition in der Mitte des Bildschirms
        frame.setLocationRelativeTo(null);

        // Registerkarten erstellen
        JTabbedPane tabbedPane = new JTabbedPane();
        // Registerkarte Länge
        JPanel pageLength = new JPanel();
        pageLength.add(new JLabel("Länge"));
        tabbedPane.addTab("Länge", pageLength());
        // Registerkarte Gewicht
        JPanel pageWeight = new JPanel();
        pageWeight.add(new JLabel("Gewicht"));
        tabbedPane.addTab("Gewicht", pageWeight());
        //Register am unteren Fensterrand
        tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);

        frame.add(tabbedPane);
        // Fenster sichtbar machen
        frame.setVisible(true);
        // Fenstergröße anpassbar machen
        frame.setResizable(true);
    }
    // Panel für Längenumrechnung
    private static JPanel pageLength() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Rand hinzufügen

        JLabel inputLabel = new JLabel("Eingabewert:");
        //inputLabel.setBounds();
        JTextField inputField = new JTextField();

        JLabel fromUnitLabel = new JLabel("Von (Einheit):");
        JComboBox<String> fromUnitCombo = new JComboBox<>(factorsLength.keySet().toArray(new String[0]));

        JLabel toUnitLabel = new JLabel("Nach (Einheit):");
        JComboBox<String> toUnitCombo = new JComboBox<>(factorsLength.keySet().toArray(new String[0]));

        JLabel resultLabel = new JLabel("Ergebnis:");
        JTextField resultField = new JTextField();
        resultField.setEditable(false);

        JButton convertButton = new JButton("Umrechnen");
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double inputValue = Double.parseDouble(inputField.getText());
                    String fromUnit = (String) fromUnitCombo.getSelectedItem();
                    String toUnit = (String) toUnitCombo.getSelectedItem();

                    double result = convert(inputValue, fromUnit, toUnit);
                    resultField.setText(String.format("%.5f", result));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Bitte eine gültige Zahl eingeben!", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(inputLabel);
        panel.add(inputField);
        panel.add(fromUnitLabel);
        panel.add(fromUnitCombo);
        panel.add(toUnitLabel);
        panel.add(toUnitCombo);
        panel.add(convertButton);
        panel.add(resultLabel);
        panel.add(resultField);

        return panel;
    }
    // Panel für Gewichtsumrechnung
    private static JPanel pageWeight() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Rand hinzufügen

        JLabel inputLabel = new JLabel("Eingabewert:");
        JTextField inputField = new JTextField();

        JLabel fromUnitLabel = new JLabel("Von (Einheit):");
        JComboBox<String> fromUnitCombo = new JComboBox<>(factorsWeight.keySet().toArray(new String[0]));

        JLabel toUnitLabel = new JLabel("Nach (Einheit):");
        JComboBox<String> toUnitCombo = new JComboBox<>(factorsWeight.keySet().toArray(new String[0]));

        JLabel resultLabel = new JLabel("Ergebnis:");
        JTextField resultField = new JTextField();
        resultField.setEditable(false);

        JButton convertButton = new JButton("Umrechnen");
        convertButton.addActionListener(e -> {
            try {
                double inputValue = Double.parseDouble(inputField.getText());
                String fromUnit = (String) fromUnitCombo.getSelectedItem();
                String toUnit = (String) toUnitCombo.getSelectedItem();

                double result = convert(inputValue, fromUnit, toUnit);
                resultField.setText(String.format("%.5f", result));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Bitte eine gültige Zahl eingeben!", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(inputLabel);
        panel.add(inputField);
        panel.add(fromUnitLabel);
        panel.add(fromUnitCombo);
        panel.add(toUnitLabel);
        panel.add(toUnitCombo);
        panel.add(convertButton);
        panel.add(resultLabel);
        panel.add(resultField);

        return panel;
    }
    // Umrechnungslogik
    private static double convert(double value, String fromUnit, String toUnit) {
        double fromFactor;
        double toFactor;

        if (factorsLength.containsKey(fromUnit) && factorsLength.containsKey(toUnit)) {
            fromFactor = factorsLength.get(fromUnit);
            toFactor = factorsLength.get(toUnit);
        } else if (factorsWeight.containsKey(fromUnit) && factorsWeight.containsKey(toUnit)) {
            fromFactor = factorsWeight.get(fromUnit);
            toFactor = factorsWeight.get(toUnit);
        } else {
            throw new IllegalArgumentException("Ungültige Einheitenkombination");
        }

        return value * (toFactor / fromFactor);
    }
}

