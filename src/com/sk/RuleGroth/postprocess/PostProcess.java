package com.sk.RuleGroth.postprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Hashtable;

public class PostProcess
{
	public static void main(String[] args) throws Exception
	{
		process("rule.temp.txt", "weibo.table", "rules.txt");
	}
	
	public static void process(String ruleTempPath,String tablePath,String rulePath) 
			throws Exception
	{
		HashMap<Integer, String> table=loadTable(tablePath);
		
		File file = new File(ruleTempPath);
		String encoding="utf-8";
		
		// 考虑到编码格式
		InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
		BufferedReader bufferedReader = new BufferedReader(read);
		FileWriter writer = new FileWriter(rulePath, false);
		
		// 判断文件是否存在
		if (file.isFile() && file.exists())
		{
			String lineTxt = null;
			String newLine = "";

			while ((lineTxt = bufferedReader.readLine()) != null)
			{
				String[] rule_sup_conf=lineTxt.trim().split("#");
				String[] antecedent_consequent=rule_sup_conf[0].trim().split("==>");
				String[] consequent_items=antecedent_consequent[1].trim().split(",");
				
				String antececent_touple = table.get(Integer.parseInt(antecedent_consequent[0].trim())).replaceAll(","," ");
				String[] consequent_items_touple=new String[consequent_items.length];
				for(int i=0;i<consequent_items.length;i++)
				{
					consequent_items_touple[i]=table.get(Integer.parseInt(consequent_items[i].trim())).replaceAll(",", " ");
				}
				String newRuleLine="";
				newRuleLine+=antececent_touple+"==>";
				for(int i=0;i<consequent_items_touple.length-1;i++)
				{
					newRuleLine+=consequent_items_touple[i]+",";
				}
				newRuleLine+=consequent_items_touple[consequent_items_touple.length-1];
				newRuleLine+="#"+rule_sup_conf[1];
				newRuleLine+="#"+rule_sup_conf[2];
				
				writer.write(newRuleLine+"\n");
				writer.flush();
			}
			
			writer.close();
			bufferedReader.close();
			read.close();
		}
	}
	
	/**
	 * 加载对照表（num  进程名 文件名 offset）
	 * @param tablePath
	 * @return
	 * @throws Exception
	 */
	public static HashMap<Integer, String> loadTable(String tablePath) 
			throws Exception
	{
		HashMap<Integer, String> table=new HashMap<Integer, String>();
		
		File file = new File(tablePath);
		String encoding="utf-8";
		
		// 考虑到编码格式
		InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
		BufferedReader bufferedReader = new BufferedReader(read);
		
		// 判断文件是否存在
		if (file.isFile() && file.exists())
		{
			String lineTxt = null;
			String newLine = "";

			while ((lineTxt = bufferedReader.readLine()) != null)
			{
				String[] strarr=lineTxt.split(" ");
				int num=Integer.parseInt(strarr[0]);
				table.put(num, strarr[1]);
			}
		}
		return table;
	}
	
}
