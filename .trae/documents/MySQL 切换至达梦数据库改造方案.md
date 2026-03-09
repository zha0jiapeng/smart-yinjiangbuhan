## 目标与范围
- 将当前项目的数据库从 MySQL 平滑迁移至达梦（DM8），确保功能与性能不回退
- 适配 JDBC 驱动、数据源、分页方言、SQL/DDL 语法与大小写策略
- 假设达梦库中所有表名与字段名为大写，且不使用引号保持默认上档行为

## 路线选择
- 路线 A（推荐）：原生达梦方言
  - 使用达梦 JDBC 驱动与 DM 方言；严格修正 MySQL 专属语法
- 路线 B（快速试切）：达梦兼容模式
  - 在 JDBC URL 增加 `compatibleMode=mysql` 降低改造量；后续逐步去兼容化

## 依赖与驱动
- 引入达梦 JDBC 驱动
  - `driver-class-name: dm.jdbc.driver.DmDriver`
  - `url: jdbc:dm://<HOST>:5236?schema=<SCHEMA>`（按需追加 `compatibleMode=mysql`）
  - 若中央仓库无驱动，先将 JAR 安装入私服或本地仓库
- 保持连接池（Druid/Hikari）不变，仅替换驱动与 URL

## 配置改造
- 数据源配置（application*.yml）
  - 更新 `spring.datasource.*`（或 Druid 配置）为达梦驱动与 URL
  - 校验连接测试语句（如 Druid 的 `validationQuery`：`select 1`/`select 1 from dual` 均可，达梦支持 dual）
- 分页方言
  - 如使用 PageHelper：`helperDialect: dm`（或 `oracle` 作为过渡）
  - 如使用 MyBatis-Plus：`DbType.DM`，或在 `mybatis-plus.configuration` 配置方言
- MyBatis 数据库标识
  - 配置 `databaseIdProvider`，为 `dm` 与 `mysql` 提供差异 SQL（必要时）

## SQL/Mapper 适配
- 清理 MySQL 专属语法
  - `LIMIT offset, size` → 使用分页插件；或改写为达梦支持的分页
  - `ON DUPLICATE KEY UPDATE`、`REPLACE INTO` → 改为 `MERGE INTO` 或显式判重更新
  - `INSERT IGNORE` → 显式判重/异常捕获
  - 函数替换：`NOW()`/`CURRENT_TIMESTAMP` → `SYSDATE`/`CURRENT_TIMESTAMP`；`IFNULL` → `NVL`；字符串拼接 `CONCAT` → `||`
  - `DATE_FORMAT` 等日期函数 → 使用达梦对应函数（`TO_CHAR`/`TO_DATE`）
- 标识符与转义
  - 去除 MySQL 反引号 `` ` ``；不对标识符加引号，保持达梦默认上档
  - SQL 与 XML Mapper 中的表名、列名统一为大写，或在查询中使用别名与实体属性对齐
- 列别名与自动映射
  - 达梦返回的列名默认大写；若启用 MyBatis `mapUnderscoreToCamelCase`，建议在 SELECT 中为列加别名与实体属性一致（小写/驼峰）
  - 示例：`SELECT USER_ID AS userId, USER_NAME AS userName FROM ...`

## DDL 与数据类型
- 主键与自增
  - MySQL 自增 → 达梦 `IDENTITY`（推荐）或 `SEQUENCE + TRIGGER`
- 常见类型映射
  - `TINYINT(1)` → `NUMBER(1)`（布尔语义）
  - `INT/BIGINT` → `NUMBER(10/19)` 或 `INTEGER/DECIMAL`（按需求）
  - `VARCHAR/TEXT/LONGTEXT` → `VARCHAR2`/`CLOB`
  - `DATETIME/TIMESTAMP` → `TIMESTAMP`；默认值 `CURRENT_TIMESTAMP`/`SYSDATE`
  - `BLOB` → `BLOB`
- 约束/索引
  - 关键字冲突（如 USER、ORDER 等）避免作为未转义标识符；或更名
  - 复核唯一索引与外键命名（达梦对标识符长度与大小写的规范）
- 批量调整
  - 将建表脚本重写为达梦 DDL，统一大写标识符

## 内置组件表适配
- Quartz（若使用）：用达梦版建表脚本替换 MySQL 脚本
- 认证/审计/日志表：检查触发器/默认值是否用到 MySQL 函数
- 分库分表或分布式 ID：若依赖数据库自增，统一改为达梦 IDENTITY 或应用侧雪花算法

## 配置与代码扫描清单
- 搜索并修复以下痕迹
  - 反引号 `` `...` ``、`LIMIT`、`INSERT IGNORE`、`REPLACE INTO`、`ON DUPLICATE KEY`、`FIND_IN_SET`、`GROUP_CONCAT`、`DATE_FORMAT`、`IFNULL`、`LOCATE`
  - MySQL 专属注释与 hint
  - MySQL 方言硬编码（分页、函数、关键字）
- XML Mapper/注解 SQL：统一大写表列或添加别名

## 数据迁移
- 建库/建表：基于达梦 DDL 初始化空库
- 数据导入
  - 通过 ETL/脚本从 MySQL 导出并清洗（布尔/日期/文本）后导入达梦
  - 或使用达梦迁移工具（如 DTS/DM 数据迁移工具）
- 差异核对
  - 行数/主键/唯一约束/外键约束/索引基数核验

## 测试与验证
- 单元测试：覆盖核心 Mapper 的 CRUD、分页、排序、统计
- 冒烟用例：启动应用、登录、核心业务流程走通
- 性能基线：关键查询执行计划比较，热点索引确认
- 灰度：双写或影子库校验（可选）

## 回滚与发布
- 支持配置级回滚至 MySQL（保留旧数据源配置）
- 控制开关：通过环境变量切换数据源与分页方言
- 发布顺序：建库 → 数据迁移 → 应用配置切换 → 验证 → 扩容

## 交付物
- 配置改造：application*.yml 中的数据源与分页方言变更
- 依赖变更：新增达梦 JDBC 驱动依赖
- SQL 改造：达梦版 DDL、初始化数据、修订后的 Mapper SQL
- 文档：差异点清单、测试报告、回滚方案

## 执行节奏（里程碑）
1. 引入驱动与配置切换（可选开关）
2. 分页方言与 DatabaseIdProvider 生效
3. SQL/Mapper 差异改造（函数/分页/UPSERT）
4. DDL 重写与空库验证
5. 数据迁移与核对
6. 全量回归与上线