package cp468_a2;

import java.util.ArrayList;

/**
 *
 * @author Lee Glendenning
 */
public class Cell {
    
    int x, y;
    int value;
    ArrayList<Integer> domain;
    
    public Cell(int xCoord, int yCoord, int val){
        x = xCoord;
        y = yCoord;
        value = val;
        domain = new ArrayList();
        
        // set domain of cell
        if (val == 0){
            for (int i = 1; i < 10; i ++){
                domain.add(i);
            }
        }else{
            domain.add(val);
        }
    }
    
    @Override
    public boolean equals(Object c2){
        if (((Cell)c2).x == x && ((Cell)c2).y == y && ((Cell)c2).value == value && ((Cell)c2).domain.equals(domain)){
            return true;
        }
        
        return false;
    }
    
    @Override
    public String toString(){
        return "x: "+x+", y: "+y+", value: "+value+", "+domain.toString();
    }
}
