import java.util.Stack;
import java.util.List;


public class BinarySearchTree<E extends Comparable<E>> {
	class Node {
		E value;
		Node leftChild = null;
		Node rightChild = null;
		Node(E value) {
			this.value = value;
		}
		@Override
		public boolean equals(Object obj) {
			if ((obj instanceof BinarySearchTree.Node) == false)
				return false;
			@SuppressWarnings("unchecked")
			Node other = (BinarySearchTree<E>.Node)obj;
			return other.value.compareTo(value) == 0 &&
					other.leftChild == leftChild && other.rightChild == rightChild;
		}
	}
	
	protected Node root = null;
	
	protected void visit(Node n) {
		System.out.println(n.value);
	}
	
	public boolean contains(E val) {
		return contains(root, val);
	}
	
	protected boolean contains(Node n, E val) {
		if (n == null) return false;
		
		if (n.value.equals(val)) {
			return true;
		} else if (n.value.compareTo(val) > 0) {
			return contains(n.leftChild, val);
		} else {
			return contains(n.rightChild, val);
		}
	}
	
	public boolean add(E val) {
		if (root == null) {
			root = new Node(val);
			return true;
		}
		return add(root, val);
	}
	
	protected boolean add(Node n, E val) {
		if (n == null) {
			return false;
		}
		int cmp = val.compareTo(n.value);
		if (cmp == 0) {
			return false; // this ensures that the same value does not appear more than once
		} else if (cmp < 0) {
			if (n.leftChild == null) {
				n.leftChild = new Node(val);
				return true;
			} else {
				return add(n.leftChild, val);
			} 	
		} else {
			if (n.rightChild == null) {
				n.rightChild = new Node(val);
				return true;
			} else {
				return add(n.rightChild, val);
			} 	
		}
	}	
	
	public boolean remove(E val) {
		return remove(root, null, val);
	}
	
	protected boolean remove(Node n, Node parent, E val) {
		if (n == null) return false;

		if (val.compareTo(n.value) == -1) {
				return remove(n.leftChild, n, val);
		} else if (val.compareTo(n.value) == 1) {
				return remove(n.rightChild, n, val);
		} else {
			if (n.leftChild != null && n.rightChild != null){
				n.value = maxValue(n.leftChild);
				remove(n.leftChild, n, n.value);
			} else if (parent == null) {
				root = n.leftChild != null ? n.leftChild : n.rightChild;
			} else if (parent.leftChild == n){
				parent.leftChild = n.leftChild != null ? n.leftChild : n.rightChild;
			} else {
				parent.rightChild = n.leftChild != null ? n.leftChild : n.rightChild;
			}
			return true;
		}
	}

	protected E maxValue(Node n) {
		if (n.rightChild == null) {
			return n.value;
	    } else {
	       return maxValue(n.rightChild);
	    }
	}

	
	/*********************************************
	 * 
	 * IMPLEMENT THE METHODS BELOW!
	 *
	 *********************************************/
	
	
	// Method #1.
	public Node findNode(E val) {
        Node node = this.root; 
        if(val == null) return null;
        while(!node.value.equals(val)){
            if(node.value.compareTo(val) > 0) node = node.leftChild;
            else node = node.rightChild;
            if(node == null) return null;
        }
        return node;

	}
	
	// Method #2.
	protected int depth(E val) { // Basically the same method as findNode
        int depth = 0;
        Node node = this.root; 
        if(val == null) return -1;
        while(!node.value.equals(val)){
            if(node.value.compareTo(val) > 0) node = node.leftChild;
            else node = node.rightChild;
            if(node == null) return -1;
            depth++;
        }
        return depth;
	}
	
	// Method #3.
	protected int height(E val) {
        int leftHeight, rightHeight;
        Node node = this.findNode(val); 
        if(val == null) return -1;
        if(node == null) return -1;

        if(node.leftChild == null && node.rightChild == null) return 0;

        leftHeight = node.leftChild == null ? 0 : this.height(node.leftChild.value); 
        rightHeight = node.rightChild == null ? 0 : this.height(node.rightChild.value); 
		return leftHeight > rightHeight ? leftHeight + 1 : rightHeight + 1; 
	}

	// Method #4.
	protected boolean isBalanced(Node n) {	
        
        if(n == null) return false;
        if(this.findNode(n.value) == null) return false;

        int diff = Math.abs(this.height(n.leftChild == null ? null : n.leftChild.value) - this.height(n.rightChild == null ? null : n.rightChild.value));
        return diff == 0 || diff == 1;
	}
	
	// Method #5. .
	public boolean isBalanced() {
        Stack<Node> explored = new Stack<Node>();
        
        explored.push(this.root);
        Node node;
        while(!explored.isEmpty()){
            node = explored.pop();
            if(!this.isBalanced(node)) return false;
            if(node.leftChild != null){
                explored.push(node.leftChild);
            }
            if(node.rightChild != null){
                explored.push(node.rightChild);
            }
        }
		return true; 
	}

}
