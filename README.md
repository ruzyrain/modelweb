#<center>用户管理与鉴权服务接口定义#
<center>2015/1/14
----------
<br>
##1、用户资源管理接口

- <big><big>**登陆**<br>

----------
	function:	flex平台登陆验证
	URL:		login/flexvalidate
	method:		post
	declaration:public @ResponseBody String flexLoginValidate(
				@RequestParam(value = "nam") String userName,
				@RequestParam(value = "psw") String password, 
				@RequestParam(value = "check") String check, 
				HttpSession session,HttpServletResponse response,
				HttpServletRequest request)

	parameter:	nam（用户名）、psw（密码）、check（验证码）
	return:		ok：登陆成功；1001：验证码不正确；1002：用户名或密码不正确；1003：邮箱未验证；

----------
	function:	获取随机数，用于与页面密码做sha1运算
	URL:		login/getrand
	method:		get
	declaration:public @ResponseBody String getRand(HttpSession session)
	parameter:	无
	return:		String（随机数）

----------
	function:	获取服务器时间，用于与页面密码做sha1运算
	URL:		login/getdate
	method:		get
	declaration:public @ResponseBody String getDate(HttpSession session)
	parameter:	无
	return:		String（服务器时间格式为yyyyMMddHHmmss）

----------
	function:	登陆成功后获取登陆信息
	URL:		login/loginInfo
	method:		get
	declaration:public @ResponseBody
				Map<String, String> getLoginInfo(HttpSession session)；

	parameter:	无
	return:		json数据username、userid、sessionid

----------
	function:	产生验证码
	URL:		/captcha/getCode
	method:		get
	declaration:public @ResponseBody String build(HttpSession session)
	parameter:	无
	return:		String（验证码）

- **资源**<br>


----------
	function:	创建新资源
	URL:		/myresources/addNew
	method:		post、get
	declaration:public @ResponseBody
				List<Integer> myCreatedResourcesPageLoad(
				@RequestParam(value = "name") final String resourceName,
				@RequestParam(value = "num") Integer resourceNum,
				@RequestParam(value = "type") final String resourceType,
				@RequestParam(value = "des") final String resourceDes,
				@RequestParam(value = "ri-checkInfo") final String checkInfo,
				HttpSession session, final HttpServletRequest request)
				throws MalformedURLException, RemoteException, Exception,NullPointerException

	parameter:	resourceName（资源名称）、resourceNum（资源数量）、resourceType（资源类型）、
				resourceDes（资源描述）、ri-checkInfo（资源验证）
	return:		List<Integer>（资源id列表）

##2、鉴权服务接口
----------

	function:	判断请求权限的用户身份是否合法（是否存在且已登录系统）
	declaration:public boolean isValidUserClient(@WebParam(name="uClientInfo") UserClientInfo info)
	parameter:	UserClientInfo bean
	return:		boolean（true合法、false非法）
	remarks：	类UserClientInfo

				public class UserClientInfo{

					/**
	 				* 用户信息bean
					 */
					private static final long serialVersionUID = 4561107246771847648L;


					private String ClientID;
					private UserCheckInfo checkInfo;
	
					public String getClientID() {
						return ClientID;
					}

					public void setClientID(String clientID) {
						ClientID = clientID;
					}

					public UserCheckInfo getCheckInfo() {
						return checkInfo;
					}
	
					public void setCheckInfo(UserCheckInfo checkInfo) {
						this.checkInfo = checkInfo;
					}

				}

----------

	function:	添加资源操作的权限鉴别
	declaration:public Privilege checkAddAction(@WebParam(name="addAct") AddResourceAction act)
	parameter:	AddResourceAction bean
	return:		Privilege
	remarks：	1、类AddResourceAction

				public class AddResourceAction implements Action {
	
					/**
					 * 资源添加实体类
					 */
					private static final long serialVersionUID = 1L;
					private UserClientInfo client;
				
					public AddResourceAction(){
		
					}
	
					public void setClient(UserClientInfo client) {
						this.client = client;
					}

					public AddResourceAction(UserClientInfo client){
						this.client = client;
					}

					@Override
					public Operation getOperation() {
						return DCOperation.ADD_RESOURCE;
					}

					@Override
					public UserClientInfo getClient() {
						return client;
					}

				}

				2、类Privilege

				public class Privilege implements Serializable {
					/**
					 *权限结果实体类
					 */
					private static final long serialVersionUID = 1L;

					public enum Result {
						/**
						 * permit some action
						 */
						Permitted,
						/**
						 * Deny some action
						 */
						Denied
					}

					private Result result;
					private String cause;
				
					public Result getResult() {
						return result;
					}
				
					public void setResult(Result result) {
						this.result = result;
					}
				
					public String getCause() {
						return cause;
					}
				
					public void setCause(String cause) {
						this.cause = cause;
					}
				}
 
