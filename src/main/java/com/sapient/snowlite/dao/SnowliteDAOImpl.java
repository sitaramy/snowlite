package com.sapient.snowlite.dao;

import org.springframework.stereotype.Repository;

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

import com.sapient.snowlite.model.Application;
import com.sapient.snowlite.model.BusinessService;
import com.sapient.snowlite.model.DBRelease;
import com.sapient.snowlite.model.DBRequest;
import com.sapient.snowlite.model.Incident;
import com.sapient.snowlite.model.Operation;
import com.sapient.snowlite.model.Request;
import com.sapient.snowlite.model.Team;
import com.sapient.snowlite.model.User;
import com.sapient.snowlite.model.UserOperation;

//@Repository
public class SnowliteDAOImpl implements SnowliteDAO{

	private static final Logger log = LoggerFactory.getLogger(SnowliteDAOImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<User> getUsers() {
		log.info("Fetching users...");
		String sql = "select user_id, name, email, team_id from SN_USER";
		List<User> userList = jdbcTemplate.query(sql, new RowMapper<User>(){

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
		return userList;
	}

	
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
		String sql = "select incident_id as id, short_description as shortDescription, description, requested_for as requestedFor, environment, "
				+ "business_service as  businessService, assignment_group as assignmentGroup, status from SN_INCIDENT where user_id = ? and status = 'Open' order by incident_id desc";
		Object[] params = new Object[] {userId};
		return jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<Incident>(Incident.class));
	}
	
	@Override
	public Incident getIncident(long incidentId) {
		log.info("Fetching incident {}", incidentId);
		Incident incident = null;
		String sql = "select incident_id as id, short_description as shortDescription, description, requested_for as requestedFor, environment, "
				+ "business_service as  businessService, assignment_group as assignmentGroup, status from SN_INCIDENT where incident_id = ?";
		Object[] params = new Object[] {incidentId};
		List<Incident> incidents = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<Incident>(Incident.class));
		if(incidents != null && !incidents.isEmpty()){
			incident = incidents.get(0);
		}
		return incident;
	}
	
	@Override
	public List<Request> getRequests(String userId) {
		log.info("Fetching requests for {}", userId);
		String sql = "select request_id, short_description, description, business_service, assignment_group, requested_resource, approval, approving_manager, status, user_id"
				+ " from SN_REQUEST where user_id = ? and status = 'Open' order by request_id desc";
		Object[] params = new Object[] {userId};
		return processRequestFetch(sql, params);
	}
	
	@Override
	public Request getRequest(long requestId) {
		log.info("Fetching request {}", requestId);
		Request request = null;
		String sql = "select request_id, short_description, description, business_service, assignment_group, requested_resource, approval, approving_manager, status, user_id"
				+ " from SN_REQUEST where request_id = ?";
		Object[] params = new Object[] {requestId};
		List<Request> requests = processRequestFetch(sql, params);
		if(requests != null && !requests.isEmpty()){
			request = requests.get(0);
		}
		return request;
	}

	/**
	 * @param sql
	 * @param params
	 * @return
	 */
	private List<Request> processRequestFetch(String sql, Object[] params) {
		List<Request> requestList =  jdbcTemplate.query(sql, params, new RowMapper<Request>(){
			@Override
			public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				long requestId = rs.getLong("request_id");
				String shortDescription = rs.getString("short_description");
				int businessService = rs.getInt("business_service");
				String description = rs.getString("description");
				String assignmentGroup = rs.getString("assignment_group");
				String requestedResource = rs.getString("requested_resource");
				String approval = rs.getString("approval");
				String approvingManager = rs.getString("approving_manager");
				
				String userId = rs.getString("user_id");
				User user = new User();
				user.setUserId(userId);
				
				String status = rs.getString("status");
				
				Request request = new Request();
				request.setId(requestId);
				request.setDescription(description);
				request.setShortDescription(shortDescription);
				request.setBusinessService(businessService);
				request.setAssignmentGroup(assignmentGroup);
				request.setRequestedResource(requestedResource);
				request.setApprovingManager(approvingManager);
				request.setApproval(approval.equals("Y") ? true : false);
				request.setUser(user);
				request.setStatus(status);
				return request;
				
			}
		});
		return requestList;
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

