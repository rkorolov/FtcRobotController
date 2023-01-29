package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.ams.AMSColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorDigitalTouch;
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


    private DcMotor armControl;
    private DcMotor linSlide;
    private Servo grabberControl;

    private TouchSensor touchSensor;

    //init sensors/camera
    OpenCvCamera camera;



    @Override
    public void runOpMode() {

        //init motors
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");

        linSlide = hardwareMap.get(DcMotor.class, "linSlide");

        armControl = hardwareMap.get(DcMotor.class, "armControl");
        grabberControl = hardwareMap.get(Servo.class, "grabberControl");

        touchSensor = hardwareMap.get(TouchSensor.class, "touchSensor");

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

        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");




            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;


           double up = gamepad2.right_trigger;
           double down = gamepad2.left_trigger;

           double armPower = -(up - down);
           linSlide.setPower(armPower);









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




            if(gamepad2.x && grabberControl.getPosition() != 1){
                grabberControl.setPosition(.7);
            }
            if(gamepad2.a && grabberControl.getPosition() != 0){
                grabberControl.setPosition(0);
            }

            if (touchSensor.isPressed()) {
                telemetry.addLine("Cone Aligned = True");
            }

            //*0.5 to set more reasonable speed
            frontRight.setPower(frontRightPower*0.5);
            frontLeft.setPower(frontLeftPower*0.5);
            backRight.setPower(backRightPower*0.5);
            backLeft.setPower(backLeftPower*0.5);

            armControl.setPower(armControlPower);


            //last line
            telemetry.addData("Front Right Position",
                    //frontLeft.getCurrentPosition(),
                    frontRight.getCurrentPosition());
            //backRight.getCurrentPosition());


            telemetry.addData("Touch Sensor Pressed: ", touchSensor.isPressed());
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


            telemetry.update();
        }
    }
}


