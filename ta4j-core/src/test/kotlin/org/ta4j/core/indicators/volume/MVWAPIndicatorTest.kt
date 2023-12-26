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

import org.junit.Before
import org.junit.Test
import org.ta4j.core.Bar
import org.ta4j.core.BarSeries
import org.ta4j.core.Indicator
import org.ta4j.core.TestUtils
import org.ta4j.core.indicators.AbstractIndicatorTest
import org.ta4j.core.mocks.MockBar
import org.ta4j.core.mocks.MockBarSeries
import org.ta4j.core.num.Num
import java.util.function.Function

class MVWAPIndicatorTest(numFunction: Function<Number?, Num>) :
    AbstractIndicatorTest<Indicator<Num>?, Num>(numFunction) {
    protected var data: BarSeries? = null
    @Before
    fun setUp() {
        val bars: MutableList<Bar> = ArrayList()
        bars.add(MockBar(44.98, 45.05, 45.17, 44.96, 1.0, numFunction))
        bars.add(MockBar(45.05, 45.10, 45.15, 44.99, 2.0, numFunction))
        bars.add(MockBar(45.11, 45.19, 45.32, 45.11, 1.0, numFunction))
        bars.add(MockBar(45.19, 45.14, 45.25, 45.04, 3.0, numFunction))
        bars.add(MockBar(45.12, 45.15, 45.20, 45.10, 1.0, numFunction))
        bars.add(MockBar(45.15, 45.14, 45.20, 45.10, 2.0, numFunction))
        bars.add(MockBar(45.13, 45.10, 45.16, 45.07, 1.0, numFunction))
        bars.add(MockBar(45.12, 45.15, 45.22, 45.10, 5.0, numFunction))
        bars.add(MockBar(45.15, 45.22, 45.27, 45.14, 1.0, numFunction))
        bars.add(MockBar(45.24, 45.43, 45.45, 45.20, 1.0, numFunction))
        bars.add(MockBar(45.43, 45.44, 45.50, 45.39, 1.0, numFunction))
        bars.add(MockBar(45.43, 45.55, 45.60, 45.35, 5.0, numFunction))
        bars.add(MockBar(45.58, 45.55, 45.61, 45.39, 7.0, numFunction))
        bars.add(MockBar(45.45, 45.01, 45.55, 44.80, 6.0, numFunction))
        bars.add(MockBar(45.03, 44.23, 45.04, 44.17, 1.0, numFunction))
        bars.add(MockBar(44.23, 43.95, 44.29, 43.81, 2.0, numFunction))
        bars.add(MockBar(43.91, 43.08, 43.99, 43.08, 1.0, numFunction))
        bars.add(MockBar(43.07, 43.55, 43.65, 43.06, 7.0, numFunction))
        bars.add(MockBar(43.56, 43.95, 43.99, 43.53, 6.0, numFunction))
        bars.add(MockBar(43.93, 44.47, 44.58, 43.93, 1.0, numFunction))
        data = MockBarSeries(bars)
    }

    @Test
    fun mvwap() {
        val vwap = VWAPIndicator(data, 5)
        val mvwap = MVWAPIndicator(vwap, 8)
        TestUtils.assertNumEquals(45.1271, mvwap.getValue(8))
        TestUtils.assertNumEquals(45.1399, mvwap.getValue(9))
        TestUtils.assertNumEquals(45.1530, mvwap.getValue(10))
        TestUtils.assertNumEquals(45.1790, mvwap.getValue(11))
        TestUtils.assertNumEquals(45.2227, mvwap.getValue(12))
        TestUtils.assertNumEquals(45.2533, mvwap.getValue(13))
        TestUtils.assertNumEquals(45.2769, mvwap.getValue(14))
        TestUtils.assertNumEquals(45.2844, mvwap.getValue(15))
        TestUtils.assertNumEquals(45.2668, mvwap.getValue(16))
        TestUtils.assertNumEquals(45.1386, mvwap.getValue(17))
        TestUtils.assertNumEquals(44.9487, mvwap.getValue(18))
    }
}