----------

	function:	定义资源操作的权限鉴别
	declaration:public Privilege checkDefAction(@WebParam(name="defAct") DefResourceAction act)
	parameter:	DefResourceAction bean
	return:		Privilege
	remarks：	类DefResourceAction
				public class DefResourceAction implements Action {
	
					/**
					 * 权限鉴别实体类
					 */
					private static final long serialVersionUID = 1L;
					public String getRid() {
						return rid;
					}
				
					public void setRid(String rid) {
						this.rid = rid;
					}
				
					public void setClient(UserClientInfo client) {
						this.client = client;
					}
				
					private String rid;
					private UserClientInfo client;
					
					public DefResourceAction(){
						
					}
					
					public DefResourceAction(UserClientInfo client, String rid){
						this.client = client;
						this.rid = rid;
					}
				
					@Override
					public UserClientInfo getClient() {
						return client;
					}
				
					@Override
					public Operation getOperation() {
						return DCOperation.DEF_RESOURCE;
					}
					
					public String getResourceID(){
						return rid;
					}
					
				}

----------

	function:	删除资源权限鉴别
	declaration:public Privilege checkDelAction(@WebParam(name="delAct") DelResourceAction act)
	parameter:	DelResourceAction bean
	return:		Privilege
	remarks：	类DelResourceAction
				public class DelResourceAction implements Action {
					
					/**
					 * 删除资源实体类
					 */
					private static final long serialVersionUID = 1L;
					public String getRid() {
						return rid;
					}
				
					public void setRid(String rid) {
						this.rid = rid;
					}
				
					public void setClient(UserClientInfo client) {
						this.client = client;
					}
				
					private String rid;
					private UserClientInfo client;
					
					public DelResourceAction(){
						
					}
					
					public DelResourceAction(UserClientInfo client, String rid){
						this.client = client;
						this.rid = rid;
					}
				
					@Override
					public UserClientInfo getClient() {
						return client;
					}
				
					@Override
					public Operation getOperation() {
						return DCOperation.DEL_RESOURCE;
					}
					
					public String getResourceID(){
						return rid;
					}
				
				}

----------

	function:	属性访问权限鉴别
	declaration:public Privilege checkPropAccessAction(@WebParam(name="accAct") PropAccessAction act)
	parameter:	PropAccessAction bean
	return:		Privilege
	remarks：	类PropAccessAction
				public class PropAccessAction implements Action {
				
					/**访问实体类
					 * 
					 */
					private static final long serialVersionUID = 1L;
				
					public enum Method {
						READ, WRITE,DEFINE
					}
				
					private String rid;
					private String path;
					private Method method;
					private UserClientInfo client;
					
					public PropAccessAction()
					{
						
					}
					
					public PropAccessAction(UserClientInfo client, Method method, String rid, String path){
						this.client = client;
						this.method = method;
						this.rid = rid;
						this.path = path;
					} 
					
					public String getRid() {
						return rid;
					}
				
					public void setRid(String rid) {
						this.rid = rid;
					}
				
					public String getPath() {
						return path;
					}
				
					public void setPath(String path) {
						this.path = path;
					}
				
					public void setMethod(Method method) {
						this.method = method;
					}
				
					public void setClient(UserClientInfo client) {
						this.client = client;
					}
				
					@Override
					public UserClientInfo getClient() {
						return client;
					}
				
					@Override
					public Operation getOperation() {
						return DCOperation.ACC_PROPERTY;
					}
					
					public String getResourceID(){
						return rid;
					}
					
					public Method getMethod(){
						return method;
					}
				
					public String getPropertyID(){
						return path;
					}
				
				}

----------

	function:	初始化资源的创建者权限视图
	declaration:public void PrivilegeInitialize(@WebParam(name="privilegeDef") PrivilegeDefinition privilegeDef)
	parameter:	PrivilegeDefinition bean
	return:		无
	remarks：	类PrivilegeDefinition
				public class PrivilegeDefinition {
				
					/**
					 * 权限定义类
					 */
					private static final long serialVersionUID = 1L;
				
					public String resourceID;
					public String initViewName;
					public String initViewDes;
					public String definable;
					public Set<PrivilegeEntry> privilegeEntries = new HashSet<PrivilegeEntry>(0);
				
					@Override
					public int hashCode() {
						final int prime = 31;
						int result = 1;
						result = prime * result
								+ ((definable == null) ? 0 : definable.hashCode());
						result = prime
								* result
								+ ((privilegeEntries == null) ? 0 : privilegeEntries.hashCode());
						return result;
					}
				
					@Override
					public boolean equals(Object obj) {
						if (this == obj)
							return true;
						if (obj == null)
							return false;
						if (getClass() != obj.getClass())
							return false;
						PrivilegeDefinition other = (PrivilegeDefinition) obj;
						if (definable == null) {
							if (other.definable != null)
								return false;
						} else if (!definable.equals(other.definable))
							return false;
						if (privilegeEntries == null) {
							if (other.privilegeEntries != null)
								return false;
						} else if (!privilegeEntries.equals(other.privilegeEntries))
							return false;
						return true;
					}
				}