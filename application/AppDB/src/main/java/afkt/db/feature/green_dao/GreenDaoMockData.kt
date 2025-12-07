package afkt.db.feature.green_dao

import afkt.db.database.green.module.note.NoteDatabase
import afkt.db.database.green.module.note.NoteType
import afkt.db.database.green.module.note.bean.Note
import afkt.db.database.green.module.note.bean.NotePicture
import afkt.db.feature.MockData
import dev.simple.extensions.hi.hiif.hiIfNotNull
import dev.utils.DevFinal
import dev.utils.common.RandomUtils
import gen.greendao.NoteDao
import gen.greendao.NotePictureDao
import java.util.*
import kotlin.random.Random

/**
 * detail: 模拟数据
 * @author Ttt
 */
object GreenDaoMockData {

    fun insertNotes(number: Int = RandomUtils.getRandom(1, 10)) {
        val noteDao = NoteDatabase.database()?.noteDao()
        val notePictureDao = NoteDatabase.database()?.notePictureDao()
        repeat(number) { insertNote(noteDao, notePictureDao) }
    }

    private fun insertNote(
        noteDao: NoteDao? = NoteDatabase.database()?.noteDao(),
        notePictureDao: NotePictureDao? = NoteDatabase.database()?.notePictureDao(),
    ) {
        val index = Random.nextInt(MockData.titles.size)
        noteDao.hiIfNotNull { dao ->
            Note().apply {
                title = MockData.titles[index]
                content = MockData.contents[index]
                type = NoteType.entries.toTypedArray().random()
                createdAt = Date(
                    System.currentTimeMillis() - RandomUtils.nextLongRange(
                        DevFinal.TIME.MONTH_MS,
                        DevFinal.TIME.YEAR_MS
                    )
                )
                val noteId = dao.insert(this)
                if (noteId != 0L && type != NoteType.TEXT) {
                    // 保存新的 ID
                    this.id = noteId
                    // 随机插入图片
                    val list = List(Random.nextInt(1, 4)) {
                        createNotePicture(noteId)
                    }
                    notePictureDao?.insertInTx(list)
                }
            }
        }
    }

    private fun createNotePicture(id: Long): NotePicture {
        val pictureUrl = String.format(
            "https://picsum.photos/id/%s/300",
            RandomUtils.getRandom(1, 101)
        )
        return NotePicture().apply {
            picture = pictureUrl
            noteId = id
        }
    }
}