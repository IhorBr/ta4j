/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 Ta4j Organization & respective
 * authors (see AUTHORS)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.ta4j.core

import org.junit.Assert
import org.junit.Test
import org.ta4j.core.indicators.AbstractIndicatorTest
import org.ta4j.core.num.Num
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.function.Function

class BaseBarBuilderTest(numFunction: Function<Number?, Num>) : AbstractIndicatorTest<BarSeries?, Num>(numFunction) {
    @Test
    fun testBuildBar() {
        val beginTime = ZonedDateTime.of(2014, 6, 25, 0, 0, 0, 0, ZoneId.systemDefault())
        val endTime = ZonedDateTime.of(2014, 6, 25, 1, 0, 0, 0, ZoneId.systemDefault())
        val duration = Duration.between(beginTime, endTime)
        val bar = BaseBarBuilder().timePeriod(duration)
            .endTime(endTime)
            .openPrice(numOf(101))
            .highPrice(numOf(103))
            .lowPrice(numOf(100))
            .closePrice(numOf(102))
            .trades(4)
            .volume(numOf(40))
            .amount(numOf(4020))
            .build()
        Assert.assertEquals(duration, bar.timePeriod)
        Assert.assertEquals(beginTime, bar.beginTime)
        Assert.assertEquals(endTime, bar.endTime)
        Assert.assertEquals(numOf(101), bar.openPrice)
        Assert.assertEquals(numOf(103), bar.highPrice)
        Assert.assertEquals(numOf(100), bar.lowPrice)
        Assert.assertEquals(numOf(102), bar.closePrice)
        Assert.assertEquals(4, bar.trades)
        Assert.assertEquals(numOf(40), bar.volume)
        Assert.assertEquals(numOf(4020), bar.amount)
    }
}