	@Override
	public List<Application> getApplications() {
		log.info("Fetching all applications...");
		String sql = "select application_id as applicationId, application_name as applicationName, application_description as description from SN_APPLICATION order by application_name";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Application>(Application.class));
	
	}

	@Override
	public List<DBRelease> getDBRelease() {
		log.info("Fetching database releases...");
		String sql = "select release_id, description, user_id, assignment_group, application, status from SN_DB_RELEASE where status LIKE 'Open' order by release_id desc";
		List<DBRelease> dbReleaseList = jdbcTemplate.query(sql, new RowMapper<DBRelease>(){

			@Override
			public DBRelease mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				long releaseId = rs.getLong("release_id");
				String description = rs.getString("description");
				String userId = rs.getString("user_id");
				User user = new User();
				user.setUserId(userId);
				String assignmentGroup = rs.getString("assignment_group");
				int applicationId = rs.getInt("application");
				Application app = new Application();
				app.setApplicationId(applicationId);
				String status = rs.getString("status");
				
				DBRelease dbRelease = new DBRelease();
				dbRelease.setApplication(app);
				dbRelease.setUserId(user);
				dbRelease.setAssignmentGroup(assignmentGroup);
				dbRelease.setDescription(description);
				dbRelease.setReleaseId(releaseId);
				dbRelease.setStatus(status);
				return dbRelease;
				
			}
		});
		
		return dbReleaseList;
	}

	@Override
	public List<DBRequest> getDBRequest(String userId) {
		log.info("Fetching DB request for {}", userId);
		String sql = "select dbr_id, environment, release_id, description,  user_id, assignment_group, status from SN_DB_REQUEST where user_Id = ? order by dbr_id desc";
		Object[] params = new Object[] {userId};
		List<DBRequest> dbRequestList = jdbcTemplate.query(sql, params, new RowMapper<DBRequest>(){

			@Override
			public DBRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				long dbrId = rs.getLong("dbr_id");
				String environment = rs.getString("environment");
				long releaseId = rs.getLong("release_id");
				DBRelease rel = new DBRelease();
				rel.setReleaseId(releaseId);
				String description = rs.getString("description");
				String assignmentGroup = rs.getString("assignment_group");
				String userId = rs.getString("user_id");
				User user = new User();
				user.setUserId(userId);
				String status = rs.getString("status");
				
				DBRequest dbRequest = new DBRequest();
				dbRequest.setDbrId(dbrId);
				dbRequest.setAssignmentGroup(assignmentGroup);
				dbRequest.setDescription(description);
				dbRequest.setEnvironment(environment);
				dbRequest.setRelease(rel);
				dbRequest.setUser(user);
				dbRequest.setStatus(status);
				return dbRequest;
				
			}
		});
		return dbRequestList;
	}

	@Override
	public void saveDBRelease(DBRelease dbRelease) {
		log.info("Saving DB Release...");
		String sql = "INSERT INTO SN_DB_RELEASE(description, user_id, assignment_group, application, status) VALUES (?, ?, ?, ?, ?)";
		
		Object[] params = new Object[] {
									dbRelease.getDescription(),
									dbRelease.getUserId().getUserId(),
									dbRelease.getAssignmentGroup(),
									dbRelease.getApplication().getApplicationId(),
									dbRelease.getStatus()
									};
		jdbcTemplate.update(sql, params);
		
	}

	@Override
	public void saveDBRequest(DBRequest dbRequest) {
		log.info("Saving DB Request...");
		String sql = "INSERT INTO SN_DB_REQUEST(environment, release_id, description,  user_id, assignment_group, status) VALUES (?, ?, ?, ?, ?, ?)";
		
		Object[] params = new Object[] {
									dbRequest.getEnvironment(),
									dbRequest.getRelease().getReleaseId(),
									dbRequest.getDescription(),
									dbRequest.getUser().getUserId(),
									dbRequest.getAssignmentGroup(),
									dbRequest.getStatus()
									};
		jdbcTemplate.update(sql, params);
	}

	@Override
	public List<Request> getPendingRequests(String type, String userId) {
		log.info("Fetching pending approval requests for {}", userId);
		String sql = "select sr.request_id, sr.short_description, su.name from SN_REQUEST sr, SN_USER su "
				+ " where sr.approving_manager = ? and sr.user_id = su.user_id and sr.status = 'Open' and sr.approval = 'N' order by sr.request_id desc";
		Object[] params = new Object[] {userId};
		
		List<Request> requestList =  jdbcTemplate.query(sql, params, new RowMapper<Request>(){
			
			@Override
			public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
				long requestId = rs.getLong("request_id");
				String shortDescription = rs.getString("short_description");
				User user = new User();
				user.setName(rs.getString("name"));

				Request request = new Request();
				request.setId(requestId);
				request.setShortDescription(shortDescription);
				request.setUser(user);
				return request;
			}
		});
		
		return requestList;
	}

	@Override
	public int updateApprovalStatus(String requestid, String status) {
		String sql = "update SN_REQUEST set approval = ?, status = ? where request_id = ? ";
		Object[] params = new Object[] {status.trim(), "Y".equals(status.trim())? "Approved" : "Rejected", requestid};
		return jdbcTemplate.update(sql, params);
	}
}
