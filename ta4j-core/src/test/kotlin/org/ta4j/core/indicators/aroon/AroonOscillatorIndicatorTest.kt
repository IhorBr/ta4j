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
package org.ta4j.core.indicators.aroon

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.ta4j.core.BarSeries
import org.ta4j.core.BaseBarSeries
import org.ta4j.core.TestUtils
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class AroonOscillatorIndicatorTest {
    private var data: BarSeries? = null
    @Before
    fun init() {
        val rawData =  // fb_daily, 2017/01/03 - 2017/08/18
            // date, close, volume, open, high, low
            """
            2017/08/18,167.4100,15065590.0000,166.8400,168.6700,166.2100
            2017/08/17,166.9100,17009420.0000,169.3400,169.8600,166.8500
            2017/08/16,170.0000,15732840.0000,171.2500,171.3800,169.2400
            2017/08/15,171.0000,8713651.0000,171.4900,171.5000,170.0100
            2017/08/14,170.7500,12940140.0000,170.0900,171.0800,169.2900
            2017/08/11,168.0800,13778190.0000,167.9500,168.8200,166.8500
            2017/08/10,167.4000,20551720.0000,170.0600,170.5900,166.8500
            2017/08/09,171.1800,10800730.0000,169.9800,171.4500,169.5600
            2017/08/08,171.2300,14317200.0000,171.8800,173.0500,170.6200
            2017/08/07,171.9800,12765230.0000,169.9500,172.0600,169.6600
            2017/08/04,169.6200,10505980.0000,168.9700,170.0600,168.6900
            2017/08/03,168.5900,10519470.0000,169.3000,169.7000,168.2500
            2017/08/02,169.3000,17181110.0000,170.3000,170.5500,166.9100
            2017/08/01,169.8600,14286110.0000,169.8200,170.4700,169.0500
            2017/07/31,169.2500,25429310.0000,172.0000,172.7200,168.5500
            2017/07/28,172.4500,24546680.0000,169.0700,173.4300,169.0500
            2017/07/27,170.4400,68454830.0000,174.7000,175.4900,167.5000
            2017/07/26,165.6100,32989200.0000,166.0100,166.0100,164.1000
            2017/07/25,165.2800,15309000.0000,165.0100,165.5400,163.8619
            2017/07/24,166.0000,17208500.0000,164.6400,166.1700,164.3100
            2017/07/21,164.4300,14712450.0000,164.1600,165.0500,163.7500
            2017/07/20,164.5300,18409730.0000,164.8000,165.0000,162.8100
            2017/07/19,164.1400,26144510.0000,163.5900,165.7000,163.1700
            2017/07/18,162.8600,23686800.0000,159.6600,163.7300,159.4200
            2017/07/17,159.7300,12757430.0000,160.2500,160.7800,158.8100
            2017/07/14,159.9700,16305570.0000,160.1300,160.3200,159.3254
            2017/07/13,159.2600,13912150.0000,158.7400,159.7800,158.4227
            2017/07/12,158.9000,22710090.0000,156.4900,159.1600,156.2000
            2017/07/11,155.2700,13652540.0000,153.3700,155.4225,152.9100
            2017/07/10,153.5000,13326410.0000,151.6900,153.9800,151.5100
            2017/07/07,151.4400,13249750.0000,149.2500,151.9900,149.1900
            2017/07/06,148.8200,14934450.0000,149.0300,150.0400,148.0000
            2017/07/05,150.3400,14321330.0000,149.0000,150.8500,148.1300
            2017/07/03,148.4300,13862740.0000,151.7200,152.1500,147.8000
            2017/06/30,150.9800,14909670.0000,151.9000,151.9200,150.0600
            2017/06/29,151.0400,23948630.0000,152.2800,152.5000,148.9175
            2017/06/28,153.2400,16696230.0000,150.9200,153.4700,149.8600
            2017/06/27,150.5800,19351560.0000,152.8400,153.3100,150.3900
            2017/06/26,153.5900,18030200.0000,156.2500,156.5000,153.1954
            2017/06/23,155.0700,17897280.0000,152.7200,155.2000,152.6500
            2017/06/22,153.4000,12835380.0000,153.0100,154.5500,152.9100
            2017/06/21,153.9100,14943770.0000,152.3600,154.0800,151.8800
            2017/06/20,152.2500,14733590.0000,152.8800,153.8400,152.2100
            2017/06/19,152.8700,18944190.0000,151.7100,153.5700,151.7100
            2017/06/16,150.6400,22843010.0000,149.5900,150.8300,148.6000
            2017/06/15,149.8000,18956910.0000,147.6700,150.0366,146.3747
            2017/06/14,150.2500,20774910.0000,151.2600,152.4000,149.0500
            2017/06/13,150.6800,20474720.0000,150.1500,151.1800,148.9000
            2017/06/12,148.4400,33150610.0000,148.1700,149.1950,144.5600
            2017/06/09,149.6000,35541670.0000,154.7700,155.5900,146.6100
            2017/06/08,154.7100,17795860.0000,154.0800,154.7300,153.1000
            2017/06/07,153.1200,12040430.0000,153.2700,153.7500,152.3400
            2017/06/06,152.8100,13443610.0000,153.4100,154.5200,152.4800
            2017/06/05,153.6300,12504820.0000,153.6400,154.7088,153.4100
            2017/06/02,153.6100,16838650.0000,151.8500,153.6300,151.3000
            2017/06/01,151.5300,14511530.0000,151.7500,152.2900,150.3000
            2017/05/31,151.4600,18001140.0000,152.7000,153.3500,151.0900
            2017/05/30,152.3800,13213700.0000,151.9700,152.9000,151.6400
            2017/05/26,152.1300,14984540.0000,152.2300,152.2500,151.1500
            2017/05/25,151.9600,19827210.0000,150.3000,152.5900,149.9500
            2017/05/24,150.0400,17914740.0000,148.5100,150.2300,148.4200
            2017/05/23,148.0700,12807840.0000,148.5200,148.8100,147.2500
            2017/05/22,148.2400,12578390.0000,148.0800,148.5900,147.6900
            2017/05/19,148.0600,16165160.0000,148.4450,149.3900,147.9600
            2017/05/18,147.6600,22778870.0000,144.7200,148.1500,144.5100
            2017/05/17,144.8500,28232610.0000,148.0000,148.6700,144.4216
            2017/05/16,149.7800,14506870.0000,150.1100,150.2100,149.0300
            2017/05/15,150.1900,14952950.0000,150.1700,151.4800,149.7700
            2017/05/12,150.3300,9584769.0000,150.4000,150.4600,149.6300
            2017/05/11,150.0400,11832750.0000,150.3100,150.6500,149.4100
            2017/05/10,150.2900,11994140.0000,150.2300,150.5200,148.8600
            2017/05/09,150.4800,17381800.0000,151.4900,152.5900,150.2100
            2017/05/08,151.0600,15813350.0000,150.7100,151.0800,149.7400
            2017/05/05,150.2400,17104730.0000,151.4500,151.6300,149.7900
            2017/05/04,150.8500,36185180.0000,150.1700,151.5200,148.7200
            2017/05/03,151.8000,28301550.0000,153.6000,153.6000,151.3400
            2017/05/02,152.7800,21617190.0000,153.3400,153.4400,151.6600
            2017/05/01,152.4600,25170200.0000,151.7400,152.5700,151.4200
            2017/04/28,150.2500,30607510.0000,149.5000,151.5300,149.0700
            2017/04/27,147.7000,11072600.0000,146.6700,147.7500,146.1400
            2017/04/26,146.5600,12388100.0000,147.0900,147.5900,146.0900
            2017/04/25,146.4900,17718030.0000,145.7900,147.1500,145.7898
            2017/04/24,145.4700,14397480.0000,144.9600,145.6738,144.3400
            2017/04/21,143.6800,12172640.0000,143.9000,144.1700,142.2700
            2017/04/20,143.8000,15948700.0000,142.9500,144.2500,142.6890
            2017/04/19,142.2700,15544500.0000,141.3500,143.0400,141.2700
            2017/04/18,140.9600,14795020.0000,141.2700,141.9050,140.6100
            2017/04/17,141.4200,11488510.0000,139.7600,141.5500,139.7500
            2017/04/13,139.3900,10939950.0000,139.6200,140.5800,139.3300
            2017/04/12,139.5800,11600930.0000,139.7200,140.4000,139.4400
            2017/04/11,139.9200,16627940.0000,140.8000,141.0299,138.8100
            2017/04/10,141.0400,9033921.0000,141.0000,141.4300,140.6300
            2017/04/07,140.7800,11811450.0000,141.2000,141.5500,140.2400
            2017/04/06,141.1700,15089610.0000,142.1100,142.2200,140.9100
            2017/04/05,141.8500,17132150.0000,142.2600,143.4400,141.2900
            2017/04/04,141.7300,11934110.0000,141.8600,142.0900,141.2700
            2017/04/03,142.2800,13552550.0000,141.9300,142.4700,140.8200
            2017/03/31,142.0500,11366390.0000,142.3100,142.6300,141.8900
            2017/03/30,142.4100,12393610.0000,142.4000,142.9500,141.8500
            2017/03/29,142.6500,16504590.0000,141.9900,142.8600,141.4300
            2017/03/28,141.7600,14660670.0000,140.3600,141.9500,140.0100
            2017/03/27,140.3200,12791820.0000,139.0500,140.6500,138.7700
            2017/03/24,140.3400,16636900.0000,140.0800,141.0244,139.7600
            2017/03/23,139.5300,12992700.0000,139.4800,140.3900,139.0900
            2017/03/22,139.5900,17020610.0000,137.9300,139.7900,137.6000
            2017/03/21,138.5100,29855980.0000,141.1500,142.3082,138.4000
            2017/03/20,139.9400,12002150.0000,139.7100,140.1900,139.2500
            2017/03/17,139.8400,20487730.0000,140.3400,140.3400,139.7000
            2017/03/16,139.9900,13604260.0000,140.2000,140.2500,139.7300
            2017/03/15,139.7200,19315340.0000,139.2900,140.1000,138.4900
            2017/03/14,139.3200,12935370.0000,139.4600,139.4600,138.5200
            2017/03/13,139.6000,10949000.0000,138.7100,139.6800,138.6729
            2017/03/10,138.7900,16299220.0000,138.9100,139.4900,138.2200
            2017/03/09,138.2400,15523900.0000,137.7200,138.5700,137.4000
            2017/03/08,137.7200,10174840.0000,137.1500,137.9900,137.0516
            2017/03/07,137.3000,13517880.0000,137.0300,138.3700,136.9900
            2017/03/06,137.4200,12562040.0000,136.8800,137.8300,136.5100
            2017/03/03,137.1700,11154380.0000,136.6300,137.3300,136.0800
            2017/03/02,136.7600,12278560.0000,137.0900,137.8200,136.3100
            2017/03/01,137.4200,16241330.0000,136.4700,137.4800,136.3000
            2017/02/28,135.5400,16096310.0000,136.7900,136.8050,134.7500
            2017/02/27,136.4100,14300970.0000,135.2600,137.1846,135.0200
            2017/02/24,135.4400,12617180.0000,134.1600,135.6200,134.1600
            2017/02/23,135.3600,18408460.0000,135.8900,136.1200,134.3300
            2017/02/22,136.1200,27331930.0000,133.6000,136.7900,133.4600
            2017/02/21,133.7200,14735860.0000,133.5000,133.9100,132.9000
            2017/02/17,133.5300,12265480.0000,133.5000,134.0912,133.1700
            2017/02/16,133.8400,12816260.0000,133.0700,133.8700,133.0220
            2017/02/15,133.4400,13215470.0000,133.4500,133.7000,132.6600
            2017/02/14,133.8500,14346090.0000,134.1000,134.2300,132.5500
            2017/02/13,134.0500,13511640.0000,134.6975,134.6975,133.7000
            2017/02/10,134.1900,15050270.0000,134.1000,134.9400,133.6800
            2017/02/09,134.1400,16454060.0000,134.4900,134.5000,133.3100
            2017/02/08,134.2000,22370760.0000,132.6000,134.4400,132.4400
            2017/02/07,131.8400,14585030.0000,132.2400,133.0000,131.6610
            2017/02/06,132.0600,17015610.0000,130.9800,132.0600,130.3000
            2017/02/03,130.9800,24776070.0000,131.2400,132.8500,130.7600
            2017/02/02,130.8400,54281800.0000,133.2200,135.4900,130.4000
            2017/02/01,133.2300,47329480.0000,132.2500,133.4900,130.6800
            2017/01/31,130.3200,19642550.0000,130.1700,130.6600,129.5157
            2017/01/30,130.9800,18919750.0000,131.5800,131.5800,129.6000
            2017/01/27,132.1800,19505970.0000,132.6800,132.9500,131.0800
            2017/01/26,132.7800,19964800.0000,131.6300,133.1400,131.4401
            2017/01/25,131.4800,18745640.0000,130.0000,131.7400,129.7700
            2017/01/24,129.3700,15140910.0000,129.3800,129.9000,128.3800
            2017/01/23,128.9300,16566300.0000,127.3100,129.2500,126.9500
            2017/01/20,127.0400,19068380.0000,128.1000,128.4800,126.7800
            2017/01/19,127.5500,12171270.0000,128.2300,128.3500,127.4500
            2017/01/18,127.9200,13107440.0000,128.4100,128.4300,126.8400
            2017/01/17,127.8700,15279990.0000,128.0400,128.3400,127.4000
            2017/01/13,128.3400,24857410.0000,127.4900,129.2700,127.3700
            2017/01/12,126.6200,18596580.0000,125.6100,126.7300,124.8000
            2017/01/11,126.0900,18334560.0000,124.3500,126.1200,124.0600
            2017/01/10,124.3500,17299590.0000,124.8200,125.5000,124.2800
            2017/01/09,124.9000,22874170.0000,123.5500,125.4300,123.0400
            2017/01/06,123.4100,28525570.0000,120.9800,123.8800,120.0300
            2017/01/05,120.6700,19459380.0000,118.8600,120.9500,118.3209
            2017/01/04,118.6900,19594560.0000,117.5500,119.6600,117.2900
            2017/01/03,116.8600,20635600.0000,116.0300,117.8400,115.5100
            
            """.trimIndent()
        val dataLine = rawData.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        data = BaseBarSeries("FB_daily")
        val dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.getDefault())
        for (i in dataLine.indices.reversed()) {
            val tickData = dataLine[i].split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val date = LocalDate.parse(tickData[0], dtf).atStartOfDay(ZoneId.systemDefault())
            data!!.addBar(date, tickData[3], tickData[4], tickData[5], tickData[1], tickData[2])
        }
    }

    @Test
    fun test() {
        val aroonOscillator = AroonOscillatorIndicator(data, 25)
        Assert.assertNotNull(aroonOscillator.aroonUpIndicator)
        Assert.assertNotNull(aroonOscillator.aroonDownIndicator)
        TestUtils.assertNumEquals(0, aroonOscillator.getValue(data!!.beginIndex))
        TestUtils.assertNumEquals(84, aroonOscillator.getValue(data!!.beginIndex + 25))
        TestUtils.assertNumEquals(80, aroonOscillator.getValue(data!!.beginIndex + 26))
        TestUtils.assertNumEquals(76, aroonOscillator.getValue(data!!.beginIndex + 27))
        TestUtils.assertNumEquals(56.0, aroonOscillator.getValue(data!!.endIndex - 5))
        TestUtils.assertNumEquals(52.0, aroonOscillator.getValue(data!!.endIndex - 4))
        TestUtils.assertNumEquals(48.0, aroonOscillator.getValue(data!!.endIndex - 3))
        TestUtils.assertNumEquals(44.0, aroonOscillator.getValue(data!!.endIndex - 2))
        TestUtils.assertNumEquals(40.0, aroonOscillator.getValue(data!!.endIndex - 1))
        TestUtils.assertNumEquals(32.0, aroonOscillator.getValue(data!!.endIndex))
    }
}