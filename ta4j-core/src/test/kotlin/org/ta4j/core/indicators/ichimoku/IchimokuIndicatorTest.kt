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
package org.ta4j.core.indicators.ichimoku

import org.junit.Before
import org.junit.Test
import org.ta4j.core.Bar
import org.ta4j.core.BarSeries
import org.ta4j.core.Indicator
import org.ta4j.core.TestUtils
import org.ta4j.core.indicators.AbstractIndicatorTest
import org.ta4j.core.mocks.MockBar
import org.ta4j.core.mocks.MockBarSeries
import org.ta4j.core.num.NaN
import org.ta4j.core.num.Num
import java.util.function.Function

class IchimokuIndicatorTest(numFunction: Function<Number?, Num>) :
    AbstractIndicatorTest<Indicator<Num>?, Num>(numFunction) {
    protected var data: BarSeries? = null
    @Before
    fun setUp() {
        val bars: MutableList<Bar> = ArrayList()
        bars.add(MockBar(44.98, 45.05, 45.17, 44.96, numFunction))
        bars.add(MockBar(45.05, 45.10, 45.15, 44.99, numFunction))
        bars.add(MockBar(45.11, 45.19, 45.32, 45.11, numFunction))
        bars.add(MockBar(45.19, 45.14, 45.25, 45.04, numFunction))
        bars.add(MockBar(45.12, 45.15, 45.20, 45.10, numFunction))
        bars.add(MockBar(45.15, 45.14, 45.20, 45.10, numFunction))
        bars.add(MockBar(45.13, 45.10, 45.16, 45.07, numFunction))
        bars.add(MockBar(45.12, 45.15, 45.22, 45.10, numFunction))
        bars.add(MockBar(45.15, 45.22, 45.27, 45.14, numFunction))
        bars.add(MockBar(45.24, 45.43, 45.45, 45.20, numFunction))
        bars.add(MockBar(45.43, 45.44, 45.50, 45.39, numFunction))
        bars.add(MockBar(45.43, 45.55, 45.60, 45.35, numFunction))
        bars.add(MockBar(45.58, 45.55, 45.61, 45.39, numFunction))
        bars.add(MockBar(45.45, 45.01, 45.55, 44.80, numFunction))
        bars.add(MockBar(45.03, 44.23, 45.04, 44.17, numFunction))
        bars.add(MockBar(44.23, 43.95, 44.29, 43.81, numFunction))
        bars.add(MockBar(43.91, 43.08, 43.99, 43.08, numFunction))
        bars.add(MockBar(43.07, 43.55, 43.65, 43.06, numFunction))
        bars.add(MockBar(43.56, 43.95, 43.99, 43.53, numFunction))
        bars.add(MockBar(43.93, 44.47, 44.58, 43.93, numFunction))
        data = MockBarSeries(bars)
    }

    @Test
    fun ichimoku() {
        val tenkanSen = IchimokuTenkanSenIndicator(data, 3)
        val kijunSen = IchimokuKijunSenIndicator(data, 5)
        val senkouSpanA = IchimokuSenkouSpanAIndicator(data, tenkanSen, kijunSen, 5)
        val senkouSpanB = IchimokuSenkouSpanBIndicator(data, 9, 5)
        val chikouSpanTimeDelay = 5
        val chikouSpan = IchimokuChikouSpanIndicator(data, chikouSpanTimeDelay)
        TestUtils.assertNumEquals(45.155, tenkanSen.getValue(3))
        TestUtils.assertNumEquals(45.18, tenkanSen.getValue(4))
        TestUtils.assertNumEquals(45.145, tenkanSen.getValue(5))
        TestUtils.assertNumEquals(45.135, tenkanSen.getValue(6))
        TestUtils.assertNumEquals(45.145, tenkanSen.getValue(7))
        TestUtils.assertNumEquals(45.17, tenkanSen.getValue(8))
        TestUtils.assertNumEquals(44.06, tenkanSen.getValue(16))
        TestUtils.assertNumEquals(43.675, tenkanSen.getValue(17))
        TestUtils.assertNumEquals(43.525, tenkanSen.getValue(18))
        TestUtils.assertNumEquals(45.14, kijunSen.getValue(3))
        TestUtils.assertNumEquals(45.14, kijunSen.getValue(4))
        TestUtils.assertNumEquals(45.155, kijunSen.getValue(5))
        TestUtils.assertNumEquals(45.18, kijunSen.getValue(6))
        TestUtils.assertNumEquals(45.145, kijunSen.getValue(7))
        TestUtils.assertNumEquals(45.17, kijunSen.getValue(8))
        TestUtils.assertNumEquals(44.345, kijunSen.getValue(16))
        TestUtils.assertNumEquals(44.305, kijunSen.getValue(17))
        TestUtils.assertNumEquals(44.05, kijunSen.getValue(18))
        TestUtils.assertNumEquals(NaN.NaN, senkouSpanA.getValue(3))
        TestUtils.assertNumEquals(45.065, senkouSpanA.getValue(4))
        TestUtils.assertNumEquals(45.1475, senkouSpanA.getValue(7))
        TestUtils.assertNumEquals(45.16, senkouSpanA.getValue(8))
        TestUtils.assertNumEquals(45.15, senkouSpanA.getValue(9))
        TestUtils.assertNumEquals(45.1575, senkouSpanA.getValue(10))
        TestUtils.assertNumEquals(45.4275, senkouSpanA.getValue(16))
        TestUtils.assertNumEquals(45.205, senkouSpanA.getValue(17))
        TestUtils.assertNumEquals(44.89, senkouSpanA.getValue(18))
        TestUtils.assertNumEquals(NaN.NaN, senkouSpanB.getValue(3))
        TestUtils.assertNumEquals(45.065, senkouSpanB.getValue(4))
        TestUtils.assertNumEquals(45.065, senkouSpanB.getValue(5))
        TestUtils.assertNumEquals(45.14, senkouSpanB.getValue(6))
        TestUtils.assertNumEquals(45.14, senkouSpanB.getValue(7))
        TestUtils.assertNumEquals(45.22, senkouSpanB.getValue(13))
        TestUtils.assertNumEquals(45.34, senkouSpanB.getValue(16))
        TestUtils.assertNumEquals(45.205, senkouSpanB.getValue(17))
        TestUtils.assertNumEquals(44.89, senkouSpanB.getValue(18))
        TestUtils.assertNumEquals(data!!.getBar(chikouSpanTimeDelay).closePrice, chikouSpan[0])
        TestUtils.assertNumEquals(data!!.getBar(1 + chikouSpanTimeDelay).closePrice, chikouSpan.getValue(1))
        TestUtils.assertNumEquals(data!!.getBar(2 + chikouSpanTimeDelay).closePrice, chikouSpan.getValue(2))
        TestUtils.assertNumEquals(data!!.getBar(3 + chikouSpanTimeDelay).closePrice, chikouSpan.getValue(3))
        TestUtils.assertNumEquals(data!!.getBar(4 + chikouSpanTimeDelay).closePrice, chikouSpan.getValue(4))
        TestUtils.assertNumEquals(data!!.getBar(5 + chikouSpanTimeDelay).closePrice, chikouSpan.getValue(5))
        TestUtils.assertNumEquals(data!!.getBar(6 + chikouSpanTimeDelay).closePrice, chikouSpan.getValue(6))
        TestUtils.assertNumEquals(data!!.getBar(7 + chikouSpanTimeDelay).closePrice, chikouSpan.getValue(7))
        TestUtils.assertNumEquals(data!!.getBar(8 + chikouSpanTimeDelay).closePrice, chikouSpan.getValue(8))
        TestUtils.assertNumEquals(data!!.getBar(9 + chikouSpanTimeDelay).closePrice, chikouSpan.getValue(9))
        TestUtils.assertNumEquals(data!!.getBar(10 + chikouSpanTimeDelay).closePrice, chikouSpan.getValue(10))
        TestUtils.assertNumEquals(data!!.getBar(11 + chikouSpanTimeDelay).closePrice, chikouSpan.getValue(11))
        TestUtils.assertNumEquals(data!!.getBar(12 + chikouSpanTimeDelay).closePrice, chikouSpan.getValue(12))
        TestUtils.assertNumEquals(data!!.getBar(13 + chikouSpanTimeDelay).closePrice, chikouSpan.getValue(13))
        TestUtils.assertNumEquals(data!!.getBar(14 + chikouSpanTimeDelay).closePrice, chikouSpan.getValue(14))
        TestUtils.assertNumEquals(NaN.NaN, chikouSpan.getValue(15))
        TestUtils.assertNumEquals(NaN.NaN, chikouSpan.getValue(16))
        TestUtils.assertNumEquals(NaN.NaN, chikouSpan.getValue(17))
        TestUtils.assertNumEquals(NaN.NaN, chikouSpan.getValue(18))
        TestUtils.assertNumEquals(NaN.NaN, chikouSpan.getValue(19))
    }
}