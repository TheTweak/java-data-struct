import java.util.ArrayList;
import java.util.List;

public class MinHeapTest {
    static class MinHeap<T extends Comparable<T>> {
        private List<T> heap = new ArrayList<>();

        private int getParent(int i) {
            int parent = i % 2 == 0 ? i / 2 - 1 : i / 2;
            return Math.max(parent, 0);
        }

        private int getLeft(int i) {
            return Math.min(heap.size() - 1, 2 * i + 1);
        }

        private int getRight(int i) {
            return Math.min(heap.size() - 1, 2 * i + 2);
        }

        private void swap(int a, int b) {
            assert a >= 0 && a < heap.size()
                    && b >= 0 && b < heap.size();
            T tmp = heap.get(a);
            heap.set(a, heap.get(b));
            heap.set(b, tmp);
        }

        public void add(T t) {
            heap.add(t);
            int i = Math.max(heap.size() - 1, 0);
            int p = getParent(i);
            while (t.compareTo(heap.get(p)) < 0) {
                swap(i, p);
                i = p;
                p = getParent(p);
            }
        }

        public T min() {
            return heap.get(0);
        }

        public T pop() {
            T min = min();
            swap(0, Math.max(heap.size() - 1, 0));
            heap.remove(Math.max(heap.size() - 1, 0));
            if (heap.isEmpty()) {
                return min;
            }
            int i = 0;
            T root = heap.get(0);
            while (true) {
                int left = getLeft(i);
                int right = getRight(i);
                if (heap.get(left).compareTo(heap.get(right)) < 0) {
                    if (root.compareTo(heap.get(left)) > 0) {
                        swap(left, i);
                        i = left;
                    }
                } else if (root.compareTo(heap.get(right)) > 0) {
                    swap(right, i);
                    i = right;
                } else {
                    break;
                }
            }
            return min;
        }
    }

    public static void main(String[] args) {
        MinHeap<Integer> mh = new MinHeap<>();
        mh.add(1);
        assert mh.min() == 1;
        mh.add(0);
        assert mh.min() == 0;
        mh.add(2);
        assert mh.min() == 0;
        assert mh.pop() == 0;
        assert mh.min() == 1;

        mh = new MinHeap<>();
        for (int i = 0; i < 10; i++) {
            mh.add(i);
        }
        for (int i = 0; i < 10; i++) {
            assert mh.pop() == i;
        }
    }
}
