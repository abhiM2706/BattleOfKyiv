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
import java.awt.CardLayout;
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

public class PlayAir extends JPanel implements Runnable,  KeyListener, ActionListener { // this class runs the entire game

	long scoreGame=0;
	boolean keepAdding=true;
	JLabel scores = new JLabel();
	JButton retry = new JButton();
	JButton backButton = new JButton();
	JLabel failed=new JLabel("");
	JLabel success=new JLabel("");
	boolean stopShooting=false;
	int shotDown=0;
	boolean tankShotLeft=false;
	boolean tankShotRight=false;
	JLabel bloodFailed=new JLabel();
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
	String imagePath = "C:\\Users\\abhin\\OneDrive\\Desktop\\game\\";
	int xLoc=100;
	int xLocLeft=1000;
	int yLoc=600;
	int cnt=0;
	int cnt1=0;
	JLabel currHealth, currHealthLeft,currHealthPlane,currFuelPlane,currFuel;
	int fuel=100;
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
	int yLocJet =150;

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
	int planeHealth=36;
	int bombDamage;
	int currBallX;
	int levelGame;
	double levelMultiplier;
	JLabel tankLabel = null;
	JLabel tankLabelLeft =null;

	JLabel nozzleRight=null,nozzleLeft=null;

	
	JLabel lives = new JLabel("Lives: ");
	JLabel healthTank = new JLabel("Health remaining: "+tankHealth);
	int bombDropping;
	JLabel roadImage,background,crackWall1,crackWall2;
	List<Integer>health = new ArrayList<Integer>();
	JLabel jets=null;
	ImageIcon jetter=new ImageIcon(),jetter1=new ImageIcon();

	JLabel turretF1, turretF;

	JPanel popUp;

	int currPlaneSpeed=2;
	boolean regSpeed=false;
	GameContainerPanel mainBoard;


