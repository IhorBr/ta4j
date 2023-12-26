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
package org.ta4j.core.indicators

import org.ta4j.core.BarSeries
import org.ta4j.core.indicators.helpers.HighPriceIndicator
import org.ta4j.core.indicators.helpers.HighestValueIndicator
import org.ta4j.core.indicators.helpers.LowPriceIndicator
import org.ta4j.core.indicators.helpers.LowestValueIndicator
import org.ta4j.core.num.*
import kotlin.math.log10

/**
 * The "CHOP" index is used to indicate side-ways markets see [https://www.tradingview.com/wiki/Choppiness_Index_(CHOP)](https://www.tradingview.com/wiki/Choppiness_Index_(CHOP))
 * 100++ * LOG10( SUM(ATR(1), n) / ( MaxHi(n) - MinLo(n) ) ) / LOG10(n) n = User
 * defined period length. LOG10(n) = base-10 LOG of n ATR(1) = Average True
 * Range (Period of 1) SUM(ATR(1), n) = Sum of the Average True Range over past
 * n bars MaxHi(n) = The highest high over past n bars
 *
 * ++ usually this index is between 0 and 100, but could be scaled differently
 * by the 'scaleTo' arg of the constructor
 *
 */
/**
 * Constructor.
 *
 * @param barSeries   the bar series [BarSeries]
 * @param ciTimeFrame time-frame often something like '14'
 * @param scaleTo     maximum value to scale this oscillator, usually '1' or '100'
 */


class ChopIndicator(barSeries: BarSeries?, ciTimeFrame: Int, scaleTo: Int) : CachedIndicator<Num>(barSeries) {
      private val  atrIndicator = ATRIndicator(barSeries, 1) // ATR(1) = Average True Range (Period of 1)
      private val  hvi = HighestValueIndicator(HighPriceIndicator(barSeries), ciTimeFrame)
      private val  lvi = LowestValueIndicator(LowPriceIndicator(barSeries), ciTimeFrame)
      private val  timeFrame = ciTimeFrame
      private val  log10n = numOf(log10(ciTimeFrame.toDouble()))
      private val  scaleUpTo = numOf(scaleTo)

    public override fun calculate(index: Int): Num {
        var summ = atrIndicator[index]
        for (i in 1 until timeFrame) {
            summ += atrIndicator[index - i]
        }
        val a = summ / (hvi[index] - lvi[index])
        // TODO: implement Num.log10(Num)
        return (scaleUpTo * numOf(log10(a.doubleValue()))) / log10n
    }
}