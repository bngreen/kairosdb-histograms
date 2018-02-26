/**
 * Copyright 2018 Bruno Green.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arpnetworking.kairosdb.aggregators;

import com.google.inject.Inject;
import org.kairosdb.core.aggregator.annotation.AggregatorName;
import org.kairosdb.core.aggregator.annotation.AggregatorProperty;
import org.kairosdb.core.datapoints.DoubleDataPointFactory;
import org.kairosdb.core.exception.KairosDBException;

/**
 * Aggregator that computes the standard deviation value of histograms.
 *
 * @author Bruno Green (bruno dot green at gmail dot com)
 */
@AggregatorName(
        name = "hdev",
        description = "Computes the standard deviation value of the histograms.",
        properties = {
                @AggregatorProperty(name = "sampling", type = "duration"),
                @AggregatorProperty(name = "align_start_time", type = "boolean")
        }
)
public class HistogramStdDevAggregator extends HistogramVarianceAggregator {
    /**
     * Public constructor.
     *
     * @param dataPointFactory A factory for creating DoubleDataPoints
     * @throws KairosDBException on error
     */
    @Inject
    public HistogramStdDevAggregator(final DoubleDataPointFactory dataPointFactory) throws KairosDBException {
        super(dataPointFactory);
    }

    @Override
    protected RangeSubAggregator getSubAggregator() {
        return new HistogramStdDevDataPointAggregator();
    }

    private final class HistogramStdDevDataPointAggregator extends HistogramVarianceDataPointAggregator {

        @Override
        protected double computeFinalValue(final long count, final double mean, final double m2) {
            return Math.sqrt(super.computeFinalValue(count, mean, m2));
        }
    }
}

