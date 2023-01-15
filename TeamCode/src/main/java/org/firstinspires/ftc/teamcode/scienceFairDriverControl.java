package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class scienceFairDriverControl extends LinearOpMode {

    //initialize motors
    private DcMotor left;
    private DcMotor right;
    private Servo servo;



    //remove InterruptedException if issue
    @Override
    public void runOpMode() throws InterruptedException {

        //configure motors
        left = hardwareMap.get(DcMotor.class, "left");
        right = hardwareMap.get(DcMotor.class, "right");
        servo = hardwareMap.get(Servo.class, "servo");

        double leftPower;
        double rightPower;

        //reverse direction of one motor, reverse other motor if goes backwards instead of forward
        right.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addLine("Init Done");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");

            double y = -gamepad1.left_stick_y; // change axis
            double x = gamepad1.left_stick_x; // Counteract imperfect strafing

            double denominator = Math.max(Math.abs(y) + Math.abs(x), 1);


            leftPower = (y + x) / denominator;
            rightPower = (y - x) /denominator;

            leftPower *= .5;
            rightPower *= .5;

            left.setPower(leftPower);
            right.setPower(rightPower);


            telemetry.update();
        }

    }
}
