package com.rubin.rpan.util;

import org.apache.commons.lang3.StringUtils;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.*;
import java.util.Date;

/**
 * 文件工具类
 * Created by RubinChu on 2021/1/22 下午 3:07
 */
public class FileUtil {

    private static final String KB_STR = "K";
    private static final String MB_STR = "M";
    private static final String GB_STR = "G";
    private static final Integer UNIT = 1024;
    private static final String FILE_SIZE_DESC_FORMAT = "%.2f";
    private static final String POINT_STR = ".";
    private static final Long ZERO_LONG = 0L;
    private static final Integer ZERO_INT = 0;
    private static final Integer ONE_INT = 1;
    private static final Integer MINUS_ONE_INT = -1;
    private static final String EMPTY_STR = "";
    private static final String SLASH = "/";
    private final static String CHUNKS_FOLDER_NAME = "chunks";
    private static final String DEFAULT_ROOT_FILE_NAME = "rpan";
    private static final String COMMON_SEPARATOR = "__,__";

    /**
     * 生成临时分片文件路径
     *
     * @param chunksFolder
     * @param identifier
     * @param chunkNumber
     * @return
     */
    public static String generateChunkFilePath(String chunksFolder, String identifier, Integer chunkNumber) {
        return new StringBuffer(chunksFolder)
                .append(File.separator)
                .append(DateUtil.getYear())
                .append(File.separator)
                .append(DateUtil.getMonth())
                .append(File.separator)
                .append(DateUtil.getDay())
                .append(File.separator)
                .append(identifier)
                .append(File.separator)
                .append(generateChunkFilename(chunkNumber)).toString();
    }

    /**
     * 生成临时分片文件名称
     *
     * @param chunkNumber
     * @return
     */
    public static String generateChunkFilename(Integer chunkNumber) {
        return new StringBuffer(UUIDUtil.getUUID()).append(COMMON_SEPARATOR).append(chunkNumber).toString();
    }

    /**
     * 根据分片文件名称解析分片文件的分片号
     *
     * @param chunkFilename
     * @return
     */
    public static Integer resolveChunkFileNumber(String chunkFilename) {
        if (StringUtils.isNotBlank(chunkFilename)) {
            String chunkNumberStr = chunkFilename.substring(chunkFilename.lastIndexOf(COMMON_SEPARATOR) + COMMON_SEPARATOR.length());
            return Integer.valueOf(chunkNumberStr);
        }
        return ZERO_INT;
    }

    /**
     * 生成文件本地的保存路径
     *
     * @param filePrefix
     * @param suffix
     * @return
     */
    public static String generateFilePath(String filePrefix, String suffix) {
        return new StringBuffer(filePrefix)
                .append(File.separator)
                .append(DateUtil.getYear())
                .append(File.separator)
                .append(DateUtil.getMonth())
                .append(File.separator)
                .append(DateUtil.getDay())
                .append(File.separator)
                .append(UUIDUtil.getUUID())
                .append(suffix)
                .toString();
    }

    /**
     * 生成文件本地的保存路径
     *
     * @param filePrefix
     * @param filename
     * @param createTime
     * @return
     */
    public static String generateFilePath(String filePrefix, String filename, Date createTime) {
        return new StringBuffer(filePrefix)
                .append(File.separator)
                .append(DateUtil.getYear(createTime))
                .append(File.separator)
                .append(DateUtil.getMonth(createTime))
                .append(File.separator)
                .append(DateUtil.getDay(createTime))
                .append(File.separator)
                .append(filename)
                .toString();
    }

    /**
     * 获取输入流写入输出流
     *
     * @param fileInputStream
     * @param outputStream
     * @param size
     * @throws IOException
     */
    public static void writeFileToStream(FileInputStream fileInputStream, OutputStream outputStream, Long size) throws IOException {
        FileChannel fileChannel = null;
        WritableByteChannel writableByteChannel = null;
        try {
            fileChannel = fileInputStream.getChannel();
            writableByteChannel = Channels.newChannel(outputStream);
            fileChannel.transferTo(ZERO_LONG, size, writableByteChannel);
            outputStream.flush();
        } catch (Exception e) {

        } finally {
            fileInputStream.close();
            outputStream.close();
            fileChannel.close();
            writableByteChannel.close();
        }
    }

    /**
     * 获取输入流写入输出流
     *
     * @param inputStream
     * @param targetFile
     * @param totalSize
     * @throws IOException
     */
    public static void writeStreamToFile(InputStream inputStream, File targetFile, Long totalSize) throws IOException {
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        targetFile.createNewFile();
        RandomAccessFile randomAccessFile = new RandomAccessFile(targetFile, "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
        fileChannel.transferFrom(readableByteChannel, ZERO_LONG, totalSize);
        fileChannel.close();
        randomAccessFile.close();
        readableByteChannel.close();
    }

    /**
     * 传统流对流传输
     *
     * @param inputStream
     * @param outputStream
     * @throws IOException
     */
    public static void writeStreamToStreamNormal(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != MINUS_ONE_INT.intValue()) {
            outputStream.write(buffer, ZERO_INT, len);
        }
        outputStream.flush();
        inputStream.close();
        outputStream.close();
    }

