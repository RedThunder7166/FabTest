// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.time.Instant;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class fabtest extends SubsystemBase {
  private TalonFX motor1 = new TalonFX(13);
  private TalonFX motor2 = new TalonFX(14);
  private double both_speed = 8;
  private double front_speed = both_speed;
  private double back_speed = both_speed;
  /** Creates a new motor. */
  public fabtest() {
    motor1.setNeutralMode(NeutralModeValue.Brake);
    motor2.setNeutralMode(NeutralModeValue.Brake);

    ShuffleboardTab tab = Shuffleboard.getTab("Fab test");
    tab.add(this);
    tab.addDouble("Both Speed", () -> both_speed);
    tab.addDouble("Front Speed", () -> front_speed);
    tab.addDouble("Back Speed", () -> back_speed);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }

  public void stop() {
    motor1.set(0);
    motor2.set(0);
  }

  public void set(double value) {
    motor1.set(value);
    motor2.set(value);
  }

  public void frontMotor(double value) {
    motor1.set(value);
  }
  public void backMotor(double value) {
    motor2.set(value);
  }
  public void frontMotorInverse(double value) {
    frontMotor(-value);
  }
  public void backMotorInverse(double value) {
    backMotor(-value);
  }

  public final RunCommand driveMotors = new RunCommand(() -> {
    frontMotorInverse(front_speed / 10);
    backMotorInverse(back_speed / 10);
  }, this);

  public final InstantCommand stopMotors = new InstantCommand(this::stop, this);

  private void setFrontSpeed(double speed) {
    front_speed = speed;
    if (front_speed < 1) {
      front_speed = 1;
    } else if (front_speed > 10) {
      front_speed = 10;
    }
  }
  private void setBackSpeed(double speed) {
    back_speed = speed;
    if (back_speed < 1) {
      back_speed = 1;
    } else if (back_speed > 10) {
      back_speed = 10;
    }
  }
  private void setBothSpeed(double speed){
    both_speed = speed;
    if (both_speed < 1) {
      both_speed = 1;
    } else if (both_speed > 10) {
      both_speed = 10;
    }
    setFrontSpeed(both_speed);
    setBackSpeed(both_speed);
  }

  public final InstantCommand decreaseBoth = new InstantCommand(() -> {
    setBothSpeed(both_speed - 1);
  }, this);
  public final InstantCommand increaseBoth = new InstantCommand(() -> {
    setBothSpeed(both_speed + 1);
  }, this);

  public final InstantCommand decreaseFront = new InstantCommand(() -> {
    setFrontSpeed(front_speed - 1);
  }, this);
  public final InstantCommand increaseFront = new InstantCommand(() -> {
    setFrontSpeed(front_speed + 1);
  }, this);

  public final InstantCommand decreaseBack = new InstantCommand(() -> {
    setBackSpeed(back_speed - 1);
  }, this);
  public final InstantCommand increaseBack = new InstantCommand(() -> {
    setBackSpeed(back_speed + 1);
  }, this);
}
