package parking

class Application {
    private val parking = Parking()
    private val commandParser = CommandParserImpl()
    private val commandExecutor = CommandExecutorImpl(parking)

    fun run() {
        var command: Command
        do {
            command = readAndExecuteCommand()
        } while (command !is ExitCommand)
    }

    private fun readAndExecuteCommand(): Command {
        val parsingResult = commandParser.parse(readLine()!!)
        if (parsingResult.haveError) {
            println(parsingResult.getErrorMessage())
        } else {
            val resultOfCommand = commandExecutor.execute(parsingResult.getCommand())
            if (resultOfCommand.isNotBlank()) {
                println(resultOfCommand)
            }
        }
        return parsingResult.getCommand()
    }
}