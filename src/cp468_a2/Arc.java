package cp468_a2;

/**
 *
 * @author Lee Glendenning
 */
public class Arc {
    
    Cell x1, x2;
    
    public Arc(Cell xi, Cell xj){
        x1 = xi;
        x2 = xj;
    }
    
    @Override
    public boolean equals(Object o){
        if (x1.equals(((Arc)o).x1) && x2.equals(((Arc)o).x2)){
            return true;
        }
        return false;
    }
}
