package afkt.db.database.green.module.note.bean;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;

import java.util.Date;
import java.util.List;

import afkt.db.database.green.module.note.DateConverter;
import afkt.db.database.green.module.note.NoteType;
import afkt.db.database.green.module.note.NoteTypeConverter;
import gen.greendao.DaoSession;
import gen.greendao.NoteDao;
import gen.greendao.NotePictureDao;

/**
 * detail: Note 实体类
 * @author Ttt
 */
@Entity(
        nameInDb = "NoteTable",
        indexes = {@Index(value = "id, createdAt DESC", unique = true)}
)
public class Note {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    @Property(nameInDb = "title123456")
    private String title;

    private String content;

    @Convert(converter = DateConverter.class, columnType = Long.class)
    private Date createdAt;

    @Convert(converter = NoteTypeConverter.class, columnType = String.class)
    private NoteType type;

    @ToMany(referencedJoinProperty = "noteId")
    @OrderBy("id ASC")
    private List<NotePicture> pictures;

    @Transient // 数据库不创建该字段
    private String temp;

    // =================
    // = Auto Generate =
    // =================

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 363862535)
    private transient NoteDao myDao;

    @Generated(hash = 1887906035)
    public Note(
            Long id,
            @NotNull String title,
            String content,
            Date createdAt,
            NoteType type
    ) {
        this.id        = id;
        this.title     = title;
        this.content   = content;
        this.createdAt = createdAt;
        this.type      = type;
    }

    @Generated(hash = 1272611929)
    public Note() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public NoteType getType() {
        return this.type;
    }

    public void setType(NoteType type) {
        this.type = type;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 465103477)
    public List<NotePicture> getPictures() {
        if (pictures == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            NotePictureDao    targetDao   = daoSession.getNotePictureDao();
            List<NotePicture> picturesNew = targetDao._queryNote_Pictures(id);
            synchronized (this) {
                if (pictures == null) {
                    pictures = picturesNew;
                }
            }
        }
        return pictures;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 1035739203)
    public synchronized void resetPictures() {
        pictures = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 799086675)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao           = daoSession != null ? daoSession.getNoteDao() : null;
    }
}