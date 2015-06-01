package HttpServer;
/*
 * 全局类型定义
 */
public interface ConstantInterface {
	String JOININ_HEADER="2";
	byte POSITIONING_HEADER='9';
	String OUTTAKEN_HEADER="3";
	/*
	 * MD package
	 */
	int TYPE1_POS=53;
	int TYPE2_POS=54;
	// Edit By TaoYe
	/**
	 * 网关节点编号长度
	 */
	int GATEWY_GID_LEN=16;
	/**
	 * 小节点编号起始位置,网关心跳包的标识起始位置
	 */
	int BLUE_MD_POS=37;
	/**
	 * 小节点编号 长度
	 */
	int MD_GID_LEN=16;
	
	/**
	 * 失联包标示位开始位置
	 */
	int MISS_POS=53;
	
	/**
	 * 蓝牙小节点的起始4位特殊编号
	 */
	String BLUE_MDID_MARK="####";
	
	/**
	 * 手机蓝牙失联包标识
	 */
	String BLUE_MISS_MARK="ge";
	
	/**
	 * 手机蓝牙重连包标识
	 */
	String BLUE_RECONNECT_MARK="gd";
	
	/**
	 * 网关节点心跳包长度
	 */
	int GATEWY_HEARTBEAT_LENGTH=39;
	
	/**
	 * 网关节点心跳包标识
	 */
	String GATEWAYHEARTBEAT_HEADER="7";
	
	/**
	 * 小节点心跳包标识
	 */
	char MD_HEATBEAT='a';
	
	/**
	 * 推送到的业务服务器IP
	 * 目前版本是肖松
	 */
	String SERVERID="10.10.12.67";
	
	/**
	 * 推送到的定位服务器IP
	 * 目前版本是陶冶
	 */
	String SERVICEID="10.10.12.164";
	
}
