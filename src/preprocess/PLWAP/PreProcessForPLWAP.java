package preprocess.PLWAP;

import java.io.*;
import java.util.ArrayList;

/**
 * 将普通的文本文件（txt，csv等等吧）进行转化，以适应PLWAP算法
 */
public class PreProcessForPLWAP
{
    public static void main(String[] args)
    {
        //processFile("data/mytrace.txt","dstForPLWAP.data"," -1 ",10);
//    	processFileChangeSeparator("data/mytrace.txt","dstForPLWAP.data");
    	processFileMatrix2Vec("data/mytrace.txt", "data/mytrace1.txt");
    }
    
    public static void processFileMatrix2Vec(String filePath,String dstFilePath)
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
                int lineIndex=0;
                String newLine="";

                while((lineTxt = bufferedReader.readLine()) != null)
                {
                	lineIndex++;
                	
                	String[] strs=lineTxt.split(" ");
                	for(int i=0;i<strs.length;i++)
                	{
                		//写文件
	                    writer.write(strs[i]+"\n");
	                    writer.flush();
                	}
                
                }

                read.close();
                writer.close();
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
    
    public static void processFileChangeSeparator(String filePath,String dstFilePath)
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
                int lineIndex=0;
                String newLine="";

                while((lineTxt = bufferedReader.readLine()) != null)
                {
                	lineIndex++;
                  
                	lineTxt=lineTxt.replaceAll(" ", " -1 ");
                	lineTxt+=" -2\n";
                	
                	//写文件
                    writer.write(lineTxt);
                    writer.flush();
                }

                read.close();
                writer.close();
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
     * 按照C-Miner那篇paper的格式准备数据
     * 将一列数据变成windowSize列的矩阵
     * @param filePath
     * @param dstFilePath
     * @param separator 新文件的每行的分隔符
     * @param windowSize 窗口大小
     */
    public static void processFile(String filePath,String dstFilePath,String separator,
    		int windowSize)
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
                int lineIndex=0;
                String newLine="";

                while((lineTxt = bufferedReader.readLine()) != null)
                {
                	lineIndex++;
                  
                    newLine=newLine+lineTxt+separator;
                    if(lineIndex%windowSize==0)
                    {
                    	//newLine=newLine.trim();
                    	newLine+="-2\n";
                    	//写文件
	                    writer.write(newLine);
	                    writer.flush();
	                    newLine="";
                    }
                }

                read.close();
                writer.close();
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
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     * @param filePath 源文件名
     * @param dstFilePath 目的文件名
     */
    public static void processFileWithIDAndCount(String filePath,String dstFilePath)
    {
        try
        {
            String encoding="GBK";
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
                int lineIndex=0;
                String newLine;

                while((lineTxt = bufferedReader.readLine()) != null)
                {
                    //读文件
                    if(lineIndex==0)
                    {
                        lineIndex++;
                        continue;
                    }
                    lineTxt=lineTxt.replaceAll(",","\t");
                    newLine=""+lineIndex+" "+10+" "+lineTxt+"\n";

                    //写文件
                    writer.write(newLine);
                    writer.flush();
                    lineIndex++;
                }

                read.close();
                writer.close();
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

}
