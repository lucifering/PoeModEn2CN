package lucifering.poe.dic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import lucifering.poe.dic.bean.CNENBean;
import lucifering.poe.dic.bean.DoCNENBean;
import lucifering.poe.dic.bean.TmpLinesBean;
import lucifering.poe.dic.utils.FUtil;
import lucifering.poe.dic.utils.StrUtil;

public class DoWorker {

	// 如果没有简体中文词缀 那么是否取繁体中文  未实现
	public static boolean enableTW = false;

	public static void main(String[] args) throws Exception {
		
		Gson gson = new Gson();
		 OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("./poe.dl"),"UTF-8");
		 
	     
		 List<String>  wStrList=new ArrayList<String>();
		List<File> statDescriptionsList = FUtil.getCurDirFileList("./DoStatDescriptions/", ".txt");
		for (File file : statDescriptionsList) {

			List<DoCNENBean>  cnenList=getMod(file);
			for(DoCNENBean bean:cnenList ) {		
				
				wStrList.add("[[{\"type\":0,\"SourString\":\"\",\"regexString\":\""+bean.en+"\",\"formatString\":\""+
				bean.en_formatString+"\"}],[{\"type\":1,\"SourString\":\"\",\"regexString\":\""+
						bean.cn+"\",\"formatString\":\""+bean.cn_formatString+"\"}]]\r\n"
						);
			}
			
			
			// break;
		}
		
