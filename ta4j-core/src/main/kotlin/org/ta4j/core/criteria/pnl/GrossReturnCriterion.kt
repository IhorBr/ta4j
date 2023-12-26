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

import org.ta4j.core.BarSeries
import org.ta4j.core.Position
import org.ta4j.core.TradingRecord
import org.ta4j.core.criteria.AbstractAnalysisCriterion
import org.ta4j.core.num.*

/**
 * Gross return (in percentage) criterion (includes trading costs).
 *
 *
 *
 * The gross return of the provided [position(s)][Position] over the
 * provided [series][BarSeries].
 */
class GrossReturnCriterion : AbstractAnalysisCriterion() {
    override fun calculate(series: BarSeries, position: Position): Num {
        return calculateProfit(series, position)
    }

    override fun calculate(series: BarSeries, tradingRecord: TradingRecord): Num {
        return tradingRecord.positions
            .stream()
            .map { position: Position -> calculateProfit(series, position) }
            .reduce(series.numOf(1)) { obj: Num, multiplicand: Num -> obj.times(multiplicand) }
    }

    /** The higher the criterion value, the better.  */
    override fun betterThan(criterionValue1: Num, criterionValue2: Num): Boolean {
        return criterionValue1.isGreaterThan(criterionValue2)
    }

    /**
     * Calculates the gross return of a position (Buy and sell).
     *
     * @param series   a bar series
     * @param position a position
     * @return the gross return of the position
     */
    private fun calculateProfit(series: BarSeries?, position: Position): Num {
        return if (position.isClosed) {
            position.getGrossReturn(series)
        } else series!!.numOf(1)
    }
}