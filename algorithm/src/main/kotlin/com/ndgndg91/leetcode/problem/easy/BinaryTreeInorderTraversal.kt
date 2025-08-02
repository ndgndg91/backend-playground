package com.ndgndg91.leetcode.problem.easy

class BinaryTreeInorderTraversal {
    /**
     * Example:
     * var ti = TreeNode(5)
     * var v = ti.`val`
     * Definition for a binary tree node.
     * class TreeNode(var `val`: Int) {
     *     var left: TreeNode? = null
     *     var right: TreeNode? = null
     * }
     */
    class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }

    class Solution {
        fun inorderTraversal(root: TreeNode?): List<Int> {
            val result = mutableListOf<Int>()
            inorderHelper(root, result)
            return result
        }

        private fun inorderHelper(node: TreeNode?, result: MutableList<Int>) {
            if (node == null) {
                return
            }

            inorderHelper(node.left, result)

            result.add(node.`val`)

            inorderHelper(node.right, result)
        }
    }
}

fun main() {
    val solution = BinaryTreeInorderTraversal.Solution()
    val root = BinaryTreeInorderTraversal.TreeNode(1)
    val right = BinaryTreeInorderTraversal.TreeNode(2)
    root.right  = right
    val rightLeft = BinaryTreeInorderTraversal.TreeNode(3)
    right.left = rightLeft
    check(solution.inorderTraversal(root) == listOf(1,3,2))
}