package cn.gary.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * 版本所有，有问题请拨打电话13522427089
 *
 * @author GarySU
 * http://www.garysu.cn
 */
public class IOUtils {

    public static String readText(String path) {

        File file = new File(path);
        StringBuffer sb = new StringBuffer();


        FileReader reader = null;
        try {
            reader = new FileReader(path);
            char[] buffer = new char[2048];
            int position = -1;
            while (-1 != (position = reader.read(buffer))) {
                sb.append(buffer, 0, position);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static byte[] readBinary(File path) {
        return null;
    }


    public static Boolean writeText(String html, String path) {

        File file = new File(path);
        try {
            FileWriter writer = new FileWriter(file);
            writer.append(html);
            //writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static Boolean writeBinary(byte[] buffer, String fullFileName) {
    	try {
			FileOutputStream outputStream = new FileOutputStream(new File(fullFileName));
			outputStream.write(buffer);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }

    public static Boolean mkdir(String path) {
        File file = new File(path);
        if(!file.exists()) {
            file.mkdirs();
        }
        return true;
    }

    public  static void listDirectory(File dir) throws Exception{
        if (!dir.exists()) {
            throw new Exception(dir+" is not exist");
        }
        if (!dir.isDirectory()) {
            throw new Exception(dir+" is not a directory");
        }
        File[] files=dir.listFiles();
        for(File file: files){
            if (file.isDirectory()) {
                listDirectory(file);
            }
            else{
                System.out.println(file);
            }
        }
    }
    
    public static void delete(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}

		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				file.delete();
				return;
			}

			for (int i = 0; i < childFiles.length; i++) {
				delete(childFiles[i]);
			}
			file.delete();
		}
	}
	
	public static void delete(String pathname) {
		File path = new File(pathname);
		delete(path);
	}

}
