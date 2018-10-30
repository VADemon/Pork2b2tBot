/*
 * Decompiled with CFR 0_132.
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Object2ObjectFunction;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public interface Object2ObjectMap<K, V>
extends Object2ObjectFunction<K, V>,
Map<K, V> {
    @Override
    public int size();

    @Override
    default public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(V var1);

    @Override
    public V defaultReturnValue();

    public ObjectSet<Entry<K, V>> object2ObjectEntrySet();

    @Override
    default public ObjectSet<Map.Entry<K, V>> entrySet() {
        return this.object2ObjectEntrySet();
    }

    @Override
    default public V put(K key, V value) {
        return Object2ObjectFunction.super.put(key, value);
    }

    @Override
    default public V remove(Object key) {
        return Object2ObjectFunction.super.remove(key);
    }

    @Override
    public ObjectSet<K> keySet();

    @Override
    public ObjectCollection<V> values();

    @Override
    public boolean containsKey(Object var1);

    public static interface Entry<K, V>
    extends Map.Entry<K, V> {
    }

    public static interface FastEntrySet<K, V>
    extends ObjectSet<Entry<K, V>> {
        public ObjectIterator<Entry<K, V>> fastIterator();

        default public void fastForEach(Consumer<? super Entry<K, V>> consumer) {
            this.forEach(consumer);
        }
    }

}
