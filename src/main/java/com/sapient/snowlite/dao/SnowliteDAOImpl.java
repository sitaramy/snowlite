package com.sapient.snowlite.dao;

import java.sql.Types;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sapient.snowlite.model.Operation;
import com.sapient.snowlite.model.User;

@Repository
public class SnowliteDAOImpl implements SnowliteDAO{

	private static final Logger log = LoggerFactory.getLogger(SnowliteDAOImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public User getUserDetails(String userId) {
		log.info("Fetching user details for {}", userId);
		String sql = "select user_id as userId, name, email from SN_USER where u.userId = ?";
		Object[] params = new Object[] {userId};
		return jdbcTemplate.queryForObject(sql, params, User.class);
	}

	@Override
	public List<Operation> getOperationsForUser(String userId) {
		log.info("Fetching supported operations for {}", userId);
		String sql = "select op.operation_id as operationId, op.operation_name as operationName, op.operation_url as operationUrl "
				+ "from SN_OPERATION op, SN_USER_DL sudl, SN_OPERATION_DL_ACCESS oda "
				+ "where sudl.user_id = ? and sudl.dl_id = oda.dl_id and op.operation_id = oda.operation_id";
		Object[] params = new Object[] {userId};
		return jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<Operation>(Operation.class));
	}

	@Override
	public List<Integer> getFrequentlyUsedOperations(String userId) {
		log.info("Fetching frequently used operations for {}", userId);
		String sql = "select distinct operation_id from SN_USER_OPERATIONS where user_id=? and access_date > {fn TIMESTAMPADD(SQL_TSI_DAY, -10, CURRENT_TIMESTAMP)}";
		Object[] params = new Object[] {userId};
		int[] type = new int[]{Types.VARCHAR};
		return jdbcTemplate.queryForList(sql, params, type, Integer.class);
	}

}
