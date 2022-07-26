package gen.greendao;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import afkt.db.database.green.module.note.bean.NotePicture;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "NOTE_PICTURE".
*/
public class NotePictureDao extends AbstractDao<NotePicture, Long> {

    public static final String TABLENAME = "NOTE_PICTURE";

    /**
     * Properties of entity NotePicture.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Picture = new Property(1, String.class, "picture", false, "PICTURE");
        public final static Property NoteId = new Property(2, Long.class, "noteId", false, "NOTE_ID");
    }

    private Query<NotePicture> note_PicturesQuery;

    public NotePictureDao(DaoConfig config) {
        super(config);
    }
    
    public NotePictureDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"NOTE_PICTURE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"PICTURE\" TEXT NOT NULL ," + // 1: picture
                "\"NOTE_ID\" INTEGER);"); // 2: noteId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"NOTE_PICTURE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, NotePicture entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getPicture());
 
        Long noteId = entity.getNoteId();
        if (noteId != null) {
            stmt.bindLong(3, noteId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, NotePicture entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getPicture());
 
        Long noteId = entity.getNoteId();
        if (noteId != null) {
            stmt.bindLong(3, noteId);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public NotePicture readEntity(Cursor cursor, int offset) {
        NotePicture entity = new NotePicture( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // picture
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2) // noteId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, NotePicture entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPicture(cursor.getString(offset + 1));
        entity.setNoteId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(NotePicture entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(NotePicture entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(NotePicture entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "pictures" to-many relationship of Note. */
    public List<NotePicture> _queryNote_Pictures(Long noteId) {
        synchronized (this) {
            if (note_PicturesQuery == null) {
                QueryBuilder<NotePicture> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.NoteId.eq(null));
                queryBuilder.orderRaw("T.'_id' ASC");
                note_PicturesQuery = queryBuilder.build();
            }
        }
        Query<NotePicture> query = note_PicturesQuery.forCurrentThread();
        query.setParameter(0, noteId);
        return query.list();
    }

}
