package com.atguigu.gmall0218.manage.service.impl;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall0218.bean.*;
import com.atguigu.gmall0218.config.RedisUtil;
import com.atguigu.gmall0218.manage.constant.ManageConst;
import com.atguigu.gmall0218.manage.mapper.*;
import com.atguigu.gmall0218.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ManageServiceImpl implements ManageService {
    Lock lock=new ReentrantLock();
    Condition  condition=lock.newCondition();
    @Autowired
    private BaseCatalog1Mapper baseCatalog1Mapper;
    @Autowired
    private BaseCatalog2Mapper baseCatalog2Mapper;
    @Autowired
    private BaseCatalog3Mapper baseCatalog3Mapper;
    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;
    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;
    @Autowired
    private SpuInfoMapper spuInfoMapper;
    @Autowired
    private BaseSaleAttrMapper baseSaleAttrMapper;
    @Autowired
    private SpuImageMapper spuImageMapper;
    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;
    @Autowired
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;
    @Autowired
    private SkuInfoMapper skuInfoMapper;
    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;
    @Autowired
    private SkuImageMapper skuImageMapper;
    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public List<BaseCatalog1> getBascatlog1List() {
        return baseCatalog1Mapper.selectAll();
    }

    @Override
    public List<BaseCatalog2> getBasecatlog2List(String catalog1Id) {
        BaseCatalog2 baseCatalog2=new BaseCatalog2();
        baseCatalog2.setCatalog1Id(catalog1Id);

        return baseCatalog2Mapper.select(baseCatalog2);
    }
    @Override
    public List<BaseCatalog3> getBasecatlog3List(String catalog2Id) {
        BaseCatalog3 baseCatalog3=new BaseCatalog3();
        baseCatalog3.setCatalog2Id(catalog2Id);
        return baseCatalog3Mapper.select(baseCatalog3);
    }
    @Override
    public List<BaseAttrInfo> getBaseAttrInfoList(String catalog3Id) {

        BaseAttrInfo baseAttrInfo=new BaseAttrInfo();
        baseAttrInfo.setCatalog3Id(catalog3Id);
        List<BaseAttrInfo>  baseAttrInfoList= baseAttrInfoMapper.select(baseAttrInfo);
        for (BaseAttrInfo attrInfo : baseAttrInfoList) {
            String id = attrInfo.getId();
            BaseAttrValue baseAttrValue=new BaseAttrValue();
            baseAttrValue.setAttrId(id);
            List<BaseAttrValue> baseAttrValueList = baseAttrValueMapper.select(baseAttrValue);
            attrInfo.setAttrValueList(baseAttrValueList);
        }
        return baseAttrInfoList;
    }
    @Override
    @Transactional
    public void saveAttrInfoAndAttrValue(BaseAttrInfo baseAttrInfo) {
        int num=0;
        if (baseAttrInfo.getId()==null){
            baseAttrInfoMapper.insert(baseAttrInfo);
        }
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        if (attrValueList.size()==0){
            BaseAttrValue baseAttrValue=new BaseAttrValue();
            baseAttrValue.setAttrId(baseAttrInfo.getId());
            num=baseAttrValueMapper.delete(baseAttrValue);
        }else {
            for (BaseAttrValue baseAttrValue : attrValueList) {

                String id = baseAttrInfo.getId();


                if ("false".equals(baseAttrValue.getEdit()) && baseAttrValue.getAttrId() != null) {
                    num = baseAttrValueMapper.updateByPrimaryKeySelective(baseAttrValue);
                } else {
                    baseAttrValue.setAttrId(id);
                    num = baseAttrValueMapper.insert(baseAttrValue);
                }


            }
        }
if (num>0){
    System.out.println("添加成功");
}else {
    System.out.println("添加失败");
}
    }

    @Override
    public List<BaseAttrValue> getAttrValueList(String attrId) {
        BaseAttrValue baseAttrValue=new BaseAttrValue();
        baseAttrValue.setAttrId(attrId);
        List<BaseAttrValue> baseAttrValueList = baseAttrValueMapper.select(baseAttrValue);

        return baseAttrValueList;
    }

    @Override
    public List<SpuInfo> spuList(String catalog3Id) {
        SpuInfo spuInfo=new SpuInfo();
        spuInfo.setCatalog3Id(catalog3Id);
        return spuInfoMapper.select(spuInfo);
    }
    @Override
    public List<BaseSaleAttr> baseSaleAttrList() {
        return baseSaleAttrMapper.selectAll();
    }

    @Override
    @Transactional
    public List<BaseSaleAttr> saveSpuInfo(SpuInfo spuInfo) {
        spuInfoMapper.insert(spuInfo);
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {
            List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
            for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {
                spuSaleAttrValue.setSpuId(spuInfo.getId());
                spuSaleAttrValueMapper.insert(spuSaleAttrValue);
            }

            spuSaleAttr.setSpuId(spuInfo.getId());
            spuSaleAttrMapper.insert(spuSaleAttr);
        }
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        for (SpuImage spuImage : spuImageList) {
            spuImage.setSpuId(spuInfo.getId());
            spuImageMapper.insert(spuImage);
        }
        return null;
    }

    @Override
    public List<SpuSaleAttr> spuSaleAttrList(String spuId) {
        List<SpuSaleAttr> spuSaleAttrList = spuSaleAttrMapper.myGetSpuSaleAttrList(spuId);

        return spuSaleAttrList;
    }

    @Override
    public List<SpuImage> spuImageList(String spuId) {
        SpuImage spuImage=new SpuImage();
        spuImage.setSpuId(spuId);
        return spuImageMapper.select(spuImage);
    }

    @Override
    @Transactional
    public void saveSkuInfo(SkuInfo skuInfo) {
        skuInfoMapper.insert(skuInfo);
        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
            skuSaleAttrValue.setSkuId(skuInfo.getId());
            skuSaleAttrValueMapper.insert(skuSaleAttrValue);

        }
        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        for (SkuImage skuImage : skuImageList) {
            skuImage.setSkuId(skuInfo.getId());
            skuImageMapper.insert(skuImage);

        }
        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        for (SkuAttrValue skuAttrValue : skuAttrValueList) {
            skuAttrValue.setSkuId(skuInfo.getId());
            skuAttrValueMapper.insert(skuAttrValue);
        }

    }

    @Override
    public SkuInfo getSkuInfo(String skuId) {
        Jedis jedis = null;
        SkuInfo skuInfo=null;
        try {
            jedis = redisUtil.getJedis();

            String key= ManageConst.SKUKEY_PREFIX+skuId+ManageConst.SKUKEY_SUFFIX;
            if (jedis.exists(key)){
                String s = jedis.get(key);
                System.out.println(s);
                 skuInfo = JSON.parseObject(s, SkuInfo.class);

                return skuInfo;


            }else {

                try {
                    lock.lock();
                    if (skuInfo==null){
                        skuInfo  = getSkuInfoFromDB(skuId);
                        jedis.setex(key,ManageConst.SKUKEY_TIMEOUT,JSON.toJSONString(skuInfo));
                        return skuInfo;
                    }else {
                        return skuInfo;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis!=null){
                jedis.close();
            }

        }
        return getSkuInfoFromDB(skuId);


    }

    private SkuInfo getSkuInfoFromDB(String skuId) {
        return skuInfoMapper.selectByPrimaryKey(skuId);
    }

    @Override
    public List<SkuImage> getSkuImageList(String skuId) {
        SkuImage skuImage=new SkuImage();
        skuImage.setSkuId(skuId);
        return skuImageMapper.select(skuImage);
    }

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrList(SkuInfo skuInfo) {
        List<SpuSaleAttr> spuSaleAttrList= spuSaleAttrMapper.myDiffcultGetSpuSaleAttrList(skuInfo.getId(),skuInfo.getSpuId());
        return spuSaleAttrList;
    }
}
