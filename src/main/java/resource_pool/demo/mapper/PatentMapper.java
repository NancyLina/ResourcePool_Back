package resource_pool.demo.mapper;

import org.apache.ibatis.annotations.*;
import resource_pool.demo.domain.Patent;
import resource_pool.demo.domain.table;

import java.util.LinkedHashMap;
import java.util.List;

public interface PatentMapper {

//    @Insert("INSERT INTO user(name,phone,create_time,age) VALUES(#{name}, #{phone}, #{createTime},#{age})")
//    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")   //keyProperty java对象的属性；keyColumn表示数据库的字段
//    int insert(User user);

    @Delete("DROP TABLE ${tableName}")
    void delete(String tableName);

    @Update("${sql}")
    void create(String sql);

    @Update("CREATE TABLE ${tableName}(name varchar(10))")
    void update(String tableName);

    @Select("SELECT ID,FNUM,CPNUM,VID,LANG FROM patent_infor")
    List<Patent> findall();


    @Select("SELECT ID,FNUM,CPNUM,VID,LANG FROM patent_infor WHERE ID = #{id}")
    List<Patent> findById(String id);

    @Select("SELECT ID,FNUM,CPNUM,VID,LANG FROM patent_infor WHERE ID = ${id}")
    List<Patent> Id_test(String id);


    @Insert("INSERT INTO new_table(record,name) VALUES(#{record},#{name})")
    void insert(@Param("record")String record,@Param("name")String name);

    @Select("SELECT * FROM new_table")
    List<table> Choose();

    @Delete("delete from new_table")
    void clear_table();

    @Select("${sql}")
    List<Patent> findbysth(String sql);

    @Select("${sql}")
    LinkedHashMap findbygroup(String sql);
}