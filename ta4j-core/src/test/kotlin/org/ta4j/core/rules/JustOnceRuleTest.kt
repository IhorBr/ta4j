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
package org.ta4j.core.rules

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class JustOnceRuleTest {
    private var rule: JustOnceRule? = null
    @Before
    fun setUp() {
        rule = JustOnceRule()
    }

    @Test
    fun isSatisfied() {
            Assert.assertTrue(rule!!.isSatisfied(10))
            Assert.assertFalse(rule!!.isSatisfied(11))
            Assert.assertFalse(rule!!.isSatisfied(12))
            Assert.assertFalse(rule!!.isSatisfied(13))
            Assert.assertFalse(rule!!.isSatisfied(14))
        }

    @Test
    fun isSatisfiedInReverseOrder() {
            Assert.assertTrue(rule!!.isSatisfied(5))
            Assert.assertFalse(rule!!.isSatisfied(2))
            Assert.assertFalse(rule!!.isSatisfied(1))
            Assert.assertFalse(rule!!.isSatisfied(0))
        }

    @Test
    fun isSatisfiedWithInnerSatisfiedRule() {
            val rule = JustOnceRule(BooleanRule(true))
            Assert.assertTrue(rule.isSatisfied(5))
            Assert.assertFalse(rule.isSatisfied(2))
            Assert.assertFalse(rule.isSatisfied(1))
            Assert.assertFalse(rule.isSatisfied(0))
        }

    @Test
    fun isSatisfiedWithInnerNonSatisfiedRule() {
            val rule = JustOnceRule(BooleanRule(false))
            Assert.assertFalse(rule.isSatisfied(5))
            Assert.assertFalse(rule.isSatisfied(2))
            Assert.assertFalse(rule.isSatisfied(1))
            Assert.assertFalse(rule.isSatisfied(0))
        }

    @Test
    fun isSatisfiedWithInnerRule() {
            val rule = JustOnceRule(FixedRule(1, 3, 5))
            Assert.assertFalse(rule.isSatisfied(0))
            Assert.assertTrue(rule.isSatisfied(1))
            Assert.assertFalse(rule.isSatisfied(2))
            Assert.assertFalse(rule.isSatisfied(3))
            Assert.assertFalse(rule.isSatisfied(4))
            Assert.assertFalse(rule.isSatisfied(5))
            Assert.assertFalse(rule.isSatisfied(1))
        }
}