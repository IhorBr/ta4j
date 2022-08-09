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
package org.ta4j.core.criteria.pnl

import org.junit.Assert
import org.junit.Test
import org.ta4j.core.BaseTradingRecord
import org.ta4j.core.CriterionFactory
import org.ta4j.core.TestUtils
import org.ta4j.core.Trade.Companion.buyAt
import org.ta4j.core.Trade.Companion.sellAt
import org.ta4j.core.TradingRecord
import org.ta4j.core.criteria.AbstractCriterionTest
import org.ta4j.core.mocks.MockBarSeries
import org.ta4j.core.num.Num
import java.util.function.Function

class ProfitLossPercentageCriterionTest(numFunction: Function<Number?, Num>) : AbstractCriterionTest(
    CriterionFactory { ProfitLossPercentageCriterion() }, numFunction
) {
    @Test
    fun calculateWithWinningLongPositions() {
        val series = MockBarSeries(numFunction, 100.0, 105.0, 110.0, 100.0, 95.0, 105.0)
        val tradingRecord: TradingRecord = BaseTradingRecord(
            buyAt(0, series), sellAt(2, series),
            buyAt(3, series), sellAt(5, series)
        )
        val profit = getCriterion()
        TestUtils.assertNumEquals(10 + 5, profit!!.calculate(series, tradingRecord))
    }

    @Test
    fun calculateWithLosingLongPositions() {
        val series = MockBarSeries(numFunction, 100.0, 95.0, 100.0, 80.0, 85.0, 70.0)
        val tradingRecord: TradingRecord = BaseTradingRecord(
            buyAt(0, series), sellAt(1, series),
            buyAt(2, series), sellAt(5, series)
        )
        val profit = getCriterion()
        TestUtils.assertNumEquals(-5 + -30, profit!!.calculate(series, tradingRecord))
    }

    @Test
    fun calculateWithOneWinningAndOneLosingLongPositions() {
        val series = MockBarSeries(numFunction, 100.0, 195.0, 100.0, 80.0, 85.0, 70.0)
        val tradingRecord: TradingRecord = BaseTradingRecord(
            buyAt(0, series), sellAt(1, series),
            buyAt(2, series), sellAt(5, series)
        )
        val profit = getCriterion()
        TestUtils.assertNumEquals(95 + -30, profit!!.calculate(series, tradingRecord))
    }

    @Test
    fun calculateWithWinningShortPositions() {
        val series = MockBarSeries(numFunction, 100.0, 90.0, 100.0, 95.0, 95.0, 100.0)
        val tradingRecord: TradingRecord = BaseTradingRecord(
            sellAt(0, series), buyAt(1, series),
            sellAt(2, series), buyAt(3, series)
        )
        val profit = getCriterion()
        TestUtils.assertNumEquals(10 + 5, profit!!.calculate(series, tradingRecord))
    }

    @Test
    fun calculateWithLosingShortPositions() {
        val series = MockBarSeries(numFunction, 100.0, 110.0, 100.0, 105.0, 95.0, 105.0)
        val tradingRecord: TradingRecord = BaseTradingRecord(
            sellAt(0, series), buyAt(1, series),
            sellAt(2, series), buyAt(3, series)
        )
        val profit = getCriterion()
        TestUtils.assertNumEquals(-10 - 5, profit!!.calculate(series, tradingRecord))
    }

    @Test
    fun betterThan() {
        val criterion = getCriterion()
        Assert.assertTrue(criterion!!.betterThan(numOf(50)!!, numOf(45)!!))
        Assert.assertFalse(criterion.betterThan(numOf(45)!!, numOf(50)!!))
    }

    @Test
    fun testCalculateOneOpenPositionShouldReturnZero() {
        openedPositionUtils.testCalculateOneOpenPositionShouldReturnExpectedValue(numFunction, getCriterion(), 0)
    }
}