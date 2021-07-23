import java.util.ArrayList;
import java.util.List;

public class HashTable<K, V> {
    private class Item {
        K key;
        V value;
        Item next;
    }

    static final int INIT_SIZE = 32;
    private final List<Item> buckets = new ArrayList<>(INIT_SIZE);

    public HashTable() {
        for (int i = 0; i < INIT_SIZE; i++) {
            buckets.add(null);
        }
    }

    public void put(K key, V value) {
        int idx = key.hashCode() % buckets.size();
        Item bucket = buckets.get(idx);
        if (bucket == null) {
            Item n = new Item();
            n.key = key;
            n.value = value;
            buckets.set(idx, n);
        } else {
            while (true) {
                if (bucket.key.equals(key)) {
                    bucket.value = value;
                    break;
                }
                if (bucket.next == null) {
                    bucket.next = new Item();
                    bucket.next.key = key;
                    bucket.next.value = value;
                    break;
                }
                bucket = bucket.next;
            }
        }
    }

    public V get(K key) {
        int idx = key.hashCode() % buckets.size();
        Item bucket = buckets.get(idx);
        while (bucket != null) {
            if (bucket.key.equals(key)) {
                return bucket.value;
            }
            bucket = bucket.next;
        }
        return null;
    }

    public boolean contains(K key) {
        return get(key) != null;
    }

    public void remove(K key) {
        int idx = key.hashCode() % buckets.size();
        Item bucket = buckets.get(idx);
        Item prev = null;
        while (bucket != null) {
            if (bucket.key.equals(key)) {
                if (prev == null) {
                    buckets.set(idx, bucket.next);
                } else {
                    prev.next = bucket.next;
                }
            }
            prev = bucket;
            bucket = bucket.next;
        }
    }
}
