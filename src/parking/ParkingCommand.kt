package parking

import parking.CommandName.*
import parking.CommandParser.ParsingResult
import parking.CommandParam.*
import java.lang.IllegalArgumentException

enum class CommandName(val value: String) {
    PARK("park"), LEAVE("leave"), CREATE_PARKING("create"),
    REG_BY_COLOR("reg_by_color"), SPOT_BY_COLOR("spot_by_color"),
    EXIT("exit"), STATUS("status"), SPOT_BY_REG("spot_by_reg")
}

enum class CommandParam {
    REG_NUM, CAR_COLOR, NUM_OF_SPOT, AMOUNT_SPOT
}

abstract class ParkingCommand(val needParkingInit: Boolean = true) : Command()

class ParkCommand(regNum: String, carColor: String) : ParkingCommand() {
    init {
        addStringValue(REG_NUM.name, regNum)
        addStringValue(CAR_COLOR.name, carColor)
    }

    fun getRegNum(): String {
        return getStringValue(REG_NUM.name)
    }

    fun getCarColor(): String {
        return getStringValue(CAR_COLOR.name)
    }

}

class LeaveCommand(spotNum: Int) : ParkingCommand() {
    init {
        addValue(NUM_OF_SPOT.name, spotNum)
    }

    fun getSpotNum(): Int {
        return getIntValue(NUM_OF_SPOT.name)
    }
}

class CreateParkingCommand(spotAmount: Int) : ParkingCommand(false) {
    init {
        addValue(AMOUNT_SPOT.name, spotAmount)
    }

    fun getSpotAmount(): Int {
        return getIntValue(AMOUNT_SPOT.name)
    }
}

class RegByColorCommand(carColor: String) : ParkingCommand() {
    init {
        addValue(CAR_COLOR.name, carColor)
    }

    fun getCarColor(): String {
        return getStringValue(CAR_COLOR.name)
    }
}

class SpotByRegNumCommand(regNum: String) : ParkingCommand() {
    init {
        addStringValue(REG_NUM.name, regNum)
    }

    fun getRegNum(): String {
        return getStringValue(REG_NUM.name)
    }
}

class SpotByCarColorCommand(carColor: String) : ParkingCommand() {
    init {
        addStringValue(CAR_COLOR.name, carColor)
    }

    fun getCarColor(): String {
        return getStringValue(CAR_COLOR.name)
    }
}

class StatusCommand : ParkingCommand()

class ExitCommand : ParkingCommand(false)

class CommandParserImpl : CommandParser {

    override fun parse(rawCommand: String): ParsingResult {
        if (rawCommand.isBlank()) {
            return ParsingResult("Command is empty")
        }

        val commandStr = rawCommand.split(" ").map { it.trim() }

        //parse name of command or exist with error message
        val commandName = CommandName.values().find { it.value == commandStr[0] }
            ?: return ParsingResult("Command '${commandStr[0]}' is not found")

        val command = when (commandName) {
            PARK -> ParkCommand(commandStr[1], commandStr[2])
            LEAVE -> {
                //check is positive spot number
                if (!commandStr[1].isPositiveNumber()) {
                    return ParsingResult("Spot number '${commandStr[1]}' is not valid")
                }
                LeaveCommand(commandStr[1].toInt())
            }
            CREATE_PARKING -> {
                if (!commandStr[1].isPositiveNumber() || commandStr[1].toInt() < 1) {
                    return ParsingResult("Amount of spots '${commandStr[1]}' is not valid")
                }
                CreateParkingCommand(commandStr[1].toInt())
            }
            REG_BY_COLOR -> RegByColorCommand(commandStr[1])
            SPOT_BY_COLOR -> SpotByCarColorCommand(commandStr[1])
            SPOT_BY_REG -> SpotByRegNumCommand(commandStr[1])
            STATUS -> StatusCommand()
            EXIT -> ExitCommand()

        }
        return ParsingResult(command)
    }

}

class CommandExecutorImpl(private val parking: Parking) : CommandExecutor {

    override fun execute(command: Command): String {
        if (command !is ParkingCommand) throw IllegalArgumentException("Executor can execute only parking command")
        if (command.needParkingInit && !parking.existSpots()) {
            return "Sorry, a parking lot has not been created."
        }

        return when (command) {
            is ParkCommand -> {
                if (parking.isParkFuel()) {
                    "Sorry, the parking lot is full."
                } else {
                    val car = Car(command.getRegNum(), command.getCarColor())
                    val spotNum = parking.park(car)
                    "${car.color} car parked in spot $spotNum."
                }
            }
            is LeaveCommand -> {
                val spot = parking.getSpotByNum(command.getSpotNum())
                if (spot == null)
                    "Cannot find spot with num = '${command.getSpotNum()}"
                else {
                    if (spot.isFree()) {
                        "There is no car in spot ${command.getSpotNum()}."
                    } else {
                        parking.leave(command.getSpotNum())
                        "Spot ${command.getSpotNum()} is free."
                    }
                }
            }
            is CreateParkingCommand -> {
                parking.changeAmountOfSpots(command.getSpotAmount())
                "Created a parking lot with ${command.getSpotAmount()} spots."
            }
            is StatusCommand -> {
                val emptySpots = parking.getAllNotEmptySpots()
                if (emptySpots.isEmpty()) "Parking lot is empty." else {
                    emptySpots.joinToString("\n") { it.toString() }
                }
            }
            is RegByColorCommand -> {
                val spots = parking.getSpotsByColor(command.getCarColor())
                if (spots.isEmpty()) {
                    "No cars with color ${command.getCarColor()} were found."
                } else {
                    spots.joinToString { it.getCar()?.regNum!! }
                }
            }
            is SpotByCarColorCommand -> {
                val spots = parking.getSpotsByColor(command.getCarColor())
                if (spots.isEmpty()) {
                    "No cars with color ${command.getCarColor()} were found."
                } else {
                    spots.joinToString { it.number.toString() }
                }
            }
            is SpotByRegNumCommand -> {
                val spot = parking.getSpotByRegNum(command.getRegNum())
                spot?.number?.toString() ?: "No cars with registration number ${command.getRegNum()} were found."
            }
            is ExitCommand -> ""
            else -> {
                throw RuntimeException("Not yet implemented command $command")
            }
        }

    }

}