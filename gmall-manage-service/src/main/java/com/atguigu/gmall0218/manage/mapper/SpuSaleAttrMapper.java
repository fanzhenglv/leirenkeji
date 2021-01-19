package com.atguigu.gmall0218.manage.mapper;

import com.atguigu.gmall0218.bean.SkuInfo;
import com.atguigu.gmall0218.bean.SpuSaleAttr;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SpuSaleAttrMapper extends Mapper<SpuSaleAttr> {
    List<SpuSaleAttr> myGetSpuSaleAttrList(String spuId);



    List<SpuSaleAttr> myDiffcultGetSpuSaleAttrList(String id, String spuId);

}
