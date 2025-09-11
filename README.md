# techInterviewNote

### 基础排序

* 冒泡排序：比较相邻元素，如果顺序错误就交换。重复此步骤，直到数组有序。O(n^2)
* 选择排序：在未排序序列中找到最小（大）元素，和起始元素进行交换。 O(n^2)
* 插入排序：将未排序数组**插入**到已排序数组中。O(n^2)


### 进阶排序（理解思想）

* 快速排序：
* 归并排序：


### 比较函数

* comparable
    ```
    public class Student<Comparable> {
        ...
        @Override
        public int compareTo(Student other) {
            return Integer.compare(this.score, other.score);
        }

        public static void main(String[] args) {
            List<Student> students = Arrays.asList();

            Collections.sort(students);
        }
    }
    ```
* comparator method(Students.sort((s1, s2) -> Integer.compare(s2.getScore(), s1.getScore()));)


### 基本的搜索算法

* DFS： 深度优先。使用递归或栈来实现，适合寻找所有可能的解。可能陷入无限递归，注意边界条件。
* BFS：广度优先。使用队列来实现，适合寻找最短路径。空间复杂度高，需要存储节点信息。


### 进阶数据结构

* 堆：一种完全二叉树，分为最大堆和最小堆。最大堆的每个节点都大于或等于其子节点的值，最小堆反之。可以用来实现**优先队列**，支持插入和删除 O(logn)。
* 并查集：一种用于处理不相交集合的合并于查找问题。主要操作有查找和合并,接近O(1)。并查集常用于解决连通性问题，如判断图的连通分量。
* 树状数组：一种用于高效区处理区间查询和单点修改问题的数据结构O(logn)。实现基于二进制的思想。


### 进阶动态规划

* 01背包：



The path of jdk used: 'c:\Users\15103\.vscode\extensions\oracle.oracle-java-24.1.0\out\webviews\jdkDownloader\jdk_downloads\jdk-24.0.1'