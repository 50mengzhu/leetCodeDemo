package me.learn.mica;


/**
 * link: https://leetcode-cn.com/problems/remove-boxes/
 * Description:
 * Given several boxes with different colors represented by different positive numbers.
 * You may experience several rounds to remove boxes until there is no box left. 
 * Each time you can choose some continuous boxes with the same color (composed of k boxes, k >= 1), remove them and get k*k points.
 * Find the maximum points you can get.
 * 
 * 
 * Input: boxes = [1,3,2,2,2,3,4,3,1]
 * Output: 23
 * Explanation:
 * [1, 3, 2, 2, 2, 3, 4, 3, 1] 
 *  ----> [1, 3, 3, 4, 3, 1] (3*3=9 points) 
 *  ----> [1, 3, 3, 3, 1] (1*1=1 points) 
 *  ----> [1, 1] (3*3=9 points) 
 *  ----> [] (2*2=4 points)
 * 
 * 
 * 
 * 
 * 根据题中的描述，我们知道本题应当采用动态规划来完成，主要的问题就是怎样将大问题划分为小问题进行解决
 * 首先因为此题是一个关于数组的问题，因此我们可能比较容易想到，使用 score[s, e]通过数组起止下标 start和 end将问题简化
 * 紧接着需要解决的问题就是，怎样通过小问题来求解大问题；实际上当从小问题向大问题扩展的时候，只有数组起止下标是有点力不从心的
 * 因为每一次在小问题的规模上添加元素，实际上并不只是简单的叠加，而是需要将原始的已经做好的小问题的基础之上进行重排序，
 * 因此这也就是本题的难点，需要再次使用再另外一个标志位描述整个数组的状态。
 * 
 * 于是使用start、end以及 num(表示end之后数组中还含有多少个连续和end所在位置元素相等的元素)
 * 
 * @author mica
 */
public class RemoveBoxes {


    /**
     * 移除 boxes的操作
     *
     * @param boxes 输入的数组
     * @return 返回最终结果
     */
    public int removeBoxes(int[] boxes) {
        // 定义将要使用的dp数组
        int[][][] result = new int[100][100][100];
        return calculate(boxes, result, 0, boxes.length - 1, 0);
    }

    /**
     * 计算 left到 right这子段的结果
     *
     * @param boxes  原始的数据
     * @param result 执行的结果
     * @param left   左侧下标
     * @param right  右侧下标
     * @param num    在原数组中右侧和右侧下标连续相同的元素的数量
     *               ([1,3,4,5,6,6] -> [1,3,4,5] : num = 0)
     *               ([1,3,4,5,6,6] -> [1,3,4,5,6] : num = 1)
     * @return [left, right]这段的执行结果
     */
    private int calculate(int[] boxes, int[][][] result, int left, int right, int num) {
        // 限制不合法的输入，实际上也是为 dp中的数组赋值
        if (right < left) {
            return 0;
        }
        // 如果待求的结果已经存在数组中，则立即返回。相当于执行了缓存
        if (result[left][right][num] != 0) {
            return result[left][right][num];
        }

        // 当所选范围中的出现类似于这样的情形 [l,x,x,e,r, ... ,u,x,x,x] y,x,y
        // 那么实际上相当于可以将后续的三个xxx不用考虑，而是从u开始考虑
        // 为题解中三维数组转二维提供num的积累
        while (right > left && boxes[right] == boxes[right - 1]) {
            -- right;
            ++ num;
        }
        // 递归计算代码中的结果数组
        result[left][right][num] =
                calculate(boxes, result, left, right - 1, 0) + (num + 1) * (num + 1);

        for (int i = left; i < right; ++ i) {
            if (boxes[i] == boxes[right]) {
                result[left][right][num] =
                        Math.max(result[left][right][num],
                                calculate(boxes, result, left, i, num + 1)
                                        + calculate(boxes, result, i + 1, right - 1, 0));
            }
        }

        return result[left][right][num];
    }


    public static void main(String[] args) {
        int[] boxes = {1,3,2,2,2,3,4,3,1};
        System.out.println(new RemoveBoxes().removeBoxes(boxes));
    }
}