package me.learn.mica;

/**
 * link: https://leetcode-cn.com/problems/balanced-binary-tree/
 * description:
 * Given a binary tree, determine if it is height-balanced.
 * For this problem, a height-balanced binary tree is defined as:
 * a binary tree in which the left and right subtrees of every node differ in height by no more than 1.
 *
 * Given the following tree [3,9,20,null,null,15,7]:
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * Return true.
 *
 * @author mica
 */
public class BalancedBinaryTree {

    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        // 递归求解
        return isChildBalanced(root) && isBalanced(root.left) && isBalanced(root.right);
    }

    public boolean isChildBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        return Math.abs(height(root.left) - height(root.right)) < 2;
    }


    private int height(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        } else {
            return Math.max(height(root.left), height(root.right)) + 1;
        }
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}

