
package org.usfirst.frc.team5821.robot;

import org.usfirst.frc.team5821.robot.isaiah.XboxController;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
//todo smooth acceleration
//todo smooth steering
//todo grab ball/eject ball
//todo extend piston
//todo unextend piston


public class Robot extends SampleRobot {

	final XboxController xbox;
	final Robot robot;

	Joystick Joystick;
	SpeedController motor;
	SpeedController motor2;
	SpeedController motor3;
	SpeedController motor4;
	
  //Movement control variables
  public static ControlMode controlMode = ControlMode.Normal;
  boolean forward = false;
  double turbo = 0;// from 0 - 1;
  int turboNotches = 3;
  double turboBoost = 1.0 / turboNotches;
  
  double saftey = 1;

  public Robot() {
		xbox = new XboxController(0);
		
		motor = new Talon(2); //right
		motor2 = new Talon(3); //right
		motor3 = new Victor(0); //left
		motor4 = new Victor(1); //left
		
		createEventListeners();
		robot = this;
	}


  @Override
  public void autonomous() {
	while(isAutonomous() && isEnabled()){
		Timer.delay(0.005);
		updateAutonomous();
	}
  }


  public void updateAutonomous() {
	double speed = 0.6;
	if(Timer.getMatchTime() < 5);
	else if(Timer.getMatchTime() < 10)
		speed = -speed;
	else if(Timer.getMatchTime() < 15);
	motor.set(-speed);
	motor2.set(-speed);
	motor3.set(speed);
	motor4.set(speed);
  }


  public void operatorControl() {
		while (isOperatorControl() && isEnabled()) {
			Timer.delay(0.005); // wait update time
			update();
		}
	}
	public void update() {
		xbox.update();
      updateControls();
	}

	public void updateControls() {
      if(controlMode == ControlMode.Normal)
          normalControlls();
	}
	public void normalControlls() {

      double divisor = 3;
      double rotationDivisor = divisor;
      double dd = divisor * turbo;

      divisor = (divisor - dd) + 1;
      double x = xbox.getMainX();
      double y = xbox.getMainY();
      double m1 = 0;
      double m2 = 0;
      if(Math.abs(x) < 0.1)x = 0;
      m1 = -y + x;
      m2 = y + x;
      
      double speed1 = m1 *saftey;
      double speed2 = m2 *saftey;
      motor.set(speed2);
      motor2.set(speed2);
      motor3.set(speed1);
      motor4.set(speed1);
	}

	public void straightControls() {
		
	}
	
	public void createEventListeners() {
	
      xbox.buttonPressed[XboxController.BUTTON_START].connect(new Runnable() {
          @Override
          public void run() {
              robot.saftey = 1;
          }
      });
      xbox.buttonPressed[XboxController.BUTTON_BACK].connect(new Runnable() {
          @Override
          public void run() {
              robot.saftey = 0;
          }
      });
      xbox.buttonPressed[XboxController.BUTTON_RB].connect(new Runnable() {
          @Override
          public void run() {
              //Increase Turbo if normal controls, button_b and pressed right_bumper
              if (xbox.isButtonDown[XboxController.BUTTON_B]) {
                  if(Robot.controlMode == ControlMode.Normal)
                      turbo = Math.min(1, turbo + turboBoost);
              }
              
              //Increase turbo all the way
              if (xbox.isButtonDown[XboxController.BUTTON_A]) {
                  if(Robot.controlMode == ControlMode.Normal)
                      turbo = 1;
              }
          }
      });

      xbox.buttonPressed[XboxController.BUTTON_LB].connect(new Runnable() {
          @Override
          public void run() {
              //Decrease Turbo if normal controls, button_b and pressed left_bumper
              if (xbox.isButtonDown[XboxController.BUTTON_B]) {
                  if(Robot.controlMode == ControlMode.Normal)
                      turbo = Math.max(0, turbo - turboBoost);
              }
              
              //Remove all turbo
              if (xbox.isButtonDown[XboxController.BUTTON_A]) {
                  if(Robot.controlMode == ControlMode.Normal)
                      turbo = 0;
              }
          }
      });

      xbox.buttonPressed[XboxController.BUTTON_MAIN_STICK].connect(new Runnable() {
          @Override
          public void run() {
              //swtich forward and backwards
              forward = !forward;
              System.out.println("main joy stick pressed");
          }
      });
      xbox.buttonReleased[XboxController.BUTTON_SECOND_STICK].connect(new Runnable() {
          @Override
          public void run() {
              //switch main and secondary joysticks
        	  System.out.println("second stick pressed");
              xbox.switchMainJoystick();
          }
      });
  }
	


}