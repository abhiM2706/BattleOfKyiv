//Abhinav Malviya
//BattleOfKyiv.java
//Game Project based on Ukraine and Russian war
//Week 5 submission

package com.stem.game;

import java.awt.*;  import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.swing.*; import javax.swing.event.*;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BattleOfKyiv {// starter class where parameters are passed
	Font theFont = new Font("TimesRoman",Font.BOLD,15);
	int currentInstructionCard = 1;
	public static String imagePath = "C:\\Users\\abhin\\OneDrive\\Desktop\\game\\";
	private Timer balltimer;
	JFrame frame;
	CardLayout cardLayout;
	GameContainerPanel pc;
	String playerName = null;
	public BattleOfKyiv(String imagePath)
	{
		if (imagePath != null && !"".equals(imagePath))
		{
			this.imagePath = imagePath;
		}
		frame = new JFrame();
		frame.setSize(1300, 850);
		pc = new GameContainerPanel();
		frame.setContentPane(pc);  
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
	public static void main(String[] args)// runs the main class and parameter (imagePath is location of images in my desktop
	{
		String imagePath = "";
		if (args.length > 1)
		{
			imagePath = args[0];
		}
		BattleOfKyiv run = new BattleOfKyiv(imagePath);
	}

	class GameContainerPanel extends JPanel implements ActionListener, KeyListener, Runnable // main class which is run by the constructor
	{
		private int ballX=0, ballY=600;
		private int oppBallX=1300, oppBallY = 300;
		private int ballSpeed=1;
		PlayAir playRussian = null;
		PlayGame playUkr = null;
		int whatIsPlaying = 0;

		int RUSSIAN_GAME = 1;
		int UKR_GAME = 2;

		JPanel mainScreenPanel, instructionsPanel,levelPanel;
		boolean boolInstructions, boolMainScreen=true, boolMonsters, boolLeaderboard, boolPlay;

		JLabel turretF1, turretF;

		boolean pos=true;
		boolean oppPos=true;

		JLabel jets=null;
		ImageIcon jetter=new ImageIcon(),jetter1=new ImageIcon();

		Image settingGame = new ImageIcon(imagePath + "background option.png").getImage();
		Image backgroundInst = new ImageIcon(imagePath + "backgroundInstructions.jpg").getImage();
		Image background = new ImageIcon(imagePath + "battleofkyiv.png").getImage();
		Image backgroundNotMain = new ImageIcon(imagePath + "first background.png").getImage();
		Image img = new ImageIcon(imagePath + "button.png").getImage();
		Image jet45Up = new ImageIcon(imagePath + "ukraine_jet.png").getImage();
		Image jet45Down = new ImageIcon(imagePath + "ukraine_jet45.png").getImage();
		Image jetRussiaUp = new ImageIcon(imagePath + "russian jet45Down.png").getImage();
		Image jetRussiaDown = new ImageIcon(imagePath + "russian jet45Up.png").getImage();


		JButton retry = new JButton();
		JLabel failed=new JLabel("");
		JLabel success=new JLabel("");
		boolean stopShooting=false;
		int shotDown=0;
		boolean tankShotLeft=false;
		boolean tankShotRight=false;
		JLabel bloodFailed=new JLabel("");
		Font theFont = new Font("TimesRoman",Font.BOLD,24);
		Date theDate;
		Thread runner;
		JLabel currLevel; 
		int counter = 100;
		JPanel board, stats;
		List<JLabel> drones = new ArrayList<JLabel>();
		List<JLabel> bullets = new ArrayList<JLabel>();
		List<Integer> speed = new ArrayList<Integer>();
		List<JLabel> wallsProtect = new ArrayList<JLabel>();
		List<JLabel> capital = new ArrayList<JLabel>();

		boolean canShoot=true;
		int howManyShoot=0;

		//Current Score

		int currScore=0;
		int nozzleAdded=0;

		List<Integer>dropBomb = new ArrayList<Integer>();
		List<Integer>dropBombLeft = new ArrayList<Integer>();

		List<Double> xTravel = new ArrayList<Double>();
		List<Double> yTravel = new ArrayList<Double>();

		int capitalHealth=5;

		int xLoc=100;
		int xLocLeft=1000;
		int yLoc=600;
		int cnt=0;
		int cnt1=0;
		JLabel currHealth, currHealthLeft,currHealthPlane;
		ImageIcon h1,h2,h3,h4;
		double currAngle=90.0;
		double currAngleLeft=90.0;

		boolean whichWayRight=false;
		boolean whichWayLeft =true;
		int speedRight=6;
		int speedLeft=-6;

		boolean whichWayRightJet=true;

		int speedRightJet=6;
		int xLocJet=800;
		int yLocJet =50;

		JLabel arrowNewleft=null,arrowNewRight=null;
		AudioInputStream clipNameAIS;
		Clip clipNameClip;

		AudioInputStream tankMove;
		Clip tank;

		//List<Integer> health = new ArrayList<Integer>();
		List<JLabel> planes = new ArrayList<JLabel>();
		List<Integer> leftRight = new ArrayList<Integer>();
		List<Integer> planeSpeed = new ArrayList<Integer>();
		List<Integer> randomPlaneY = new ArrayList<Integer>();
		Image nozzle = new ImageIcon(imagePath + "tankNozzle.png").getImage();

		JLabel s = new JLabel("");
		Image bull = new ImageIcon("").getImage();
		int tankHealth = 12;
		int tankHealthLeft=12;
		int planeHealth=3;
		int bombDamage;
		int currBallX;
		int levelGame;
		double levelMultiplier;
		JLabel tankLabel = null;
		JLabel tankLabelLeft =null;
		JPanel bul = null;
		int index=0;
		ImageIcon sd = new ImageIcon(imagePath + "explodePlane.gif");
		JLabel exp = new JLabel();
		JTextField name = new JTextField(1);
		public void paintComponent(Graphics g) //responsible for drawing backgrounds of start panels and images
		{
			super.paintComponent(g);
			if(boolMainScreen)
			{
				g.drawImage(background, 0, 0, 1300, 800, null);
			}
			name.requestFocusInWindow();
		}

		public boolean isHit(int currentBulletIndex) // this checks whether the bullet has a hit a bomb or plane
		{
			if(stopShooting)
				return false;
			boolean hit =false;

			JLabel currentBullet = bullets.get(currentBulletIndex);
			boolean ex=false;
			JLabel droneToCheck = jets;
			/*
			 * Check whether current bullet is between drone X coord and Y coord
			 */


			if(((currentBullet.getX()>=droneToCheck.getX()+10) && (currentBullet.getX()<=droneToCheck.getX()+170)) 
					|| ((currentBullet.getX()+35>=droneToCheck.getX()+10) && (currentBullet.getX()+35<=droneToCheck.getX()+170)))
			{
				if((currentBullet.getY()>=droneToCheck.getY())&&(currentBullet.getY()<=droneToCheck.getY()+40))
				{
					hit=true;
					if(planeHealth!=0)planeHealth--;
					//System.out.println(planeHealth);
					if(planeHealth==0)
					{
						ex=true;
						planeHealth = (int)(Math.random()*4+2);
						int which = (int)(Math.random()*4+1);
						if(which==1 && which==4)
						{
							xLocJet = (int)(Math.random()*50+1400);
							yLocJet = (int)(Math.random()*50+50);
							whichWayRightJet=true;
						}
						else {
							xLocJet = (int)(Math.random()*50-100);
							yLocJet = (int)(Math.random()*50+50);
							whichWayRightJet=false;
						}
						planeHealth = (int)(Math.random()*8+2);
					}

				}
			}

			if(ex)
			{
				sd.getImage().flush();
				exp.setIcon(sd);
				exp.setBounds(jets.getX()-50,jets.getY()-150,400,366);
				if(index==0)
				{

					bul.add(exp);
					exp.setVisible(true);
				}
				index++;


				if(index>4)
				{

					index=0;
					//ex=false;

					exp.setVisible(false);
					exp.setBounds(-100,-100,1,1);

				}
			}



			return hit;
		}


		public ImageIcon setbulletRotate(double x) //rotate the bullet by x degrees
		{
			BufferedImage bufferedImage = null;
			x=x-4;

			try {
				bufferedImage = ImageIO.read(new File(imagePath + "bullet.png"));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			int posX = s.getX();
			int posY = s.getY();

			double locationX = bufferedImage.getWidth()/2;
			double locationY = bufferedImage.getHeight()/2;

			double diff = Math.abs(bufferedImage.getWidth()-bufferedImage.getHeight());

			double rotationRequired = Math.toRadians(x);
			double unitX = Math.abs(Math.cos(rotationRequired));
			double unitY = Math.abs(Math.sin(rotationRequired));

			double correctUx = unitX;
			double correctUy = unitY;

			if(bufferedImage.getWidth()< bufferedImage.getHeight())
			{
				correctUx=unitY;
				correctUy=unitX;
			}

			int posAffineX = posX-(int)(locationX)-(int)(correctUx*diff);
			int posAffineY = posY-(int)(locationY)-(int)(correctUy*diff);

			AffineTransform objTrans = new AffineTransform();
			objTrans.translate(correctUx*diff,correctUy*diff);
			objTrans.rotate(rotationRequired,locationX,locationY);

			AffineTransformOp op = new AffineTransformOp(objTrans, AffineTransformOp.TYPE_BILINEAR);

			ImageIcon a = new ImageIcon(op.filter(bufferedImage, null));
			return a;

		}
		public ImageIcon setarrowRotate(double x) // rotate the arrow by x degrees
		{
			BufferedImage bufferedImage = null;
			x=x-10;
			try {
				bufferedImage = ImageIO.read(new File(imagePath + "arrow.png"));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			int posX = s.getX();
			int posY = s.getY();

			double locationX = bufferedImage.getWidth()/2;
			double locationY = bufferedImage.getHeight()/2;

			double diff = Math.abs(bufferedImage.getWidth()-bufferedImage.getHeight());

			double rotationRequired = Math.toRadians(x);
			double unitX = Math.abs(Math.cos(rotationRequired));
			double unitY = Math.abs(Math.sin(rotationRequired));

			double correctUx = unitX;
			double correctUy = unitY;

			if(bufferedImage.getWidth()< bufferedImage.getHeight())
			{
				correctUx=unitY;
				correctUy=unitX;
			}

			int posAffineX = posX-(int)(locationX)-(int)(correctUx*diff);
			int posAffineY = posY-(int)(locationY)-(int)(correctUy*diff);

			AffineTransform objTrans = new AffineTransform();
			objTrans.translate(correctUx*diff,correctUy*diff);
			objTrans.rotate(rotationRequired,locationX,locationY);

			AffineTransformOp op = new AffineTransformOp(objTrans, AffineTransformOp.TYPE_BILINEAR);

			ImageIcon a = new ImageIcon(op.filter(bufferedImage, null));
			return a;

		}


		public GameContainerPanel() //creates screens and adds to a cardLayout
		{
			bul = new JPanel();
			bul.setLayout(null);
			bul.setBackground(new Color(0,0,0,0));
			bul.setOpaque(true);
			Image jetsImg1 = new ImageIcon(imagePath + "jetFightLeft.png").getImage();
			jetter1.setImage(jetsImg1);

			Image jetsImg = new ImageIcon(imagePath + "jetFight.png").getImage();
			jetter.setImage(jetsImg);

			ImageIcon turr = new ImageIcon();
			Image t = new ImageIcon(imagePath + "quarterLeft.png").getImage();
			turr.setImage(t);
			turretF = new JLabel(turr);
			turretF.setBounds(0,600,150,200);

			ImageIcon turr1 = new ImageIcon();
			Image t1 = new ImageIcon(imagePath + "quarterRight.png").getImage();
			turr1.setImage(t1);
			turretF1 = new JLabel(turr1);
			turretF1.setBounds(1160,600,150,200);
			//mainScreenPanel.add(turretF1);

			ballX = 0;
			ballY = 600;

			// create timer for animation of ball
			PlaneMover ballmover = new PlaneMover();
			balltimer = new Timer(10, ballmover);
			//balltimer.start();

			setBackground(Color.BLACK);
			cardLayout = new CardLayout();
			setLayout(cardLayout);
			setOpaque(false);

			mainScreenPanel = new JPanel(new BorderLayout());
			mainScreenPanel.setOpaque(false);
			mainScreenPanel.setPreferredSize(new Dimension(1290, 700));

			this.add(mainScreenPanel, "mainScreenPanel");

			instructionsPanel = new JPanel();
			instructionsPanel.setLayout(new BorderLayout(0,0));
			instructionsPanel.setOpaque(false);
			instructionsPanel.setPreferredSize(new Dimension(1290, 700));
			this.add(instructionsPanel, "instructionsPanel");

			this.addKeyListener(this);

			mainScreen();
			instructionScreen();
			start();
		}

		public void mainScreen() //creates main screen with all components
		{
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50,407));
			buttonPanel.setAlignmentY(CENTER_ALIGNMENT);
			buttonPanel.setPreferredSize(new Dimension(1000,400));
			buttonPanel.setBackground(new Color(0,0,0,0));
			JButton instructionsButton = new JButton("Instructions");
			instructionsButton.setPreferredSize(new Dimension(350, 50));
			instructionsButton.setOpaque(true);
			instructionsButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			instructionsButton.setLocation(50, 300);
			instructionsButton.addActionListener(e -> cardLayout.show(this, "instructionsPanel"));
			instructionsButton.addActionListener(this);
			instructionsButton.setOpaque(true);
			instructionsButton.setContentAreaFilled(false);
			instructionsButton.setBorderPainted(false);
			instructionsButton.setFont(new Font("Times New Roman", Font. BOLD, 25));
			instructionsButton.setForeground(Color.BLACK);

			JButton playRussia = new JButton("Play as Russian");
			playRussia.setPreferredSize(new Dimension(330, 50));
			playRussia.setOpaque(true);
			playRussia.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			playRussia.setBackground(Color.RED);
			playRussia.setLocation(300, 300);
			playRussia.addActionListener(e -> playScreenAir());
			playRussia.addActionListener(this);
			playRussia.setOpaque(true);
			playRussia.setContentAreaFilled(false);
			playRussia.setBorderPainted(false);
			playRussia.setFont(new Font("Times New Roman", Font. BOLD, 25));
			playRussia.setForeground(Color.BLACK);

			buttonPanel.add(playRussia);


			JButton playUk = new JButton("Play as Ukraine");
			playUk.setPreferredSize(new Dimension(330, 50));
			playUk.setOpaque(true);
			playUk.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			playUk.setBackground(Color.RED);
			playUk.setLocation(300, 300);
			playUk.addActionListener(e -> playScreenGame());
			playUk.addActionListener(this);
			playUk.setOpaque(true);
			playUk.setContentAreaFilled(false);
			playUk.setBorderPainted(false);
			playUk.setFont(new Font("Times New Roman", Font. BOLD, 25));
			playUk.setForeground(Color.BLACK);

			buttonPanel.add(playUk);


			buttonPanel.add(instructionsButton);

			JButton monstersButton = new JButton("Leaderboard");
			monstersButton.setPreferredSize(new Dimension(330, 50));
			monstersButton.setOpaque(true);
			monstersButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			//monstersButton.setBackground(Color.YELLOW);
			monstersButton.setLocation(600, 300);
			//monstersButton.addActionListener(e -> cardLayout.show(this, "levelPanel"));
			monstersButton.addActionListener(this);
			monstersButton.setOpaque(true);
			monstersButton.setContentAreaFilled(false);
			monstersButton.setBorderPainted(false);
			monstersButton.setFont(new Font("Times New Roman", Font. BOLD, 25));
			monstersButton.setForeground(Color.BLACK);

			//buttonPanel.add(monstersButton);

			name.setEditable(true);
			name.setFocusable(true);
			name.setEnabled(true);
			name.setBounds(520,700,200,50);
			name.setFont(new Font("Times New Roman", Font. ITALIC, 15));
			name.addActionListener(this);
			mainScreenPanel.add(name);


			JButton enterName = new JButton("Submit Name");
			enterName.setBounds(760,700,150,50);	
			enterName.setFont(new Font("Times New Roman", Font.ITALIC, 15));
			enterName.addActionListener(e->setName());
			mainScreenPanel.add(enterName);

			JButton leaderboardButton = new JButton("LeaderBoard", new ImageIcon(imagePath+ "buttonLevel.png"));

			leaderboardButton.setPreferredSize(new Dimension(320, 50));
			leaderboardButton.setOpaque(true);

			leaderboardButton.setLocation(300, 600);
			leaderboardButton.addActionListener(e -> cardLayout.show(this, "leaderboardPanel"));
			leaderboardButton.addActionListener(this);
			leaderboardButton.setOpaque(true);
			leaderboardButton.setContentAreaFilled(false);
			leaderboardButton.setBorderPainted(false);
			leaderboardButton.setFont(new Font("Times New Roman", Font. BOLD, 25));
			leaderboardButton.setForeground(Color.BLACK);

			buttonPanel.add(leaderboardButton);
			mainScreenPanel.add(turretF);
			mainScreenPanel.add(turretF1);
			JLabel leaderBoardTitle = new JLabel("Leaderboard");
			leaderBoardTitle.setFont(new Font("Times New Roman", Font. BOLD, 25));
			leaderBoardTitle.setBounds(580,555,250,110);
			bul.add(leaderBoardTitle);
			bul.add(playRussia);
			bul.add(playUk);
			bul.add(instructionsButton);
			bul.add(leaderboardButton);

			leaderboardButton.addActionListener(e-> leaderScreen());
			playRussia.setBounds(150,410,200,50);
			playUk.setBounds(550,410,200,50);
			instructionsButton.setBounds(891,410,300,50);
			leaderboardButton.setBounds(510,560,310,110);
			mainScreenPanel.add(bul);


			//buttonPanel.setBounds(200,200,300,300);

		}

		public void setName()//sets name for player
		{
			playerName = name.getText();
		}
		public void leaderScreen() { // this calls the GameBoard class which is attached; this runs the main game
			// TODO Auto-generated method stub
			//balltimer.stop();
			levelPanel = new JPanel();
			this.add(levelPanel, "levelPanel");
			showLeaderBoard();
			cardLayout.show(this, "levelPanel");
		}

		public void playScreenAir() { // this calls the GameBoard class which is attached; this runs the main game
			// TODO Auto-generated method stub
			if (playerName == null)
			{
				JOptionPane.showMessageDialog(frame, "Please enter your name first.");
				return;
			}
			balltimer.restart();
			playRussian = new PlayAir(this); 
			this.add(playRussian,"gamePanel");
			whatIsPlaying = RUSSIAN_GAME;
			playRussian.start();
			cardLayout.show(this, "gamePanel");
		}
		public void playScreenGame() { // this calls the GameBoard class which is attached; this runs the main game
			// TODO Auto-generated method stub
			if (playerName == null)
			{
				JOptionPane.showMessageDialog(frame, "Please enter your name first.");
				return;
			}
			balltimer.restart();
			playUkr = new PlayGame(this); 
			this.add(playUkr,"gamePanelRussia");
			whatIsPlaying = UKR_GAME;
			playUkr.start();
			cardLayout.show(this, "gamePanelRussia");
		}

		public void showLeaderBoard() // for the Panel that will appear when the level button is pressed
		{
			levelPanel.setLayout(null);
			levelPanel.setOpaque(false);
			levelPanel.setPreferredSize(new Dimension(1290, 700));
			boolMainScreen=false;
			JLabel i1 = new JLabel(new ImageIcon(imagePath + "leaderboard.png"));
			i1.setBounds(0, 0, 1300, 800);

			ScoreManager scoreManager = new ScoreManager();
			LinkedHashMap<String, Integer> scores = scoreManager.returnScores();
			List <String>keys = new ArrayList(scores.keySet());
			int yCoord=300;
			int cnt=0;
			JButton back = new JButton("Home");
			back.addActionListener(e->showHome(-1));
			back.setBounds(580,640,180,50);
			levelPanel.add(back);
			for(int i=0;i<keys.size();i++)
			{
				cnt++;
				if(cnt>10)
				{
					break;
				}
				String currKey = keys.get(i);
				JLabel currName = new JLabel(currKey);
				currName.setBounds(500,yCoord,300,100);
				JLabel currScore = new JLabel(scores.get(currKey)+"");
				currScore.setBounds(700,yCoord,300,100);
				currName.setFont(theFont);
				currScore.setFont(theFont);
				levelPanel.add(currName);
				levelPanel.add(currScore);
				yCoord+=30;

			}


			levelPanel.add(i1);
		}

		public void instructionScreen() //when run this creates the components for the instruction screen 
		{

			JPanel inst1,inst2,inst3,inst4;
			JLabel i1,i2,i3,i4;
			JPanel instructions = new JPanel(cardLayout);

			JPanel buttonPanel = new JPanel(null);
			buttonPanel.setPreferredSize(new Dimension(1290,200));
			buttonPanel.setBackground(new Color(89,18,15));

			i1 = new JLabel(new ImageIcon(imagePath + "screen1.png"));
			i2 = new JLabel(new ImageIcon(imagePath + "screen2.png"));
			i3 = new JLabel(new ImageIcon(imagePath + "screen3.png"));
			i4 = new JLabel(new ImageIcon(imagePath + "screen4.png"));

			inst1 = new JPanel();
			inst1.add(i1);

			inst2 = new JPanel();
			inst2.add(i2);

			inst3 = new JPanel();
			inst3.add(i3);

			inst4 = new JPanel();
			inst4.add(i4);

			instructions.add(inst1,"pan1");
			instructions.add(inst2,"pan2");
			instructions.add(inst3,"pan3");
			instructions.add(inst4,"pan4");

			JButton next = new JButton("", new ImageIcon(imagePath+ "right.png"));
			//next.setPreferredSize(new Dimension(100, 100));
			next.addActionListener(e -> cardLayout.next(instructions));
			next.setBackground(new Color(89,18,15));
			next.setBorderPainted(false);
			next.addActionListener(this);

			JButton prev = new JButton("", new ImageIcon(imagePath+ "left.png"));
			//prev.setPreferredSize(new Dimension(100, 100));
			prev.setBackground(new Color(89,18,15));
			prev.addActionListener(e -> cardLayout.previous(instructions));
			prev.setBorderPainted(false);
			prev.addActionListener(this);

			JButton backButton = new JButton("e",  new ImageIcon(imagePath+ "exit.png"));
			//backButton.setPreferredSize(new Dimension(200,100));
			backButton.setBackground(new Color(89,18,15));

			backButton.setForeground(new Color(0,0,0,0));
			backButton.addActionListener(e -> cardLayout.show(this, "mainScreenPanel"));
			backButton.setBorderPainted(false);
			backButton.addActionListener(this);

			buttonPanel.add(backButton);
			buttonPanel.add(prev);
			buttonPanel.add(next);

			Insets insets = buttonPanel.getInsets();

			backButton.setBounds(25 + insets.left, 5 + insets.top,
					100, 20);

			prev.setBounds(1000 + insets.left, 5 + insets.top,
					115, 20);

			next.setBounds(1150 + insets.left, 5 + insets.top,
					115, 20);

			instructionsPanel.add(instructions,BorderLayout.NORTH);
			instructionsPanel.add(buttonPanel, BorderLayout.CENTER);

		}

		@Override
		public void actionPerformed(ActionEvent e)// this is controlling what is being displayed on the screen at a given time
		{
			String command = e.getActionCommand();
			if (command.equals("Instructions")) 
			{
				boolInstructions = true; 
				boolMainScreen=false;
			}
			if (command.equals("Start")) 
			{
				boolPlay = true; 
				boolMainScreen=false;
			}
			if (command.equals("Levels")) 
			{
				boolPlay = false; 
				boolMainScreen=false;
			}
			if(command.equals("e"))
			{
				boolMainScreen=true;
			}

		}



		class PlaneMover implements ActionListener { // this is for moving the planes on the home screen
			public void actionPerformed(ActionEvent e) {
				// ball XY location
				ballX += ballSpeed;
				if(pos==true)
					ballY+=ballSpeed;
				else
					ballY-=ballSpeed;

				if (ballX > 1400 || ballX<-20 || ballY<-30 || ballY>1000) {
					int temp2 = (int)(Math.random()*2+1);
					if(temp2==1)
					{
						pos=false;
					}
					else {
						pos=true;
					}
					ballX = -15;
					ballY = (int)(Math.random()*800+1);
				}

				oppBallX -= ballSpeed;
				if(oppPos==true)
					oppBallY+=ballSpeed;
				else
					oppBallY-=ballSpeed;

				if (oppBallX > 1400 || oppBallX<-20 || oppBallY<-30 || oppBallY>1000) {
					int temp2 = (int)(Math.random()*2+1);
					if(temp2==1)
					{
						oppPos=false;
					}
					else {
						oppPos=true;
					}
					oppBallX = 1300;
					oppBallY = (int)(Math.random()*800+1);
				}


				repaint();
				grabFocus();
			}
		} // end BallMove

		public void start() { //this starts the thread and it is called in the main method

			if (runner == null) 
			{
				runner = new Thread(this);
				runner.start();
			}
		}

		public void stop() // this will stop the thread
		{
			if (runner != null) 
			{
				runner = null;
			}
		}

		public void run() { 
			JLabel sp = new JLabel();// this is the main thread running the while(true) loop
			while (true) 
			{
				if(cnt1==0)
				{
					ImageIcon s = new ImageIcon(imagePath + "sparkle.gif");

					sp.setIcon(s);
					mainScreenPanel.add(sp);

					jets = new JLabel(jetter1);
					jets.setBounds((int)(Math.random()*800+100),(int)(Math.random()*50+50),200,80);
					mainScreenPanel.add(jets);
					cnt1++;
					ImageIcon i1 = new ImageIcon();
					Image tank = new ImageIcon(imagePath + "tank.png").getImage();
					i1.setImage(tank);
					tankLabel = new JLabel(i1);
					tankLabel.setVisible(false);

					ImageIcon i2 = new ImageIcon();
					Image tank1 = new ImageIcon(imagePath + "tankLeft.png").getImage();
					i2.setImage(tank1);
					tankLabelLeft = new JLabel(i2);
					tankLabelLeft.setVisible(false);

					arrowNewleft = new JLabel("");
					mainScreenPanel.add(arrowNewleft);
					arrowNewleft.setBounds(turretF.getX()+100,yLoc,100,72);

					arrowNewRight = new JLabel("");
					mainScreenPanel.add(arrowNewRight);
					arrowNewRight.setBounds(turretF1.getX()+10,yLoc,100,72);

					mainScreenPanel.add(tankLabel);
					mainScreenPanel.add(tankLabelLeft);


				}

				if(whichWayRight)
				{
					xLoc+=3;
				}
				else {
					xLoc-=3;
				}
				for(int i=0;i<dropBombLeft.size();i++)
				{
					if((xLoc>=dropBombLeft.get(i)-3)&&(xLoc<=dropBombLeft.get(i)+3))
					{
						if(whichWayRight)
						{
							JLabel bulletNew = new JLabel(setbulletRotate((double)Math.toDegrees(Math.atan((jets.getX()+170-turretF.getX())/Math.abs(turretF.getY()-jets.getY()+0.0000000001)))));
							bul.add(bulletNew);
							arrowNewleft.setIcon(setarrowRotate((double)Math.toDegrees(Math.atan((jets.getX()+170-turretF.getX())/Math.abs(turretF.getY()-jets.getY()+0.0000000001)))));
							arrowNewleft.setBounds(turretF.getX()+80,yLoc+20,100,72);
							bulletNew.setBounds(turretF.getX()+80,yLoc-10,35,99);
							bullets.add(bulletNew);
							xTravel.add((double)(jets.getX()+170-turretF.getX())/(Math.abs(turretF.getY()-jets.getY())+1));
							yTravel.add(1.0);
							dropBombLeft.remove(i);
						}
						else {
							JLabel bulletNew = new JLabel(setbulletRotate((double)Math.toDegrees(Math.atan((jets.getX()-50-turretF.getX())/Math.abs(turretF.getY()-jets.getY()+0.0000000001)))));
							bul.add(bulletNew);
							arrowNewleft.setIcon(setarrowRotate((double)Math.toDegrees(Math.atan((jets.getX()-50-turretF.getX())/Math.abs(turretF.getY()-jets.getY()+0.0000000001)))));
							arrowNewleft.setBounds(turretF.getX()+80,yLoc+20,100,72);
							bulletNew.setBounds(turretF.getX()+80,yLoc-10,35,99);
							bullets.add(bulletNew);
							xTravel.add((double)(jets.getX()-50-turretF.getX())/Math.abs(turretF.getY()-jets.getY()+0.0000000000001));
							yTravel.add(1.0);
							dropBombLeft.remove(i);
						}


					}
				}
				if(whichWayRight==false && xLoc<50)
				{
					whichWayRight =true;
					speedRight=2;
					dropBombLeft.clear();
					int numOfBombs =(int)(Math.random()*4+2);
					for(int z=0;z<numOfBombs;z++)
					{
						dropBombLeft.add((int)(Math.random()*1000+50));
						//System.out.print(dropBombLeft.get(z)+" ");
					}
					//System.out.println("");
				}

				else if(whichWayRight==true && xLoc>1000)
				{
					whichWayRight =false;
					speedLeft=-2;
					dropBombLeft.clear();
					int numOfBombs =(int)(Math.random()*4+2);
					for(int z=0;z<numOfBombs;z++)
					{
						dropBombLeft.add((int)(Math.random()*1000+50));
						//System.out.print(dropBombLeft.get(z)+" ");
					}
					//System.out.println("");
				}

				if(whichWayLeft)
				{
					xLocLeft+=3;
				}
				else {
					xLocLeft-=3;
				}
				for(int i=0;i<dropBomb.size();i++)
				{
					if((xLoc>=dropBomb.get(i)-3)&&(xLoc<=dropBomb.get(i)+3))
					{
						if(whichWayRight)
						{
							JLabel bulletNew = new JLabel(setbulletRotate((double)Math.toDegrees(Math.atan((jets.getX()+170-turretF1.getX())/Math.abs(turretF1.getY()-jets.getY()+0.0000000000001)))));
							arrowNewRight.setIcon(setarrowRotate((double)Math.toDegrees(Math.atan((jets.getX()+170-turretF1.getX())/Math.abs(turretF1.getY()-jets.getY()+0.0000000000001)))));

							arrowNewRight.setBounds(turretF1.getX()+10,yLoc+20,100,72);
							bul.add(bulletNew);

							bulletNew.setBounds(turretF1.getX()+10,turretF1.getY()-10,35,99);
							//System.out.println((turretF1.getX()+10)+" "+(turretF1.getY()-10));
							bullets.add(bulletNew);
							xTravel.add((double)(jets.getX()+170-turretF1.getX())/Math.abs(turretF1.getY()-jets.getY()+0.0000000000001));
							yTravel.add(1.0);
							dropBomb.remove(i);
						}
						else {
							JLabel bulletNew = new JLabel(setbulletRotate((double)Math.toDegrees(Math.atan((jets.getX()+50-turretF1.getX())/Math.abs(turretF1.getY()-jets.getY()+0.0000000000001)))));
							arrowNewRight.setIcon(setarrowRotate((double)Math.toDegrees(Math.atan((jets.getX()+50-turretF1.getX())/Math.abs(turretF1.getY()-jets.getY()+0.0000000000001)))));

							arrowNewRight.setBounds(turretF1.getX()+10,yLoc+20,100,72);
							bul.add(bulletNew);

							bulletNew.setBounds(turretF1.getX()+10,turretF1.getY()-10,35,99);
							//System.out.println((turretF1.getX()+10)+" "+(turretF1.getY()-10));
							bullets.add(bulletNew);
							xTravel.add((double)(jets.getX()+50-turretF1.getX())/Math.abs(turretF1.getY()-jets.getY()+0.0000000000001));
							yTravel.add(1.0);
							dropBomb.remove(i);
						}

					}
				}
				if(whichWayLeft==false && xLocLeft<50)
				{
					whichWayLeft =true;
					xLocLeft+=2;
					dropBomb.clear();
					int numOfBombs =(int)(Math.random()*4+2);
					for(int z=0;z<numOfBombs;z++)
					{
						dropBomb.add((int)(Math.random()*1000+50));
						//System.out.print(dropBomb.get(z)+" ");
					}
					//System.out.println("");

				}

				else if(whichWayLeft==true && xLocLeft>1000)
				{
					whichWayLeft =false;
					xLocLeft-=2;
					dropBomb.clear();
					int numOfBombs =(int)(Math.random()*4+2);
					for(int z=0;z<numOfBombs;z++)
					{
						dropBomb.add((int)(Math.random()*1000+50));
					}
				}
				tankLabel.setBounds((xLoc),yLoc,125,110);
				tankLabelLeft.setBounds((xLocLeft),yLoc,125,110);
				if(whichWayRightJet)
				{
					xLocJet+=2;
				}
				else 
				{
					xLocJet-=2;
				}
				if(whichWayRightJet==false && xLocJet<0)
				{
					whichWayRightJet =true;
					howManyShoot=0;
					jets.setIcon(jetter1);
				}

				else if(whichWayRightJet==true && xLocJet>1100)
				{
					whichWayRightJet =false;
					howManyShoot=0;
					jets.setIcon(jetter);
				}
				jets.setBounds(xLocJet,yLocJet,200,80);
				for (int i=0; i < bullets.size(); i++)
				{
					JLabel b6 = bullets.get(i);
					b6.setBounds((int)(b6.getX()+(9*xTravel.get(i))),(int)(b6.getY()-(9)),66,80);
					if(b6.getY()<-100 || b6.getX()>1300  || b6.getX()<-50|| isHit(i))
					{
						//System.out.print("gone ");
						//System.out.println((b6.getX())+" "+b6.getY());
						bullets.remove(i);
						xTravel.remove(i);
						yTravel.remove(i);
						//mainScreenPanel.remove(b6);
						b6.setVisible(false);
					}
				}
				sp.setBounds(505,-30,200,200);
				repaint();
				try { Thread.sleep(10); } // try catch for the number of milliseconds to refresh
				catch (InterruptedException e) { } // catch error
			}

		}

		public void showHome(int score) { //take code back home
			ScoreManager scoreManager = new ScoreManager();
			if(score!=-1)
			{
				scoreManager.addScore(playerName, score);
			}
			cardLayout.show(this, "mainScreenPanel");
			boolMainScreen = true;
			this.addKeyListener(this);
			start();
		}

		@Override
		public void keyTyped(KeyEvent e) {
			if (whatIsPlaying == RUSSIAN_GAME)
			{
				playRussian.keyTyped(e);
			}
			if (whatIsPlaying == UKR_GAME)
			{
				playUkr.keyTyped(e);
			}

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (whatIsPlaying == RUSSIAN_GAME)
			{
				playRussian.keyPressed(e);
			}
			if (whatIsPlaying == UKR_GAME)
			{
				playUkr.keyPressed(e);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (whatIsPlaying == RUSSIAN_GAME)
			{
				playRussian.keyReleased(e);
			}
			if (whatIsPlaying == UKR_GAME)
			{
				playUkr.keyReleased(e);
			}

		}



	}


}



