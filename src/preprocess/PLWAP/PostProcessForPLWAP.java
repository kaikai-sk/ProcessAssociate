package preprocess.PLWAP;

import java.io.*;

/**
 * 将普通的csv文件进行转化，以适应PLWAP算法
 */
public class PostProcessForPLWAP
{
    public static void main(String[] args)
    {
        processFile("result_PLWAP.data","result_PLWAP_post.data");
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
    public static void processFile(String filePath,String dstFilePath)
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
                    String[] arrs=lineTxt.split("\t");
                    if(arrs.length==2)
                    {
                        //写文件
                        writer.write(lineTxt+"\n");
                        writer.flush();
                        lineIndex++;
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

}
