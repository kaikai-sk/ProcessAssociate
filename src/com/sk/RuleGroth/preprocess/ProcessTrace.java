package com.sk.RuleGroth.preprocess;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ProcessTrace
{
	public static void main(String[] args) throws Exception
	{
		String dstFilePath="weibo.trace";
		String tablePath="weibo.table";
		processTrace("C:\\Users\\shankai\\Desktop\\单凯",dstFilePath,tablePath);
		processTraceForRuleGrowth("weibo1.trace","weibo.trace",10);
	}
	
	public static void preprocess() throws Exception
	{
		String dstFilePath="weibo.trace";
		String tablePath="weibo.table";
		processTrace("C:\\Users\\shankai\\Desktop\\单凯",dstFilePath,tablePath);
		processTraceForRuleGrowth("weibo.trace","weibo.final.trace",10);
	}
	
	
	/**
	 * 将一列page格式的trace转换成可供RuleGrowth算法使用的格式
	 * @param filePath
	 * @param dstFilePath
	 */
	public static void processTraceForRuleGrowth(String filePath,String dstFilePath,int windowSize)
	{
		try
        {
            String encoding="UTF-8";
            File file=new File(filePath);
            //判断文件是否存在
            if(file.isFile() && file.exists())
            {
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);

                // 打开一个写文件器，不追加写
                FileWriter writer = new FileWriter(dstFilePath, false);

                String lineTxt = null;
                String newLine="";
                int lineIndex=0;
                ArrayList<String> temp=new ArrayList<String>();
                while((lineTxt = bufferedReader.readLine()) != null)
                {
                	temp.add(lineTxt);
                	lineIndex++;
                	if(lineIndex==windowSize)
                	{
                		String aline="";
                		for(int i=0;i<temp.size()-1;i++)
                		{
                			aline+=temp.get(i)+" -1 ";
                		}
                		aline+=temp.get(temp.size()-1)+" -2";
                		
                		writer.write(aline+="\n");
                		writer.flush();
                		
                		lineIndex=0;
                		temp.clear();
                	}
                }
            }
            else
            {
                System.out.println("找不到指定的文件");
            }
        }
        catch (Exception e)
        {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
	}
	
	/**
	 * 将printk产生的trace解析并转换成一列page（相当于page）
	 * 
	 * @param filePath
	 * @param dstFilePath
	 * @param tablePath
	 * @throws Exception 
	 * @throws  
	 */
	public static void processTrace(String dirPath, String dstFilePath, String tablePath) throws Exception
	{
		ArrayList<String> filePaths = (ArrayList<String>) getAllFile(dirPath, false);

		// 打开一个写文件器，不追加写
		FileWriter writer = new FileWriter(dstFilePath, false);
		FileWriter tableWriter = new FileWriter(tablePath, false);
		Map<Integer, ArrayList<String>> map = new HashMap<Integer, ArrayList<String>>();
		
		for (String filePath : filePaths)
		{
			if (filePath.contains(".trace"))
			{
				
				File file = new File(filePath);
				// 判断文件是否存在
				if (file.isFile() && file.exists())
				{
					/**
					 * 处理一个文件
					 */
					processAFile(file, writer, tableWriter, (HashMap<Integer, ArrayList<String>>)map);
				}
				else
				{
					System.out.println("找不到指定的文件");
				}
			}
		}
		
		Iterator iter = map.entrySet().iterator();
		int index = 1;
		while (iter.hasNext())
		{
			Map.Entry entry = (Map.Entry) iter.next();
			ArrayList<String> tempSecond = (ArrayList<String>) entry.getValue();
			if (tempSecond.size() > 1)
			{
				System.out.println(index + " " + entry.getKey() + "," + tempSecond.toString());
				index++;
			}
		}

		writer.close();
		tableWriter.close();
	}
	
	/**
	 * 为processTrace函数服务，只是处理一个文件
	 * @param file
	 * @param writer
	 * @param tableWriter
	 * @param map
	 * @throws Exception
	 */
	public static void processAFile(File file,FileWriter writer,FileWriter tableWriter,
			HashMap<Integer, ArrayList<String>>map) throws Exception
	{
		String encoding = "UTF-8";
		InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);

		String lineTxt = null;
		String newLine = "";

		while ((lineTxt = bufferedReader.readLine()) != null)
		{
			if (lineTxt.contains("GenericRead") || lineTxt.contains("FileFault"))
			{
				newLine = lineTxt.split(" ")[2];
				newLine = newLine.replaceAll(":", ",");
				String[] temp = newLine.split(",");
				if (temp.length == 5 && isStr2Num(temp[3]))
				{
					String myLine = temp[1] + temp[2] + temp[4];
					int num = getBKDRHashCode(myLine, 11);

					if (map.containsKey(num))
					{
						ArrayList<String> second = map.get(num);
						if (second.contains(myLine))
						{

						}
						else
						{
							second.add(myLine);
						}
					}
					else
					{
						ArrayList<String> temp1 = new ArrayList<String>();
						temp1.add(myLine);
						map.put(num, temp1);
					}

					tableWriter.write(num + " " + temp[1]+","+temp[2]+","+temp[3] + "\n");
					// tableWriter.write(num+","+temp[1]+","+temp[2]+","+temp[4]+"\n");
					tableWriter.flush();

					writer.write(num + "\n");
					writer.flush();
				}
			}
		}
		
		bufferedReader.close();
		read.close();
	}
	
	 /**
     * 获取路径下的所有文件/文件夹
     * @param directoryPath 需要遍历的文件夹路径
     * @param isAddDirectory 是否将子文件夹的路径也添加到list集合中
     * @return
     */
	public static List<String> getAllFile(String directoryPath, boolean isAddDirectory)
	{
		List<String> list = new ArrayList<String>();
		File baseFile = new File(directoryPath);
		if (baseFile.isFile() || !baseFile.exists())
		{
			return list;
		}
		File[] files = baseFile.listFiles();
		for (File file : files)
		{
			if (file.isDirectory())
			{
				if (isAddDirectory)
				{
					list.add(file.getAbsolutePath());
				}
				list.addAll(getAllFile(file.getAbsolutePath(), isAddDirectory));
			}
			else
			{
				list.add(file.getAbsolutePath());
			}
		}
		return list;
	}
	
	/**
	 * 对一个字符串进行hash，得到其hash码（正整数）
	 * 
	 * @param str
	 * @param seed
	 * @return
	 */
	public static int getBKDRHashCode(String str,int seed)
	{
	    int hash = 1;
	    for(int i = 0;i!= str.length();++i)
	    {
	        hash =  seed * hash + str.charAt(i);
	    }
//	    return hash;
	    return hash & 0x7FFFFFFF;
	}
	
	/** 
	* 查看一个字符串是否可以转换为数字 
	* @param str 字符串 
	* @return true 可以; false 不可以 
	*/  
	public static boolean isStr2Num(String str) 
	{   
		try 
		{  
		    Integer.parseInt(str);  
		    return true;  
		}
		catch (NumberFormatException e) 
		{  
		    return false;  
		}  
	}  
}
