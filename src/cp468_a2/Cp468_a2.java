package cp468_a2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Cp468_a2 {
    
    final static File FILENAME = new File("sudoku3.txt");
    
    public static void main(String[] args) {
        CSP csp = null;
        
        try {
            csp = new CSP(FILENAME);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Cp468_a2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        csp.printGrid();
        
        boolean isValid = doAC3(csp);
        if (!isValid){
            System.out.println("The Sudoku puzzle is unsolveable");
        }
        
            
        csp.updateValues();
        if (csp.isSolved()){
            System.out.println("The puzzle has been solved with AC-3!");
        }else{
            System.out.println("The puzzle has not been solved with AC-3.");
        }
        csp.printGrid();
    }
    
    public static boolean doAC3(CSP csp){
        int count = 0;
        while (!csp.arcs.isEmpty()){
            count ++;
            //System.out.println("Queue length: " + csp.arcs.size());
            Arc arc = csp.arcs.remove(0);
            //System.out.println("x1: "+arc.x1);
            //System.out.println("x2: "+arc.x2);

            if (revise(csp, arc)){
                if (arc.x1.domain.isEmpty()){
                    return false;
                }
                Cell[] neighbours = csp.getNeighbours(arc.x1);
                for (int i = 0; i < neighbours.length; i ++){
                    //System.out.println("testing "+arc.x2+" against: "+neighbours[i]);
                    if (!neighbours[i].equals(arc.x2)){ // && !csp.arcs.contains(new Arc(arc.x1, neighbours[i]))
                        csp.arcs.add(new Arc(arc.x1, neighbours[i]));
                    }
                }
            }
        }
        for (int i = 0; i < csp.vars.length; i ++){
            for (int j = 0; j < csp.vars.length; j ++){
                System.out.println("Domain of cell["+i+"]["+j+"]: "+csp.vars[i][j].domain);
            }
        }
        System.out.println(count+" iterations");
        return true;
    }
    
    private static boolean revise(CSP csp, Arc arc){
        boolean revised = false;
        int iterations = arc.x1.domain.size();
        for (int i = 0; i < iterations; i ++){
            if (csp.noValueSatisfiesConstraint(arc.x1.domain.get(i), arc.x2.domain)){
                arc.x1.domain.remove(i);
                iterations --;
                revised = true;
            }
        }
        
        return revised;
    }
    
    
}
