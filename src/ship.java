import java.util.*;

public class ship { // Used for handling ships and relavent functions
    static Scanner in = new Scanner(System.in);
    static Random rand = new Random();
    private int size;
    private int [] positions;
    private boolean sank;

    public ship (int size) { // Creates a ship based on size
        this.size = size;
        this.positions = new int[size];
        sank = false;
    }

    public boolean isSank() {
        return sank;
    }

    public void sinkShip() {
        sank = true;
    }

    public int[] getPositions() {
        return positions;
    }

    public int getSize() {
        return size;
    }

    public void setPositions(int[] positions) {
        this.positions = positions;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int[] setPose(Grid grid) { // Used for getting the player to set up a ship
        boolean valid = false; 
        char letter = 'a';
        int num = 0;
        String coord = "";
        boolean[] bools = new boolean[4];
        int orientation = 0;
        int [][] positionsList = new int[4][size];
        while(!valid) { // Keeps looping until a valid position
            valid = false;
            while(!valid) { // Keeps looping while a valid position
                System.out.println("Where would you like your ship to be placed?");
                coord = in.nextLine();
                if (coord.length() == 2) { // If the length is not 2, not valid
                    if (((int) coord.toUpperCase().charAt(0) >= 65) && ((int) coord.toUpperCase().charAt(0) <= 74) && ((int) coord.charAt(1) >= 48) && ((int) coord.charAt(1) <= 57)) { // If the coordinate is on the grid, valid
                    valid = true;
                    }
                    else {
                        System.out.println("That is not a valid coordinate!");

                    }
                }
                else {
                        System.out.println("That is not a valid coordinate!");

                }
            }
            letter = coord.charAt(0);
            int letternum = ((int) coord.toUpperCase().charAt(0)-65);
            num = coord.charAt(1) - '0';
            bools = findValidOrientation(grid.getShipPositions(), num, letternum); // Finds the valid orientations of the ship
            orientation = 0;
            for (int i = 0; i < size; i++) { // Sets up possible positions
                positionsList[0][i] = letternum*10 + num + i;
            }
            for (int i = 0; i < size; i++) {
                positionsList[1][i] = (letternum+i)*10 + num;
            }
            for (int i = 0; i < size; i++) {
                positionsList[2][i] = (letternum)*10 + num-i;
            }
            for (int i = 0; i < size; i++) {
                positionsList[3][i] = (letternum-i)*10 + num;
            }
            if (bools[4] && bools[5] && bools[6] && bools[7]) { // If the ship neighbours a ship in every direction, not valid
                System.out.println("There is another ship neighbouring the ship. You must find somewhere else to place it.");
                valid = false; 
            } 
            if (bools[0]) {  // Sets default orientation, if not available, moves on to the next
                orientation = 0;
            }
            else if (bools[1]) {
                orientation = 1;   
            }
            else if (bools[2]) {
                orientation = 2;   
            }
            else if (bools[3]) {
                orientation = 3;
            }
            else {
                System.out.println("Sorry, the ship is out of bounds or interferes with anothership.");
                valid = false; 
            }
        }
        
        int[] newpositions = rotateShip(positions, bools, grid, orientation, positionsList); // Gets positions based on how the user wants to rotate the ship
        this.positions = newpositions;
        return newpositions;
        
        

        
        

    }
    public Boolean contains(ArrayList <Integer> placed, int num) { // Method for checking if an arraylist contains a number (linear search)
        boolean contained = false; 
        for (int i = 0; i < placed.size(); i++) {
            if (num == placed.get(i)) {
                contained = true;
            }
        }
        return contained;
    }

    public boolean[] findValidOrientation(ArrayList <Integer> placed, int  num, int letternum) { // Method for determining valid orientations of a ship
        boolean validHor = true, validVer = true, validHorNeg = true, validVerNeg = true;
        int currPose = 0;
        boolean surroundedShipHor = false, surroundedShipVer = false, surroundedShiphorNeg = false, surroundedShipVerNeg = false;
        int [] endpoints = new int[5];
        endpoints[0] = letternum*10+num; // Keeps track of endpoints to check them seperatly
        for (int i = 0; i < size; i++) { // Goes through each position to the right
            currPose = letternum*10+num+i;
            if (contains(placed, currPose-10) || contains(placed, currPose+10)) { // If the positions are adjacent with another ship
                surroundedShipHor = true;
                validHor = false;
            }
            if (contains(placed, currPose) || ((num+i) > 9 )) { // If the position interferes with a ship or is out of bounds, not valid
                validHor = false;
            }
        }
        endpoints[1] = currPose;
        for (int i = 0; i < size; i++) { // Goes through each position on the bottom
            currPose = (letternum+i)*10+num;
            if (((contains(placed, currPose-1) || contains(placed, currPose + 1)) && ((num+1) < 10) && ((num-1) >= 0))|| (num+1 ==10 && contains(placed, currPose-1)) || (num == 0 && contains(placed, currPose+1)) ) { // If the position is next to a ship 
                surroundedShipVer = true;
                validVer = false;
            }
            if (contains(placed, currPose) || ((letternum+i) > 9 )) { // If position interferes with ship or out of bounds, not valid
                validVer = false;
            }
        }
        endpoints[2] = currPose;

        for (int i = 0; i < size; i++) { // Goes through each position to the left
            currPose = letternum*10 + num - i;
            if (contains(placed, currPose-10) || contains(placed, currPose+10)) { // If adjacent, false
                surroundedShiphorNeg = true;
                validHorNeg = false;
            }
            if (contains(placed, currPose) || ((num-i) < 0 )) { // If intereferes or out of bounds, false
                validHorNeg = false;
            }
        }
        endpoints[3] = currPose;
        for (int i = 0; i < size; i++) { // Goes through each position on top
            currPose = (letternum-i)*10 + num;
            if ((contains(placed, currPose-1) || contains(placed, currPose+1))  && ((num+1) < 10) && ((num-1) > 0)) { // If adjacent, false
                surroundedShipVerNeg = true;
                validVerNeg = false;
            }
            if (contains(placed, currPose) || ((letternum-i) < 0 )) { // If interferes or out of bounds, false
                validVerNeg = false;
            }
        }
        endpoints[4] = currPose;

        if ((contains(placed,endpoints[0]+1) && endpoints[0]%10+1 < 10) || (contains(placed, endpoints[0]-1) && endpoints[0]%10-1 >= 0) || contains(placed, endpoints[0]-10) || contains(placed, endpoints[0]+10)) { // Checks to see if the starting point is valid
            validHor = false;
            validVer = false;
            validHorNeg = false;
            validVerNeg = false;
        }

        if (contains(placed, (endpoints[1]+1)) && endpoints[1]%10+1 < 10) { // Checks to see if end points are valid
            validHor = false;
            surroundedShipHor = true;
        }

        if (contains(placed, endpoints[2]+10)) {
            validVer = false;
            surroundedShipVer = true;

        }

        if (contains(placed, (endpoints[3]-1)) && endpoints[1]%10-1 >= 0) {
            validHorNeg = false;
            surroundedShiphorNeg = true;

        }

        if (contains(placed, endpoints[4]-10)) {
            validVerNeg = false;
            surroundedShipVerNeg = true;

        }

        boolean [] bools = {validHor, validVer, validHorNeg, validVerNeg, surroundedShipHor,surroundedShipVer,surroundedShiphorNeg,surroundedShipVerNeg}; // Returns all the booleans
        return bools;
    }

    public int[] rotateShip(int[] positions, boolean[] bools, Grid grid, int orientation, int[][] positionsList) { // Method to allow user to rotate the ship
        boolean end = false;
        while (!end) { // Until user chooses not to
            grid.printGrid(positionsList[orientation]); // Displays the current orientation
            System.out.println("If you would like to rotate the ship, type r. Otherwise, press enter to select the orientation.");
            String answer = in.nextLine();
            if (answer.length() == 0) {  // If they press enter, exit
                end = true;
            }
            else { // If they do not exit
                char c = answer.toLowerCase().charAt(0);
                if (c=='r') { // If they type "r"
                    boolean valid = false; 
                    while (!valid) { // Keeps looping until valid orientation is reached
                        orientation++; // Increments orientation
                        if (orientation > 3) { // If orientation goes above 3, drops back to 0
                            orientation = 0;
                        }
                        if (bools[orientation] == true) { // If orientation is available, exits loop
                        valid = true;
                        }
                    }
                }
                else {
                    end = true;
                }
            }
            
        }

       
        
        return positionsList[orientation];
    }

    public int[] setPoseComputer(Grid grid) { // Method for setting up computer pose. 
        boolean valid = false; 
        int coord = 0;
        int num = 0;
        int letternum = 0;
        boolean[] bools = new boolean[4];
        int orientation = 0;
        int [][] positionsList = new int[4][size];
        while(!valid) { // Until valid position
            valid = true;
            int random = rand.nextInt(0, grid.getOpenSquares().size()); // picks random position
            coord = grid.getOpenSquares().get(random); // Gets the random position from the index
            num = coord%10;
            letternum = (coord-num)/10;
            bools = findValidOrientation(grid.getShipPositions(), num, letternum); // Gets the booleans
            orientation = 0;
            for (int i = 0; i < size; i++) { // Generates all possible positions
                positionsList[0][i] = letternum*10 + num + i;
            }
            for (int i = 0; i < size; i++) {
                positionsList[1][i] = (letternum+i)*10 + num;
            }
            for (int i = 0; i < size; i++) {
                positionsList[2][i] = (letternum)*10 + num-i;
            }
            for (int i = 0; i < size; i++) {
                positionsList[3][i] = (letternum-i)*10 + num;
            }
            if (bools[4] && bools[5] && bools[6] && bools[7]) { // If adjacent to ship, false
                valid = false; 
            }
            if (bools[0]) {  // Gets the default orientation
                orientation = 0;
            }
            else if (bools[1]) {
                orientation = 1;   
            }
            else if (bools[2]) {
                orientation = 2;   
            }
            else if (bools[3]) {
                orientation = 3;
            }
            else {
                valid = false; 
            }
        }
        
        int[] newpositions = rotateShipComputer(positions, bools, grid, orientation, positionsList); // Rotates ship randomly
        this.positions = newpositions;
        return newpositions;    

    }

    public int[] rotateShipComputer(int[] positions, boolean[] bools, Grid grid, int orientation, int[][] positionsList) { // Method for rotating computer's ship
        boolean valid = false;
        while (!valid) { // Until valid oreintation
            orientation = rand.nextInt(0, 4); // Generates random orientation 0-3
            if (bools[orientation] == true) { // if it is valid, exit loop
            valid = true;
            }
        }
        
        return positionsList[orientation];
    }

    public boolean checkIfSunken(Grid grid) { //Method for checking if the ship is sunken
        boolean sunk = true; // Sets default to true
        for (int i = 0; i < size; i++) {
            if (!contains(grid.getSunken(), positions[i])) { // IF any position is not contained in the sunken arraylist, the ship is not sunken
                sunk = false;
            }
        }
        return sunk;
    }

    

    
}
