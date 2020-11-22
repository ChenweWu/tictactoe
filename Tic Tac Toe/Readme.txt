Chenwei Wu
cwu59
CSC242 PROJECT I
Collaborators: Yi Yao yyao20 Haosong Rao hrao
3X3:
State Class: representing 3*3 board.
the evalu method is to check whether some player has won the game with four for loops for vertical,horizontal and diagonal.
the checkWin method is to print out the result who wins.
the drawBoard method is to print out the current board to debug
Game Class: the class that contains the method that update the player of the information of the board, the method that prompts the human to input choice. 
The minimax and FindBest method to get the output are also included in this game class.
Test3X3 class: the class that contains the main method. The simple 3X3 tic-tac-toe game can be runned in that class

9X9
Staten Class: a new object that represents 9 3*3 boards
has 3*3 state matrix as a parameter.
temp()is the deepcopy method that allows the search of minimax performed on new duplicated board.
Possible() is the method that generates possible move represented by deepcopy staten
GameNine Class: the class that contains the method that prompt the player to make a move. And the heuristic function and alpha-beta minimax method
Test9X9 class: the class that runs the advancedTTT game.


To see the test results, run Test3X3 or Test 9X9(3X3 game and 9X9 game)