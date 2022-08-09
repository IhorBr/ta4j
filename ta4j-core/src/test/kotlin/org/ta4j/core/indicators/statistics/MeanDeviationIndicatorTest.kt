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

import org.junit.Before
import org.junit.Test
import org.ta4j.core.BarSeries
import org.ta4j.core.Indicator
import org.ta4j.core.TestUtils
import org.ta4j.core.indicators.AbstractIndicatorTest
import org.ta4j.core.indicators.helpers.ClosePriceIndicator
import org.ta4j.core.mocks.MockBarSeries
import org.ta4j.core.num.Num
import java.util.function.Function

class MeanDeviationIndicatorTest(numFunction: Function<Number?, Num>) :
    AbstractIndicatorTest<Indicator<Num>?, Num>(numFunction) {
    private var data: BarSeries? = null
    @Before
    fun setUp() {
        data = MockBarSeries(numFunction, 1.0, 2.0, 7.0, 6.0, 3.0, 4.0, 5.0, 11.0, 3.0, 0.0, 9.0)
    }

    @Test
    fun meanDeviationUsingBarCount5UsingClosePrice() {
        val meanDeviation = MeanDeviationIndicator(ClosePriceIndicator(data), 5)
        TestUtils.assertNumEquals(2.44444444444444, meanDeviation.getValue(2))
        TestUtils.assertNumEquals(2.5, meanDeviation.getValue(3))
        TestUtils.assertNumEquals(2.16, meanDeviation.getValue(7))
        TestUtils.assertNumEquals(2.32, meanDeviation.getValue(8))
        TestUtils.assertNumEquals(2.72, meanDeviation.getValue(9))
    }

    @Test
    fun firstValueShouldBeZero() {
        val meanDeviation = MeanDeviationIndicator(ClosePriceIndicator(data), 5)
        TestUtils.assertNumEquals(0, meanDeviation[0])
    }

    @Test
    fun meanDeviationShouldBeZeroWhenBarCountIs1() {
        val meanDeviation = MeanDeviationIndicator(ClosePriceIndicator(data), 1)
        TestUtils.assertNumEquals(0, meanDeviation.getValue(2))
        TestUtils.assertNumEquals(0, meanDeviation.getValue(7))
    }
}