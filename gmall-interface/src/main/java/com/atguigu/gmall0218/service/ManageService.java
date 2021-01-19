package com.atguigu.gmall0218.service;

import com.atguigu.gmall0218.bean.*;

import java.util.List;

public interface ManageService {
    List<BaseCatalog1> getBascatlog1List();
    List<BaseCatalog2> getBasecatlog2List(String catalog1Id);
    List<BaseCatalog3> getBasecatlog3List(String catalog2Id);
    List<BaseAttrInfo> getBaseAttrInfoList(String catalog3Id);

    void saveAttrInfoAndAttrValue(BaseAttrInfo baseAttrInfo);

    List<BaseAttrValue> getAttrValueList(String attrId);

    List<SpuInfo> spuList(String catalog3Id);

    List<BaseSaleAttr> baseSaleAttrList();

    List<BaseSaleAttr> saveSpuInfo(SpuInfo spuInfo);


    List<SpuSaleAttr> spuSaleAttrList(String spuId);

    List<SpuImage> spuImageList(String spuId);

    void saveSkuInfo(SkuInfo skuInfo);

    SkuInfo getSkuInfo(String skuId);

    List<SkuImage> getSkuImageList(String skuId);

    List<SpuSaleAttr> getSpuSaleAttrList(SkuInfo skuInfo);

}
