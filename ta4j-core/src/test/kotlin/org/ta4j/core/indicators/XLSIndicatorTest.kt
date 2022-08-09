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
package org.ta4j.core.indicators

import org.ta4j.core.BarSeries
import org.ta4j.core.ExternalIndicatorTest
import org.ta4j.core.Indicator
import org.ta4j.core.XlsTestsUtils
import org.ta4j.core.num.Num
import java.util.function.Function

class XLSIndicatorTest
/**
 * Constructor.
 *
 * @param clazz    class containing the file resources
 * @param fileName file name of the file containing the workbook
 * @param column   column number containing the calculated indicator values
 */(
    private val clazz: Class<*>,
    private val fileName: String,
    private val column: Int,
    private val numFunction: Function<Number?, Num>
) : ExternalIndicatorTest {
    private var cachedSeries: BarSeries? = null

    /**
     * Gets the BarSeries from the XLS file.
     *
     * @return BarSeries from the file
     * @throws Exception if getSeries throws IOException or DataFormatException
     */
    @Throws(Exception::class)
    override fun getSeries(): BarSeries? {
        if (cachedSeries == null) {
            cachedSeries = XlsTestsUtils.getSeries(clazz, fileName, numFunction)
        }
        return cachedSeries
    }

    /**
     * Gets the Indicator from the XLS file given the parameters.
     *
     * @param params indicator parameters
     * @return Indicator from the file given the parameters
     * @throws Exception if getIndicator throws IOException or DataFormatException
     */
    @Throws(Exception::class)
    override fun getIndicator(vararg params: Any): Indicator<Num> {
        return XlsTestsUtils.getIndicator(clazz, fileName, column, getSeries()!!.function(), *params)
    }
}