import vrml.node.Script;
import javax.swing.*;

/**
 * A script class for a VRML scene
 * that visualizes the terrain construction
 * using Delaunay triangulation.
 * 
 * @author Mikhail Dubov
 */
public class VRMLTerrainScript extends Script {
    
    @Override
    public void initialize() {
        
        frame = new JFrame("Terrain construction via Delaunay triangulation");
        
        JSpinner points = new JSpinner();
        points.setValue(20);
        frame.getContentPane().add(points);
        
        JButton generate = new JButton();
        generate.setText("Generate");
        frame.getContentPane().add(generate);
        
        frame.pack();
        frame.setVisible(true);
    }
    
    @Override
    public void shutdown() {
        frame.dispose();
    }
    
    private JFrame frame;
}
