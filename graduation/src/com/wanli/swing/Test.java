package com.wanli.swing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.TitleEvent;
import org.eclipse.swt.browser.TitleListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import com.wanli.utils.StaticVariable;

public class Test {

    static{
     System.setProperty("org.eclipse.swt.browser.XULRunnerPath", "e:\\xulrunner"); 
    } 
    public static void main(String[] args) {

        Display display = new Display (); 
        final Shell shell = new Shell (display); 
        FillLayout layout = new FillLayout(); 
        shell.setLayout(layout); 

        Browser browser = new Browser(shell, SWT.MOZILLA);  //1
        browser.addTitleListener(new TitleListener(){  //2
            public void changed(TitleEvent event) { 
                shell.setText(event.title); 
            } 
        }); 
        browser.setUrl("www.baidu.com"); //3
        shell.open (); 
        while (!shell.isDisposed ()) { 
            if (!display.readAndDispatch ()) display.sleep (); 
        } 
        display.dispose ();
    }
    
//    boolean OpenTextFile() {
//		StaticVariable.index = 1;
//		// 定义对话框，类型为打开型
//		FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
//		// 设置对话框打开的限定类型
//		dialog.setFilterExtensions(new String[] { " *.txt", "*.doc", "*.docx"});
//		// 打开对话框，并返回打开文件的路径
//		String openFile = dialog.open();
//		if (openFile == null) {
//			return false;
//		}
//		// 打开指定的文件
//		file = new File(openFile);
//		String fileExtension = file.getName().substring(file.getName().indexOf("."));
//		try {
//			//读取扩展名为.doc的word文档
//			HWPFDocument doc = null;
//			//读取扩展名为.docx的word文档
//			XWPFDocument docx = null;
//			XWPFWordExtractor extractor = null;
//			//读取扩展名为.txt的文档
//			StringBuffer sb = null;
//			String fileText = null;
//			BufferedReader reader = null;
//			FileInputStream in = null;
//			if (fileExtension.equals(".doc")) {
//				in = new FileInputStream(file);
//				doc = new HWPFDocument(in);
//				fileText = doc.getDocumentText();
//				StaticVariable.questions = fileText.split(new String("#\\^"));
//				StaticVariable.text.setText(StaticVariable.questions[1]);
//			} else if (fileExtension.equals(".docx")) {
//				in = new FileInputStream(file);
//				docx = new XWPFDocument(in);
//				extractor = new XWPFWordExtractor(docx);
//				fileText = extractor.getText();
//				StaticVariable.questions = fileText.split(new String("#\\^"));
//				StaticVariable.text.setText(StaticVariable.questions[1]);
//			}
//			else {
//				// 读取文件
//				FileReader fileReader = new FileReader(file);
//				// 把字符流的字符读入缓冲区
//				reader = new BufferedReader(fileReader);
//				sb = new StringBuffer();
//				String line = null;
//				while ((line = reader.readLine()) != null) {
//					// 通过 append() 方法实现将字符串添加到字符缓冲区。
//					sb.append(line);
//					sb.append("\r\n");
//				}
//				// 将读取的文件用字符串"#^"分开，因为'^'是转义字符，所以前面要加\\
//				StaticVariable.questions = sb.toString().split(new String("#\\^"));	
//				StaticVariable.text.setText(StaticVariable.questions[1]);
//			}
//			System.out.println(StaticVariable.questions.length);
//			int num = StaticVariable.questions.length - 1;
//			String fileName = file.getName();
//			shell.setText(APPNAME + "-" + file);
//			StaticVariable.tableName = fileName.substring(0, fileName.indexOf("."));
//			dbService.createTable(num, StaticVariable.tableName);
//			
//			//有文件，启用按钮
////			first.setEnabled(true);
////			previous.setEnabled(true);
////			next.setEnabled(true);
////			last.setEnabled(true);
//			if (reader != null) {
//				reader.close();				
//			}
//			if (in != null) {
//				in.close();
//			}
//			return true;
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return false;
//	}
}
