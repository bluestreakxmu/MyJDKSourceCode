package org.nullhint.java.util;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Reimplement <code>HashMap</code>.
 *
 * @author lixibo
 * @date 2017/08/17
 */
public class MyHashMap<K, V> implements Map<K, V> {

    /**
     * The default initial capacity - MUST be a power of two.
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * The load factor used when none specified in constructor.
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * An empty table instance to share when the table is not inflated.
     */
    static final Entry<?, ?>[] EMPTY_TABLE = {};


    /**
     * The next size value at which to resize (capacity * load factor).
     */
    // If table == EMPTY_TABLE then this is the initial capacity at which the
    // table will be created when inflated.
    int threshold;

    /**
     * The load factor for the hash table.
     */
    final float loadFactor;

    /**
     * use this entry array to store data.
     */
    Entry<K, V>[] table = (Entry<K, V>[]) EMPTY_TABLE;

    /**
     * A randomizing value associated with this instance that is applied to
     * hash code of keys to make hash collisions harder to find. If 0 then
     * alternative hashing is disabled.
     */
    transient int hashSeed = 0;

    /**
     * The number of times this HashMap has been structurally modified
     * Structural modifications are those that change the number of mappings in
     * the HashMap or otherwise modify its internal structure (e.g.,
     * rehash).  This field is used to make iterators on Collection-views of
     * the HashMap fail-fast.  (See ConcurrentModificationException).
     */
    transient int modCount;

    /**
     * The number of key-value mappings contained in this map.
     */
    transient int size;

