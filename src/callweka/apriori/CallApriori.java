package callweka.apriori;

import weka.associations.Apriori;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.unsupervised.attribute.FirstOrder;

import java.io.*;
import java.util.ArrayList;

public class CallApriori
{
    public static void main(String[] args) throws Exception
    {
        String fileName="ts0-input-t.txt";
        String dstFilePath=fileName+"_sk.csv";
        //preprocess the file
        processFile(fileName,dstFilePath,10,0);
        //call Apriori
//        Instances data= ConverterUtils.DataSource.read(dstFilePath);
//        Apriori apriori=new Apriori();
//        apriori.buildAssociations( data );
//        System.out.println(apriori.toString());
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
     * @param windowSize 窗口大小
     * @param colIndex 要处理的列的编号
     */
    public static void processFile(String filePath,String dstFilePath,int windowSize,int colIndex)
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
                ArrayList<String> aRecorde=new ArrayList<>(windowSize);
                while((lineTxt = bufferedReader.readLine()) != null)
                {
                    if(lineIndex==0)
                    {
                        lineIndex++;
                        //写属性名
                        String atts="";
                        int i;
                        for(i=0;i<windowSize-1;i++)
                        {
                            atts+="att"+i+",";
                        }
                        atts+="att"+i+"\n";
                        writer.write(atts);

                        continue;
                    }
                    String[] temp=lineTxt.split(",");
                    //aRecorde[lineIndex%windowSize]=temp[colIndex];
                    aRecorde.add(temp[colIndex]);
                    if(lineIndex%windowSize==0)
                    {
                        String writeLine="";
                        for(int i=0;i<windowSize-1;i++)
                        {
                            writeLine+=aRecorde.get(i)+",";
                        }
                        writeLine+=aRecorde.get(windowSize-1)+"\n";
                        writer.write(writeLine);
                        writer.flush();
                        aRecorde.clear();
                    }

                    lineIndex++;
                }

                if(aRecorde.size()>0)
                {
                    String writeLine="";
                    for(int i=0;i<windowSize-1;i++)
                    {
                        if(i<aRecorde.size())
                        {
                            writeLine+=aRecorde.get(i)+",";
                        }
                        else
                        {
                            writeLine+=""+",";
                        }
                    }
                    if(aRecorde.size()==windowSize)
                    {
                        writeLine+=aRecorde.get(windowSize-1)+"\n";
                    }
                    else
                    {
                        writeLine+=""+"\n";
                    }
                    writer.write(writeLine);
                    writer.flush();
                    aRecorde.clear();
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
     * 向文件中追加写一行文件
     * @param fileName 文件名
     * @param content 一行文本
     */
    public static void writeALineToFile(String fileName,String content)
    {
        try
        {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.flush();
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


//    public static Instances divideWindow(Instances instances,int windowSize)
//    {
//        ArrayList<Attribute> atts=new ArrayList<>(windowSize);
//        for(int i=0;i<windowSize;i++)
//        {
//            atts.add(new Attribute("att"+(i+1),(ArrayList<String>)null));
//        }
//        Instances dataRes=new Instances("MyRelation",atts,0);
//
//        double[] vals=new double[windowSize];
//
//        int attIndex=0;
//        for(int i=1;i<=instances.size();i++)
//        {
//            Instance inst=instances.get(i-1);
//            double d=inst.value(0);
//            vals[(i-1)%windowSize]=dataRes.attribute(attIndex++).addStringValue(d);
//            if(i%windowSize==0)
//            {
//                dataRes.add(new DenseInstance(1.0,vals));
//                attIndex=0;
//            }
//        }
//
//        return dataRes;
//    }

}
