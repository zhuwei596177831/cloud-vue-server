package com.example.coreweb.util;

import com.example.core.vo.system.IpAddressVo;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.springframework.util.StringUtils;

import java.io.File;
import java.lang.reflect.Method;

/**
 * @author 朱伟伟
 * @date 2022-06-07 22:56:26
 * @description 根据id地址获取位置
 */
public class AddressUtils {

    //private static final Logger logger = LoggerFactory.getLogger(AddressUtils.class);

    public static void main(String[] args) {
        //国家|大区|城市|网络运营商
        //中国|0|浙江省|杭州市|阿里云
        //System.out.println(getAddress("101.37.117.70"));

        //中国|0|北京|北京市|移动
        //System.out.println(getAddress("120.244.228.9"));

        //印度|0|0|0|0
        //System.out.println(getAddress("103.146.78.22"));

        //美国|0|德克萨斯|达拉斯|0
        //System.out.println(getAddress("103.136.149.1"));

        //System.out.println(getAddress("127.0.0.1"));

        //System.out.println(getAddress("172.16.20.11"));

        //IpAddressVo ipAddressVo = getIpAddressVo("101.37.117.70");
        //System.out.println(ipAddressVo);
    }

    public static IpAddressVo getIpAddressVo(String ip) {
        String address = getAddress(ip);
        if (StringUtils.isEmpty(address)) {
            return null;
        }
        String[] array = StringUtils.delimitedListToStringArray(address, "|");
        IpAddressVo ipAddressVo = new IpAddressVo();
        ipAddressVo.setCountry(array[0]);
        ipAddressVo.setRegion(array[1]);
        ipAddressVo.setProvince(array[2]);
        ipAddressVo.setCity(array[3]);
        ipAddressVo.setIsp(array[4]);
        return ipAddressVo;
    }

    public static String getAddress(String ip) {

        //db
        String dbPath = AddressUtils.class.getResource("ip2region.db").getPath();

        File file = new File(dbPath);

        if (!file.exists()) {
            System.out.println("Error: Invalid ip2region.db file");
        }

        //查询算法
        int algorithm = DbSearcher.BTREE_ALGORITHM; //B-tree
        //DbSearcher.BINARY_ALGORITHM //Binary
        //DbSearcher.MEMORY_ALGORITYM //Memory
        try {
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, dbPath);

            //define the method
            Method method = null;
            switch (algorithm) {
                case DbSearcher.BTREE_ALGORITHM:
                    method = searcher.getClass().getMethod("btreeSearch", String.class);
                    break;
                case DbSearcher.BINARY_ALGORITHM:
                    method = searcher.getClass().getMethod("binarySearch", String.class);
                    break;
                case DbSearcher.MEMORY_ALGORITYM:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
            }

            DataBlock dataBlock = null;
            if (!Util.isIpAddress(ip)) {
                System.out.println("Error: Invalid ip address");
            }

            dataBlock = (DataBlock) method.invoke(searcher, ip);

            return dataBlock.getRegion();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
