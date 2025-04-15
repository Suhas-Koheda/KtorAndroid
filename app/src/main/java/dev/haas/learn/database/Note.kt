import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val timeCreated: LocalDateTime = LocalDateTime.now(),
    val timeEdited: LocalDateTime = LocalDateTime.now(),
    val timesEdited: Int = 0
)