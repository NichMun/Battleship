import java.util.*;

public class Grid { // Used to create a grid and all functions associated with it
    static Random rand = new Random();
    static Scanner in = new Scanner(System.in);
    private int length;
    private int height;
    private ArrayList<Integer> shipPositions;
    private ArrayList<Integer> guesses;
    private ArrayList<Integer> sunken;
    private ArrayList<Integer> openSquares;
    private ship[] ships;
    private ArrayList<Integer> fullySunkenPositions;

    public Grid(int length, int height) { // Grid is created using height dimensions
        this.length = length;
        this.height = height;
        this.shipPositions = new ArrayList<>();
        this.guesses = new ArrayList<>();
        this.sunken = new ArrayList<>();
        ArrayList<Integer> temp = new ArrayList<>(); 
        for (int i = 0; i < 100; i++) { // Open squares are 0-99 at start of game
            temp.add(i);
        }
        this.openSquares = temp;
        this.ships = new ship[5];
        this.fullySunkenPositions = new ArrayList<>();
    }

    public ship[] getShips() {
        return ships;
    }

    public void addFullySunkenPosition(int position) { // Adds a sunken position
        fullySunkenPositions.add(position);
    }

    public void removeOpenSquare(int index) { // Method for removing previous guesses
        openSquares.remove(index);
    }

    public ArrayList<Integer> getOpenSquares() {
        return openSquares;
    }

    public ArrayList<Integer> getShipPositions() {
        return shipPositions;
    }

    public void addShipPositions(int[] positions) { // Adds positions when setting up grid
        for (int position : positions) {
            shipPositions.add(position);
        }
    }

    public ArrayList<Integer> getguesses() {
        return guesses;
    }

    public void addGuess(int guess) { // Adds a guess when guessing
        guesses.add(guess);
    }

    public ArrayList<Integer> getSunken() {
        return sunken;
    }

    public void addSunken(int sunk) { // Adds a guess when a ship is hit
        sunken.add(sunk);
    }