		wStrList.sort(new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				// TODO Auto-generated method stub
				return o2.length()-o1.length();
			}
			
		});
		
		for(String str :wStrList) {
			
			out.write(str);
		}
		
		 
		out.close();
		System.out.println("All Done!");

	}

	@SuppressWarnings("unused")
	public static List<DoCNENBean> getMod(File file) throws Exception {
		System.out.println(file.getAbsolutePath());
		Gson gson = new Gson();
		List<DoCNENBean> beanList = new ArrayList<DoCNENBean>();

		InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "unicode");
		BufferedReader br = new BufferedReader(isr);

		String line = null;

		boolean descriptionStart = false;
		boolean enStart = false;
		boolean twStart = false;
		boolean cnStart = false;

		boolean handStart = false;
		int test = 0;
		Map<String, TmpLinesBean> linesListMap = new HashMap<String, TmpLinesBean>();

		String mapKey = "Test";

		while ((line = br.readLine()) != null) {

			line = line.trim();
			if (line.length() == 0) {
				continue;
			}

			if (line.trim().equalsIgnoreCase("description") || line.trim().startsWith("description")
					|| line.trim().contains("handed_description")) {

				if (line.trim().contains("handed_description")) {
					handStart = true;
				}
				descriptionStart = true;
				enStart = false;
				twStart = false;
				cnStart = false;
				test++;

				continue;

			}
			if (descriptionStart && !enStart && !StrUtil.isIn(line)) {

				mapKey = line.trim().replace(" ", "");

				// System.out.println("mapKey="+mapKey);
				continue;
			}

			if (descriptionStart && !enStart && StrUtil.isIn(line) && handStart) {

				continue;
			}

			if (descriptionStart && !enStart && !StrUtil.isIn(line) && handStart) {

				mapKey = line.trim().replace(" ", "");
				handStart = false;
				continue;
			}

			if (descriptionStart && !enStart && StrUtil.isIn(line)) {
				// System.out.println("enStart");
				enStart = true;
				descriptionStart = false;
				continue;

			}
			if (enStart && (line.contains("lang "))) {
				enStart = false;
			}
			if (enStart) {

				TmpLinesBean tbean;
				if (linesListMap.containsKey(mapKey)) {
					tbean = linesListMap.get(mapKey);
					if (tbean.en == null) {
						tbean.en = new ArrayList<String>();
					}
					tbean.en.add(line);

				} else {
					tbean = new TmpLinesBean();
					tbean.en = new ArrayList<String>();
					tbean.en.add(line);

				}
				linesListMap.put(mapKey, tbean);

			}

			if (line.trim().equalsIgnoreCase("lang \"Traditional Chinese\"")) {
				enStart = false;
				twStart = true;
				continue;
			}
			if (twStart && (line.contains("lang ") || line.contains("description")
					|| line.contains("handed_description"))) {
				twStart = false;
			}
			if (twStart && !StrUtil.isIn(line)) {
				TmpLinesBean tbeantw;
				if (linesListMap.containsKey(mapKey)) {
					tbeantw = linesListMap.get(mapKey);

					if (tbeantw.tw == null) {
						tbeantw.tw = new ArrayList<String>();
					}

					tbeantw.tw.add(line);
				} else {
					tbeantw = new TmpLinesBean();
					tbeantw.tw = new ArrayList<String>();
					tbeantw.tw.add(line);

				}
				linesListMap.put(mapKey, tbeantw);

				continue;
			}

			if (line.trim().equalsIgnoreCase("lang \"Simplified Chinese\"")) {
				enStart = false;
				twStart = false;
				cnStart = true;

				continue;
			}
			if (cnStart && (line.contains("lang ") || line.contains("description")
					|| line.contains("handed_description"))) {
				cnStart = false;
			}
			if (cnStart && !StrUtil.isIn(line)) {

				TmpLinesBean tbeancn;
				if (linesListMap.containsKey(mapKey)) {
					tbeancn = linesListMap.get(mapKey);

					if (tbeancn.cn == null) {
						tbeancn.cn = new ArrayList<String>();
					}

					tbeancn.cn.add(line);
				} else {
					tbeancn = new TmpLinesBean();
					tbeancn.cn = new ArrayList<String>();
					tbeancn.cn.add(line);

				}
				linesListMap.put(mapKey, tbeancn);
				continue;
			}
			continue;

		}

		br.close();

		for (Map.Entry<String, TmpLinesBean> entry : linesListMap.entrySet()) {
			// System.out.println("【ok】="+gson.toJson( entry.getValue()));
			List<DoCNENBean>  cnenList=TmpLinesBean2CnEnBean(entry.getKey(), entry.getValue());
			
			
			beanList.addAll( cnenList);
		}

		return beanList;
	}

	// 获取的数据转换为 cn和en
	public static List<DoCNENBean> TmpLinesBean2CnEnBean(String key, TmpLinesBean tmpbean) {
		Gson gson = new Gson();
		List<DoCNENBean> beanList = new ArrayList<DoCNENBean>();

		if (tmpbean.en != null && tmpbean.cn != null) {

			if (tmpbean.en.size() != tmpbean.cn.size()) {

				if (tmpbean.tw != null) {

					if (tmpbean.en.size() != tmpbean.tw.size()) {

					 //todo
					}
				}

			}else {
				
				
				for(int i=0;i<tmpbean.en.size();i++) {
					String stren=tmpbean.en.get(i);
					String strcn=tmpbean.cn.get(i);
					
					if(stren.contains("\"") && strcn.contains("\"")) {
						
						DoCNENBean bean=new DoCNENBean();
						String en=stren.substring(stren.indexOf("\"")+1,stren.lastIndexOf("\""));
						String cn=strcn.substring(strcn.indexOf("\"")+1,strcn.lastIndexOf("\""));
						
					//	en=en.replaceAll("\\\\n", "<br>");
					//	cn=cn.replaceAll("\\\\n", "<br>");
						
						
						bean.en=StrUtil.doEnPre(en,false);
						bean.cn=StrUtil.doEnPre(cn,false);
						bean.cn_formatString=StrUtil.cnPreFormatString(cn,true);
						bean.en_formatString=StrUtil.enPreFormatString(en,true);
						
						
						
						if(en.contains("%1$+d")) {
							bean.en=StrUtil.doEnPre(en,false);
							bean.cn=StrUtil.doEnPre(cn,false);
							bean.cn_formatString=StrUtil.cnPreFormatString( cn,true);
							bean.en_formatString=StrUtil.enPreFormatString(en,true);
							
							
							if(bean.en.contains("%1$+d")) {
								
								DoCNENBean bean2=new DoCNENBean();
								bean2.cn=bean.cn;
								bean2.en=bean.en;
								
								bean2.cn_formatString=StrUtil.cnPreFormatString( cn,false);
								bean2.en_formatString=StrUtil.enPreFormatString(en,false);
								
								while((bean.en.contains("%1$+d"))) {
									bean.en=bean.en.replace("%1$+d" , "\\\\+(\\\\d+|(\\\\d+\\\\.\\\\d+))");
									 
									 
								}
								while((bean.cn.contains("%1$+d"))) {
									bean.cn=bean.cn.replace("%1$+d" , "\\\\+(\\\\d+|(\\\\d+\\\\.\\\\d+))");
								}
								
								while((bean2.en.contains("%1$+d"))) {
									bean2.en=bean2.en.replace("%1$+d" , "\\\\-(\\\\d+|(\\\\d+\\\\.\\\\d+))");
								}
								while((bean2.cn.contains("%1$+d"))) {
									bean2.cn=bean2.cn.replace("%1$+d" , "\\\\-(\\\\d+|(\\\\d+\\\\.\\\\d+))");
								}
								beanList.add(bean2);
								
								
							 }
						
							
						}else {
							bean.en=StrUtil.doEnPre(en,false);
							bean.cn=StrUtil.doEnPre(cn,false);
						}
						
						
						if(en.isEmpty()) {
							System.out.println("【Error】=" + gson.toJson(tmpbean));
						}else {
							 
						//	System.out.println("【ok】=" + en);
						//	System.out.println("【ok】=" + cn);
							
						}
						
						beanList.add(bean);
						
					}else {
						System.out.println("【Error】=" + gson.toJson(tmpbean));
						
					}
					
					
				}
				
			}

		} else {
			System.out.println("【Error!】=" + gson.toJson(tmpbean));
		}

		return beanList;
	}
	
	
	

}
