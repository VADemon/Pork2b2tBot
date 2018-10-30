/*
 * Decompiled with CFR 0_132.
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2ShortMap;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public interface Float2ShortSortedMap
extends Float2ShortMap,
SortedMap<Float, Short> {
    public Float2ShortSortedMap subMap(float var1, float var2);

    public Float2ShortSortedMap headMap(float var1);

    public Float2ShortSortedMap tailMap(float var1);

    public float firstFloatKey();

    public float lastFloatKey();

    @Deprecated
    default public Float2ShortSortedMap subMap(Float from, Float to) {
        return this.subMap(from.floatValue(), to.floatValue());
    }

    @Deprecated
    default public Float2ShortSortedMap headMap(Float to) {
        return this.headMap(to.floatValue());
    }

    @Deprecated
    default public Float2ShortSortedMap tailMap(Float from) {
        return this.tailMap(from.floatValue());
    }

    @Deprecated
    @Override
    default public Float firstKey() {
        return Float.valueOf(this.firstFloatKey());
    }

    @Deprecated
    @Override
    default public Float lastKey() {
        return Float.valueOf(this.lastFloatKey());
    }

    @Deprecated
    @Override
    default public ObjectSortedSet<Map.Entry<Float, Short>> entrySet() {
        return this.float2ShortEntrySet();
    }

    public ObjectSortedSet<Float2ShortMap.Entry> float2ShortEntrySet();

    @Override
    public FloatSortedSet keySet();

    @Override
    public ShortCollection values();

    public FloatComparator comparator();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Float2ShortMap.Entry>,
    Float2ShortMap.FastEntrySet {
        public ObjectBidirectionalIterator<Float2ShortMap.Entry> fastIterator();

        public ObjectBidirectionalIterator<Float2ShortMap.Entry> fastIterator(Float2ShortMap.Entry var1);
    }

}