	public PlayAir(GameContainerPanel gameContainerPanel) // this constructor initializes some of the variables 
	{
		this.mainBoard = gameContainerPanel;
		setPreferredSize(new Dimension(1300, 800));
		setLayout(new BorderLayout());
		setVisible(true);
		Image jetsImg1 = new ImageIcon(imagePath + "jetFightLeft.png").getImage();
		jetter1.setImage(jetsImg1);

		Image jetsImg = new ImageIcon(imagePath + "jetFight.png").getImage();
		jetter.setImage(jetsImg);



		h1 = new ImageIcon();
		Image healthA = new ImageIcon(imagePath + "healthFull.png").getImage();
		h1.setImage(healthA);
		currHealth = new JLabel(h1);
		currHealthLeft = new JLabel(h1);
		currHealthPlane = new JLabel(h1);


		ImageIcon f1 = new ImageIcon();
		Image fuel = new ImageIcon(imagePath + "stamina.png").getImage();
		f1.setImage(fuel);
		currFuelPlane = new JLabel(f1);

		ImageIcon f2 = new ImageIcon();
		f2.setImage(new ImageIcon(imagePath + "yellowBack.png").getImage());
		currFuel = new JLabel(f2);

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
		stats.setBackground(new Color(91,127,255));
		stats.setPreferredSize(new Dimension(1200,80));
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

		lives.setBounds(1000,10,100,50);
		lives.setForeground(Color.RED);

		lives.setFont(theFont);
		stats.setBounds(0, 0, 1200, 200);

		stats.add(lives);

		add(stats,BorderLayout.NORTH);

		currBallX = (int)(Math.random()*100+10);
		levelGame = 3; //set to 1 for testing purposes

		ImageIcon road = new ImageIcon();
		Image r = new ImageIcon(imagePath + "road.png").getImage();
		road.setImage(r);
		roadImage = new JLabel(road);
		roadImage.setBounds(0,500,1300,183);

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

		
			currLevel = new JLabel("City of Donobas");
			currLevel.setFont(theFont);
			levelMultiplier = 1;
			bombDamage=1;
			bombDropping = 4;
		
		currLevel.setBounds(100,10,200,50);
		stats.add(currLevel);

		board.add(currHealth);
		board.add(currHealthLeft);
		board.add(currHealthPlane);
		board.add(currFuelPlane);
		board.add(currFuel);
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
	public void resetVars()
	{
		retry = new JButton();
		failed=new JLabel("");
		success=new JLabel("");
		stopShooting=false;
		shotDown=0;
		tankShotLeft=false;
		tankShotRight=false;
		bloodFailed=new JLabel();
		theFont = new Font("TimesRoman",Font.BOLD,24);



		counter = 100;


		canShoot=true;
		howManyShoot=0;

		//Current Score

		currScore=0;


		nozzleAdded=0;


		capitalHealth=5;
		xLoc=100;
		xLocLeft=1000;
		yLoc=500;
		cnt=0;
		cnt1=0;
		fuel=100;
		currAngle=90.0;
		currAngleLeft=90.0;

		whichWayRight=false;
		whichWayLeft =true;
		speedRight=6;
		speedLeft=-6;

		whichWayRightJet=true;

		speedRightJet=6;
		xLocJet=800;
		yLocJet =150;

		arrowNewleft=null;arrowNewRight=null;

		//List<Integer> health = new ArrayList<Integer>();

		s = new JLabel("");
		bull = new ImageIcon("").getImage();
		tankHealth = 12;
		tankHealthLeft=12;
		planeHealth=3;
		tankLabel = null;
		tankLabelLeft =null;
		nozzleRight=null;nozzleLeft=null;
		jets=null;
		jetter=new ImageIcon();
		jetter1=new ImageIcon();


		currPlaneSpeed=2;
		regSpeed=false;
		board.removeAll();
		board.repaint();
		remove(board);
		board = new JPanel();

		//------------------------------------------------------------------------
		Image jetsImg1 = new ImageIcon(imagePath + "jetFightLeft.png").getImage();
		jetter1.setImage(jetsImg1);

		Image jetsImg = new ImageIcon(imagePath + "jetFight.png").getImage();
		jetter.setImage(jetsImg);

		h1 = new ImageIcon();
		Image healthA = new ImageIcon(imagePath + "healthFull.png").getImage();
		h1.setImage(healthA);
		currHealth = new JLabel(h1);
		currHealthLeft = new JLabel(h1);
		currHealthPlane = new JLabel(h1);

		ImageIcon f1 = new ImageIcon();
		Image fuel = new ImageIcon(imagePath + "stamina.png").getImage();
		f1.setImage(fuel);
		currFuelPlane = new JLabel(f1);

		ImageIcon f2 = new ImageIcon();
		f2.setImage(new ImageIcon(imagePath + "yellowBack.png").getImage());
		currFuel = new JLabel(f2);

		h2 = new ImageIcon();
		healthA = new ImageIcon(imagePath + "health3.png").getImage();
		h2.setImage(healthA);

		h3= new ImageIcon();
		healthA = new ImageIcon(imagePath + "health2.png").getImage();
		h3.setImage(healthA);

		h4= new ImageIcon();
		healthA = new ImageIcon(imagePath + "health1.png").getImage();
		h4.setImage(healthA);

		board.setLayout(null);
		stats = new JPanel();
		stats.setLayout(null);
		stats.setBackground(new Color(91,127,255));
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


		lives.setBounds(1000,10,100,50);
		lives.setForeground(Color.RED);
		lives.setFont(theFont);
		stats.setBounds(0, 0, 1000, 150);

		stats.add(lives);

		add(stats,BorderLayout.NORTH);

		currBallX = (int)(Math.random()*100+10);
		levelGame = (int)(Math.random()*3+1); //set to 1 for testing purposes

		ImageIcon road = new ImageIcon();
		Image r = new ImageIcon(imagePath + "road.png").getImage();
		road.setImage(r);
		roadImage = new JLabel(road);
		roadImage.setBounds(0,550,1200,183);

		ImageIcon back = new ImageIcon();
		Image b = new ImageIcon(imagePath + "backgroundPlay.png").getImage();
		back.setImage(b);
		background = new JLabel(back);
		background.setBounds(0,0,1200,800);

		ImageIcon turr = new ImageIcon();
		Image t = new ImageIcon(imagePath + "quarterLeft.png").getImage();
		turr.setImage(t);
		turretF = new JLabel(turr);
		turretF.setBounds(0,600,150,200);
		board.add(turretF);

		ImageIcon turr1 = new ImageIcon();
		Image t1 = new ImageIcon(imagePath + "quarterRight.png").getImage();
		turr1.setImage(t1);
		turretF1 = new JLabel(turr1);
		turretF1.setBounds(1050,600,150,200);
		board.add(turretF1);


		currLevel = new JLabel("City of Donobas");
		currLevel.setFont(theFont);
		levelMultiplier = 1;
		bombDamage=1;
		bombDropping = 4;

		currLevel.setBounds(100,10,200,50);
		stats.add(currLevel);

		board.add(currHealth);
		board.add(currHealthLeft);
		board.add(currHealthPlane);
		board.add(currFuelPlane);
		board.add(currFuel);

		cnt=0;
		cnt1=0;


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

	public void keyTyped(KeyEvent e) {
		// this method is for when a key is typed on the key board

	}

	@Override
	public void keyPressed(KeyEvent e) { // this method is for when a key is pressed on the key board

		switch (e.getKeyCode())
		{

		case KeyEvent.VK_W:
		{

			if(jets.getY()> 0)
			{
				jets.setBounds(jets.getX(),jets.getY()-14,200,80);
				break;
			}
		}
		case KeyEvent.VK_S:
		{
			if(jets.getY()<500)
			{
				jets.setBounds(jets.getX(),jets.getY()+14,200,80);
				break;
			}

		}
		}
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_LEFT:
		{
			if(regSpeed)
			{
				break;
			}
			if(currPlaneSpeed>2)
			{
				currPlaneSpeed-=2;
			}
			break;
		}
		case KeyEvent.VK_RIGHT:
		{
			if(regSpeed)
			{
				break;
			}
			if(currPlaneSpeed<18)
			{
				currPlaneSpeed+=2;

			}

			break;
		}

		}
	}

	public void keyReleased(KeyEvent e) { // this method is for when a key is released on the key board
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_DOWN:
		{
			if(stopShooting) break;
			howManyShoot++;
			if(howManyShoot>5)
			{
				canShoot=false;
			}
			else {
				canShoot=true;
			}
			clipNameClip.setFramePosition(0);
			clipNameClip.start();

			JLabel currentBullet=new JLabel();
			if(canShoot)
			{

				ImageIcon explodeIcon = new ImageIcon();
				Image explode = new ImageIcon(imagePath + "bomb.png").getImage();
				explodeIcon.setImage(explode);
				currentBullet.setIcon(explodeIcon);
				currentBullet.setBounds(jets.getX()+100,jets.getY(),50,50);

				speed.add((int)(Math.random()*3+1));
				board.add(currentBullet);
				drones.add(currentBullet);
			}
			break;
		}
		}

	}

