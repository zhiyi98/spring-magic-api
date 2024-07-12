package cn.gojava.app.common.utils.mybatis;

import cn.gojava.app.common.utils.ServletUtils;
import cn.gojava.app.common.utils.text.Convert;
import com.github.pagehelper.PageHelper;

/**
 * 分页工具类
 */
public class PageUtils extends PageHelper
{
    /**
     * 页号
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 页数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 设置请求分页数据
     */
    public static void startPage()
    {
        Integer pageNum = Convert.toInt(ServletUtils.getParameter(PAGE_NUM), 1);
        Integer pageSize = Convert.toInt(ServletUtils.getParameter(PAGE_SIZE), 10);
        //noinspection resource
        PageHelper.startPage(pageNum, pageSize);
    }

    /**
     * 清理分页的线程变量
     */
    public static void clearPage()
    {
        PageHelper.clearPage();
    }
}