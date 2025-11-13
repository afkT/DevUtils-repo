package afkt.umshare

import dev.engine.DevEngine
import dev.umshare.UMShareEngine

object AppUMShare {

    fun engine(): UMShareEngine? {
        return DevEngine.getShare()?.let {
            it as? UMShareEngine
        }
    }
}