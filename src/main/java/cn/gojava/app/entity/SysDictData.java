package cn.gojava.app.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

/**
 * 字典数据表 sys_dict_data
 */
@Data
@Table("sys_dict_data")
public class SysDictData
{
    /**
     * 字典编码
     */
    @Id
    private Long dictCode;
    /**
     * 字典排序
     */
    private Integer dictSort;

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典键值
     */
    private String dictValue;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 样式属性（其他样式扩展）
     */
    private String cssClass;

    /**
     * 表格回显样式
     */
    private String listClass;

    /**
     * 是否默认（Y是 N否）
     */
    private char isDefault;
    /**
     * 状态（0正常 1停用）
     */
    private char status;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;


}
