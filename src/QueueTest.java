import java.util.Arrays;

public class QueueTest {
    static class IntQueue {
        static final int INIT_SIZE = 32;
        private int[] data = new int[INIT_SIZE];
        private int size = 0;

        public void add(int i) {
            if (size == data.length) {
                data = Arrays.copyOf(data, data.length*2);
            }
            data[size++] = i;
        }

        public int get() {
            assert size > 0;
            int result = data[0];
            for (int i = 0; i < size; i++) {
                data[i] = data[i+1];
            }
            size--;
            return result;
        }

        public int size() {
            return size;
        }
    }
    public static void main(String[] args) {
        IntQueue iq = new IntQueue();
        for (int i = 0; i < 10; i++) {
            iq.add(i);
        }
        for (int i = 0; i < 10; i++) {
            assert iq.get() == i;
        }
        assert iq.size() == 0;
    }
}
