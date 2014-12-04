package org.sample;

import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.util.Pair;
import org.openjdk.jmh.annotations.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Benchmark                                                                      (sampleSize)   Mode  Samples       Score      Error  Units
 * o.s.Math3Benchmark.measureBetaDistributionSampleBulkWithCache                             1  thrpt        5  204473.292 ± 5970.609  ops/s
 * o.s.Math3Benchmark.measureBetaDistributionSampleBulkWithCache                            10  thrpt        5   21276.800 ± 1310.017  ops/s
 * o.s.Math3Benchmark.measureBetaDistributionSampleBulkWithCache                           100  thrpt        5    2070.927 ±  109.567  ops/s
 * o.s.Math3Benchmark.measureBetaDistributionSampleBulkWithCache                          1000  thrpt        5     210.469 ±   14.800  ops/s
 * o.s.Math3Benchmark.measureBetaDistributionSampleBulkWithCache                         10000  thrpt        5      21.310 ±    1.861  ops/s
 * o.s.Math3Benchmark.measureBetaDistributionSampleBulkWithoutCache                          1  thrpt        5   42483.183 ± 2076.843  ops/s
 * o.s.Math3Benchmark.measureBetaDistributionSampleBulkWithoutCache                         10  thrpt        5   15288.983 ±  578.173  ops/s
 * o.s.Math3Benchmark.measureBetaDistributionSampleBulkWithoutCache                        100  thrpt        5    2022.336 ±  158.143  ops/s
 * o.s.Math3Benchmark.measureBetaDistributionSampleBulkWithoutCache                       1000  thrpt        5     212.166 ±   15.950  ops/s
 * o.s.Math3Benchmark.measureBetaDistributionSampleBulkWithoutCache                      10000  thrpt        5      21.401 ±    1.007  ops/s
 * o.s.Math3Benchmark.measureBetaDistributionSampleIndividualOneByOneWithCache               1  thrpt        5  208682.240 ± 5464.149  ops/s
 * o.s.Math3Benchmark.measureBetaDistributionSampleIndividualOneByOneWithCache              10  thrpt        5   21102.130 ± 1033.270  ops/s
 * o.s.Math3Benchmark.measureBetaDistributionSampleIndividualOneByOneWithCache             100  thrpt        5    2135.970 ±  168.070  ops/s
 * o.s.Math3Benchmark.measureBetaDistributionSampleIndividualOneByOneWithCache            1000  thrpt        5     213.229 ±   12.048  ops/s
 * o.s.Math3Benchmark.measureBetaDistributionSampleIndividualOneByOneWithCache           10000  thrpt        5      21.460 ±    0.694  ops/s
 * o.s.Math3Benchmark.measureBetaDistributionSampleIndividualWithoutCache                    1  thrpt        5   42343.853 ± 3339.901  ops/s
 * o.s.Math3Benchmark.measureBetaDistributionSampleIndividualWithoutCache                   10  thrpt        5    4215.789 ±  334.808  ops/s
 * o.s.Math3Benchmark.measureBetaDistributionSampleIndividualWithoutCache                  100  thrpt        5     436.108 ±   35.811  ops/s
 * o.s.Math3Benchmark.measureBetaDistributionSampleIndividualWithoutCache                 1000  thrpt        5      42.100 ±    4.404  ops/s
 * o.s.Math3Benchmark.measureBetaDistributionSampleIndividualWithoutCache                10000  thrpt        5       4.247 ±    0.261  ops/s
 *
 * @author skong
 */
@State(Scope.Thread)
public class Math3Benchmark {

    //a concurrent map acts like a cache
    Map<Pair<Class<? extends RealDistribution>, Pair<Double, Double>>, RealDistribution> cache = new ConcurrentHashMap<Pair<Class<? extends RealDistribution>, Pair<Double, Double>>, RealDistribution>();

    @Param({"1", "10", "100", "1000", "10000"})
    public int sampleSize;

    @Setup
    public void prepare() {
        cache.put(new Pair<Class<? extends RealDistribution>, Pair<Double, Double>>(BetaDistribution.class, new Pair<Double, Double>(1.0, 2.0)), new BetaDistribution(1.0, 2.0));
    }

    @Benchmark
    public void measureBetaDistributionSampleBulkWithoutCache() {
        double[] sample = new BetaDistribution(1.0, 2.0).sample(sampleSize);
    }

    @Benchmark
    public void measureBetaDistributionSampleBulkWithCache() {
        RealDistribution realDistribution = cache.get(new Pair<Class<? extends RealDistribution>, Pair<Double, Double>>(BetaDistribution.class, new Pair<Double, Double>(1.0, 2.0)));
        assert realDistribution != null;
        double[] sample = realDistribution.sample(sampleSize);
    }

    @Benchmark
    public void measureBetaDistributionSampleIndividualWithoutCache() {
        double[] sample = new double[sampleSize];
        for (int i = 0; i < sampleSize; i++) {
            sample[i] = new BetaDistribution(1.0, 2.0).sample();
        }
    }

    @Benchmark
    public void measureBetaDistributionSampleIndividualOneByOneWithCache() {
        RealDistribution realDistribution = cache.get(new Pair<Class<? extends RealDistribution>, Pair<Double, Double>>(BetaDistribution.class, new Pair<Double, Double>(1.0, 2.0)));
        assert realDistribution != null;
        double[] sample = new double[sampleSize];
        for (int i = 0; i < sampleSize; i++) {
            sample[i] = realDistribution.sample();
        }
    }
}
