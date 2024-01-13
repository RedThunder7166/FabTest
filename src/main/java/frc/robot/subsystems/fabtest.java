// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.time.Instant;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
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

  private GenericEntry rps_input;

  /** Creates a new motor. */
  public fabtest() {
    motor1.setNeutralMode(NeutralModeValue.Brake);
    motor2.setNeutralMode(NeutralModeValue.Brake);
    motor1.setInverted(false);
    motor2.setInverted(false);
    var slot0Configs = new Slot0Configs();
    slot0Configs.kS = 0.05;
    slot0Configs.kV = 0.12;
    slot0Configs.kP = 1;
    slot0Configs.kI = 0;
    slot0Configs.kD = 0;

    ShuffleboardTab tab = Shuffleboard.getTab("Fab test");
    tab.add(this);
    tab.addDouble("Both Speed", () -> both_speed);
    tab.addDouble("Front Speed", () -> front_speed);
    tab.addDouble("Back Speed", () -> back_speed);
    tab.addDouble("Motor RPM", ()-> motor1.getVelocity().getValue());
    rps_input = tab.add("RPS", 10).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }

  public void stop() {
    motor1.set(0);
    motor2.set(0);
  }

  // public void set(double value) {
  //   motor1.set(value);
  //   motor2.set(value);
  // }

  public void frontMotor(double value) {
    final VelocityVoltage m_request = new VelocityVoltage(0).withSlot(0);

    motor1.setControl(m_request.withVelocity(value).withFeedForward(1));
    // motor1.set(value);
  }
  public void backMotor(double value) {
    final VelocityVoltage m_request = new VelocityVoltage(0).withSlot(0);

    motor2.setControl(m_request.withVelocity(value).withFeedForward(1));
  }

  public void frontMotorInverse(double value) {
    frontMotor(-value);
  }
  public void backMotorInverse(double value) {
    backMotor(-value);
  }

  // public final RunCommand driveMotors = new RunCommand(() -> {
  //   frontMotorInverse(front_speed / 10);
  //   backMotorInverse(back_speed / 10);
  // }, this);
  public final RunCommand driveMotors = new RunCommand(() -> {
    double rps = rps_input.getDouble(0);
    System.out.println(rps);
    frontMotor(rps);
    backMotor(rps);
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
