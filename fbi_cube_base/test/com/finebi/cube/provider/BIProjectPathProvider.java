package com.finebi.cube.provider;

import com.finebi.cube.common.log.BILoggerFactory;
import com.fr.bi.stable.utils.program.BIStringUtils;

import java.io.File;

/**
 * Created by Lucifer on 2017-2-21.
 *
 * @author Lucifer
 * @since 4.0
 */
public class BIProjectPathProvider {

    private static String basePath = computeBasePath();

    public static String projectPath = computePath();

    public static String bigfilePath = computeBigfilePath();

    private static String computeBigfilePath() {
        if (basePath.endsWith(File.separator)) {
            return BIStringUtils.append(basePath, "testFolder", File.separator, "bigfiles", File.separator, "cube");
        } else {
            return BIStringUtils.append(basePath, File.separator, "testFolder", File.separator, "bigfiles", File.separator, "cube");
        }
    }


    private static String computePath() {
        if (basePath.endsWith(File.separator)) {
            return BIStringUtils.append(basePath, "testFolder", File.separator, "cube");
        } else {
            return BIStringUtils.append(basePath, File.separator, "testFolder", File.separator, "bigfiles", File.separator, "cube");
        }
    }

    private static String computeBasePath() {
        String classFileName = "classes";
        String libFileName = "lib";
        File directory = new File("");
        String classRootPath = "";
        if (new File(BIProjectPathProvider.class.getResource("/").getPath()).exists()) {
            classRootPath = BIProjectPathProvider.class.getResource("/").getPath();
        } else if (new File("H:\\jenkins\\workspace\\test\\build\\classes").exists()) {
            classRootPath = "H:\\jenkins\\workspace\\test\\build\\classes";
        }
        classRootPath = classRootPath.replace("/", File.separator);
        if (classRootPath.endsWith(File.separator)) {
            classRootPath = cut(classRootPath, File.separator);
        }
        if (classRootPath.endsWith(classFileName)) {
            classRootPath = cut(classRootPath, classFileName);
        }
        if (classRootPath.endsWith(libFileName)) {
            classRootPath = cut(classRootPath, libFileName);
        }
        return classRootPath;
    }

    private static String cut(String path, String suffix) {
        return BIStringUtils.cutEndChar(path, suffix);
    }

    public static void main(String[] args) {
        BILoggerFactory.getLogger().info(computeBasePath());
        BILoggerFactory.getLogger().info(computePath());
        BILoggerFactory.getLogger().info(computeBigfilePath());
    }
}
