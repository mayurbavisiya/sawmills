package dsa.pramp;

import java.util.LinkedList;
import java.util.List;

public class SawMills {

	// Approach Brute Force
	// Calculate the profit for possible permutations and return maximum among
	// them
	// Time Complexity 2^n Since we are checking for all possible permutations

	public static void main(String[] args) {
		int[] woods1 = { 2, 3, 1 };
		int[] woods2 = { 1, 2 };
		int[] woods3 = { 1, 4 };
		System.out.println(calculateMaxprofit(woods1));
		System.out.println(calculateMaxprofit(woods2));
		System.out.println(calculateMaxprofit(woods3));
	}

	public static int calculateMaxprofit(int[] woods) {
		List<List<Integer>> ls = permute(woods);
		int profit = 0;
		for (List<Integer> list : ls) {
			profit = Math.max(profit, getProfit(list));
		}
		return profit;
	}

	// This method return the profit for one possible combination
	// Time Complexity O(n), Space Complexity O(1)
	private static int getProfit(List<Integer> arr) {
		int cuts = 3, start = 0, temp = 0, pendingCut = 0, profit = 0, remainder = 0;
		while (start < arr.size()) {
			pendingCut = arr.get(start) / cuts;
			if (remainder == 0) {
				if (pendingCut > 1) {
					profit += pendingCut;
					temp = arr.get(start) % cuts;
					if (temp > 0)
						profit += getValue(temp);
					remainder = cuts - temp;
				} else {
					profit += getValue(arr.get(start));
					remainder = cuts - arr.get(start);
				}
			} else {
				profit += getValue(remainder);
				if (pendingCut > 1) {
					profit += pendingCut;
					temp = arr.get(start) % cuts;
					if (temp > 0)
						remainder = temp - remainder;
					if (remainder > 0)
						profit += getValue(remainder);
				} else {
					remainder = arr.get(start) - remainder;
					if (remainder > 0)
						profit += getValue(remainder);
				}
				remainder = 0;
			}
			start++;
		}
		return profit;
	}

	// This is helper method to get cost for each block
	// Time Complexity O(1), Space Complexity O(1)
	private static int getValue(int index) {
		if (index == 1)
			return -1;
		else if (index == 2)
			return 3;
		else
			return 1;
	}

	// This is helper method to get all possible permutation for given array
	// Time Complexity 2^n, Space Complexity O(n^2)
	public static List<List<Integer>> permute(int[] nums) {
		List<List<Integer>> result = new LinkedList<>();
		boolean[] visited = new boolean[nums.length];
		if (nums.length == 0 || nums == null)
			result.add(new LinkedList<>());
		perm(new LinkedList<Integer>(), result, nums, visited);

		return result;
	}

	// This is helper method to get all possible permutation for given array
	// Time Complexity 2^n, Space Complexity O(n^2)
	private static void perm(List<Integer> permutation, List<List<Integer>> result, int[] nums, boolean[] visited) {
		if (nums.length == permutation.size()) {
			result.add(new LinkedList<Integer>(permutation));
			return;
		}
		for (int i = 0; i < nums.length; i++) {
			if (visited[i])
				continue;
			visited[i] = true;
			permutation.add(nums[i]);
			perm(permutation, result, nums, visited);
			permutation.remove(permutation.size() - 1);
			visited[i] = false;
		}
	}

}
