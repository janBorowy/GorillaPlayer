import command.BlackmodeCommand
import command.BotCommand
import command.InfoCommand
import command.JoinCommand
import command.LeaveCommand
import command.PlayCommand
import command.ShowQueueCommand
import command.SkipCommand

object ServerCommandPalette : HashMap<String, BotCommand>() {

    init {
        register(InfoCommand)
        register(JoinCommand)
        register(LeaveCommand)
        register(PlayCommand)
        register(ShowQueueCommand)
        register(BlackmodeCommand)
        register(SkipCommand)
    }

    private fun register(command: BotCommand) {
        this[command.commandId] = command
    }
}