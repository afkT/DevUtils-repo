package afkt.db.feature.green_dao

import afkt.db.database.room.module.note.*
import afkt.db.feature.MockData
import dev.simple.extensions.hi.hiif.hiIfNotNull
import dev.utils.DevFinal
import dev.utils.common.RandomUtils
import java.util.*
import kotlin.random.Random

/**
 * detail: 模拟数据
 * @author Ttt
 */
object GreenDaoMockData {

    fun insertNodes(number: Int = RandomUtils.getRandom(1, 10)) {
        val noteDao = NoteDatabase.database()?.noteDao
        repeat(number) { insertNode(noteDao) }
    }

    private fun insertNode(
        noteDao: NoteDao? = NoteDatabase.database()?.noteDao
    ) {
        val index = Random.nextInt(MockData.titles.size)
        noteDao.hiIfNotNull { dao ->
            Note(
                id = 0,
                title = MockData.titles[index],
                content = MockData.contents[index],
                type = NoteType.entries.toTypedArray().random(),
                createdAt = Date(
                    System.currentTimeMillis() - RandomUtils.nextLongRange(
                        DevFinal.TIME.MONTH_MS,
                        DevFinal.TIME.YEAR_MS
                    )
                )
            ).apply {
                val noteId = dao.insertNote(this)
                if (noteId != 0L && type != NoteType.TEXT) {
                    // 保存新的 ID
                    this.id = noteId
                    // 随机插入图片
                    val list = List(Random.nextInt(1, 4)) {
                        createNotePicture(noteId)
                    }
                    dao.insertNotePictures(list)
                }
            }
        }
    }

    private fun createNotePicture(noteId: Long): NotePicture {
        val pictureUrl = String.format(
            "https://picsum.photos/id/%s/300",
            RandomUtils.getRandom(1, 101)
        )
        return NotePicture(noteId = noteId, picture = pictureUrl)
    }
}