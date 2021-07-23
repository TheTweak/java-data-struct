import java.util.ArrayList;
import java.util.List;

public class HashSetTest {
    public static class HashSet<V> {
        private class Item {
            V value;
            Item next;
        }

        static final int INIT_SIZE = 32;
        private final List<Item> buckets = new ArrayList<>(INIT_SIZE);

        public HashSet() {
            for (int i = 0; i < INIT_SIZE; i++) {
                buckets.add(null);
            }
        }

        public void add(V value) {
            int idx = value.hashCode() % buckets.size();
            Item bucket = buckets.get(idx);
            if (bucket == null) {
                Item n = new Item();
                n.value = value;
                buckets.set(idx, n);
            } else {
                while (true) {
                    if (bucket.value.equals(value)) {
                        break;
                    }
                    if (bucket.next == null) {
                        bucket.next = new Item();
                        bucket.next.value = value;
                        break;
                    }
                    bucket = bucket.next;
                }
            }
        }

        public boolean contains(V value) {
            int idx = value.hashCode() % buckets.size();
            Item bucket = buckets.get(idx);
            while (bucket != null) {
                if (bucket.value.equals(value)) {
                    return true;
                }
                bucket = bucket.next;
            }
            return false;
        }

        public void remove(V value) {
            int idx = value.hashCode() % buckets.size();
            Item bucket = buckets.get(idx);
            Item prev = null;
            while (bucket != null) {
                if (bucket.value.equals(value)) {
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

    public static void main(String[] args) {
        HashSet<String> hashSet = new HashSet<>();
        assert !hashSet.contains("abc");
        hashSet.add("abc");
        assert hashSet.contains("abc");
        hashSet.remove("abc");
        assert !hashSet.contains("abc");
        assert !hashSet.contains("cw");
    }
}

