package halfplanes;

/**
 * Represents a halfplane by means of the boundary line
 * ax + by = c, where one accepts that
 * for the halfplane ax + by <= c holds.
 * 
 * @author Mikhail Dubov
 */
public class Halfplane {
    
    public Halfplane(Line line) {
        this.line = line;
    }
    
    public Line getLine() {
        return line;
    }
    
    private Line line;
}
