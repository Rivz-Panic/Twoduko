import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.princeton.cs.introcs.StdDraw;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JFrame 
{

	private JPanel contentPane;
	public static int h;//Height
	public static int w;//width
	public static int ur;//userRow 
	public static int uc;//userColoumn   
	public static int num;//number entered by user
	public static int mode;//Mode of game
	public static int arrayGrid[][];//Array of numbers for grid
	public static int player = 1;//Distinguishes between players
	
	public static void mode()//Prompts the user to select a game mode
	{
		mode =	Integer.parseInt(JOptionPane.showInputDialog(null,"Enter game mode"));
		 if(mode == 1)
		 {
			 playerTurn();//players move
		 }else
		 if(mode == 0)
		 {
			 player = 0;
			 JOptionPane.showMessageDialog(null, "Computers Move ");			 
			 computerTurn();//computers move
		 }else
		 {
			 while(!(mode == 0 || mode == 1))//prompts user to enter correct mode number
			 {
				 mode = Integer.parseInt(JOptionPane.showInputDialog(null,"Invalid entry, enter game mode"));				 
			 }
		 }
	}
	
	public static void playerTurn()
	{
		JOptionPane.showMessageDialog(null, "Player "+ player + " enter a value");
	}
	
	public static void computerTurn() //Generates a move for the computer
	{
		int min = 1;   // minimum value that can be entered into a grid
		int max = w*h; // maximum value that can be entered into a grid
		ur =(int)(Math.random() * max); //random row number
		uc =(int)(Math.random() * max); //random column  number
		num = min + (int)(Math.random() * (max));// random number
		inputGrid();	   
	}
	
	public static boolean moveValid()//checks if move is valid
	{
		boolean val = true;
			if(arrayGrid[ur][uc] == 0 && !(num < 1 || num > (w*h)))//checks if the space in the array is available and within the boundaries
			{				
				for (int i = 0; i < h*h; i++)//Checks if the move is valid in the row
				{
					if (arrayGrid[ur][i] == num) 
					{
						val = false;
					}
				}
					for (int j = 0; j < w*w; j++) //Checks if the move is valid in the column
					{
						if (num == arrayGrid[j][uc]) 
						{
							val = false;
						}
					}
					for(int i = (ur/h) * h; i < ((ur/h) +1)  * h; i++)//Checks if the move is valid in the subgrid
					{ 
						for(int j =  (uc/h) * w; j < ((uc/w)+ 1) * w; j++)
						{
							if(num == arrayGrid[i][j])
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
		return val;
	}
	
	
	public static void inputGrid()//Assigns the numbers to the array of numbers and displays the grid with the updated array.
	{		
		if((ur < h*h && ur >= 0) && (uc < w*w && uc >= 0))//checks if rows and columns are valid
		{			
			if(moveValid())
			{
				arrayGrid[ur][uc]= num;
				StdDraw.text(uc + 0.5, ur + 0.5, num+"");
				if (result()) //checks if game is over
				{
					if (player == 0)//Checks who wins
					{
						JOptionPane.showMessageDialog(null, "COMPUTER HAS WON THE GAME!!!");
					} else 
					{
						JOptionPane.showMessageDialog(null, "PLAYER " + player + " HAS WON THE GAME!!!");
					}					
				} else 
				{
					if(mode == 1)//Determines whose turn it is
					 {
						if(player == 1)
						{
							player = 2;
						}else
						{
							player = 1;
						}
					    playerTurn();
					 }else
					 {			 
						 if(player == 1)
							{
								player = 0;
								JOptionPane.showMessageDialog(null, "Computers Move ");
								computerTurn();
							}else
							{
								player = 1;
								playerTurn();
							}	
					 } 

				}
			}else
			{
				if(mode == 1)
				 {
					JOptionPane.showMessageDialog(null, "Invalid move");					
				    playerTurn();
				 }else
				 {			 
					 if(player == 1)
					{
						JOptionPane.showMessageDialog(null, "Invalid move");					
						playerTurn();
					}else
					{
						computerTurn();
					}	
				 } 
			}
		}else
		{
			JOptionPane.showMessageDialog(null, "Invalid move");
			playerTurn();
		}	
	}

	
	
	public static void drawGrid()
	{
		StdDraw.clear();
		StdDraw.setXscale(-1, (w*w) + 1);
		StdDraw.setYscale((h*h) + 1, -1);
		for (int i = 0; i <= w*w; i++)
		{ // draws columns
			if (i%w == 0) 
			{
				StdDraw.setPenRadius(0.005);
			} else 
			{
				StdDraw.setPenRadius(0.002);
			}
			StdDraw.line(i, 0, i, h*h);

		}
		
		for (int j = 0; j <=w*w; j++) 
		{ // draws rows
			if (j % h == 0) 
			{
				StdDraw.setPenRadius(0.005);
			} else 
			{
				StdDraw.setPenRadius(0.002);
			}
			StdDraw.line(0, j, w*w, j);
			}		
		}
	
	public static boolean result()//Checks if game is over
	{
		boolean res = true;
		for (int i = 1; i < (w*h)+1; i++) 
		{
			num = i;
			for (int j = 0; j < h*h; j++) 
			{
				ur = j;
				for (int k = 0; k < w*w; k++)
				{
					uc = k;
					if (moveValid()) 
					{
						res = false;						
					}					
				}				
			}			
		}
		return res;
	}
	
	
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try
				{
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 770, 574);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("TWODUKO");
		lblNewLabel.setBounds(276, 25, 214, 37);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		panel.add(lblNewLabel);
		
		JButton btnStartGame = new JButton("START GAME");
		btnStartGame.setBounds(129, 78, 132, 37);
		panel.add(btnStartGame);
		
		JButton btnQuitGame = new JButton("QUIT GAME");
		btnQuitGame.setBounds(461, 78, 132, 37);
		panel.add(btnQuitGame);
		
		JLabel lblGridSizes = new JLabel("GRID SIZES");
		lblGridSizes.setBounds(143, 139, 118, 24);
		panel.add(lblGridSizes);
		
		JButton btn2X2 = new JButton("2 X 2");
		btn2X2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn2X2.setBounds(25, 198, 95, 61);
		panel.add(btn2X2);
		
		JButton btn3X3 = new JButton("3 X 3");
		btn3X3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn3X3.setBounds(132, 198, 96, 61);
		panel.add(btn3X3);
		
		JButton btn4X4 = new JButton("4 X 4");
		btn4X4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn4X4.setBounds(240, 198, 95, 61);
		panel.add(btn4X4);
		
		JButton btn2X3 = new JButton("2 X 3");
		btn2X3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn2X3.setBounds(25, 272, 95, 61);
		panel.add(btn2X3);
		
		JButton btn2X4 = new JButton("2 X 4");
		btn2X4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn2X4.setBounds(132, 272, 96, 61);
		panel.add(btn2X4);
		
		JButton btn2X5 = new JButton("2 X 5");
		btn2X5.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn2X5.setBounds(240, 272, 95, 61);
		panel.add(btn2X5);
		
		JButton btn3X4 = new JButton("3 X 4");
		btn3X4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn3X4.setBounds(132, 353, 96, 61);
		panel.add(btn3X4);
		
		JLabel lblValues = new JLabel("VALUES");
		lblValues.setBounds(495, 139, 58, 24);
		panel.add(lblValues);
		
		JButton btn1 = new JButton("1");
		btn1.setBounds(412, 187, 60, 25);
		panel.add(btn1);
		
		JButton btn2 = new JButton("2");
		btn2.setBounds(495, 187, 60, 25);
		panel.add(btn2);
		
		JButton btn3 = new JButton("3");
		btn3.setBounds(581, 187, 60, 25);
		panel.add(btn3);
		
		JButton btn4 = new JButton("4");
		btn4.setBounds(412, 234, 60, 25);
		panel.add(btn4);
		
		JButton btn5 = new JButton("5");
		btn5.setBounds(495, 234, 60, 25);
		panel.add(btn5);
		
		JButton btn6 = new JButton("6");
		btn6.setBounds(581, 233, 60, 25);
		panel.add(btn6);
		
		JButton btn7 = new JButton("7");
		btn7.setBounds(412, 279, 60, 25);
		panel.add(btn7);
		
		JButton btn8 = new JButton("8");
		btn8.setBounds(495, 279, 60, 25);
		panel.add(btn8);
		
		JButton btn9 = new JButton("9");
		btn9.setBounds(581, 278, 60, 25);
		panel.add(btn9);
		
		JButton btn10 = new JButton("10");
		btn10.setBounds(412, 325, 60, 25);
		panel.add(btn10);
		
		JButton btn11 = new JButton("11");
		btn11.setBounds(495, 325, 60, 25);
		panel.add(btn11);
		
		JButton btn12 = new JButton("12");
		btn12.setBounds(581, 324, 60, 25);
		panel.add(btn12);
		
		JButton btn13 = new JButton("13");
		btn13.setBounds(412, 373, 60, 25);
		panel.add(btn13);
		
		JButton btn14 = new JButton("14");
		btn14.setBounds(495, 374, 60, 25);
		panel.add(btn14);
		
		JButton btn15 = new JButton("15");
		btn15.setBounds(581, 373, 60, 25);
		panel.add(btn15);
		
		JButton btn16 = new JButton("16");
		btn16.setBounds(495, 423, 60, 25);
		panel.add(btn16);
		
		JButton btnSurrender = new JButton("SURRENDER");
		btnSurrender.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
			}
		});
		btnSurrender.setBounds(461, 485, 132, 37);
		panel.add(btnSurrender);
		
		btnStartGame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				lblGridSizes.setVisible(true);
				btn2X2.setVisible(true);
				btn3X3.setVisible(true);
				btn4X4.setVisible(true);
				btn2X3.setVisible(true);
				btn2X4.setVisible(true);
				btn2X5.setVisible(true);
				btn3X4.setVisible(true);
			}
		});

		btnQuitGame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		});
		
		btn2X2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				lblGridSizes.setVisible(false);
				btn2X2.setVisible(false);
				btn3X3.setVisible(false);
				btn4X4.setVisible(false);
				btn2X3.setVisible(false);
				btn2X4.setVisible(false);
				btn2X5.setVisible(false);
				btn3X4.setVisible(false);
			    lblValues.setVisible(true);
				btn1.setVisible(true);
				btn2.setVisible(true);
				btn3.setVisible(true);
				btn4.setVisible(true);
				w = 2;
				h = 2;
				arrayGrid = new int [h*h][w*w];
				drawGrid();
				mode();
			}
		});		

		btn3X3.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				lblGridSizes.setVisible(false);
				btn2X2.setVisible(false);
				btn3X3.setVisible(false);
				btn4X4.setVisible(false);
				btn2X3.setVisible(false);
				btn2X4.setVisible(false);
				btn2X5.setVisible(false);
				btn3X4.setVisible(false);
				lblValues.setVisible(true);
				btn1.setVisible(true);
				btn2.setVisible(true);
				btn3.setVisible(true);
				btn4.setVisible(true);
				btn5.setVisible(true);
				btn6.setVisible(true);
				btn7.setVisible(true);
				btn8.setVisible(true);
				btn9.setVisible(true);
				w = 3;
				h = 3;
				arrayGrid = new int [h*h][w*w];
				drawGrid();
				mode();
			}
		});		

		btn4X4.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				lblGridSizes.setVisible(false);
				btn2X2.setVisible(false);
				btn3X3.setVisible(false);
				btn4X4.setVisible(false);
				btn2X3.setVisible(false);
				btn2X4.setVisible(false);
				btn2X5.setVisible(false);
				btn3X4.setVisible(false);
				lblValues.setVisible(true);
				btn1.setVisible(true);
				btn2.setVisible(true);
				btn3.setVisible(true);
				btn4.setVisible(true);
				btn5.setVisible(true);
				btn6.setVisible(true);
				btn7.setVisible(true);
				btn8.setVisible(true);
				btn9.setVisible(true);
				btn10.setVisible(true);
				btn11.setVisible(true);
				btn12.setVisible(true);
				btn13.setVisible(true);
				btn14.setVisible(true);
				btn15.setVisible(true);
				btn16.setVisible(true);
				w = 4;
				h = 4;
				arrayGrid = new int [h*h][w*w];
				drawGrid();
				mode();
			}
		});

		btn2X3.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				lblGridSizes.setVisible(false);
				btn2X2.setVisible(false);
				btn3X3.setVisible(false);
				btn4X4.setVisible(false);
				btn2X3.setVisible(false);
				btn2X4.setVisible(false);
				btn2X5.setVisible(false);
				btn3X4.setVisible(false);
				lblValues.setVisible(true);
				btn1.setVisible(true);
				btn2.setVisible(true);
				btn3.setVisible(true);
				btn4.setVisible(true);
				btn5.setVisible(true);
				btn6.setVisible(true);
				w = 3;
				h = 2;
				arrayGrid = new int [h*h][w*w];
				drawGrid();
				mode();
			}
		});

		btn2X4.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				lblGridSizes.setVisible(false);
				btn2X2.setVisible(false);
				btn3X3.setVisible(false);
				btn4X4.setVisible(false);
				btn2X3.setVisible(false);
				btn2X4.setVisible(false);
				btn2X5.setVisible(false);
				btn3X4.setVisible(false);
				lblValues.setVisible(true);
				btn1.setVisible(true);
				btn2.setVisible(true);
				btn3.setVisible(true);
				btn4.setVisible(true);
				btn5.setVisible(true);
				btn6.setVisible(true);
				btn7.setVisible(true);
				btn8.setVisible(true);
				w = 4;
				h = 2;
				arrayGrid = new int [h*h][w*w];
				drawGrid();
				mode();
			}
		});

		btn2X5.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				lblGridSizes.setVisible(false);
				btn2X2.setVisible(false);
				btn3X3.setVisible(false);
				btn4X4.setVisible(false);
				btn2X3.setVisible(false);
				btn2X4.setVisible(false);
				btn2X5.setVisible(false);
				btn3X4.setVisible(false);
				lblValues.setVisible(true);
				btn1.setVisible(true);
				btn2.setVisible(true);
				btn3.setVisible(true);
				btn4.setVisible(true);
				btn5.setVisible(true);
				btn6.setVisible(true);
				btn7.setVisible(true);
				btn8.setVisible(true);
				btn9.setVisible(true);
				btn10.setVisible(true);
				w = 5;
				h = 2;
				arrayGrid = new int [h*h][w*w];
				drawGrid();
				mode();
			}
		});

		btn3X4.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				lblGridSizes.setVisible(false);
				btn2X2.setVisible(false);
				btn3X3.setVisible(false);
				btn4X4.setVisible(false);
				btn2X3.setVisible(false);
				btn2X4.setVisible(false);
				btn2X5.setVisible(false);
				btn3X4.setVisible(false);
				lblValues.setVisible(true);
				btn1.setVisible(true);
				btn2.setVisible(true);
				btn3.setVisible(true);
				btn4.setVisible(true);
				btn5.setVisible(true);
				btn6.setVisible(true);
				btn7.setVisible(true);
				btn8.setVisible(true);
				btn9.setVisible(true);
				btn10.setVisible(true);
				btn11.setVisible(true);
				btn12.setVisible(true);
				w = 4;
				h = 3;
				arrayGrid = new int [h*h][w*w];
				drawGrid();
				mode();
			}
		});
		
		btn1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				num = 1;				
				ur = Integer.parseInt(JOptionPane.showInputDialog("Enter row number")) - 1;
				uc = Integer.parseInt(JOptionPane.showInputDialog("Enter column number")) - 1;
				inputGrid();				
			}
		});

		btn2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				num = 2;				
				ur = Integer.parseInt(JOptionPane.showInputDialog("Enter row number")) - 1;
				uc = Integer.parseInt(JOptionPane.showInputDialog("Enter column number")) - 1;
				inputGrid();
			}
		});

		btn3.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				num = 3;				
				ur = Integer.parseInt(JOptionPane.showInputDialog("Enter row number")) - 1;
				uc = Integer.parseInt(JOptionPane.showInputDialog("Enter column number")) - 1;
				inputGrid();
			}
		});

		btn4.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				num = 4;				
				ur = Integer.parseInt(JOptionPane.showInputDialog("Enter row number")) - 1;
				uc = Integer.parseInt(JOptionPane.showInputDialog("Enter column number")) - 1;
				inputGrid();
			}
		});

		btn5.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				num = 5;				
				ur = Integer.parseInt(JOptionPane.showInputDialog("Enter row number")) - 1;
				uc = Integer.parseInt(JOptionPane.showInputDialog("Enter column number")) - 1;
				inputGrid();
			}
		});

		btn6.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				num = 6;				
				ur = Integer.parseInt(JOptionPane.showInputDialog("Enter row number")) - 1;
				uc = Integer.parseInt(JOptionPane.showInputDialog("Enter column number")) - 1;
				inputGrid();
			}
		});

		btn7.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				num = 7;				
				ur = Integer.parseInt(JOptionPane.showInputDialog("Enter row number")) - 1;
				uc = Integer.parseInt(JOptionPane.showInputDialog("Enter column number")) - 1;
				inputGrid();
			}
		});

		btn8.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				num = 8;				
				ur = Integer.parseInt(JOptionPane.showInputDialog("Enter row number")) - 1;
				uc = Integer.parseInt(JOptionPane.showInputDialog("Enter column number")) - 1;
				inputGrid();
			}
		});

		btn9.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				num = 9;				
				ur = Integer.parseInt(JOptionPane.showInputDialog("Enter row number")) - 1;
				uc = Integer.parseInt(JOptionPane.showInputDialog("Enter column number")) - 1;
				inputGrid();
			}
		});

		btn10.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				num = 10;				
				ur = Integer.parseInt(JOptionPane.showInputDialog("Enter row number")) - 1;
				uc = Integer.parseInt(JOptionPane.showInputDialog("Enter column number")) - 1;
				inputGrid();
			}
		});

		btn11.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				num = 11;				
				ur = Integer.parseInt(JOptionPane.showInputDialog("Enter row number")) - 1;
				uc = Integer.parseInt(JOptionPane.showInputDialog("Enter column number")) - 1;
				inputGrid();
			}
		});

		btn12.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				num = 12;				
				ur = Integer.parseInt(JOptionPane.showInputDialog("Enter row number")) - 1;
				uc = Integer.parseInt(JOptionPane.showInputDialog("Enter column number")) - 1;
				inputGrid();
			}
		});

		btn13.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				num = 13;				
				ur = Integer.parseInt(JOptionPane.showInputDialog("Enter row number")) - 1;
				uc = Integer.parseInt(JOptionPane.showInputDialog("Enter column number")) - 1;
				inputGrid();
			}
		});

		btn14.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				num = 14;				
				ur = Integer.parseInt(JOptionPane.showInputDialog("Enter row number")) - 1;
				uc = Integer.parseInt(JOptionPane.showInputDialog("Enter column number")) - 1;
				inputGrid();
			}
		});

		btn15.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				num = 15;				
				ur = Integer.parseInt(JOptionPane.showInputDialog("Enter row number")) - 1;
				uc = Integer.parseInt(JOptionPane.showInputDialog("Enter column number")) - 1;
				inputGrid();
			}
		});

		btn16.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				num = 16;				
				ur = Integer.parseInt(JOptionPane.showInputDialog("Enter row number")) - 1;
				uc = Integer.parseInt(JOptionPane.showInputDialog("Enter column number")) - 1;
				inputGrid();
			}
		});
		lblGridSizes.setVisible(false);
		btn2X2.setVisible(false);
		btn3X3.setVisible(false);
		btn4X4.setVisible(false);
		btn2X3.setVisible(false);
		btn2X4.setVisible(false);
		btn2X5.setVisible(false);
		btn3X4.setVisible(false);
		lblValues.setVisible(false);
		btn1.setVisible(false);
		btn2.setVisible(false);
		btn3.setVisible(false);
		btn4.setVisible(false);
		btn5.setVisible(false);
		btn6.setVisible(false);
		btn7.setVisible(false);
		btn8.setVisible(false);
		btn9.setVisible(false);
		btn10.setVisible(false);
		btn11.setVisible(false);
		btn12.setVisible(false);
		btn13.setVisible(false);
		btn14.setVisible(false);
		btn15.setVisible(false);
		btn16.setVisible(false);
		btnSurrender.setVisible(false);
	}
}
