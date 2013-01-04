import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 自然语言处理 Pro2
 * 基于词典和规则实现中文自动分词
 * 主要利用FMM和RMM算法
 * */
public class main {
	public static ArrayList<String> dict;
	
	/**
	 * 初试化词库
	 * */
	public static void Ini(){
		dict=new ArrayList<String>();
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("ce.txt"),"UTF-8"));
			while(br.ready()){
				String line=br.readLine().split(",")[0];
				dict.add(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//利用了FMM算法，存在缺陷
	public static ArrayList<String> getSeperate(String phase){
		ArrayList<String> result=new ArrayList<String>();
		String temp;
		while(phase.length()>1){
			if(phase.length()>=5){
			temp=phase.substring(0,5);
			}else
				temp=phase;
			while(dict.indexOf(temp)==-1 && temp.length()>=2){
				temp=temp.substring(0,temp.length()-1);
			}
			result.add(temp);
			phase=phase.substring(temp.length());
		}
		result.add(phase);
		return result;
	}
	
	//利用RMM算法
	public static ArrayList<String> getSepRMM(String phase){
		ArrayList<String> result=new ArrayList<String>();
		String temp;
		while(phase.length()>1){
			if(phase.length()>=5){
				temp=phase.substring(phase.length()-5,phase.length());
			}else
				temp=phase;
			while(dict.indexOf(temp)==-1 && temp.length()>=2){
				temp=temp.substring(1,temp.length());
			}
			result.add(0,temp);
			phase=phase.substring(0,phase.length()-temp.length());
		}
		result.add(0,phase);
		return result;
	}
	
	public static boolean compare(ArrayList<String> a,ArrayList<String> b){
		if(a.size()!=b.size())
			return false;
		for(int i=0;i<a.size();i++){
			if(!a.get(i).equals(b.get(i)))
				return false;
		}
		return true;
	}
	
	//同时利用FMM和RMM，比较
	//可以消岐
	public static ArrayList<String> Merge(ArrayList<String> fmm_arr,ArrayList<String> rmm_arr){
		ArrayList<String> final_result=new ArrayList<String>();
		if(compare(fmm_arr,rmm_arr))
			return fmm_arr;
		else{
			final_result=fmm_arr.size()>rmm_arr.size()?rmm_arr:fmm_arr;
		}
		return final_result;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException{
		System.out.println("词库初始化..");
		Ini();
		System.out.println("初试化完毕..."+dict.size());
		System.out.println("输入短语:");
		Scanner s=new Scanner(System.in,"UTF-8");
		while(s.hasNext()){
			String phase=s.nextLine();
			ArrayList<String> fmm_result=getSeperate(phase);
			ArrayList<String> rmm_result=getSepRMM(phase);
			ArrayList<String> final_result=Merge(fmm_result,rmm_result);
			System.out.println("分词为：");
			for(String str:final_result){
				System.out.print(str+"/");
			}
			System.out.println("\n");
		}
	}
}
