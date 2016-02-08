import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class OperatorTree {
	private int size;  //number of nodes of the tree
	public Node root;
	public final static Set<String> UNARY_OPERATORS = new HashSet<String>();
	public final static Set<String> BINARY_OPERATORS = new HashSet<String>();
	public final static Set<String> VARIABLES = new HashSet<String>();
	public final static String ALPHABET = "abcdefghijklmnopqrstuvwxyz"; 
	private Random r;
	public final static String[] UNARY = {"-","sqrt", "sqr", "exp", "log", "sin", 
			"cos", "tan", "cotan", "asin", "acos", "atan", "sinh", "cosh", 
			"tanh", "abs", "expm1", "log1p", ""};
	public final static String[] BINARY = {"+", "-", "*", "/", "expt", "atan2", "mod", "hypot"};
	
	static {
		BINARY_OPERATORS.addAll(Arrays.asList(BINARY));
		UNARY_OPERATORS.addAll(Arrays.asList(UNARY));
	}
	
	/**
	 * @param size size of the tree, which is also the number of nodes
	 * @param numOfVars number of variables
	 */
	public OperatorTree(int size, int numOfVars) {
		this.size = size;
		r =  new Random();
		
		for (int i = 0; i < numOfVars; i++) {
			VARIABLES.add(ALPHABET.charAt(i) + "");
		}
	}
	
	public void createEmpty() {
		root = null;
		if (this.size > 0) {
			root = createEmptyHelper(root, size);
		}
	}

	private Node createEmptyHelper(Node n, int size) {
		if (size > 1) {
			n = new Node();
			size--;
			int leftSize = r.nextInt(size);
			int rightSize = size - leftSize;
			n.left = createEmptyHelper(n.left, leftSize);
			n.right = createEmptyHelper(n.right, rightSize);
		} else if (size == 1) {
			n = new Node();
		} 
		return n;
	}
	
	public String parse() {
		StringBuilder sb = new StringBuilder(0);
		Stack<String> s = new Stack<String>();
		parse(sb, root, s);	
		return sb.toString();
	}
	
	private void parse(StringBuilder sb, Node node, Stack<String> s) {
		if (node != null) {
			boolean isOperator = BINARY_OPERATORS.contains(node.data) || UNARY_OPERATORS.contains(node.data);
			if (isOperator) {
				sb.append("(" + node.data + " ");
				s.push(node.data);
			} else {
				sb.append(node.data + " ");
			}
			parse(sb, node.left, s);
			parse(sb, node.right, s);
			if (isOperator) {
				sb.append(") ");
				s.pop();
			}
		}
	}
	

	public void populate(Node n) {
		if (n != null) {
			if (n.left == null && n.right == null) {
				// leaf node
				if (r.nextInt(2) == 0) {
					n.data = r.nextDouble()* 1000+"";
					n.data = n.data.substring(0, 7);
				} else {
					String[] varArr = new String[VARIABLES.size()];
					VARIABLES.toArray(varArr);
					n.data = varArr[r.nextInt(varArr.length)];
				}
			} else if (n.left == null || n.right == null) {
				// node with single child
				// so only choose from UNARY_OPERATORS
				int opIndex = r.nextInt(UNARY_OPERATORS.size());
				String[] optArr = new String[UNARY_OPERATORS.size()];
				UNARY_OPERATORS.toArray(optArr);
				n.data = optArr[opIndex];
				populate(n.left);
				populate(n.right);
			} else {
				// node with two children
				// so only choose from BINARY_OPERATORS
				int opIndex = r.nextInt(BINARY_OPERATORS.size());
				String[] optArr = new String[BINARY_OPERATORS.size()];
				BINARY_OPERATORS.toArray(optArr);
				n.data = optArr[opIndex];
				populate(n.left);
				populate(n.right);
			}
		}
	}
	
	public int size() {
		return size;
	}
	
	public void preOrder() {
		preOrder(root);
	}
	
	private void preOrder(Node n) {
		if (n == null) {
			return;
		} else {
			System.out.println(n.data);
			preOrder(n.left);
			preOrder(n.right);
		}
	}
	
	public class Node {
		private String data;
		private Node left;
		private Node right;
		
		public Node() {
			this(null, null, null);
		}
		
		public Node(String data, Node left, Node right) {
			this.data = data;
			this.left = left;
			this.right = right;
		}
	}
	
	
	// All code below are just printing functions.
	public static void printNode(Node root) {
        int maxLevel = maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static void printNodeInternal(List<Node> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        printWhitespaces(firstSpaces);

        List<Node> newNodes = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node != null) {
                System.out.print(node.data);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).left != null)
                    System.out.print("/");
                else
                    printWhitespaces(1);

                printWhitespaces(i + i - 1);

                if (nodes.get(j).right != null)
                    System.out.print("\\");
                else
                    printWhitespaces(1);

                printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static int maxLevel(Node node) {
        if (node == null)
            return 0;

        return Math.max(maxLevel(node.left), maxLevel(node.right)) + 1;
    }

    private static boolean isAllElementsNull(List list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }
}