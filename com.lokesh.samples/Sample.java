import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

public class Sample {

    /**
     * calculates the nth fib number using recursion with memoization.
     * @param position
     * @param memo
     * @return
     */
    private static long fib_num_at_nth_position_recursive(int position, Map<Integer, Long> memo) {
        if (memo.containsKey(position)) {
            return memo.get(position);
        }
        if (position == 1 || position == 2) {
            return 1;
        }
        final long intermdeiateValue = fib_num_at_nth_position_recursive(position - 1, memo) + fib_num_at_nth_position_recursive(position - 2, memo);
        memo.put(position, intermdeiateValue);
        return intermdeiateValue;
    }

    /**
     * Calculates the nth fib number using tabulation.
     * @param position
     * @return
     */
    private static long fib_num_at_nth_position_tabulation(int position) {
        List<Long> lst = new ArrayList<>(position);//.stream().map(it -> 0).collect(Collectors.toList());

        IntStream.range(0, position).forEach(it -> lst.add(it, 0L));

        lst.add(1, 1L);

        for (int i = 0; i <= position; i++) {
            lst.add(i + 2, lst.get(i) + lst.get(i + 1));
        }
        return lst.get(position);
    }

    /**
     * Calculates the possibility if a number can be built by summing a combination of numbers from an array of numbers. Uses recursion with memoization.
     * @param targetSum
     * @param numbers
     * @param memo
     * @return
     */
    private static boolean canSum(int targetSum, List<Integer> numbers, Map<Integer, Boolean> memo) {
        if (memo.containsKey(targetSum)) {
            return memo.get(targetSum);
        }
        if (targetSum == 0) {
            return true;
        }
        if (targetSum < 0) {
            return false;
        }
        for (int num : numbers) {
            int newTargetSum = targetSum - num;
            final boolean intermediate = canSum(newTargetSum, numbers, memo);

            if (intermediate) {
                memo.put(targetSum, true);
                return true;
            }
        }
        memo.put(targetSum, false);
        return false;
    }

    /**
     * Calculates the combination of numbers using which a target number can be achieved from an array of numbers. Uses recursion with memoization.
     * @param targetSum
     * @param numbers
     * @param memo
     * @return
     */
    private static List<Integer> howSum(int targetSum, List<Integer> numbers, Map<Integer, List<Integer>> memo) {
        if (memo.containsKey(targetSum)) {
            return memo.get(targetSum);
        }
        if (targetSum == 0) {
            return new ArrayList<>();
        }
        if (targetSum < 0) {
            return null;
        }
        for (int num : numbers) {
            int remainder = targetSum - num;
            List<Integer> res = howSum(remainder, numbers, memo);
            if (res != null) {
                List<Integer> temp = new ArrayList<>(res);
                temp.add(num);
                memo.put(targetSum, temp);
                return temp;
            }
        }

        memo.put(targetSum, null);
        return null;

    }

    /**
     * calculates the best possible combination of numbers to arrive at a target number from an array of numbers. Uses recursion with memoization.
     * @param targetSum
     * @param numbers
     * @param memo
     * @return
     */
    private static List<Integer> bestSum(int targetSum, List<Integer> numbers, Map<Integer, List<Integer>> memo) {
        if (memo.containsKey(targetSum)) {
            return memo.get(targetSum);
        }
        if (targetSum == 0) {
            return new ArrayList<>();
        }
        if (targetSum < 0) {
            return null;
        }

        List<Integer> combination = null;
        for (int num : numbers) {
            int remainder = targetSum - num;
            List<Integer> remainderResult = bestSum(remainder, numbers, memo);
            if (remainderResult != null) {
                List<Integer> current_combination = new ArrayList<>(remainderResult);
                current_combination.add(num);
                if (combination == null || combination.size() > current_combination.size()) {
                    combination = current_combination;
                }
            }

        }

        memo.put(targetSum, combination);
        return combination;

    }

    /**
     * calculates the best possible combination of numbers to arrive at a target number from an array of numbers. Uses tabulation.
     * @param targetSum
     * @param numbers
     * @param memo
     * @return
     */
    private static List<Integer> bestSum_tabulation(int targetSum, List<Integer> numbers, Map<Integer, List<Integer>> memo) {
        if (targetSum == 0) {
            return new ArrayList<>();
        }

        List<List<Integer>> table = new ArrayList<>();

        IntStream.range(0, targetSum).forEach(it -> table.add(null));

        table.add(0, new ArrayList<>());

        for (int i = 0; i <= targetSum; i++) {
            for (int num : numbers) {
                if (table.get(i) != null) {
                    final List<Integer> lst = new ArrayList<>(table.get(i));
                    lst.add(0, num);

                    if (i + num <= targetSum) {
                        if (table.get(i + num) == null) {
                            table.remove(i + num);
                            table.add(i + num, lst);
                        } else if (table.get(i + num).size() > lst.size()) {
                            table.remove(i + num);
                            table.add(i + num, lst);
                        }

                    }
                }
            }
        }

        return table.get(targetSum);

    }

