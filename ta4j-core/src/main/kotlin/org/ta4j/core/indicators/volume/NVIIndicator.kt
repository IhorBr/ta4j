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
 * Negative Volume Index (NVI) indicator.
 *
 * @see [
 * http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:negative_volume_inde](http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:negative_volume_inde)
 *
 * @see [
 * http://www.metastock.com/Customer/Resources/TAAZ/Default.aspx?p=75](http://www.metastock.com/Customer/Resources/TAAZ/Default.aspx?p=75)
 *
 * @see [
 * http://www.investopedia.com/terms/n/nvi.asp](http://www.investopedia.com/terms/n/nvi.asp)
 */
class NVIIndicator(series: BarSeries?) : RecursiveCachedIndicator<Num>(series) {
    override fun calculate(index: Int): Num {
        if (index == 0) {
            return numOf(1000)
        }
        val currentBar = barSeries!!.getBar(index)
        val previousBar = barSeries.getBar(index - 1)
        val previousValue = getValue(index - 1)
        if (currentBar.volume!!.isLessThan(previousBar.volume)) {
            val currentPrice = currentBar.closePrice!!
            val previousPrice = previousBar.closePrice!!
            val priceChangeRatio = (currentPrice - previousPrice) / previousPrice
            return previousValue + priceChangeRatio * previousValue
        }
        return previousValue
    }
}