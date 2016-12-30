package org.usfirst.frc.team5821.robot.isaiah;
// P.S credit to team3309;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;


public class XboxController extends GenericHID {

	////BUTTONS////
	// Base Buttons
	public static final int BUTTON_A = 1;
	public static final int BUTTON_B = 2;
	public static final int BUTTON_X = 3;
	public static final int BUTTON_Y = 4;
	// DPAD
	public static final int BUTTON_DPAD_UP = 5;
	public static final int BUTTON_DPAD_DOWN = 6;
	public static final int BUTTON_DPAD_LEFT = 7;
	public static final int BUTTON_DPAD_RIGHT = 8;
	// Start and Back
	public static final int BUTTON_START = 8;
	public static final int BUTTON_BACK = 7;
	// Sticks
	public static final int BUTTON_LEFT_STICK = 9;
	public static final int BUTTON_RIGHT_STICK = 10;
    public static final int BUTTON_MAIN_STICK = 16;
    public static final int BUTTON_SECOND_STICK = 17;
    //due to isaiah's OCD
    //  the stick declared as the main joystick is stored in the 16's button array spot
    //  the stick that is the second joystick is stored in the 17's button array spot

	// Bumpers and Home
	public static final int BUTTON_LB = 5;
	public static final int BUTTON_RB = 6;
	public static final int BUTTON_HOME = 15;

	//// AXES ////
	public static final int AXIS_LEFT_X = 0;
	public static final int AXIS_LEFT_Y = 1;
	public static final int AXIS_LEFT_TRIGGER = 2;
	public static final int AXIS_RIGHT_TRIGGER = 3;

	public static final int AXIS_RIGHT_X = 4;
	public static final int AXIS_RIGHT_Y = 5;

	// main instance joystick being called throughout class
	Joystick controller;

	// info for usage of xbox remotes found at -
	// http://www.chiefdelphi.com/forums/showthread.php?t=83597
	// Constructor, takes number and makes xbox remote that number joystick that
	// is set by driver station

    public boolean[] isButtonDown = new boolean[18];
    public boolean[] lastIsButtonDown = new boolean[18];
    public Event[] buttonPressed = new Event[18];
    public Event[] buttonReleased = new Event[18];

	public XboxController(int joystickNum) {
		controller = new Joystick(joystickNum);
        init();
	}
    public void init() {
        //for loop starts at 1 cause there is no button at 0, at least none that i know of.
        for (int i = 1; i <= isButtonDown.length - 1; i++) {
            isButtonDown[i] = false;
            lastIsButtonDown[i] = false;
            buttonPressed[i] = new Event();
            buttonReleased[i] = new Event();
        }
        
    }

    //you need to call the update method every *frame* in order to use the button events
    //
    /*example code for using events, also put below event code somewhere it only runs once.
        xboxController.buttonPressed[XboxController.BUTTON_A].connect(
            new Runnable() {
                @Override
                public void run() {
                    System.out.println("You just Pressed A button");
                }
            }
        );
    */
    public void update() {
        for(int i = 1; i <= isButtonDown.length - 1; i++) {
            int b = i; //raw button
            int c = i;	//mainstick or second stick flippy floppy timmy windy 
            if(i >= 11 && i <= 15) {
            	continue;
            }
            
            if (i >= 16) {
                if(i == BUTTON_MAIN_STICK) {
                    b = main;
                    c = main2;
                } else {
                    b = second;
                    c = second2;
                }
            }
            
    
            boolean isDown = getRawButton(b);
            isButtonDown[i] = isDown;
            if(isButtonDown[i] != lastIsButtonDown[i]) {
                //then the button has either been pressed or realeased and we need to trigger an event
                //check whether it was pressed or released
                if(isDown) {
                    //then it was just pressed
                    buttonPressed[c].trigger();
                } else {
                    //then it was just released
                    buttonReleased[c].trigger();
                }
            }

            //change the last button down to the current one to determine if a button was pressed on the next time
            lastIsButtonDown[i] = isButtonDown[i];
            if(i == BUTTON_MAIN_STICK) {
            	//System.out.println(isButtonDown[i] + " i:" + i + " b:" + b);
            }
        }
    }

	// Now, here are all the button methods, they all return a boolean that
	// returns true if button is pressed (obviously)
	public boolean getA() {
		return controller.getRawButton(BUTTON_A);
	}
	public boolean getB() {
		return controller.getRawButton(BUTTON_B);
	}
	public boolean getXButton() {
		return controller.getRawButton(BUTTON_X);
	}
	public boolean getYButton() {
		return controller.getRawButton(BUTTON_Y);
	}

    public boolean getDpadUp() {
		return controller.getRawButton(BUTTON_DPAD_UP);
	}
	public boolean getDpadDown() {
		return controller.getRawButton(BUTTON_DPAD_DOWN);
	}
	public boolean getDpadLeft() {
		return controller.getRawButton(BUTTON_DPAD_LEFT);
	}
	public boolean getDpadRight() {
		return controller.getRawButton(BUTTON_DPAD_RIGHT);
	}

