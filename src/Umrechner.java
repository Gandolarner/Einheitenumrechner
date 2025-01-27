import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.*;
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
        factorsLength.put("Fuß",  3.28084);
        factorsLength.put("Zoll", 39.3701);
        factorsLength.put("Seemeilen", 0.0005399568);
        factorsLength.put("Giraffenzungen", 2.1053);
        factorsLength.put("Lichtjahre", 0.0000000000000001057);
        factorsLength.put("Fußballfelder", 0.009524);
        // zum Hinzufügen neuer Einheiten kopieren und Einheitenname sowie Faktor ersetzen:
        // factorsLength.put("Meter", 1.0);
        factorsWeight.put("Kilogramm", 1.0);
        factorsWeight.put("Gramm", 1000.0);
        factorsWeight.put("Milligramm", 1000000.0);
        factorsWeight.put("Mikrogramm", 1000000000.0);
        factorsWeight.put("Tonnen", 0.001);
        factorsWeight.put("Pfund", 2.0);
        factorsWeight.put("Pfund-USA", 2.2);
        factorsWeight.put("Karat", 5000.0);
        factorsWeight.put("Unzen", 35.274);
        factorsWeight.put("Menschenbabys", 3.240);
        factorsWeight.put("Elefantenbabys", 95.000);
        factorsWeight.put("Goldbarren", 12.400);
        // zum Hinzufügen neuer Einheiten kopieren und Einheitenname sowie Faktor ersetzen:
        // factorsWeight.put("Meter", 1.0);
    }

    public static void main(String[] args) {
        // Hauptfenster erstellen
        JFrame frame = new JFrame("Einheiten-Umrechner");
        // Programm wird gestoppt beim Schließen des Fensters
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Fenster(-mindest-)größe festlegen
        frame.setMinimumSize(new Dimension(400, 350));
        // Fensterposition in der Mitte des Bildschirms
        frame.setLocationRelativeTo(null);
        // Register erstellen
        JTabbedPane tabbedPane = new JTabbedPane();
        // Registerkarte Länge
        JPanel page0 = new JPanel();
        page0.add(new JLabel("Länge"));
        tabbedPane.addTab("Länge", pageLength());
        // Registerkarte Gewicht
        JPanel page1 = new JPanel();
        page1.add(new JLabel("Gewicht"));
        tabbedPane.addTab("Gewicht", pageWeight());
        //Register am unteren Fensterrand
        tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
        // Registerkarten zum Fenster hinzufügen
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
        // Rand hinzufügen
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel inputLabel = new JLabel("Eingabewert:");

        JTextField inputField = new JTextField();

        JLabel fromUnitLabel = new JLabel("Von (Einheit):");
        JComboBox<String> fromUnitCombo = new JComboBox<>(factorsLength.keySet().toArray(new String[0]));

        JLabel toUnitLabel = new JLabel("Nach (Einheit):");
        JComboBox<String> toUnitCombo = new JComboBox<>(factorsLength.keySet().toArray(new String[0]));

        JLabel resultLabel = new JLabel("Ergebnis:");
        JTextField resultField = new JTextField();
        resultField.setEditable(false);

        JButton convertButton = new JButton("Umrechnen");
        convertButton.addActionListener(_ -> {
            try {
                String inputText = inputField.getText().replace(',', '.');
                double inputValue = Double.parseDouble(inputText);

                if (inputValue <= 0){
                    throw new IllegalArgumentException("Bitte gib eine positive Zahl ein!");
                }
                String fromUnit = (String) fromUnitCombo.getSelectedItem();
                String toUnit = (String) toUnitCombo.getSelectedItem();

                double result = convert(inputValue, fromUnit, toUnit);
                // Auf 3 Nachkommastellen formatieren
                String formatted = String.format("%.3f", result);
                // Entferne endständige Nullen
                formatted = formatted.replaceAll("0*$", "");
                // Entferne das Komma, falls keine Dezimalstellen übrig sind
                formatted = formatted.replaceAll(",$", "");
                // Unterscheidung ob 1 oder !1 die Ausgangsmenge ist
                if (inputValue == 1) {
                    resultField.setText(inputText + " " + fromUnit + " ist gleich " + formatted + " " + toUnit);
                } else {
                    resultField.setText(inputText + " " + fromUnit + " sind gleich" + formatted + " " + toUnit);
                }
            }
                catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Bitte gib eine gültige Zahl ein!", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
                catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                }
        });
        // Eingabefelder auf Knopfdruck leeren
        JButton clearButton = new JButton("Neue Eingabe");
        clearButton.addActionListener(_ -> {
            inputField.setText("");
            resultField.setText("");
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
        panel.add(clearButton);
        // Rückgabewert der Methode
        return panel;
    }
    // Panel für Gewichtsumrechnung
    private static JPanel pageWeight() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
        convertButton.addActionListener(_ -> {
            try {
                String inputText = inputField.getText().replace(',', '.');
                double inputValue = Double.parseDouble(inputText);

                if (inputValue <= 0){
                    throw new IllegalArgumentException("Bitte gib eine positive Zahl ein!");
                }
                String fromUnit = (String) fromUnitCombo.getSelectedItem();
                String toUnit = (String) toUnitCombo.getSelectedItem();

                double result = convert(inputValue, fromUnit, toUnit);
                String formatted = String.format("%.3f", result);
                formatted = formatted.replaceAll("0*$", "");
                formatted = formatted.replaceAll(",$", "");
                if (inputValue == 1) {
                    resultField.setText(inputText + " " + fromUnit + " ist gleich " + formatted + " " + toUnit);
                } else {
                    resultField.setText(inputText + " " + fromUnit + " sind gleich" + formatted + " " + toUnit);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Bitte gib eine gültige Zahl ein!", "Fehler",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton clearButton = new JButton("Neue Eingabe");
        clearButton.addActionListener(_ -> {
            inputField.setText("");
            resultField.setText("");
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
        panel.add(clearButton);

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
