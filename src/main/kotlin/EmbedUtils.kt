import com.google.api.services.youtube.model.Video
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import java.awt.Color

val EMBED_COLOR = Color(75, 0, 130)

fun buildVideoEmbed(video: Video) : MessageEmbed {
    val snippet = video.snippet
    val contentDetails = video.contentDetails
    return EmbedBuilder()
        .setAuthor("Added to queue...")
        .setTitle(snippet.title)
        .setDescription(snippet.channelTitle)
        .setColor(EMBED_COLOR)
        .setThumbnail(snippet.thumbnails.standard.url)
        .addField("Duration:", parseDurationString(contentDetails.duration), true)
        .build()
}

private fun parseDurationString(str: String) : String{
    val withoutPT = str.substring(2)
    val vals = withoutPT.split("H", "M", "S")
    return when (vals.size) {
        2 -> "0:${vals[1]}"
        3 -> "${vals[0]}:${vals[1]}"
        4 -> "${vals[0]}:${vals[1]}:${vals[2]}"
        else -> ""
    }
}