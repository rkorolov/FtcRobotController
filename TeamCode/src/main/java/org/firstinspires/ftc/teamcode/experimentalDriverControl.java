package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.openftc.easyopencv.OpenCvCamera;



/*import com.qualcomm.robotcore.hardware.DigitalChannel;
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

    private DcMotor armControlLeft;
    private DcMotor armControlRight;
    //private DcMotor armControl;
    private Servo grabberControl;

    private int grabberStatus = -1;

    //init sensors/camera
    OpenCvCamera camera;



    @Override
    public void runOpMode() {

        //init motors
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");

        armControlLeft = hardwareMap.get(DcMotor.class, "armControlLeft");
        armControlRight = hardwareMap.get(DcMotor.class, "armControlRight");
        //armControl = hardwareMap.get(DcMotor.class, "armControl");
        grabberControl = hardwareMap.get(Servo.class, "grabberControl");

        double frontLeftPower;
        double backLeftPower;
        double frontRightPower;
        double backRightPower;



        float armControlLeftPower = 0;
        float armControlRightPower = 0;
        //float armControlPower = 0;
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
            //test




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

            if(gamepad1.dpad_left){
                frontLeftPower += -0.3;
                backLeftPower += -0.3;
                frontRightPower += 0.3;
                backRightPower += 0.3;
            }
            if(gamepad1.dpad_right){
                frontLeftPower += 0.3;
                backLeftPower += 0.3;
                frontRightPower += -0.3;
                backRightPower += -0.3;
            }
            if(gamepad1.dpad_up){
                frontLeftPower += 0.3;
                backLeftPower += 0.3;
                frontRightPower += 0.3;
                backRightPower += 0.3;
            }
            if(gamepad1.dpad_down){
                frontLeftPower += -0.3;
                backLeftPower += -0.3;
                frontRightPower += -0.3;
                backRightPower += -0.3;
            }
            if(gamepad1.right_bumper){
                frontLeftPower += 1.6;
                backLeftPower += 1.6;
                frontRightPower += 1.6;
                backRightPower += 1.6;
            }
            if(gamepad1.left_bumper){
                frontLeftPower += -1.6;
                backLeftPower += -1.6;
                frontRightPower += -1.6;
                backRightPower += -1.6;
            }
            if(gamepad2.dpad_up){
                //armControlRightPower = (float)-0.25;
                armControlRightPower = (float)0.5;
            }else if(gamepad2.dpad_down){
                //armControlRightPower = (float)0.25;
                armControlRightPower = (float)-0.5;
            }else{
                //armControlRightPower = 0;
                armControlRightPower = 0;
            }
            if(gamepad2.dpad_left){
                armControlLeftPower = (float)0.5;
            }else if(gamepad2.dpad_right){
                armControlLeftPower = (float)-0.5;
            }else{
                armControlLeftPower = 0;
            }
            telemetry.addData("left Trigger", gamepad2.left_trigger);
            telemetry.addData("right Trigger", gamepad2.right_trigger);
            armControlRightPower = gamepad2.left_trigger - gamepad2.right_trigger;

            //armControlPower = gamepad2.left_trigger - gamepad2.right_trigger;
            telemetry.addData("armControlPower", armControlRightPower);

            /*if(gamepad2.left_trigger > 0 || gamepad2.right_trigger > 0) {
                armControlLeftPower = gamepad2.left_trigger - gamepad2.right_trigger;
                armControlRightPower = gamepad2.right_trigger - gamepad2.left_trigger;
            }*/
            /*if(gamepad2.left_trigger > 0){
                armControlLeftPower = -(gamepad2.left_trigger);
            }else{
                armControlLeftPower = 0;
            }
            if(gamepad2.right_trigger > 0){
                armControlRightPower = gamepad2.right_trigger;
            }else{
                armControlRightPower = 0;
            }*/
            if(gamepad2.x && grabberControl.getPosition() != 1){
                grabberStatus = 0;
                grabberControl.setPosition(.7);
            }
            if(gamepad2.a && grabberControl.getPosition() != 0){
                grabberStatus = 1;
                grabberControl.setPosition(0);
            }

            //*0.5 to set more reasonable speed
            frontRight.setPower(frontRightPower*0.5);
            frontLeft.setPower(frontLeftPower*0.5);
            backRight.setPower(backRightPower*0.5);
            backLeft.setPower(backLeftPower*0.5);

            //armControl.setPower(armControlPower);
            armControlLeft.setPower(armControlLeftPower);
            armControlRight.setPower(armControlRightPower);


            //last line
            telemetry.addData("Front Right Position",
                    //frontLeft.getCurrentPosition(),
                    frontRight.getCurrentPosition());
            //backRight.getCurrentPosition());


            telemetry.addData("grabber Status",
                    grabberStatus);
            telemetry.addData("Front Right Speed",
                    frontRight.getPower());
            telemetry.addData("Front Left Speed",
                    frontLeft.getPower());
            telemetry.addData("Back Right Speed",
                    backRight.getPower());
            telemetry.addData("Back Left Speed",
                    backLeft.getPower());
            //telemetry.addData("Arm Control Speed",
                    //armControlLeft.getPower());
            //telemetry.addData("Arm Control Position",
                    //armControlLeft.getCurrentPosition());
            telemetry.addLine("updated");


            telemetry.update();
        }
    }
}


