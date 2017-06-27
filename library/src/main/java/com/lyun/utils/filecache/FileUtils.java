package com.lyun.utils.filecache;

/**
 * @author Gordon
 * @since 2016/3/10
 * do()
 */

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileUtils {

    /*
     * 读raw中的文件 id： 资源编号 R.raw.filename;charset :编码，要和文件的编码一样，不然会乱码
     */
    public static String readRawFile(Context context, int id, String charset) throws IOException {
        String res = "";
        // 得到资源中的Raw数据流
        InputStream in = context.getResources().openRawResource(id);
        // 得到数据的大小
        int length = in.available();
        byte[] buffer = new byte[length];
        // 读取数据
        in.read(buffer);
        // 依test.txt的编码类型选择合适的编码，如果不调整会乱码
        res = new String(buffer, charset);
        // 关闭
        in.close();
        return res;

    }

    /*
     * 读assert中的文件 charset :编码，要和文件的编码一样，不然会乱码，一般“UTF-8”
     */
    public static String readAssetFile(Context context, String fileName, String charset) throws IOException {
        // String fileName = "test.txt"; //文件名字
        String res = "";
        // 得到资源中的asset数据流
        InputStream in = context.getResources().getAssets().open(fileName);
        int length = in.available();
        byte[] buffer = new byte[length];
        in.read(buffer);
        in.close();
        res = new String(buffer, charset);
        return res;
    }

    /*
     * 写文件到“/data/data/<应用程序名>目录 ”下，追加内容到file中
     */
    public static void writeFile(Context context, String fileName, String writestr) throws IOException {
        FileOutputStream fout = context.openFileOutput(fileName, Context.MODE_APPEND);
        byte[] bytes = writestr.getBytes();
        fout.write(bytes);
        fout.close();
    }

    /*
     * 写文件到“/data/data/<应用程序名>目录 ”下
     */
    public static void writeFile(Context context, String fileName, Object obj, int mode) throws IOException {
        if (context == null)
            return;
        FileOutputStream os = context.openFileOutput(fileName, mode);
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(obj);
        oos.close();
        os.close();
    }

    /*
     * 读“/data/data/<应用程序名>目录 ”下文件 charset :编码，要和文件的编码一样，不然会乱码，一般“UTF-8”
     */
    public static String readFile(Context context, String fileName, String charset) throws IOException {
        String res = "";
        FileInputStream fin = context.openFileInput(fileName);
        int length = fin.available();
        byte[] buffer = new byte[length];
        fin.read(buffer);
        res = new String(buffer, charset);
        fin.close();

        return res;

    }

    /*
     * 读“/data/data/<应用程序名>目录 ”下文件 charset :编码，要和文件的编码一样，不然会乱码，一般“UTF-8”
     */
    public static Object readFile(Context context, String fileName) throws IOException {
        if (context == null)
            return null;
        FileInputStream fin = context.openFileInput(fileName);
        ObjectInputStream ois = new ObjectInputStream(fin);
        try {
            Object obj = ois.readObject();
            fin.close();
            return obj;
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fin.close();
            return null;
        }
    }

    /*
     * 往SD中的文件追加内容
     */
    public static void writeSDFile(String fileName, String write_str) throws IOException {
        // 定义File类对象
        File file = new File(fileName);
        // 如果父文件夹不存在，则创建父文件夹
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fout = new FileOutputStream(file, true);
        byte[] bytes = write_str.getBytes();
        fout.write(bytes);
        fout.close();
    }

    /**
     * 往SD中的文件追加内容
     *
     * @param fileName
     * @param data
     * @throws IOException
     */
    public static void writeSDFile(String fileName, byte[] data) throws IOException {

        // 定义File类对象
        File file = new File(fileName);
        // 如果父文件夹不存在，则创建父文件夹
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();
    }

    /*
     * 读sd卡中的文件内容，
     */
    public static String readSDFile(String fileName, String charset) throws IOException {
        String res = "";
        FileInputStream fin = new FileInputStream(fileName);
        int length = fin.available();
        byte[] buffer = new byte[length];
        fin.read(buffer);
        res = new String(buffer, charset);
        fin.close();
        return res;
    }

    /*
     * 读sd卡中的文件内容（用File的方式）
     */
    public static String readSDFile2(String fileName, String charset) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        int length = fis.available();
        byte[] buffer = new byte[length];
        fis.read(buffer);
        String res = new String(buffer, charset);
        fis.close();
        return res;
    }

    /*
     * 往SD中的文件追加内容 （用File的方式）
     */
    public static void writeSDFile2(String fileName, String write_str) throws IOException {
        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(file, true);
        byte[] bytes = write_str.getBytes();
        fos.write(bytes);
        fos.close();
    }

    /*
     * 获得SD卡上文件的长度
     */
    public static long getSDFileLength(String fileName) throws IOException {
        File file = new File(fileName);
        // FileInputStream fis = new FileInputStream(file);
        // int fileLen = fis.available(); //这就是文件大小
        // fis.close();
        return file.length();
    }

    /*
     * 获得“/data/data/<应用程序名>目录 ”下文件的长度
     */
    public static long getFileLength(Context context, String fileName) throws IOException {
        File file = context.getFilesDir();
        String path = file.getAbsolutePath();
        File file2 = new File(path + File.separator + fileName);
        int fileLen = 0;
        if (file2.exists()) {
            FileInputStream fis = context.openFileInput(fileName);
            fileLen = fis.available(); // 这就是文件大小
            fis.close();
        }

        return fileLen;
    }

    /*
     * 重命名“/data/data/<应用程序名>目录 ”下文件的名称
     */
    public static void reFileName(Context context, String oldName, String newName) {
        File file = context.getFilesDir();
        String path = file.getAbsolutePath();
        File oldfile = new File(path + "/" + oldName);
        File newfile = new File(path + "/" + newName);
        if (oldfile.exists()) {
            oldfile.renameTo(newfile);
        }

    }

    /*
     * 重命名sd卡上文件
     */
    public static void reSDFileName(String oldname, String newName) {
        File oldfile = new File(oldname);
        File newfile = new File(newName);
        oldfile.renameTo(newfile);
    }

    /*
     * 读“/data/data/<应用程序名>目录 ”下文件files文件夹下的所有文件
     */
    public static File[] getFileLists(Context context) {
        File file = context.getFilesDir();
        File[] array = file.listFiles();
        /*
         * String[] files = new String[array.length]; for(int i = 0 ; i <
		 * array.length ; i ++){ files[i] =array[i].getName(); }
		 */
        return array;

    }

    public static void delFile(Context context, String fileName) {
        if (context != null)
            context.deleteFile(fileName);
    }

    /**
     * 获得sd卡上的文件数据
     *
     * @param filePath 文件路径
     * @return
     */
    public static byte[] getSDFileData(String filePath) {
        try {
            FileInputStream fin = new FileInputStream(filePath);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            fin.close();
            return buffer;
        } catch (Exception ex) {

        }
        return null;
    }

	/*
     * String Name = File.getName(); //获得文件或文件夹的名称： String parentPath =
	 * File.getParent(); //获得文件或文件夹的父目录 String path =
	 * File.getAbsoultePath();//绝对路经 String path = File.getPath();//相对路经
	 * File.createNewFile();//建立文件 File.mkDir(); //建立文件夹 File.isDirectory();
	 * //判断是文件或文件夹 File[] files = File.listFiles(); //列出文件夹下的所有文件和文件夹名
	 * File.renameTo(dest); //修改文件夹和文件名 File.delete(); //删除文件夹或文件
	 */

}
