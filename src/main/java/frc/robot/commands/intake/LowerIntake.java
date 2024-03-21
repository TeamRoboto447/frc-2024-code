// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.IntakeSubsystem.IntakeStatus;

public class LowerIntake extends Command {
  /** Creates a new LowerIntake. */
  private final IntakeSubsystem subsystem;
  public LowerIntake(IntakeSubsystem intake) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.subsystem = intake;
    addRequirements(this.subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.subsystem.lowerIntake();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return this.subsystem.getState() == IntakeStatus.LOWERED || this.subsystem.getState() == IntakeStatus.STALLED_AT_BOTTOM;
  }
}
