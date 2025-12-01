package afkt.db.feature.room

import afkt.db.AppRouter
import afkt.db.BR
import afkt.db.R
import afkt.db.base.BaseActivity
import afkt.db.base.BaseViewModel
import afkt.db.databinding.ActivityRoomBinding
import android.view.View
import com.therouter.router.Route
import dev.base.simple.extensions.asActivity

/**
 * detail: Room 使用
 * @author Ttt
 */
@Route(path = AppRouter.PATH_ROOM_ACTIVITY)
class RoomActivity : BaseActivity<ActivityRoomBinding, RoomViewModel>(
    R.layout.activity_room, BR.viewModel, simple_Agile = { act ->
        act.asActivity<RoomActivity> {
            binding.vidTitle.leftView.setOnClickListener { finish() }
        }
    }
)

class RoomViewModel : BaseViewModel() {

    val clickAdd = View.OnClickListener {
    }
}