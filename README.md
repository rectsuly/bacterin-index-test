# bacterin-index-test
利用JDBC和Oracle数据库的连接建立药品疫苗应用系统索引测试

使用VC/C++等程序设计语言以及与SQL（ORACLE）的接口编写一个有关药品疫苗应用系统。该系统涉及的关系模式有：
1.BS_BACTERIN_INFO (int 疫苗ID, String 疫苗编码, String 疫苗名称, int 疫苗种类, String 有效期, String 上市时间, String 上市地点);

2.BS_BACTERIN_PRICE(int 疫苗ID, int 厂家ID, int 上市价格, int 销售价格);

3.BS_MANUFACT(int 厂家ID, String 产商简称, String 产商全称, String 备注);

4.BS_PROVIDER(int 供应商ID, String 供应商名称, String 供应商地址, String 备注);

5.RT_PUTINSTORE_MAIN(int 供应商ID, int 确认, date 入库日期, String 采购员);

6.RT_PUTINSTORE_SUB(int SERIALNUMBER, int 疫苗ID, int 厂家ID, String 疫苗编码, date 有效期, int 销售价格, int 上市价格, int 入库数量, date 入库日期, String 发票号, int 疫苗种类, int 销售金额, int 应付账款, int 预付账款, String 上市名称, String 上市地点);

7.RT_PUTOUTSTORE_MAIN(int 确认, date 出库日期, String 提货人员, String 发票号);

8.RT_PUTOUTSTORE_SUB(int SERIALNUMBER, int 疫苗ID, int 厂家ID, String 疫苗编码, date 有效期, int 销售价格, int 上市价格, int 出库数量, int 疫苗种类, int 上市金额, int 销售金额, int 应付账款, String 发票号);

9.RT_STORE(int 疫苗ID, int 厂家ID, String 疫苗编码, date 有效期, int 上市数量, date 上市日期, int 上市价格, int 销售价格, int 销售金额);

10.RT_STORE_SORT(int 上市数量, int 上市金额, int 销售金额).

该系统要求完成的功能：

1．创建索引（一般索引、唯一性索引）。

2．给出一个涉及索引属性的查询访问。循环执行该查询20次，显示执行时间。

3．删除上一题查询访问涉及的索引，再重复执行该查询20次，显示执行时间。

4．修改上两个题目中的查询为多表查询，在重复有索引和无索引时的执行情况。如果时间比较区分不出来，增加循环执行次数。

5．其他任何你愿意做的与性能有关的讨论及实验。
