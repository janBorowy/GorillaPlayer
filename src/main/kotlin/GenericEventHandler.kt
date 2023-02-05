import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.hooks.EventListener

object GenericEventHandler : EventListener{

    override fun onEvent(event: GenericEvent) =
        when (event) {
            is ReadyEvent -> onReady(event)
            else -> {}
        }

    private fun onReady(event: GenericEvent) {
        println("[READY] handling ready event")
        println("[READY] bot is up")
    }

    private fun onUnhandledEvent(event: GenericEvent) {
        println("[EVENT] unhandled event with response : ${event.responseNumber}")
    }

}