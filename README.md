# Battleship Game

A Java implementation of the classic Battleship game.

### Requirements
- Java JDK 11 or higher installed

## How to run


1. Open a terminal or command prompt
2. Clone repository: 
git clone https://github.com/NichMun/Battleship.git

From now on, you can either open this in an IDE and run it that way or:

3. Enter project folder directory:
cd Battleship

4. Compile game:
javac src/*.java

5. Run the game:
java src.Battleship


## How to Play

1. Start the game by placing ships. To place a ship, give a coordinate with a letter(A-J), \n followed by a number (0-9). Examples include, a0, d7, e9, h2, etc. The coordinate is the origin. \n You can then rotate the ship about this origin. The ships will be in sizes 2-6, in that order.


2. Ships cannot be placed next to each other. This means horizontal and vertical (not diagonal) adjacency is not allowed. \n For obvious reasons, placing ships on top of each other is not allowed either.


3. You and your opponent will take turns guessing where each others ships are. You can't guess where you already have.


4. If you land a hit, it will be indicated with a '!'. Landing a hit means you get to go again.


5. You and your opponent keep playing either one of you ships are all fully sunken.

