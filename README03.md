##Spring事务管理
+ 事务特征
    + 提供的统一接口的api接口支持不同的资源
    + 提供申明式事务管理
    + 方便的与Spring框架集成
    + 多个资源的事务管理，同步
+ 事务隔离机制
    + ISOLATION_DEFAULT：使用数据库的隔离机制：默认可重复读
    + ISOLATION_READ_UNCOMMITTED：一个事务可以读取另外一个事务还没有提交的数据（脏读）
    + ISOLATION_READ_COMMITTED：一个事务读取到另外一个事务提交的数据（幻读）
    + ISOLATION_REPEATABLE_READ：两个事务同时运行，一个事务多次读数据不会读取到其他线程提交的数据
    + ISOLATION_SERIALIZABLE：所有的操作排队串行执行，影响性能
+ 事务传播机制
    + PROPAGATION_REQUIRED：调用我的方法有事务就用原来的事务，没有的话就创建新事物
    + PROPAGATION_SUPPORTS：调用我的方法没有事务就不开启事务，有事务的话就用原来的事务
    + PROPAGATION_MANDATORY：调用我的方法必须在事务中，没有就报错
    + PROPAGATION_REQUIRES_NEW：无论原来是否在事务中，都会挂起原来的事务，新建事务执行
    + PROPAGATION_NOT_SUPPORTED：无论调用我的方法是否在事务中，有事务就挂起原来的事务，在否事务下执行完执行在恢复原来的事物
    + PROPAGATION_NEVER：不支持事务，有事务就抛出异常
    + PROPAGATION_NESTED：嵌套事务,对原来的事务创建存盘点，在嵌套事务执行中报错的话就回滚到原来的存盘点
    + PROPAGATION_REQUIRES_NEW PROPAGATION_NESTED区别：
+ Spring JMS Session
    + 特性
        + 通过Session进行事务管理
        + 同Session是thread-bounds
        + 事务上下文：在一个线程中的一个Session
     + 事务类型
        + Session管理的事务-原生事务
        + 外部管理的事务：JmsTransactionManager，JPA
+ Spring分布式事务实现
    
    