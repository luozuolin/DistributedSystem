##分布式系统的分布式事务实现
+   事务同步
+   重试和幂等性
+   Try-Confirm/Cancle
+   根据微服务系统的架构具体路况具体分析
+   分布式事务的实现没有一个统一的框架和规范
+   分布式事务的实现跟微服务系统的架构设计有关
+   微服务架构的设计模式-》分布式事务实现方式的总结
##微服务设计模式
+   聚合模式
+   代理模式
+   服务链模式
+   数据共享模式
+   消息驱动模式
+   事件溯源模式
##分布式事务实现
+   保证高可用：网络，分布式部署
+   保证事务同步：同步多个数据源的事务
+   保证幂等性：通过重试解决大部分错误
+   合理设计业务流程：条件检查，预留资源，业务操作，完成资源（TCC）
##Spring事务机制
+   服务单元：访问多个数据源
+   使用JTA实现强一致性，或其他同步机制实现最终一致性
    +   都是数据库：链式事务，
+   根据数据源的类型，使用适当的事务同步机制
+   使用重试超时定时检查等方式处理同步错误
+   使用幂等性，UUID，分布式锁等
##合理的设计业务
+   一致性要求的严格要求
+   同步异步处理
+   合理的拆分复杂的业务
+   合理的设计实现代码：数据检查，条件检查，预留资源，实现业务