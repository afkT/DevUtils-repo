package afkt.db.feature.room

import afkt.db.database.room.module.note.*
import dev.simple.extensions.hi.hiif.hiIfNotNull
import dev.utils.DevFinal
import dev.utils.common.RandomUtils
import java.util.*
import kotlin.random.Random

/**
 * detail: 模拟数据
 * @author Ttt
 */
object RoomMockData {

    fun insertNodes(number: Int = RandomUtils.getRandom(1, 10)) {
        val noteDao = NoteDatabase.database()?.noteDao
        repeat(number) { insertNode(noteDao) }
    }

    private fun insertNode(
        noteDao: NoteDao? = NoteDatabase.database()?.noteDao
    ) {
        noteDao.hiIfNotNull { dao ->
            Note(
                id = 0,
                title = titles.random(),
                content = contents.random(),
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
            RandomUtils.getRandom(1, 10)
        )
        return NotePicture(noteId = noteId, picture = pictureUrl)
    }

    private val titles = arrayOf(
        "望庐山瀑布",
        "春晓",
        "静夜思",
        "登鹳雀楼",
        "相思",
        "独坐敬亭山",
        "春夜喜雨",
        "江雪",
        "寻隐者不遇",
        "游子吟",
        "悯农",
        "古朗月行",
        "风",
        "咏柳",
        "凉州词",
        "出塞",
        "芙蓉楼送辛渐",
        "鹿柴",
        "送元二使安西",
        "九月九日忆山东兄弟",
        "黄鹤楼送孟浩然之广陵",
        "早发白帝城",
        "望天门山",
        "别董大",
        "绝句",
        "春夜洛城闻笛",
        "枫桥夜泊",
        "乌衣巷",
        "滁州西涧",
        "竹里馆",
        "江南逢李龟年"
    )

    private val contents = arrayOf(
        "日照香炉生紫烟，遥看瀑布挂前川。\n飞流直下三千尺，疑是银河落九天。",
        "春眠不觉晓，处处闻啼鸟。\n夜来风雨声，花落知多少。",
        "床前明月光，疑是地上霜。\n举头望明月，低头思故乡。",
        "白日依山尽，黄河入海流。\n欲穷千里目，更上一层楼。",
        "红豆生南国，春来发几枝。\n愿君多采撷，此物最相思。",
        "众鸟高飞尽，孤云独去闲。\n相看两不厌，只有敬亭山。",
        "好雨知时节，当春乃发生。\n随风潜入夜，润物细无声。",
        "千山鸟飞绝，万径人踪灭。\n孤舟蓑笠翁，独钓寒江雪。",
        "松下问童子，言师采药去。\n只在此山中，云深不知处。",
        "慈母手中线，游子身上衣。\n临行密密缝，意恐迟迟归。\n谁言寸草心，报得三春晖。",
        "锄禾日当午，汗滴禾下土。\n谁知盘中餐，粒粒皆辛苦。",
        "小时不识月，呼作白玉盘。\n又疑瑶台镜，飞在青云端。",
        "解落三秋叶，能开二月花。\n过江千尺浪，入竹万竿斜。",
        "碧玉妆成一树高，万条垂下绿丝绦。\n不知细叶谁裁出，二月春风似剪刀。",
        "葡萄美酒夜光杯，欲饮琵琶马上催。\n醉卧沙场君莫笑，古来征战几人回。",
        "秦时明月汉时关，万里长征人未还。\n但使龙城飞将在，不教胡马度阴山。",
        "寒雨连江夜入吴，平明送客楚山孤。\n洛阳亲友如相问，一片冰心在玉壶。",
        "空山不见人，但闻人语响。\n返景入深林，复照青苔上。",
        "渭城朝雨浥轻尘，客舍青青柳色新。\n劝君更尽一杯酒，西出阳关无故人。",
        "独在异乡为异客，每逢佳节倍思亲。\n遥知兄弟登高处，遍插茱萸少一人。",
        "故人西辞黄鹤楼，烟花三月下扬州。\n孤帆远影碧空尽，唯见长江天际流。",
        "朝辞白帝彩云间，千里江陵一日还。\n两岸猿声啼不住，轻舟已过万重山。",
        "天门中断楚江开，碧水东流至此回。\n两岸青山相对出，孤帆一片日边来。",
        "千里黄云白日曛，北风吹雁雪纷纷。\n莫愁前路无知己，天下谁人不识君。",
        "两个黄鹂鸣翠柳，一行白鹭上青天。\n窗含西岭千秋雪，门泊东吴万里船。",
        "谁家玉笛暗飞声，散入春风满洛城。\n此夜曲中闻折柳，何人不起故园情。",
        "月落乌啼霜满天，江枫渔火对愁眠。\n姑苏城外寒山寺，夜半钟声到客船。",
        "朱雀桥边野草花，乌衣巷口夕阳斜。\n旧时王谢堂前燕，飞入寻常百姓家。",
        "独怜幽草涧边生，上有黄鹂深树鸣。\n春潮带雨晚来急，野渡无人舟自横。",
        "独坐幽篁里，弹琴复长啸。\n深林人不知，明月来相照。",
        "岐王宅里寻常见，崔九堂前几度闻。\n正是江南好风景，落花时节又逢君。"
    )
}