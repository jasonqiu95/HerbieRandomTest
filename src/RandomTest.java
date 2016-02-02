
public class RandomTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OperatorTree opt = new OperatorTree(7);
		printTest(opt, 5);
//		opt.preOrder();
//		OperatorTree.printNode(opt.root);
		
	}
	
	public static void printTest(OperatorTree opt) {
		printTest(opt, 1);
	}
	
	public static void printTest(OperatorTree opt, int n) {
		for (int i  =0; i < n; i++) {
			//initialize tree
			opt.createEmpty();
			opt.populate(opt.root);
			// print test
			System.out.print("(herbie-test (");
			String[] varArr = new String[OperatorTree.VARIABLES.size()];
			OperatorTree.VARIABLES.toArray(varArr);
			System.out.print(varArr[0]);
			for (int j = 1; j < varArr.length; j++) {
				System.out.print(" " + varArr[j]);
			}
			System.out.println(")");
			System.out.println("  \"Random test " + (i+1) + "\"");
			System.out.print("    " + opt.parse());
			System.out.println(")");
			System.out.println();
		}
	}

}