package com.atguigu.gmall0218.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall0218.bean.*;
import com.atguigu.gmall0218.service.ManageService;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class ManagerController {
    @Reference
    private ManageService manageService;
    @RequestMapping("getCatalog1")
    public List<BaseCatalog1> getCatalog1(){
   return manageService.getBascatlog1List();

    }
    //http://localhost:8082/getCatalog1
    //http://localhost:8082/getCatalog2?catalog1Id=3
    @RequestMapping("getCatalog2")
    public List<BaseCatalog2> getCatalog2(String catalog1Id){
        List<BaseCatalog2> basecatlog2List = manageService.getBasecatlog2List(catalog1Id);

        return basecatlog2List;
    }
    //http://localhost:8082/getCatalog3?catalog2Id=24
    @RequestMapping("getCatalog3")
    public List<BaseCatalog3> getCatalog3(String catalog2Id){
        List<BaseCatalog3> basecatlog3List = manageService.getBasecatlog3List(catalog2Id);
        return basecatlog3List;
    }

    //http://localhost:8082/attrInfoList?catalog3Id=228
    @RequestMapping("attrInfoList")
    public List<BaseAttrInfo> attrInfoList(String catalog3Id){
        List<BaseAttrInfo> baseAttrInfoList = manageService.getBaseAttrInfoList(catalog3Id);
        return baseAttrInfoList;
    }
    //http://localhost:8082/saveAttrInfo

    @RequestMapping("saveAttrInfo")
    public void attrInfoList(@RequestBody BaseAttrInfo baseAttrInfo){
        manageService.saveAttrInfoAndAttrValue(baseAttrInfo);
    }
    //http://localhost:8082/getAttrValueList?attrId=23
    @RequestMapping("getAttrValueList")
    public List<BaseAttrValue> getAttrValueList(String attrId){
        List<BaseAttrValue>  baseAttrValueList= manageService.getAttrValueList(attrId);
        return baseAttrValueList;
    }
    //http://localhost:8082/spuList?catalog3Id=61

    @RequestMapping("spuList")
    public List<SpuInfo> spuList(String catalog3Id){
        List<SpuInfo> spuInfoList=manageService.spuList(catalog3Id);
        return spuInfoList;
    }
    //http://localhost:8082/baseSaleAttrList
    @RequestMapping("baseSaleAttrList")
    public List<BaseSaleAttr> baseSaleAttrList(){
        List<BaseSaleAttr> baseSaleAttrList=manageService.baseSaleAttrList();
        return baseSaleAttrList;
    }
    //ttp://localhost:8082/saveSpuInfo
    @RequestMapping("saveSpuInfo")
    public List<BaseSaleAttr> saveSpuInfo(@RequestBody SpuInfo spuInfo){
        List<BaseSaleAttr> baseSaleAttrList=manageService.saveSpuInfo(spuInfo);
        return baseSaleAttrList;
    }
    //http://localhost:8082/spuSaleAttrList? =15
    @RequestMapping("spuSaleAttrList")
    public List<SpuSaleAttr> spuSaleAttrList(String spuId){
        List<SpuSaleAttr> spuSaleAttrList=manageService.spuSaleAttrList(spuId);
        return spuSaleAttrList;
    }
    //http://localhost:8082/spuImageList?spuId=70
    @RequestMapping("spuImageList")
    public List<SpuImage> spuImageList(String spuId){
        List<SpuImage> spuImageList=manageService.spuImageList(spuId);
        return spuImageList;
    }
    //http://localhost:8082/saveSkuInfo
    @RequestMapping("saveSkuInfo")
    public void saveSkuInfo(@RequestBody SkuInfo skuInfo){

       manageService.saveSkuInfo(skuInfo);
    }

}
