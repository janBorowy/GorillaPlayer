import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import youtubeModel.VideoData
import java.awt.Color
import java.time.Duration

val EMBED_COLOR = Color(75, 0, 130)

fun buildVideoInfoEmbed(video: VideoData) : MessageEmbed =
    with(video) {
        EmbedBuilder()
            .setTitle(title)
            .setDescription(channel)
            .setColor(EMBED_COLOR)
            .setImage(thumbnailUrl)
            .addField("Duration:", duration.doubleDotOrNull(), true)
            .build()
    }

fun buildVideoAddedEmbed(video: VideoData) : MessageEmbed =
    with(video) {
        EmbedBuilder()
            .setAuthor("Added to queue...")
            .setTitle(title)
            .setDescription(channel)
            .setColor(EMBED_COLOR)
            .setThumbnail(thumbnailUrl)
            .addField("Duration:", duration.doubleDotOrNull(), true)
            .build()
    }


fun buildQueueEmbed(songs: List<VideoData>) : MessageEmbed {
    val description = buildString {
        var counter = 1
        for(song in songs) {
            with(song) {
                append("$counter. $title - ${duration.doubleDotOrNull()}\n")
            }
            ++counter
        }
    }

    return with(EmbedBuilder()) {
        setTitle("Currently ${songs.size} songs in queue")
        setDescription(description)
        setColor(EMBED_COLOR)
    }.build()
}

fun Duration.doubleDot(): String {
    val seconds = seconds
    val minutes = seconds / 60
    val hours = seconds / 3600

    val secondsInStr = seconds - minutes * 60
    val minutesInStr = minutes - hours * 60

    return String.format("${if(hours.toInt() != 0) "%d:" else ""}%02d:%02d", hours, minutesInStr, secondsInStr)
}

fun Duration?.doubleDotOrNull(): String =
    this?.doubleDot() ?: "DURATION_UNKNOWN"