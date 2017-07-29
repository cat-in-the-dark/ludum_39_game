package org.catinthedark.vvtf.server

import org.catinthedark.shared.invokers.DeferrableInvoker
import org.catinthedark.shared.invokers.SimpleInvoker
import org.catinthedark.shared.invokers.TickInvoker

object Consts {
    val invoker: DeferrableInvoker = SimpleInvoker()
    val gameInvoker: DeferrableInvoker = TickInvoker()
}