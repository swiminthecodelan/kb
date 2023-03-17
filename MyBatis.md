# MyBatis

## MyBatis 特性

- MyBatis 是支持定制化 SQL、存储过程以及高级映射的持久层框架
    - hibernate 只能使用其生成的 sql，不易维护。
    - 简单映射：数据库中数据和实体属性的映射
    - 高级映射：一对多关系的映射

- MyBatis 避免了 JDBC 代码和手动设置参数以及获取结果集
    - 手动设置参数：向sql语句拼接参数，MyBatis 提供了两种方式设置sql语句参数。

- MyBatis可以收用简单的XML或注解用于配置和原始映，将接口和Java的POJO映射成数据库中的记录。
    - SQL 语句写在 XML
    - 以注解的方式写在接口里面
    - 面向接口编程：调用接口时直接对应 SQL 语句，不需要实现类。

- hibernate 是全自动的，自动生成表、sql 语句。MyBatis 是半自动的 ORM 框架。

## 配置 MyBatis

- 核心配置文件：连接数据库的信息和 MyBatis 全局配置信息
    - <properties resource="jdbc.properties" /> 在核心配置文件中引入属性 properties 文件，将 <property> 内容独立出来。
    - <typeAliases></typeAliases> 设置类型别名
        - <typeAlias type="com.mybatis.pojo.User" alias="User"/>，alias 默认不区分大小写，alias 可不写，默认为类名。
        - <package name="com.mybatis.pojo.User"/> 将包下所有类型设置默认的类型别名
    - <environments> 可配置多个连接数据库的环境
        - default: 设置默认使用的环境的 id
        - <environment>：配置某个具体的环境
            - 属性：
                id：表示连接数据库的环境的唯一标识，不能重复
                - <transactionManager> 设置事务管理的方式
                    - 属性：
                        - type="JDBC|MANAGED"
                            - JDBC: 表示当前环境中，执行时，使用的是JDBC中原生的事务管理方式，事物的提交或回滚需要手动处理。
                            - MANAGED：被管理，例如Spring
                - <dataSource type="POOLED">
                    - type: 设置数据源的类型。
                        - POOLED：标识使用数据库连接池缓存数据库连接
                        - UNPOOLED：标识不使用数据库连接池
                        - JNDI: 标识使用上下文中的数据源。使用 Spring 整合时，数据源取自 Spring上下文中，因此不用配置。
                    - <property>：配置驱动，数据库地址，用户名，密码
    - <mappers>
        <mapper resource="mappers/UserMapper.xml"/>
        以包为单位引入映射文件
        要求：
            1.mapper 接口所在的包要和映射文件所在的包一致
            2.mapper 接口要和映射文件的名字一致

        <package name="com.mybatis.mapper"/>
    </mappers>

- 映射文件：sql 语句。
    - mapper namespace 与 mapper 接口全类名一致。
    - sql语句id与mapper接口中的方法名一致

- MyBatis 获取参数值的方式
    - ${} 本质字符串拼接dddad
    - #{} 本质占位符赋值
    - mybatis 帮忙构造多参数map
    - 自己传入 map
    - mapper 接口方法的参数时实体类类型
    - 实体类通过get方法的名字，不是属性，存在只有get方法，没有属性的情况。mybatis 不支持函数重载，无法识别相同的方法名不同的参数。例如在mapper 里声明两个方法，相同的 insertUser，xml 中使用同一个 insert id，并不能根据参数进行方法匹配。
    - @Param

git clone https://github.com/zsh-users/zsh-autosuggestions /home/tyler/.oh-my-zsh/custom/plugins/zsh-autosuggestions

git clone https://github.com/jirutka/zsh-shift-select.git /home/tyler/.oh-my-zsh/custom/plugins/zsh-shift-select
