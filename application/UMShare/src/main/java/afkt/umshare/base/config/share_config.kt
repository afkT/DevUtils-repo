package afkt.umshare.base.config

import dev.module.share.ShareConfig

val shareConfig: ShareConfig by lazy {
    ShareConfig("", "", platformKey = ArrayList())
}