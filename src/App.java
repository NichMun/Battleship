import java.util.*;

public class App {
    static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        
        displayRules();

        Grid newgrid = new Grid(10,10); // Creates player's grid
        newgrid.setUpGrid(); // Puts ships on grid
        Grid enemygrid = new Grid(10,10); // Creates computer's grid
        enemygrid.setUpGridComputer(); // Puts ships on grid
        gridGuess info = new gridGuess(); // Creates an object for tracking computer guess data
        boolean end = false;
        int winner = 0;
        while (!end) { // While game still continues
            boolean hit = true;
            
            while (hit) { // If the player hits, it is their turn again
                enemygrid.printComputerGrid(); // Prints both grids
                newgrid.printGrid();

                hit = newgrid.guess(enemygrid); // Checks if player hit
                if (enemygrid.checkIfLost()) { // Checks if computer lost; ends game
                    hit = false;
                    end = true;
                    winner = 1;
                }
            }
            if (!end) { // If game ended, don't let the computer guess
                hit = true;
            }
        
            while (hit) { // If the computer hits, it keeps guessing
                enemygrid.printComputerGrid(); // Prints both grids
                newgrid.printGrid();
                hit = enemygrid.guessComputer(newgrid, info).getHitActual(); // Checks to see if computer hits
                if (newgrid.checkIfLost()) { // If computer wins, ends game
                    end = true;
                    winner = 2;
                    hit = false;
                }
            }

            
        }
        if (winner == 1) { 
            System.out.println("Congratulations! You won!");
        }

        if (winner == 2) {
            System.out.println("Sorry, you lost. ");
        }

        
    }

    public static void displayRules() {
        System.out.println("Welcome to battleship. Here are the rules. (Type anything to continue)");
        System.out.println();
        in.nextLine();
        System.out.println("1. Start the game by placing ships. To place a ship, give a coordinate with a letter(A-J), \n followed by a number (0-9). Examples include, a0, d7, e9, h2, etc. The coordinate is the origin. \n You can then rotate the ship about this origin. The ships will be in sizes 2-6, in that order.");
        in.nextLine();
        System.out.println("2. Ships cannot be placed next to each other. This means horizontal and vertical (not diagonal) adjacency is not allowed. \n For obvious reasons, placing ships on top of each other is not allowed either.");
        in.nextLine();
        System.out.println("3. You and your opponent will take turns guessing where each others ships are. You can't guess where you already have.");
        in.nextLine();
        System.out.println("4. If you land a hit, it will be indicated with a '!'. Landing a hit means you get to go again");
        in.nextLine();
        System.out.println("5. You and your opponent keep playing either one of you ships are all fully sunken.");
        in.nextLine();
        System.out.println();
        System.out.println("'x' = miss");
        System.out.println("'!' = hit");
        System.out.println("'S' = sunken");
        System.out.println();
        boolean ready = false;
        while (!ready) {
            System.out.println("Type 'ready' when you are ready to begin.");
            String response = in.nextLine();
            if (response.equalsIgnoreCase("ready")) {
                ready = true;
            }
            System.out.println();
        }
    }



}


