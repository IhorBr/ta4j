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
package org.ta4j.core.indicators.candles

import org.ta4j.core.BarSeries
import org.ta4j.core.Indicator
import org.ta4j.core.indicators.CachedIndicator
import org.ta4j.core.indicators.SMAIndicator
import org.ta4j.core.indicators.helpers.TransformIndicator
import org.ta4j.core.num.*

/**
 * Doji indicator.
 *
 * A candle/bar is considered Doji if its body height is lower than the average
 * multiplied by a factor.
 *
 * @see [
 * http://stockcharts.com/school/doku.php?id=chart_school:chart_analysis:introduction_to_candlesticks.doji](http://stockcharts.com/school/doku.php?id=chart_school:chart_analysis:introduction_to_candlesticks.doji)
 */
/**
 * Constructor.
 *
 * @param series     the bar series
 * @param barCount   the number of bars used to calculate the average body
 * height
 * @param bodyFactor the factor used when checking if a candle is Doji
 */

class DojiIndicator(series: BarSeries?, barCount: Int, bodyFactor: Double) : CachedIndicator<Boolean>(series) {
    /**
     * Body height
     */
    private val bodyHeightInd: Indicator<Num>

    /**
     * Average body height
     */
    private val averageBodyHeightInd: SMAIndicator
    private val factor: Num

    init {
        bodyHeightInd = TransformIndicator.abs(RealBodyIndicator(series))
        averageBodyHeightInd = SMAIndicator(bodyHeightInd, barCount)
        factor = numOf(bodyFactor)
    }

    override fun calculate(index: Int): Boolean {
        if (index < 1) {
            return bodyHeightInd[index].isZero
        }
        val averageBodyHeight = averageBodyHeightInd[index-1]
        val currentBodyHeight = bodyHeightInd[index]
        return currentBodyHeight.isLessThan(averageBodyHeight.times(factor))
    }
}