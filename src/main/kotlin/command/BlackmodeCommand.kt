package command

import MessageHelper
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object BlackmodeCommand : BotCommand {
    override val commandId: String = "blackmode"

    override fun onCommand(event: MessageReceivedEvent) {
        MessageHelper.sendGenericMessage(event.channel, "https://e3.365dm.com/20/05/1600x900/skynews-george-floyd-killed-by-police_5001291.jpg?20200616043751")
    }
}