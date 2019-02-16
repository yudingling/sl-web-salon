package com.sl.web.mybatis;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.mapping.MappedStatement;

import tk.mybatis.mapper.MapperException;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

public class ExtMapperProvider extends MapperTemplate {
	private static final Map<String, Boolean> idcheckMap = new ConcurrentHashMap<>();

	public ExtMapperProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}
	
	private static void checkIdField(Class<?> entityClass){
		if(!idcheckMap.containsKey(entityClass.getName())){
			boolean hasId = false;
			Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
			for (EntityColumn column : columnList) {
				if(column.isId()){
					hasId = true;
					break;
				}
			}
			
			idcheckMap.put(entityClass.getName(), hasId);
			
			if(!hasId){
				throw new MapperException(String.format("id field must set for entity [%s]", entityClass.getName()));
			}
		}
	}
	
	public String insertList(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        
        checkIdField(entityClass);
        
        StringBuilder sql = new StringBuilder();
        
        sql.append(SqlHelper.insertIntoTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.insertColumns(entityClass, false, false, false));
        sql.append(" VALUES ");
        sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\" >");
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        for (EntityColumn column : columnList) {
            if (column.isInsertable()) {
                sql.append(column.getColumnHolder("record") + ",");
            }
        }
        
        sql.append("</trim></foreach>");
        return sql.toString();
    }
	
	public String insertUpdateList(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        
        sql.append(SqlHelper.insertIntoTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.insertColumns(entityClass, false, false, false));
        sql.append(" VALUES ");
        sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\" >");
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        for (EntityColumn column : columnList) {
            if (column.isInsertable()) {
                sql.append(column.getColumnHolder("record") + ",");
            }
        }
        
        sql.append("</trim>");
        sql.append("</foreach>");
        
        this.appendDuplicateUpdateSql(sql, columnList);
        
        return sql.toString();
    }
	
	public String insertUpdate(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        
        sql.append(SqlHelper.insertIntoTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.insertColumns(entityClass, false, false, false));
        sql.append("<trim prefix=\"VALUES(\" suffix=\")\" suffixOverrides=\",\">");
        
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        for (EntityColumn column : columnList) {
            if (column.isInsertable()) {
            	sql.append(column.getColumnHolder("record", null, ","));
            }
        }
        
        sql.append("</trim>");
        
        this.appendDuplicateUpdateSql(sql, columnList);
        
        return sql.toString();
    }
	
	
	
	public String updateBatchById(MappedStatement ms){
		final Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        
        EntityColumn idField = null;
        
        for (EntityColumn column : columnList) {
        	if(column.isId()){
            	idField = column;
            	break;
        	}
        }
        
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        
        sql.append("<trim prefix=\"set\" suffixOverrides=\",\">");
        
        sql.append("<foreach collection=\"updateFields\" item=\"field\" >");
        
        //can not use "<trim prefix=\"${field} = case\" suffix=\"end,\">", the parameter '${field}' will not replace in trim statement.
        sql.append(" ${field} = case ");
        
        sql.append("<foreach collection=\"list\" item=\"e\">");
        
        sql.append(String.format(" when %s=%s then #{e.${field}} ", idField.getColumn(), idField.getColumnHolder("e")));
        
        sql.append("</foreach>");
        
        sql.append(" end,");
        
        sql.append("</foreach>");
        
        sql.append("</trim>");
        
        sql.append(String.format(" where %s in ", idField.getColumn()));
        sql.append("<foreach collection=\"list\" item=\"e\" separator=\",\" open=\"(\" close=\")\">");
        sql.append(idField.getColumnHolder("e"));
        sql.append("</foreach>");
        
        return sql.toString();
	}
	
	public String updateBatch(MappedStatement ms){
		final Class<?> entityClass = getEntityClass(ms);
		
        StringBuilder sql = new StringBuilder();
        
        sql.append("<foreach collection=\"list\" item=\"record\" close=\";\" separator=\";\" >");
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(" set ");
        
        sql.append("<foreach collection=\"updateFields\" item=\"field\" separator=\",\" >");
        sql.append(" ${field}=#{record.${field}} ");
        sql.append("</foreach>");
        
        sql.append(" where ");
        
        sql.append("<foreach collection=\"whereFields\" item=\"field\" separator=\"and\" >");
        sql.append(" ${field}=#{record.${field}} ");
        sql.append("</foreach>");
        
        sql.append("</foreach>");
        
        return sql.toString();
	}
	
	private void appendDuplicateUpdateSql(StringBuilder sql, Set<EntityColumn> columnList){
		StringBuilder sb = new StringBuilder();
        
        for (EntityColumn column : columnList) {
            if (!column.isId() && column.isInsertable() && column.isUpdatable()) {
            	sb.append(String.format("%s=VALUES(%s),", column.getColumn(), column.getColumn()));
            }
        }
        
        if(sb.length() > 0 && sb.charAt(sb.length() - 1) == ','){
        	sb.deleteCharAt(sb.length() - 1);
        }
        
        if(sb.length() > 0){
        	sql.append(" ON DUPLICATE KEY UPDATE ");
        	sql.append(sb);
        }
	}
}