	public boolean getBack() {
		return controller.getRawButton(BUTTON_BACK);
	}
	public boolean getStart() {
		return controller.getRawButton(BUTTON_START);
	}

	public boolean getLeftStickButton() {
		return controller.getRawButton(BUTTON_LEFT_STICK);
	}
	public boolean getRightStickButton() {
		return controller.getRawButton(BUTTON_RIGHT_STICK);
	}

	public boolean getLB() {
		return controller.getRawButton(BUTTON_LB);
	}
	public boolean getRB() {
		return controller.getRawButton(BUTTON_RB);
	}

	public boolean getHome() {
		return controller.getRawButton(BUTTON_HOME);
	}

	// Next are the methods for getting the Axes, they return a double
	// You may notice that each method has a temp value and a scaledVal.
	// The temp value is what that current axis is at.
	// The scaledVal just takes the temp value and scales it.
	// Chagne the scaleAxis method to add a deadband, or to add a constant
	// multiplier to every axis.
	// Returns from -1 to 1
	public double getLeftX() {
		double temp = controller.getRawAxis(AXIS_LEFT_X);
		double scaledVal = scaleAxis(temp);
		return scaledVal;
	}
	public double getLeftY() {
		double temp = controller.getRawAxis(AXIS_LEFT_Y);
		double scaledVal = scaleAxis(temp);
		return scaledVal;
	}
    public double getRightX() {
        double temp = controller.getRawAxis(AXIS_RIGHT_X);
        double scaledVal = scaleAxis(temp);
        return scaledVal;
    }
    public double getRightY() {
        double temp = controller.getRawAxis(AXIS_RIGHT_Y);
        double scaledVal = scaleAxis(temp);
        return scaledVal;
    }

	public double getRightTrigger() {
		double temp = controller.getRawAxis(AXIS_RIGHT_TRIGGER);
		double scaledVal = scaleAxis(temp);
		return scaledVal;
	}
	public double getLeftTrigger() {
		double temp = controller.getRawAxis(AXIS_LEFT_TRIGGER);
		double scaledVal = scaleAxis(temp);
		return scaledVal;
	}	

	// btw this is your deadband
	static final double DEADBAND = .1;

	// here is the scaling method used in axes
	private double scaleAxis(double value) {
		// if the joystick is not pressed enough, don't move. We dont like
		// crawling spider robots.
		if (Math.abs(value) < DEADBAND) {
			return 0;
		}
		// this spot would be where you could scale stuff
		return value;
	}

	// required to work
	public double getX(Hand hand) {
		if (hand.equals(Hand.kLeft)) {
			return getLeftX();
		} else {
			return getRightX();
		}
	}

	public double getY(Hand hand) {
		if (hand.equals(Hand.kLeft)) {
			return getLeftY();
		} else {
			return getRightY();
		}
	}

	public double getZ(Hand hand) {
		if (hand.equals(Hand.kLeft)) {
			return 0;
		} else {
			return getLeftTrigger() - getRightTrigger();
		}
	}

	public double getTwist() {
		return 0;
	}
	public double getThrottle() {
		return 0;
	}
	public double getRawAxis(int i) {
		return controller.getRawAxis(i);
	}
    public double getScaledAxis(int i) {
        return scaleAxis(getRawAxis(i));
    }
	public boolean getTrigger(Hand hand) {
		if (hand.equals(Hand.kLeft)) {
			return getLB();
		} else {
			return getRB();
		}
	}
	public boolean getTop(Hand hand) {
		return false;
	}
	public boolean getBumper(Hand hand) {
		if (hand.equals(Hand.kLeft)) {
			return getLB();
		} else {
			return getRB();
		}
	}
	public boolean getRawButton(int i) {
		return controller.getRawButton(i);
	}

	@Override
	public int getPOV(int pov) {
		// TODO Auto-generated method stub
		return 0;
	}

    //extra extra, super duper special isaiah ballah has OCD and needs everything how he likes it code//
    //adding a bit more funky functionality for my own liking ;d
    int mainX = 0;
    int mainY = 1;
    int main = 9;
    int main2 = 16;
    
    int secondX = 4;
    int secondY = 5;
    int second = 10;
    int second2 = 17;
    
    int temp, tempX, tempY, temp2;

    //xbox.button

    public void switchMainJoystick() {
        tempX = mainX;
        tempY = mainY;
        temp = main;

        mainX = secondX;
        mainY = secondY;
        main = second;

        secondX = tempX;
        secondY = tempY;
        second = temp;
    }

    public double getMainX() {
        return getScaledAxis(mainX);
    }
    public double getMainY() {
        return getScaledAxis(mainY);
    }
    
    
    public double getSecondX() {
        return getScaledAxis(secondX);
    }
    public double getSecondY() {
        return getScaledAxis(secondY);
    }

}// END OF CLASS