package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.google.common.base.CaseFormat;

import freemarker.template.TemplateExceptionHandler;

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
	 * 项目在硬盘上的基础路径
	 */
	private static final String PROJECT_PATH = System.getProperty("user.dir");
	/**
	 * 模板位置
	 */
	private static final String TEMPLATE_FILE_PATH = PROJECT_PATH + "/src/test/resources/generator/template";

	/**
	 * java文件路径
	 */
	private static final String JAVA_PATH = "/src/main/java";

	/**
	 * 生成的Service存放路径
	 */
	private static final String PACKAGE_PATH_SERVICE = packageConvertPath("com.wyd.zc.demo.service");
	/**
	 * 生成的Service实现存放路径
	 */
	private static final String PACKAGE_PATH_SERVICE_IMPL = packageConvertPath("com.wyd.zc.demo.service.impl");
	/**
	 * 生成的Controller存放路径
	 */
	private static final String PACKAGE_PATH_CONTROLLER = packageConvertPath("com.wyd.zc.demo.web");

	/**
	 * @author
	 */
	private static final String AUTHOR = "王玉栋";
	/**
	 * @date
	 */
	private static final String DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	/**
	 * since
	 */
	private static final String SINCE = "1.0";

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
		genService(tableName, modelName);
		genController(tableName, modelName);
	}

	public static void genController(String tableName, String modelName) {
		try {
			freemarker.template.Configuration cfg = getConfiguration();

			Map<String, Object> data = new HashMap<String, Object>(10);
			data.put("author", AUTHOR);
			data.put("date", DATE);
			data.put("since", SINCE);
			String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
			data.put("baseRequestMapping", modelNameConvertMappingPath(modelNameUpperCamel));
			data.put("modelNameUpperCamel", modelNameUpperCamel);
			data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));

			File file = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CONTROLLER + modelNameUpperCamel + "Controller.java");
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			cfg.getTemplate("controller-restful.ftl").process(data, new FileWriter(file));

			System.out.println(modelNameUpperCamel + "Controller.java 生成成功");
		} catch (Exception e) {
			throw new RuntimeException("生成Controller失败", e);
		}

	}

	public static void genService(String tableName, String modelName) {
		try {
			freemarker.template.Configuration cfg = getConfiguration();

			Map<String, Object> data = new HashMap<String, Object>(10);
			data.put("author", AUTHOR);
			data.put("since", SINCE);
			data.put("date", DATE);
			String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
			data.put("modelNameUpperCamel", modelNameUpperCamel);
			data.put("modelNameLowerCamel", tableNameConvertLowerCamel(tableName));

			File file = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE + modelNameUpperCamel + "Service.java");
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			cfg.getTemplate("service.ftl").process(data, new FileWriter(file));
			System.out.println(modelNameUpperCamel + "Service.java 生成成功");

			File file1 = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE_IMPL + modelNameUpperCamel + "ServiceImpl.java");
			if (!file1.getParentFile().exists()) {
				file1.getParentFile().mkdirs();
			}
			cfg.getTemplate("service-impl.ftl").process(data, new FileWriter(file1));
			System.out.println(modelNameUpperCamel + "ServiceImpl.java 生成成功");
		} catch (Exception e) {
			throw new RuntimeException("生成Service失败", e);
		}
	}

	private static freemarker.template.Configuration getConfiguration() throws IOException {
		freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
		cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_PATH));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		return cfg;
	}

	private static String modelNameConvertMappingPath(String modelName) {
		String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, modelName);
		return tableNameConvertMappingPath(tableName);
	}

	private static String packageConvertPath(String packageName) {
		return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
	}

	private static String tableNameConvertLowerCamel(String tableName) {
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName.toLowerCase());
	}

	private static String tableNameConvertMappingPath(String tableName) {
		// 兼容使用大写的表名
		tableName = tableName.toLowerCase();
		return "/" + (tableName.contains("_") ? tableName.replaceAll("_", "/") : tableName);
	}

	private static String tableNameConvertUpperCamel(String tableName) {
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());
	}

}
