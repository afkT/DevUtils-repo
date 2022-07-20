package dev.module.listener

import dev.engine.share.listener.ShareListener
import dev.module.share.ShareParams

/**
 * detail: 分享回调 封装接口
 * @author Ttt
 * 减少需要 ShareListener<ShareParams> 声明使用
 * 内部直接封装具体泛型类
 */
interface DevShareListener : ShareListener<ShareParams>