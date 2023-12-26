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
package org.ta4j.core.indicators.helpers

import org.junit.Test
import org.ta4j.core.BarSeries
import org.ta4j.core.Indicator
import org.ta4j.core.TestUtils
import org.ta4j.core.indicators.AbstractIndicatorTest
import org.ta4j.core.mocks.MockBarSeries
import org.ta4j.core.num.NaN
import org.ta4j.core.num.Num
import java.util.function.Function

class DifferencePercentageIndicatorTest(numFunction: Function<Number?, Num>) :
    AbstractIndicatorTest<Indicator<Num>?, Num>(numFunction) {
    private var percentageChangeIndicator: DifferencePercentageIndicator? = null
    @Test
    fun getValueWithoutThreshold() {
        val series: BarSeries = MockBarSeries(numFunction)
        val mockIndicator = FixedIndicator(
            series, numOf(100), numOf(101), numOf(98.98),
            numOf(102.186952), numOf(91.9682568), numOf(100.5213046824), numOf(101.526517729224)
        )
        percentageChangeIndicator = DifferencePercentageIndicator(mockIndicator)
        TestUtils.assertNumEquals(NaN.NaN, percentageChangeIndicator!![0])
        TestUtils.assertNumEquals(numOf(1), percentageChangeIndicator!!.getValue(1))
        TestUtils.assertNumEquals(numOf(-2), percentageChangeIndicator!!.getValue(2))
        TestUtils.assertNumEquals(numOf(3.24), percentageChangeIndicator!!.getValue(3))
        TestUtils.assertNumEquals(numOf(-10), percentageChangeIndicator!!.getValue(4))
        TestUtils.assertNumEquals(numOf(9.3), percentageChangeIndicator!!.getValue(5))
        TestUtils.assertNumEquals(numOf(1), percentageChangeIndicator!!.getValue(6))
    }

    @Test
    fun getValueWithNumThreshold() {
        val series: BarSeries = MockBarSeries(numFunction)
        val mockIndicator = FixedIndicator(
            series, numOf(1000), numOf(1010), numOf(1020),
            numOf(1050), numOf(1060.5), numOf(1081.5), numOf(1102.5), numOf(1091.475), numOf(1113.525),
            numOf(1036.35), numOf(1067.4405)
        )
        percentageChangeIndicator = DifferencePercentageIndicator(mockIndicator, numOf(5))
        TestUtils.assertNumEquals(NaN.NaN, percentageChangeIndicator!![0])
        TestUtils.assertNumEquals(numOf(1), percentageChangeIndicator!!.getValue(1))
        TestUtils.assertNumEquals(numOf(2), percentageChangeIndicator!!.getValue(2))
        TestUtils.assertNumEquals(numOf(5), percentageChangeIndicator!!.getValue(3))
        TestUtils.assertNumEquals(numOf(1), percentageChangeIndicator!!.getValue(4))
        TestUtils.assertNumEquals(numOf(3), percentageChangeIndicator!!.getValue(5))
        TestUtils.assertNumEquals(numOf(5), percentageChangeIndicator!!.getValue(6))
        TestUtils.assertNumEquals(numOf(-1), percentageChangeIndicator!!.getValue(7))
        TestUtils.assertNumEquals(numOf(1), percentageChangeIndicator!!.getValue(8))
        TestUtils.assertNumEquals(numOf(-6), percentageChangeIndicator!!.getValue(9))
        TestUtils.assertNumEquals(numOf(3), percentageChangeIndicator!!.getValue(10))
    }

    @Test
    fun getValueWithNumberThreshold() {
        val series: BarSeries = MockBarSeries(numFunction)
        val mockIndicator = FixedIndicator(
            series, numOf(1000), numOf(1000), numOf(1010),
            numOf(1025), numOf(1038.325)
        )
        percentageChangeIndicator = DifferencePercentageIndicator(mockIndicator, 1.5)
        TestUtils.assertNumEquals(NaN.NaN, percentageChangeIndicator!![0])
        TestUtils.assertNumEquals(numOf(0), percentageChangeIndicator!!.getValue(1))
        TestUtils.assertNumEquals(numOf(1), percentageChangeIndicator!!.getValue(2))
        TestUtils.assertNumEquals(numOf(2.5), percentageChangeIndicator!!.getValue(3))
        TestUtils.assertNumEquals(numOf(1.3), percentageChangeIndicator!!.getValue(4))
    }
}