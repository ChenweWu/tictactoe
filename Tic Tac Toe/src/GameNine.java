import java.util.ArrayList; 
import java.util.Scanner;

public class GameNine {

	Scanner in = new Scanner(System.in);
	Staten staten;
	boolean Human;

	String winner;
	public GameNine() throws CloneNotSupportedException
	{
		staten = new Staten();
		
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
			cord[0]=j;
			cord[1]=i;
		return cord;
		}
		
	public void ReadHumanMoves(char C)
	{
		//prompts the user to input the value of rows and cols
		int cord = in.nextInt();
		int i=transformer(cord)[0];
		int j=transformer(cord)[1];
		//determining whether the position on the board is occupied or the value just doesn't make sense at all
		while( i<0 || j<0 || i > 2 || j > 2 || this.staten.boarder[staten.actionx][staten.actiony].board[i][j]!=' ' )
		{
				System.err.println("Already Occupied...");
				i = in.nextInt();
				j = in.nextInt();
			}
		
		if(this.staten.boarder[staten.actionx][staten.actiony].checkFull()) {
			System.err.println("the board is full,change to another");
			ReadFirsthumanMove(C);
			return;
			
		}
			this.staten.boarder[staten.actionx][staten.actiony].board[i][j] = C;
			this.staten.actionx=i;
			this.staten.actiony=j;
			this.staten.turn++;	
		}
	public void ReadFirsthumanMove(char C)
	{		
		System.err.println("Choose a board:");
		int cord=in.nextInt();
		int i=transformer(cord)[0];
		int j=transformer(cord)[1];		
		System.err.println("Choose a position:");
		cord = in.nextInt();
		int x=transformer(cord)[0];
		int y=transformer(cord)[1];
		while(i > 2 || j>2 || x> 2 ||y >2 || this.staten.boarder[i][j].board[x][y] !=' ' )
		{
			System.err.println("Please pick a legal move");
			System.err.println("Choose a board:");
			cord=in.nextInt();
			i=transformer(cord)[0];
			j=transformer(cord)[1];		
			System.err.println("Choose a position:");
			cord = in.nextInt();
			x=transformer(cord)[0];
			y=transformer(cord)[1];
		}
		this.staten.boarder[i][j].board[x][y] = C;
		staten.actionx = x;
		staten.actiony = y;

		this.staten.turn++;
	}
	public void start() 
	{
		System.err.println("----------TTT Ultimate Version-----------");
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
			System.err.println("Your playing as O");
			Human = false;
		}
		else
		{
			start();
		}
		if(this.Human == true)
		{
			while(!Over())
			{ System.err.println(this.staten.turn+" "+Human);
				if(this.staten.turn%2 == 0)
				{
					if(this.staten.turn == 0)
					{
						ReadFirsthumanMove('X');
					}
					else
					{
						ReadHumanMoves('X');
					}
				}
				else
				{
					this.staten = FindBestStaten(this.staten, 7, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
				}

				this.staten.drawNineBoard();
			}
			if(!winner.equals("D")) {
				System.err.println("Player: "+winner+" Winner Winner,Chicken Dinner!");}
				else {
					System.err.println("This is a draw!");
				}
		}
		else
		{
			while(!Over())
			{
				if(this.staten.turn%2 == 0)
				{
					
					this.staten = FindBestStaten(this.staten, 7, Integer.MIN_VALUE, Integer.MAX_VALUE, true);	
					
				}
				else
				{
					ReadHumanMoves('O');
				}
				this.staten.drawNineBoard();
			}

			if(!winner.equals("D")) {
				System.err.println("Player: "+winner+" Winner Winner,Chicken Dinner!");}
				else {
					System.err.println("This is a draw!");
				}
		}
	
	}

	public boolean Over()
	{
		//using switch statement to check whether and which player has won the game or
		//or its just a draw
		switch(this.staten.checkWin()) {
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
	
	//I read the wikipedia before writing this method
	//https://zh.wikipedia.org/wiki/Alpha-beta%E5%89%AA%E6%9E%9D#.E4.BC.AA.E4.BB.A3.E7.A0.81
	public int alphabeta(Staten board ,int depth , int alpha, int beta, boolean maximizingplayer )  
	{
		ArrayList<Staten> allmoves = board.Possible();
		//if depth=0 or it reaches the terminal node
		if(depth <= 0 || board.checkWin()!='N')
		{
			if(board.checkWin()=='X') {
			return board.stateScore()-depth;}
			else if(board.checkWin()=='O') {
				return -board.stateScore()+depth;
			}
			else {
				return board.stateScore();
			}
		}		
		if(maximizingplayer)
			{
				for(Staten s  : allmoves)
				{
					int score = alphabeta(s,depth - 1, alpha , beta, false);
					alpha = Math.max(score, alpha);		
					if(alpha >= beta)
					{
						break;
					}
				}
				return alpha;
			}
			else
			{
				for(Staten s  : allmoves)
				{
					int score = alphabeta(s,depth - 1, alpha , beta, true);
					beta = Math.min(score, beta);		
					if(alpha >= beta)
					{
						break;
					}
				}
				return beta;
			}
		
	}
	
	public Staten FindBestStaten(Staten current , int depth ,int alpha, int beta , boolean is) 
	{	
		int k=tr(current.actionx,current.actiony);
		ArrayList<Staten> children = current.Possible();
		Staten bestMove = children.get(0).Temp();
		int bestVal;
		if(is)
		{
			for(Staten s: children)
			{
				bestVal =  alphabeta(bestMove,depth,alpha,beta,false);
				Staten choice = s.Temp();
				
				int tempmax = alphabeta(choice,depth,alpha,beta,false);
				if(tempmax > bestVal)
				{
					bestMove = choice;
					bestVal = tempmax;
				}
			}
		}
		else
		{
			for(Staten s: children)
			{
				bestVal =  alphabeta(bestMove,depth,alpha,beta,true);
				Staten choice= s.Temp();
				int tempmax = alphabeta(choice,depth,alpha,beta,true);
				if(tempmax < bestVal)
				{
					bestMove = choice;
					bestVal = tempmax;
				}
			}
		}
		System.out.println(k+" "+tr(bestMove.actionx,bestMove.actiony));
		return bestMove;
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
}
