package preprocess.PLWAP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

class Range
{
	int start;
	int end;
	
	public Range(int start,int end)
	{
		this.start=start;
		this.end=end;
	}
}

public class DivideFile
{
	public static int getLineNum(String fileName) throws Exception
	{

		 String encoding="GBK";
		 File file=new File(fileName);
         //判断文件是否存在
         if(file.isFile() && file.exists())
         {
             InputStreamReader read = new InputStreamReader(
                     new FileInputStream(file),encoding);//考虑到编码格式
             BufferedReader bufferedReader = new BufferedReader(read);

        

             String lineTxt = null;
             int lineIndex=0;
           
             while((lineTxt = bufferedReader.readLine()) != null)
             {
                 lineIndex++;
             }
             return lineIndex;
         }
        return -1;
	}
	
	public static void divideFile(String fileName,int rangeBig) throws Exception
	{
		int lineNum=getLineNum(fileName);
		if(lineNum==-1)
			return ;
		Range[] ranges=new Range[3];
		ranges[0]=new Range(0, rangeBig-1);
		ranges[2]=new Range(lineNum-rangeBig, lineNum-1);
		int center=(1+rangeBig)/2;
		int start=center-rangeBig/2;
		int end=start+(rangeBig-rangeBig/2);
		ranges[1]=new Range(start, end);
		
		  int lineIndex=0;
          String lineTxt;
          
          String encoding="GBK";
          InputStreamReader read = new InputStreamReader(
                  new FileInputStream(fileName),encoding);//考虑到编码格式
          BufferedReader bufferedReader = new BufferedReader(read);
          
          while((lineTxt = bufferedReader.readLine()) != null)
          {
        	  int fileIndex=1;
        	
        	  
        	  boolean flag=false;
        	  
        	  if(lineIndex == ranges[0].start || lineIndex == ranges[1].start
        			  || lineIndex == ranges[2].start)
        	  {
        		  flag=true;
        	  }
        	  
        	  FileWriter writer = null;
              if(flag==true)
              {
            	  String dstFileName="ts0_"+fileIndex+".txt";
            	  // 打开一个写文件器，不追加写
                  writer = new FileWriter(dstFileName, false);
            	  writer.write(lineTxt);
              }
              
              if(lineIndex == ranges[0].end || lineIndex == ranges[1].end
        			  || lineIndex == ranges[2].end)
        	  {
            	  if(flag==true)
                  {
            		  writer.close();
                  }
        		  flag=false;
        	  }
              
              lineIndex++;
              System.out.println(lineIndex);
          }
		
	}
	
	public static void main(String[] args) throws Exception
	{
		String fileName="ts0-input-t.txt";
		divideFile(fileName, 10000);
	}
}