	public boolean isHit(int currentBulletIndex) // this checks whether the bullet has a hit a bomb or plane
	{
		if(stopShooting)
			return false;
		boolean hit =false;

		JLabel currentBullet = bullets.get(currentBulletIndex);

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
						//explodeIcon.setImage(explode);
						currentBullet.setIcon(explodeIcon);
						currentBullet.setBounds(currentBullet.getX(),currentBullet.getY(),25,25);
						if((currentBullet.getY()>=droneToCheck.getY())&&(currentBullet.getY()<=droneToCheck.getY()+40))
						{
							hit=true;
							drones.remove(i);
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
		JLabel droneToCheck = jets;
		/*
		 * Check whether current bullet is between drone X coord and Y coord
		 */
		if(((currentBullet.getX()>=droneToCheck.getX()) && (currentBullet.getX()<=droneToCheck.getX()+200)) 
				|| ((currentBullet.getX()+25>=droneToCheck.getX()) && (currentBullet.getX()+25<=droneToCheck.getX()+200)))
		{
			if((currentBullet.getY()>=droneToCheck.getY())&&(currentBullet.getY()<=droneToCheck.getY()+40))
			{
				hit=true;
				planeHealth--;
			}
		}
		return hit;
	}
	public boolean tankHit(JLabel bomb) // this checks whether the tanks have been hit by bombs
	{
		if(stopShooting)
			return false;
		boolean hitTank =false;
		JLabel currTank = tankLabel;
		if(!tankShotRight)
			if((bomb.getX()>=currTank.getX() && bomb.getX()<=currTank.getX()+125) || (bomb.getX()+35>=currTank.getX() && bomb.getX()+35<=currTank.getX()+125))
			{
				if((currTank.getY()>=bomb.getY()) && (currTank.getY()<=bomb.getY()+49))
				{
					hitTank=true;
					tankHealth--;
					currScore++;

				}
			}
		currTank = tankLabelLeft;
		if(!tankShotLeft)
			if((bomb.getX()>=currTank.getX() && bomb.getX()<=currTank.getX()+125) || (bomb.getX()+35>=currTank.getX() && bomb.getX()+35<=currTank.getX()+125))
			{
				if((currTank.getY()>=bomb.getY()) && (currTank.getY()<=bomb.getY()+49))
				{
					hitTank=true;
					tankHealthLeft--;
					currScore++;

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
			//System.out.print(false);
			fuel-=(currPlaneSpeed-2)/2;
			if(cnt1==0)
			{

				jets = new JLabel(jetter1);
				jets.setBounds(800,50,200,80);
				board.add(jets);
				cnt1++;
			}
			if(jets.getY()<500)
				jets.setBounds(jets.getX(),jets.getY()+1,200,80);

			if(jets.getY()>=500 || jets.getY()<=10)
			{
				planeHealth--;
			}

			if(fuel<100)
			{
				fuel+=2;
				regSpeed=false;
			}
			if(fuel<=0)
			{
				regSpeed=true;
				currPlaneSpeed=2;
			}
			currFuel.setBounds(jets.getX()+60,jets.getY()+90,fuel,15);
			currFuelPlane.setBounds(jets.getX()+40,jets.getY()+80,130,40);
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
				arrowNewleft.setBounds(turretF.getX()+100,yLoc-50,100,72);

				arrowNewRight = new JLabel("");
				board.add(arrowNewRight);
				arrowNewRight.setBounds(turretF1.getX()+10,yLoc-50,100,72);

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

				retry = new JButton("Retry");
				retry.setFont(theFont);
				retry.setForeground(Color.BLACK);
				retry.setBounds(450,300,300,50);
				retry.setOpaque(true);
				scores.setFont(theFont);
				scores.setForeground(Color.BLACK);
				scores.setBounds(450,300,300,50);
				scores.setOpaque(true);
				scores.setVisible(false);
				retry.addActionListener(e -> resetVars());
				retry.addActionListener(this);

				backButton = new JButton("Go Back");
				backButton.setFont(theFont);
				backButton.setForeground(Color.BLACK);
				backButton.setBounds(450,300,300,50);
				backButton.setOpaque(true);
				backButton.addActionListener(this);
				backButton.setVisible(false);

				backButton.setBounds(450,400,300,50);

				board.add(failed);
				failed.setBounds(400,150,400,225);
				failed.setVisible(false);


				board.add(retry);
				board.add(scores);
				board.add(backButton);
				retry.setVisible(false);

				board.add(success);
				success.setBounds(400,150,400,225);
				success.setVisible(false);

				board.add(bloodFailed);
				bloodFailed.setBounds(330,150,550,406);
				bloodFailed.setVisible(false);

				currFuel.setBounds(jets.getX()+60,jets.getY()+90,100,15);

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
			if(tankHealth==0)
			{
				if(cnt==1)
				{
					currScore+=1000;
					cnt++;
				}


				currHealth.setVisible(false);
				tankLabel.setVisible(false);
				board.remove(tankLabel);
				board.remove(currHealth);

				shotDown++;
				tankShotRight=true;
				if(tankShotLeft && tankShotRight)
				{
					currFuel.setVisible(false);
					board.remove(currFuel);
					currFuelPlane.setVisible(false);
					board.remove(currFuelPlane);
					keepAdding=false;
					scores.setVisible(true);
					scores.setText("Score: "+currScore+"");



					scores.setBackground(new Color(0,0,0,0));
					stopShooting=true;
					success.setVisible(true);
					bloodFailed.setVisible(true);
					backButton.setVisible(true);
					stop();
					break;
				}
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
			if(tankHealthLeft==0)
			{
				if(cnt1==1)
				{
					currScore+=1000;
					cnt1++;
				}

				currHealthLeft.setVisible(false);

				board.remove(tankLabelLeft);
				board.remove(currHealthLeft);	
				tankShotLeft=true;
				scores.setBackground(new Color(0,0,0,0));

				if(tankShotLeft && tankShotRight)
				{
					currFuel.setVisible(false);
					board.remove(currFuel);
					currFuelPlane.setVisible(false);
					board.remove(currFuelPlane);
					scores.setVisible(true);
					scores.setText("Score: "+currScore+"");	
					keepAdding = false;
					stopShooting=true;
					success.setVisible(true);
					bloodFailed.setVisible(true);
					backButton.setVisible(true);
					stop();
					break;
				}
			}
			if(planeHealth>27)
				currHealthPlane.setIcon(h1);

			else if(planeHealth<=27 && planeHealth>18)
			{
				currHealthPlane.setIcon(h2);
			}
			else if(planeHealth<=18 && planeHealth>9)
			{
				currHealthPlane.setIcon(h3);
			}
			else if(planeHealth<=9 && planeHealth>0)
			{
				currHealthPlane.setIcon(h4);
			}

			if(planeHealth==0)
			{
				scores.setVisible(true);
				scores.setText("Score: "+currScore+"");
				currHealthPlane.setVisible(false);
				jets.setVisible(false);
				scores.setBackground(new Color(0,0,0,0));
				board.remove(jets);
				board.remove(currHealthPlane);
				keepAdding = false;
				bloodFailed.setVisible(true);
				failed.setVisible(true);
				stopShooting=true;
				backButton.setVisible(true);
				currFuel.setVisible(false);
				currFuelPlane.setVisible(false);
				board.remove(currFuel);
				board.remove(currFuelPlane);
				stop();
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
				for(int i=0;i<dropBombLeft.size();i++)
				{
					if((xLoc>=dropBombLeft.get(i)-3)&&(xLoc<=dropBombLeft.get(i)+3))
					{
						JLabel bulletNew = new JLabel(setbulletRotate((double)Math.toDegrees(Math.atan((jets.getX()-turretF.getX())/Math.abs(turretF.getY()-jets.getY()+0.0000000001)))));
						board.add(bulletNew);
						arrowNewleft.setIcon(setarrowRotate((double)Math.toDegrees(Math.atan((jets.getX()-turretF.getX())/Math.abs(turretF.getY()-jets.getY()+0.0000000001)))));
						arrowNewleft.setBounds(turretF.getX()+80,yLoc,100,72);
						bulletNew.setBounds(turretF.getX()+80,yLoc-10,35,99);
						bullets.add(bulletNew);
						xTravel.add((double)Math.abs(turretF.getX()-jets.getX())/Math.abs(turretF.getY()-jets.getY()));
						yTravel.add(1.0);
						dropBombLeft.remove(i);
					}
				}
				if(whichWayRight==false && xLoc<50)
				{
					whichWayRight =true;
					speedRight=3;
					dropBombLeft.clear();
					int numOfBombs =(int)(Math.random()*7+2);
					for(int z=0;z<numOfBombs;z++)
					{
						dropBombLeft.add((int)(Math.random()*1000+50));
					}
				}
				else if(whichWayRight==true && xLoc>1000)
				{
					whichWayRight =false;
					speedLeft=-3;
					dropBombLeft.clear();
					int numOfBombs =(int)(Math.random()*7+2);
					for(int z=0;z<numOfBombs;z++)
					{
						dropBombLeft.add((int)(Math.random()*1000+50));
					}
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
					if((xLocLeft>=dropBomb.get(i)-3)&&(xLocLeft<=dropBomb.get(i)+3))
					{
						JLabel bulletNew = new JLabel(setbulletRotate((double)Math.toDegrees(Math.atan((jets.getX()-turretF1.getX())/Math.abs(turretF1.getY()-jets.getY()+0.0000000000001)))));
						arrowNewRight.setIcon(setarrowRotate((double)Math.toDegrees(Math.atan((jets.getX()-turretF1.getX())/Math.abs(turretF1.getY()-jets.getY()+0.0000000000001)))));
						arrowNewRight.setBounds(turretF1.getX()+10,yLoc-10,100,72);
						board.add(bulletNew);
						bulletNew.setBounds(turretF1.getX()+20,yLoc+10,35,99);
						bullets.add(bulletNew);
						xTravel.add((double)(jets.getX()-turretF1.getX())/Math.abs(turretF1.getY()-jets.getY()+1));
						yTravel.add(1.0);
						dropBomb.remove(i);
					}
				}
				if(whichWayLeft==false && xLocLeft<50)
				{
					whichWayLeft =true;
					xLocLeft+=3;
					dropBomb.clear();
					int numOfBombs =(int)(Math.random()*7+2);
					for(int z=0;z<numOfBombs;z++)
					{
						dropBomb.add((int)(Math.random()*1000+50));
						//System.out.print(dropBomb.get(z)+" ");
					}
				}
				else if(whichWayLeft==true && xLocLeft>1000)
				{
					whichWayLeft =false;
					xLocLeft-=3;
					dropBomb.clear();
					int numOfBombs =(int)(Math.random()*7+2);
					for(int z=0;z<numOfBombs;z++)
					{
						dropBomb.add((int)(Math.random()*1000+50));
					}
				}
				tankLabel.setBounds((xLoc),yLoc-50,125,110);
				tankLabelLeft.setBounds((xLocLeft),yLoc-50,125,110);


				currHealthPlane.setBounds(jets.getX()+50,jets.getY(),100,27);
				currHealth.setBounds(xLoc,yLoc+20,100,27);
				currHealthLeft.setBounds(xLocLeft,yLoc+20,100,27);

				if(whichWayRightJet)
				{
					xLocJet+=currPlaneSpeed;
				}
				else 
				{
					xLocJet-=currPlaneSpeed;
				}
				if(whichWayRightJet==false && xLocJet<0)
				{
					whichWayRightJet =true;
					howManyShoot=0;
					jets.setIcon(jetter1);
				}
				else if(whichWayRightJet==true && xLocJet>1000)
				{
					whichWayRightJet =false;
					howManyShoot=0;
					jets.setIcon(jetter);
				}
				jets.setBounds(xLocJet,jets.getY(),200,80);

				for (int i=0; i < bullets.size(); i++)
				{
					JLabel b = bullets.get(i);
					b.setBounds((int) (b.getX()+(8*xTravel.get(i))),(int) (b.getY()-(8*yTravel.get(i))),66,80);

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
						speed.remove(i);
						drone.setVisible(false);
					}
				}

				lives.setText("Lives: "+planeHealth+"");
				lives.setFont(theFont);
				s.setFont(theFont);
				s.setBounds(810,-15,500,100);
				stats.add(s);
				board.add(roadImage);
				board.add(background);

				repaint();
				if(keepAdding)currScore+=1;
				if(currScore%10==0)
				{
					s.setText("Score: "+currScore+"");
				}


			}

			try { Thread.sleep(10); } // try catch for the number of milliseconds to refresh
			catch (InterruptedException e) { } // catch error
		}
	}

	public void paint(Graphics g) { // paint Component (not used)
		super.paint(g);
		//g.drawString(score+"",10,50);
		//g.setFont(theFont);
		//g.drawString(theDate.toString(),10,50);
	}

	@Override
	public void actionPerformed(ActionEvent e) {//for JButtons when clicked
		String command = e.getActionCommand();
		if (command.equals("Go Back")) 
		{
			mainBoard.showHome(currScore);
		}

	}

}