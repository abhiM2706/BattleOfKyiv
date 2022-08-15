package com.stem.game;
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

import com.stem.game.BattleOfKyiv.GameContainerPanel;

public class PlayGame extends JPanel implements Runnable,  ActionListener, KeyListener { // this class runs the entire game

	JButton backButton = new JButton();
	int currScore=0;
	int bombScore=0;
	boolean keepAdding=true;
	JLabel scores = new JLabel();
	JButton retry = new JButton();
	JLabel failed=new JLabel("");
	JLabel success=new JLabel("");
	boolean stopShooting=false;
	JPanel popUp;
	JLabel bloodFailed=new JLabel();
	Font theFont = new Font("TimesRoman",Font.BOLD,24);
	Date theDate;
	Thread runner;
	JLabel currLevel; 
	int counter = 100;
	JPanel board, stats;
	List<JLabel> drones = new ArrayList<JLabel>();
	List<Integer> dronePower = new ArrayList<Integer>();
	List<JLabel> bullets = new ArrayList<JLabel>();
	List<Integer> speed = new ArrayList<Integer>();
	List<JLabel> wallsProtect = new ArrayList<JLabel>();
	List<JLabel> capital = new ArrayList<JLabel>();
	boolean rapidFire = false;
	int nozzleAdded=0;
	
	JLabel arrowNewleft=null,arrowNewRight=null;
	
	List<List<Integer>>dropBomb = new ArrayList<List<Integer>>();
	
	List<Double> xTravel = new ArrayList<Double>();
	List<Double> yTravel = new ArrayList<Double>();
	
	int capitalHealth=5;
	String imagePath = "C:\\Users\\abhin\\OneDrive\\Desktop\\game\\";
	int xLoc=1000;
	int xLocLeft=100;
	int yLoc=600;
	int cnt=0;
	JLabel currHealth, currHealthLeft;
	ImageIcon h1,h2,h3,h4;
	double currAngle=45.0;
	double currAngleLeft=135.0;
	
	boolean whichWayRight=false;
	boolean whichWayLeft =true;
	int speedRight=6;
	int speedLeft=-6;
	
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
	int bombDamage;
	int currBallX;
	int levelGame;
	double levelMultiplier;
	JLabel tankLabel = null;
	JLabel tankLabelLeft =null;
	
	JLabel nozzleRight=null,nozzleLeft=null;
	
	
	
	
	int bombDropping;
	JLabel roadImage,background,crackWall1,crackWall2;
	List<Integer>health = new ArrayList<Integer>();
	
	
	JLabel turretF1, turretF;
	