    public void printGrid(int[] positions) { // Method for printing grid when setting up game
        System.out.println("  | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 |");
        System.out.println("  ----------------------------------------");
        for (int i = 0; i < height; i++) { // Creates each row
            System.out.print((char) (i + 65) + " "); // Converts to letters A-J
            for (int j = 0; j < length; j++) { // Prints each cell in the row
                int cell = i * 10 + j;
                System.out.print("| ");
                if (contains(shipPositions, cell) || contains(positions, cell)) {
                    System.out.print("o ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.print("|");
            System.out.println();
            System.out.println("   ---------------------------------------");
        }
    }

    public void printGrid() { // Method for printing grid during game
        System.out.println();
        System.out.println("Your Grid:");
        System.out.println("  | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 |");
        System.out.println("  ----------------------------------------");
        for (int i = 0; i < height; i++) { // Creates each row
            System.out.print((char) (i + 65) + " ");
            for (int j = 0; j < length; j++) { // Converts to letters A-J
                int cell = i * 10 + j;
                System.out.print("| ");
                if (contains(shipPositions, cell)) { 
                    if (contains(fullySunkenPositions, cell)) { // If ship is sunk, display all S's
                        System.out.print("S ");
                    }
                    else if (contains(sunken, cell)) { // If ship is hit but not sunk, display '!'
                        System.out.print("! ");
                    }

                    else { // If ship is there but is not hit, display 'o'
                        System.out.print("o ");
                    }
                } else if (contains(guesses, cell)) { // If the square was guessed display 'x'
                    System.out.print("x ");
                } else { // If empty square, do nothign
                    System.out.print("  ");
                }
            }
            System.out.print("|");
            System.out.println();
            System.out.println("   ---------------------------------------");
        }
    }

    public void printComputerGrid() { // Method for printing computer's grid so that ships are not visible to player (Same thing as above, except removed the 'o')
        System.out.println();
        System.out.println("Enemy Grid:");
        System.out.println("  | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 |");
        System.out.println("  ----------------------------------------");
        for (int i = 0; i < height; i++) {
            System.out.print((char) (i + 65) + " ");
            for (int j = 0; j < length; j++) {
                int cell = i * 10 + j;
                System.out.print("| ");
                if (contains(fullySunkenPositions, cell)) {
                    System.out.print("S ");
                }
                else if (contains(sunken, cell)) {
                    System.out.print("! ");
                } else if (contains(guesses, cell)) {
                    System.out.print("x ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.print("|");
            System.out.println();
            System.out.println("   ---------------------------------------");
        }
    }

    public boolean contains(int[] positions, int cell) { // Method for checking if an array contains a cell (linear search)
        boolean contained = false;
        for (int i = 0; i < positions.length; i++) {
            if (positions[i] == cell) {
                contained = true;
            }
        }
        return contained;
    }

    public boolean contains(ArrayList<Integer> positions, int cell) { // Method for checking if an arraylist contains a cell (linear search)
        boolean contained = false;
        for (int i = 0; i < positions.size(); i++) {
            if (positions.get(i) == cell) {
                contained = true;
            }
        }
        return contained;
    }

    public void setUpGrid() { // Sets up player's grid
        for (int i = 2; i < 7; i++) { // For each ship
            ships[i - 2] = new ship(i); // Creates a new ship (sizes 2-6)
            int[] positions = ships[i - 2].setPose(this); // Sets up the ship
            addShipPositions(positions); // Adds the ship's positions to the grid
        }
    }

    public boolean guess(Grid enemyGrid) { // Method for having the player guess
        boolean valid = false;
        String coord = "";
        int letternum = 0;
        int num = 0;
        while (!valid) { // Keeps looping until there is a valid guess
            System.out.println("Where would you like to guess?");
            wait(1);
            coord = in.nextLine();
            if (coord.length() == 2) { // Avoids error on the line below if player only types one character.
                if (((int) coord.toUpperCase().charAt(0) >= 65) && ((int) coord.toUpperCase().charAt(0) <= 74) // If the first character is A-J && second character is 0-9
                        && ((int) coord.charAt(1) >= 48) && ((int) coord.charAt(1) <= 57)) {
                    valid = true;
                    char letter = coord.charAt(0); 
                    letternum = ((int) coord.toUpperCase().charAt(0) - 65);
                    num = coord.charAt(1) - '0';
                    if (contains(enemyGrid.getguesses(), letternum * 10 + num) || contains(enemyGrid.getSunken(), letternum * 10 + num)) { // If player already guessed there, not valid
                        System.out.println("You've already guessed there!");
                        valid = false;
                    }
                } else { // If coordinate does not exist, not valid
                    System.out.println("That is not a valid coordinate!");
                }
            } else { // If coordinate is not length 2, not valid
                System.out.println("That is not a valid coordinate!");
            }
        }
        int guess = num + letternum * 10;
        boolean hit = false;
        wait(1);
        if (contains(enemyGrid.getShipPositions(), guess)) { // If it is a hit

            enemyGrid.addSunken(guess); // Add the guess to the hit positions
            System.out.println("Great job, you landed a hit on " + coord);
            checkIfNewShipSunk(enemyGrid); // Checks if ship sinks
            hit = true;
        } else { // If it is a miss

            System.out.println("Sorry, you missed.");
            enemyGrid.addGuess(guess); // Adds the miss to the guesses arraylist
        }
        wait(1);
        return hit; // Returns true(hit) or false(miss)
    }

    public boolean checkIfLost() { // Checks if game is lost
        boolean lost = false;
        if (sunken.size() == shipPositions.size()) { // If the grid has as many sunken squares as ship positions, game is lost
            lost = true;
        }
        return lost;
    }

    public void setUpGridComputer() { // Seperate method for setting up computer grid. Does not display ships
        for (int i = 2; i < 7; i++) {
            ships[i - 2] = new ship(i);
            int[] positions = ships[i - 2].setPoseComputer(this);
            addShipPositions(positions);
        }
    }

    public gridGuess guessComputer(Grid grid, gridGuess info) { // Method for having the computer guess
        wait(1);
        int guess = 0;
        int index = 0;
        boolean valid = false;

        if (info.getHit()) { // If the computer already hit a new ship
            if (info.getLastHit() == -1) { // If the computer only had one guess (uncertain direction)
                guess = info.getSecondLastHit() + 1;
                if (contains(grid.getguesses(), guess) // Checks to see if the square to the right is available 
                        || (contains(grid.getSunken(), guess + 1) && guess % 10 + 1 < 10) || guess % 10 == 0) {
                    info.setBool(0, false);
                }
                guess = info.getSecondLastHit() - 1;
                if (contains(grid.getguesses(), guess) // Checks to see if the square to the left is available
                        || (contains(grid.getSunken(), guess - 1) && guess % 10 - 1 > 0) || guess % 10 == 9) {
                    info.setBool(2, false);
                }
                guess = info.getSecondLastHit() + 10; 
                if (contains(grid.getguesses(), guess) || contains(grid.getSunken(), guess + 10) // Checks to see if the square to the bottom is available
                        || guess / 10 == 10) {
                    info.setBool(1, false);
                }
                guess = info.getSecondLastHit() - 10;
                if (contains(grid.getguesses(), guess) || contains(grid.getSunken(), guess - 10) // Checks to see if the square on top is available
                        || guess / 10 == 0) {
                    info.setBool(3, false);
                }
                valid = false;
                while (!valid) { // Keeps refreshing until a valid direction is chosen
                    index = rand.nextInt(0, 4); // Picks a direction
                    if (info.getBools()[index]) { // If direction is valid, exit loop
                        valid = true;
                    }

                }
                info.setDirection(index);
                int[] guesses = { info.getSecondLastHit() + 1, info.getSecondLastHit() + 10, info.getSecondLastHit() - 1, info.getSecondLastHit() - 10 };
                guess = guesses[index]; // Takes the guess based on the direction
                int num = guess % 10;
                char letternum = (char) (guess / 10 + 65);
                grid.removeOpenSquare(guess, true);
                System.out.println("The computer guessed " + letternum + "" + num);
                wait(1);

                if (contains(grid.getShipPositions(), guess)) { // If the guess is contained in the ship positions
                    grid.addSunken(guess); // Add it to the sunken array list
                    System.out.println("The computer hit!");
                    info.setLastHit(guess); // Sets the last hit to indicate a second hit
                    info.setHitActual(true); // Used to determine if the player keeps going 
                    if (grid.checkIfNewShipSunk(grid)) { // Checks if a new ship was sunken
                        info.setNewSunkenShip(); // Updates the information about the guess to prevent another guess in the same direction
                    }

                    wait(1);
                } else { // If missed
                    grid.addGuess(guess); // Adds the guess
                    System.out.println("The computer missed!");
                    info.setHitActual(false); // Prevents the computer from going again
                    wait(1);
                    info.setBool(index, false); // Sets the direction to false

                }

            } else { // If the computer had another hit
                if (info.getDirection() == 0 && (info.getLastHit()%10 == 9 || contains(grid.getSunken(), info.getLastHit()+1) || contains(grid.getguesses(), info.getLastHit()+1))) { // Checks to see if the right direction will be out of bounds or next to another ship
                    info.setBool(0,false);
                    
                    info.setDirection(2); // Switches direction
                    info.setLastHit(info.getSecondLastHit()); // Resets the last hit
                }
                if (info.getDirection() == 2 && (info.getLastHit()%10 == 0 || contains(grid.getSunken(), info.getLastHit()-1) || contains(grid.getguesses(), info.getLastHit()-1))) { // Checks to see if the left direction will be out of bounds or next to another ship
                    info.setDirection(0); // Switches direction
                    info.setLastHit(info.getSecondLastHit()); // Resets the last hit

                }
                if (info.getDirection() == 1 && (contains(grid.getSunken(), info.getLastHit()+10) || contains(grid.getguesses(), info.getLastHit()+10) || info.getLastHit()/10 == 9)) { // Checks to see if the down direction will be out of bounds or next to another ship
                    info.setDirection(3); // Switches direction
                    info.setLastHit(info.getSecondLastHit()); // Resets the last hit
                }
                if (info.getDirection() == 3 && (contains(grid.getSunken(), info.getLastHit()-10) || contains(grid.getguesses(), info.getLastHit()-10) || info.getLastHit()/10 == 0)) { // Checks to see if the up direction will be out of bounds or next to another ship
                    info.setDirection(1); // Switches direction
                    info.setLastHit(info.getSecondLastHit()); // Resets the last hit
                }
                int[] guesses = { info.getLastHit() + 1, info.getLastHit() + 10, info.getLastHit() - 1, info.getLastHit() - 10 };
                guess = guesses[info.getDirection()]; // Gets the guess based on the direction
                
                
                grid.removeOpenSquare(guess, true); // Removes the square to make sure it is not guessed again
                int num = guess % 10;
                char letternum = (char) (guess / 10 + 65);
                System.out.println("The computer guessed " + letternum + "" + num);
                if (contains(grid.getShipPositions(), guess)) { // If the computer hit
                    info.setHitActual(true); // Sets hit to be true
                    grid.addSunken(guess); // Adds the position to prevent repeated guesses
                    System.out.println("The computer hit!");
                    info.setLastHit(guess); // Updates the last hit
                    if (grid.checkIfNewShipSunk(grid)) { // Checks if new ship sinks. If so, resetsvalues
                        info.setNewSunkenShip();
                    }

                } else { // If miss, changes the direction to the opposite.
                    grid.addGuess(guess);
                    System.out.println("The computer missed!");
                    index = info.getDirection();
                    index += 2;
                    if (index >= 4) {
                        index -= 4;
                    }
                    info.setLastHit(info.getSecondLastHit()); // Resets the last hit.
                    info.setDirection(index);
                    info.setHitActual(false);

                }
                wait(1);

            }

        } else { // If first guess
            valid = false;
            while (!valid) { // Keeps looping until a valid guess is reached
                index = rand.nextInt(0, grid.getOpenSquares().size()); // Generates random index
                guess = grid.getOpenSquares().get(index);
                grid.removeOpenSquare(index); // gets random cell 
                int smallestShip = 6;
                for (ship currShip : ships) { // Finds smallest ship size
                    if (currShip.getSize() < smallestShip) {
                        smallestShip = currShip.getSize();
                    }
                }
                int check = guess;
                int maxSizeHor = 1; // Counts the starting square
                while (!contains(grid.getguesses(), check + 1) && !contains(grid.getSunken(), check + 1) // Increments ship size for each available square to the right
                        && (check % 10) + 1 < 10) {
                    maxSizeHor++;
                    check++;
                }
                check = guess;
                while (!contains(grid.getguesses(), check - 1) && !contains(grid.getSunken(), check - 1) // Increments ship size for each available square to the left
                        && (check % 10) - 1 >= 0) {
                    maxSizeHor++;
                    check--;
                }
                check = guess;
                int maxSizeVer = 1;
                while (!contains(grid.getguesses(), check + 10) && !contains(grid.getSunken(), check + 10) // Increments ship size for each available square on the bottom
                        && (check) / 10 + 10 < 10) {
                    maxSizeVer++;
                    check += 10;
                }
                check = guess;
                while (!contains(grid.getguesses(), check - 10) && !contains(grid.getSunken(), check - 10) // Increments ship size for each available square on the top
                        && (check) / 10 - 10 >= 0) {
                    maxSizeVer++;
                    check -= 10;
                }
                if (maxSizeVer < smallestShip && maxSizeHor < smallestShip) { // If the ship does not fit, the guess is false
                    valid = false;
                } else { // If it fits
                    if ((contains(grid.getSunken(), guess + 1) && guess % 10 + 1 < 10)
                            || (contains(grid.getSunken(), guess - 1) && guess % 10 - 1 >= 0)
                            || contains(grid.getSunken(), guess + 10)
                            || contains(grid.getSunken(), guess - 10)) { // Checks to see if the guess is next to a sunken ship

                        valid = false;
                    } else { // If not
                        valid = true; // Exits loop
                        int num = guess % 10;
                        char letternum = (char) (guess / 10 + 65);
                        System.out.println("The computer guessed " + letternum + "" + num);
                        wait(1);
                        if (contains(grid.getShipPositions(), guess)) { // If it is a hit
                            info.setSecondLastHit(guess); // Updates information and indicates the position
                            info.setHit(true);
                            info.setHitActual(true);

                            grid.addSunken(guess);
                            System.out.println("The computer hit!");
                            boolean sunk = checkIfNewShipSunk(grid);
                            if (sunk) { // checks if the ship has sunken 
                                info.setNewSunkenShip();
                            }

                        } else { // if it is a miss
                            System.out.println("The computer missed!");
                            grid.addGuess(guess); // Updates info
                            info.setHitActual(false);
                        }
                        wait(1);
                    }
                }
            }
        }

        return info;

    }

    public boolean checkIfNewShipSunk(Grid currGrid) { // Checks if new ship sunk
        boolean sunk = false;
        for (int i = 0; i < ships.length; i++) { // Goes through each ship
            if (currGrid.getShips()[i].checkIfSunken(currGrid) && !currGrid.getShips()[i].isSank()) { // If the ship is sunken, but not already sunk, new ship has sunken
                currGrid.getShips()[i].sinkShip();
                sunk = true;
                System.out.println("The ship of size " + currGrid.getShips()[i].getSize() + " has sunken!");
                for (int position: currGrid.getShips()[i].getPositions()) { // Adds the positionsfor later use
                    currGrid.addFullySunkenPosition(position);
                }
            }
        }
        return sunk;
    }

    public void wait(int seconds) { // Method for waiting
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void removeOpenSquare(int guess, boolean yes) { // Method for removing open squares based on the cell and not index

        for (int i = 0; i < openSquares.size(); i++) {
            if(openSquares.get(i) == guess) {
                openSquares.remove(i);
            }
        }
    }

}