    /**
     * Calculates the possibility if a target string can be constructed by combining the strings from a given array of strings. Uses recursion with memoization.
     * @param target
     * @param strs
     * @param memo
     * @return
     */
    private static boolean canConstruct(String target, List<String> strs, Map<String, Boolean> memo) {
        if (memo.containsKey(target)) {
            return memo.get(target);
        }
        if (target.isEmpty()) {
            return true;
        }

        for (String str : strs) {
            if (target.startsWith(str)) {
                if (canConstruct(target.substring(str.length()), strs, memo)) {
                    memo.put(target, true);
                    return true;
                }
            }
        }
        memo.put(target, false);
        return false;
    }

    /**
     * Calculates the possibility if a target string can be constructed by combining the strings from a given array of strings. Uses tabulation.
     * @param target
     * @param strs
     * @param memo
     * @return
     */
    private static boolean canConstruct_tabulation(String target, List<String> strs, Map<String, Boolean> memo) {
        if (target.length() == 0) {
            return true;
        }
        boolean[] table = new boolean[target.length() + 1];

        table[0] = true;

        for (int i = 0; i < target.length(); i++) {
            for (String str : strs) {
                if (str.startsWith(Character.toString(target.charAt(i)))) {
                    if (i + str.length() <= target.length()) {
                        table[i + str.length()] = table[i];
                    }

                }
            }
        }

        return table[target.length()];
    }

    /**
     * Calculates the number of combinations by which a target string can be constructed by combining the strings from a given array of strings. Uses recursion with memoization.
     * @param target
     * @param strs
     * @param memo
     * @return
     */
    private static int countConstructs(String target, List<String> strs, Map<String, Integer> memo) {
        if (memo.containsKey(target)) {
            return memo.get(target);
        }
        if (target.length() == 0) {
            return 1;
        }

        int total = 0;
        for (String str : strs) {
            if (target.startsWith(str)) {
                total += countConstructs(target.substring(str.length()), strs, memo);
            }
        }

        memo.put(target, total);
        return total;
    }

    /**
     * Calculates the number of combinations by which a target string can be constructed by combining the strings from a given array of strings. Uses tabulation.
     * @param target
     * @param strs
     * @param memo
     * @return
     */
    private static int countConstructs_tabulation(String target, List<String> strs, Map<String, Integer> memo) {
        if (target.length() == 0) {
            return 1;
        }
        int[] table = new int[target.length() + 1];
        for (int i = 0; i <= target.length(); i++) {
            table[i] = 0;
        }
        table[0] = 1;

        for (int i = 0; i < target.length(); i++) {
            for (String str : strs) {
                if (str.startsWith(Character.toString(target.charAt(i)))) {
                    if (i + str.length() <= target.length()) {
                        table[i + str.length()] += table[i];
                    }
                }
            }
        }
        return table[target.length()];
    }

    /**
     * calculates all the possible combination by which a target string can be cosntructed using strings of a given array. Uses recursion with memoization.
     * @param target
     * @param strs
     * @param memo
     * @return
     */
    private static List<List<String>> allConstructs(String target, List<String> strs, Map<String, List<List<Integer>>> memo) {
        if (target.isEmpty()) {
            final List<List<String>> tmp = new ArrayList<>();
            tmp.add(new ArrayList<>());
            return tmp;
        }

        List<List<String>> constructs = new ArrayList<>();
        for (String str : strs) {
            if (target.startsWith(str)) {
                List<List<String>> suffixWays = allConstructs(target.substring(str.length()), strs, memo);

                for (List<String> way : suffixWays) {
                    way.add(0, str);
                }
                constructs.addAll(suffixWays);
            }
        }
        return constructs;
    }

    /**
     * calculates number of moves required to move from top left to bottom right of 2D grid. Uses recursion with memoization.
     * @param m
     * @param n
     * @return
     */
    private static int grid_traveler(int m, int n) {
        if (m == 1 && n == 1) {
            return 1;
        }
        if (m == 0 || n == 0) {
            return 0;
        }

        return grid_traveler(m - 1, n) + grid_traveler(m, n - 1);
    }

