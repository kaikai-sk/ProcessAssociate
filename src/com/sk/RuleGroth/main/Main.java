package com.sk.RuleGroth.main;

import java.io.UnsupportedEncodingException;
import java.net.URL;

import com.sk.RuleGroth.postprocess.PostProcess;
import com.sk.RuleGroth.preprocess.ProcessTrace;

import ca.pfv.spmf.algorithms.sequential_rules.rulegrowth.AlgoRULEGROWTH;
import ca.pfv.spmf.test.MainTestRuleGrowth;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		ProcessTrace.preprocess();
		callRuleGrowth("weibo.final.trace", "rules.temp.txt", 0.05, 0.5, 1, 1);
		PostProcess.process("rules.temp.txt", "weibo.table", "rules.txt");
	}
	
	/**
	 * 
	 * @param input the database
	 * @param output  the path for saving the frequent itemsets found
	 *                for instance "output.txt"
	 * @param minsup_absolute the support parameter (a double ratio)
	 *                        for istance 0.75(it means 75 %)
	 * @param minconf the confidence parameter(a double ratio)
	 *                for instance:0.5
	 * @param maxAntecedentSize the max size of Antecedent 
	 * 							for instance:1
	 * @param maxConsequentSize the max size of Consequent
	 *                          recently,no use
	 * @throws Exception
	 */
	public static void callRuleGrowth(String input,String output,double minsup_absolute,
			double minconf,int maxAntecedentSize,int maxConsequentSize) throws Exception
	{
		AlgoRULEGROWTH algo = new AlgoRULEGROWTH();
		
		// This optional parameter allows to specify the maximum number of items in the 
		// left side (antecedent) of rules found:
		algo.setMaxAntecedentSize(maxAntecedentSize);  // optional
		
		algo.runAlgorithm(minsup_absolute, minconf, input, output);
			
		// print statistics
		algo.printStats();
		
//		// This optional parameter allows to specify the maximum number of items in the 
//		// right side (consequent) of rules found:
//		algo.setMaxConsequentSize(2);  // optional
		
		//algo.runAlgorithm(input, output, minsup_relative, minconf);
	}
	
}
