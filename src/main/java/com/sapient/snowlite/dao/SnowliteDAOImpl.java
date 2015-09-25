package com.sapient.snowlite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sapient.snowlite.model.BusinessService;
import com.sapient.snowlite.model.Incident;
import com.sapient.snowlite.model.Operation;
import com.sapient.snowlite.model.Request;
import com.sapient.snowlite.model.Team;
import com.sapient.snowlite.model.User;
import com.sapient.snowlite.model.UserOperation;

@Repository
public class SnowliteDAOImpl implements SnowliteDAO{

	private static final Logger log = LoggerFactory.getLogger(SnowliteDAOImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public User getUserDetails(String userId) {
		log.info("Fetching user details for {}", userId);
		String sql = "select user_id, name, email, team_id from SN_USER where user_Id = ?";
		Object[] params = new Object[] {userId};
		List<User> userList = jdbcTemplate.query(sql, params, new RowMapper<User>(){

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				String userId = rs.getString("user_id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String teamId = rs.getString("team_id");
				
				Team team = new Team();
				team.setTeamId(teamId);
				
				User user = new User();
				user.setUserId(userId);
				user.setName(name);
				user.setEmail(email);
				user.setTeam(team);
				return user;
				
			}
		});
		
		User user = userList.get(0);
		
		//Fetch team details as well
		String teamSql = "select t.team_id, t.team_name, t.assignment_group, t.approval_manager, u.name, u.email, t.team_dl, t.team_dev_dl, t.team_qa_dl, t.db_assignment_group"
				+ " from sn_team t, sn_user u where t.team_id = ? and u.user_id = t.approval_manager";
		
		Object[] teamParams = new Object[] {user.getTeam().getTeamId()};
		List<Team> teamList = jdbcTemplate.query(teamSql, teamParams, new RowMapper<Team>(){

			@Override
			public Team mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				String teamId = rs.getString("team_id");
				String teamName = rs.getString("team_name");
				String assignmentGroup = rs.getString("assignment_group");
				
				String approvalManager = rs.getString("approval_manager");
				String name = rs.getString("name");
				String email = rs.getString("email");
				
				String teamDL = rs.getString("team_dl");
				String teamDevDL = rs.getString("team_dev_dl");
				String teamQADL = rs.getString("team_qa_dl");
				String databaseAssignmentGroup = rs.getString("db_assignment_group");
				
				User approvalMgr = new User();
				approvalMgr.setUserId(approvalManager);
				approvalMgr.setName(name);
				approvalMgr.setEmail(email);
				
				Team team = new Team();
				team.setTeamId(teamId);
				team.setTeamName(teamName);
				team.setApprovalManager(approvalMgr);
				team.setTeamAssignmentGroup(assignmentGroup);
				team.setTeamDevDL(teamDevDL);
				team.setTeamQADL(teamQADL);
				team.setTeamDL(teamDL);
				team.setDatabaseAssignmentGroup(databaseAssignmentGroup);
				
				return team;
			}
		});
		
		user.setTeam(teamList.get(0));
		
		return user;
	}

	@Override
	public List<Operation> getOperationsForUser(String userId) {
		log.info("Fetching supported operations for {}", userId);
		String sql = "select op.operation_id as operationId, op.operation_name as operationName, op.operation_url as operationUrl, op.approval_required as approvalRequired "
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

	@Override
	public List<BusinessService> getBusinessServices() {
		log.info("Fetching business services");
		String sql = "select business_svc_id as serviceId, business_service as serviceName, assignment_group as assignmentGroup from SN_BUSINESS_SVC";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<BusinessService>(BusinessService.class));
	}
	
	@Override
	public List<Incident> getIncidents(String userId) {
		log.info("Fetching incidents for {}", userId);
		String sql = "select incident_id as incidentId, short_description as shortDescription, description, requested_for as requestedFor, environment, "
				+ "business_service as  businessService, assignment_group as assignmentGroup, status from SN_INCIDENT where user_id = ?";
		Object[] params = new Object[] {userId};
		return jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<Incident>(Incident.class));
	}

	
	@Override
	public void saveIncident(Incident incident) {
		log.info("Saving incident...");
		String sql = "insert into sn_incident(short_description, description, requested_for, environment, business_service, assignment_group, user_id, status) "
					+ "values (?, ?, ?, ?, ?, ?, ?, ?)";
		
		Object[] params = new Object[] {
									incident.getShortDescription(),
									incident.getDescription(),
									incident.getRequestedFor(),
									incident.getEnvironment(),
									incident.getBusinessService(),
									incident.getAssignmentGroup(),
									incident.getUser().getUserId(),
									incident.getStatus()
									};
		jdbcTemplate.update(sql, params);
	}

	@Override
	public void saveRequest(Request request) {
		log.info("Saving Request...");
		String sql = "insert into sn_request(short_description, description, user_id, business_service, assignment_group, requested_resource, approval, approving_manager, status) "
					+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		Object[] params = new Object[] {
									request.getShortDescription(),
									request.getDescription(),
									request.getUser().getUserId(),
									request.getBusinessService(),
									request.getAssignmentGroup(),
									request.getRequestedResource(),
									request.isApproval() ? "Y" : "N",
									request.getApprovingManager(),
									request.getStatus()
									};
		jdbcTemplate.update(sql, params);
		
	}

	@Override
	public void saveUserOperation(UserOperation userOperation) {
		log.info("Saving user operation...");
		String sql = "insert into SN_USER_OPERATIONS(operation_id, user_id, access_date)  "
					+ "values (?, ?, {fn TIMESTAMPADD(SQL_TSI_DAY, -0, CURRENT_TIMESTAMP)})";
		
		Object[] params = new Object[] {
									userOperation.getOperationId(),
									userOperation.getUserId()
									};
		jdbcTemplate.update(sql, params);
		
	}

}
