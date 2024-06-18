/** 
 * @author Eliandro Pizzonia
 * 
 * This class is used to represent a tile grid board. 
 */
public class LetterCrush{
    
    // instance variables grid and EMPTY are initialized 
    private char[][] grid; 
    public static final char EMPTY = ' ';


    /**
     * contstructor that determines the grids demensions
     * @param width
     * @param height
     * @param initial
     */
    public LetterCrush(int width, int height, String initial){
       
        // creates grid with demesions form constructor
        grid = new char[height][width];     
    
        // filling the grid with the initial String values
        for(int i = 0; i < initial.length() && i < width * height; i++ ){
            grid[i/width][i % width] = initial.charAt(i);
        }

        // replacing any unused cells in the grid with EMPTY
        for( int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++ ){
                if (grid[i][j] == 0) {
                    grid[i][j] = EMPTY;
                }
            }

        }

    }

    /**
     * @return Returns a String representing the characters stored in the grid 
     */
    public String toString(){
        
        // building the string
        StringBuilder gridString = new StringBuilder();

        // appending each row grid to gridstring
        for(int i = 0; i < grid.length; i++){
            gridString.append("|");
                for(int j = 0; j < grid[i].length; j++ ){
                
                gridString.append(grid[i][j]);
           }  
          gridString.append("|" + i + "\n");

        }
   
        // appending the column numbers to gridstring
        gridString.append("+");
        for(int j = 0; j < grid[0].length; j++ ){
            gridString.append(j);
        }


        return  "\"LetterCrush\n" + gridString.toString() + "+\" ";
        
    }    

    /**
     * @return Returns false if there is any position in grid where a non-EMPTY character is 
        above an EMPTY character
     */
    public boolean isStable(){

        // looping through grid to determine if there is an empty below an non empty cell (not stable)
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++ ){
                if (grid[i][j] == EMPTY){
                   if(i >= 1 && grid[i-1][j] != EMPTY){
                    return false;
                   }
                }   
            }
        }
         
        return true;
    }


    /**
     * This method simulates gravity pulling tiles down by replacing 
        each EMPTY cell in grid with the one above it
     */
    public void applyGravity(){

        // swapVariable is used as a value holder when switching the empty cell with the non empty cell above it
        char swapVariable;

        // looping through grid from bottom to top and left to right
        for (int i = grid.length - 1; i >= 0; i--) {
            for (int j = grid[i].length - 1; j >= 0; j--) {
                
                // if a cell is empty and the cell above it is not empty, they are swaped
                if(grid[i][j] == EMPTY){
                    if(i >= 1 && grid[i-1][j] != EMPTY){
                        swapVariable = grid[i-1][j];
                        grid[i-1][j] = grid[i][j];
                        grid[i][j] = swapVariable;
                        }
                }

            }
        }
   
    }
       
    /**
     * @param theLine
     * @return false if theLine is not a valid line (if the line’s start or end points are 
        not within the grid)
     */

    public boolean remove(Line theLine){

        // invoking the getStart(), length(), and isHorizontal() methods to determine the start point, length, and direction of theline
       int [] start = theLine.getStart();
       int length = theLine.length();
       boolean horizontal = theLine.isHorizontal();
       int [] end = new int[2];
    
    // calculating the end point of the line
    if (horizontal) {
        end[0] = start[0];
        end[1] = start[1] + length - 1;
    }
    
    else{
        end[0] = start[0] + length - 1;
        end[1] = start[1];
    }
           
    // if the start point or end point is outside the grid, the line is not valid
    if (start[0] >= grid.length || start[1] >= grid[0].length){
            return false;
        }
    
    if (end[0] >= grid.length || end[1] >= grid[0].length) {
        return false;
    }

    // if the line is horizontal, the characters in the same row are removed
    if (horizontal) {
        for(int j = start[1]; j <= end[1]; j++){
                grid[start[0]][j] = EMPTY;
            }  
        }
    
    // if the line is vertical, the characters in the same colunm are removed
    else{
        for(int i = start[0]; i <= end[0]; i++){
            grid[i][start[1]] = EMPTY;

        }

    }
    return true;

    }


