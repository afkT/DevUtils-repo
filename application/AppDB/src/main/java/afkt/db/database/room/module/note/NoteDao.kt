package afkt.db.database.room.module.note

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * detail: Room DAO 访问数据库方法
 * @author Ttt
 */
@Dao
interface NoteDao {

    // ===============
    // = Note 基本操作 =
    // ===============

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(vararg notes: Note): LongArray

    @Delete
    suspend fun deleteNote(note: Note): Int

    // ============================
    // = Note 与 NotePicture 一对多 =
    // ============================

    /**
     * 获取所有笔记及其图片列表（一对多）
     */
    @Transaction
    @Query("SELECT * FROM NoteTable")
    fun getNoteAndPictureLists(): LiveData<List<NoteAndPicture>>

    /**
     * 分页获取笔记及其图片列表（一对多）
     * @param limit 每页大小
     * @param offset 偏移量
     */
    @Transaction
    @Query("SELECT * FROM NoteTable LIMIT :limit OFFSET :offset")
    fun getNoteAndPictureLists(
        limit: Int,
        offset: Int
    ): LiveData<List<NoteAndPicture>>

    // ===================
    // = NotePicture 操作 =
    // ===================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotePictures(pictures: List<NotePicture>): List<Long>

    @Delete
    suspend fun deleteNotePictures(vararg notePictures: NotePicture): Int

    /**
     * 根据 noteId 删除该笔记下的所有图片（级联删除由数据库外键已保证，这里提供便捷方法）
     */
    @Query("DELETE FROM NotePictureTable WHERE noteId = :noteId")
    suspend fun deleteNotePicturesByNoteId(noteId: Long): Int

    /**
     * 根据 noteId 查询该笔记下的所有图片
     */
    @Query("SELECT * FROM NotePictureTable WHERE noteId = :noteId")
    fun getPicturesByNoteId(noteId: Long): LiveData<List<NotePicture>>
}

///**
// * detail: Room DAO 访问数据库方法
// * @author Ttt
// */
//@Dao
//public interface NoteDao {
//
//    @Insert
//    long[] insertNotes(Note... notes);
//
//    @Insert
//    long insertNote(Note note);
//
//    @Delete
//    void deleteNote(Note note);
//
//    @Transaction
//    @Query("SELECT * FROM NoteTable")
//    List<NoteAndPicture> getNoteAndPictureLists();
//
//    @Transaction
//    @Query("SELECT * FROM NoteTable LIMIT :limit OFFSET :offset")
//    List<NoteAndPicture> getNoteAndPictureLists(
//            int limit,
//            int offset
//    );
//
//    // =
//
//    @Insert
//    long[] insertNotePictures(List<NotePicture> lists);
//
//    @Delete
//    int deleteNotePictures(NotePicture... notePictures);
//}