/*
 * Decompiled with CFR 0_132.
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractReferenceList;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterators;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import it.unimi.dsi.fastutil.objects.ReferenceCollections;
import it.unimi.dsi.fastutil.objects.ReferenceList;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.RandomAccess;

public final class ReferenceLists {
    public static final EmptyList EMPTY_LIST = new EmptyList();

    private ReferenceLists() {
    }

    public static <K> ReferenceList<K> shuffle(ReferenceList<K> l, Random random) {
        int i = l.size();
        while (i-- != 0) {
            int p = random.nextInt(i + 1);
            K t = l.get(i);
            l.set(i, l.get(p));
            l.set(p, t);
        }
        return l;
    }

    public static <K> ReferenceList<K> emptyList() {
        return EMPTY_LIST;
    }

    public static <K> ReferenceList<K> singleton(K element) {
        return new Singleton<K>(element);
    }

    public static <K> ReferenceList<K> synchronize(ReferenceList<K> l) {
        return l instanceof RandomAccess ? new SynchronizedRandomAccessList<K>(l) : new SynchronizedList<K>(l);
    }

    public static <K> ReferenceList<K> synchronize(ReferenceList<K> l, Object sync) {
        return l instanceof RandomAccess ? new SynchronizedRandomAccessList<K>(l, sync) : new SynchronizedList<K>(l, sync);
    }

    public static <K> ReferenceList<K> unmodifiable(ReferenceList<K> l) {
        return l instanceof RandomAccess ? new UnmodifiableRandomAccessList<K>(l) : new UnmodifiableList<K>(l);
    }

    public static class UnmodifiableRandomAccessList<K>
    extends UnmodifiableList<K>
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 0L;

        protected UnmodifiableRandomAccessList(ReferenceList<K> l) {
            super(l);
        }

        @Override
        public ReferenceList<K> subList(int from, int to) {
            return new UnmodifiableRandomAccessList<K>((ReferenceList<K>)this.list.subList(from, to));
        }
    }

    public static class UnmodifiableList<K>
    extends ReferenceCollections.UnmodifiableCollection<K>
    implements ReferenceList<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ReferenceList<K> list;

        protected UnmodifiableList(ReferenceList<K> l) {
            super(l);
            this.list = l;
        }

        @Override
        public K get(int i) {
            return this.list.get(i);
        }

        @Override
        public K set(int i, K k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int i, K k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public K remove(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(Object k) {
            return this.list.indexOf(k);
        }

        @Override
        public int lastIndexOf(Object k) {
            return this.list.lastIndexOf(k);
        }

        @Override
        public boolean addAll(int index, Collection<? extends K> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getElements(int from, Object[] a, int offset, int length) {
            this.list.getElements(from, a, offset, length);
        }

        @Override
        public void removeElements(int from, int to) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int index, K[] a, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int index, K[] a) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int size) {
            this.list.size(size);
        }

        @Override
        public ObjectListIterator<K> listIterator() {
            return ObjectIterators.unmodifiable(this.list.listIterator());
        }

        @Override
        public ObjectListIterator<K> iterator() {
            return this.listIterator();
        }

        @Override
        public ObjectListIterator<K> listIterator(int i) {
            return ObjectIterators.unmodifiable(this.list.listIterator(i));
        }

        @Override
        public ReferenceList<K> subList(int from, int to) {
            return new UnmodifiableList<K>((ReferenceList<K>)this.list.subList(from, to));
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            return this.collection.equals(o);
        }

        @Override
        public int hashCode() {
            return this.collection.hashCode();
        }
    }

    public static class SynchronizedRandomAccessList<K>
    extends SynchronizedList<K>
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 0L;

        protected SynchronizedRandomAccessList(ReferenceList<K> l, Object sync) {
            super(l, sync);
        }

        protected SynchronizedRandomAccessList(ReferenceList<K> l) {
            super(l);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ReferenceList<K> subList(int from, int to) {
            Object object = this.sync;
            synchronized (object) {
                return new SynchronizedRandomAccessList<K>((ReferenceList<K>)this.list.subList(from, to), this.sync);
            }
        }
    }

    public static class SynchronizedList<K>
    extends ReferenceCollections.SynchronizedCollection<K>
    implements ReferenceList<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ReferenceList<K> list;

        protected SynchronizedList(ReferenceList<K> l, Object sync) {
            super(l, sync);
            this.list = l;
        }

        protected SynchronizedList(ReferenceList<K> l) {
            super(l);
            this.list = l;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public K get(int i) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.get(i);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public K set(int i, K k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(i, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(int i, K k) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(i, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public K remove(int i) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.remove(i);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int indexOf(Object k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.indexOf(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int lastIndexOf(Object k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.lastIndexOf(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int index, Collection<? extends K> c) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(index, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void getElements(int from, Object[] a, int offset, int length) {
            Object object = this.sync;
            synchronized (object) {
                this.list.getElements(from, a, offset, length);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void removeElements(int from, int to) {
            Object object = this.sync;
            synchronized (object) {
                this.list.removeElements(from, to);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(int index, K[] a, int offset, int length) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(index, a, offset, length);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(int index, K[] a) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(index, a);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void size(int size) {
            Object object = this.sync;
            synchronized (object) {
                this.list.size(size);
            }
        }

        @Override
        public ObjectListIterator<K> listIterator() {
            return this.list.listIterator();
        }

        @Override
        public ObjectListIterator<K> iterator() {
            return this.listIterator();
        }

        @Override
        public ObjectListIterator<K> listIterator(int i) {
            return this.list.listIterator(i);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ReferenceList<K> subList(int from, int to) {
            Object object = this.sync;
            synchronized (object) {
                return new SynchronizedList<K>((ReferenceList<K>)this.list.subList(from, to), this.sync);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            Object object = this.sync;
            synchronized (object) {
                return this.collection.equals(o);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int hashCode() {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.hashCode();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void writeObject(ObjectOutputStream s) throws IOException {
            Object object = this.sync;
            synchronized (object) {
                s.defaultWriteObject();
            }
        }
    }

    public static class Singleton<K>
    extends AbstractReferenceList<K>
    implements RandomAccess,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final K element;

        protected Singleton(K element) {
            this.element = element;
        }

        @Override
        public K get(int i) {
            if (i == 0) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean remove(Object k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public K remove(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(Object k) {
            return k == this.element;
        }

        @Override
        public Object[] toArray() {
            Object[] a = new Object[]{this.element};
            return a;
        }

        @Override
        public ObjectListIterator<K> listIterator() {
            return ObjectIterators.singleton(this.element);
        }

        @Override
        public ObjectListIterator<K> iterator() {
            return this.listIterator();
        }

        @Override
        public ObjectListIterator<K> listIterator(int i) {
            if (i > 1 || i < 0) {
                throw new IndexOutOfBoundsException();
            }
            ListIterator l = this.listIterator();
            if (i == 1) {
                l.next();
            }
            return l;
        }

        @Override
        public ReferenceList<K> subList(int from, int to) {
            this.ensureIndex(from);
            this.ensureIndex(to);
            if (from > to) {
                throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
            }
            if (from != 0 || to != 1) {
                return ReferenceLists.EMPTY_LIST;
            }
            return this;
        }

        @Override
        public boolean addAll(int i, Collection<? extends K> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends K> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public void size(int size) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return this;
        }
    }

    public static class EmptyList<K>
    extends ReferenceCollections.EmptyCollection<K>
    implements ReferenceList<K>,
    RandomAccess,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyList() {
        }

        @Override
        public K get(int i) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean remove(Object k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public K remove(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int index, K k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public K set(int index, K k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(Object k) {
            return -1;
        }

        @Override
        public int lastIndexOf(Object k) {
            return -1;
        }

        @Override
        public boolean addAll(int i, Collection<? extends K> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectListIterator<K> listIterator() {
            return ObjectIterators.EMPTY_ITERATOR;
        }

        @Override
        public ObjectListIterator<K> iterator() {
            return ObjectIterators.EMPTY_ITERATOR;
        }

        @Override
        public ObjectListIterator<K> listIterator(int i) {
            if (i == 0) {
                return ObjectIterators.EMPTY_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(i));
        }

        @Override
        public ReferenceList<K> subList(int from, int to) {
            if (from == 0 && to == 0) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void getElements(int from, Object[] a, int offset, int length) {
            if (from == 0 && length == 0 && offset >= 0 && offset <= a.length) {
                return;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void removeElements(int from, int to) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int index, K[] a, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int index, K[] a) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int s) {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return ReferenceLists.EMPTY_LIST;
        }

        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof List && ((List)o).isEmpty();
        }

        @Override
        public String toString() {
            return "[]";
        }

        private Object readResolve() {
            return ReferenceLists.EMPTY_LIST;
        }
    }

}
