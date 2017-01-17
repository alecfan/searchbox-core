package com.lovi.searchbox.query;

import com.lovi.searchbox.common.lua.CommonFunctions;
import com.lovi.searchbox.config.ConfigKeys;
import com.lovi.searchbox.query.sort.Sort;
import com.lovi.searchbox.service.search.Page;

/**
 * 
 * @author Tharanga Thennakoon
 * @see <a href="https://github.com/loviworld/searchbox-core">https://github.com/loviworld/searchbox-core</a>
 *
 */
public class Query {

	private Criteria criteria;

	private Page page;

	public Query(Criteria criteria) {
		this.criteria = criteria;
		this.page = new Page();
	}

	public Criteria getCriteria() {
		return criteria;
	}

	public String getQueryString() throws Exception {
		StringBuilder stmtStringBuilder = new StringBuilder();

		// append common lua functions
		stmtStringBuilder.append(CommonFunctions.find_script());
		stmtStringBuilder.append("\n");
		stmtStringBuilder.append(CommonFunctions.intersection_script());
		stmtStringBuilder.append("\n");
		stmtStringBuilder.append(CommonFunctions.union_script());
		stmtStringBuilder.append("\n");
		stmtStringBuilder.append(CommonFunctions.difference_script());
		stmtStringBuilder.append("\n");
		stmtStringBuilder.append(CommonFunctions.symmetric_script());
		stmtStringBuilder.append("\n");
		stmtStringBuilder.append(CommonFunctions.is_empty());
		stmtStringBuilder.append("\n");
		stmtStringBuilder.append(CommonFunctions.sort_set_script());
		stmtStringBuilder.append("\n");
		stmtStringBuilder.append(CommonFunctions.string_split_script());
		stmtStringBuilder.append("\n");
		stmtStringBuilder.append(CommonFunctions.string_split_to_words_script());
		stmtStringBuilder.append("\n");
		stmtStringBuilder.append(CommonFunctions.string_remove_spaces_script());
		stmtStringBuilder.append("\n");
		stmtStringBuilder.append(CommonFunctions.json_get_value_from_json_script());
		stmtStringBuilder.append("\n");
		stmtStringBuilder.append(CommonFunctions.redis_get_models_eq_script());
		stmtStringBuilder.append("\n");
		stmtStringBuilder.append(CommonFunctions.redis_get_models_by_ids_script());
		stmtStringBuilder.append("\n");
		stmtStringBuilder.append(CommonFunctions.redis_get_models_by_pattern_script());
		stmtStringBuilder.append("\n");
		stmtStringBuilder.append(CommonFunctions.redis_get_models_by_range_script());
		stmtStringBuilder.append("\n");
		stmtStringBuilder.append(CommonFunctions.redis_zrangebylex_script());
		stmtStringBuilder.append("\n");
		stmtStringBuilder.append(CommonFunctions.redis_get_models_ne_script());
		stmtStringBuilder.append("\n");
		// append common lua functions

		// setting app name
		stmtStringBuilder.append("local app_name = ARGV[1]");
		stmtStringBuilder.append("\n");
		// setting app name

		// setting model name
		stmtStringBuilder.append("local model_name = ARGV[2]");
		stmtStringBuilder.append("\n");
		// setting model name

		// setting page length
		stmtStringBuilder.append("local page_length = tonumber(ARGV[3])");
		stmtStringBuilder.append("\n");
		// setting page length

		// setting page length
		stmtStringBuilder.append("local page_no = tonumber(ARGV[4])");
		stmtStringBuilder.append("\n");
		// setting page length

		stmtStringBuilder.append("\n");
		
		// criteria script
		stmtStringBuilder.append(criteria.getScript());
		stmtStringBuilder.append("\n");
		// criteria script
		
		// setting last parameter
		stmtStringBuilder.append("local _parm = " + criteria.getParameterName());
		stmtStringBuilder.append("\n");
		// setting last parameter

		stmtStringBuilder.append("\n");
		
		// get models by ids
		// mapKey -> <app_name>:table:<model_name>
		// get_models_by_ids(mapKey, modelIds, pageLength, pageNo)
		String getModelsStmt = String.format(
				"return get_models_by_ids(app_name .. ':%1$s:' .. model_name, _parm, page_length, page_no)",
				ConfigKeys.table_perfix);

		stmtStringBuilder.append(getModelsStmt);
		stmtStringBuilder.append("\n");
		// get models by ids
		
		return stmtStringBuilder.toString();

	}

	public int getPageLength() {
		return page.getPageLength();
	}

	public void setPageLength(int pageLength) {
		this.page.setPageLength(pageLength);
	}

	public int getPageNo() {
		return page.getPageNo();
	}

	public void setPageNo(int pageNo) {
		this.page.setPageNo(pageNo);
	}
	
	/**
	 * ---Sort---
	 */
	@SuppressWarnings("rawtypes")
	private Sort sort;
	
	public <T> Query sort(Sort<T> sort){
		this.sort = sort;
		return this;
	}
	
	@SuppressWarnings("rawtypes")
	public Sort getSort(){
		return this.sort;
	}
	
	/**
	 * ---Sort---
	 */
	
}