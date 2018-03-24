package preprocess.PLWAP;

import java.awt.List;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

public class ProduceTrace
{
	public static void main(String[] args) throws Exception
	{
		String filePath="data/mytrace.txt";
		int windowSize=10;
		int lineNum=100;
		generateTrace(filePath,windowSize,lineNum);
	}

	private static void generateTrace(String filePath, int windowSize, int lineNum) throws Exception
	{
		  // 打开一个写文件器，不追加写
        FileWriter writer = new FileWriter(filePath, false);
        int blockNum=lineNum/100;
        for(int i=0;i<blockNum;i++)
        {
        	ArrayList<ArrayList<String>> block=new ArrayList<ArrayList<String>>();
        	
        	ArrayList<Integer> lineIndex60Percentile=new ArrayList<Integer>();
    		ArrayList<Integer> lineIndex50Percentile=new ArrayList<Integer>();
    		ArrayList<Integer> lineIndex40Percentile=new ArrayList<Integer>();
    		genknuth(60,100,lineIndex60Percentile);
    		genknuth(50,100,lineIndex50Percentile);
    		genknuth(40,100,lineIndex40Percentile);
    		
    		int lineIndex=0;
        	for (int j=0;j<100;j++)
        	{
        		
        		String[] aLine=new String[windowSize];
        		
        		int[] orderInline=random_sort(new Integer[]{1,2,3,4,5});
        		
        		for(int k=0;k<5;k++)
        		{
        			switch(orderInline[k])
        			{
        			//all
        			case 1:
        				aLine[2*k]="6969";
        				aLine[2*k+1]="7979";
        				break;
        			//80 percentile
        			case 2:
        				if (lineIndex60Percentile.contains(lineIndex))
        				{
        					aLine[2*k]="1111";
            				aLine[2*k+1]="2222";
        				}
        				else
        				{
        					aLine[2*k]=randomIntInRange(10000, 100000)+"";
            				aLine[2*k+1]=randomIntInRange(10000, 100000)+"";	
						}
        				break;
        			//70 percentile
        			case 3:
        				if (lineIndex50Percentile.contains(lineIndex))
        				{
        					aLine[2*k]="3333";
            				aLine[2*k+1]="4444";
        				}
        				else
        				{
        					aLine[2*k]=randomIntInRange(10000, 100000)+"";
            				aLine[2*k+1]=randomIntInRange(10000, 100000)+"";	
						}
        				break;
        			//60 percentile
        			case 4:
        				if (lineIndex40Percentile.contains(lineIndex))
        				{
        					aLine[2*k]="5555";
            				aLine[2*k+1]="6666";
        				}
        				else
        				{
        					aLine[2*k]=randomIntInRange(10000, 100000)+"";
            				aLine[2*k+1]=randomIntInRange(10000, 100000)+"";	
						}
        				break;
        			case 5:
        				aLine[2*k]=randomIntInRange(10000, 100000)+"";
        				aLine[2*k+1]=randomIntInRange(10000, 100000)+"";
        				break;
        			}
        		}
        		
        		writer.write(strArray2String(aLine));
        		writer.flush();
        		
        		lineIndex++;
        		
        	}
        	
        }
	}
	
	private static String strArray2String(String[] aLine)
	{
		String tempLine="";
		for(int i=0;i<aLine.length;i++)
		{
			tempLine+=aLine[i]+" ";
		}
		tempLine=tempLine.trim();
		tempLine+="\n";
		return tempLine;
	}

	public static int randomIntInRange(int min,int max)
	{
        Random random = new Random();

        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
	}
	
	// 将数组中的数放到一个容器中，根据容器的长度随机产生一个数，取出该数，
	// 存到另一个容器中，且删除原来容器的这个数，此时原来容器的长度会减1，
	// 则产生随机数的范围也会减一，如下：
	// int tmp = new Random().nextInt(len - i);
	public static int[] random_sort(Integer[] arr)
	{
		// 将此随机数对应的list中的数，存到结果的对应位置
		// resultList[i] = lt.get(tmp);
		LinkedList<Integer> lt = new LinkedList<Integer>(Arrays.asList(arr));

//		System.out.print("随机排序前：");
//		for (int t : lt)
//		{
//			System.out.print(t + " ");
//		}
//		System.out.println();
		
		int len = lt.size();
		// 存放随机排序的结果
		int[] resultList = new int[len];
		Random rand = new Random();
		for (int i = 0; i < len; i++)
		{
			// 产生一个随机数
			int tmp = rand.nextInt(len - i);
			// 将此随机数对应的list中的数，存到结果的对应位置
			resultList[i] = lt.get(tmp);
			// 删除已挑选出的元素
			lt.remove(tmp);
		}
		
		
//		System.out.print("随机排序后：");
//
//		// 展现结果
//		for (int t : resultList)
//		{
//			System.out.print(t + " ");
//		}
		return resultList;
	}
	
	
	/**
	 * 等概率无重复的从n个数中选取m个数
	 * @param m
	 * @param n
	 * @param list
	 */
	public static void  genknuth(int m,int n,ArrayList<Integer> list)  
	{  
	    int i;  
	    for(i=0;i<n;i++)  
	    {
	    	if((int)(Math.random())%(n -i) < m) 
	        {  
	        	list.add(i);
	        	m--;  
	        }  
	    }
	}  
}
