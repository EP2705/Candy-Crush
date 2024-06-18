/** 
 * @author Eliandro Pizzonia
 * 
 * This class represents a horizontal or verticle line in an 
 * array and stores the rows and columns of the endpoints of the line. 
 */
public class Line {

        private int[] start;
        private int[] end;
    
    
    /**
     * The constructor which contstructs the line
     * @param row
     * @param col
     * @param horizontal
     * @param length
     */
    public Line(int row, int col, boolean horizontal, int length){

        // creating the start and end arrays
        start = new int[2];
        end = new int[2];
        
        start[0] = row;
        start[1] = col;

        // checking if horizontal to determine if the end point will be on the same row or column 
        if(horizontal){
            end[0] = row;
            end[1] = col + length - 1;
        } 

        else {
            end[0] = row + length - 1;
            end[1] = col;
        }

    }
    

    /**
     * 
     * @return returns a copy of the start method 
     */
    public int[] getStart(){

        // creating a new array to store the copy of start
        int [] copyStart = new int[2];

        // for loop to copy each element from the start array to the copyStart array 
        for (int i = 0; i < start.length; i++){
            copyStart[i] = start[i];
        }
            return copyStart;
    }



   /**
    * 
    * @return the length of line (positive value same as length in contstructor)
    */
    public int length(){
       
       // if the line is horizontal, the length is calculated by the difference in the columns 
        if(start[0] == end[0]){

        int length = Math.abs(end[1] + 1 - start[1]);
        
        return length;
    }
        // if the line is vertical, the length is calculated by the difference in the rows
        else{ 
            int length = Math.abs(end[0] + 1 - start[0]);
            return length;
        }

    }


    /**
     * checks if the line is horizontal
     * @return true if line is horizontal
     */

    public boolean isHorizontal(){

        // the line is horizontal if the first element of the start and end arrays are the same
        if (start[0] == end[0]) {
            return true;
        }
        else{
            return false;
        }

    }


    /**
     * @param row
     * @param col
     * @return true if the position of the grid at the row and column indicated by the 
    parameters is contained in the Line
     */
    public boolean inLine(int row, int col){    

        // if the point is between the start and end points, it is in the line
        if ((start[0] <= row && row <= end[0]) && (start[1] <= col && col <= end[1])) {
            return true;
        }
        else{
            return false;
        }

    }

    /**
     * Returns a String with the endpoints
     * @return A String with endpoints
     */
    public String toString(){

    // building the string
    StringBuilder lineString = new StringBuilder();
    
    // appending the start point to the linestring
    lineString.append("Line:[");
    for(int i = 0; i < start.length; i++){
        lineString.append(start[i]);
        if (i != start.length - 1) {
            lineString.append(",");
        }
        
    }
    // apending the end point to the linestring
    lineString.append("]->[");
    for(int j = 0; j < end.length; j++){
         lineString.append(end[j]);
         if (j != end.length -1 ) {
            lineString.append(",");
        }
        
       
    }

    lineString.append("]");

    // returning the string with the toString() method
    return lineString.toString();



    }


}