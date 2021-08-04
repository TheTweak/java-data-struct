import java.util.ArrayDeque;
import java.util.Deque;

public class TreeMapTest {
    public static class IntTreeMap {
        static class Node {
            Integer key;
            Integer value;

            Node parent;
            Node left;
            Node right;

            Node(Integer key, Integer value, Node parent,
                    Node left, Node right) {
                this.key = key;
                this.value = value;
                this.parent = parent;
                this.left = left;
                this.right = right;
            }
        }

        static final Node EMPTY = new Node(0, 0, null, null, null);

        Node root;
        int size;

        int getBalance(Node node) {
        	int hl = 0;
        	if (node.left != null) {
        		hl += 1;
        		hl += getHeight(node.left);
        	}
        	int hr = 0;
        	if (node.right != null) {
        		hr += 1;
        		hr += getHeight(node.right);
        	}
        	return hl - hr;
        }

        int getHeight(Node node) {
        	assert node != null;
        	int hl = 0, hr = 0;
        	if (node.left != null) {
        		hl++;
	        	hl += getHeight(node.left);
        	}
        	if (node.right != null) {
        		hr++;
        		hr += getHeight(node.right);
        	}
        	return Math.max(hl, hr);
        }

        private enum InsertDirection {
        	L, R;
        }

        private void rebalance(Node inserted) {
        	Deque<InsertDirection> dir = new ArrayDeque<>();
        	Node n = inserted;
        	while (n.parent != null) {
        		if (n.parent.left != null && 
        			n.value.compareTo(n.parent.left.value) == 0) {
        			dir.add(InsertDirection.L);
        		} else if (n.parent.right != null &&
        			n.value.compareTo(n.parent.right.value) == 0) {
        			dir.add(InsertDirection.R);
        		}
        		if (dir.size() > 2) {
        			dir.remove();
        		}
        		int balance = getBalance(n.parent);
        		if (balance > 1 || balance < -1) {
        			// found unbalanced node
        			System.out.print("inserted " + inserted.value + ", ");
        			System.out.println("unbalanced node is " + n.parent.value + ", balance = " + balance);
        			System.out.print("direction is ");
        			if (!dir.isEmpty()) {
        				System.out.print(dir.pollLast().name());
        			}
        			if (!dir.isEmpty()) {
        				System.out.println(" " + dir.pollLast().name());
        			}
        			break;
        		} else {
        			n = n.parent;
        		}
        	}
        }

        public void set(Integer key, Integer value) {
            assert key != null && value != null;
            Node inserted = null;
            if (root == null) {
                root = new Node(key, value, null, null, null);
                inserted = root;
            } else {
                Node n = root;
                while (true) {
                    int comp = key.compareTo(n.key);
                    if (comp == 0) {
                        n.value = value;
                        return;
                    } else if (comp > 0) {
                        if (n.right != null) {
                            n = n.right;
                        } else {
                            n.right = new Node(key, value, n, null, null);
                            inserted = n.right;
                            break;
                        }
                    } else {
                        if (n.left != null) {
                            n = n.left;
                        } else {
                            n.left = new Node(key, value, n, null, null);
                            inserted = n.left;
                            break;
                        }
                    }
                }
            }
            size++;
            rebalance(inserted);
        }

        public Integer get(Integer key) {
            assert key != null;
            Node n = root;
            while (n != null) {
                int c = key.compareTo(n.key);
                if (c == 0) {
                    return n.value;
                } else if (c < 0) {
                    n = n.left;
                } else {
                    n = n.right;
                }
            }
            return null;
        }

        public boolean contains(Integer key) {
            return get(key) != null;
        }

        public int size() {
            return size;
        }

        public int height() {
            return (int)(Math.log(size)/Math.log(2));
        }

        static class NodeLevel {
            Node node;
            int level;
            String value;
            int n;

            NodeLevel(Node node, int level, String value, int n) {
                this.node = node;
                this.level = level;
                this.value = value;
                this.n = n;
            }
        }

        public String toString() {
            int height = getHeight(root);
            System.out.println("height=" + height + ", size=" + size());
            Deque<NodeLevel> queue = new ArrayDeque<>();
            int nodeCount = 0;
            queue.add(new NodeLevel(root, 1, root.value + "", nodeCount));
            int prevLevel = 1;
            StringBuilder sb = new StringBuilder();
            int i = 0;
            int maxWidth = (int)(Math.pow(2, height)) * 4;
            while (i < Math.pow(2, height + 1)) {
                i++;
                NodeLevel n = queue.removeFirst();
                if (n.node.left != null) {
                    queue.add(new NodeLevel(n.node.left, n.level + 1, n.node.left.value + "", ++nodeCount));
                } else {
                    queue.add(new NodeLevel(EMPTY, n.level + 1, " ", ++nodeCount));
                }
                if (n.node.right != null) {
                    queue.add(new NodeLevel(n.node.right, n.level + 1, n.node.right.value + "", ++nodeCount));
                } else {
                    queue.add(new NodeLevel(EMPTY, n.level + 1, " ", ++nodeCount));
                }
                if (prevLevel != n.level) {
                    sb.append("\n");
                }
                for (int j = 0; j < maxWidth/(n.level*2); j++) {
                    sb.append(" ");
                }
                sb.append(n.value);
                for (int j = 0; j < maxWidth/(n.level*2); j++) {
                    sb.append(" ");
                }
                prevLevel = n.level;
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        IntTreeMap tree = new IntTreeMap();
        tree.set(10, 10);
        tree.set(5, 5);
        tree.set(100, 100);
        tree.set(50, 50);
        tree.set(40, 40);
        tree.set(60, 60);
        tree.set(30, 30);
        tree.set(35, 35);
        tree.set(34, 34);
        System.out.println(tree);
        System.out.println(tree.getBalance(tree.root));
    }
}