    //****construct methods start**************************************
    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity, float loadFactor) {
        // check arguments legality ...

        // NOTE: not init entry array here, we defer initialization until we really neet it.

        this.loadFactor = loadFactor;
        threshold = initialCapacity;

        // hook method ...
    }

    //****construct methods end****************************************


    //****implement methods from Map<K, V> start***********************
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(Object key) {
        return false;
    }

    /**
     * Returns the entry associated with the specified key in the
     * HashMap.  Returns null if the HashMap contains no mapping
     * for the key.
     */
    final Entry<K, V> getEntry(Object key) {
        if (0 == size) {
            return null;
        }

        // TODO

        return null;
    }

    public boolean containsValue(Object value) {
        return false;
    }

    public V get(Object key) {
        // 对key=null的键值对做处理
        if (null == key) {
            return getForNullKey();
        }
        // 获取相应的Entry<K, V>
        Entry<K, V> entry = getEntry(key);

        return null == entry ? null : entry.value;
    }

    private V getForNullKey() {
        if (0 == size) {
            return null;
        }

        for (Entry<K, V> e = table[0]; e != null; e = e.next) {
            if (e.key == null) {
                return e.value;
            }
        }

        return null;
    }

    public V put(K key, V value) {
        if (table == EMPTY_TABLE) {
            inflateTable(threshold);
        }

        // 对于key=null，放在数组index=0的Entry<K,V>链表上
        if (key == null) {
            return putForNullKey(value);
        }

        // 对key重新hash，防止某些hash算法不完善造成更多的冲突
        int hash = hash(key);
        // 根据hash值获取该键值对在数组中的索引位置
        int index = indexFor(hash, table.length);

        // find the right position in the entry table,
        // and if the map previously contained a mapping for the key, the old value is replaced.
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && (e.key == key || e.key.equals(key))) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }

        modCount++;
        addEntry(hash, key, value, index);
        return null;
    }

    /**
     * 把key=null的键值对存入内部结构。<br>
     * Note: key=null放在数组index=0的Entry<K,V>链表上。
     */
    private V putForNullKey(V value) {
        // 已存在key=null的，替换为新值
        for (Entry<K, V> e = table[0]; e != null; e = e.next) {
            if (e.key == null) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }
        modCount++;
        // 不存在key=null的，插入新值
        addEntry(0, null, value, 0);
        return null;
    }

    public V remove(Object key) {
        return null;
    }

    public void putAll(Map<? extends K, ? extends V> m) {

    }

    public void clear() {

    }

    public Set<K> keySet() {
        return null;
    }

    public Collection<V> values() {
        return null;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return null;
    }

    //****implement methods from Map<K, V> end***************************

    /**
     * Inflates the table.
     */
    private void inflateTable(int toSize) {
        // Find a power of 2 >= toSize, why?
        int capacity = roundUpToPowerOf2(toSize);

        threshold = (int) Math.min(capacity * loadFactor, MAXIMUM_CAPACITY + 1);
        table = new Entry[capacity];
        initHashSeedAsNeeded(capacity);
    }

    private static int roundUpToPowerOf2(int number) {
        int rounded = number >= MAXIMUM_CAPACITY
                ? MAXIMUM_CAPACITY
                : (rounded = Integer.highestOneBit(number)) != 0
                ? (Integer.bitCount(number) > 1) ? rounded << 1 : rounded
                : 1;

        return rounded;
    }

    /**
     * Initialize the hashing mask value. We defer initialization until we
     * really need it.
     */
    final boolean initHashSeedAsNeeded(int capacity) {
        boolean currentAltHashing = hashSeed != 0;
        boolean useAltHashing = sun.misc.VM.isBooted() &&
                (capacity >= MyHashMap.Holder.ALTERNATIVE_HASHING_THRESHOLD);
        boolean switching = currentAltHashing ^ useAltHashing;
        if (switching) {
            hashSeed = useAltHashing
                    ? sun.misc.Hashing.randomHashSeed(this)
                    : 0;
        }
        return switching;
    }

    void addEntry(int hash, K key, V value, int bucketIndex) {

    }

    void createEntry(int hash, K key, V value, int bucketIndex) {
        Entry<K, V> e = table[bucketIndex];
        // My note: put into the first place
        table[bucketIndex] = new Entry<K, V>(hash, key, value, e);
        size++;
    }

    /**
     * Retrieve object hash code and applies a supplemental hash function to the
     * result hash, which defends against poor quality hash functions.  This is
     * critical because HashMap uses power-of-two length hash tables, that
     * otherwise encounter collisions for hashCodes that do not differ
     * in lower bits. Note: Null keys always map to hash 0, thus index 0.
     */
    final int hash(Object key) {
        int h = this.hashSeed;
        if (h != 0 && key instanceof String) {
            return sun.misc.Hashing.stringHash32((String) key);
        }

        h ^= key.hashCode();

        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
     * Returns index for hash code h.
     */
    static int indexFor(int h, int length) {
        // Note: 当length总是2的n次方时，h&(length-1)等价于h%length，但是&比%具有更高的效率
        // FIXME 分析原理
        return h & (length - 1);
    }

    //*******Entry start**************************************************

    /**
     * HashMap inner data structure.
     *
     * @param <K> Key Type
     * @param <V> Value Type
     */
    // NOTE: the Entry is static.
    static class Entry<K, V> implements Map.Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next; // linked list structure
        int hash;

        /**
         * Creates new entry.
         */
        Entry(int h, K k, V v, Entry<K, V> n) {
            hash = h;
            key = k;
            value = v;
            next = n;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        /**
         * Set value into Entry.
         *
         * @param newValue new Value
         * @return oldValue
         */
        public V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }
    }

    //******Entry end******************************************************

    static final int ALTERNATIVE_HASHING_THRESHOLD_DEFAULT = Integer.MAX_VALUE;

    /**
     * holds values which can't be initialized until after VM is booted.
     * My note: ignore this part.
     */
    private static class Holder {

        /**
         * Table capacity above which to switch to use alternative hashing.
         */
        static final int ALTERNATIVE_HASHING_THRESHOLD;

        static {
            String altThreshold = java.security.AccessController.doPrivileged(
                    new sun.security.action.GetPropertyAction(
                            "jdk.map.althashing.threshold"));

            int threshold;
            try {
                threshold = (null != altThreshold)
                        ? Integer.parseInt(altThreshold)
                        : ALTERNATIVE_HASHING_THRESHOLD_DEFAULT;

                // disable alternative hashing if -1
                if (threshold == -1) {
                    threshold = Integer.MAX_VALUE;
                }

                if (threshold < 0) {
                    throw new IllegalArgumentException("value must be positive integer.");
                }
            } catch (IllegalArgumentException failed) {
                throw new Error("Illegal value for 'jdk.map.althashing.threshold'", failed);
            }

            ALTERNATIVE_HASHING_THRESHOLD = threshold;
        }
    }
}
