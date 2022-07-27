package afkt.db

import afkt.db.base.BaseActivity
import afkt.db.base.RouterPath
import afkt.db.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun isToolBar(): Boolean = false

    override fun baseLayoutId(): Int = R.layout.activity_main

    override fun initListener() {
        super.initListener()

        binding.vidRoom.setOnClickListener {
            routerActivity("Room", RouterPath.RoomActivity_PATH)
        }

        binding.vidGreenDao.setOnClickListener {
            routerActivity("GreenDAO", RouterPath.GreenDaoActivity_PATH)
        }
    }
}