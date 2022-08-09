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
package org.ta4j.core.indicators.volume

import org.ta4j.core.BarSeries
import org.ta4j.core.indicators.RecursiveCachedIndicator
import org.ta4j.core.num.*

/**
 * Positive Volume Index (PVI) indicator.
 *
 * @see [
 * http://www.metastock.com/Customer/Resources/TAAZ/Default.aspx?p=92](http://www.metastock.com/Customer/Resources/TAAZ/Default.aspx?p=92)
 *
 * @see [
 * http://www.investopedia.com/terms/p/pvi.asp](http://www.investopedia.com/terms/p/pvi.asp)
 */
class PVIIndicator(series: BarSeries?) : RecursiveCachedIndicator<Num>(series) {
    override fun calculate(index: Int): Num {
        if (index == 0) {
            return numOf(1000)
        }
        val currentBar = barSeries!!.getBar(index)
        val previousBar = barSeries.getBar(index - 1)
        val previousValue = getValue(index - 1)
        if (currentBar.volume!!.isGreaterThan(previousBar.volume)) {
            val currentPrice = currentBar.closePrice!!
            val previousPrice = previousBar.closePrice!!
            val priceChangeRatio = (currentPrice - previousPrice) / previousPrice
            return previousValue + priceChangeRatio * previousValue
        }
        return previousValue
    }
}