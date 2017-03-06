package com.fr.bi.cluster.manager;import com.fr.bi.cluster.ClusterHostManagerInterface;import com.fr.bi.cluster.utils.ClusterEnv;import com.fr.bi.cluster.utils.PropertiesUtils;import com.fr.file.ClusterService;import java.io.File;import java.util.Properties;/** * 从配置文件中读取主机信息 * Created by Hiram on 2015/2/27. */public class TextClusterHostManager implements ClusterHostManagerInterface {    private static TextClusterHostManager ourInstance = new TextClusterHostManager();    private boolean isSelf;    private ClusterService hostClusterService;    private boolean isInit;    private boolean isConfig;    private TextClusterHostManager() {        ensureInit();    }    public static TextClusterHostManager getInstance() {        return ourInstance;    }    @Override    public String getIp() {        return getHostClusterService().getIp();    }    @Override    public int getPort() {        return Integer.parseInt(getHostClusterService().getPort());    }    @Override    public String getWebAppName() {        return null;    }    @Override    public String getServiceName() {        return null;    }    @Override    public boolean isSelf() {        ensureInit();        return isSelf;    }    @Override    public ClusterService getHostClusterService() {        ensureInit();        return hostClusterService;    }    public boolean isConfig() {        return isConfig;    }    private void ensureInit() {        if (!isInit) {            isInit = true;            init();        }    }    private void init() {        File infoFile = ClusterEnv.getRedirectInfoFile();        Properties properties = PropertiesUtils.load(infoFile);        if (properties == null) {            return;        }        hostClusterService = new ClusterService();        hostClusterService.setIp(properties.getProperty("ip"));        hostClusterService.setPort(properties.getProperty("port"));        hostClusterService.setWebAppName(properties.getProperty("webAppName"));        hostClusterService.setServiceName(properties.getProperty("serviceName"));        this.isSelf = Boolean.valueOf(properties.getProperty("isSelf"));        this.isConfig = Boolean.valueOf(properties.getProperty("isConfig"));    }}