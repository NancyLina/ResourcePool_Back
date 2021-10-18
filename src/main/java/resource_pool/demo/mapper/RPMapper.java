package resource_pool.demo.mapper;

import org.apache.ibatis.annotations.*;
import resource_pool.demo.domain.Patent;
import resource_pool.demo.domain.Table_name;
import resource_pool.demo.domain.table_information;

import java.util.HashMap;
import java.util.List;

public interface RPMapper {
    @Update("${sql}")
    void create(String sql);

    @Select("SELECT * FROM table_form")
    List<table_information> take_table();

    @Select("${sql}")
    List<Table_name> search_tablename(String sql);

    @Select("${sql}")
    List<table_information> search_table(String sql);

    @Insert("${sql}")
    void insert(String sql);

    @Delete("${sql}")
    void delete(String sql);

    @Select("${sql}")
    List<HashMap<String,Object>> find(String sql);

    @Select("select name,model,makein,place,buydate from equipment where id>${start} and id<=${end}")
    List<HashMap<String,Object>> get_equipment_data(Integer start,Integer end);
}
