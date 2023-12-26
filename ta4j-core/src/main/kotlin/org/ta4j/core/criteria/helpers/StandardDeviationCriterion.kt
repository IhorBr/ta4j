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
package org.ta4j.core.criteria.helpers

import org.ta4j.core.*
import org.ta4j.core.criteria.AbstractAnalysisCriterion
import org.ta4j.core.num.*

/**
 * Standard deviation criterion.
 *
 *
 *
 * Calculates the standard deviation for a Criterion.
 */
/**
 * Constructor.
 *
 * @param criterion the criterion from which the "standard deviation" is
 * calculated
 */

class StandardDeviationCriterion(criterion: AnalysisCriterion) : AbstractAnalysisCriterion() {
    private val varianceCriterion = VarianceCriterion(criterion)


    override fun calculate(series: BarSeries, position: Position): Num {
        return varianceCriterion.calculate(series, position).sqrt()
    }

    override fun calculate(series: BarSeries, tradingRecord: TradingRecord): Num {
        return if (tradingRecord.positions.isEmpty()) {
            series.numOf(0)
        } else varianceCriterion.calculate(series, tradingRecord).sqrt()
    }

    /** The higher the criterion value, the better.  */
    override fun betterThan(criterionValue1: Num, criterionValue2: Num): Boolean {
        return criterionValue1.isGreaterThan(criterionValue2)
    }
}