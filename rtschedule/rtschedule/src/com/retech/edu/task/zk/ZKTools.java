package com.retech.edu.task.zk;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.*;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.data.Stat;

import java.util.Map;

/**
 * zookeeper工具类
 * @author jackwang
 */
public class ZKTools {

    /**
     * 创建node
     *
     * @param nodeName
     * @param value
     * @return
     */
    public static boolean createNode(CuratorFramework client,String nodeName, String value) {
        boolean suc = false;
        try {
            Stat stat = client.checkExists().forPath(nodeName);
            if (stat == null) {
                String opResult = null;
                if (Strings.isNullOrEmpty(value)) {
                    opResult = client.create().creatingParentsIfNeeded().forPath(nodeName);
                }
                else {
                    opResult =
                            client.create().creatingParentsIfNeeded()
                                    .forPath(nodeName, value.getBytes(Charsets.UTF_8));
                }
                suc = Objects.equal(nodeName, opResult);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return suc;
    }

    /**
     * 更新节点
     *
     * @param nodeName
     * @param value
     * @return
     */
    public static boolean updateNode(CuratorFramework client,String nodeName, String value) {
        boolean suc = false;
        try {
            Stat stat = client.checkExists().forPath(nodeName);
            if (stat != null) {
                Stat opResult = client.setData().forPath(nodeName, value.getBytes(Charsets.UTF_8));
                suc = opResult != null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return suc;
    }

    /**
     * 删除节点
     *
     * @param nodeName
     */
    public static void deleteNode(CuratorFramework client,String nodeName) {
        try {
            client.delete().deletingChildrenIfNeeded().forPath(nodeName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 找到指定节点下所有子节点的名称与值
     *
     * @param node
     * @return
     */
    public static Map<String, String> listChildrenDetail(CuratorFramework client,String node) {
        Map<String, String> map = Maps.newHashMap();
        try {
            GetChildrenBuilder childrenBuilder = client.getChildren();
            List<String> children = childrenBuilder.forPath(node);
            GetDataBuilder dataBuilder = client.getData();
            if (children != null) {
                for (String child : children) {
                    String propPath = ZKPaths.makePath(node, child);
                    map.put(child, new String(dataBuilder.forPath(propPath), Charsets.UTF_8));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    /**
     * 列出子节点的名称
     *
     * @param node
     * @return
     */
    public static List<String> listChildren(CuratorFramework client,String node) {
        List<String> children = Lists.newArrayList();
        try {
            GetChildrenBuilder childrenBuilder = client.getChildren();
            children = childrenBuilder.forPath(node);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return children;
    }
}
