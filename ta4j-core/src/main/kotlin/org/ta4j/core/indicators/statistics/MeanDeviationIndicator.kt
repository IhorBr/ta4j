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
package org.ta4j.core.indicators.statistics

import org.ta4j.core.Indicator
import org.ta4j.core.indicators.CachedIndicator
import org.ta4j.core.indicators.SMAIndicator
import org.ta4j.core.num.*
import kotlin.math.max

/**
 * Mean deviation indicator.
 *
 * @see [
 * http://en.wikipedia.org/wiki/Mean_absolute_deviation.Average_absolute_deviation](http://en.wikipedia.org/wiki/Mean_absolute_deviation.Average_absolute_deviation)
 */
/**
 * Constructor.
 *
 * @param indicator the indicator
 * @param barCount  the time frame
 */
class MeanDeviationIndicator(private val indicator: Indicator<Num>, private val barCount: Int) : CachedIndicator<Num>(
    indicator
) {
    private val sma: SMAIndicator = SMAIndicator(indicator, barCount)

    override fun calculate(index: Int): Num {
        var absoluteDeviations = numOf(0)
        val average = sma[index]
        val startIndex = max(0, index - barCount + 1)
        val nbValues = index - startIndex + 1
        for (i in startIndex..index) {
            // For each period...
            absoluteDeviations += (indicator[i] - average).abs()
        }
        return absoluteDeviations / numOf(nbValues)
    }

    override fun toString(): String {
        return javaClass.simpleName + " barCount: " + barCount
    }
}