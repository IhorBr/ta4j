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

import org.ta4j.core.AnalysisCriterion
import org.ta4j.core.BarSeries
import org.ta4j.core.Position
import org.ta4j.core.TradingRecord
import org.ta4j.core.criteria.AbstractAnalysisCriterion
import org.ta4j.core.criteria.NumberOfPositionsCriterion
import org.ta4j.core.num.*

/**
 * Variance criterion.
 *
 *
 *
 * Calculates the standard deviation for a Criterion.
 */
class VarianceCriterion
/**
 * Constructor.
 *
 * @param criterion the criterion from which the "variance" is calculated
 */(private val criterion: AnalysisCriterion) : AbstractAnalysisCriterion() {
    private val numberOfPositionsCriterion = NumberOfPositionsCriterion()
    override fun calculate(series: BarSeries, position: Position): Num {
        val criterionValue = criterion.calculate(series, position)
        val numberOfPositions = numberOfPositionsCriterion.calculate(series, position)
        var variance = series.numOf(0)
        val average = criterionValue.div(numberOfPositions)
        val pow = criterion.calculate(series, position).minus(average).pow(2)
        variance = variance.plus(pow)
        variance = variance.div(numberOfPositions)
        return variance
    }

    override fun calculate(series: BarSeries, tradingRecord: TradingRecord): Num {
        if (tradingRecord.positions.isEmpty()) {
            return series.numOf(0)
        }
        val criterionValue = criterion.calculate(series, tradingRecord)
        val numberOfPositions = numberOfPositionsCriterion.calculate(series, tradingRecord)
        var variance = series.numOf(0)
        val average = criterionValue.div(numberOfPositions)
        for (position in tradingRecord.positions) {
            val pow = criterion.calculate(series, position).minus(average).pow(2)
            variance = variance.plus(pow)
        }
        variance = variance.div(numberOfPositions)
        return variance
    }

    /** The higher the criterion value, the better.  */
    override fun betterThan(criterionValue1: Num, criterionValue2: Num): Boolean {
        return criterionValue1.isGreaterThan(criterionValue2)
    }
}