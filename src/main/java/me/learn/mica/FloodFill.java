package me.learn.mica;

import java.util.Stack;

/**
 * link: https://leetcode-cn.com/problems/flood-fill/
 * description:
 * An image is represented by a 2-D array of integers, each integer representing the pixel value of the image (from 0 to 65535).
 * Given a coordinate (sr, sc) representing the starting pixel (row and column) of the flood fill,
 * and a pixel value newColor, "flood fill" the image.
 * To perform a "flood fill", consider the starting pixel,
 * plus any pixels connected 4-directionally to the starting pixel of the same color as the starting pixel,
 * plus any pixels connected 4-directionally to those pixels (also with the same color as the starting pixel),
 * and so on. Replace the color of all of the aforementioned pixels with the newColor.
 * At the end, return the modified image.
 *
 * Input:
 * image = [[1,1,1],[1,1,0],[1,0,1]]
 * sr = 1, sc = 1, newColor = 2
 *
 * Output:
 * [[2,2,2],[2,2,0],[2,0,1]]
 *
 * Explanation:
 * From the center of the image (with position (sr, sc) = (1, 1)), all pixels connected
 * by a path of the same color as the starting pixel are colored with the new color.
 * Note the bottom corner is not colored 2, because it is not 4-directionally connected
 * to the starting pixel.
 *
 *
 *
 * @author mica
 */
public class FloodFill {

    /**
     * 填充之后的 image图像返回值
     *
     * @param image    原始的图像
     * @param sr       起始横坐标
     * @param sc       起始纵坐标
     * @param newColor 将要替换的像素点
     * @return 新 image图像
     */
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
//        return findBoxUsingStack(image, sr, sc, newColor);
        if (image[sr][sc] == newColor) {
            return image;
        }
        findBoxUsingDfs(image, sr, sc, image[sr][sc], newColor);
        return image;
    }

    /**
     * 使用数据结构栈完成代码的书写
     *
     * @param image    原始传入的 image数组
     * @param sr       起始横坐标
     * @param sc       起始纵坐标
     * @param newColor 新的像素值
     * @return 新的 image图像
     */
    private int[][] findBoxUsingStack(int[][] image, int sr, int sc, int newColor) {
        int[][] mark = new int[image.length][image[0].length];
        final int original = image[sr][sc];
        // 使用栈存储将要填充的元素位置
        Stack<int[]> stack = new Stack<>();
        int[] pos = {sr, sc};
        stack.push(pos);
        while (!stack.isEmpty()) {
            int[] centerElement = stack.pop();
            int centerSr = centerElement[0];
            int centerSc = centerElement[1];
            // 为中间的位置附上新的像素值
            if (original == image[centerSr][centerSc]) {
                image[centerSr][centerSc] = newColor;
            } else {
                continue;
            }

            int leftSr = centerSr - 1;
            int rightSr = centerSr + 1;
            int upSc = centerSc - 1;
            int downSc = centerSc + 1;

            if (leftSr >= 0) {
                int[] ele = {leftSr, centerSc};
                if (mark[leftSr][centerSc] != 1) {
                    stack.push(ele);
                    // mark一下
                    mark[leftSr][centerSc] = 1;
                }
            }
            if (rightSr < image.length) {
                int[] ele = {rightSr, centerSc};
                if (mark[rightSr][centerSc] != 1) {
                    stack.push(ele);
                    mark[rightSr][centerSc] = 1;
                }
            }
            if (upSc >= 0) {
                int[] ele = {centerSr, upSc};
                if (mark[centerSr][upSc] != 1) {
                    stack.push(ele);
                    mark[centerSr][upSc] = 1;
                }
            }
            if (downSc < image[0].length) {
                int[] ele = {centerSr, downSc};
                if (mark[centerSr][downSc] != 1) {
                    stack.push(ele);
                    mark[centerSr][downSc] = 1;
                }
            }
        }
        return image;
    }


    /**
     * 使用 DFS对执行时间进行优化
     *
     * @param image    传入的 image图像值
     * @param sr       起始横坐标
     * @param sc       起始纵坐标
     * @param oldColor 旧的像素值
     * @param newColor 新的像素值
     * @return 新的 image的图标
     */
    private void findBoxUsingDfs(int[][] image, int sr, int sc, int oldColor, int newColor) {
        if (sr >= 0 && sr < image.length && sc >= 0 &&
                sc < image[0].length && oldColor == image[sr][sc]) {
            image[sr][sc] = newColor;
            findBoxUsingDfs(image, sr - 1, sc, oldColor, newColor);
            findBoxUsingDfs(image, sr + 1, sc, oldColor, newColor);
            findBoxUsingDfs(image, sr , sc - 1, oldColor, newColor);
            findBoxUsingDfs(image, sr, sc + 1, oldColor, newColor);
        }
    }


    public static void main(String[] args) {
        int[][] image = {{1,1,1},{1,1,0},{1,0,1}};
        int sc = 1;
        int sr = 1;
        int newColor = 1;
        int[][] ints = new FloodFill().floodFill(image, sr, sc, newColor);
        for (int i = 0; i < ints.length; ++ i) {
            for (int j = 0; j < ints[0].length; ++ j) {
                System.out.println(ints[i][j]);
            }
        }
    }

}
