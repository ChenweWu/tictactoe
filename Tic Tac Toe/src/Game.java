/*CSC242 Project 1
 *Collaborated by Chenwei Wu, Yi Yao, Haosong Rao
 *September 9.21 2017
 * */

import java.util.ArrayList;  
import java.util.Scanner;

public class Game {
	
	Scanner in = new Scanner(System.in);
	State state = new State();
	int turn;	//a numerical value that records the current turn (also functions as depth)
	String winner=""; // char value to determine who wins and produce corresponding output
	boolean Human = false;
	
	public Game()
	{
		this.turn=0;
		initializeState();//initialize the tictactoe board to an empty one
	}
	
	public void readfromHuman(char type)
	{
	//prompts the user to input the value of rows and cols
		System.err.println("Choose a position:");
		int cord = in.nextInt();
		int i=transformer(cord)[0];
		int j=transformer(cord)[1];
	//determining whether the position on the board is occupied or the value just doesn't make sense at all
		while( i<0 || j<0 || i > 2 || j > 2 || this.state.board[i][j]!=' ' )
		{
			System.err.println("Already Occupied...");
			i = in.nextInt();
			j = in.nextInt();
		}
		this.state.board[i][j] = type;
		this.turn+=1;	
	}
	
	public void initializeState() {
		//initialize the tictactoe board to an empty one
		char[][] temp = {{' ',' ',' '},
				{' ',' ',' '},
				{' ', ' ',' '}};	
		this.state = new State(temp ,0);
	}
	
	public boolean Over()
	{
		//using switch statement to check whether and which player has won the game or
		//or its just a draw
		switch(this.state.checkWin()) {
		case 'X':
			winner="X";
			return true;
		case 'O':
			winner="O";
			return true;
		case 'D':
			winner="D";
			return true;
		case  'N':
			return false;
		default:
			return false;
		}
	}
	
	
	public void start()
	{
		System.err.println("----------TTT 3X3 Version-----------");
		System.err.println("Enter your choice as X or O.....");
		String choice = in.next();
		//determining the order of play
		if(choice.equals("X")||choice.equals("x"))
		{
			System.err.println("Human : X, AI : O");
			Human = true;
		}
		else if(choice.equals("O")||choice.equals("o"))
		{
			System.err.println("You r playing as O");
			Human = false;
		}
		else
		{
			start();
		}
		this.state.drawBoard();
		if(Human)
		{
			while(!Over())
			{
				if(this.state.turn%2 == 0)
				{
					readfromHuman('X'); 
					this.state.drawBoard();
					this.state.turn++;
				}
				else
				{
					this.state = Finder(this.state,!Human);
					this.state.drawBoard();	
				}	
			}if(!winner.equals("D")) {
				System.err.println("Player: "+winner+" Winner Winner,Chicken Dinner!");}
				else {
					System.err.println("This is a draw!");
				}
		}
		else
		{
			while(!Over())
			{
				if(this.state.turn%2 == 0)
				{
					this.state = Finder(this.state , !Human); 
					this.state.drawBoard();
				}
				else
				{
					readfromHuman('O'); 
					this.state.drawBoard(); 
					this.state.turn++;
				}
			}
			if(!winner.equals("D")) {
			System.err.println("Player: "+winner+" Winner Winner,Chicken Dinner!");}
			else {
				System.err.println("This is a draw!");
			}
		}
	}
	
	//input transform code
	public int[] transformer(int j) {
		int[] cord=new int[2];
		int i=0;
		for(; j>3; j-=3) 
		{
		i++;
		}
		j--;
		cord[0]=i;
		cord[1]=j;
	return cord;
	}
	
	//minimax recursive function
	public int evaluation(State board,int depth , boolean maximizer) 
	{
		//taking the depth and final result of the boards into consideration and evaluates
		if(board.checkWin()=='O')
		{
			return -10+depth;
		}
		else if(board.checkWin()=='X')
		{
			return 10-depth;
		}
		else if(depth > 8||board.checkWin()=='D')
		{
			return 0;
		}
		//setting different initial values for best depending on maximizer or minimizer
		int best= (maximizer)? Integer.MIN_VALUE:Integer.MAX_VALUE;
		//generates the children of a certain node in the search tree
		ArrayList<State> allmoves = board.PossibleMoves();
		for(State i: allmoves)
		{
			//maximizer 
			if(maximizer) 
			{
			int test = evaluation(i , i.turn, false);
			best = Math.max(test,best);
			}
			//minimizer
			else 
			{
				int test =evaluation(i,i.turn, true);
				best = Math.min(test,best);
			}
		}
		return best;
	}

	public int tr(int a , int b) {
		int c=0;
		if(a==0&&b==0) {
			c=1;
		}if(a==0&&b==1) {
			c=2;
		}if(a==0&&b==2) {
			c=3;
		}if(a==1&&b==0) {
			c=4;
		}if(a==1&&b==1) {
			c=5;
		}if(a==1&&b==2) {
			c=6;
		}if(a==2&&b==0) {
			c=7;
		}if(a==2&&b==1) {
			c=8;
		}if(a==2&&b==2) {
			c=9;
		}
		return c;
		
	}
	//finding and returning best state using minimax evaluation method 
	public State Finder(State Board,boolean X)
	{
		ArrayList<State> allmoves = Board.PossibleMoves();
		State Best= allmoves.get(0).temp();
		int best;
		if(X)
		{
			//a whole check of all the possible states and return the best move
			for(State i: allmoves)
			{
				best =  evaluation(Best,Best.turn,false);
				State copy = i.temp();
				int cur = evaluation(copy,copy.turn,false);
				if(cur > best)
				{
					Best = copy;
					best = cur;
				}
			}
		}
		else
		{
			for(State i: allmoves)
			{
				best =  evaluation(Best,Best.turn,true);
				State copy = i.temp();
				int cur= evaluation(copy,copy.turn,true);
				if(cur < best)
				{
					Best = copy;
					best = cur;
				}
			}
		}

		System.out.println(tr(Best.actionrow,Best.actioncol));
		return Best;
	}
}