/**
 * 
 * @param theLine
 * @return a String similar to the above toString() method except that
    any letter corresponding to a grid location within theLine will be shown as a 
   lowercase letter
 */
    public String toString(Line theLine){


        // invoking the getStart(), length(), and isHorizontal() methods to determine the start point, length, and direction of theline
        int [] start = theLine.getStart();
       int length = theLine.length();
       boolean horizontal = theLine.isHorizontal();
 

        // building the string
        StringBuilder gridString = new StringBuilder();

        // looping through the grid
        for(int i = 0; i < grid.length; i++){
            gridString.append("|");

                for(int j = 0; j < grid[i].length; j++ ){
                    
                    // if the cell is in the line, the lowercase character is appended to gridstring
                    if (horizontal) {
                        if (i == start[0] && start[1] <= j && j <= start[1] + length) {
                        gridString.append(Character.toLowerCase(grid[i][j]));
                    }
                        // else append as it is
                        else{
                            gridString.append(grid[i][j]); 
                        }
                }
                    // same for vertical
                    else {
                        if (j == start[1] && start[0] <= i && i <= start[0] + length) {
                            gridString.append(Character.toLowerCase(grid[i][j]));
                        }
                    
                        else{
                     gridString.append(grid[i][j]);   
                    }
                 } 
                
           }
          gridString.append("|" + i + "\n");

        }
   
        // appending the column numbers to gridstring
        gridString.append("+");
        for(int j = 0; j < grid[0].length; j++ ){
            gridString.append(j);
        }


        return  "\"CrushLine\n" + gridString.toString() + "+\" ";
        
    }   

/**
 * 
 * @return returns a uniquely defined longest line in grid that contains adjacent
    matching letters.
    @return Returns null if there does not exist in grid a line of at least 3 adjacent matching letters
 */
    public Line longestLine(){

        // initializing the longest line as a single cell line and setting the length of the largest line to 0
        Line longLine = new Line(0,0,true, 1);
        int largest = 0;

        //initializing the current letter and the count of adjacent letters that are matching 
        for (int i = grid.length - 1; i >= 0; i--){
          char letter =  grid[i][0];     
          int adjacent = 1;

            // looping through the grid from left to right and checking if the current cell matches the one before and incrementing the adjacent count
            for(int j = 1; j < grid[i].length; j++) {
                if (grid[i][j] == letter && grid[i][j] != EMPTY) {
                    adjacent += 1;   
                
                    // if the adjacent count is greater than the length of longest line, the longest line is updated
                    if (adjacent > largest) {
                        largest = adjacent;
                        longLine = new Line(i,j - adjacent + 1,true,adjacent);
                    }
                }
                // else the current letter is updated and the adjacent count is reset
                else{

                    letter = grid[i][j];
                    adjacent = 1;

                }
            }      
        }

        // (same for columns)
        for(int j = 0; j < grid[0].length; j++){

            char letter = grid[grid.length - 1][j];
            int adjacent = 1;

            for(int i = grid.length - 2; i >= 0; i--){

                if (grid[i][j] == letter && letter != EMPTY) {
                    adjacent += 1;
                
                    if (adjacent > largest) {
                        
                        largest = adjacent;
                        longLine = new Line(i, j, false, adjacent);
                        }

                    }
                
                else{

                    letter = grid[i][j];
                    adjacent = 1;

                }

            }
        }
 
        // if the length of longline is larger than 2 then the longline is returned
        if (longLine.length() > 2) {
            
            return longLine;
        }


        else{

            return null;
        }
        
    }             
                
    /**
     * iteratively removes the longest line of adjacent matching letters, of 
        length at least 3 and repeatedly invokes the applyGravity method
     */
    public void cascade(){

        // while there is a longest line that contains at least three adjacent cells, the longest line is removed and the gravity is applied
        while(longestLine() != null){
            Line longestLine = longestLine();
            remove(longestLine);
            applyGravity();

        }

    }
            
}
        




