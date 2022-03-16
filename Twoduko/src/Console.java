import edu.princeton.cs.introcs.StdIn;

public class Console 
{
	public static int w,h,m,player = 1;//width,height,mode,player
	public static int [][] arrNum;//Array of numbers
	public static int r,c;//row and columns for gird
	public static String [][] grid;//Array for grid
	
	
	public static void main(String[] args) 
	{
		w = Integer.parseInt(args[0]);
		h = Integer.parseInt(args[1]);
		m = Integer.parseInt(args[2]);
		r=(2*(h*h))+1;
		c = w*w;
		grid = new String[r][c];
		int row = h*h;
		int col = w*w;
		arrNum = new int[row][col];
		if (w==h && (w==2||w==3||w==4)) //Checks if measurements are valid
		{
			for (int i = 0; i < r; i++) //Creates the grid
			{
				for (int j = 0; j < c; j++) 
				{
					if (w==4 || h==4)//Checks if extra space is needed for the bigger grid
					{	
						if (i%2==0)
						{
							if ((i%(h*2)==0) && (i!=0&&i!=(r-1))) 
							{
								if ((j+1)%w==0) 
								{
									grid[i][j] = "+====+";
								} else 
								{
									grid[i][j] = "+====";
								}				
							} else 
							{
								if ((j+1)%w==0) 
								{
									grid[i][j] = "+----+";
								} else 
								{
									grid[i][j] = "+----";
								}							
							}
														
						} else
						{
							if ((j+1)%w==0) 
							{
								grid[i][j] = "|    |";
							} else 
							{
								grid[i][j] = "|    ";
							}					
						}
											
					} else 
					{
						if (i%2==0)
						{
							if ((i%(h*2)==0) && (i!=0&&i!=(r-1))) 
							{
								if ((j+1)%w==0) 
								{
									grid[i][j] = "+===+";
								} else 
								{
									grid[i][j] = "+===";
								}				
							} else 
							{
								if ((j+1)%w==0) 
								{
									grid[i][j] = "+---+";
								} else 
								{
									grid[i][j] = "+---";
								}							
							}
														
						} else
						{
							if ((j+1)%w==0) 
							{
								grid[i][j] = "|   |";
							} else 
							{
								grid[i][j] = "|   ";
							}					
						}
					}								
				}			
			}
			if (m==0) //Checks game mode
			{
				player = 0;
				computerTurn();//Computers Play
			} else  
			{

				for (int i = 0; i < r; i++)//displays grid
				{
					for (int j = 0; j < c; j++) 
					{
						System.out.print(grid[i][j]);				
					}		
					System.out.println();
				}
				System.out.println();
				playerTurn();//Players Turn
			}			
		} else 
		{
			System.out.println("Illegal grid size.  Please use 2x2, 3x3, or 4x4.");
		}
	}
	
	public static void displayGrid(int ur,int uc,int input)//Displays the grid with numbers
	{
		if (valid(ur,uc,input))//checks if move is valid
		{ 
			arrNum[ur][uc] = input;
			String s = input+"";
			if (w == 4 && s.length()==1) //Adds extra space for numbers with length bigger than 1
			{
				s+=" ";				
			}
			if ((uc+1)%w==0) 
			{
				grid[(ur*2)+1][uc] = "| "+s+" |";
			} else 
			{
				grid[(ur*2)+1][uc] = "| "+s+" ";
			}
			System.out.println();
			for (int i = 0; i < r; i++)
			{
				for (int j = 0; j < c; j++) 
				{
					System.out.print(grid[i][j]);				
				}		
				System.out.println();
			}
			System.out.println();
			if (result()) //Checks if game is over
			{
				if (player == 0)//Checks who wins the game
				{
					System.out.println("COMPUTER HAS WON THE GAME!!!");					
				} else
				{
					System.out.println("PLAYER " + player + " HAS WON THE GAME!!!");
				}				
			} else 
			{
				if (m == 0) //Changes the players turn after move is completed
				{
					if (player == 0) 
					{
						player = 1;
						playerTurn();
					} else
					{
						player = 0;
						computerTurn();
					}
				} else 
				{
					if (player == 2) 
					{
						player = 1;
					} else
					{
						player = 2;
					}	
					playerTurn();			
				}
			}
		}else
		{
			if (player==0) 
			{
				computerTurn();
			}else
			{
				System.out.println("Illegal move");
				System.out.println("GAME OVER!!!");
				System.exit(0);//Terminates game if game is over
			}
		}
	}
	
	public static boolean valid(int ur, int uc, int num)//Checks if move is valid
	{
		boolean val = true;
		if (!(ur<0||ur>=h*h) && !(uc<0||uc>=w*w))//Checks if the row and column are valid
		{
			if(arrNum[ur][uc]==0 && !(num < 1 || num > (w*h)))//checks if the space in the array is available and within the boundaries
			{				
				for (int i = 0; i < h * h; i++)//Checks if the move is valid in the row
				{
					if (arrNum[ur][i] == num) 
					{
						val = false;
					}
				}
					for (int j = 0; j < w * w; j++)//Checks if the move is valid in the row
					{
						if (num == arrNum[j][uc]) 
						{
							val = false;
						}
					}
					for(int i = (ur/h) * h; i < ((ur/h) +1)  * h; i++)//Checks if the move is valid in the column
					{ 
						for(int j =  (uc/ h) * w; j < ((uc/ w)+ 1) * w; j++)//Checks if the move is valid in the subgrid
						{
							if(num == arrNum[i][j])
							{
								val = false;
							} 
						}
					}
				}
				else 
				{
					val= false;
				}			
		} else 
		{
			val= false;
		}
			
		return val;
	}
	
	public static void playerTurn()//Asks the user to enter a move
	{
		System.out.println();
		System.out.println("Player " + player + " Enter your move:");
		System.out.print("> ");
		int ur = StdIn.readInt() - 1;
		int uc = StdIn.readInt() - 1;
		int in = StdIn.readInt();
		displayGrid(ur, uc, in);
	}
	
	public static void computerTurn() //Generates a move for the computer
	{
		int min = 1;// minimum value that can be entered into a grid
		int max = w*h;// maximum value that can be entered into a grid
		int row =(int)(Math.random() * max); //random row number
		int col =(int)(Math.random() * max);//random column  number
		int number = min + (int)(Math.random() * (max));// random number
		displayGrid(row, col, number);					
	}
	
	public static boolean result()//Checks if game is over
	{
		boolean res = true;
		for (int i = 1; i < (w*h)+1; i++)//Every possible value 
		{
			for (int j = 0; j < h*h; j++) 
			{
				for (int k = 0; k < w*w; k++)
				{
					if (valid(j, k, i)) 
					{
						res= false;						
					}					
				}				
			}			
		}
		return res;
	}
}
