package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import com.qualcomm.robotcore.hardware.Servo;


import org.openftc.easyopencv.OpenCvCamera;




/*import com.qualcomm.robotcore.hardware.DigitalChannel;
=======
/*import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
>>>>>>> 89e938da1597b612558dcfc78345ebbb357e6ab1
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;

import org.openftc.easyopencv.OpenCvCamera;*/

@TeleOp
public class experimentalDriverControl extends LinearOpMode {

    //initialize motors
    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;


    private DcMotor armControl;
    private Servo grabberControl;

    //init sensors/camera
    OpenCvCamera camera;


    @Override
    public void runOpMode() {

        //init motors
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");


        armControl = hardwareMap.get(DcMotor.class, "armControl");
        grabberControl = hardwareMap.get(Servo.class, "grabberControl");

        double frontLeftPower;
        double backLeftPower;
        double frontRightPower;
        double backRightPower;


        float armControlPower = 0;
        float grabberControlPower = 0;


        //double check which motors are reversed, assumption is right-side
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

    /*
    frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    telemetry.addData("Starting at",  "%7d :%7d",
            frontLeft.getCurrentPosition(),
            backLeft.getCurrentPosition(),
            frontRight.getCurrentPosition(),
            backRight.getCurrentPosition());

     */


        telemetry.addLine("Init Done");

        //double check which motors are reversed, assumption is right-side
    /*
    frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
    frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

    frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

     */

        telemetry.addLine("Initialized");


        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");


            // Things to Change;
            //

            //Original Code
            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

        /*
        if(gamepad1.a == true){
            while(variable for posistion x > value for range || x < value for range){
                set power of wheels such that the robot will strafe left or right based on position
            }
            repeat while loop for y position
        }
        */


            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

            frontLeftPower = (y + x + rx) / denominator;
            backLeftPower = (y - x + rx) / denominator;
            frontRightPower = (y - x - rx) / denominator;
            backRightPower = (y + x - rx) / denominator;

            if (gamepad1.dpad_left) {
                frontLeftPower += -0.3;
                backLeftPower += -0.3;
                frontRightPower += 0.3;
                backRightPower += 0.3;
            }
            if (gamepad1.dpad_right) {
                frontLeftPower += 0.3;
                backLeftPower += 0.3;
                frontRightPower += -0.3;
                backRightPower += -0.3;
            }
            if (gamepad1.dpad_up) {
                frontLeftPower += 0.3;
                backLeftPower += 0.3;
                frontRightPower += 0.3;
                backRightPower += 0.3;
            }
            if (gamepad1.dpad_down) {
                frontLeftPower += -0.3;
                backLeftPower += -0.3;
                frontRightPower += -0.3;
                backRightPower += -0.3;

                frontLeftPower = (y + x + rx) / denominator;
                backLeftPower = (y - x + rx) / denominator;
                frontRightPower = (y - x - rx) / denominator;
                backRightPower = (y + x - rx) / denominator;

                if (gamepad1.dpad_left) {
                    frontLeftPower += -0.1;
                    backLeftPower += -0.1;
                    frontRightPower += 0.1;
                    backRightPower += 0.1;
                }
                if (gamepad1.dpad_right) {
                    frontLeftPower += 0.1;
                    backLeftPower += 0.1;
                    frontRightPower += -0.1;
                    backRightPower += -0.1;
                }
                if (gamepad1.dpad_up) {
                    frontLeftPower += 0.1;
                    backLeftPower += 0.1;
                    frontRightPower += 0.1;
                    backRightPower += 0.1;
                }
                if (gamepad1.dpad_down) {
                    frontLeftPower += -0.1;
                    backLeftPower += -0.1;
                    frontRightPower += -0.1;
                    backRightPower += -0.1;

                }
                if (gamepad1.right_bumper) {
                    frontLeftPower += 1.6;
                    backLeftPower += 1.6;
                    frontRightPower += 1.6;
                    backRightPower += 1.6;
                }
                if (gamepad1.left_bumper) {
                    frontLeftPower += -1.6;
                    backLeftPower += -1.6;
                    frontRightPower += -1.6;
                    backRightPower += -1.6;

                    armControlPower = gamepad2.right_trigger - gamepad2.left_trigger;
                    if (gamepad2.x && grabberControl.getPosition() != 1) {
                        grabberControl.setPosition(.7);
                    }
                    if (gamepad2.a && grabberControl.getPosition() != 0) {
                        grabberControl.setPosition(0);
                    }


                    //*0.5 to set more reasonable speed
                    frontRight.setPower(frontRightPower * 0.5);
                    frontLeft.setPower(frontLeftPower * 0.5);
                    backRight.setPower(backRightPower * 0.5);
                    backLeft.setPower(backLeftPower * 0.5);


                    armControl.setPower(armControlPower);


                    //last line
                    telemetry.addData("Front Right Position",
                            //frontLeft.getCurrentPosition(),
                            frontRight.getCurrentPosition());
                    //backRight.getCurrentPosition());


                    telemetry.addData("Front Right Speed",
                            frontRight.getPower());
                    telemetry.addData("Front Left Speed",
                            frontLeft.getPower());
                    telemetry.addData("Back Right Speed",
                            backRight.getPower());
                    telemetry.addData("Back Left Speed",
                            backLeft.getPower());
                    telemetry.addData("Arm Control Speed",
                            armControl.getPower());

                    telemetry.addLine("updated");


                    //last line
                    telemetry.addData("At Position", "%7d :%7d",
                            frontLeft.getCurrentPosition(),

                            backLeft.getCurrentPosition());

                    telemetry.addData("Speed", "%7d",
                            frontRight.getPower(),
                            backRight.getPower()
                    );

                    telemetry.update();
                }
            }
        }

    }
}