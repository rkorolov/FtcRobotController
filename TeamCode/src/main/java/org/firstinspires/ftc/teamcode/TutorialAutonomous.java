package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Tutorial Autonomous")

public class TutorialAutonomous extends LinearOpMode {

    //Declare motors
    DcMotor frontRight = null;
    DcMotor frontLeft = null;
    DcMotor backRight = null;
    DcMotor backLeft = null;

    @Override
    public void runOpMode() throws InterruptedException {
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");

        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        //Robot Instructions

        //get the robot to drive forward
        driveForwardTime(-0.5, 4000);

        //get cone number from sensor
        int collectedConeNumber = 0;
        getConeNumber(collectedConeNumber);

        if (collectedConeNumber == 1) {
            goToPositionOne();
        }

        else if (collectedConeNumber == 2) {
            goToPositionTwo();
        }

        else if (collectedConeNumber == 3) {
            goToPositionThree();
        }

        else {
            //print error message
        }

    }

    //get the robot to drive forward (1/3 chance of parking in correct spot)
    public void driveForward(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(power);

    }

    public void driveForwardTime(double power, long time) throws InterruptedException {
        driveForward(power);
        Thread.sleep(time);
    }

    //identify correct cone side from custom sleeve
    public int getConeNumber(int coneNumber) {
        //if sensor identifies side 1
            //coneNumber == 1;

        //else if sensor identifies side 2
            //coneNumber == 2;

        //else if sensor identifies side 3
            //coneNumber == 3;

        //else
            //cone must've not identified correctly
            //print error message

        return coneNumber;
    }

    //Location 1 is identified
    //position 1 is to the left of the starting position
    public void goToPositionOne() {
        //go forward 1 block
        //strafe left 1 block
    }

    //Location 2 is identified
    //position 2 is in front of the starting position
    public void goToPositionTwo() {
        //go forward 1 block
    }

    //Location 3 is identified
    //position 3 is to the right of the starting position
    public void goToPositionThree() {
        //go forward 1 block
        //strafe right 1 block
    }

}


