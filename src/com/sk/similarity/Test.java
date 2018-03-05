package com.sk.similarity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import cn.bob.similarity.CosineSimilarAlgorithm;
import cn.bob.similarity.Distance;
import cn.bob.similarity.SimilarityBase;
import cn.bob.similarity.SimilarityRate;

public class Test
{
	public static void main(String[] args) throws Exception
	{
		String dirPath="data";
	
		File file = new File(dirPath);
		File[] tempList = file.listFiles();

		SimilarityBase similarityBase = new SimilarityRate();
		
	    for (int i = 0; i < tempList.length; i++) 
	    {
	    	for(int j=0;j<i;j++)
	    	{
	    		String path1=tempList[i].getPath();
	    		String path2=tempList[j].getPath();
	    		
	    		System.out.println(path1+"    "+path2+"    "+
	    				similarityBase.sim(getFileContent(path1),getFileContent(path2)));
	    	}
	    }	    
	}
		
	public static String getFileContent(String path) throws Exception
	{
		File file = new File(path);//定义一个file对象，用来初始化FileReader
	    FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
	    BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
	    StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
	    String s = "";
	    while ((s =bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
	        sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
	        //System.out.println(s);
	    }
	    bReader.close();
	    String str = sb.toString();
	    //System.out.println(str );
	    return str;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
