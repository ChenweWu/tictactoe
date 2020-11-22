
import java.util.ArrayList; 

public class Staten  {
	int actionx=1;
	int actiony=1;
	State[][] boarder=new State[3][3];// the larger board is made up of 3x3 array of smaller boards
	int turn=0;
	
	public Staten() 
	{
		initializar();
	}
	public Staten Temp() {
		State[][] boardar = new State[3][3];
		
		for(int i =0;i<3;i++)
		{
			for(int j = 0; j < 3; j++)
			{
				boardar[i][j] = this.boarder[i][j].temp();
			}
		}
		
		int xx = this.actionx;
		int yy = this.actiony;
		int turnn = this.turn;
		
		Staten temp = new Staten(boardar,xx,yy,turnn);
		
		return temp;
	}
	
	
	public Staten(State[][] board,int x,int y, int t)
	{
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				this.boarder[i][j] = board[i][j];
			}
		}
		
		this.actionx = x;
		this.actiony = y;
		this.turn = t;
		
	}
	
	
	public void initializar() {

		State elements = new State();
		for(int i = 0; i< 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				this.boarder[i][j] = elements.temp();
			}
		}
	}
	
	
	//draw the Nine by Nine board
	public void drawNineBoard()
	
	{
		System.err.println();
		System.err.println();
		for(int a=0;a<3;a++)
		{
			for(int b =0;b<3;b++)
			{
				for(int d=0;d<3;d++)
				{
					System.err.print(" ");
					for(int c=0;c<3;c++)
						{
						if(boarder[d][a].board[c][b]==' ')
							System.err.print("[]");
						else
						{
							System.err.print(boarder[d][a].board[c][b]);
							System.err.print(" ");
						}
						}	
					System.err.print("  ");
 			}
 			System.err.println();
 			if(b!=2)
 			System.err.println(" ++++++   ++++++   ++++++  ");
 		}
 		System.err.println();
		}
	}
	
	public ArrayList<Staten> Possible() 
	{
		
		ArrayList<State> grandchildren = this.boarder[actionx][actiony].PossibleMoves(this.turn);
		
		ArrayList <Staten> children= new ArrayList<Staten>();
		
		for(int i = 0; i< grandchildren.size(); i++)
		{
			children.add(this.Temp());
			children.get(i).setABoard(actionx, actiony, grandchildren.get(i));
			children.get(i).actionx = grandchildren.get(i).actionrow;
			children.get(i).actiony = grandchildren.get(i).actioncol;
			children.get(i).turn++;
		}
		
		return children;
		
	}
	
	public void setABoard(int i, int j, State board ) 
	{
		this.boarder[i][j] = board.temp(); 
	}
	public int totalScore(char C)
	{
		int score = 0;
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j <3;j++)
			{
				score+= this.boarder[i][j].heuristic(C);
			}
		}
		return score;
	}
	
	public int stateScore()
	{	
		int x=this.totalScore('X');
		int o=this.totalScore('O');
		if(x > 499)
		{
			return 500;
		}
		if
		(o> 499)
		{
			return  500;
		}
		else
		{
			return x-o  ;
		}
	}
	public char checkWin() {
		int x=this.totalScore('X');
		int o=this.totalScore('O');
		if(x > 499)
		{
			return 'X';
		}
		else if ( o > 499)
		{
			return  'O';
		}
		else if (turn>80) {
			return 'D';
		}
		return 'N';
	}
	
	

}
