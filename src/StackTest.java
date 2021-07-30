import java.util.Arrays;

public class StackTest {
    static class IntStack {
        static final int INIT_SIZE = 32;
        private int[] data = new int[INIT_SIZE];
        private int size = 0;

        public void push(int i) {
            if (data.length == size) {
                data = Arrays.copyOf(data, size*2);
            }
            data[size++] = i;
        }

        public int pop() {
            assert size > 0;
            return data[--size];
        }

        public int size() {
            return size;
        }
    }

    public static void main(String[] args) {
        IntStack is = new IntStack();
        for (int i = 0; i < 128; i++) {
            is.push(i);
        }
        assert is.size() == 128;
        for (int i = 127; i >= 0; i--) {
            assert is.pop() == i;
        }
        assert is.size() == 0;
    }
}