    /**
     * calculates number of moves required to move from top left to bottom right of 2D grid. Uses tabulation.
     * @param m
     * @param n
     * @return
     */
    private static int grid_traveler_tabulation(int m, int n) {
        if (m == 1 && n == 1) {
            return 1;
        }
        if (m == 0 || n == 0) {
            return 0;
        }

        int[][] table = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                table[i][j] = 0;
            }
        }
        table[1][1] = 1;
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i + 1 <= m) {
                    table[i + 1][j] += table[i][j];
                }
                if (j + 1 <= n) {
                    table[i][j + 1] += table[i][j];
                }

            }
        }

        return table[m][n];

    }

    private static int river_length(int m, int n, int[][] matrix, boolean[][] visited, int total_length) {
        int width = matrix.length;
        int height = matrix[0].length;

        if (m < 0 || n < 0 || m >= width || n >= height) {
            return 0;
        }
        if (visited[m][n]) {
            visited[m][n] = true;
            return 0;
        }

        if (matrix[m][n] == 0) {
            visited[m][n] = true;
            return total_length;
        }

        if (matrix[m][n] == 1) {
            visited[m][n] = true;

            return 1 + river_length(m, n - 1, matrix, visited, total_length)
                       + river_length(m + 1, n, matrix, visited, total_length)
                       + river_length(m, n + 1, matrix, visited, total_length)
                       + river_length(m - 1, n, matrix, visited, total_length);
        }

        return total_length;

    }

    /**
     * Solves the river length problem specified here : https://www.algoexpert.io/questions/River%20Sizes . Uses recursion.
     * @param matrix
     * @return
     */
    private static List<Integer> all_river_lengths(int[][] matrix) {
        boolean[][] visited = new boolean[][] {{false, false, false, false},
            {false, false, false, false},
            {false, false, false, false}};
        int width = matrix.length;
        int height = matrix[0].length;
        List<Integer> river_lengths = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                final int river_length = river_length(i, j, matrix, visited, 0);
                if (river_length > 0) {
                    river_lengths.add(river_length);
                }
            }
        }

        return river_lengths;

    }

    /**
     * Solves the problem specified here : https://www.algoexpert.io/questions/Shift%20Linked%20List.
     * @param k
     * @param lst
     * @return
     */
    private static MyLinkedList shifted_by_k(int k, MyLinkedList lst) {
        int lst_length = lst.length();

        if(k > 0 && k >= lst_length) {
            k -= lst_length; //reset k !
        }

        if(k < 0) {
            k = k * -1;
            k = 2 * lst_length - k;
            k = k % lst_length;
        }

        if(k % lst_length == 0) { //base case
            return lst;
        }
        Node current_head = lst.getHead();
        Node current = lst.getHead();
        int idx = 2;
        int condition = lst.length() - k;

        while(idx <= condition) {
            current = current.getNext();
            idx+=1;
        }
        Node new_tail = current;
        Node next = current.getNext();
        new_tail.setNext(null); //new tail

        lst.setHead(next); // new head

        while(next.getNext() != null) {
            next = next.getNext();
        }
        next.setNext(current_head);

        return lst;
    }


    /**
     * Main method for testing purpose..
     * @param args
     */
    public static void main(String[] args) {

        MyLinkedList lst = new MyLinkedList();

        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);

        lst.add(n1);
        lst.add(n2);
        lst.add(n3);
        lst.add(n4);
        lst.add(n5);
        lst.add(n6);

        lst.print();

        int k = 2;

        shifted_by_k(k, lst).print();


        /*lst.print();

        int k = 2;

        shifted_by_k(k, lst).print();
*/



        /*int[][] matrix =  new int[][]{{1,0,1,1},
                                      {1,1,0,0},
                                      {0,1,1,1}};

        System.out.println("all river lenghts:"+ all_river_lengths(matrix));*/

        /*int m = 0, n = 0;

        int[][] matrix =  new int[][]{{1,0,1,1},
                                      {1,1,0,0},
                                      {0,1,1,1}};
        boolean[][] visited =  new boolean[][]{{false,false,false,false},
                                                {false,false,false,false},
                                                {false,false,false,false}};

        System.out.println("river length from index:"+ m +","+n+" is:"+river_length(m,n,matrix,visited,0));*/

        /*int num =  100;
        List<Integer> numbers = Arrays.asList(1,2,5,25);

        System.out.println("bestSum for "+ num + " is :"+ bestSum_tabulation(num, numbers, new HashMap<>()));*/

        /*String target = "abcdef";
        List<String> strs = Arrays.asList("abx", "abc", "cd", "defy", "abcd");

        System.out.println("canConstruct " + target + " out of " + strs + " : " + canConstruct_tabulation(target, strs, new HashMap<>()));*/

        /*String target = "abcdef";
        List<String> strs = Arrays.asList("ab", "abc", "cd", "def", "abcd");

        System.out.println("count of constructs "+ target + " out of "+ strs +" : "+ countConstructs_tabulation(target, strs, new HashMap<>()));*/

        /*int m = 3, n = 3;
        System.out.println("Number of ways to travel in a grid of "+ m + " X " + n + " is:"+ grid_traveler_tabulation(m, n));*/

        /*int m = 2, n = 3;
        System.out.println("Number of ways to travel in a grid of "+ m + " X " + n + " is:"+ grid_traveler(m, n));*/

        /*String target = "abcdef";
        List<String> strs = Arrays.asList("ab", "abc", "cd", "def", "abcd", "ef", "c");

        System.out.println("canConstruct "+ target + " out of "+ strs +" :="+ allConstructs(target, strs, new HashMap<>()));*/


        /*String target = "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeef";
        List<String> strs = Arrays.asList("ee", "eeeeeeeeeeeeeeee", "eeeeeeeeeeeee", "eeee", "e");

        System.out.println("countConstructs "+ target + " out of "+ strs +" : "+ countConstructs(target, strs, new HashMap<>()));*/

        /*String target = "abcdef";
        List<String> strs = Arrays.asList("ab", "abc", "cd", "def", "abcd");

        System.out.println("canConstruct "+ target + " out of "+ strs +" :="+ canConstruct(target, strs, new HashMap<>()));

        target = "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeef";
        strs = Arrays.asList("ee", "eeeeeeeeeeeeeeee", "eeeeeeeeeeeee", "eeee", "e");*/

        //System.out.println("canConstruct "+ target + " out of "+ strs +" :="+ canConstruct(target, strs, new HashMap<>()));


        /*int num =  100;
        List<Integer> numbers = Arrays.asList(1,2,5,25);

        System.out.println("bestSum for "+ num + " is :"+ bestSum(num, numbers, new HashMap<>()));*/

        //System.out.println(">>>>>>howSum:"+ howSum(300, Arrays.asList(7,14), new HashMap<>()));

        /*Map<Integer, Boolean> memo = new HashMap<>();
        int num = 300;
        List<Integer> numbers = Arrays.asList(7,14);

        System.out.println("canSum "+ num + " from numbers:"+ canSum(num, numbers, memo));*/

        /*int num = 8;
        Map<Integer, Long> memo = new HashMap<>();
        System.out.println(">>>>fib at "+num+"th position is:"+ fib_num_at_nth_position_recursive(num, memo));
        num = 11;
        System.out.println(">>>>fib at "+num+"th position is:"+ fib_num_at_nth_position_recursive(num, memo));
        num = 21;
        System.out.println(">>>>fib at "+num+"th position is:"+ fib_num_at_nth_position_recursive(num, memo));
        num = 50;
        System.out.println(">>>>fib at "+num+"th position is:"+ fib_num_at_nth_position_recursive(num, memo));*/

        /*int num = 8;
        Map<Integer, Long> memo = new HashMap<>();
        System.out.println(">>>>fib at "+num+"th position is:"+ fib_num_at_nth_position_tabulation(num));
        num = 11;
        System.out.println(">>>>fib at "+num+"th position is:"+ fib_num_at_nth_position_tabulation(num));
        num = 21;
        System.out.println(">>>>fib at "+num+"th position is:"+ fib_num_at_nth_position_tabulation(num));
        num = 50;
        System.out.println(">>>>fib at "+num+"th position is:"+ fib_num_at_nth_position_tabulation(num));*/

    }

}

