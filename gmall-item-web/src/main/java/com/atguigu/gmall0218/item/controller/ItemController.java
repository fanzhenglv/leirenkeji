package com.atguigu.gmall0218.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall0218.bean.SkuImage;
import com.atguigu.gmall0218.bean.SkuInfo;
import com.atguigu.gmall0218.bean.SpuSaleAttr;
import com.atguigu.gmall0218.service.ManageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ItemController {
    @Reference
    private ManageService manageService;
    @RequestMapping("{skuId}.html")
    public String item(@PathVariable String skuId, HttpServletRequest request){
      SkuInfo skuInfo = manageService.getSkuInfo(skuId);
       List<SkuImage>  skuImageList=  manageService.getSkuImageList(skuId);
       List<SpuSaleAttr> spuSaleAttrList=manageService.getSpuSaleAttrList(skuInfo);
        request.setAttribute("spuSaleAttrList",spuSaleAttrList);
        request.setAttribute("skuImageList",skuImageList);
        request.setAttribute("skuInfo",skuInfo);
        return "item";
    }

}
