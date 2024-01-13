// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class fabtest extends SubsystemBase {
  private TalonFX motor1 = new TalonFX(13);
  private TalonFX motor2 = new TalonFX(14);
  private double speed = 0;
  /** Creates a new motor. */
  public fabtest() {
    
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
}
