package com.example.coreweb.util;

import com.example.core.vo.system.IpAddressVo;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;

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
        System.out.println(getAddress("101.37.117.70"));
        System.out.println(getAddress("103.135.248.233"));
        System.out.println(getAddress("27.102.130.167"));

        //中国|0|北京|北京市|移动
        //System.out.println(getAddress("120.244.228.9"));

        //印度|0|0|0|0
        //System.out.println(getAddress("103.146.78.22"));

        //美国|0|德克萨斯|达拉斯|0
        System.out.println(getAddress("103.136.149.1"));

        System.out.println(getAddress("114.100.182.144"));

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
        try {
            //db
            //String dbPath = AddressUtils.class.getResource("ip2region.db").getPath();
            //logger.info("ip2region.db 文件路径：{}", dbPath);
            //
            //File file = new File(dbPath);
            //
            //if (!file.exists()) {
            //    logger.error("Error: ip2region.db 文件 不存在");
            //}
            URL url = AddressUtils.class.getClassLoader().getResource("com/example/coreweb/util/ip2region.db");
            //logger.info("ip2region.db 文件路径：{}", url.getPath());
            UrlResource urlResource = new UrlResource(url);
            InputStream inputStream = urlResource.getInputStream();
            //查询算法
            //int algorithm = DbSearcher.BTREE_ALGORITHM; //B-tree
            int algorithm = DbSearcher.MEMORY_ALGORITYM; //B-tree
            //DbSearcher.BINARY_ALGORITHM //Binary
            //DbSearcher.MEMORY_ALGORITYM //Memory
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, FileCopyUtils.copyToByteArray(inputStream));

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
            //logger.error("获取ip所属地失败：{}", e.getMessage());
        }

        return null;
    }

}
