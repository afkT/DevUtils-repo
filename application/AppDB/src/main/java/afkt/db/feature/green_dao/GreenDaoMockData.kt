package afkt.db.feature.green_dao

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
object GreenDaoMockData {

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
        "观黄山云海",
        "夏昼偶书",
        "秋夕寄怀",
        "登岳阳楼",
        "赠别",
        "山居即事",
        "夜雨述怀",
        "寒江独钓",
        "访友不遇",
        "戍妇吟",
        "秋收谣",
        "星河漫想",
        "骤雨初歇",
        "咏荷",
        "边城晓角",
        "长城谣",
        "夜泊秦淮",
        "空谷传音",
        "灞桥折柳",
        "中秋望月",
        "赤壁怀古",
        "三峡行舟",
        "洞庭晚眺",
        "塞上闻笛",
        "山行",
        "秋夜闻筝",
        "寒山拾句",
        "金陵怀古",
        "秋浦晚歌",
        "竹林清趣",
        "长安逢故人"
    )

    private val contents = arrayOf(
        "峰峦叠嶂锁烟霞，万壑奔雷走白沙。\n忽卷银涛吞日月，恍疑蜃阙落天涯。",
        "赤日炎炎午梦残，槐荫匝地午蝉喧。\n忽惊竹簟生凉意，知有清风过北轩。",
        "金风瑟瑟叩疏棂，素魄娟娟浸画屏。\n欲写秋心无着处，满庭梧叶落空庭。",
        "浩渺烟波接混茫，君山一点立苍茫。\n凭栏欲揽潇湘月，却恐清辉湿客裳。",
        "驿亭柳色蘸春溪，执手无言泪暗垂。\n莫道此生长契阔，江湖何处不相思。",
        "茅檐低小傍溪斜，竹杖芒鞋野径赊。\n最爱晚炊升碧霭，一篱豆架缀黄花。",
        "墨痕犹带砚池温，灯烬挑残夜已昏。\n忽听隔墙童稚语，方知檐溜涨前村。",
        "芦荻萧萧雪满汀，孤篷破笠立伶仃。\n寒江不冻钓竿稳，为有鱼龙夜出听。",
        "柴门寂寂掩莓苔，犬吠声中客未来。\n唯有阶前红杏蕊，迎风自向短墙开。",
        "锦字难凭雁足传，戍旗猎猎陇云边。\n夜深怕听孤城角，吹落灯花第几篇。",
        "金风拂穗稻粱肥，陌上村童叱犊归。\n老妪灶前炊黍熟，隔篱先唤阿爷回。",
        "银潢耿耿泻清辉，牛女迢迢隔岸矶。\n欲驾长鲸凌浩渺，手携星斗补天衣。",
        "黑云翻墨压城低，电掣雷轰裂石陂。\n忽转晴光悬素练，虹桥双跨碧琉璃。",
        "翠盖亭亭立晓风，胭脂浅淡映霞红。\n淤泥不染冰清质，留取芳心照水中。",
        "刁斗声声彻夜寒，戍楼遥指暮云残。\n征人莫唱阳关曲，塞草萋萋已及鞍。",
        "雉堞蜿蜒接大荒，戍旗猎猎卷斜阳。\n千年明月依然在，曾照秦皇射虎狼。",
        "烟笼寒水月笼沙，商女歌声透碧纱。\n六代豪华随逝水，空余白鹭啄芦芽。",
        "空山寂历起松涛，百丈飞泉挂翠旄。\n忽有樵歌穿谷至，惊飞幽鸟出林梢。",
        "灞桥杨柳绿毵毵，折尽柔条不忍堪。\n此去阳关千万里，春风应不到征衫。",
        "蟾光如水浸雕栏，万里清辉共倚看。\n今夜故园应更好，桂花香里话团圞。",
        "惊涛拍岸卷霜雪，故垒萧萧芦荻秋。\n周郎已矣江声在，依旧东风送客舟。",
        "瞿塘险处浪排空，十二峰青刺碧穹。\n忽转轻舟穿峡过，满江星斗落樯栊。",
        "气蒸云梦接苍冥，万顷琉璃落眼青。\n欲唤鸬鹚同载酒，醉眠沙渚数鸥汀。",
        "羌管悠悠霜满地，征人遥望玉门关。\n梅花落尽江南信，一夜相思损客颜。",
        "石磴萦纡入翠微，白云深处扣岩扉。\n山僧煮茗留题处，满壁苔痕认旧题。",
        "冰弦乍拨凤求凰，哀怨分明断客肠。\n十二阑干都拍遍，不知何处觅潇湘。",
        "危岩古木挂藤萝，石径苔深屐印多。\n忽见野僧持锡至，笑谈云外有松窝。",
        "朱雀桥边野草花，乌衣巷口夕阳斜。\n旧时王谢堂前燕，飞入寻常百姓家。",
        "秋浦澄波漾夕晖，蓼花红处雁初飞。\n渔翁醉把鲈鱼脍，笑指沧洲白鹭矶。",
        "独坐幽篁抚素琴，松风谡谡和清音。\n偶然得句无人会，付与山禽自在吟。",
        "曲江池畔柳毵毵，二十年前共盍簪。\n今日相逢须尽醉，莫教铜狄笑尘凡。"
    )
}