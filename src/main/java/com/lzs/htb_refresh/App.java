package com.lzs.htb_refresh;

import java.util.Random;
import java.util.Scanner;

import com.lzs.tools.http.HttpUtil;

/**
 * Hello world!
 *
 */
public class App 
{
	private static String url = "http://www.haitaobei.com/ajax/Click";
	
    public static void main( String[] args ) throws InterruptedException
    {
        System.out.println( "Hello HaiTaoBei!请按照提示进行操作。为了确保安全，程序设置了最多刷200次后自动停止。若需要刷多于200次，请再次运行本程序。" );
        Integer id = null;
		while(id == null){
			try{
				System.out.println( "请输入页面ID（URL最后的那串数字）：" );
		        Scanner sc = new Scanner(System.in);
		        id = sc.nextInt();
			}catch (Exception e){
				System.out.println( "输入数字啊！" );
			}
		}
		Integer fre = null;
		while(fre == null){
			try{
				System.out.println( "请输入频率（单位秒）" );
		        Scanner sc = new Scanner(System.in);
		        fre = sc.nextInt();
			}catch (Exception e){
				System.out.println( "输入数字啊！" );
			}
		}
		Integer max = null;
		while(max == null){
			try{
				System.out.println( "为保证安全请输入最多刷新次数（1-500次之间）" );
		        Scanner sc = new Scanner(System.in);
		        max = sc.nextInt();
			}catch (Exception e){
				System.out.println( "输入数字啊！" );
			}
		}
		if(max > 500){
			max = 500;
		}
		int i=1;
		float factor = 0.5f;// 随机因子
		if(factor > 0){
			float radius = fre*factor;// 随机数值范围半径
			int base = (int)(fre - radius);//基点
			int rang = (int)radius*2;
			Random random=new java.util.Random(System.currentTimeMillis());// 定义随机类
			
			int sleep = 1000;
			
	        for(; i<=max ; i++){
	        	String s = HttpUtil.request(url, HttpUtil.METHOD_POST, "application/x-www-form-urlencoded", "ObjectId=" + id + "&ObjectName=find");
	        	System.out.println(s);
	        	System.out.println("成功刷了" + i + "次(Ctrl+c 或者直接关闭 停止运行)");
	        	int r = rang<0?0:random.nextInt(rang);
	        	sleep = 1000*(base + r);
	        	System.out.println("下一次刷新在"+(base+r) + "秒之后");
	        	Thread.currentThread().sleep(sleep);// 返回[0,10)集合中的整数，注意不包括10);
	        }
		} else {
			int sleep = 1000*fre;
			
			for(; i<=max ; i++){
	        	String s = HttpUtil.request(url, HttpUtil.METHOD_POST, "application/x-www-form-urlencoded", "ObjectId=" + id + "&ObjectName=find");
	        	System.out.println(s);
	        	System.out.println("成功刷了" + i + "次(Ctrl+c 或者直接关闭 停止运行)");
	        	System.out.println(sleep);
	        	Thread.currentThread().sleep(sleep);// 返回[0,10)集合中的整数，注意不包括10);
	        }
		}
        
        System.out.println("程序运行结束，总共刷新了" + (i-1) + "次");
    }
}
