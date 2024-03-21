// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.autoScripts.AimRobotBaseAtSpeaker;
import frc.robot.commands.autoScripts.AimShooterAtTarget;
import frc.robot.commands.autoScripts.AimShooterAtTargetEndless;
import frc.robot.commands.autoScripts.Shoot;
import frc.robot.commands.autoScripts.ShootAt49Point9;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveSubsystem;

public final class Autos {
    /**
     * This command is used to allow for a script to exist that just skips autonomous and is not used for any other purpose
     * @return A command object, in our case usually used to create named commands
     */
    public static Command noAuto() {
        return new InstantCommand(() -> {
            System.out.println("Skipping Auto Script!");
        });
    }

    /**
     * This command aims the robot at the speaker to prepare for another command to shoot
     * @param swerve The swerve subsystem, we are directly controlling it, so this command will cancel other commands that control it
     * @param shooter The shooter subsystem, we are directly controlling it, so this command will cancel other commands that control it
     * @return A command object, in our case usually used to create named commands
     */
    public static Command aimAtTarget(SwerveSubsystem swerve, ShooterSubsystem shooter) {
        return new ParallelCommandGroup(
            new AimRobotBaseAtSpeaker(swerve),
            new AimShooterAtTarget(swerve, shooter));
    }

    /**
     * This command aims at the speaker and then shoots the held note
     * @param swerve The swerve subsystem, we are directly controlling it, so this command will cancel other commands that control it
     * @param shooter The shooter subsystem, we are directly controlling it, so this command will cancel other commands that control it
     * @param intake The intake subsystem, we are directly controlling it, so this command will cancel other commands that control it
     * @return A command object, in our case usually used to create named commands
     */
    public static Command shoot(SwerveSubsystem swerve, ShooterSubsystem shooter, IntakeSubsystem intake) {
        return new ParallelCommandGroup(
            new AimRobotBaseAtSpeaker(swerve),
            new Shoot(shooter, swerve, intake)
        );
    }

    /**
     * This command shoots at the target at the beginning of an autonomous run, We assume that it is already correctly aimed and as such we don't attempt to turn the robot
     * @param swerve The swerve subsystem, in this case to read from it, it is not a 'required' subsystem and as such this command won't cancel other commands that use it
     * @param shooter The shooter subsystem, we are directly controlling it, so this command will cancel other commands that control it
     * @param intake The intake subsystem, we are directly controlling it, so this command will cancel other commands that control it
     * @return A command object, in our case usually used to create named commands
     */
    public static Command shootAtStart(SwerveSubsystem swerve, ShooterSubsystem shooter, IntakeSubsystem intake) {
        return new ParallelCommandGroup(
            new ShootAt49Point9(shooter, swerve, intake)
        );
    }

    /**
     * This is an endless command that keeps the shooter angle aimed at the target, when used in autonomous use a parallel race group
     * @param swerve The swerve subsystem, in this case to read from it, it is not a 'required' subsystem and as such this command won't cancel other commands that use it
     * @param shooter The shooter subsystem, we are directly controlling it, so this command will cancel other commands that control it
     * @return A command object, in our case usually used to create named commands
     */
    public static Command keepShooterAimedEndless(SwerveSubsystem swerve, ShooterSubsystem shooter) {
        return new AimShooterAtTargetEndless(swerve, shooter);
    }

    /**
     * This is a utility class with only static functions, so we should never be creating an instance of it
     */
    private Autos() {
        throw new UnsupportedOperationException("This is a utility class!");
    }
}
