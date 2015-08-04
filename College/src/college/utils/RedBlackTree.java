package college.utils;

import college.utils.RedBlackTree.Datum;

public class RedBlackTree<Data extends Datum> {

	private RedBlackNode<Data> current;
	private RedBlackNode<Data> parent;
	private RedBlackNode<Data> grand;
	private RedBlackNode<Data> great;
	private RedBlackNode<Data> header;
	private RedBlackNode<Data> nullNode;

	/* Black - 1 RED - 0 */
	public static final int BLACK = 1;
	public static final int RED = 0;
	private static final int NULLVALUE = 0;
	private static final int HEADERVALUE = Integer.MIN_VALUE;

	public RedBlackTree() {
		Datum nullv = new Datum(NULLVALUE);
		Datum headerv = new Datum(HEADERVALUE);
		
		nullNode = new RedBlackNode(nullv);
		nullNode.left = nullNode;
		nullNode.right = nullNode;
		
		header = new RedBlackNode(headerv);
		header.left = nullNode;
		header.right = nullNode;
	}

	/* Function to check if tree is empty */
	public boolean isEmpty() {
		return header.right == nullNode;
	}

	/* Make the tree logically empty */
	public void makeEmpty() {
		header.right = nullNode;
	}

	/* Function to insert item */
	public void insert(Data item) {
		current = parent = grand = header;
		nullNode.data = item;
		while (current.data != item) {
			great = grand;
			grand = parent;
			parent = current;
			current = item.compareTo(current.data) < 0 ? current.left : current.right;
			// Check if two red children and fix if so
			if (current.left.color == RED && current.right.color == RED)
				handleReorient(item);
		}
		// Insertion fails if already present
		if (current != nullNode) {
			return;
		}
			
		current = new RedBlackNode<Data>(item, nullNode, nullNode);
		// Attach to parent
		if (item.compareTo(parent.data) < 0)
			parent.left = current;
		else
			parent.right = current;
		handleReorient(item);
	}

	private void handleReorient(Data item) {
		// Do the color flip
		current.color = RED;
		current.left.color = BLACK;
		current.right.color = BLACK;

		if (parent.color == RED) {
			// Have to rotate
			grand.color = RED;
			if (item.compareTo(grand.data) < 0 != item.compareTo(parent.data) < 0)
				parent = rotate(item, grand); // Start dbl rotate
			current = rotate(item, great);
			current.color = BLACK;
		}
		// Make root black
		header.right.color = BLACK;
	}

	private RedBlackNode<Data> rotate(Data item, RedBlackNode<Data> parent) {
		if (item.compareTo(parent.data) < 0)
			return parent.left = item.compareTo(parent.left.data) < 0 ? rotateWithLeftChild(parent.left)
					: rotateWithRightChild(parent.left);
		else
			return parent.right = item.compareTo(parent.right.data) < 0 ? rotateWithLeftChild(parent.right)
					: rotateWithRightChild(parent.right);
	}

	/* Rotate binary tree node with left child */
	private RedBlackNode<Data> rotateWithLeftChild(RedBlackNode<Data> k2) {
		RedBlackNode<Data> k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		return k1;
	}

	/* Rotate binary tree node with right child */
	private RedBlackNode<Data> rotateWithRightChild(RedBlackNode<Data> k1) {
		RedBlackNode<Data> k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		return k2;
	}

	/* Functions to count number of nodes */
	public int countNodes() {
		return countNodes(header.right);
	}

	private int countNodes(RedBlackNode<Data> r) {
		if (r == nullNode)
			return 0;
		else {
			int l = 1;
			l += countNodes(r.left);
			l += countNodes(r.right);
			return l;
		}
	}

	/* Functions to search for an data */
	public boolean search(Data val) {
		return search(header.right, val);
	}

	private boolean search(RedBlackNode<Data> r, Data val) {
		boolean found = false;
		while ((r != nullNode) && !found) {
			Data rval = r.data;
			if (val.compareTo(rval) < 0)
				r = r.left;
			else if (val.compareTo(rval) > 0)
				r = r.right;
			else {
				found = true;
				break;
			}
			found = search(r, val);
		}
		return found;
	}

	/* Function for inorder traversal */
	public void inorder() {
		inorder(header.right);
	}

	private void inorder(RedBlackNode<Data> r) {
		if (r != nullNode) {
			inorder(r.left);
			char c = 'B';
			if (r.color == 0)
				c = 'R';
			System.out.print(r.data + "" + c + " ");
			inorder(r.right);
		}
	}

	/* Function for preorder traversal */
	public void preorder() {
		preorder(header.right);
	}

	private void preorder(RedBlackNode<Data> r) {
		if (r != nullNode) {
			char c = 'B';
			if (r.color == 0)
				c = 'R';
			System.out.print(r.data + "" + c + " ");
			preorder(r.left);
			preorder(r.right);
		}
	}

	/* Function for postorder traversal */
	public void postorder() {
		postorder(header.right);
	}

	private void postorder(RedBlackNode<Data> r) {
		if (r != nullNode) {
			postorder(r.left);
			postorder(r.right);
			char c = 'B';
			if (r.color == 0)
				c = 'R';
			System.out.print(r.data + "" + c + " ");
		}
	}
	
	public static class Datum implements Comparable<Datum>{

		int x;
		
		public Datum(int x) {
			this.x = x;
		}
		
		@Override
		public int compareTo(Datum o) {
			return this.x - o.x;
		}
		
		@Override
		public String toString() {
			return x + "";	
		}
		
	}

}