    /**
     * 获取文件大小字符串
     *
     * @param size
     * @return
     */
    public static String getFileSizeDesc(long size) {
        double fileSize = (double) size;
        String fileSizeSuffix = KB_STR;
        fileSize = fileSize / UNIT;
        if (fileSize >= UNIT) {
            fileSize = fileSize / UNIT;
            fileSizeSuffix = MB_STR;
        }
        if (fileSize >= UNIT) {
            fileSize = fileSize / UNIT;
            fileSizeSuffix = GB_STR;
        }
        return String.format(FILE_SIZE_DESC_FORMAT, fileSize) + fileSizeSuffix;
    }

    /**
     * 通过文件名获取文件后缀
     *
     * @param filename
     * @return
     */
    public static String getFileSuffix(String filename) {
        if (StringUtils.isBlank(filename) || (filename.indexOf(POINT_STR) == MINUS_ONE_INT)) {
            return EMPTY_STR;
        }
        return filename.substring(filename.lastIndexOf(POINT_STR)).toLowerCase();
    }

    /**
     * 获取文件扩展名
     *
     * @param suffix
     * @return
     */
    public static String getFileExtName(String suffix) {
        if (StringUtils.isBlank(suffix)) {
            return suffix;
        }
        return suffix.substring(ONE_INT);
    }

    /**
     * 通过文件路径获取文件名称
     *
     * @param filePath
     * @return
     */
    public static String getFilename(String filePath) {
        String filename = EMPTY_STR;
        if (StringUtils.isBlank(filePath)) {
            return filename;
        }
        if (filePath.indexOf(File.separator) != MINUS_ONE_INT) {
            filename = filePath.substring(filePath.lastIndexOf(File.separator) + ONE_INT);
        }
        if (filePath.indexOf(SLASH) != MINUS_ONE_INT) {
            filename = filePath.substring(filePath.lastIndexOf(SLASH) + ONE_INT);
        }
        return filename;
    }

    /**
     * 获取文件的content-type
     *
     * @param filePath
     * @return
     */
    public static String getContentType(String filePath) {
        //利用nio提供的类判断文件ContentType
        File file = new File(filePath);
        String contentType = null;
        try {
            contentType = Files.probeContentType(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //若失败则调用另一个方法进行判断
        if (StringUtils.isBlank(contentType)) {
            contentType = new MimetypesFileTypeMap().getContentType(file);
        }
        return contentType;
    }

    /**
     * 创建文件夹
     *
     * @param folderPath
     */
    public static void createFolder(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    /**
     * 生成临时分片文件目录路径
     *
     * @param basePath
     * @return
     */
    public static String generateChunksFolderPath(String basePath) {
        return basePath + File.separator + CHUNKS_FOLDER_NAME;
    }

    /**
     * 生成临时文件目录路径
     *
     * @return
     */
    public static String generateDefaultRootFolderPath() {
        return System.getProperty("user.home") + File.separator + DEFAULT_ROOT_FILE_NAME;
    }

    /**
     * 删除物理文件
     *
     * @param filePath
     */
    public static void delete(String filePath) throws IOException {
        if (StringUtils.isBlank(filePath)) {
            return;
        }
        delete0(new File(filePath));
    }

    /**
     * 递归删除文件
     *
     * @param file
     * @throws IOException
     */
    private static void delete0(File file) throws IOException {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            final File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    delete0(files[i]);
                }
            }
            Files.delete(file.toPath());
        } else {
            Files.delete(file.toPath());
        }
    }

    /**
     * 校验是否为空文件夹
     *
     * @return
     */
    public static boolean checkIsEmptyFolder(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files == null || files.length == 0) {
                return true;
            }
            for (int i = 0; i < files.length; i++) {
                if (!checkIsEmptyFolder(files[i])) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 生成分片缓存key
     *
     * @param identifier
     * @param userId
     * @return
     */
    public static String generateChunkKey(String identifier, Long userId) {
        return new StringBuffer(identifier).append(COMMON_SEPARATOR).append(userId).toString();
    }

    /**
     * 创建新文件
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static File createFile(String filePath) throws IOException {
        if (StringUtils.isNotBlank(filePath)) {
            File file = new File(filePath);
            if (file.exists()) {
                return file;
            }
            createFolder(file.getParent());
            file.createNewFile();
            return file;
        }
        return null;
    }

    /**
     * 追加写文件
     *
     * @param target
     * @param source
     * @throws IOException
     */
    public static void appendWrite(Path target, Path source) throws IOException {
        Files.write(target, Files.readAllBytes(source), StandardOpenOption.APPEND);
    }

    /**
     * 移动物理文件
     *
     * @param sourcePath
     * @param targetPath
     * @throws IOException
     */
    public static void moveFile(String sourcePath, String targetPath) throws IOException {
        createFile(targetPath);
        Files.move(Paths.get(sourcePath), Paths.get(targetPath), StandardCopyOption.ATOMIC_MOVE);
    }

    /**
     * 清理目标文件夹的所有子空文件夹
     *
     * @param parentFolder
     */
    public static void cleanChildEmptyFolder(File parentFolder) throws IOException {
        if (parentFolder != null && parentFolder.isDirectory()) {
            File[] files = parentFolder.listFiles();
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory() && FileUtil.checkIsEmptyFolder(files[i])) {
                        delete(files[i].getAbsolutePath());
                    }
                }
            }
        }
    }

}