	GameContainerPanel mainBoard;
	public PlayGame(GameContainerPanel gameContainerPanel) // this constructor initializes some of the variables 
	{
		this.mainBoard = gameContainerPanel;
		setPreferredSize(new Dimension(1300, 800));
		setLayout(new BorderLayout());

		setVisible(true);
		setLayout(new BorderLayout());

		randomPlaneY.add(20);
		randomPlaneY.add(80);
		randomPlaneY.add(130);
		randomPlaneY.add(180);
		randomPlaneY.add(210);

		h1 = new ImageIcon();
		Image healthA = new ImageIcon(imagePath + "healthFull.png").getImage();
		h1.setImage(healthA);
		currHealth = new JLabel(h1);
		currHealthLeft = new JLabel(h1);

		h2 = new ImageIcon();
		healthA = new ImageIcon(imagePath + "health3.png").getImage();
		h2.setImage(healthA);

		h3= new ImageIcon();
		healthA = new ImageIcon(imagePath + "health2.png").getImage();
		h3.setImage(healthA);

		h4= new ImageIcon();
		healthA = new ImageIcon(imagePath + "health1.png").getImage();
		h4.setImage(healthA);

		board = new JPanel();
		board.setLayout(null);
		stats = new JPanel();
		stats.setLayout(null);
		stats.setBackground(Color.GRAY);
		stats.setPreferredSize(new Dimension(1200,70));
		add(board,BorderLayout.CENTER);

		try //try catch for getting audio clip
		{
			clipNameAIS = AudioSystem.getAudioInputStream(new File("C:\\Users\\abhin\\OneDrive\\Desktop\\game\\shootingGun.wav").getAbsoluteFile());
			clipNameClip = AudioSystem.getClip();
			clipNameClip.open(clipNameAIS);
		}
		catch(Exception e) // catch method for above
		{
			System.out.println("Failure to load sound " + e.getMessage());
		}

		
		stats.setBounds(0, 0, 1200, 200);
		stats.setBackground(new Color(91,127,255));
		
		add(stats,BorderLayout.NORTH);
		
		currBallX = (int)(Math.random()*100+10);
		levelGame = 2; //set to 1 for testing purposes

		ImageIcon road = new ImageIcon();
		Image r = new ImageIcon(imagePath + "road.png").getImage();
		road.setImage(r);
		roadImage = new JLabel(road);
		roadImage.setBounds(0,550,1200,183);

		ImageIcon back = new ImageIcon();
		Image b = new ImageIcon(imagePath + "backgroundPlay.png").getImage();
		back.setImage(b);
		background = new JLabel(back);
		background.setBounds(0,0,1300,800);

		ImageIcon turr = new ImageIcon();
		Image t = new ImageIcon(imagePath + "quarterLeft.png").getImage();
		turr.setImage(t);
		turretF = new JLabel(turr);
		turretF.setBounds(0,550,150,200);
		board.add(turretF);

		ImageIcon turr1 = new ImageIcon();
		Image t1 = new ImageIcon(imagePath + "quarterRight.png").getImage();
		turr1.setImage(t1);
		turretF1 = new JLabel(turr1);
		turretF1.setBounds(1150,550,150,200);
		board.add(turretF1);

		if(levelGame == 2) 
		{
			currLevel = new JLabel("Protecting Kyiv");
			currLevel.setFont(theFont);
			levelMultiplier = 1.5;
			bombDamage=2;
			bombDropping = 6;

		}
		
		currLevel.setBounds(100,10,200,50);
		stats.add(currLevel);
		board.add(currHealth);
		board.add(currHealthLeft);
	}

