import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import vrml.node.Script;
import javax.swing.*;
import ru.dubov.primitives.Triangle;
import ru.dubov.primitives.Point;
import ru.dubov.delaunay.*;

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
        
        // VRML events
        
        point_changed = (vrml.field.MFVec3f)getEventOut("point_changed");
        coordIndex_changed = (vrml.field.MFInt32)getEventOut("coordIndex_changed");
        
        // Helper frame
        
        frame = new JFrame("Terrain via Delaunay");
        frame.setResizable(false);
        
        final JSpinner pointCount = new JSpinner();
        pointCount.setValue(20);
        pointCount.setLocation(80, 80);
        frame.getContentPane().add(pointCount, BorderLayout.CENTER);
        
        JButton generate = new JButton();
        generate.setText("Generate");
        generate.setLocation(60, 150);
        
        generate.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                // Generate points
                Random rand = new Random();
                points = new ArrayList<Point>();
                
                points.add(new ProjectedPoint3D(-3, -3, 0));
                points.add(new ProjectedPoint3D(-3, 3, 0));
                points.add(new ProjectedPoint3D(3, -3, 0));
                points.add(new ProjectedPoint3D(3, 3, 0));
                
                for (int i = 0; i < (Integer)pointCount.getValue(); i++) {
                    points.add(new ProjectedPoint3D(rand.nextDouble()*6-3,
                            rand.nextDouble()*6-3, rand.nextDouble()*0.5-0.25));
                }
                
                // Triangulate (Delaunay)
                triangulation = Delaunay.randomizedIncremental(points);
                
                // Draw (IndexedLineSet)
                float[] pointCoord = new float[triangulation.size()*9];
                int[] pointInd = new int[triangulation.size()*4];
                
                for(int i = 0; i < triangulation.size(); i++) {
                    pointCoord[i*9] = (float)((ProjectedPoint3D)triangulation.get(i).getA()).getX();
                    pointCoord[i*9+1] = (float)((ProjectedPoint3D)triangulation.get(i).getA()).getY();
                    pointCoord[i*9+2] = (float)((ProjectedPoint3D)triangulation.get(i).getA()).getZ();
                    pointCoord[i*9+3] = (float)((ProjectedPoint3D)triangulation.get(i).getB()).getX();
                    pointCoord[i*9+4] = (float)((ProjectedPoint3D)triangulation.get(i).getB()).getY();
                    pointCoord[i*9+5] = (float)((ProjectedPoint3D)triangulation.get(i).getB()).getZ();
                    pointCoord[i*9+6] = (float)((ProjectedPoint3D)triangulation.get(i).getC()).getX();
                    pointCoord[i*9+7] = (float)((ProjectedPoint3D)triangulation.get(i).getC()).getY();
                    pointCoord[i*9+8] = (float)((ProjectedPoint3D)triangulation.get(i).getC()).getZ();
                    
                    pointInd[i*4] = i*3;
                    pointInd[i*4+1] = i*3+1;
                    pointInd[i*4+2] = i*3+2;
                    pointInd[i*4+3] = -1;
                }
                
                point_changed.setValue(pointCoord);
                coordIndex_changed.setValue(pointInd);
            }
            
            private ArrayList<Point> points;
            private ArrayList<Triangle> triangulation;
        });
        
        frame.getContentPane().add(generate, BorderLayout.SOUTH);
        
        frame.pack(); 
        frame.setLocationRelativeTo(frame.getRootPane());
        frame.setSize(240, 100);
        frame.setVisible(true);
    }
    
    @Override
    public void shutdown() {
        frame.setVisible(false);
        frame.dispose();
    }
    
    
    static class ProjectedPoint3D extends Point {
        
        public ProjectedPoint3D(double x, double y, double z) {
            super(x, y);
            this.z = z;
        }
        
        public double getZ() {
            return z;
        }
        
        final protected double z;
    }
    
    private JFrame frame;
    
    private vrml.field.MFVec3f point_changed;
    private vrml.field.MFInt32 coordIndex_changed;
}
