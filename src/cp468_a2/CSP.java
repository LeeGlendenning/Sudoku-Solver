package cp468_a2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Lee Glendenning
 */
public class CSP {
    Cell vars[][];
    ArrayList<Arc> arcs;
    int n;
    
    // reads suduku grid file and sets domain and vars variables accordingly
    public CSP(File f) throws FileNotFoundException{
        Scanner s = new Scanner(f);
        n = s.nextInt();
        System.out.println("n = "+n);
        setVars(s, n);
        arcs = new ArrayList();
        generateArcs();
    }
    
    private void setVars(Scanner s, int n){
        vars = new Cell[n][n];
        
        for (int i = 0; i < n; i ++){
            for (int j = 0; j < n; j ++){
                vars[i][j] = new Cell(i, j, s.nextInt());
            }
        }
    }
    
    public boolean isSolved(){
        for (int i = 0; i < n; i ++){
            for (int j = 0; j < n; j ++){
                if (vars[i][j].domain.size() != 1){
                    return false;
                }
            }
        }
        return true;
    }
    
    private void generateArcs(){
        for (int i = 0; i < n; i ++){
            for (int j = 0; j < n; j ++){
                
                Cell curCell = vars[i][j];
                Cell neighbours[] = getNeighbours(curCell);
                for (int k = 0; k < neighbours.length; k ++){
                    arcs.add(new Arc(curCell, neighbours[k]));
                }
            }
        }
    }
    
    public void updateValues(){
        //System.out.println("---------------------------------");
        //System.out.println("Update Values:");
        for (int i = 0; i < n; i ++){
            for (int j = 0; j < n; j ++){
                //System.out.println(vars[i][j].domain.size());
                if (vars[i][j].value == 0 && vars[i][j].domain.size() == 1){
                    vars[i][j].value = vars[i][j].domain.get(0);
                    //System.out.println("Adding value ("+vars[i][j].value+") at ("+i+","+j+")");
                }
            }
        }
    }
    
    public Cell[] getNeighbours(Cell c){
        Cell neighbours[] = new Cell[(n-1)*2 + new Double(Math.floor(n/2)).intValue()];
        
    //check that this function actually manipulates the neighbours object!!!
        fillNeighbours(neighbours, getRowNeighbours(c), getColNeighbours(c), getRegionNeighbours(c));
        
        return neighbours;
    }
    
    private void fillNeighbours(Cell neighbours[], Cell row[], Cell col[], Cell region[]){
        int i = 0;
        
        for (int j = 0; j < n-1; j ++){
            neighbours[i] = row[j];
            //System.out.println("neighbours["+i+"] = "+neighbours[i]);
            i ++;
        }
        for (int j = 0; j < n-1; j ++){
            neighbours[i] = col[j];
            //System.out.println("neighbours["+i+"] = "+neighbours[i]);
            i ++;
        }
        for (int j = 0; j < new Double(Math.floor(n/2)).intValue(); j ++){
            neighbours[i] = region[j];
            //System.out.println("neighbours["+i+"] = "+neighbours[i]);
            i ++;
        }
    }
    
    private Cell[] getRowNeighbours(Cell c){
        Cell rowNeighbours[] = new Cell[n-1]; // array of size 8
        int j = 0;
        for (int i = 0; i < n; i ++){
            if (i != c.x){
                rowNeighbours[j] = vars[i][c.y];
                j ++;
            }
        }
        return rowNeighbours;
    }
    
    private Cell[] getColNeighbours(Cell c){
        Cell colNeighbours[] = new Cell[n-1]; // array of size 8
        int j = 0;
        for (int i = 0; i < n; i ++){
            if (i != c.y){
                colNeighbours[j] = vars[c.x][i];
                j ++;
            }
        }
        return colNeighbours;
    }
    
    private Cell[] getRegionNeighbours(Cell c){
        Cell regNeighbours[] = new Cell[new Double(Math.floor(n/2)).intValue()]; // array of size 8
    //check that this function actually manipulates the neighbours object!!!
        fillRegNeighbours(regNeighbours, getRegionNumber(c.x, c.y), c);
        
        return regNeighbours;
    }
    
    private int getRegionNumber(int row, int col){
        
        /*    Regions
         *  ___ ___ ___
         * |_1_|_2_|_3_|
         * |_4_|_5_|_6_|
         * |_7_|_8_|_9_|
         * 
         */
        int x, y;
        
        if (row < 3){
            x = 1;
        }else if (row >= 3 && row < 6){
            x = 2;
        }else{// 7 <= row <= 9
            x = 3;      
        }
        
        
        if (col < 3){
            y = 1;
        }else if (col >= 3 && col < 6){
            y = 2;
        }else{// 7 <= col <= 9
            y = 3;
        }
        
        //System.out.println("region number "+ (x + (y-1)*3));
        return x + (y-1)*3;
    }
    
    private void fillRegNeighbours(Cell regNeighbours[], int region, Cell c){
        int xmin, xmax;
        int ymin, ymax;
        /*    Regions
         *  ___ ___ ___
         * |_1_|_2_|_3_|
         * |_4_|_5_|_6_|
         * |_7_|_8_|_9_|
         * 
         */
        switch(region){
            case 1:
            case 4:
            case 7:
                xmin = 0;
                xmax = 2;
                break;
            case 2:
            case 5:
            case 8:
                xmin = 3;
                xmax = 5;
                break;
            case 3:
            case 6:
            case 9:
                xmin = 6;
                xmax = 8;
                break;
            default:
                xmin = -1;
                xmax = -1;
        }
         // y
        switch(region){
            case 1:
            case 2:
            case 3:
                ymin = 0;
                ymax = 2;
                break;
            case 4:
            case 5:
            case 6:
                ymin = 3;
                ymax = 5;
                break;
            case 7:
            case 8:
            case 9:
                ymin = 6;
                ymax = 8;
                break;
            default:
                ymin = -1;
                ymax = -1;
        }
        //System.out.println("xmin = "+xmin+", ymin = "+ymin+"xmax = "+xmax+", ymax = "+ymax);
        //System.out.println(region);
        int k = 0;
        for (int i = xmin; i <= xmax; i ++){
            for (int j = ymin; j <= ymax; j ++){
                //System.out.println("i="+i+", j="+j+", c.x="+c.x+", c.y="+c.y);
                if (i != c.x && j != c.y){
                    //System.out.println("adding that one^");
                    regNeighbours[k] = vars[i][j];
                    k ++;
                }
            }
        }
    }
    
    public boolean noValueSatisfiesConstraint(int val, ArrayList<Integer> domain){
        if (domain.size() == 1 && val == domain.get(0)){
            return true;
        }
        return false;
    }
    
    public void printGrid(){
        //System.out.println(" ");
        for (int i = 0; i < vars.length; i ++){
            System.out.print(" _____");
        }
        System.out.println();
        for (int i = 0; i < vars.length; i ++){
            for (int j = 0; j < vars.length; j ++){
                if (vars[i][j].value > 0){
                    System.out.print("|  " + vars[i][j].value + "  ");
                }else{
                    System.out.print("|     ");
                }
                if (j == vars.length - 1){
                    System.out.println("|");
                }
            }
            //System.out.println();
            for (int k = 0; k < vars.length; k ++){
                System.out.print("|_____");
            }
            System.out.println("|");
        }
    }
}
