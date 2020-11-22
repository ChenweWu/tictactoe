/*CSC242 Project 1
 *Collaborated by Chenwei Wu, Yi Yao, Haosong Rao
 *September 9.21 2017
 * */
import java.util.ArrayList;
public class State {
	
	char[][] board={
			{ ' ',' ',' '},
			{' ',' ',' '},
			{' ',' ',' '}
			};
	int actionrow=0;
	int actioncol=0;
	char winner='N';//initializing the winner to 'None' 
	boolean HumanPlayerX = false;
	int turn = 0;

	//used for the deep copy
	public State(char[][] board, int num)
	{
		this.board = board;
		this.turn = num;
	}
	
	public boolean checkFull() {
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++){
				if(this.board[i][j]==' ') {
					return false;
				}
			}
		}
		return true;
	}
	
	public State()
	{
		char[][] Board = {
				{' ',' ',' '},
				{' ',' ',' '},
				{' ',' ',' '}
				};
		board = Board;
	}
	
	//check for each state who has won
	public char checkWin() {
		if(this.evalu('X'))
		{
			return 'X';
		}
		else if (this.evalu('O'))
		{
			return 'O';					
		}
		else if(this.turn > 8)//means the ttt game is already done
		{
			return 'D';
		}
		else
		{
			return 'N';
		}
	}
	
	//checking whether there is three 'X' or 'O' in a row,column or diagonal,
	// if it does, return true
	public boolean evalu(char Type)
	{
		boolean condition=false;
		//rows
		for(int i = 0; i < 3; i++)
		{
			int counter = 0; 
			for(int j = 0; j < 3 ; j++)
			{
				if(this.board[i][j] == Type)
				{
					counter++;
				}
			}
			if(counter == 3)
			{
				condition=true;
			}
		}
		//columns
		for(int j = 0 ; j < 3 ; j++)
		{
			int counter = 0;
			for(int i = 0; i < 3 ; i++)
			{
				if(this.board[i][j] == Type)
				{
					counter++;
				}
			}if(counter == 3)
			{
				condition=true;
			}
		}
		int counter = 0;
		//diagonal
		for(int i = 0; i < 3 ; i++ )
		{
			if(this.board[i][i] == Type)
			{
				counter++;
			}
			if(counter == 3)
			{
				condition=true;
			}
		}
		counter = 0;
		//diagonal
		for(int i = 0; i < 3 ; i++ )
		{
			if(this.board[i][2-i] == Type)
			{
				counter++;
			}
			if(counter == 3)
			{
				condition=true;
			}
		}
		return condition;
	}
	
	//making a deep copy of the state so that we don't have to undo the actions
	//referring from http://javatechniques.com/blog/faster-deep-copies-of-java-objects/

	protected State temp() {
		char[][] temp = new char[this.board.length][this.board.length];
		for(int i = 0; i < 3 ;i++)
		{
			temp[i] = this.board[i].clone();
		}
		int turn2 = this.turn;
		
	    State copy = new State(temp,turn2);	
	    copy.actioncol=this.actioncol;
	    copy.actionrow=this.actionrow;
		return copy;
	}
	
	//draws the board
	 public void drawBoard(){
			int row = 3;
			int col = 3;
			System.err.print("\n    ");
			for (int i = 0; i < col; i++)
				System.err.print(i + "   ");
			System.err.println();

			for (int i = 0; i < row; i++) 
			{
				System.err.print(i + "  ");
				for (int j = 0; j < col; j++) 
				{
					if (j !=0)
					{
						System.err.print("|");
					}
					System.err.print(" " + (this.board[i][j]) + " "); 
				}
				System.err.println();
				if (i != (row - 1)) 
				{
					System.err.print("   ");
					for (int j = 0; j <col; j++) 
					{
						if (j != 0)
						{
							System.err.print("++++++");
						}
					}
					System.err.println();
				}
			}
			System.err.println("\n");
		}
	
	//generating all possible children states of a certain state
	public ArrayList<State> PossibleMoves() 
	{
		ArrayList<State> AllMoves=  new ArrayList<State>();
		int k = 0;
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j< 3; j++)
			{
				if(this.board[i][j] == ' ')
				{
						AllMoves.add(this.temp());
						AllMoves.get(k).turn++;
						AllMoves.get(k).board[i][j] = (this.turn%2 == 0)? 'X':'O';
						AllMoves.get(k).actionrow =i;
						AllMoves.get(k).actioncol =j;
						k++;
				}
			}
		}
		return AllMoves;		
	}
	
	public ArrayList<State> PossibleMoves(int turns)
	{
		ArrayList<State> AllMoves=  new ArrayList<State>();
		int k = 0;
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j< 3; j++)
			{
				if(this.board[i][j] == ' ')
				{
						AllMoves.add(this.temp());
						AllMoves.get(k).turn++;
						AllMoves.get(k).board[i][j] = (turns%2 == 0)? 'X':'O';
						AllMoves.get(k).actionrow =i;
						AllMoves.get(k).actioncol =j;
						k++;
				}
			}
		}
		return AllMoves;		
	}
	public int heuristic(char Type) {
		int score=0;
		int c = 0; 
		int e=0;
		//rows
		for(int i = 0; i < 3; i++)
		{
			c = 0; 
			 e=0;
			for(int j = 0; j < 3 ; j++)
			{
				if(this.board[i][j] == Type)
				{
					c++;
				}
				else if(this.board[i][j] == Type) 
				{
					e++;
				}
				if(c == 1 && e==2)
				{score+=1;
				}
				else if(c==2 && e==1) {
					score+=10;
				}
				else if (c==3) {
					score+=500;
				}
			}
		}
		//columns
		for(int j = 0 ; j < 3 ; j++)
		{
			c = 0;
			e= 0;
			for(int i = 0; i < 3 ; i++)
			{
				if(this.board[i][j] == Type)
				{
					c++;
				}else if(this.board[i][j] == Type) 
				{
					e++;
				}
				if(c == 1 && e==2)
				{score+=1;
				}
				else if(c==2 && e==1) {
					score+=10;
				}
				else if (c==3) {
					score+=500;
				}
			}
			}
		//diagonal
		c=0;

		e=0;
		for(int i = 0; i < 3 ; i++ )
		{
			if(this.board[i][i] == Type)
			{
				c++;
			}
			if(c == 3)
			{
				score+=500;
			}
		}
		c = 0;
		e=0;
		//diagonal
		for(int i = 0; i < 3 ; i++ )
		{
			if(this.board[i][2-i] == Type)
			{
				c++;
			}
			if(c == 1 && e==2)
			{score+=1;
			}
			else if(c==2 && e==1) {
				score+=10;
			}
			else if (c==3) {
				score+=500;
			}
		}
		return score;
	}

	
}
