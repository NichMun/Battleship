# Battleship Game

A Java implementation of the classic Battleship game.

## How to Play

1. Start the game by placing ships. To place a ship, give a coordinate with a letter(A-J), \n followed by a number (0-9). Examples include, a0, d7, e9, h2, etc. The coordinate is the origin. \n You can then rotate the ship about this origin. The ships will be in sizes 2-6, in that order.


2. Ships cannot be placed next to each other. This means horizontal and vertical (not diagonal) adjacency is not allowed. \n For obvious reasons, placing ships on top of each other is not allowed either.


3. You and your opponent will take turns guessing where each others ships are. You can't guess where you already have.


4. If you land a hit, it will be indicated with a '!'. Landing a hit means you get to go again.


5. You and your opponent keep playing either one of you ships are all fully sunken.

