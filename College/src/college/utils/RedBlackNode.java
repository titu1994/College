package college.utils;

import college.utils.RedBlackTree.Datum;


public class RedBlackNode<T extends Datum> {
	protected RedBlackNode<T> left, right;
	T data;
	int color;

	public RedBlackNode(T data) {
		this(data, null, null);
	}

	public RedBlackNode(T data, RedBlackNode<T> lt, RedBlackNode<T> rt) {
		left = lt;
		right = rt;
		this.data = data;
		color = 1;
	}
}
