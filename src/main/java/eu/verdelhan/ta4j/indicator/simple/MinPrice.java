package eu.verdelhan.ta4j.indicator.simple;

import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.TimeSeries;
import java.math.BigDecimal;

public class MinPrice implements Indicator<BigDecimal> {

	private TimeSeries data;

	public MinPrice(TimeSeries data) {
		this.data = data;
	}

	@Override
	public BigDecimal getValue(int index) {
		return data.getTick(index).getMinPrice();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}