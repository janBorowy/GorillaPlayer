import command.BotCommand
import command.InfoCommand
import command.JoinCommand
import command.LeaveCommand
import command.PauseCommand
import command.PlayCommand
import command.ResumeCommand
import command.ShowQueueCommand
import command.SkipCommand

object ServerCommandPalette : HashMap<String, BotCommand>() {

    init {
        register(InfoCommand)
        register(JoinCommand)
        register(LeaveCommand)
        register(PlayCommand)
        register(ShowQueueCommand)
        register(SkipCommand)
        register(PauseCommand)
        register(ResumeCommand)
    }

    private fun register(command: BotCommand) {
        this[command.commandId] = command
    }
}