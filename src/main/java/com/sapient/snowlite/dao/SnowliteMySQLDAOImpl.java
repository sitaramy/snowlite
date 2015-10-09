package com.sapient.snowlite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sapient.snowlite.model.Application;
import com.sapient.snowlite.model.BusinessService;
import com.sapient.snowlite.model.Change;
import com.sapient.snowlite.model.DBRelease;
import com.sapient.snowlite.model.DBRequest;
import com.sapient.snowlite.model.Incident;
import com.sapient.snowlite.model.Operation;
import com.sapient.snowlite.model.Request;
import com.sapient.snowlite.model.Team;
import com.sapient.snowlite.model.User;
import com.sapient.snowlite.model.UserOperation;

@Repository
public class SnowliteMySQLDAOImpl implements SnowliteDAO {

	private static final Logger log = LoggerFactory
			.getLogger(SnowliteMySQLDAOImpl.class);

	@Autowired
	private JdbcTemplate mySQLJdbcTemplate;

	@Override
	public List<User> getUsers() {
		log.info("Fetching users...");
		String sql = "select id, name, email, team from user";
		List<User> userList = mySQLJdbcTemplate.query(sql,
				new RowMapper<User>() {

					@Override
					public User mapRow(ResultSet rs, int rowNum)
							throws SQLException {

						String userId = rs.getString("id");
						String name = rs.getString("name");
						String email = rs.getString("email");
						String teamName = rs.getString("team");

						Team team = new Team();
						team.setTeamId(teamName);

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
		String sql = "select id, name, email, team from user where id = ?";
		Object[] params = new Object[] { userId };
		List<User> userList = mySQLJdbcTemplate.query(sql, params,
				new RowMapper<User>() {

					@Override
					public User mapRow(ResultSet rs, int rowNum)
							throws SQLException {

						String userId = rs.getString("id");
						String name = rs.getString("name");
						String email = rs.getString("email");

						String teamName = rs.getString("team");

						Team team = new Team();
						team.setTeamId(teamName);

						User user = new User();
						user.setUserId(userId);
						user.setName(name);
						user.setEmail(email);
						user.setTeam(team);
						return user;

					}
				});

		User user = userList.get(0);

		String teamSql = "select t.name as team_id, t.team_detail as team_name, ag.name as assignment_group_name, t.assignment_group, "
				+ "t.approval_manager, u.name, u.email, dl1.name as team_dl_name, dl2.name as team_dev_dl_name, dl3.name as team_qa_dl_name, "
				+ "t.team_dl, t.team_dev_dl, t.team_qa_dl,  ag1.name as db_assignment_group_name, t.db_assignment_group from team t, user u, "
				+ "assignment_group ag, assignment_group ag1, dl_list dl1,  dl_list dl2, dl_list dl3 where t.name = ? and t.assignment_group = ag.id "
				+ "and t.db_assignment_group = ag1.id and t.team_dl = dl1.id and t.team_dev_dl = dl2.id  and t.team_qa_dl = dl3.id  and u.id = t.approval_manager";

		Object[] teamParams = new Object[] { user.getTeam().getTeamId() };
		List<Team> teamList = mySQLJdbcTemplate.query(teamSql, teamParams,
				new RowMapper<Team>() {

					@Override
					public Team mapRow(ResultSet rs, int rowNum)
							throws SQLException {

						String teamId = rs.getString("team_id");
						String teamName = rs.getString("team_name");
						String assignmentGroup = rs
								.getString("assignment_group_name");

						String approvalManager = rs
								.getString("approval_manager");
						String name = rs.getString("name");
						String email = rs.getString("email");

						String teamDL = rs.getString("team_dl_name");
						String teamDevDL = rs.getString("team_dev_dl_name");
						String teamQADL = rs.getString("team_qa_dl_name");
						String databaseAssignmentGroup = rs
								.getString("db_assignment_group_name");

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
		String sql = "select distinct op.id as operationId, op.name as operationName, op.url as operationUrl,op.approval_required as approvalRequired from operations op, user_dl sudl, operation_dl_access oda where sudl.user_id = ? and sudl.dl_id = oda.dl_id and op.id = oda.opr_id";
		Object[] params = new Object[] { userId };
		return mySQLJdbcTemplate.query(sql, params,
				new BeanPropertyRowMapper<Operation>(Operation.class));
	}

	@Override
	public List<Integer> getFrequentlyUsedOperations(String userId) {
		log.info("Fetching frequently used operations for {}", userId);
		String sql = "select distinct uo.opr_id from USER_OPERATIONS uo where uo.user_id = ? and DATEDIFF(NOW(),uo.upd_on) <= 10";
		Object[] params = new Object[] { userId };
		int[] type = new int[] { Types.VARCHAR };
		return mySQLJdbcTemplate.queryForList(sql, params, type, Integer.class);
	}

	@Override
	public List<BusinessService> getBusinessServices() {
		log.info("Fetching business services");
		String sql = "select bs.id as serviceId, bs.name as serviceName, ag.id as assignmentGroupId, ag.name as assignmentGroup from business_svc bs,"
				+ " assignment_group ag where bs.assignment_group = ag.id";
		return mySQLJdbcTemplate.query(sql,
				new BeanPropertyRowMapper<BusinessService>(
						BusinessService.class));
	}

	@Override
	public List<Incident> getIncidents(String userId) {
		log.info("Fetching incidents for {}", userId);
		String sql = "select inc.id as id, inc.short_description as shortDescription, inc.description, inc.requested_for as requestedFor, "
				+ " inc.env as environment, bs.name as  businessService, ag.name as assignmentGroup,inc.status, inc.created_on as createdOn from incident inc, "
				+ "business_svc bs, assignment_group ag where inc.assignment_group = ag.id and inc.business_svc = bs.id and bs.assignment_group = ag.id "
				+ "and inc.user_id = ? and inc.status = 'Open' order by inc.id desc";
		Object[] params = new Object[] { userId };
		return processIncidentFetch(sql, params);
	}

	@Override
	public Incident getIncident(long incidentId) {
		log.info("Fetching incident {}", incidentId);
		Incident incident = null;
		String sql = "select inc.id as id, inc.short_description as shortDescription, inc.description, inc.requested_for as requestedFor, "
				+ " inc.env as environment, bs.name as  businessService, ag.name as assignmentGroup,inc.status, inc.created_on as createdOn from incident inc, "
				+ "business_svc bs, assignment_group ag where inc.assignment_group = ag.id and inc.business_svc = bs.id and bs.assignment_group = ag.id "
				+ "and inc.id = ?";
		Object[] params = new Object[] { incidentId };
		List<Incident> incidents = processIncidentFetch(sql, params);
		if (incidents != null && !incidents.isEmpty()) {
			incident = incidents.get(0);
		}
		return incident;
	}
	
	/**
	 * @param sql
	 * @param params
	 * @return
	 */
	private List<Incident> processIncidentFetch(String sql, Object[] params) {
		List<Incident> incidentList = mySQLJdbcTemplate.query(sql, params,
				new RowMapper<Incident>() {
					@Override
					public Incident mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						
						
						long requestId = rs.getLong("id");
						String shortDescription = rs
								.getString("shortDescription");
						String businessService = rs
								.getString("businessService");
						String description = rs.getString("description");
						String assignmentGroup = rs
								.getString("assignmentGroup");
						String requestedFor = rs
								.getString("requestedFor");
						String environment = rs.getString("environment");
						String status = rs.getString("status");
						
						Timestamp createdOnTime = rs.getTimestamp("createdOn");
						
						Incident incident = new Incident();
						incident.setId(requestId);
						incident.setShortDescription(shortDescription);
						incident.setAssignmentGroup(assignmentGroup);
						incident.setRequestedFor(requestedFor);
						incident.setDescription(description);
						incident.setStatus(status);
						incident.setEnvironment(environment);
						incident.setBusinessService(businessService);
						incident.setCreatedOn(createdOnTime.toLocalDateTime());
						return incident;

					}
				});
		return incidentList;
	}

	@Override
	public List<Request> getRequests(String userId) {
		log.info("Fetching requests for {}", userId);
		String sql = "select req.id as id, req.short_description as shortDescription, req.description, req.requested_resource, "
				+ "bs.name as  businessService, ag.name as assignmentGroup, req.user_id, req.approval, req.approval_mgr, "
				+ "req.status,req.created_on as createdOn from request req, business_svc bs, assignment_group ag where req.assignment_group = ag.id and req.business_svc =bs.id "
				+ "and bs.assignment_group = ag.id and req.user_id = ? and req.status = 'Open' order by req.id desc ";
		Object[] params = new Object[] { userId };
		return processRequestFetch(sql, params);
	}

	@Override
	public Request getRequest(long requestId) {
		log.info("Fetching request {}", requestId);
		Request request = null;
		String sql = "select req.id as id, req.short_description as shortDescription, req.description, req.requested_resource, "
				+ "bs.name as  businessService, ag.name as assignmentGroup, req.user_id, req.approval, u.name as approval_mgr, "
				+ "req.status, req.created_on as createdOn from request req, business_svc bs, assignment_group ag , user u where req.approval_mgr = u.id and req.assignment_group = ag.id and req.business_svc =bs.id "
				+ "and bs.assignment_group = ag.id and req.id = ? ";
		Object[] params = new Object[] { requestId };
		List<Request> requests = processRequestFetch(sql, params);
		if (requests != null && !requests.isEmpty()) {
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
		List<Request> requestList = mySQLJdbcTemplate.query(sql, params,
				new RowMapper<Request>() {
					@Override
					public Request mapRow(ResultSet rs, int rowNum)
							throws SQLException {

						long requestId = rs.getLong("id");
						String shortDescription = rs
								.getString("shortDescription");
						String businessService = rs
								.getString("businessService");
						String description = rs.getString("description");
						String assignmentGroup = rs
								.getString("assignmentGroup");
						String requestedResource = rs
								.getString("requested_resource");
						String approval = rs.getString("approval");
						String approvingManager = rs.getString("approval_mgr");

						String userId = rs.getString("user_id");
						User user = new User();
						user.setUserId(userId);

						String status = rs.getString("status");
						
						Timestamp createdOnTime = rs.getTimestamp("createdOn");
						
						Request request = new Request();
						request.setId(requestId);
						request.setDescription(description);
						request.setShortDescription(shortDescription);
						request.setBusinessServiceName(businessService);
						request.setAssignmentGroup(assignmentGroup);
						request.setRequestedResource(requestedResource);
						request.setApprovingManager(approvingManager);
						request.setApproval(approval.equals("Y") ? true : false);
						request.setUser(user);
						request.setStatus(status);
						request.setCreatedOn(createdOnTime.toLocalDateTime());
						return request;

					}
				});
		return requestList;
	}

	@Override
	public void saveIncident(Incident incident) {
		log.info("Saving incident...");
		String sql = "insert into incident(short_description, description, requested_for, env, business_svc, assignment_group, user_id, status) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?)";

		Object[] params = new Object[] { incident.getShortDescription(),
				incident.getDescription(), incident.getRequestedFor(),
				incident.getEnvironment(), incident.getBusinessService(),
				incident.getAssignmentGroupId(),
				incident.getUser().getUserId(), incident.getStatus() };
		mySQLJdbcTemplate.update(sql, params);
	}

	@Override
	public void saveRequest(Request request) {
		log.info("Saving Request...");
		String sql = "insert into request(short_description, description, user_id, business_svc, assignment_group, requested_resource, approval, approval_mgr, status) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		Object[] params = new Object[] { request.getShortDescription(),
				request.getDescription(), request.getUser().getUserId(),
				request.getBusinessService(), request.getAssignmentGroupId(),
				request.getRequestedResource(),
				request.isApproval() ? "Y" : "N",
				request.getApprovingManager(), request.getStatus() };
		mySQLJdbcTemplate.update(sql, params);

	}

	@Override
	public void saveUserOperation(UserOperation userOperation) {
		log.info("Saving user operation...");
		String sql = "insert into user_operations(opr_id, user_id, upd_on)  "
				+ "values (?, ?, curdate())";

		Object[] params = new Object[] { userOperation.getOperationId(),
				userOperation.getUserId() };
		mySQLJdbcTemplate.update(sql, params);

	}

	@Override
	public List<Application> getApplications() {
		log.info("Fetching all applications...");
		String sql = "select id as applicationId, name as applicationName, description as description from applications order by name";
		return mySQLJdbcTemplate.query(sql,
				new BeanPropertyRowMapper<Application>(Application.class));

	}

	@Override
	public List<DBRelease> getDBRelease() {
		log.info("Fetching database releases...");
		String sql = "select dbr.id, dbr.description, dbr.user_id, ag.name assignment_group, app.id app_id, app.name app_name, dbr.status "
				+ "from db_release dbr, assignment_group ag, applications app where dbr.assignment_group = ag.id "
				+ "and dbr.application = app.id and dbr.status = 'Open' order by dbr.id desc";
		List<DBRelease> dbReleaseList = mySQLJdbcTemplate.query(sql,
				new RowMapper<DBRelease>() {

					@Override
					public DBRelease mapRow(ResultSet rs, int rowNum)
							throws SQLException {

						long releaseId = rs.getLong("id");
						String description = rs.getString("description");
						String userId = rs.getString("user_id");
						User user = new User();
						user.setUserId(userId);
						String assignmentGroup = rs
								.getString("assignment_group");
						Application app = new Application();
						app.setApplicationId(rs.getInt("app_id"));
						app.setApplicationName(rs.getString("app_name"));
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
		String sql = "select dbr.id, dbr.env environment, dbr.release_id, dbr.description,  dbr.user_id, ag.name assignment_group, "
				+ "dbr.status from db_request dbr, assignment_group ag  "
				+ "where dbr.assignment_group = ag.id and dbr.user_Id = ? order by dbr.id desc";
		Object[] params = new Object[] { userId };
		List<DBRequest> dbRequestList = mySQLJdbcTemplate.query(sql, params,
				new RowMapper<DBRequest>() {

					@Override
					public DBRequest mapRow(ResultSet rs, int rowNum)
							throws SQLException {

						long dbrId = rs.getLong("id");
						String environment = rs.getString("environment");
						long releaseId = rs.getLong("release_id");
						DBRelease rel = new DBRelease();
						rel.setReleaseId(releaseId);
						String description = rs.getString("description");
						String assignmentGroup = rs
								.getString("assignment_group");
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
		String sql = "INSERT INTO db_release(description, user_id, assignment_group, application, status) VALUES (?, ?, ?, ?, ?)";

		Object[] params = new Object[] { dbRelease.getDescription(),
				dbRelease.getUserId().getUserId(),
				dbRelease.getAssignmentGroupId(),
				dbRelease.getApplication().getApplicationId(),
				dbRelease.getStatus() };
		mySQLJdbcTemplate.update(sql, params);

	}

	@Override
	public void saveDBRequest(DBRequest dbRequest) {
		log.info("Saving DB Request...");
		String sql = "INSERT INTO db_request(env, release_id, description,  user_id, assignment_group, status) VALUES (?, ?, ?, ?, ?, ?)";

		Object[] params = new Object[] { dbRequest.getEnvironment(),
				dbRequest.getRelease().getReleaseId(),
				dbRequest.getDescription(), dbRequest.getUser().getUserId(),
				dbRequest.getAssignmentGroupId(), dbRequest.getStatus() };
		mySQLJdbcTemplate.update(sql, params);
	}

	@Override
	public List<Request> getPendingRequests(String type, String userId) {
		log.info("Fetching pending approval requests for {}", userId);
		String sql = "select sr.id as request_id, sr.short_description, su.name from request sr, user su  where sr.approval_mgr = ? "
				+ " and sr.user_id = su.id and sr.status = 'Open' and sr.approval = 'N' order by sr.id desc";
		Object[] params = new Object[] { userId };

		List<Request> requestList = mySQLJdbcTemplate.query(sql, params,
				new RowMapper<Request>() {

					@Override
					public Request mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						long requestId = rs.getLong("request_id");
						String shortDescription = rs
								.getString("short_description");
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
		String sql = "update request set approval = ?, status = ? where id = ? ";
		Object[] params = new Object[] { status.trim(),
				"Y".equals(status.trim()) ? "Approved" : "Rejected", requestid };
		return mySQLJdbcTemplate.update(sql, params);
	}

	@Override
	public List<Change> getChangesForApproval(String userId) {
		log.info("Fetching pending approval requests for {}", userId);
		String sql = "select sr.id as request_id, sr.short_description, su.name from change_request sr, user su  where sr.approval_mgr = ? "
				+ " and sr.user_id = su.id and sr.status = 'Open' and sr.approval = 'N' order by sr.id desc";
		Object[] params = new Object[] { userId };

		List<Change> changeList = mySQLJdbcTemplate.query(sql, params,
				new RowMapper<Change>() {

					@Override
					public Change mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						long requestId = rs.getLong("request_id");
						String shortDescription = rs
								.getString("short_description");
						User user = new User();
						user.setName(rs.getString("name"));

						Change change = new Change();
						change.setId(requestId);
						change.setShortDescription(shortDescription);
						change.setUser(user);
						return change;
					}
				});

		return changeList;
	}
}