/**
 * Custom linked list implementation for testing purpose.
 */
class MyLinkedList {

    Node head;
    int length;

    public Node getHead() {
        return head;
    }

    public void add(Node node) {

        if (head == null) {
            this.length = 1;
            head = node;
        } else {
            Node current = head;
            while(current.getNext() != null) {
                current = current.getNext();
            }
            this.length += 1;
            current.next = node;
        }
    }

    public int length() {
        return this.length;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MyLinkedList that = (MyLinkedList) o;
        return Objects.equals(head, that.head);
    }

    @Override
    public int hashCode() {

        return Objects.hash(head);
    }

    void print() {
        Node current = head;
        System.out.println();
        while(current != null) {
            System.out.print(" "+ current.getVal());
            current = current.next;
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return "MyLinkedList{" +
                   "head=" + head +
                   '}';
    }
}

class Node {

    int val;
    Node next = null;

    Node(int val) {
        this.val = val;
        this.next = null;
    }

    public int getVal() {
        return val;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Node node = (Node) o;
        return val == node.val &&
                   Objects.equals(next, node.next);
    }

    @Override
    public int hashCode() {

        return Objects.hash(val, next);
    }

    @Override
    public String toString() {
        return "Node{" +
                   "val=" + val +
                   ", next=" + next +
                   '}';
    }
}