	public ImageIcon setbulletRotate(double x) // rotate the bullet by x degrees
	{
		BufferedImage bufferedImage = null;
		
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
		
		double rotationRequired = Math.toRadians(90-x);
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
		
		double rotationRequired = Math.toRadians(90-x);
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
	
	
	public void keyTyped(KeyEvent e) { // this method is for when a key is typed on the key board
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) { // this method is for when a key is pressed on the key board
		
		switch (e.getKeyCode())
		{
			
			case KeyEvent.VK_A:
			{
				if(currAngle<=90)
				{
					currAngle+=5;
				}
				arrowNewleft.setIcon(setarrowRotate(currAngle));
				break;
			}
			case KeyEvent.VK_D:
			{
				
				if(currAngle>=0)
				{
					currAngle-=5;
				}
				arrowNewleft.setIcon(setarrowRotate(currAngle));
				
				break;
			}
			
			
			case KeyEvent.VK_LEFT:
			{
				if(currAngleLeft<=180)
				{
					currAngleLeft+=5;
				}
				arrowNewRight.setIcon(setarrowRotate(currAngleLeft));
				
				break;
			}
			case KeyEvent.VK_RIGHT:
			{
				if(currAngleLeft>=90)
				{
					currAngleLeft-=5;
				}
				arrowNewRight.setIcon(setarrowRotate(currAngleLeft));
				
				break;
			}
			
		}
	}
	
	public void keyReleased(KeyEvent e) { // this method is for when a key is released on the key board
		// TODO Auto-generated method stub
		

		switch (e.getKeyCode())
		{
			case KeyEvent.VK_W:
			{
				clipNameClip.setFramePosition(0);
				clipNameClip.start();
				//System.out.print(true);
				
				if(bullets.size()<15)
				{
					
					/*ImageIcon i = new ImageIcon();
					Image jet45Up = new ImageIcon(imagePath + "bullet.png").getImage();
					i.setImage(jet45Up);
					JLabel b = new JLabel(i);
					b.setPreferredSize(new Dimension(20,76));
					*/
					xTravel.add(Math.cos(Math.toRadians(currAngle)));
					yTravel.add(Math.sin(Math.toRadians(currAngle)));
					
					JLabel bulletNew = new JLabel(setbulletRotate(currAngle));
					//System.out.println((xLoc+44)+" "+(yLoc-40));
					bulletNew.setBounds(turretF.getX()+74,yLoc-80,66,80);
					
					board.add(bulletNew);
					bullets.add(bulletNew);
				}
				break;
			}
			case KeyEvent.VK_UP:
			{
				clipNameClip.setFramePosition(0);
				clipNameClip.start();
				//System.out.print(true);
				/*ImageIcon i = new ImageIcon();
				Image jet45Up = new ImageIcon(imagePath + "bullet.png").getImage();
				i.setImage(jet45Up);
				JLabel b = new JLabel(i);
				b.setPreferredSize(new Dimension(20,76));*/
				if(bullets.size()<15)
				{
					xTravel.add(Math.cos(Math.toRadians(currAngleLeft)));
					yTravel.add(Math.sin(Math.toRadians(currAngleLeft)));
					
					
					JLabel bulletNew = new JLabel(setbulletRotate(currAngleLeft));
					//System.out.println((xLoc+44)+" "+(yLoc-40));
					bulletNew.setBounds(turretF1.getX()-10,yLoc-80,66,80);
					
					board.add(bulletNew);
					bullets.add(bulletNew);
				}
				break;
			}
		}
		
	}

	public boolean isHit(int currentBulletIndex) // this checks whether the bullet has a hit a bomb or plane
	{
		boolean hit =false;
		boolean hitCapital = false;
		boolean checkBall=false;
		JLabel circleCheck = null;
		JLabel currentBullet = bullets.get(currentBulletIndex);
		if(planes.size()>0)
		{
			checkBall=true;
		}
		for(int i =0;i<drones.size();i++)
		{
			JLabel droneToCheck = drones.get(i);
			
			/*
			 * Check whether current bullet is between drone X coord and Y coord
			 */
			if(((currentBullet.getX()>=droneToCheck.getX()) && (currentBullet.getX()<=droneToCheck.getX()+35)) 
					|| ((currentBullet.getX()+25>=droneToCheck.getX()) && (currentBullet.getX()+25<=droneToCheck.getX()+35)))
			{
				if((currentBullet.getY()>=droneToCheck.getY())&&(currentBullet.getY()<=droneToCheck.getY()+99))
				{
					try
					{
						ImageIcon explodeIcon = new ImageIcon(imagePath+"explode.gif");
						explodeIcon.setImage(explodeIcon.getImage().getScaledInstance(50, 50, explodeIcon.getImage().SCALE_DEFAULT));
						currentBullet.setIcon(explodeIcon);
						currentBullet.setBounds(currentBullet.getX(),currentBullet.getY(),50,50);
						if((currentBullet.getY()>=droneToCheck.getY())&&(currentBullet.getY()<=droneToCheck.getY()+40))
						{
							if(dronePower.get(i) == 1)
							{
								
								rapidFire=true;
							}
							bombScore+=20;
							hit=true;
							drones.remove(i);
							dronePower.remove(i);
							speed.remove(i);
							droneToCheck.setVisible(false);
							currentBullet.setVisible(false);
							
						}
					}
					catch (Exception e)
					{
						System.out.println("exception in replace" + e.getMessage());
					}
				}
			}
		}
		
		for(int i =0;i<planes.size();i++) 
		{
			JLabel droneToCheck = planes.get(i);
			/*
			 * Check whether current bullet is between drone X coord and Y coord
			 */
			if(((currentBullet.getX()>=droneToCheck.getX()) && (currentBullet.getX()<=droneToCheck.getX()+100)) 
					|| ((currentBullet.getX()+25>=droneToCheck.getX()) && (currentBullet.getX()+25<=droneToCheck.getX()+35)))
			{
				if((currentBullet.getY()>=droneToCheck.getY())&&(currentBullet.getY()<=droneToCheck.getY()+50))
				{
					
						if((currentBullet.getY()>=droneToCheck.getY())&&(currentBullet.getY()<=droneToCheck.getY()+40))
						{
							bombScore+=100;
							hit=true;
							planes.remove(i);
							planeSpeed.remove(i);
							leftRight.remove(i);
							dropBomb.remove(i);
							droneToCheck.setVisible(false);
						}
					
				}
			}
		}

		return hit;
	}
	public boolean tankHit(JLabel bomb) // this checks whether the tanks have been hit by bombs
	{
		boolean hitTank =false;
		JLabel currTank = tankLabel;
		if((bomb.getX()>=currTank.getX() && bomb.getX()<=currTank.getX()+125) || (bomb.getX()+35>=currTank.getX() && bomb.getX()+35<=currTank.getX()+125))
		{
			if((currTank.getY()>=bomb.getY()) && (currTank.getY()<=bomb.getY()+49))
			{
				hitTank=true;
				tankHealth--;
			}
		}
		
		currTank = tankLabelLeft;
		if((bomb.getX()>=currTank.getX() && bomb.getX()<=currTank.getX()+125) || (bomb.getX()+35>=currTank.getX() && bomb.getX()+35<=currTank.getX()+125))
		{
			if((currTank.getY()>=bomb.getY()) && (currTank.getY()<=bomb.getY()+49))
			{
				hitTank=true;
				tankHealthLeft--;
			}
		}
		return hitTank;
	}

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

	public void run() { // this is the main thread running the while(true) loop
		while (true) 
		{
			if(rapidFire)
			{
				int num=0;
				for(int i=0;i<20;i++)
				{
					num+=40;
					xTravel.add(Math.cos(Math.toRadians(currAngleLeft)));
					yTravel.add(Math.sin(Math.toRadians(currAngleLeft)));


					JLabel bulletNew = new JLabel(setbulletRotate(currAngleLeft));
					//System.out.println((xLoc+44)+" "+(yLoc-40));
					bulletNew.setBounds(turretF1.getX()-10+num,yLoc-80+num,66,80);

					board.add(bulletNew);
					bullets.add(bulletNew);

					xTravel.add(Math.cos(Math.toRadians(currAngle)));
					yTravel.add(Math.sin(Math.toRadians(currAngle)));

					JLabel bulletNew1 = new JLabel(setbulletRotate(currAngle));
					//System.out.println((xLoc+44)+" "+(yLoc-40));
					bulletNew1.setBounds(turretF.getX()+74-num,yLoc-80+num,66,80);

					board.add(bulletNew1);
					bullets.add(bulletNew1);
				}
				rapidFire=false;
			}
			if(cnt==0)
			{
				ImageIcon i1 = new ImageIcon();
				Image tank = new ImageIcon(imagePath + "tank.png").getImage();
				i1.setImage(tank);
				tankLabel = new JLabel(i1);
				tankLabelLeft = new JLabel(i1);

				ImageIcon i2 = new ImageIcon();
				Image tank1 = new ImageIcon(imagePath + "tankLeft.png").getImage();
				i2.setImage(tank1);
				tankLabelLeft = new JLabel(i2);


				board.add(tankLabel);
				board.add(tankLabelLeft);

				arrowNewleft = new JLabel("");
				board.add(arrowNewleft);
				arrowNewleft.setBounds(turretF.getX()+120,yLoc,100,72);

				arrowNewRight = new JLabel("");
				board.add(arrowNewRight);
				arrowNewRight.setBounds(turretF1.getX()+10,yLoc,100,72);

				ImageIcon mission = new ImageIcon();
				Image miss = new ImageIcon(imagePath + "missionFailed.png").getImage();
				mission.setImage(miss);
				failed = new JLabel(mission);

				ImageIcon missionS = new ImageIcon();
				Image missS = new ImageIcon(imagePath + "gtaPassed.png").getImage();
				missionS.setImage(missS);
				success = new JLabel(missionS);

				ImageIcon blood = new ImageIcon();
				Image b = new ImageIcon(imagePath + "popUpBackground.png").getImage();
				blood.setImage(b);
				bloodFailed = new JLabel(blood);

				scores.setFont(theFont);
				scores.setForeground(Color.BLACK);
				scores.setBounds(450,300,300,50);
				scores.setOpaque(true);
				scores.setVisible(false);

				retry = new JButton("Retry");
				retry.setFont(theFont);
				retry.setForeground(Color.BLACK);
				retry.setBounds(450,300,300,50);
				retry.setOpaque(true);

				backButton = new JButton("Go Back");
				backButton.setFont(theFont);
				backButton.setForeground(Color.BLACK);
				backButton.setBounds(450,300,300,50);
				backButton.setOpaque(true);
				backButton.addActionListener(this);
				backButton.setVisible(false);
				board.add(backButton);
				backButton.setBounds(450,400,300,50);

				board.add(failed);
				failed.setBounds(400,150,400,225);
				failed.setVisible(false);
				board.add(retry);
				board.add(scores);
				retry.setVisible(false);
				board.add(success);
				success.setBounds(400,150,400,225);
				success.setVisible(false);

				board.add(bloodFailed);
				bloodFailed.setBounds(330,150,550,406);
				bloodFailed.setVisible(false);



				cnt++;
			}

			if(tankHealth>9)currHealth.setIcon(h1);

			else if(tankHealth<=9 && tankHealth>6)
			{
				currHealth.setIcon(h2);
			}
			else if(tankHealth<=6 && tankHealth>3)
			{
				currHealth.setIcon(h3);
			}
			else if(tankHealth<=3 && tankHealth>0)
			{
				currHealth.setIcon(h4);
			}
			if(tankHealth<=0)
			{
				currHealth.setVisible(false);
				tankLabel.setVisible(false);
				board.remove(tankLabel);
				board.remove(currHealth);

			}
			if(tankHealthLeft>9)currHealthLeft.setIcon(h1);

			else if(tankHealthLeft<=9 && tankHealthLeft>6)
			{
				currHealthLeft.setIcon(h2);
			}
			else if(tankHealthLeft<=6 && tankHealthLeft>3)
			{
				currHealthLeft.setIcon(h3);
			}
			else if(tankHealthLeft<=3 && tankHealthLeft>0)
			{
				currHealthLeft.setIcon(h4);
			}
			if(tankHealthLeft<=0)
			{
				currHealthLeft.setVisible(false);
				tankLabelLeft.setVisible(false);
				board.remove(tankLabelLeft);
				board.remove(currHealthLeft);
			}
			if(tankHealthLeft<=0 && tankHealth<=0)
			{
				keepAdding=false;
				scores.setBackground(new Color(0,0,0,0));
				scores.setVisible(true);
				scores.setText("Score: "+(currScore+bombScore));	
				backButton.setVisible(true);
				failed.setVisible(true);
				bloodFailed.setVisible(true);
				break;
			}
			if(keepAdding)
			{
				if(whichWayRight)
				{
					xLoc+=3;
				}
				else {
					xLoc-=3;
				}
				if(whichWayRight==false && xLoc<50)
				{
					whichWayRight =true;
					speedRight=3;

				}

				else if(whichWayRight==true && xLoc>1000)
				{
					whichWayRight =false;
					speedLeft=-3;

				}

				if(whichWayLeft)
				{
					xLocLeft+=3;
				}
				else {
					xLocLeft-=3;
				}
				if(whichWayLeft==false && xLocLeft<50)
				{
					whichWayLeft =true;
					xLocLeft+=3;

				}

				else if(whichWayLeft==true && xLocLeft>1000)
				{
					whichWayLeft =false;
					xLocLeft-=3;

				}

				tankLabel.setBounds((xLoc),yLoc,125,110);
				tankLabelLeft.setBounds((xLocLeft),yLoc,125,110);

				arrowNewleft.setBounds(turretF.getX()+60,yLoc-50,100,72);

				arrowNewRight.setBounds(turretF1.getX()+10,yLoc-50,100,72);



				currHealth.setBounds(xLoc,yLoc+75,100,27);
				currHealthLeft.setBounds(xLocLeft,yLoc+75,100,27);
				if(planes.size()==0)
				{
					int howMany = (int)(Math.random()*(levelGame)+1);
					for(int i=0;i<howMany;i++)
					{
						JLabel img;
						int leftOrRight = (int)(Math.random()*2+1);
						if(leftOrRight==1)
						{
							currBallX = (int)(Math.random()*100-100);
							planeSpeed.add((int)(Math.random()*4+2));
							ImageIcon ballIcon = new ImageIcon();
							Image ball = new ImageIcon(imagePath + "jet.png").getImage();
							ballIcon.setImage(ball);
							img = new JLabel(ballIcon);
						}
						else {
							currBallX = (int)(Math.random()*100+1100);
							planeSpeed.add((int)(Math.random()*4-6));
							ImageIcon ballIcon = new ImageIcon();
							Image ball = new ImageIcon(imagePath + "jetFlip.png").getImage();
							ballIcon.setImage(ball);
							img = new JLabel(ballIcon);
						}

						List<Integer>now=new ArrayList<Integer>();
						int numOfBombs =(int)(Math.random()*(levelGame+4)+2);
						for(int z=0;z<numOfBombs;z++)
						{
							now.add((int)(Math.random()*1000+50));
						}
						dropBomb.add(now);

						leftRight.add(leftOrRight);
						board.add(img);
						planes.add(img);

						img.setBounds(currBallX,(int)(Math.random()*100+50),100,50);
					}

				}
				else {
					for(int i=0;i<planes.size();i++)
					{
						boolean move =true;
						JLabel temp = planes.get(i);
						if(leftRight.get(i)==1)
						{

							if(temp.getX()>1200)
							{
								temp.setVisible(false);
								planes.remove(i);
								leftRight.remove(i);
								planeSpeed.remove(i);
								move =false;
							}
						}
						else {

							if(temp.getX()<-200)
							{
								temp.setVisible(false);
								planes.remove(i);
								leftRight.remove(i);
								planeSpeed.remove(i);
								dropBomb.remove(i);
								move=false;
							}
						}
						if(move==true)
						{
							temp.setBounds(temp.getX()+planeSpeed.get(i),temp.getY(),100,50);
						}

						List<Integer>loc = new ArrayList<Integer>();
						if(dropBomb.size()>i+1)
						{
							loc = dropBomb.get(i);

							for(int z = 0;z<loc.size();z++)
							{
								if((temp.getX()>=loc.get(z)-20)&&(temp.getX()<=loc.get(z)+20))
								{
									int choice = (int)(Math.random()*8+1);
									if(choice == 3)
									{
										ImageIcon i1 = new ImageIcon();
										Image jet45Up = new ImageIcon(imagePath + "rapidFire.png").getImage();
										i1.setImage(jet45Up);
										JLabel img1 = new JLabel(i1);
										img1.setPreferredSize(new Dimension(50,50));
										board.add(img1);
										speed.add((int)((int)(Math.random()*3+1)*levelMultiplier));
										img1.setBounds(temp.getX(),temp.getY(),60,90);
										drones.add(img1);
										dronePower.add(1);
										loc.remove(z);
									}
									else {
										ImageIcon i1 = new ImageIcon();
										Image jet45Up = new ImageIcon(imagePath + "bomb.png").getImage();
										i1.setImage(jet45Up);
										JLabel img1 = new JLabel(i1);
										img1.setPreferredSize(new Dimension(50,50));
										board.add(img1);
										speed.add((int)((int)(Math.random()*3+1)*levelMultiplier));
										img1.setBounds(temp.getX(),temp.getY(),35,99);
										drones.add(img1);
										dronePower.add(0);
										loc.remove(z);
									}
								}
							}	
						}
					}
				}

				for (int i=0; i < bullets.size(); i++)
				{
					JLabel b = bullets.get(i);
					b.setBounds((int) (b.getX()+(7*xTravel.get(i))),(int) (b.getY()-(7*yTravel.get(i))),66,80);
					if(b.getY()<0 || b.getX()>1300  || b.getX()<-50|| isHit(i))
					{
						bullets.remove(i);
						xTravel.remove(i);
						yTravel.remove(i);
						b.setVisible(false);
					}
				}

				for (int i=0; i < drones.size(); i++)
				{
					JLabel drone = drones.get(i);
					drone.setBounds(drone.getX(),drone.getY()+(speed.get(i)+2),35,99);
					if(drone.getY()>650 || tankHit(drone))
					{
						drones.remove(i);
						dronePower.remove(i);
						speed.remove(i);
						drone.setVisible(false);
					}
				}

				s.setFont(theFont);
				s.setBounds(870,10,300,100);
				stats.add(s);

				board.add(roadImage);
				board.add(background);

				repaint();
				if(keepAdding)currScore+=1;
				if(currScore%10==0)
				{
					s.setText("Score: "+(bombScore+currScore));
				}
				if(currScore%400==0)
				{
					levelGame++;
				}

			}

			try { Thread.sleep(10); } // try catch for the number of milliseconds to refresh
			catch (InterruptedException e) { } // catch error
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {// action listener for button
		String command = e.getActionCommand();
		if (command.equals("Go Back")) 
		{
			mainBoard.showHome((currScore+bombScore));
		}

	}

	public void paint(Graphics g) { // paint Component (not used)
		super.paint(g);
	}

}