package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class OnlyForwardAuto2 extends LinearOpMode{

    int origin = 0;
    int position = 0;

    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;


    public void runOpMode() {

        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");

        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        origin = frontRight.getCurrentPosition();
        position = frontRight.getCurrentPosition();

        waitForStart();

        while (opModeIsActive()) {

            while ((Math.abs(position) - Math.abs(origin)) < (1000)) {
                position = frontRight.getCurrentPosition();
                int value = Math.abs(position - origin);
                telemetry.addLine(String.valueOf(value));
                telemetry.update();
                frontRight.setPower(0.5);
                frontLeft.setPower(0.5);
                backRight.setPower(-0.5);
                backLeft.setPower(-0.5);
                telemetry.addData("At Position",  "%7d :%7d",
                        frontRight.getCurrentPosition(),

                        backRight.getCurrentPosition());
            }
            frontRight.setPower(0);
            frontLeft.setPower(0);
            backRight.setPower(0);
            backLeft.setPower(0);
            /*
            origin = frontRight.getCurrentPosition();
            position = frontRight.getCurrentPosition();
            while ((Math.abs(position) - Math.abs(origin)) < (1000)){
                position = frontRight.getCurrentPosition();
                int value = Math.abs(position - origin);
                telemetry.addLine(String.valueOf(value));
                telemetry.update();
                frontRight.setPower(-0.5);
                frontLeft.setPower(0.5);
                backRight.setPower(-0.5);
                backLeft.setPower(0.5);
            }
            frontRight.setPower(0);
            frontLeft.setPower(0);
            backRight.setPower(0);
            backLeft.setPower(0);*/
        }
    }
}



