public class Unit {
    private final double factor;
    private final String name;
    private final String abbreviation;

    public Unit(double factor, String name, String abbreviation) {
        this.factor = factor;
        this.name = name;
        this.abbreviation = abbreviation;
    }
    public double getFactor() {
        return factor;
    }
    public String getName() {
        return name;
    }
    public String getAbbreviation() {
        return abbreviation;
    }
}