package command

import MessageHelper
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object JoinCommand : BotCommand {

    override val commandId: String = "join"

    override fun onCommand(event: MessageReceivedEvent) {
        val status = CommandUtils.attemptToJoinAChannel(event)
        if(status != CommandUtils.JOIN_STATUS.SUCCESS) {
            MessageHelper.sendJoinStatusMessage(event.channel, status)
        }
    }
}