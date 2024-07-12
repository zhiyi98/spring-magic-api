package cn.gojava.app.service;

import cn.gojava.app.entity.SysDictData;
import cn.gojava.app.mapper.SysDictDataMapper;
import cn.gojava.app.common.utils.mybatis.PageUtils;
import com.github.pagehelper.PageInfo;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

import static cn.gojava.app.common.utils.mybatis.PageUtils.startPage;


/**
 * 字典 业务层处理
 */
@Service
public class SysDictDataService
{
    @Resource
    private SysDictDataMapper dictDataMapper;

    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    public PageInfo<SysDictData> selectDictDataList(SysDictData dictData)
    {
        PageUtils.startPage();
        List<SysDictData> sysDictData = dictDataMapper.selectListByQuery(new QueryWrapper()
                .like(SysDictData::getDictLabel, dictData.getDictLabel(), StringUtils.hasText(dictData.getDictLabel()))
                .like(SysDictData::getDictType, dictData.getDictType(), StringUtils.hasText(dictData.getDictType()))
                .orderBy(SysDictData::getCreateTime).asc());
        return new PageInfo<>(sysDictData);
    }

    /**
     * 根据字典类型和值获取字典数据。
     * @param dictType 字典类型，用于指定要获取字典数据的类型。
     * @param value    字典值，用于指定要获取的字典数据的值。
     * @return 如果找到匹配的字典数据，则返回相应的 SysDictData 对象；否则返回 null。
     */
    public SysDictData getDictData(String dictType, String value)
    {
        return dictDataMapper.selectOneByQuery(new QueryWrapper()
                .eq(SysDictData::getDictType, dictType)
                .eq(SysDictData::getDictValue, value)
        );
    }

    /**
     * 根据字典类型和标签解析字典数据。
     * @param dictType 字典类型，用于指定要解析字典数据的类型。
     * @param label    字典标签，用于指定要解析的字典数据的标签。
     * @return 如果找到匹配的字典数据，则返回相应的 SysDictData 对象；否则返回 null。
     */
    public SysDictData parseDictData(String dictType, String label) {
        return dictDataMapper.selectOneByQuery(new QueryWrapper()
                .eq(SysDictData::getDictType, dictType)
                .eq(SysDictData::getDictLabel, label)
        );
    }
}
