package generator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.PluginConfiguration;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.google.common.base.CaseFormat;
import com.wyd.zc.db.core.Mapper;

/**
 * @description 代码生成器，根据数据表名称生成对应的Model、Mapper、Service、Controller简化开发。
 * @author 王玉栋
 * @date 2018-03-24 23:00:02
 * @since 1.0
 */
public class CodeGenerator {

	public static void main(String[] args) {
		genCode("zc_user_users", "ZcUserUsers");
	}

	/**
	 * JDBC配置，请修改为你项目的实际配置
	 */
	private static final String JDBC_URL = "jdbc:mysql://47.104.24.168:3306/zc?useSSL=false&useUnicode=true&characterEncoding=gbk&autoReconnect=true&failOverReadOnly=false";
	private static final String JDBC_USERNAME = "root";
	private static final String JDBC_PASSWORD = "root";
	private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

	/**
	 * 项目在硬盘上的基础路径
	 */
	private static final String PROJECT_PATH = System.getProperty("user.dir");

	/**
	 * java文件路径
	 */
	private static final String JAVA_PATH = "/src/main/java";
	/**
	 * 资源文件路径
	 */
	private static final String RESOURCES_PATH = "/src/main/resources";

	/**
	 * 通过数据表名称生成代码，Model 名称通过解析数据表名称获得，下划线转大驼峰的形式。 如输入表名称 "t_user_detail" 将生成
	 * TUserDetail、TUserDetailMapper、TUserDetailService ...
	 *
	 * @param tableNames
	 *            数据表名称...
	 */
	public static void genCode(String... tableNames) {
		for (String tableName : tableNames) {
			genCode(tableName, null);
		}
	}

	/**
	 * 通过数据表名称，和自定义的 Model 名称生成代码 如输入表名称 "t_user_detail" 和自定义的 Model 名称 "User" 将生成
	 * User、UserMapper、UserService ...
	 *
	 * @param tableName
	 *            数据表名称
	 * @param modelName
	 *            自定义的 Model 名称
	 */
	public static void genCode(String tableName, String modelName) {
		genModelAndMapper(tableName, modelName);
	}

	public static void genModelAndMapper(String tableName, String modelName) {
		Context context = new Context(ModelType.FLAT);
		context.setId("Potato");
		context.setTargetRuntime("MyBatis3Simple");
		context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
		context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");

		JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
		jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
		jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
		jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
		jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
		context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

		PluginConfiguration pluginConfiguration = new PluginConfiguration();
		pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
		pluginConfiguration.addProperty("mappers", Mapper.class.getName());
		context.addPluginConfiguration(pluginConfiguration);

		JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
		javaModelGeneratorConfiguration.setTargetProject(PROJECT_PATH + JAVA_PATH);
		javaModelGeneratorConfiguration.setTargetPackage("com.wyd.zc.db.model");
		context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

		SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
		sqlMapGeneratorConfiguration.setTargetProject(PROJECT_PATH + RESOURCES_PATH);
		sqlMapGeneratorConfiguration.setTargetPackage("mappers");
		context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

		JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
		javaClientGeneratorConfiguration.setTargetProject(PROJECT_PATH + JAVA_PATH);
		javaClientGeneratorConfiguration.setTargetPackage("com.wyd.zc.db.mapper");
		javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
		context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

		TableConfiguration tableConfiguration = new TableConfiguration(context);
		tableConfiguration.setTableName(tableName);
		tableConfiguration.setDomainObjectName(modelName);
		context.addTableConfiguration(tableConfiguration);

		List<String> warnings;
		MyBatisGenerator generator;
		try {
			Configuration config = new Configuration();
			config.addContext(context);
			config.validate();

			boolean overwrite = true;
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			warnings = new ArrayList<String>();
			generator = new MyBatisGenerator(config, callback, warnings);
			generator.generate(null);
		} catch (Exception e) {
			throw new RuntimeException("生成Model和Mapper失败", e);
		}

		if (generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
			throw new RuntimeException("生成Model和Mapper失败：" + warnings);
		}
		if (StringUtils.isEmpty(modelName)) {
			modelName = tableNameConvertUpperCamel(tableName);
		}
		System.out.println(modelName + ".java 生成成功");
		System.out.println(modelName + "Mapper.java 生成成功");
		System.out.println(modelName + "Mapper.xml 生成成功");
	}

	private static String tableNameConvertUpperCamel(String tableName) {
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());
	}

}
