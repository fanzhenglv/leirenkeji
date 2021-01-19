package com.atguigu.gmall0218.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
public class BaseAttrValue implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 获取主键自增！
    private String id;
    @Column
    private String valueName;
    @Column
    private String attrId;
    @Transient // 表示当前字段不是表中的字段，是业务需要使用的！
    private String edit;

}
