/*
 * Copyright (c) 2005, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package org.sample;

import com.google.common.collect.ImmutableList;
import com.gs.collections.impl.factory.Lists;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@State(Scope.Thread)
public class MyBenchmark {


    private ArrayList<Double> arrayList;

    private List<Double> unmodifiableList;

    private ImmutableList<Double> guavaImmutableList;
    private com.gs.collections.api.list.ImmutableList<Double> gsImmutableList;


    @Setup
    public void prepare() {
        arrayList = new ArrayList<Double>();
        arrayList.add(1.0);
        arrayList.add(2.0);
        arrayList.add(3.0);

        unmodifiableList = Collections.unmodifiableList(Arrays.asList(1.0, 2.0, 3.0));

        guavaImmutableList = ImmutableList.of(1.0, 2.0, 3.0);

        gsImmutableList = Lists.immutable.of(1.0, 2.0, 3.0);
    }

    @Benchmark
    public void testArrayListIterator() {
        arrayList.iterator();
    }


    @Benchmark
    public void testUnmodifiableList() {
        unmodifiableList.iterator();
    }

    @Benchmark
    public void testGuavaImmutableList() {
        guavaImmutableList.iterator();
    }

    @Benchmark
    public void testGsImmutableList() {
        gsImmutableList.iterator();
    }

}
