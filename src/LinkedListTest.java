public class LinkedListTest {
    public static class LinkedList<E> {
        private class Node {
            E elem;
            Node next;
        }

        private Node root;
        private int size;

        public void add(E elem) {
            size += 1;
            if (root == null) {
                root = new Node();
                root.elem = elem;
            } else {
                Node next = root;
                while (next.next != null) {
                    next = next.next;
                }
                next.next = new Node();
                next.next.elem = elem;
            }
        }

        private Node getNode(int i) {
            assert i < size;
            int j = 0;
            Node node = root;
            while (j != i) {
                node = node.next;
                j++;
            }
            return node;
        }

        public E get(int i) {
            Node node = getNode(i);
            assert node != null;
            return node.elem;
        }

        public int size() {
            return size;
        }

        public void remove(int i) {
            size = Math.max(0, size - 1);
            if (i == 0) {
                root = root.next;
            } else {
                Node prevNode = getNode(i - 1);
                prevNode.next = prevNode.next.next;
            }
        }
    }

    public static void main(String[] args) {
        LinkedList<String> ll = new LinkedList<>();
        assert ll.size() == 0;
        ll.add("test");
        assert ll.size() == 1;
        ll.remove(0);
        assert ll.size() == 0;
        ll.add("test");
        ll.add("abc");
        ll.add("test");
        ll.add("test");
        assert ll.size() == 4;
        assert ll.get(3).equals("test");
        assert ll.get(1).equals("abc");
    }
}
